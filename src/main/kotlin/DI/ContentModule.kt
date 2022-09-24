package DI

import Service.ContentService
import Service.ContentServiceImpl
import Service.DiceService
import Service.DiceServiceImpl

import org.koin.dsl.module


val contentModule = module {
    factory<DiceService> { DiceServiceImpl() }
    single<ContentService> { ContentServiceImpl(storage = get()) }
}