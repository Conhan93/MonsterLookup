package Service


import Model.Monster.Action
import Model.Util.HitRoll

interface DiceService {

    /**
     *
     * Rolls all the damage dice in an action and returns
     * the rolls in a map
     *
     * @param action the action whose dice you want rolled
     *
     * @return map where the keys are the damage type and values
     * are the values of the rolls
     */
    fun rollActionDamageDice(action: Action): Map<String, Int>

    fun rollHitDice(attackBonus: Int? = null) : HitRoll
}