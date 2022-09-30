package DI

import ViewModel.Content.ContentViewModel
import ViewModel.Main.MainViewModel
import ViewModel.Search.SearchViewModel
import org.koin.dsl.module


val viewModelModule = module {

    single { SearchViewModel(get(), get(), get())}
    single(createdAtStart = true) { ContentViewModel(get(), get()) }
    single { MainViewModel(get()) }
}