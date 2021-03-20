package com.killkinto.zapplus.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.killkinto.zapplus.R
import com.killkinto.zapplus.ZapApplication
import com.killkinto.zapplus.data.model.Portal
import com.killkinto.zapplus.databinding.FragmentOverviewBinding
import com.killkinto.zapplus.util.isInternetAvailable

class OverviewFragment : Fragment() {

    private val viewModel by viewModels<OverviewViewModel> {
        OverviewViweModelFactory(
            (requireContext().applicationContext as ZapApplication)
                .propertyRepositoy
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
            propertyList.adapter = PropertyListAdapter(PropertyListAdapter.OnClickListener {
                viewModel.displayPropertyDetails(it)
            })
        }

        val portal = OverviewFragmentArgs.fromBundle(requireArguments()).portalSelected
        viewModel.loadProperties(portal, isInternetAvailable(requireContext()))

        setupNavigation()
        setupToolbar(portal)

        return binding.root
    }

    private fun setupNavigation() {
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, {
            if (it != null) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToPropertyDetailFragment(it.id)
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })
    }

    private fun setupToolbar(portal: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(
                R.string.portal_title, when (portal) {
                    Portal.ZAP -> getString(R.string.portal_zap)
                    Portal.VIVA_REAL -> getString(R.string.portal_viva_real)
                    else -> ""
                }
            )
    }
}