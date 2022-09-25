package Model.Service.ContentService

import kotlin.reflect.KClass

sealed class ContentRequest {

    data class RequestByName(
        val name : String,
        val klass : KClass<*>
        ) : ContentRequest()

    data class RequestByReference(
        val ref: Model.Data.Base.APIReference,
        val klass: KClass<*>
        ) : ContentRequest()
}


fun ContentRequest.getName() : String? {
    return when(this) {
        is ContentRequest.RequestByName -> this.name
        is ContentRequest.RequestByReference -> this.ref.name
    }
}

fun ContentRequest.getClass() : KClass<*> {
    return when(this) {
        is ContentRequest.RequestByName -> this.klass
        is ContentRequest.RequestByReference -> this.klass
    }
}