package ViewModel

import Model.Data.Base.APIReference
import Model.Data.Base.Base
import Model.Data.Monster.Monster
import Model.Data.Monster.SpecialAbilities
import Model.Data.Monster.Spells.SpellCasting
import Model.Data.Spell.Spell
import Model.Service.ContentService.ContentRequest
import Model.Service.ContentService.ContentService
import Model.Service.SharedPropertiesServiceImpl
import TestHelper.Resource.LoadTestResource
import TestHelper.Resource.getTestResource
import ViewModel.Content.ContentEvent
import ViewModel.Content.ContentViewModel
import androidx.compose.animation.core.snap
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.readable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
//import org.mockito.Mockito.*
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class ContentViewModelTest {

    private var mockContentService = mock<ContentService> {}
    private val sharedPropertiesService = SharedPropertiesServiceImpl()

    @BeforeEach
    fun before() {
        mockContentService = mock {}
        sharedPropertiesService.clear()
    }

    @Test
    fun `OnClickAction should update isActionClicked`() {
        val viewModel = ContentViewModel(mockContentService, sharedPropertiesService)

        // default value
        assertEquals(false, viewModel.isActionClicked)

        viewModel.onEvent(ContentEvent.onClickAction(true))

        assertEquals(true, viewModel.isActionClicked)

        viewModel.onEvent(ContentEvent.onClickAction(false))

        assertEquals(false, viewModel.isActionClicked)
    }

    @Test
    fun `OnClickSpecialAbility should update isAbilityClicked`() {
        val viewModel = ContentViewModel(mockContentService, sharedPropertiesService)

        // default value
        assertEquals(false, viewModel.isAbilityClicked)

        viewModel.onEvent(ContentEvent.onClickSpecialAbility(true))

        assertEquals(true, viewModel.isAbilityClicked)

        viewModel.onEvent(ContentEvent.onClickSpecialAbility(false))

        assertEquals(false, viewModel.isAbilityClicked)
    }


    @Test
    fun `monsterSubscription should update on property set`() = runTest {
        val viewModel = ContentViewModel(mockContentService, sharedPropertiesService)

        assertNull(viewModel.monsterSubscription.value)

        val testMonster = getTestResource(LoadTestResource.blackDragon)

        sharedPropertiesService.setPropertyValue("search_monster", testMonster)

        assertEquals(testMonster, viewModel.monsterSubscription.value as Monster)

        sharedPropertiesService.setPropertyValue("search_monster", null)

        assertNull(viewModel.monsterSubscription.value)
    }

}