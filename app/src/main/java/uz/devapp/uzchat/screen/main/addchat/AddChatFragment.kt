package uz.devapp.uzchat.screen.main.addchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.devapp.uzchat.databinding.FragmentAddChatBinding
import uz.devapp.uzchat.screen.main.MainViewModel
import uz.devapp.uzchat.utils.showMessage

interface AddChatCallback {
    fun updateChatList()
}

class AddChatFragment(val callback: AddChatCallback) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddChatBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(callback: AddChatCallback) =
            AddChatFragment(callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorLiveData.observe(this) {
            requireActivity().showMessage(it)
            binding.cardViewFriend.visibility = View.GONE
        }

        viewModel.progressLiveData.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.cardViewSearch.visibility = if (!it) View.VISIBLE else View.GONE
        }

        viewModel.searchFriendLiveData.observe(this) {
            binding.cardViewFriend.visibility = View.VISIBLE
            binding.tvFullname.text = it.fullname
            binding.tvPhone.text = it.phone
        }

        viewModel.addFriendLiveData.observe(this) {
            callback.updateChatList()
            dismiss()
        }

        binding.cardViewSearch.setOnClickListener {
            viewModel.searchFriend(binding.edPhone.text.toString())
        }

        binding.imgAdd.setOnClickListener {
            viewModel.addFriend(viewModel.searchFriendLiveData.value?.id ?: 0)
        }
    }
}