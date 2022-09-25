package DI

import Model.Storage.ILocalStorage
import Model.Storage.LocalStorage
import Model.Storage.StorageTypes
import Model.Storage.getStorageProperties
import org.koin.dsl.module


val storageModule = module {
    single<ILocalStorage> { LocalStorage(getStorageProperties("data/db", StorageTypes.H2)) }
}