package com.example.pawpal.screens.home.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.R
import com.example.pawpal.databinding.FragmentDashboardBinding
import com.example.pawpal.entity.Banner
import com.example.pawpal.utils.setImage

class DashboardFragment : Fragment() {
    private val viewModel by viewModels<DashboardViewModel>()

    private lateinit var toyAdapter: DashboardAdapter
    private lateinit var foodAdapter: DashboardAdapter
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toyAdapter = DashboardAdapter()
        foodAdapter = DashboardAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvToys.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvToys.adapter = toyAdapter
            rvFood.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvFood.adapter = foodAdapter
        }
        with(viewModel) {
            processLD.observe(viewLifecycleOwner) { inProgress ->
                with(binding) {
                    groupDashboard.isVisible = !inProgress
                    groupBanner.isVisible = !inProgress
                    progressIndicator.isVisible = inProgress
                }
            }
            toysLD.observe(viewLifecycleOwner) { toyAdapter.updateItems(it) }
            foodLD.observe(viewLifecycleOwner) { foodAdapter.updateItems(it) }
            bannerLD.observe(viewLifecycleOwner) { fillBanner(it) }
        }
    }

    private fun fillBanner(banner: Banner) {
        with(binding) {
            tvBannerDescription.text = banner.description
            tvBannerDescriptionPercent.text = getString(R.string.percents, banner.discount)
            tvBannerTitle.text = banner.title
            ivBanner.setImage(banner.image)
            ivBanner.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(banner.link)
                    )
                )
            }
        }
    }
}