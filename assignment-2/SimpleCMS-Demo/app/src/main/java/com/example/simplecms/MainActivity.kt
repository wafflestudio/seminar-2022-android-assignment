package com.example.simplecms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simplecms.ui.theme.SimpleCMSTheme
import com.example.simplecms.util.AuthStorage
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    private val authStorage: AuthStorage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCMSTheme {
                val navController = rememberNavController()
                val authInfo by authStorage.authInfo.collectAsState()
                NavHost(
                    navController = navController,
                    startDestination = if (authInfo == null) "login" else "postList",
                ) {
                    composable("login") { LoginContainer() }
                    composable("postList") {
                        PostListContainer(navigateToDetail = {
                            navController.navigate(
                                "postDetail/${it}"
                            )
                        })
                    }
                    composable(
                        "postDetail/{postId}",
                        arguments = listOf(navArgument("postId") { type = NavType.IntType })
                    ) {
                        val postId = it.arguments?.getInt("postId") ?: -1
                        PostDetailContainer(viewModel = koinViewModel(parameters = {
                            parametersOf(
                                postId
                            )
                        }))
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleCMSTheme {
        Greeting("Android")
    }
}