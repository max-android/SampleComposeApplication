package com.sample.ru.features.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sample.ru.R

@Composable
fun TestColumnLayout() {
    Column(
        modifier = Modifier
            .background(color = Color.Yellow)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.End,
        //используется для указания расположения дочерних
        // элементов в макетах "Столбец" и "Строка" в направлении оси (горизонтальном и вертикальном).
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "ПРИМЕР 1",
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center
        )
        Text(text = "ПРИМЕР 123",
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.End
        )
        Text(text = "ПРИМЕР 123456",
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun TestText() {
//    Box(
//        modifier = Modifier.size(height = 120.dp, width = 300.dp).background(color = Color.Yellow),
//       // contentAlignment = Alignment.TopCenter
//    ) {
//        Text(text = "TopStart", modifier = Modifier.align(Alignment.TopStart))
//        Text(text = "TopCenter", modifier = Modifier.align(Alignment.TopCenter))
//        Text(text = "TopEnd", modifier = Modifier.align(Alignment.TopEnd))
//
//        Text(text = "CenterStart", modifier = Modifier.align(Alignment.CenterStart))
//        Text(text = "Center", modifier = Modifier.align(Alignment.Center))
//        Text(text = "CenterEnd", modifier = Modifier.align(Alignment.CenterEnd))
//
//        Text(text = "BottomStart", modifier = Modifier.align(Alignment.BottomStart))
//        Text(text = "BottomCenter", modifier = Modifier.align(Alignment.BottomCenter))
//        Text(text = "BottomEnd", modifier = Modifier.align(Alignment.BottomEnd))
//
//    }

//    Column(
//        //horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = "Espresso",
//            fontSize = 50.sp
//        )
//        Text(
//            text = "Espresso",
//            fontSize = 50.sp
//        )
//    }

//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.fillMaxWidth().background(color = Color.Yellow)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_gallery),
//            contentDescription = "Espresso",
//            modifier = Modifier.size(100.dp)
//        )
//        Text(
//            text = "Espresso",
//            fontSize = 30.sp
//        )
//    }

//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_gallery),
//            contentDescription = "Espresso",
//            modifier = Modifier.size(100.dp)
//        )
//        Column(
//            modifier = Modifier.padding(horizontal = 8.dp)
//        ) {
//            Text(
//                text = "Espresso",
//                fontSize = 24.sp
//            )
//            Text(
//                text = "Espresso is coffee of Italian origin, brewed by forcing a small amount of nearly boiling water under pressure (expressing) through finely-ground coffee beans.",
//                style = TextStyle(textAlign = TextAlign.Justify)
//            )
//        }
//    }

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (logo, title, description, image) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_gallery),
            contentDescription = "Espresso",
            modifier = Modifier
                .size(100.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                }
        )
        Text(
            text = "Espresso",
            fontSize = 24.sp,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                linkTo(start = logo.end, end = parent.end, startMargin = 8.dp, endMargin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = "Espresso is coffee of Italian origin, brewed by forcing a small amount of nearly boiling water under pressure (expressing) through finely-ground coffee beans.",
            style = TextStyle(textAlign = TextAlign.Justify),
            modifier = Modifier.constrainAs(description) {
                top.linkTo(title.bottom)
                linkTo(start = title.start, end = image.start, endMargin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )

        Image(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )
    }

}