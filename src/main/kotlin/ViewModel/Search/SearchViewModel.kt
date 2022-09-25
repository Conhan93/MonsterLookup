package ViewModel.Search

import Model.Data.Monster.Monster
import Model.Service.ContentService.ContentRequest
import Model.Service.ContentService.ContentService
import Model.Storage.ILocalStorage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val contentService: ContentService,
    private val storage: ILocalStorage
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    val monsterState = mutableStateOf(Monster())
    var name by mutableStateOf("")
        private set

    var isSearching by mutableStateOf(false)
        private set

    var isDropDownMenuExpanded by mutableStateOf(false)

    var dropDownMenuNames : List<String> = listOf()

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.onSetSearch -> isSearching = event.isSearching
            is SearchEvent.onSetName -> onSetName(event.name)
            is SearchEvent.onDropDownMenuExpand -> {
                isDropDownMenuExpanded = event.isExpanded

                updateDropDownNames()
            }
            is SearchEvent.onSearch -> {
                isSearching = true
                isDropDownMenuExpanded = false
                performSearch(event.onResult)

            }
            is SearchEvent.onClickDropDownMenuItem -> {
                name = event.name

                isDropDownMenuExpanded = false
            }
        }
    }

    private fun performSearch(onResult : (Result<Monster>) -> Unit) {

        val request = ContentRequest.RequestByName(name, Monster::class)

        scope.launch {
            try {
                contentService
                    .getContentAsync(request)
                    ?.let {
                        monsterState.value = it as Monster
                        onResult(Result.success(it as Monster))
                        isSearching = false
                    }
            } catch (e : Exception) {
                onResult(Result.failure(e))
                isSearching = false
            }

            // reset name after search
            onSetName("")
        }
    }

    private fun onSetName(name : String) {
        this@SearchViewModel.name = name

        isDropDownMenuExpanded = name.isNotEmpty()

        updateDropDownNames()
    }

    private fun updateDropDownNames() {
        dropDownMenuNames = if (isDropDownMenuExpanded)
            storage
                .getMonsterNames()
                .filter { if (name.isNotEmpty()) it.contains(name, ignoreCase = true) else true }
        else listOf()
    }

}