package com.seros.domain.validator

import com.seros.domain.validator.LoginValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LoginValidatorTest {

    @Test
    fun `validateUsername returns error when empty`() {
        val result = LoginValidator.validateUsername("")
        assertEquals("Поле обязательно", result)
    }

    @Test
    fun `validateUsername returns error when less than 3 chars`() {
        val result = LoginValidator.validateUsername("ab")
        assertEquals("Минимум 3 символа", result)
    }

    @Test
    fun `validateUsername returns null when valid`() {
        val result = LoginValidator.validateUsername("validUser")
        assertNull(result)
    }

    @Test
    fun `validatePassword returns error when empty`() {
        val result = LoginValidator.validatePassword("")
        assertEquals("Пароль обязателен", result)
    }

    @Test
    fun `validatePassword returns null when valid`() {
        val result = LoginValidator.validatePassword("123456")
        assertNull(result)
    }
}
