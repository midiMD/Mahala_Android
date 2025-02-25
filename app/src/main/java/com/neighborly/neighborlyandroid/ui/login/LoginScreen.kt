package com.neighborly.neighborlyandroid.ui.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.common.LoadingOverlay
import com.neighborly.neighborlyandroid.ui.common.SuccessOverlay
import com.neighborly.neighborlyandroid.ui.login.components.AccountQueryComponent
import com.neighborly.neighborlyandroid.ui.login.components.HeadingTextComponent
import com.neighborly.neighborlyandroid.ui.login.components.MyTextFieldComponent
import com.neighborly.neighborlyandroid.ui.login.components.PasswordTextFieldComponent
import com.neighborly.neighborlyandroid.ui.login.components.ResetPasswordQuery
import com.neighborly.neighborlyandroid.ui.theme.AccentColor
import com.neighborly.neighborlyandroid.ui.theme.GrayColor
import com.neighborly.neighborlyandroid.ui.theme.Secondary

@Composable
fun LoginScreen(onNavigateToRegister:()->Unit,
                navigateToMarket:()->Unit,
                onNavigateToResetPassword:()->Unit,
                viewModel:LoginViewModel = viewModel(factory = LoginViewModel.Factory)) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    LoginScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onClickLogin = viewModel::onLoginButtonPress,
        onNavigateToRegister = onNavigateToRegister,
        onNavigateToResetPassword = onNavigateToResetPassword,
        toggleIdle = viewModel::toggleIdle,
        navigateToMarket = navigateToMarket)
}
@Composable
fun LoginFormAndButton(
    onClickLogin:(String,String)->Unit,
    onNavigateToResetPassword: () -> Unit,
    onClickGoogleLogin:()->Unit,
    onNavigateToRegister: ()->Unit
){
    var emailFieldText: String by rememberSaveable  { mutableStateOf("") }
    var passwordFieldText: String by rememberSaveable { mutableStateOf("") }
    // Form fields
    Column {
        MyTextFieldComponent(value = emailFieldText, onChange = {emailFieldText = it}, labelValue = "Email", icon = Icons.Outlined.Email)
        Spacer(modifier = Modifier.height(10.dp))
        PasswordTextFieldComponent(value = passwordFieldText, onChange = {passwordFieldText = it},labelValue = "Password", icon = Icons.Outlined.Lock)
    }
    //Everything bellow the fields i.e. Login button, login w google, forgot pass etc
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {onClickLogin(emailFieldText,passwordFieldText)}, //Login button
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
                    Text(text = "Login", color = Color.White, fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    thickness = 1.dp,
                    color = GrayColor
                )
                Text(
                    text = "Or",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 20.sp
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    thickness = 1.dp,
                    color = GrayColor
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { /*TODO*/ }, // Implement log in with google
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    modifier = Modifier
                        .padding(4.dp)
                        .border(
                            width = 2.dp,
                            color = Color(android.graphics.Color.parseColor("#d2d2d2")),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_color_icon),
                        contentDescription = "Google Logo",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.width(2.dp))
            }


            Spacer(modifier = Modifier.height(2.dp))
            // e.g. "Already have an account"
            AccountQueryComponent("Don't have an account? ", "Register", onNavigateToScreen = {onNavigateToRegister()})
            ResetPasswordQuery(navigateToResetScreen = onNavigateToResetPassword)

        }
    }
}
@Composable
internal fun LoginScreen(snackbarHostState: SnackbarHostState,uiState:LoginScreenState,onClickLogin:(String,String)->Unit,onNavigateToRegister: () -> Unit,onNavigateToResetPassword: () -> Unit,
                         toggleIdle:()->Unit,navigateToMarket:()->Unit){
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                HeadingTextComponent(value = "Welcome Back")
            }
            Spacer(modifier = Modifier.height(25.dp))
            LoginFormAndButton(
                onClickLogin = onClickLogin,
                onClickGoogleLogin = { Log.i("logs","Auth with google ")},
                onNavigateToRegister = onNavigateToRegister,
                onNavigateToResetPassword = onNavigateToResetPassword
            )
            LaunchedEffect(uiState){
                when(uiState){
                    is LoginScreenState.Error -> {
                        snackbarHostState.showSnackbar(message = uiState.message)
                        toggleIdle()
                    }
                    else -> {}
                }

            }
        }
    }
    when(uiState){

        LoginScreenState.Loading -> {
            LoadingOverlay()
        }
        LoginScreenState.AddressVerified ->{
            SuccessOverlay()
            Log.i("logs","Navigating to Market from Login Screen")
            toggleIdle()
            navigateToMarket()

        }
        LoginScreenState.AddressNotVerified->{
            UnverifiedAddressScreen({toggleIdle()})
        }

        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        uiState = LoginScreenState.AddressVerified,
        snackbarHostState = SnackbarHostState(),
        onClickLogin = {a,b->Log.i("logs","asda")},
        onNavigateToRegister = {},
        onNavigateToResetPassword = {},
        toggleIdle = { },
        navigateToMarket = {},
    )
}
