package ViewModel.Content

import Model.Data.Base.APIReference
import Model.Data.Monster.Action
import Model.Data.Monster.Monster
import Model.Data.Monster.SpecialAbilities
import Model.Data.Spell.Spell
import Model.Data.Util.DamageRoll
import Model.Data.Util.ItemRoll

import Model.Service.ContentService.ContentRequest
import Model.Service.ContentService.ContentService
import Model.Service.DiceService.DiceService
import Model.Service.SharedPropertiesService


import androidx.compose.runtime.*
import kotlinx.coroutines.*

import mu.KotlinLogging
import org.koin.java.KoinJavaComponent.get

class ContentViewModel(
    private val contentService : ContentService,
    sharedPropertiesService: SharedPropertiesService
) {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val logger = KotlinLogging.logger {}

    val monsterSubscription = sharedPropertiesService.observeProperty<Monster>("search_monster")

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
                    event.ability?.let { loadPopupSpells(it) }
                else
                    spellDetailSpells.clear()
            }
            is ContentEvent.onClickAction -> {
                if (event.isClicked) {
                    event.action?.let { onActionDiceRoll(it) }
                    isActionClicked = true
                }

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
        logger.debug { "roll result: $diceRoll" }
    }
}