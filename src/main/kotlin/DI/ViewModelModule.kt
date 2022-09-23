package DI

import ViewModel.Search.SearchViewModel
import org.koin.dsl.module


val viewModelModule = module {

    single { SearchViewModel(get(), get())}
}