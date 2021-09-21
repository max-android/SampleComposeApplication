package com.sample.ru.features.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sample.ru.ui.theme.SampleComposeAppTheme
import com.sample.ru.R
import com.sample.ru.ui.theme.Gray
import com.sample.ru.ui.theme.Teal200
import timber.log.Timber

@Composable
fun ArrowSample() {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.test_text),
            fontFamily = Font(R.font.montserrat_bold).toFontFamily(),
            fontSize = 16.sp
        )
        Image(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = null,
            // alignment = Alignment.CenterEnd
        )

    }

}

@Composable
fun ArrowSample2() {

    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        val (text, image) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(text) {
                    // start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin = 0.dp)
                    bottom.linkTo(parent.bottom, margin = 0.dp)
                    // end.linkTo(image.start, margin = 8.dp)
                    // linkTo(parent.start, parent.end, bias = 0.1f)
                },
            text = stringResource(id = R.string.test_text),
            fontFamily = Font(R.font.montserrat_bold).toFontFamily(),
            fontSize = 16.sp
        )

        Image(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = null,
            modifier = Modifier.constrainAs(image) {
                // end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(text.end, margin = 8.dp)
                // linkTo(parent.start, parent.end, bias = 0.7f)
                linkTo(text.end, parent.end, bias = 1f)
            }
        )

    }

}


@Composable
private fun SampleConstraintLayout() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val (heading, btn1, btn2, btn3, btn4, btn5) = createRefs()

        Text(text = "TV Guide in Compose 💥",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .height(120.dp)
                .constrainAs(heading) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        Button(
            onClick = { },
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(btn1) {
                    top.linkTo(heading.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text("Button-1")
        }

        Button(
            onClick = { },
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(btn2) {
                    top.linkTo(btn1.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text("Button-2")
        }

        Button(
            onClick = { },
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .constrainAs(btn3) {
                    top.linkTo(btn2.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(btn4.start)
                }
        ) {
            Text("Button-3")
        }

        Button(
            onClick = { },
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(btn4) {
                    top.linkTo(btn3.top)
                    start.linkTo(btn3.end)
                    end.linkTo(btn5.start)
                }
        ) {
            Text("Button-4")
        }

        Button(
            onClick = { },
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .constrainAs(btn5) {
                    top.linkTo(btn3.top)
                    start.linkTo(btn4.end)
                    end.linkTo(parent.end)
                }
        ) {
            Text("Button-5")
        }

    }
}

@Composable
private fun SampleConstraintLayout2() {
    Column() {
        Text("Simple constraint layout example")
        SimpleConstraintLayoutComponent()

        Text("Constraint layout example with guidelines")
        GuidelineConstraintLayoutComponent()

        Text("Constraint layout example with barriers")
        BarrierConstraintLayoutComponent()

        Text("Constraint layout example with bias")
        BiasConstraintLayoutComponent()
    }
}

@Composable
fun SimpleConstraintLayoutComponent() {

    Card(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        // ConstraintLayout is a composable that positions its children based on the constraints
        // we specify in its scope.
        ConstraintLayout {
            // This is where we specify the children of the ConstraintLayout composable.

            // In order to specify constraints, we use the helper function called createRefs.
            // This function helps us create ConstrainedLayoutReference, which we will assign to
            // our composable layouts. In order to apply these constraints to a
            // composable(view/layout), we reference these references to impose the respective
            // constraint on that composable. Look at how each of these references are being
            // reference below using the Modifier.contrainAs modifier.
            val (title, subtitle, image) = createRefs()

            // Text is a predefined composable that does exactly what you'd expect it to -
            // display text on the screen. It allows you to customize its appearance using
            // the style property. We also pass a modifier to it.

            // You can think of Modifiers as implementations of the decorators pattern that are used
            // to modify the composable that its applied to. In the example below, we configure the
            // Box to occupy the entire available height & width using Modifier.fillMaxSize().
            Text(
                "Title", style = TextStyle(
                    fontFamily = FontFamily.Serif, fontWeight =
                    FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(title) {
                    // Constraint the left edge of title to the right edge of the image
                    // and add a margin of 16dp
                    start.linkTo(image.end, margin = 16.dp)
                    // Constraint the top edge of title to the top edge of the image
                    top.linkTo(image.top)
                }
            )
            Text(
                "Subtitle", style = TextStyle(
                    fontFamily = FontFamily.Serif, fontWeight =
                    FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(subtitle) {
                    // Constraint the bottom edge of subtitle to the bottom edge of the image
                    bottom.linkTo(image.bottom)
                    // Constraint the start/left edge of subtitle to the right/end edge of the
                    // image and add a margin on 16.dp
                    start.linkTo(image.end, margin = 16.dp)
                }
            )

            Column(
                modifier = Modifier
                    .height(72.dp)
                    .width(72.dp)
                    .constrainAs(image) {
                        // We want to vertically center the image tag
                        centerVerticallyTo(parent)
                        // Constraint the left edge of image to the left edge of the parent
                        // and add a margin of 16dp
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_forward),
                    contentDescription = ""
                )
            }
        }
    }
}


@Composable
fun GuidelineConstraintLayoutComponent() {
    Card(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        ConstraintLayout {
            val (text1, text2) = createRefs()
            val quarter = createGuidelineFromStart(0.25f)
            val half = createGuidelineFromStart(0.5f)
            Text(
                "Quarter", style = TextStyle(
                    fontFamily = FontFamily.Serif, fontWeight =
                    FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(text1) {
                    centerVerticallyTo(parent)
                    end.linkTo(quarter)
                }
            )
            Text(
                "Half", style = TextStyle(
                    fontFamily = FontFamily.Serif, fontWeight =
                    FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(text2) {
                    centerVerticallyTo(parent)
                    start.linkTo(half)
                }
            )
        }
    }
}

@Composable
fun BarrierConstraintLayoutComponent() {
    Card(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        ConstraintLayout {
            val (text1, text2, text3) = createRefs()
            val barrier = createEndBarrier(text1, text2, margin = 16.dp)
            Text(
                "Short text", style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(text1) {
                    start.linkTo(parent.start, margin = 16.dp)
                    centerVerticallyTo(parent)
                }
            )
            Text(
                "This is a long text", style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(text2) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(text1.bottom, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
            )
            Text(
                "Barrier Text", style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(text3) {
                    start.linkTo(barrier)
                    centerVerticallyTo(parent)
                }
            )
        }
    }
}

@Composable
fun BiasConstraintLayoutComponent() {
    Card(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        ConstraintLayout {
            val (text1, text2) = createRefs()
            Text(
                "Left", style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(text1) {
                    centerVerticallyTo(parent)
                    linkTo(parent.start, parent.end, bias = 0.1f)
                }
            )
            Text(
                "Right", style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.W900, fontSize = 14.sp
                ), modifier = Modifier.constrainAs(text2) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                    linkTo(parent.start, parent.end, bias = 0.7f)
                }
            )
        }
    }
}

@Composable
fun SimpleButtonWithBorderComponent() {
    // Button is a pre-defined Material Design implementation of a contained button -
    // https://material.io/design/components/buttons.html#contained-button.

    // You can think of Modifiers as implementations of the decorators pattern that are used to
    // modify the composable that its applied to. In this example, we assign a padding of
    // 16dp to the Button.
    Button(
        onClick = {},
        modifier = Modifier.padding(16.dp),
        elevation = ButtonDefaults.elevation(5.dp),
        // Provide a border for this button
        border = BorderStroke(width = 5.dp, brush = SolidColor(Color.Black))
    ) {
        // The Button composable allows you to provide child composables that inherit this button
        // functionality.
        // The Text composable is pre-defined by the Compose UI library; you can use this
        // composable to render text on the screen
        Text(text = "Simple button with border", modifier = Modifier.padding(16.dp))
    }
}

// We represent a Composable function by annotating it with the @Composable annotation. Composable
// functions can only be called from within the scope of other composable functions. We should
// think of composable functions to be similar to lego blocks - each composable function is in turn
// built up of smaller composable functions.
@Composable
fun RoundedCornerButtonComponent() {
    // Button is a pre-defined Material Design implementation of a contained button -
    // https://material.io/design/components/buttons.html#contained-button.

    // You can think of Modifiers as implementations of the decorators pattern that are used to
    // modify the composable that its applied to. In this example, we assign a padding of
    // 16dp to the Button.
    Button(
        onClick = {},
        modifier = Modifier.padding(16.dp),
        // Provide a custom shape for this button. In this example. we specify the button to have
        // round corners of 16dp radius.
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(5.dp),
    ) {
        // The Button composable allows you to provide child composables that inherit this button
        // functionality.
        // The Text composable is pre-defined by the Compose UI library; you can use this
        // composable to render text on the screen
        Text(text = "Button with rounded corners", modifier = Modifier.padding(16.dp))
    }
}

// We represent a Composable function by annotating it with the @Composable annotation. Composable
// functions can only be called from within the scope of other composable functions. We should
// think of composable functions to be similar to lego blocks - each composable function is in turn
// built up of smaller composable functions.
@Composable
fun OutlinedButtonComponent() {
    // Button is a pre-defined Material Design implementation of a outlined button -
    // https://material.io/design/components/buttons.html#outlined-button.

    // You can think of Modifiers as implementations of the decorators pattern that are used to
    // modify the composable that its applied to. In this example, we assign a padding of
    // 16dp to the Button.
    OutlinedButton(
        onClick = {},
        modifier = Modifier.padding(16.dp)
    ) {
        // The Button composable allows you to provide child composables that inherit this button
        // functionality.
        // The Text composable is pre-defined by the Compose UI library; you can use this
        // composable to render text on the screen
        Text(text = "Outlined Button", modifier = Modifier.padding(16.dp))
    }
}

// We represent a Composable function by annotating it with the @Composable annotation. Composable
// functions can only be called from within the scope of other composable functions. We should
// think of composable functions to be similar to lego blocks - each composable function is in turn
// built up of smaller composable functions.
@Composable
fun TextButtonComponent() {
    // Button is a pre-defined Material Design implementation of a text button -
    // https://material.io/design/components/buttons.html#text-button.

    // You can think of Modifiers as implementations of the decorators pattern that are used to
    // modify the composable that its applied to. In this example, we assign a padding of
    // 16dp to the Button.
    TextButton(
        onClick = {},
        modifier = Modifier.padding(16.dp)
    ) {
        // The Button composable allows you to provide child composables that inherit this button
        // functionality.
        // The Text composable is pre-defined by the Compose UI library; you can use this
        // composable to render text on the screen
        Text(text = "Text Button", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun LoadProgress() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(56.dp),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
fun LoadError() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = null,
            modifier = Modifier
                .size(125.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun BaseSpacer() {
    Spacer(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .background(Teal200, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}


@Preview
@Composable
fun CheckList1Preview() {
    SampleComposeAppTheme() {
        //ArrowSample2()
        // LoadProgress(true)
    }
}

@Preview
@Composable
fun EmptyListUi() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_view_list),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .size(250.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun LineElement() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 64.dp, end = 8.dp),
        color = Teal200,
        thickness = 2.dp
    )
}

@Preview
@Composable
fun EmptyDetailItemUi() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_placeholder),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .size(250.dp)
                .align(Alignment.Center)
        )
    }
}