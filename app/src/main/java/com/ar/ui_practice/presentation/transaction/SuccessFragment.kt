package com.ar.ui_practice.presentation.transaction


import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ar.ui_practice.R
import com.ar.ui_practice.databinding.FragmentSuccessBinding
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SuccessFragment : Fragment() {

    private var _binding : FragmentSuccessBinding? = null
    private val binding get() = _binding!!
    private val args: SuccessFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private var isBalanceHidden = false
    @SuppressLint("SetTextI18n")
    private fun initListener() {
        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
        binding.copyTransactionId.setOnClickListener {
            copyTextToClipboard(binding.tvTransactionId.text.toString())
        }
        binding.ivToogleBalance.setOnClickListener {
            if (isBalanceHidden){
                binding.currentBalance.text = (400000 - (args.amount.toInt() + if(args.charge == "Free") 0 else 10)).toString()
                isBalanceHidden = false
            }else{
                binding.currentBalance.text = "****"
                isBalanceHidden = true
            }
        }
    }

    private fun copyTextToClipboard(text: String) {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = android.content.ClipData.newPlainText("text", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.contactName.text = args.name
        binding.contactNumber.text = args.number
        binding.transactionAmount.text = args.amount
        binding.tvCharge.text = args.charge
        binding.totalAmount.text = (args.amount.toInt() + if(args.charge == "Free") 0 else 10).toString()
        binding.currentBalance.text = (400000 - (args.amount.toInt() + if(args.charge == "Free") 0 else 10)).toString()
        setDate()
        binding.tvTransactionId.text = generateTransactionID()
    }

    private fun setDate() {
        val date = Date()
        val pattern = "hh:mm a\ndd MMM yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        binding.tvTimeDate.text =  simpleDateFormat.format(date)
    }

    private fun generateTransactionID(): String {
        val charPool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val random = SecureRandom()
        val stringBuilder = StringBuilder()

        repeat(21) {
            val index = random.nextInt(charPool.length)
            stringBuilder.append(charPool[index])
        }

        return stringBuilder.toString()
    }
}