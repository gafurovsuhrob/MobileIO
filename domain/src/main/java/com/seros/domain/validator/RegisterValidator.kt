package com.seros.domain.validator

object RegisterValidator {
    fun validateUsername(username: String): String? {
        if (username.isBlank()) return "Field is required"
        if (username.length < 3) return "Minimum 3 characters"
        return null
    }

    fun validateName(name: String): String? {
        if (name.isBlank()) return "Field is required"
        if (name.length < 3) return "Minimum 3 characters"
        return null
    }

    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Field is required"
        val emailRegex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""".toRegex()
        if (!emailRegex.matches(email)) return "Wrong email format"
        return null
    }

    fun validatePhone(phone: String): String? {
        if (phone.isBlank()) return "Field is required"
        val phoneRegex = """^\+992\d{9}$""".toRegex()
        if (!phoneRegex.matches(phone)) return "Wrong phone format"
        return null
    }


    fun validateDateOfBirth(date: String): String? {
        if (date.isBlank()) return "Field is required"
        return null
    }

    fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Password is required"
        if (password.length < 6) return "Minimum 6 characters"
        return null
    }
}