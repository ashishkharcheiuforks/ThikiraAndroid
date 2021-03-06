package com.dsm.restaurant.presentation.ui.restaurant

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dsm.restaurant.R
import com.dsm.restaurant.databinding.FragmentRestaurantBinding
import com.dsm.restaurant.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantFragment : BaseFragment<FragmentRestaurantBinding>() {
    override val layoutResId: Int = R.layout.fragment_restaurant

    private val viewModel: RestaurantViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigate()

        binding.viewModel = viewModel
    }

    private fun setupNavigate() {
        iv_restaurant_setting.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantFragment_to_settingActivity)
        }
    }
}