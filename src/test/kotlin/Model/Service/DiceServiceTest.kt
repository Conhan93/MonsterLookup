package Model.Service

import Model.Data.Base.APIReference
import Model.Data.Monster.Action
import Model.Data.Monster.Damage
import Model.Service.DiceService.DiceService
import Model.Service.DiceService.DiceServiceImpl
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DiceServiceTest {

    val logger = KotlinLogging.logger {}

    @Test
    fun `Should be able to roll an action damage dice`() {
        val service: DiceService = DiceServiceImpl()

        var testDamage = Damage(damage_type = APIReference(name = "Blunt"))
        var testAction = Action(damage = listOf(testDamage))

        repeat(100) {
            val times = (1..20).random()
            val sides = (1..20).random()
            val bonus = (1..999).random()

            testDamage = testDamage.copy(damage_dice = "${times}d${sides}+${bonus}")
            testAction = testAction.copy(damage = listOf(testDamage))

            val rollResult = service.rollActionDamageDice(testAction)

            assert((bonus..(times*sides+bonus)).contains(rollResult["Blunt"])) {
                "Failed with value $rollResult for times: $times, sides: $sides, bonus: $bonus"
            }
        }
    }

    @Test
    fun `Should be able to roll action damage dice without bonus`() {
        val service: DiceService = DiceServiceImpl()

        var testDamage = Damage(damage_type = APIReference(name = "Blunt"))
        var testAction = Action(damage = listOf(testDamage))

        repeat(100) {
            val times = (1..20).random()
            val sides = (1..20).random()

            testDamage = testDamage.copy(damage_dice = "${times}d${sides}")
            testAction = testAction.copy(damage = listOf(testDamage))

            val rollResult = service.rollActionDamageDice(testAction)

            assert((1..(times*sides)).contains(rollResult["Blunt"])) {
                "Failed with value $rollResult for times: $times, sides: $sides"
            }
        }
    }

    @Test
    fun `Should give correct hit dice result`() {
        val service: DiceService = DiceServiceImpl()

        repeat(100) {
            val attackBonus = (0..999).random()

            val result = service.rollHitDice(attackBonus)

            assert((1..1019).contains(result.total))
            assert((1..20).contains(result.roll))
        }
    }

    @Test
    fun `Should give correct hit dice result without bonus`() {
        val service: DiceService = DiceServiceImpl()

        repeat(50) {
            val result = service.rollHitDice()
            assert((1..20).contains(result.roll))
            assert(result.roll == result.total)
        }
    }

    @Test
    fun `Should throw on none matching dice string`() {
        val service: DiceService = DiceServiceImpl()

        val testDamage = Damage(
            damage_dice = "foo34242265",
            damage_type = APIReference(name = "foo")
        )
        val testAction = Action(damage = listOf(testDamage))

        assertThrows<IllegalArgumentException> {
            service.rollActionDamageDice(testAction)
        }
    }

    @Test
    fun `Should throw on null dice string`() {
        val service: DiceService = DiceServiceImpl()

        val testDamage = Damage(damage_type = APIReference(name = "foo"))
        val testAction = Action(damage = listOf(testDamage))

        assertThrows<IllegalArgumentException> {
            service.rollActionDamageDice(testAction)
        }
    }
}