package com.ar.ui_practice.presentation.top_up

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.top_up.OfferViewPagerAdapter
import com.ar.ui_practice.adapter.top_up.OffersAdapter
import com.ar.ui_practice.adapter.top_up.SuggestedAmountAdapter
import com.ar.ui_practice.data.demo.DemoData
import com.ar.ui_practice.databinding.FragmentTopUpBinding
import java.nio.file.WatchEvent


class TopUpFragment : Fragment() {
    private var _binding: FragmentTopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OffersAdapter
    private lateinit var suggestionAdapter: SuggestedAmountAdapter
    private lateinit var viewPager: ViewPager2


    private val offers = DemoData.OffersList

    private val args: TopUpFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItems()
        initView()
        initListener()
    }

    private fun initListener() {
        binding.actionBar.ivNavIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.etAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty()){
                    binding.btnConfirm.setImageResource(R.drawable.ic_action_btn)
                    binding.btnConfirm.setOnClickListener {

                    }
                }else {
                    binding.btnConfirm.setImageResource(R.drawable.ic_action_bth_active)
                    binding.btnConfirm.setOnClickListener {
                        Toast.makeText(requireContext(), "The entered amount is -> $s", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun initItems() {
        suggestionAdapter = SuggestedAmountAdapter {

        }
    }

    private fun initView() {
        binding.contactName.text = args.name
        binding.contactNumber.text = args.number
        binding.actionBar.tvNavTitle.text = args.title
        binding.offers.apply {
            adapter = OffersAdapter { position ->
                viewPager.setCurrentItem(position, true)
            }
            rvTitle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvTitle.adapter = adapter
            adapter.submitList(offers.map { it.category })

            val viewPageAdapter = OfferViewPagerAdapter(this@TopUpFragment, offers)

            viewPager = horizontalViewPager
            horizontalViewPager.adapter = viewPageAdapter

            horizontalViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    adapter.updateSelectedPosition(position)
                    rvTitle.scrollToPosition(position)
                }
            })
        }
        binding.rvSuggestedAmount.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = suggestionAdapter
        }
        suggestionAdapter.submitList(listOf("69","99","199","299","299","369","489","1000"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}