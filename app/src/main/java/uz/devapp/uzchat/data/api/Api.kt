package uz.devapp.uzchat.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.devapp.uzchat.data.model.ChatMessagesModel
import uz.devapp.uzchat.data.model.ChatModel
import uz.devapp.uzchat.data.model.UserModel
import uz.devapp.uzchat.data.model.request.AddChatRequest
import uz.devapp.uzchat.data.model.request.LoginRequest
import uz.devapp.uzchat.data.model.request.RegistrationRequest
import uz.devapp.uzchat.data.model.request.SearchUserRequest
import uz.devapp.uzchat.data.model.response.BaseResponse
import uz.devapp.uzchat.data.model.response.LoginResponse

interface Api {
    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): Response<BaseResponse<LoginResponse?>>

    @POST("api/registration")
    suspend fun registration(
        @Body request: RegistrationRequest,
    ): Response<BaseResponse<LoginResponse?>>

    @GET("api/user")
    suspend fun getUser(
    ): Response<BaseResponse<UserModel?>>

    @POST("api/findbyphone")
    suspend fun searchFriend(
        @Body request: SearchUserRequest,
    ): Response<BaseResponse<UserModel?>>

    @POST("api/add/chat")
    suspend fun addChat(
        @Body request: AddChatRequest,
    ): Response<BaseResponse<Any?>>

    @GET("api/chat/list")
    suspend fun getChatList(
    ): Response<BaseResponse<List<ChatModel>?>>

    @GET("/api/chat/{chat_id}/list")
    suspend fun getChatMessages(
        @Path("chat_id") chatId: Int,
    ): Response<BaseResponse<ChatMessagesModel?>>

}