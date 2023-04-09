package uz.devapp.uzchat.screen.main.message

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import uz.devapp.uzchat.data.model.ChatModel
import uz.devapp.uzchat.data.model.request.SendMessageRequest
import uz.devapp.uzchat.databinding.ActivityMessageBinding
import uz.devapp.uzchat.screen.main.MainViewModel
import uz.devapp.uzchat.utils.Constants
import uz.devapp.uzchat.utils.PrefUtils
import uz.devapp.uzchat.utils.showMessage
import uz.devapp.uzchat.view.adapter.MessageAdapter
import javax.inject.Inject

@AndroidEntryPoint
class MessageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMessageBinding
    private val viewModel: MainViewModel by viewModels()
    lateinit var chat: ChatModel
    @Inject
    lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chat = intent.getSerializableExtra(Constants.EXTRA_DATA) as ChatModel

        viewModel.errorLiveData.observe(this) {
            showMessage(it)
        }

        viewModel.chatMessagesLiveData.observe(this){
            binding.recycler.adapter = MessageAdapter(it.messages)
            binding.recycler.postDelayed({
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
            }, 50)
        }

        binding.imgBack.setOnClickListener { finish() }
        binding.tvFullname.text = chat.user.fullname
        binding.recycler.layoutManager = LinearLayoutManager(this)

        binding.imgSend.setOnClickListener {
            socket.emit("send", Gson().toJson(SendMessageRequest(PrefUtils.getToken(), chat.id, binding.edMessage.text.toString())))
            binding.edMessage.setText("")
            viewModel.getChatMessageList(chat.id)
        }

        viewModel.getChatMessageList(chat.id)
        initSocket()
    }

    fun initSocket(){
        socket.on("new_message"){
            viewModel.getChatMessageList(chat.id)
        }
    }
}