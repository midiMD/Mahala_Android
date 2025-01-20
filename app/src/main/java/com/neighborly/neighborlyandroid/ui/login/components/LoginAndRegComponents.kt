package com.neighborly.neighborlyandroid.ui.login.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.ui.theme.AccentColor
import com.neighborly.neighborlyandroid.ui.theme.BgColor
import com.neighborly.neighborlyandroid.ui.theme.GrayColor
import com.neighborly.neighborlyandroid.ui.theme.Primary
import com.neighborly.neighborlyandroid.ui.theme.Secondary
import com.neighborly.neighborlyandroid.ui.theme.TextColor


@Composable
fun ShowPasswordIcon(value:Boolean) =
    if(!value){
        Icon(
            painter = painterResource(id = R.drawable.baseline_visibility_off_24),
            contentDescription = "Show password off"
        )
    }else{
        Icon(
            painter = painterResource(id = R.drawable.baseline_visibility_24),
            contentDescription = "Show password on"
        )

    }

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HouseRegistrationHeading(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 20.dp),
        horizontalArrangement = Arrangement.Center)
    {
        Text(
            text = "House",

            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            color = TextColor,
            //textAlign = TextAlign.Center,
        )
        Icon(
            imageVector = Icons.Filled.Home,
            "House registration logo",
        )
    }

}

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MyTextFieldComponent(value:String, onChange:(String)->Unit,labelValue: String, icon: ImageVector) {
    OutlinedTextField(
        label = {
            Text(text = labelValue)
        },
        value = value,
        onValueChange = {
            onChange(it)
        },

        colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AccentColor,
            focusedLabelColor = AccentColor,
            cursorColor = Primary,
            unfocusedContainerColor = BgColor,
            focusedLeadingIconColor = AccentColor,
            focusedTextColor = TextColor,
            unfocusedTextColor = TextColor
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "profile"
            )
        },
        keyboardOptions = KeyboardOptions.Default
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComponent(value:String, onChange:(String)->Unit,labelValue: String, icon: ImageVector) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        label = {
            Text(text = labelValue)
        },
        value = value,
        onValueChange = {
            onChange(it)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = AccentColor,
            focusedLabelColor = AccentColor,
            cursorColor = Primary,
            unfocusedContainerColor = BgColor,
            focusedLeadingIconColor = AccentColor,
            focusedTextColor = TextColor,
            unfocusedTextColor = TextColor
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "profile"
            )
        },
        trailingIcon = {

            IconButton(onClick = {
                isPasswordVisible = !isPasswordVisible
            }) {
                ShowPasswordIcon(isPasswordVisible)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}



@Composable
fun RegistrationTAndC(onChange:(Boolean)-> Unit, isChecked:Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(10.dp)
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onChange(it)
            }
        )
        RegistrationTAndCText()
    }
}

@Composable
fun RegistrationTAndCText(privacyPolicyUrl:String ="http://www.google.com", termsOfUseUrl:String = "http://www.google.com" ) {
    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = TextColor)) {
            append("By continuing you accept our ")
        }
        val privacyPolicyLink = LinkAnnotation.Url(
            url = privacyPolicyUrl,
            styles = TextLinkStyles(SpanStyle(color = Secondary))
        ){
            val url = (it as LinkAnnotation.Url).url // ir
            Log.i("logs","Clicked privacy policy")
            uriHandler.openUri(url)
        }
        withLink(privacyPolicyLink) { append("Privacy Policy") }

        withStyle(style = SpanStyle(color = TextColor)) {
            append(" and ")
        }
        val termsOfUseLink = LinkAnnotation.Url(
            url = termsOfUseUrl,
            styles = TextLinkStyles(SpanStyle(color = Secondary))
        ){
            val url = (it as LinkAnnotation.Url).url // ir
            Log.i("logs","Clicked terms of us")
            uriHandler.openUri(url)
        }
        withLink(termsOfUseLink) { append("Term of Use") }
        append(".")
    }
    Text(
        text = annotatedString
    )
}


@Composable
fun AccountQueryComponent(
    textQuery: String,
    textClickable: String,
    onNavigateToScreen: () ->Unit
) {

    val annonatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = TextColor, fontSize = 15.sp)) {
            append(textQuery)
        }


        val clickableLink = LinkAnnotation.Clickable(
            tag = textClickable,
            styles = TextLinkStyles(SpanStyle(color = Secondary))
        ){
//            val tag = (it as LinkAnnotation.Clickable).tag
//            Log.i("logs","clicked "+ tag)
            onNavigateToScreen()
        }

        withStyle(style = SpanStyle(color = Secondary, fontSize = 15.sp)) {
            withLink(clickableLink) { append(textClickable) }
        }
    }

    Text(text = annonatedString)
}

@Composable
fun ResetPasswordQuery(navigateToResetScreen:()->Unit){
    val annonatedString = buildAnnotatedString {
//        withStyle(style = SpanStyle(color = TextColor, fontSize =MaterialTheme.typography.bodySmall.fontSize )) {
//            append("Reset Password")
//        }
        val clickableLink = LinkAnnotation.Clickable(
            tag = "Reset Password",
            styles = TextLinkStyles(SpanStyle(color = Primary, fontSize = MaterialTheme.typography.bodyMedium.fontSize))
        ){
            navigateToResetScreen()
        }


        withLink(clickableLink) { append("Reset Password") }

    }

    Text(text = annonatedString)
}
