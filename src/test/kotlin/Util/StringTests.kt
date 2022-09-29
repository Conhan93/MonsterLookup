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

    @Test
    fun `Should return readable from formatted`() {
        val expected = "Adult black dragon"
        val testString = "adult-black-dragon"

        val actual = testString.formattedToReadable()

        assertEquals(expected,actual)
    }

    @Test
    fun `Should return capitalised version of string`() {
        val foo = "foo"

        assertEquals("Foo", foo.capitalise())
    }
}