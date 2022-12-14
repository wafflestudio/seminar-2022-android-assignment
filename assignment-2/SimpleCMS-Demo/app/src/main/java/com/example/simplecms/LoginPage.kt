package com.example.simplecms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginContainer(viewModel: UserViewModel = koinViewModel()) {
    val onSubmitLogin: suspend (String, String) -> Unit = viewModel::login
    LoginPage(onSubmitLogin = onSubmitLogin)
}

@Composable
fun LoginPage(
    onSubmitLogin: suspend (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Login with username and password",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username, onValueChange = { username = it },
            label = { Text(text = "Username") },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password, onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(text = "Password") },
            singleLine = true
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 20.dp),
            onClick = {
                scope.launch {
                    onSubmitLogin(username, password)
                }
            }) {
            Text(text = "Login")
        }
    }
}

@Composable
@Preview
private fun LoginPagePreview() {
    LoginPage(onSubmitLogin = { _, _ -> })
}