package DI

import Service.ContentService
import Service.ContentServiceImpl

import org.koin.dsl.module


val contentModule = module {

    single<ContentService> { ContentServiceImpl() }
}