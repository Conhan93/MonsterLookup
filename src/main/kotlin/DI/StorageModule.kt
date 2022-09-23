package DI

import Storage.*
import org.koin.dsl.module


val storageModule = module {
    single<ILocalStorage> { LocalStorage(getStorageProperties("data/db", StorageTypes.H2)) }
}