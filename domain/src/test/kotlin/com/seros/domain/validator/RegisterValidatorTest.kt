package com.seros.domain.validator


import com.seros.domain.validator.RPValidator
import com.seros.domain.validator.RegisterValidator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RegisterValidatorTest {

    @Test
    fun `username empty`() {
        val result = RegisterValidator.validateUsername("")
        assertEquals("Поле обязательно", result)
    }

    @Test
    fun `username short`() {
        val result = RegisterValidator.validateUsername("ab")
        assertEquals("Минимум 3 символа", result)
    }

    @Test
    fun `username valid`() {
        val result = RegisterValidator.validateUsername("validUser")
        assertNull(result)
    }

    @Test
    fun `name empty`() {
        val result = RegisterValidator.validateName("")
        assertEquals("Поле обязательно", result)
    }

    @Test
    fun `name short`() {
        val result = RegisterValidator.validateName("ab")
        assertEquals("Минимум 3 символа", result)
    }

    @Test
    fun `name valid`() {
        val result = RegisterValidator.validateName("validName")
        assertNull(result)
    }

    @Test
    fun `email empty`() {
        val result = RegisterValidator.validateEmail("")
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
        val result = RegisterValidator.validateEmail("test@example.com")
        assertNull(result)
    }

    @Test
    fun `phone empty`() {
        val result = RegisterValidator.validatePhone("")
        assertEquals("Поле обязательно", result)
    }

    @Test
    fun `phone Invalid format`() {
        val result = RegisterValidator.validatePhone("1234567890")
        assertEquals("Неверный формат телефона", result)
    }

    @Test
    fun `phone valid`() {
        val result = RegisterValidator.validatePhone("+992123456789")
        assertNull(result)
    }

    @Test
    fun `date empty`() {
        val result = RegisterValidator.validateDateOfBirth("")
        assertEquals("Поле обязательно", result)
    }

    @Test
    fun `date invalid`() {
        val result = RegisterValidator.validateDateOfBirth("01/01/200")
        assertEquals("Неверный формат даты", result)
    }

    @Test
    fun `date valid`() {
        val result = RegisterValidator.validateDateOfBirth("01/01/2000")
        assertNull(result)
    }

    @Test
    fun `password empty`() {
        val result = RegisterValidator.validatePassword("")
        assertEquals("Поле обязательно", result)
    }

    @Test
    fun `password short`() {
        val result = RegisterValidator.validatePassword("123")
        assertEquals("Минимум 6 символов", result)
    }

    @Test
    fun `password valid`() {
        val result = RegisterValidator.validatePassword("123456")
        assertNull(result)
    }
}
