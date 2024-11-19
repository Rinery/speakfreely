package com.example.speakfreely.app.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speakfreely.app.core.domain.LanguageCode
import com.example.speakfreely.app.core.domain.TranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val translationUseCase: TranslationUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(TranslationUiState())
    val uiState: StateFlow<TranslationUiState> = _uiState

    fun updateInputText(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun clearInputText() {
        _uiState.update { it.copy(inputText = "") }
    }

    fun swapLanguages() {
        _uiState.update {
            it.copy(
                sourceLang = it.targetLang,
                targetLang = it.sourceLang
            )
        }
    }

    fun translateText() {
        viewModelScope.launch {
            val result = translationUseCase.translate(
                sl = LanguageCode.ENGLISH,
                dl = LanguageCode.RUSSIAN,
                text = _uiState.value.inputText,
            )

            // обновляем результат вывода, если перевода нет (null) - выводим то значение которое ввели
            _uiState.update { it.copy(translatedText = result.translations.possibleTranslations.firstOrNull()
                ?: _uiState.value.inputText) }
        }
    }
}

data class TranslationUiState(
    val sourceLang: String = "English",
    val targetLang: String = "Russian",
    val inputText: String = "",
    val translatedText: String? = null,
)