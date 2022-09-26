package DI

import Model.Service.ContentService.ContentService
import Model.Service.ContentService.ContentServiceImpl
import Model.Service.DiceService.DiceService
import Model.Service.DiceService.DiceServiceImpl
import Model.Service.SharedPropertiesService
import Model.Service.SharedPropertiesServiceImpl

import org.koin.dsl.module


val contentModule = module {
    single<SharedPropertiesService> { SharedPropertiesServiceImpl() }
    factory<DiceService> { DiceServiceImpl() }
    single<ContentService> { ContentServiceImpl(storage = get()) }
}