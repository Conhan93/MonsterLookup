package ViewModel.Content

import Model.Data.Monster.Action
import Model.Data.Monster.SpecialAbilities

sealed class ContentEvent {
    data class onClickSpecialAbility(
        val expand : Boolean,
        val ability : SpecialAbilities? = null
    ) : ContentEvent()

    data class onClickAction(val isClicked: Boolean, val action: Action? = null) : ContentEvent()
}
