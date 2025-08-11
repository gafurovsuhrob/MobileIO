# Seros Auth App

Мобильное приложение на **Kotlin + Jetpack Compose**, реализующее функционал регистрации, авторизации и работы в офлайн-режиме с использованием **Clean Architecture**.

---

Перед запуском сделайте POST запрос через Postman, для того чтобы сервер проснулся, первый запрос займёт 2-2.5 минуты, таймер на засыпание 15 минут бездействия.

Endpoint: https://javaspring-backend.onrender.com/api/v1/auth/mobile/login
Body:
{
    "username":"admin",
    "password":"password"
}

## 📌 Основные функции

- Регистрация и вход через API (Ktor)
- Вход в офлайн-режиме при недоступности сервера (данные из Room)
- Валидация полей (логин, пароль, email)
- Сохранение токена в **TokenManager** (DataStore)
- Навигация с учётом состояния авторизации
- DI через **Koin**

---

## 🏗 Архитектура проекта

Проект построен на основе **Clean Architecture** и разделён на слои:

app/
├── data/ # Источники данных (API, Room, DataStore)
├── domain/ # UseCase-слой, бизнес-логика
├── presentation/ # UI-слой (Jetpack Compose, ViewModel)
└── di/ # Модули Koin


**Поток данных:**
1. UI генерирует события (Event) → ViewModel
2. ViewModel вызывает UseCase
3. UseCase обращается к репозиторию
4. Репозиторий решает — взять данные из API или локальной БД
5. Результат возвращается обратно в UI

---

## ⚙️ Технологии

- **Kotlin Coroutines / Flow**
- **Jetpack Compose**
- **Koin** — Dependency Injection
- **Ktor Client** — API
- **Room** — локальная БД
- **DataStore** — хранение токена
- **StateFlow** — управление состоянием экрана
- **JUnit / MockK / Mockito** — Unit-тесты

---

## 🚀 Запуск проекта

1. Клонировать репозиторий:
   [git clone https://github.com/username/seros-auth-app.git](https://github.com/gafurovsuhrob/MobileIO.git)
2. Открыть проект в Android Studio
3. Запустить сборку:
  ./gradlew assembleDebug
4. Для тестов:
  ./gradlew test

Если нужно что-то изменить в API для себя, вот репозиторий: https://github.com/gafurovsuhrob/JavaSpring.git 

---

🌐 Офлайн-режим
- При запуске приложения проверяется наличие сети
- Если интернет недоступен — вход возможен, если пользователь есть в локальной БД
- При успешном онлайн-входе данные пользователя сохраняются в Room и токен в DataStore

---
