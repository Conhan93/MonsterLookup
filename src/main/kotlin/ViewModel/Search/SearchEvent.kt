package ViewModel.Search

import Model.Data.Monster.Monster

sealed class SearchEvent {
    data class onSetSearch(val isSearching : Boolean) : SearchEvent()
    data class onSetName(val name : String) : SearchEvent()
    data class onSearch(val onResult : (Result<Monster>) -> Unit) : SearchEvent()
    data class onDropDownMenuExpand(val isExpanded : Boolean) : SearchEvent()
    data class onClickDropDownMenuItem(val name : String) : SearchEvent()
}
