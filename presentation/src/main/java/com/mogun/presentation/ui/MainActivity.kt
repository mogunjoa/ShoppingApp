package com.mogun.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mogun.presentation.ui.theme.ShoppingAppTheme
import com.mogun.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShoppingAppTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen()
                }
            }
        }
        viewModel.updateColumnCount(getColumnCount())
    }

    private fun getColumnCount(): Int {
        return getDisplayWidthDp().toInt() / DEFAULT_COLUMN_SIZE
    }

    private fun getDisplayWidthDp(): Float {
        return resources.displayMetrics.run {
            widthPixels / density
        }
    }

    companion object {
        private const val DEFAULT_COLUMN_SIZE = 160
    }
}