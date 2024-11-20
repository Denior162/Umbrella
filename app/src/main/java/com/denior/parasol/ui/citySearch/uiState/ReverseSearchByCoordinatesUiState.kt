package com.denior.parasol.ui.citySearch.uiState

import android.util.Log
import com.denior.parasol.network.model.City

sealed class ReverseSearchByCoordinatesUiState {
    data object Loading : ReverseSearchByCoordinatesUiState() {
        init {
            Log.d("ReverseSearchUiState", "Loading state initiated")
        }
    }

    data class Success(val result: City) : ReverseSearchByCoordinatesUiState() {
        init {
            Log.d("ReverseSearchUiState", "Success state with result: $result")
        }
    }

    data object Error : ReverseSearchByCoordinatesUiState() {
        init {
            Log.e("ReverseSearchUiState", "Error state encountered")
        }
    }
}