package com.ar.ui_practice.presentation.transaction


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ar.ui_practice.R
import com.ar.ui_practice.databinding.FragmentConfirmBinding
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

    private fun initListener() {
        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
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
        binding.tvTransactionId.text = generateTransactionID(21)
    }

    private fun setDate() {
        val date = Date()
        val pattern = "hh:mm a\ndd MMM yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        binding.tvTimeDate.text =  simpleDateFormat.format(date)
    }

    fun generateTransactionID(length: Int): String {
        val charPool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val random = SecureRandom()
        val stringBuilder = StringBuilder()

        repeat(length) {
            val index = random.nextInt(charPool.length)
            stringBuilder.append(charPool[index])
        }

        return stringBuilder.toString()
    }
}