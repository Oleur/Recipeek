package com.recipeek.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.recipeek.R
import com.recipeek.data.Recipe
import com.recipeek.ui.common.LinearGradientTransformation
import com.recipeek.ui.common.atom.SearchInput
import com.recipeek.ui.theme.AppColorsTheme
import com.recipeek.ui.theme.RecipeekTheme

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val context = LocalContext.current

    val viewModel = hiltViewModel<RecipesViewModel>()
    viewModel.getRecipes()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = context.getString(R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Black)
                    )
                },
                backgroundColor = AppColorsTheme.colors.mainColor,
                actions = {
                    IconButton(
                        onClick = {
                            // TODO
                        }
                    ) {
                        Icon(Icons.Filled.Settings, null)
                    }
                }
            )
        },
        backgroundColor = AppColorsTheme.colors.uiBackground,
        contentColor = AppColorsTheme.colors.uiBackground,
        content = {
            // A surface container using the 'background' color from the theme
            Surface(color = AppColorsTheme.colors.uiBackground) {
                HomeScreenList { petId ->
                    navController.navigate("recipe/${petId}")
                }
            }
        }
    )
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun HomeScreenList(onNavigateTo: (petId: Int) -> Unit) {
    val viewModel = hiltViewModel<RecipesViewModel>()
    val recipes = viewModel.recipes.collectAsState().value

    var query by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(start = 8.dp, top = 24.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchInput(
            text = query,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            onTextChanged = {
                query = it
                viewModel.findRecipes(it)
            },
            hint = "Search for recipes"
        )

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize(),
            content = {
                items(count = recipes.size) { position ->
                    HomeRecipeItem(
                        recipe = recipes[position],
                        onNavigateTo = onNavigateTo
                    )
                }
            }
        )
    }
}

@Composable
fun HomeRecipeItem(recipe: Recipe, onNavigateTo: (petId: Int) -> Unit) {
    Button(
        onClick = { onNavigateTo(recipe.id) },
        shape = MaterialTheme.shapes.medium,
        elevation = null,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = rememberImagePainter(
                    data = recipe.imageUrl,
                    builder = {
                        scale(Scale.FILL)
                        transformations(
                            LinearGradientTransformation(recipe.imageUrl ?: recipe.id.toString()),
                            RoundedCornersTransformation(LocalDensity.current.run { 8.dp.toPx() }),
                        )
                    },
                ),
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = recipe.title,
                style = MaterialTheme.typography.h4,
                color = AppColorsTheme.colors.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = "${recipe.calories} cal • ${recipe.cookingTime} min",
                style = MaterialTheme.typography.caption,
                color = AppColorsTheme.colors.textHint,
            )
        }
    }
}

@Preview
@Composable
fun RecipeCardPreview() {
    RecipeekTheme {
        val recipe = Recipe(
            id = 0,
            title = "Ballotine de dinde automnale",
            desc = "Une ballotine de poulet farcie à la pancetta poivrée, coulis de poivrons rouges et Ossau-Iraty.\n" +
                "Sur une mousseline de pomme de terre et chanterelles roties au beurre",
            imageUrl = "https://images.unsplash.com/photo-1633796331241-4bb7e4658d56?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1974&q=80",
            calories = 876,
            cookingTime = 90
        )
        HomeRecipeItem(recipe = recipe, onNavigateTo = {})
    }
}
