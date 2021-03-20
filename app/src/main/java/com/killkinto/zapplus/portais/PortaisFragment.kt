package com.killkinto.zapplus.portais

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.killkinto.zapplus.data.model.Portal
import com.killkinto.zapplus.databinding.FragmentPortaisBinding

class PortaisFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPortaisBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            zapBtn.setOnClickListener { view -> navigate(Portal.ZAP, view) }
            vivaRealBtn.setOnClickListener { view -> navigate(Portal.VIVA_REAL, view) }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    private fun navigate(portal: String, view: View) {
        view.findNavController()
            .navigate(PortaisFragmentDirections.actionPortaisFragmentToOverviewFragment(portal))
    }
}