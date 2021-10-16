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

data class Recipe(
    val id: Int,
    val title: String,
    val desc: String = "",
    val imageUrl: String? = null,
    val calories: Int = 0,
    val cookingTime: Int = 0, // In minutes
    val service: Int = 1,
    val ingredients: List<Ingredient> = listOf(),
    val steps: List<Step> = listOf(),
)

data class Step(
    val position: Int,
    val desc: String,
    val prepTime: Int? = null
)

data class Ingredient(
    val id: Int,
    val name: String,
    val desc: String? = null,
    val quantity: Int = 0,
    val quantityType: String? = null,
    val iconRes: Int = 0
)
