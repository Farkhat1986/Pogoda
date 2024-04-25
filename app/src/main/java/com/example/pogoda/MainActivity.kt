package com.example.pogoda

import android.app.DownloadManager.Request
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pogoda.ui.theme.PogodaTheme
import org.json.JSONObject


const val API_KEY = "e5263f3dc7184af3ac6193051242404"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PogodaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("London", this)
                }
            }
        }
    }
}

@Composable
fun Greeting(sity: String, context: Context) {
    val state = remember{
        mutableStateOf("")}
    val status = remember{
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text(text = "Температура в городе $sity")
        }
        Box(modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text(text = "${state.value} градусов по C")
        }
        Box(modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text(text = "Дата и время сейчас ${status.value}")
        }

        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.Transparent),
            contentAlignment = Alignment.BottomCenter) {

            Button(onClick = {
                getResault(sity, state, status, context)
            }, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()) {
                Text(text = "Нажми для получения данных")
            }

        }
    }

}

private fun getResault(city:String, state: MutableState<String>, status: MutableState<String>, context: Context) {
    val url = "https://api.weatherapi.com/v1/current.json" + "?key=$API_KEY&" +
            "q=$city" + "&aqi=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(com.android.volley.Request.Method.GET, url,
        { response ->
            val obj = JSONObject(response)
        state.value = obj.getJSONObject("current").getString("temp_c")
            val tim = JSONObject(response)
        status.value = tim.getJSONObject("location").getString("localtime")
        },
        { error ->

        }
    )
    queue.add(stringRequest)
}