package uz.devapp.uzchat.view.model

data class EventModel<T>(
    val command: Int,
    val data: T
)
