package Service

import Model.Base.APIReference
import Model.Monster.Action
import Model.Monster.Damage
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
    fun `Should throw on none matching dice string`() {
        val service: DiceService = DiceServiceImpl()

        val testDamage = Damage(damage_dice = "foo34242265", damage_type = APIReference(name = "foo"))
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