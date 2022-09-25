package ViewModel.Content

import Model.Data.Base.APIReference
import Model.Data.Monster.Action
import Model.Data.Monster.SpecialAbilities
import Model.Data.Spell.Spell
import Model.Data.Util.DamageRoll
import Model.Data.Util.ItemRoll

import Model.Service.ContentService.ContentRequest
import Model.Service.ContentService.ContentService
import Model.Service.DiceService.DiceService

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
            hitRoll = diceService.rollHitDice(action.attack_bonus),
            itemName = action.name ?: "No name",
            itemDescr = action.desc ?: ""
        )

        isActionClicked = true
        logger.debug { "roll result: $diceRoll" }
    }
}