package com.killkinto.zapplus.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.killkinto.zapplus.R
import com.killkinto.zapplus.ZapApplication
import com.killkinto.zapplus.data.model.BusinessType
import com.killkinto.zapplus.data.model.Property
import com.killkinto.zapplus.databinding.FragmentPropertyDetailBinding
import com.killkinto.zapplus.setPropertyImage
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class PropertyDetailFragment : Fragment() {

    private val viewModel by viewModels<PropertyDetailViewModel> {
        PropertyDetailViweModelFactory(
            (requireContext().applicationContext as ZapApplication)
                .propertyRepositoy
        )
    }

    private lateinit var images: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPropertyDetailBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

        val propertyId = PropertyDetailFragmentArgs.fromBundle(requireArguments()).propertyId
        viewModel.loadProperty(propertyId)

        setupListeners(binding.imagesView)

        return binding.root
    }

    private fun setupListeners(imagesView: CarouselView) {
        val imageListener =
            ImageListener { position, imageView -> imageView.setPropertyImage(images[position]) }

        viewModel.property.observe(viewLifecycleOwner, {
            if (it != null) {
                images = it.images
                popularCarrosselView(imagesView, imageListener)
                setupToolbar(it)
            }
        })
    }

    private fun setupToolbar(property: Property) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(
            when (property.infos.businessType) {
                BusinessType.SALE -> R.string.for_sale_detail_title
                BusinessType.RENTAL -> R.string.for_rental_detail_title
            }
        )
    }

    private fun popularCarrosselView(imagesView: CarouselView, imageListener: ImageListener) {
        imagesView.setImageListener(imageListener)
        imagesView.pageCount = images.size
    }
}