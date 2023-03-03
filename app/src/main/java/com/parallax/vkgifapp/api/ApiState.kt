package com.parallax.vkgifapp.api

/**
 * Класс, обьединющий в себе классы состояний запроса апи
 */
sealed class ApiState {
    object Loading : ApiState()  //загрузка
    class Failure(val e: Throwable) : ApiState()  //не успешно
    class Success(val data: Any) : ApiState()  //успешно
    object Empty : ApiState()
}