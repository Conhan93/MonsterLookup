package ViewModel.Content

import Model.Monster.SpecialAbilities

sealed class ContentEvent {
    data class onClickSpecialAbility(
        val expand : Boolean,
        val ability : SpecialAbilities? = null
    ) : ContentEvent()
}
