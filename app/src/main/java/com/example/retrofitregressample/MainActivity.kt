package com.example.retrofitregressample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrofitregressample.network.ApiClient
import com.example.retrofitregressample.network.models.login.LoginRequest
import com.example.retrofitregressample.network.sessionmanager.SessionManager
import com.example.retrofitregressample.ui.theme.RetrofitRegresSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType

private lateinit var apiClient: ApiClient

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitRegresSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    Greeting(sessionManager)
                }
            }
        }
    }
}

@Composable
fun Greeting(sessionManager: SessionManager) {
    val TAG = "vladr: main: "
    apiClient = ApiClient(sessionManager = sessionManager)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(bottom = 400.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composableScope = rememberCoroutineScope()

        Button(
            modifier = Modifier
                .padding(10.dp)
                .width(200.dp)
                .height(50.dp),
            shape = RectangleShape,
            onClick = {
                composableScope.launch {
                    login(sessionManager)
                }
            }
        ) {
            Text("Login")
        }

        Button(
            modifier = Modifier
                .padding(10.dp)
                .width(200.dp)
                .height(50.dp),
            shape = RectangleShape,
            onClick = {
                composableScope.launch {
                    getResources()
                }
            }
        ) {
            Text("Get Resources")
        }

        var resourceIdText by remember { mutableStateOf("") }
        Button(
            modifier = Modifier
                .padding(10.dp)
                .width(200.dp)
                .height(50.dp),
            shape = RectangleShape,
            onClick = {
                composableScope.launch {
                    try {
                        getResource(resourceIdText.toInt())

                    } catch (e: java.lang.Exception) {
                        println("$TAG wrong format -> need number")
                    }
                }
            }
        ) {
            Text("Get Single Resource")
        }

        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = resourceIdText,
            onValueChange = { newText ->
                resourceIdText = newText
            },
            label = {
                Text(text = "Resource ID")
            },
            modifier = Modifier.padding(10.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitRegresSampleTheme {}
}

private suspend fun login(sessionManager: SessionManager) {
    val TAG = "vladr: login: "
    try {
        val result = apiClient.getApiService()
            .login(LoginRequest(email = "eve.holt@reqres.in", password = "mypassword"))
        println("$TAG token: ${result.body()?.token}")
        if (result.isSuccessful) {
            sessionManager.saveAuthToken(result.body()?.token.toString())
        } else {
            println("$TAG result not successfull: ${result.errorBody()}")
        }
    } catch (e: java.lang.Exception) {
        println("$TAG error: ${e.message}")
    }
}

private suspend fun getResources() {
    val TAG = "vladr: get resources: "
    try {
        val result = apiClient.getApiService()
            .getResources()
        if (result.isSuccessful) {
            println("$TAG success: ${result.body()}")
        } else {
            println("$TAG result not successfully: ${result.errorBody()}")
        }
    } catch (e: java.lang.Exception) {
        println("$TAG error: ${e.message}")
    }
}

private suspend fun getResource(resourceId: Int = 2) {
    val TAG = "vladr: get single resource: "
    try {
        val result = apiClient.getApiService()
            .getResource(resourceId)
        if (result.isSuccessful) {
            println("$TAG success: ${result.body()}")
        } else {
            println("$TAG result not successfully: ${result.errorBody()}")
        }
    } catch (e: java.lang.Exception) {
        println("$TAG error: ${e.message}")
    }
}