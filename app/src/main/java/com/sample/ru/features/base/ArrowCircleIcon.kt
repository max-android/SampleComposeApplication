package com.sample.ru.features.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sample.ru.ui.theme.SampleComposeAppTheme
import com.sample.ru.R

@Preview
@Composable
fun ArrowCircleIconPreview() {
    Surface() {
        SampleComposeAppTheme() {
            ArrowCircleIcon(
                boxColor = MaterialTheme.colors.primaryVariant,
                iconID = R.drawable.ic_arrow_forward,
                arrowColor = Color.Blue,
                iconSize = 18.dp
            )
        }
    }
}

@Composable
fun ArrowCircleIcon(
    boxColor: Color,
    @DrawableRes iconID: Int,
    arrowColor: Color,
    iconSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                boxColor,
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(id = iconID),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(iconSize),
            tint = arrowColor
        )
    }
}