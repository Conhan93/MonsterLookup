package Model.Service

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.isActive
import mu.KotlinLogging

class SharedPropertiesServiceImpl : SharedPropertiesService {

    private val _properties: MutableMap<String, MutableStateFlow<Any?>> = mutableMapOf()

    private val logger = KotlinLogging.logger {  }

    override fun getPropertyValue(id: String): Any? {

        return if (_properties.containsKey(id)) {
            _properties[id]?.value
        } else null
    }

    override suspend fun setPropertyValue(id: String, value: Any?) {

        if (_properties.containsKey(id)) {
            _properties[id]?.emit(value)
        } else {
            logger.debug { "Added new property: $id" }
            _properties[id] = MutableStateFlow(value)
        }
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T> observeProperty(id: String): StateFlow<T?> {

        var job: Job? = null

        if (!_properties.containsKey(id))
            job = CoroutineScope(Dispatchers.Default).launch {
                setPropertyValue(id, null)
            }

        job?.let {
            while (it.isActive) {
                Thread.sleep(1)
                logger.debug { "Waiting for $id initialisation job to finish" }
            }
        }

        return _properties[id]!! as StateFlow<T?>
    }

    override fun removeProperty(id: String) : Boolean {
        if (_properties.containsKey(id)) {
            _properties[id]?.let {
                logger.info { "property $id sub count: ${it.subscriptionCount.value}" }
                if (it.subscriptionCount.value >= 1) {
                    return false
                } else {
                    _properties.remove(id)
                    return true
                }
            }
        }
        return false
    }

    override fun clear() {
        _properties.clear()
    }
}