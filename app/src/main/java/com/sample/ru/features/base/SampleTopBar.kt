package com.sample.ru.features.base

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AddTransactionTopBarPreview() {
    return SampleTopBar(
        topAppBarText = "Title",
        actionTitle = "Save",
        onBackPressed = {  },
        onActionClicked = {  },
        actionEnabled = false
    )
}

@Composable
fun SampleTopBar(
    topAppBarText: String,
    actionTitle: String,
    onBackPressed: (() -> Unit)? = null,
    onActionClicked: (() -> Unit)? = null ,
    actionEnabled: Boolean = true,
) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp)
            )
        },
        navigationIcon = if (onBackPressed != null) {
            {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = null
                    )
                }
            }
        } else {
            null
        },
        actions = {
            if (onActionClicked != null) {
                Spacer(modifier = Modifier.width(68.dp))
                TextButton(onClick = onActionClicked, enabled = actionEnabled) {
                    Text(
                        actionTitle.uppercase(),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    )
}