package uz.devapp.uzchat.data.model.request

import uz.devapp.uzchat.utils.PrefUtils

data class AddChatRequest(
    val receiver_user_id: Int,
    val sender_user_id: Int = PrefUtils.getUser()!!.id
)