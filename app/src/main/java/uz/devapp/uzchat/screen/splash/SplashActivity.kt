package uz.devapp.uzchat.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.devapp.uzchat.databinding.ActivitySplashBinding
import uz.devapp.uzchat.screen.auth.LoginActivity
import uz.devapp.uzchat.screen.main.MainActivity
import uz.devapp.uzchat.utils.PrefUtils

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.img.postDelayed({
            val i = Intent(this, if (PrefUtils.getToken().isNotEmpty()) MainActivity::class.java else LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }, 2000)
    }
}