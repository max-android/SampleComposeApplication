package com.sample.ru.features.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sample.ru.ui.theme.Shapes

@Composable
fun AlertDialogSample() {
        Column {
            val openDialog = remember { mutableStateOf(false)  }

            Button(onClick = {
                openDialog.value = true
            }) {
                Text("Click me")
            }

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Dialog Title")
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(

                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("This is the Confirm Button")
                        }
                    },
                    dismissButton = {
                        Button(

                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("This is the dismiss Button")
                        }
                    }
                )
            }
        }
}

@Composable
fun AppAlertDialog(
    confirmButtonText : String,
    dismissButtonText : String,
    message : String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val show = remember { mutableStateOf(true) }
    if (show.value) {
        AlertDialog(
            modifier = Modifier.graphicsLayer {
                shape = RoundedCornerShape(24.dp)
                clip = true
            },
            onDismissRequest = {},
            title = {
                Text("title", color = MaterialTheme.colors.onPrimary)
            },
            shape = Shapes.large,
            text = {
                Text(text = message, color = MaterialTheme.colors.onPrimary)
            },
            backgroundColor = MaterialTheme.colors.primary,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm.invoke()
                        show.value = false
                    }) {
                    Text(
                        text = confirmButtonText,
                        color = MaterialTheme.colors.secondary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss.invoke()
                        show.value = false
                    }) {
                    Text(
                        text = dismissButtonText,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        )
    }
}