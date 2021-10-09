/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.recipeek.data

import javax.inject.Inject

class RecipeRepository @Inject constructor() {

    fun getRecipes() = recipes

    fun getRecipe(id: Int) = recipes.find { it.id == id }

    companion object {
        private val recipes = listOf(
            Recipe(
                id = 0,
                title = "Ballotine de dinde automnale",
                desc = "Une ballotine de poulet farcie à la pancetta poivrée, coulis de poivrons rouges et Ossau-Iraty.\n" +
                    "Sur une mousseline de pomme de terre et chanterelles roties au beurre",
                imageUrl = "https://images.unsplash.com/photo-1633796331241-4bb7e4658d56?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
                calories = 876,
                cookingTime = 90,
            ),
            Recipe(
                id = 1,
                title = "Le boeuf bourguignon",
                imageUrl = "https://images.unsplash.com/photo-1633799710991-e8a8cebea471?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
                calories = 1574,
                cookingTime = 270,
            ),
            Recipe(
                id = 2,
                title = "Risotto d'asperges sauvages",
                imageUrl = "https://images.unsplash.com/photo-1633800197170-6af1eecb2319?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
                calories = 1200,
                cookingTime = 45,
            ),
            Recipe(
                id = 3,
                title = "Pancakes et fraises",
                imageUrl = "https://images.unsplash.com/photo-1633800121138-f98d81e85ff9?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
            ),
            Recipe(
                id = 4,
                title = "Les Nuggets",
                imageUrl = "https://images.unsplash.com/photo-1633799897655-325beb35113c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
            ),
        )
    }
}
