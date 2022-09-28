package ViewModel

import Model.Data.Base.APIReference
import Model.Data.Monster.Action
import Model.Data.Monster.Damage
import Model.Data.Monster.Monster
import Model.Service.ContentService.ContentService
import Model.Service.DiceService.DiceService
import Model.Service.DiceService.DiceServiceImpl
import Model.Service.SharedPropertiesServiceImpl
import TestHelper.Resource.LoadTestResource
import TestHelper.Resource.getTestResource
import ViewModel.Content.ContentEvent
import ViewModel.Content.ContentViewModel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension

import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class ContentViewModelTest: KoinTest {

    private var mockContentService = mock<ContentService> {}
    private val sharedPropertiesService = SharedPropertiesServiceImpl()

    @BeforeEach
    fun before() {
        mockContentService = mock {}
        sharedPropertiesService.clear()
    }

    @RegisterExtension
    @JvmField
    val koinTestExtension = KoinTestExtension.create {
        modules(  module {  factory<DiceService> { DiceServiceImpl() } } )
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

    @Test
    fun `On action clicked should update diceRoll`() {

        assertNotNull(get(DiceService::class.java))

        val viewModel = ContentViewModel(mockContentService, sharedPropertiesService)

        assertNull(viewModel.diceRoll)

        val action = Action(damage = listOf(Damage(APIReference(name="foo"), "1d6")))

        viewModel.onEvent(ContentEvent.onClickAction(true, action))

        assertNotNull(viewModel.diceRoll)
        assert(viewModel.diceRoll!!.itemDescr.isEmpty())
        assertEquals("foo", viewModel.diceRoll!!.rolls[0].damageType)
        assert((1..6).contains(viewModel.diceRoll!!.rolls[0].damage))

    }

}