package com.ar.ui_practice.presentation.top_up

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
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
import com.ar.ui_practice.data.model.top_up.Offer
import com.ar.ui_practice.databinding.FragmentTopUpBinding
import com.ar.ui_practice.presentation.top_up.fragment.OfferListFragment
import java.nio.file.WatchEvent


class TopUpFragment : Fragment(), OfferListFragment.OfferListener {
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
        binding.etAmount.doOnTextChanged { s, _, _, _ ->
            if(s.isNullOrEmpty()){
                binding.btnConfirm.isEnabled = false
            }else {
                binding.btnConfirm.isEnabled = true
                binding.btnConfirm.setOnClickListener {
                    navigateToConfirm(binding.etAmount.text.toString().trimMargin())
                }
            }
        }
    }

    private fun initItems() {
        suggestionAdapter = SuggestedAmountAdapter {
            navigateToConfirm(it)
        }
    }

    private fun navigateToConfirm(it: String) {
        findNavController().navigate(TopUpFragmentDirections.actionTopUpFragmentToConfirmFragment(name = args.name, number = args.number, amount = it))
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
        binding.btnConfirm.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOfferClick(offer: Offer) {
        navigateToConfirm(offer.price)
    }
}