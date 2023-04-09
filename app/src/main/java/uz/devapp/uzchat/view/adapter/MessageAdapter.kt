package uz.devapp.uzchat.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.devapp.uzchat.data.model.MessageModel
import uz.devapp.uzchat.data.model.MessageRole
import uz.devapp.uzchat.databinding.MessageItemLayoutBinding
import uz.devapp.uzchat.utils.PrefUtils

class MessageAdapter(val items: List<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: MessageItemLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            MessageItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.lyOwn.visibility =
            if (item.user.id == PrefUtils.getUser()!!.id) View.VISIBLE else View.GONE
        holder.binding.lyPartner.visibility =
            if (item.user.id != PrefUtils.getUser()!!.id) View.VISIBLE else View.GONE

        holder.binding.tvMessage.text = item.message
        holder.binding.tvPartnerMessage.text = item.message
        holder.binding.tvTime.text = item.time
        holder.binding.tvPartnerTime.text = item.time
    }
}