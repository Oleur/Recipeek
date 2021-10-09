package com.recipeek.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeek.data.Recipe
import com.recipeek.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    val recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipe = MutableStateFlow<Recipe?>(null)

    fun getPets() = viewModelScope.launch(Dispatchers.IO) {
        recipes.emit(repository.getRecipes())
    }

    fun getPet(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        recipe.emit(repository.getRecipe(id))
    }
}