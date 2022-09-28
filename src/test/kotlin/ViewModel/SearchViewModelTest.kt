package ViewModel

import Model.Data.Monster.Monster
import Model.Service.ContentService.ContentService
import Model.Service.SharedPropertiesService
import Model.Service.SharedPropertiesServiceImpl
import Model.Storage.ILocalStorage
import TestHelper.Resource.LoadTestResource
import TestHelper.Resource.getTestResource
import ViewModel.Search.SearchEvent
import ViewModel.Search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var contentService: ContentService
    private lateinit var storage: ILocalStorage
    private val sharedPropertiesService: SharedPropertiesService = SharedPropertiesServiceImpl()


    @BeforeEach
    fun before() {
        sharedPropertiesService.clear()
        contentService = mock {}
        storage = mock {}
    }

    @Test
    fun `OnSetName event should update name`() {
        val viewModel = SearchViewModel(contentService, storage, sharedPropertiesService)

        assert(viewModel.name.isEmpty())

        viewModel.onEvent(SearchEvent.onSetName("foo"))

        assert(viewModel.name.equals("foo"))
    }

    @Test
    fun `OnSetSearch event should update isSearching`() {
        val viewModel = SearchViewModel(contentService, storage, sharedPropertiesService)

        assert(viewModel.isSearching == false)

        viewModel.onEvent(SearchEvent.onSetSearch(true))

        assert(viewModel.isSearching == true)

        viewModel.onEvent(SearchEvent.onSetSearch(false))

        assert(viewModel.isSearching == false)
    }

    @Test
    fun `onClickDropDownMenuItem should update name`() {
        val viewModel = SearchViewModel(contentService, storage, sharedPropertiesService)

        assert(viewModel.name.isEmpty())

        viewModel.onEvent(SearchEvent.onClickDropDownMenuItem("foo"))

        assert(viewModel.name.equals("foo"))
        assert(viewModel.isDropDownMenuExpanded == false)
    }

    @Test
    fun `OnDropDownMenuExpand should populate drop down menu list`() {
        val names = listOf("foo", "bar", "har")
        whenever(storage.getMonsterNames(anyOrNull())).thenReturn(names)

        val viewModel = SearchViewModel(contentService, storage, sharedPropertiesService)

        assert(viewModel.name.isEmpty())

        viewModel.onEvent(SearchEvent.onDropDownMenuExpand(true))

        assertEquals(true ,viewModel.isDropDownMenuExpanded)
        assertEquals(viewModel.dropDownMenuNames, names)

    }


    @Test
    fun `OnSearch should fetch monster`() = runTest {
        val testMonster = getTestResource(LoadTestResource.blackDragon) as Monster
        whenever(contentService.getContentAsync(any())).thenReturn(testMonster)

        val viewModel = SearchViewModel(contentService, storage, sharedPropertiesService)

        viewModel.onEvent(SearchEvent.onSearch {
            assert(it.isSuccess)
            assertEquals(testMonster, it.getOrNull())
        } )
        assertEquals(false, viewModel.isDropDownMenuExpanded)
        withContext(Dispatchers.Default) { delay(10) } // let contentrequest finish
        assertEquals(false, viewModel.isSearching)
        assert(viewModel.name.isEmpty())
    }

    @Test
    fun `OnSearch should update shared monster property`() = runTest {
        val testMonster = getTestResource(LoadTestResource.blackDragon) as Monster
        whenever(contentService.getContentAsync(any())).thenReturn(testMonster)

        val viewModel = SearchViewModel(contentService, storage, sharedPropertiesService)

        val propertyId = "search_monster"

        // should not exist yet
        assertNull(sharedPropertiesService.getPropertyValue(propertyId))

        val property = sharedPropertiesService.observeProperty<Monster>(propertyId)

        // default value null
        assertNull(property.value)

        viewModel.onEvent(SearchEvent.onSearch {})
        // let value be found and set
        withContext(Dispatchers.Default) {delay(10)}

        assertEquals(testMonster, property.value)
    }
}