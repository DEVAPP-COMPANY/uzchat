package uz.devapp.uzchat.data.model

data class ChatMessagesModel(
    val id: Int,
    val receiver_user: ChatUserModel,
    val messages: List<MessageModel>,
) : java.io.Serializable
