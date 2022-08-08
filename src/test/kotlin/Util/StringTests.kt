package Util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StringTests {

    @Test
    fun `Should format capitalised simple name`() {
        val expected = "adult-black-dragon"
        val testString = "Adult black dragon"

        val formatted = testString.formatSearchName()

        assertEquals<String>(expected, formatted)
    }
}