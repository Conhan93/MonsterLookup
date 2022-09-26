package Model.Service

import Model.Data.Monster.Monster
import Model.Service.SharedPropertiesService
import Model.Service.SharedPropertiesServiceImpl
import androidx.compose.runtime.getValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class SharedPropertiesServiceTest {

    private val service: SharedPropertiesService = SharedPropertiesServiceImpl()
    private val logger = KotlinLogging.logger {  }

    @BeforeEach
    fun before() {
        service.clear()
    }


    @Test
    fun `Should save property`() = runTest {

        val id = "foo"
        val value = Monster(desc = listOf("foo", "bar"))

        service.setPropertyValue(id, value)

        val actual = service.getPropertyValue(id)

        assertEquals(value, actual)
    }

    @Test
    fun `Property should be observable`() = runTest {

        val id = "foo"
        val value = Monster(desc = listOf("foo", "bar"))

        val actualSubscription = service.observeProperty<Monster>(id)
        var actual = actualSubscription.value

        assertNull(actual)

        service.setPropertyValue(id, value)

        actual = actualSubscription.value

        assertEquals(value, actual)
    }

    @Test
    fun `Should remove property`() = runTest {

        val id = "foo"
        val value = Monster(desc = listOf("foo", "bar"))

        service.setPropertyValue(id, value)

        val actualFlow: StateFlow<Monster?>? = service.observeProperty<Monster>(id)

        assertEquals(value, service.getPropertyValue(id))

        val job = launch { actualFlow
            ?.onEach {
                logger.info { "value $it" }
                assertEquals(value, it)
            }
            ?.collect()
        }

        delay(1)
        assertEquals(value, actualFlow?.value)

        assertEquals(false, service.removeProperty(id))

        assertEquals(value, service.getPropertyValue(id))
        assertEquals(value, actualFlow?.value)

        job.cancel()
        delay(1)

        assertEquals(true, service.removeProperty(id))
        assertNull(service.getPropertyValue(id))
    }
}