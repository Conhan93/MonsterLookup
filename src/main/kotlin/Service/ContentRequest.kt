package Service

import Model.Base.APIReference
import kotlin.reflect.KClass

sealed class ContentRequest {

    data class RequestByName(
        val name : String,
        val klass : KClass<*>
        ) : ContentRequest()

    data class RequestByReference(
        val ref: APIReference,
        val klass: KClass<*>
        ) : ContentRequest()
}