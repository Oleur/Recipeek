package com.recipeek.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.recipeek.R
import com.recipeek.data.Recipe
import com.recipeek.ui.common.LinearGradientTransformation
import com.recipeek.ui.theme.AppColorsTheme

@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = context.getString(R.string.app_name),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold)
                    )
                },
                backgroundColor = AppColorsTheme.colors.mainColor,
                actions = {
                    IconButton(
                        onClick = {
                            // TODO
                        }
                    ) {
                        Icon(Icons.Filled.Phone, null)
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

@ExperimentalFoundationApi
@Composable
fun HomeScreenList(onNavigateTo: (petId: Int) -> Unit) {
    val viewModel = hiltViewModel<RecipesViewModel>()
    val recipes by viewModel.recipes.collectAsState()

    viewModel.getPets()

    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.padding(all = 8.dp),
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
