package uz.devapp.uzchat.data.model

import com.google.gson.annotations.SerializedName

enum class MessageRole(value: String){
    @SerializedName("sender")
    SENDER("sender"),
    @SerializedName("receiver")
    RECEIVER("receiver")
}
data class MessageModel(
    val role: MessageRole,
    val message: String,
    val time: String,
    val user: ChatUserModel
)