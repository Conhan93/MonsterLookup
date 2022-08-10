package State

import Model.Monster.Monster
import Model.Spell.Spell

sealed class State {
    object Loading : State()
    data class Content(val monster: Monster? = null, val spell: Spell? = null) : State()
    data class Error(val message : String, val cause : Throwable? = null) : State()
}