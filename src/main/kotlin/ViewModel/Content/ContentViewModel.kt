package ViewModel.Content

import Model.Base.APIReference
import Model.Monster.SpecialAbilities
import Model.Spell.Spell
import Service.ContentRequest
import Service.ContentService
import ViewModel.Search.SearchViewModel
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContentViewModel(
    private val contentService : ContentService,
    searchViewModel: SearchViewModel
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    var monster by searchViewModel.monsterState


    var isAbilityClicked by mutableStateOf(false)
        private set


    val spellDetailSpells = mutableStateListOf<Spell>()


    fun onEvent(event: ContentEvent) {
        when(event) {
            is ContentEvent.onClickSpecialAbility -> {
                isAbilityClicked = event.expand

                if (isAbilityClicked)
                    loadPopupSpells(event.ability!!)
                else
                    spellDetailSpells.clear()
            }
        }
    }

    private fun loadPopupSpells(specialAbility : SpecialAbilities) {
        scope.launch {
            val requests = specialAbility
                .spellcasting
                .spells
                .map { APIReference(name = it.name, url = it.url) }
                .map { ContentRequest.RequestByReference(it, Spell::class) }

            contentService.getContentAsync(requests) { spellDetailSpells.add(it as Spell) }
        }
    }
}