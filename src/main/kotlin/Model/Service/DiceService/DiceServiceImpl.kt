package Model.Service.DiceService

import Model.Data.Monster.Action
import Model.Data.Monster.Damage
import Model.Data.Util.HitRoll

import mu.KotlinLogging

class DiceServiceImpl : DiceService {

    private val logger = KotlinLogging.logger { }

    override fun rollActionDamageDice(action: Action): Map<String, Int> {

        return action
            .damage
            .associate {
                 it.damage_type.name!! to rollDice(it)
            }
    }

    override fun rollHitDice(attackBonus: Int?): HitRoll {
        val roll = (1..20).random()

        val bonus = attackBonus ?: 0
        return HitRoll(roll, roll + bonus)
    }

    private data class DamageDice(
        val times : Int,
        val sides : Int,
        val bonus : Int? = null
    )

    private fun rollDice(damage: Damage) : Int {
        damage.damage_dice?.let { diceString ->
            val dice = decodeDiceString(diceString)

            var roll = 0

            repeat(dice.times) {
                roll += (1..dice.sides).random()
            }

            dice.bonus?.let {
                roll += it
            }

            return roll
        }

        val message = "No dice string"

        logger.error { message }
        throw IllegalArgumentException(message)
    }

    private fun decodeDiceString(diceString: String) : DamageDice {
        val pattern = Regex("""([\d]{1,2})[d]([\d]{1,2})([+]([\d]{1,3}))?${'$'}""")


        if (pattern.containsMatchIn(diceString)) {
            val values = pattern
                .findAll(diceString)
                .map { it.groups }
                .first()
                .mapNotNull { it?.value?.toIntOrNull() }
            logger.debug { "Matched values: $values" }

            return if(values.size >= 3)
                DamageDice(values[0], values[1], values[2] )
            else
                DamageDice(values[0], values[1])
        } else {
            val message = "diceString does not match pattern: $diceString"
            logger.error { message }
            throw IllegalArgumentException(message)
        }
    }
}