package com.dsm.restaurant.presentation.ui.menu.registration

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dsm.mediapicker.MediaPicker
import com.dsm.restaurant.R
import com.dsm.restaurant.databinding.FragmentMenuRegistration1Binding
import com.dsm.restaurant.presentation.model.MenuCategoryModel
import com.dsm.restaurant.presentation.ui.base.BaseFragment
import com.dsm.restaurant.presentation.util.BusProvider
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragment_menu_registration1.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuRegistration1Fragment : BaseFragment<FragmentMenuRegistration1Binding>() {

    companion object {
        private const val IMAGE_CODE = 100
    }

    override val layoutResId: Int = R.layout.fragment_menu_registration1

    private val viewModel: MenuRegistrationViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupImagePicker()
        setupNavigate()

        binding.viewModel = viewModel
    }

    private fun setupImagePicker() =
        iv_menu_registration_menu.setOnClickListener {
            MediaPicker.createImage(this)
                .single()
                .theme(R.style.AppTheme)
                .toolbarBackgroundColor(R.color.colorPrimaryLight)
                .toolbarTextColor(R.color.colorPickerWhite)
                .toolbarTitle(getString(R.string.select_image))
                .start(IMAGE_CODE)
        }

    private fun setupNavigate() {
        tb_menu_registration1.setNavigationOnClickListener { activity?.finish() }

        btn_menu_registration1_next.setOnClickListener {
            findNavController().navigate(R.id.action_menuRegistration1Fragment_to_menuRegistration2Fragment)
        }

        btn_menu_registration1_menu_category.setOnClickListener {
            findNavController().navigate(R.id.action_menuRegistration1Fragment_to_menuCategoryListFragment)
        }
    }

    @Subscribe
    fun subscribeMenuCategory(menuCategoryModel: MenuCategoryModel) {
        viewModel.setMenuCategory(menuCategoryModel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK) {
            val imagePath = data?.getStringArrayListExtra("result")?.get(0) ?: ""
            viewModel.uploadImage(imagePath)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BusProvider.getInstance().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BusProvider.getInstance().unregister(this)
    }
}
