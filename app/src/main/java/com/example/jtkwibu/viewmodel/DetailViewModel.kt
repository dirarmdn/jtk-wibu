package com.example.jtkwibu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jtkwibu.data.AnimeEntity
import com.example.jtkwibu.data.AnimeRepository
import com.example.jtkwibu.data.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeDetail = MutableStateFlow<AnimeEntity?>(null)
    val animeDetail: StateFlow<AnimeEntity?> get() = _animeDetail

    fun getAnimeById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val anime = repository.getAnimeById(id)
            _animeDetail.value = anime
        }
    }
}
