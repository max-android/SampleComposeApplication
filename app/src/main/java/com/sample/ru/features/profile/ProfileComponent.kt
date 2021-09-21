package com.sample.ru.features.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.R
import androidx.compose.runtime.*
import com.sample.ru.features.base.LineElement

class ProfileScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.ProfileScreen.route) {
            ProfileComponent()
        }
    }

}

@Composable
fun ProfileComponent() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 36.dp, bottom = 24.dp, start = 8.dp, end = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            style = MaterialTheme.typography.h2,
            text = stringResource(id = R.string.settings_app_title),
        )
        ConfigureElement()
        DecorationElement()
        SettingSwitchElement()
        SettingElement()
    }
}

@Composable
fun ConfigureElement() {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.ic_settings_app),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 12.dp, top = 8.dp)
            )
            Column() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    style = MaterialTheme.typography.subtitle1,
                    text = stringResource(id = R.string.settings_configure_access_code_title),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp, bottom = 16.dp),
                    style = MaterialTheme.typography.subtitle2,
                    text = stringResource(id = R.string.settings_configure_access_code_description),
                )
            }
        }
    }
}

@Composable
fun DecorationElement() {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 24.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.ic_settings_app),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 12.dp, top = 8.dp)
            )
            Column() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    style = MaterialTheme.typography.subtitle1,
                    text = stringResource(id = R.string.settings_decoration),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp, bottom = 16.dp),
                    style = MaterialTheme.typography.subtitle2,
                    text = stringResource(id = R.string.settings_decoration_description),
                )
            }
        }
    }
}

@Composable
fun SettingSwitchElement() {
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.subtitle2,
                text = stringResource(id = R.string.settings_switch_text),
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 8.dp, bottom = 16.dp),
                style = MaterialTheme.typography.subtitle2,
                text = stringResource(id = R.string.settings_switch_text),
            )
        }

        var checkedState by remember { mutableStateOf(true) }
        Switch(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            checked = checkedState,
            onCheckedChange = {
                checkedState = it
            }
        )

    }
}

@Composable
fun SettingElement() {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 56.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column() {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.h2,
                text = stringResource(id = R.string.settings_text),
            )
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_settings_app),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .padding(start = 12.dp, top = 8.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    style = MaterialTheme.typography.subtitle1,
                    text = stringResource(id = R.string.settings_text_description),
                )
            }
            LineElement()
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_settings_app),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .padding(start = 12.dp, top = 8.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    style = MaterialTheme.typography.subtitle1,
                    text = stringResource(id = R.string.settings_text_description),
                )
            }
            LineElement()
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_settings_app),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .padding(start = 12.dp, top = 8.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    style = MaterialTheme.typography.subtitle1,
                    text = stringResource(id = R.string.settings_text_description),
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}





