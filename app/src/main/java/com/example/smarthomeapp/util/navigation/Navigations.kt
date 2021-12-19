package com.example.smarthomeapp.util.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smarthomeapp.R

fun NavController.clearBackstack() {
    currentDestination?.id?.let {
        if (it != graph.startDestination) {
            while (true) {
                if (!navigateUp()) {
                    break
                }
            }
        }
    }
}

fun Fragment.findNavController(@IdRes container: Int = R.id.nav_host_fragment): NavController {
    return NavHostFragment.findNavController(this)
}