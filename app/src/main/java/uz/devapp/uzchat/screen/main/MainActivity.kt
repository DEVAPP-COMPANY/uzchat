package uz.devapp.uzchat.screen.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import uz.devapp.uzchat.databinding.ActivityMainBinding
import uz.devapp.uzchat.screen.main.addchat.AddChatCallback
import uz.devapp.uzchat.screen.main.addchat.AddChatFragment
import uz.devapp.uzchat.utils.PrefUtils
import uz.devapp.uzchat.utils.showMessage
import uz.devapp.uzchat.view.adapter.ChatListAdapter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AddChatCallback {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.errorLiveData.observe(this) {
            showMessage(it)
        }

        viewModel.chatListLiveData.observe(this) {
            binding.recycler.adapter = ChatListAdapter(it)
        }

        binding.imgAdd.setOnClickListener {
            val fragment = AddChatFragment(this)
            fragment.show(supportFragmentManager, fragment.tag)
        }

        binding.recycler.layoutManager = LinearLayoutManager(this)

        loadData()
        initSocket()
    }

    fun loadData() {
        viewModel.getUser()
        viewModel.getChatList()
    }

    override fun updateChatList() {
        viewModel.getChatList()
    }

    fun initSocket() {
        socket.on(Socket.EVENT_CONNECT) {
            Log.d("JW", "Connected!")
            socket.emit(
                "authorization", PrefUtils.getToken()
            )
        }
        socket.on(Socket.EVENT_CONNECT_ERROR) {
            Log.d("JW", "Connect error ${it[0]}")
        }
        socket.on(Socket.EVENT_DISCONNECT) {
            Log.d("JW", "Connect error ${it[0]}")
        }

        socket.connect()
    }
}