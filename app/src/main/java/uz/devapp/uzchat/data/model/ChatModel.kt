package uz.devapp.uzchat.data.model

data class ChatUserModel(
    val id: Int,
    val fullname: String,
    val phone: String,
): java.io.Serializable

data class ChatModel(
    val id: Int,
    val user: ChatUserModel,
    val time: String,
) : java.io.Serializable
