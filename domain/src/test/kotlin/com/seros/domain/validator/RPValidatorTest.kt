package com.seros.domain.validator

import com.seros.domain.validator.RPValidator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RPValidatorTest {

    @Test
    fun `email empty`() {
        val result = RPValidator.validateEmail("")
        assertEquals("Email обязателен", result)
    }

    @Test
    fun `email Invalid format`() {
        val result = RPValidator.validateEmail("wrong-email@.com")
        assertEquals("Неверный формат email", result)
    }

    @Test
    fun `email Invalid format 2`() {
        val result = RPValidator.validateEmail("wrong-email@gmail")
        assertEquals("Неверный формат email", result)
    }

    @Test
    fun `email valid`() {
        val result = RPValidator.validateEmail("test@example.com")
        assertNull(result)
    }
}