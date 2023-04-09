package uz.devapp.uzchat.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.devapp.uzchat.data.api.Api
import uz.devapp.uzchat.data.model.UserModel
import uz.devapp.uzchat.data.model.request.AddChatRequest
import uz.devapp.uzchat.data.model.request.SearchUserRequest
import uz.devapp.uzchat.data.model.sealed.DataResult

class MainRepository(private val api: Api) : BaseRepository() {

    suspend fun getUser() = withContext(Dispatchers.IO) {
        try {
            val response = api.getUser()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun searchFriend(phone: String): DataResult<UserModel?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.searchFriend(SearchUserRequest(phone))
                return@withContext wrapResponse(response)
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.localizedMessage)
            }
        }
    }


    suspend fun addChat(receiverId: Int): DataResult<Any?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.addChat(AddChatRequest(receiver_user_id = receiverId))
                return@withContext wrapResponse(response)
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.localizedMessage)
            }
        }
    }

    suspend fun getChatList() = withContext(Dispatchers.IO) {
        try {
            val response = api.getChatList()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun getChatMessages(chatId: Int) = withContext(Dispatchers.IO) {
        try {
            val response = api.getChatMessages(chatId)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }
}