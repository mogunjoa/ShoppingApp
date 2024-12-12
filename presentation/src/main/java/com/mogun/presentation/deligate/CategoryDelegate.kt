package com.mogun.presentation.deligate

import androidx.navigation.NavHostController
import com.mogun.domain.model.Category

interface CategoryDelegate {
    fun openCategory(navHostController: NavHostController, category: Category)
}