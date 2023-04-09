package uz.devapp.uzchat.data.model.request

data class SendMessageRequest(
    val token: String,
    val chat_id: Int,
    val message: String,
)