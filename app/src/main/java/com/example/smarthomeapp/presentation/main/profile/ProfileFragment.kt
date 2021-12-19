package com.example.smarthomeapp.presentation.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceFragmentCompat
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmFragment
import com.example.smarthomeapp.presentation.main.profile.contract.ProfileContract
import com.example.smarthomeapp.presentation.main.profile.contract.ProfileViewModel

class ProfileFragment : MvvmFragment<ProfileContract.Scene, ProfileContract.ViewModel>(),
    ProfileContract.Scene {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            parentFragmentManager.beginTransaction().replace(R.id.settings, SettingsFragment())
                .commit()
        }
    }

    override fun getViewModelClass(): Class<out ViewModel> = ProfileViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

    companion object {
        fun newInstance() = ProfileFragment()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}