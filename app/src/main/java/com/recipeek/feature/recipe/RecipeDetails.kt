package com.recipeek.feature.recipe

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.recipeek.R
import com.recipeek.data.Ingredient
import com.recipeek.data.Recipe
import com.recipeek.data.Step
import com.recipeek.feature.home.RecipesViewModel
import com.recipeek.ui.theme.AppColorsTheme
import com.recipeek.ui.theme.RecipeekShapes

@ExperimentalAnimationApi
@Composable
fun RecipeDetailsScreen(recipeId: Int) {
    val density = LocalDensity.current
    val viewModel = hiltViewModel<RecipesViewModel>()

    viewModel.getRecipe(recipeId)

    val recipe = viewModel.recipe.collectAsState().value
    checkNotNull(recipe) { return }

    val scrollState = rememberScrollState()
    val imageCornerSize = density.run { 32.dp.toPx() }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(scrollState)
    ) {
        val (backdrop, recipeCard, spacer, ingredientsRef, stepsRef) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.imageUrl)
                    .scale(Scale.FILL)
                    .crossfade(true)
                    .transformations(
                        RoundedCornersTransformation(
                            bottomLeft = imageCornerSize,
                            bottomRight = imageCornerSize,
                        )
                    ).build()
            ),
            contentScale = ContentScale.Crop,
            contentDescription = "Recipe image",
            modifier = Modifier
                .constrainAs(backdrop) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .height(350.dp)
        )

        RecipeCard(
            recipe = recipe,
            modifier = Modifier.constrainAs(recipeCard) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(backdrop.bottom)
                bottom.linkTo(backdrop.bottom)
            }
        )

        Spacer(
            modifier = Modifier
                .size(16.dp)
                .constrainAs(spacer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(recipeCard.bottom)
                    bottom.linkTo(ingredientsRef.top)
                }
        )

        if (recipe.ingredients.isNotEmpty()) {
            IngredientList(
                ingredients = recipe.ingredients,
                modifier = Modifier
                    .constrainAs(ingredientsRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(spacer.bottom)
                        bottom.linkTo(stepsRef.top)
                    }
                    .padding(start = 24.dp, end = 24.dp)
            )
        }

        if (recipe.steps.isNotEmpty()) {
            CookingSteps(
                steps = recipe.steps,
                modifier = Modifier
                    .constrainAs(stepsRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(ingredientsRef.bottom)
                    }
                    .padding(start = 24.dp, end = 24.dp)
            )
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, end = 24.dp),
        shape = RecipeekShapes.medium,
        backgroundColor = AppColorsTheme.colors.uiBackground,
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = 16.dp),
        ) {
            Text(
                text = recipe.title,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                color = AppColorsTheme.colors.text
            )
            Text(
                text = context.resources.getQuantityString(
                    R.plurals.ingredientsQuantity,
                    recipe.ingredients.size,
                    recipe.ingredients.size
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.caption,
                color = AppColorsTheme.colors.textHint
            )
        }
    }
}

@Composable
fun IngredientList(ingredients: List<Ingredient>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "Ingrédients",
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.h5,
            color = AppColorsTheme.colors.text,
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            items(ingredients) { ingredient ->
                IngredientItem(ingredient = ingredient)
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Column {
        Image(
            painter = painterResource(id = ingredient.iconRes),
            contentDescription = ingredient.name,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = Color.Red.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(8.dp)
        )
        Text(
            text = ingredient.name,
            modifier = Modifier
                .align(Alignment.Start)
                .width(80.dp),
            style = MaterialTheme.typography.h6,
            color = AppColorsTheme.colors.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${ingredient.quantity} ${ingredient.quantityType}",
            modifier = Modifier
                .align(Alignment.Start)
                .width(80.dp),
            style = MaterialTheme.typography.body2,
            color = AppColorsTheme.colors.textHint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@ExperimentalAnimationApi
@Composable
fun CookingSteps(steps: List<Step>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Préparation",
            style = MaterialTheme.typography.h5,
            color = AppColorsTheme.colors.text,
        )
        Spacer(modifier = Modifier.size(8.dp))
        steps.forEach { step ->
            StepItem(step = step)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun StepItem(step: Step) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(
                color = Color(0x3300b388),
                shape = RecipeekShapes.medium.copy(all = CornerSize(32.dp))
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Etape ${step.position}",
            style = MaterialTheme.typography.h6,
            color = AppColorsTheme.colors.mainColor
        )
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                fadeOut(animationSpec = tween(150)) using
                SizeTransform { initialSize, targetSize ->
                    if (targetState) {
                        keyframes {
                            // Expand horizontally first.
                            IntSize(targetSize.width, initialSize.height) at 150
                            durationMillis = 300
                        }
                    } else {
                        keyframes {
                            // Shrink vertically first.
                            IntSize(initialSize.width, targetSize.height) at 150
                            durationMillis = 300
                        }
                    }
                }
            }
        ) { targetExpanded ->
            Text(
                text = step.desc,
                modifier = Modifier.clickable { expanded = !expanded },
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium),
                color = AppColorsTheme.colors.text,
                maxLines = if (targetExpanded) Integer.MAX_VALUE else 3,
                overflow = if (targetExpanded) TextOverflow.Clip else TextOverflow.Ellipsis
            )
        }
    }
}
