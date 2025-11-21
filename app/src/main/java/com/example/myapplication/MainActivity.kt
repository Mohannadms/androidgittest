package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    nameTextField()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun showCol() {

}


@Composable
fun nameTextField() {
    var name by remember { mutableStateOf(value = "") }
    TextField(
        value = name,
        onValueChange = { name = it },
        label = { Text(text = "Contact Name") },
        placeholder = { Text(text = "name") },
        modifier = Modifier.fillMaxWidth().padding(all = 40.dp)
    )
}

@Composable
fun phoneTextField() {
    var phonenum by remember { mutableStateOf(value = "") }
    TextField(
        value = phonenum,
        onValueChange = { phonenum = it },
        label = { Text(text = "Phone Number") },
        placeholder = { Text(text = "01000000000") },
        modifier = Modifier.fillMaxWidth().padding(all = 40.dp)
    )
}

@Composable
fun categoryTextField() {
    var category by remember { mutableStateOf(value = "") }
    TextField(
        value = category,
        onValueChange = { category = it },
        label = { Text(text = "Category") },
        placeholder = { Text(text = "name") },
        modifier = Modifier.fillMaxWidth().padding(all = 40.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewNameTextField() {
    MyApplicationTheme {
        nameTextField()
    }
}

