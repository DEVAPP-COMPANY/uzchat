package uz.devapp.uzchat.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.devapp.uzchat.data.model.ChatModel
import uz.devapp.uzchat.databinding.ChatItemLayoutBinding
import uz.devapp.uzchat.screen.main.message.MessageActivity
import uz.devapp.uzchat.utils.Constants

class ChatListAdapter(val items: List<ChatModel>): RecyclerView.Adapter<ChatListAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: ChatItemLayoutBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ChatItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.tvFullname.text = item.user.fullname
        holder.binding.tvTime.text = item.time

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, MessageActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, item)
            it.context.startActivity(intent)
        }
    }
}