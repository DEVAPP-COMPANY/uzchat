package uz.devapp.uzchat.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import uz.devapp.uzchat.databinding.ActivitySignUpBinding
import uz.devapp.uzchat.screen.main.MainActivity
import uz.devapp.uzchat.utils.PrefUtils
import uz.devapp.uzchat.utils.showMessage

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.errorLiveData.observe(this) {
            showMessage(it)
        }

        viewModel.progressLiveData.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.cardViewRegistration.visibility = if (!it) View.VISIBLE else View.GONE
        }

        viewModel.authResponseData.observe(this) {
            PrefUtils.setToken(it.token)
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.cardViewRegistration.setOnClickListener {
            if (binding.edPassword.text.toString() != binding.edRePassword.text.toString()){
                showMessage("Please enter the correct password!")
                return@setOnClickListener
            }

            viewModel.registration(
                binding.edFullname.text.toString(),
                binding.edPhone.text.toString(),
                binding.edPassword.text.toString()
            )
        }
    }
}