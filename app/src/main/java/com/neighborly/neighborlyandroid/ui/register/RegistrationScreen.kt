package com.neighborly.neighborlyandroid.ui.register
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.domain.model.RegistrationDetails
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.common.LoadingSpinner
import com.neighborly.neighborlyandroid.ui.common.SuccessTick
import com.neighborly.neighborlyandroid.ui.common.showSnackbar
import com.neighborly.neighborlyandroid.ui.login.components.AccountQueryComponent
import com.neighborly.neighborlyandroid.ui.login.components.HeadingTextComponent
import com.neighborly.neighborlyandroid.ui.login.components.HouseRegistrationHeading
import com.neighborly.neighborlyandroid.ui.login.components.MyTextFieldComponent
import com.neighborly.neighborlyandroid.ui.login.components.PasswordTextFieldComponent
import com.neighborly.neighborlyandroid.ui.login.components.RegistrationTAndC
import com.neighborly.neighborlyandroid.ui.navigation.Screen
import com.neighborly.neighborlyandroid.ui.theme.AccentColor
import com.neighborly.neighborlyandroid.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(onNavigateToScreen:(screen:Screen)->Unit,
                       viewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory)
) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    //{TO DO} Snackbar business with a `when` statement on uiState
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            HeadingTextComponent(value = "Create an Account")
            Spacer(modifier = Modifier.height(5.dp))
            if (uiState == RegistrationScreenState.Loading){
                LoadingSpinner()
            }else if (uiState==RegistrationScreenState.Success){
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    SuccessTick()
                }
                onNavigateToScreen(Screen.Market)
            }
            else {
                RegisterFormAndButton(
                    onClickRegister = viewModel::onRegisterButtonPress,
                    onNavigateToScreen = {screen->onNavigateToScreen(screen)}
                )
                LaunchedEffect(uiState) {
                    when (uiState) {
                        RegistrationScreenState.Error.EmailExists -> {
                            showSnackbar(snackbarHostState, "Invalid Credentials")
                        }

                        RegistrationScreenState.Error.InvalidEmail -> {
                            showSnackbar(snackbarHostState, "Invalid Email")
                        }

                        RegistrationScreenState.Error.InvalidHouseError -> {
                            showSnackbar(snackbarHostState, "Invalid House Address")
                        }
                        is RegistrationScreenState.Error.MissingFields ->
                        {showSnackbar(snackbarHostState, "Fill in all compulsory fields")}
                        RegistrationScreenState.Error.NetworkError,
                        RegistrationScreenState.Error.ServerError -> {
                            showSnackbar(snackbarHostState, "Network Error")
                        }
                        RegistrationScreenState.Error.UnCheckedTAndC ->
                        {
                            showSnackbar(snackbarHostState, "Must consent to T&Cs to continue")
                        }
                        RegistrationScreenState.Error.WeakPassword -> {
                            showSnackbar(snackbarHostState, "Password is weak")
                        }
                        RegistrationScreenState.Idle,
                        RegistrationScreenState.Loading,
                        RegistrationScreenState.Success -> {}
                    }
                    viewModel.toggleIdle() // change state back to Idle after Errors are shown
                }
            }


        }
    }
}


@Composable
fun RegisterFormAndButton(
    onClickRegister:(details: RegistrationDetails)->Unit,
    onNavigateToScreen:(screen:Screen)->Unit
) {
    var fullNameFieldText:String by remember { mutableStateOf("") }
    var emailFieldText: String by remember { mutableStateOf("") }
    var passwordFieldText: String by remember { mutableStateOf("") }
    //house fields state
    var streetFieldText: String by remember { mutableStateOf("") }
    var postcodeFieldText: String by remember { mutableStateOf("") }
    var apartmentFieldText: String by remember { mutableStateOf("") }
    var numberFieldText: String by remember { mutableStateOf("") }

    var isCheckedTAndC: Boolean by remember { mutableStateOf(false) }

    Column {
        // Forms
        MyTextFieldComponent(
            labelValue = "Full Name",
            value = fullNameFieldText ,
            onChange = {fullNameFieldText = it},
            icon = Icons.Outlined.Person
        )
        Spacer(modifier = Modifier.height(2.dp))

        MyTextFieldComponent(
            labelValue = "Email",
            value = emailFieldText,
            onChange = {emailFieldText = it},
            icon = Icons.Outlined.Email
        )
        Spacer(modifier = Modifier.height(2.dp))
        PasswordTextFieldComponent(
            value = passwordFieldText,
            onChange  = {passwordFieldText = it},
            labelValue = "Password",
            icon = Icons.Outlined.Lock
        )
        Spacer(modifier = Modifier.height(5.dp))
        //House registration
        HouseRegistrationHeading()
        MyTextFieldComponent(
            labelValue = "Street Name",
            value = streetFieldText,
            onChange = {streetFieldText = it},
            icon = Icons.Outlined.LocationOn
        )
        MyTextFieldComponent(
            labelValue = "House Number",
            value = numberFieldText,
            onChange = {numberFieldText = it},
            icon = Icons.Outlined.LocationOn
        )
        Spacer(modifier = Modifier.height(2.dp))
        MyTextFieldComponent(
            labelValue = "Apartment Number*",
            value = apartmentFieldText,
            onChange = {apartmentFieldText = it},
            icon = Icons.Outlined.LocationOn
        )
        Spacer(modifier = Modifier.height(2.dp))
        MyTextFieldComponent(
            labelValue = "Postcode",
            value = postcodeFieldText,
            onChange = {postcodeFieldText = it},
            icon = Icons.Outlined.LocationOn
        )
        RegistrationTAndC(isChecked = isCheckedTAndC, onChange = {isCheckedTAndC = it})
        // Button and "already have an account?"


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val regDetails = RegistrationDetails(
                            fullName =fullNameFieldText,
                            email = emailFieldText,
                            password = passwordFieldText,
                            street = streetFieldText,
                            number = numberFieldText,
                            postcode = postcodeFieldText,
                            apartmentNumber = apartmentFieldText,
                            isCheckedTAndC = isCheckedTAndC
                        )
                        onClickRegister(regDetails)}, //Register
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(listOf(Secondary, AccentColor)),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .fillMaxWidth()
                            .heightIn(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Register", color = Color.White, fontSize = 20.sp)
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                // e.g. "Already have an account"
                AccountQueryComponent("Already have an account? ", "Login", onNavigateToScreen = {screen -> onNavigateToScreen(screen)})
            }
        }
    }
}


