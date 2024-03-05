package com.tzh.animal.ui.screen.animal_detail

import android.support.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.tzh.animal.R
import com.tzh.animal.domain.model.Animal


@OptIn(ExperimentalCoilApi::class)
@Composable
fun AnimalDetailScreen(viewModel: AnimalDetailViewModel = hiltViewModel()) {

    val animalList by viewModel.animalList.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Use rememberImagePainter to load and display the image
//        val painter = rememberImagePainter(data = viewModel.animalImage)
//        Box(modifier = Modifier.size(100.dp), Alignment.Center) {
//            if (painter.state is ImagePainter.State.Loading) {
//                CircularProgressIndicator()
//            }
//            Image(
//                painter = painter,
//                contentDescription = viewModel.animalName,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(CircleShape),
//                contentScale = ContentScale.None
//            )
//        }
        AnimalList(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), animals = animalList
        )
    }
}

@Composable
fun AnimalList(modifier: Modifier, animals: List<Animal>) {
    LazyColumn(modifier = modifier) {
        items(animals) { animal ->
            AnimalItem(animal)
        }
    }
}

@Composable
fun AnimalItem(animal: Animal) {
    // Wrapper Card
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = animal.name,
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary)
            )
            Text(
                text = "Kingdom: ${animal.taxonomy.kingdom}\t|   " + "Location: ${
                    animal.locations.joinToString(
                        ", "
                    )
                }"
            )

            // Characteristics Rows
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                // Weight Column
                CharacteristicsColumn(
                    imageResource = R.drawable.weight,
                    label = "Weight",
                    value = animal.characteristics.weight
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Length Column
                CharacteristicsColumn(
                    imageResource = R.drawable.lenght,
                    label = "Length",
                    value = animal.characteristics.length
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                // Top Speed Column
                CharacteristicsColumn(
                    imageResource = R.drawable.speed,
                    label = "Top Speed",
                    value = animal.characteristics.top_speed
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Lifespan Column
                CharacteristicsColumn(
                    imageResource = R.drawable.lifespan,
                    label = "Lifespan",
                    value = animal.characteristics.lifespan
                )
            }
        }
    }
}

@Composable
private fun RowScope.CharacteristicsColumn(
    @DrawableRes imageResource: Int, label: String, value: String?
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.weight(1f)
    ) {
        // Characteristic Image
        Icon(
            painter = painterResource(id = imageResource),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(48.dp)
        )

        // Spacer
        Spacer(modifier = Modifier.height(8.dp))

        // Characteristic Text
        Text(text = value ?: "Researching...", fontWeight = FontWeight.Bold)

    }
}