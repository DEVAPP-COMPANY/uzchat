package uz.devapp.uzchat.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast

fun Context.showMessage(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG)
        .show()
}