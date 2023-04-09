package uz.devapp.uzchat.data.model.request

data class RegistrationRequest(
    val phone: String,
    val password: String,
    val fullname: String,
)
