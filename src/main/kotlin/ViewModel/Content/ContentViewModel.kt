package ViewModel.Content

import Model.Base.APIReference
import Model.Monster.Action
import Model.Monster.SpecialAbilities
import Model.Spell.Spell
import Model.Util.DamageRoll
import Model.Util.ItemRoll
import Service.ContentRequest
import Service.ContentService
import Service.DiceService
import ViewModel.Search.SearchViewModel
import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.koin.java.KoinJavaComponent.get

class ContentViewModel(
    private val contentService : ContentService,
    searchViewModel: SearchViewModel
) {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val logger = KotlinLogging.logger {}

    var monster by searchViewModel.monsterState
        private set

    var diceRoll : ItemRoll<DamageRoll>? = null
        private set


    var isAbilityClicked by mutableStateOf(false)
        private set
    var isActionClicked by mutableStateOf(false)
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
            is ContentEvent.onClickAction -> {
                if (event.isClicked)
                    event.action?.let { onActionDiceRoll(it) }
                else
                    isActionClicked = false

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

    private fun onActionDiceRoll(
        action: Action,
        diceService: DiceService = get(DiceService::class.java)
    ) {
        logger.debug { "Action rolled: $action" }
        diceRoll = ItemRoll(
            rolls = diceService
                .rollActionDamageDice(action)
                .map { DamageRoll(damageType = it.key, damage = it.value) },
            itemName = action.name ?: "No name",
            itemDescr = action.desc ?: ""
        )

        isActionClicked = true
        logger.debug { "roll result: $diceRoll" }
    }
}