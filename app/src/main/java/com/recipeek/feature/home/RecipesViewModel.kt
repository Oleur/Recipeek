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

    fun getRecipes() = viewModelScope.launch(Dispatchers.IO) {
        recipes.emit(repository.getRecipes())
    }

    fun findRecipes(query: String) = viewModelScope.launch(Dispatchers.IO) {
        if (query.isEmpty()) {
            recipes.emit(repository.getRecipes())
            return@launch
        }

        if (query.isNotEmpty() && query.length > 2) {
            recipes.emit(
                repository.getRecipes().filter { recipe ->
                    recipe.title.contains(query, true)
                }
            )
        }
    }

    fun getRecipe(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        recipe.emit(repository.getRecipe(id))
    }
}