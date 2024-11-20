package com.example.speakfreely.app.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.speakfreely.app.core.data.TranslationFavorite
import com.example.speakfreely.app.core.data.TranslationHistory
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favorites = viewModel.getFavorites().collectAsState(initial = emptyList())

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(title = { Text("Favorite")})

        LazyColumn (modifier = Modifier.padding(horizontal = 16.dp)) {
            items(favorites.value) { favorite ->
              FavoriteItem(favorite)
            }
        }
    }
}

@Composable
fun FavoriteItem(favorite: TranslationFavorite) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Source: ${favorite.sourceText}")
        Text(text = "Translation: ${favorite.translatedText}")
    }

}