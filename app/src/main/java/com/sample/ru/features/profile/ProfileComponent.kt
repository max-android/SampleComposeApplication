package com.sample.ru.features.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.ru.data.model.ProfileModel
import com.sample.ru.features.base.LineElement
import com.sample.ru.navigation.navigateSafe
import com.sample.ru.util.EMPTY_VALUE

class ProfileScreenFactory(private val onDarkModeChanged: (Boolean) -> Unit) : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.ProfileScreen.route) {
            ProfileComponent(onDarkModeChanged, navController)
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileComponent(onDarkModeChanged: (Boolean) -> Unit, navController: NavController) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val state: ProfileState? by viewModel.state.collectAsState()
    val sideEffect: ProfileSideEffect? by viewModel.sideEffect.collectAsState(null)
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveState(
        state,
        onCheckedChangeSwitch = {
            viewModel.obtainEvent(ChangeSwitchEvent(it))
        },
        onUserNameChange = {
            //keyboardController?.hide()
            viewModel.obtainEvent(EditProfileNameEvent(it))
        },
        onDarkModeChanged = onDarkModeChanged,
        onClickImagesLoad = {
            viewModel.obtainEvent(LoadPhoneImageEvent)
        }
    )
    ObserveSideEffect(sideEffect, navController)
}

@Composable
private fun ObserveState(
    state: ProfileState?,
    onCheckedChangeSwitch: (Boolean) -> Unit,
    onUserNameChange: (String) -> Unit,
    onDarkModeChanged: (Boolean) -> Unit,
    onClickImagesLoad: () -> Unit
) {
    state?.let { profileState ->
        when (profileState) {
            is SuccessProfile -> {
                ProfileUi(profileState.profileModel,
                    onCheckedChangeSwitch,
                    onUserNameChange,
                    onDarkModeChanged,
                    onClickImagesLoad
                )
            }
        }
    }
}

@Composable
fun ProfileUi(
    profile: ProfileModel,
    onCheckedChangeSwitch: (Boolean) -> Unit,
    onUserNameChange: (String) -> Unit,
    onDarkModeChanged: (Boolean) -> Unit,
    onClickImagesLoad: () -> Unit
) {
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
                .padding(16.dp),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h2,
            text = stringResource(id = R.string.settings_app_title),
        )
        EditNameElement(profile, onUserNameChange)
        ConfigureElement()
        DecorationElement()
        SettingSwitchElement(profile, onCheckedChangeSwitch, onDarkModeChanged)
        SettingElement(onClickImagesLoad)
    }
}

@Composable
fun EditNameElement(profile: ProfileModel, onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(profile.userName) }
    var isError by remember { mutableStateOf(false) }
    fun validate(text: String) {
        isError = text.count() < COUNT_SYMBOL
    }
    Column() {
        TextField(
            value = text,
            onValueChange = { userName ->
                text = userName
                isError = false
                if (userName.length >= COUNT_SYMBOL) {
                    onValueChange.invoke(userName)
                }
            },
            label = {
                Text(
                    text = stringResource(id = R.string.settings_enter_name),
                    color = MaterialTheme.colors.onPrimary
                )
            },
            keyboardActions = KeyboardActions { validate(text) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.secondary
            )
        )
        Text(
            text =
            if (isError) stringResource(id = R.string.settings_email_format_is_invalid) else EMPTY_VALUE,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun ConfigureElement() {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.ic_settings_app),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 12.dp, top = 8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )
            Column() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onPrimary,
                    text = stringResource(id = R.string.settings_configure_access_code_title),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp, bottom = 16.dp),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onPrimary,
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
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.ic_settings_app),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 12.dp, top = 8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
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
fun SettingSwitchElement(
    profile: ProfileModel,
    onCheckedChangeSwitch: (Boolean) -> Unit,
    onDarkModeChanged: (Boolean) -> Unit
) {
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

        var checkedState by remember { mutableStateOf(profile.isDarkThemeEnabled) }
        Switch(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            checked = checkedState,
            onCheckedChange = {
                checkedState = it
                onCheckedChangeSwitch.invoke(it)
                onDarkModeChanged.invoke(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.secondary,
                checkedTrackColor = MaterialTheme.colors.secondary,
                uncheckedThumbColor = MaterialTheme.colors.secondaryVariant,
                uncheckedTrackColor = MaterialTheme.colors.secondaryVariant,
            )
        )
    }
}

@Composable
fun SettingElement(onClickImagesLoad: () -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 56.dp, bottom = 68.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Column {
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
                        .padding(start = 12.dp, top = 8.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
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
                        .padding(start = 12.dp, top = 8.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
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
                        .padding(start = 12.dp, top = 8.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    style = MaterialTheme.typography.subtitle1,
                    text = stringResource(id = R.string.settings_text_description),
                )
            }
            LoadPhotoElement(onClickImagesLoad)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun LoadPhotoElement(onClickImagesLoad: () -> Unit) {
    OutlinedButton(
        onClick = {
            onClickImagesLoad.invoke()
        },
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 68.dp, end = 68.dp)
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(
                id = R.string.settings_load_images
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
private fun ObserveSideEffect(sideEffect: ProfileSideEffect?, navController: NavController) {
    sideEffect?.let { profileSideEffect ->
        when (profileSideEffect) {
            is ShowPhoneImage -> {
                navController.navigateSafe(Screen.PhoneImageScreen.route)
            }
        }
    }
}

private const val COUNT_SYMBOL = 3