package DI

import ViewModel.Content.ContentViewModel
import ViewModel.Search.SearchViewModel
import org.koin.dsl.module


val viewModelModule = module {

    single { SearchViewModel(get(), get())}
    single { ContentViewModel(get(), get()) }
}