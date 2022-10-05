package com.example.retrofitregressample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrofitregressample.network.ApiClient
import com.example.retrofitregressample.network.models.login.LoginRequest
import com.example.retrofitregressample.network.sessionmanager.SessionManager
import com.example.retrofitregressample.ui.theme.RetrofitRegresSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

private lateinit var apiClient: ApiClient

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitRegresSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.black)
                ) {
                    Greeting(sessionManager)
                }
            }
        }
    }
}

@Composable
fun Greeting(sessionManager: SessionManager) {
    apiClient = ApiClient(sessionManager = sessionManager)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composableScope = rememberCoroutineScope()
        Button(
            modifier = Modifier
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
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitRegresSampleTheme {}
}

private suspend fun login(sessionManager: SessionManager) {
    try {
        val result = apiClient.getApiService()
            .login(LoginRequest(email = "eve.holt@reqres.in", password = "mypassword"))
        println("vladr: token: ${result.body()?.token}")
        if (result.isSuccessful) {
            sessionManager.saveAuthToken(result.body()?.token.toString())
        } else {
            println("vladr: result not successfull: ${result.errorBody()}")
        }
    } catch (e: java.lang.Exception) {
        println("vladr: error: ${e.message}")
    }
}