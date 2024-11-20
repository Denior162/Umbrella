package com.denior.parasol.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.denior.parasol.ui.citySelection.CitySelectionViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var citySelectionViewModel: CitySelectionViewModel
    private lateinit var uvIndexViewModel: UVIndexViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        citySelectionViewModel = ViewModelProvider(this).get(CitySelectionViewModel::class.java)
        uvIndexViewModel = ViewModelProvider(this).get(UVIndexViewModel::class.java)

        // Наблюдение за состоянием UV индекса
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                uvIndexViewModel.indexUiState.collect { state ->
                    when (state) {
                        is IndexUiState.Loading -> {
                            // Показать индикатор загрузки
                        }

                        is IndexUiState.Success -> {
                            // Обновить UI с UV индексом
                        }

                        is IndexUiState.Error -> {
                            // Показать сообщение об ошибке
                        }
                    }
                }
            }
        }

        // Наблюдение за списком городов
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                citySelectionViewModel.citiesList.collect { cities ->
                    // Обновить UI со списком городов
                }
            }
        }
    }
}

