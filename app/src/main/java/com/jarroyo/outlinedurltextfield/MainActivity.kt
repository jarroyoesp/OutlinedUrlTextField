package com.jarroyo.outlinedurltextfield

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jarroyo.outlinedurltextfield.ui.component.OutlinedUrlTextField
import com.jarroyo.outlinedurltextfield.ui.theme.OutlinedUrlTextFieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OutlinedUrlTextFieldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { scaffoldPadding ->
                    MainContent(modifier = Modifier.padding(scaffoldPadding))
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.then(Modifier.padding(16.dp)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        var text by remember { mutableStateOf("http://www.url.com") }
        var editMode by remember { mutableStateOf(true) }
        val context = LocalContext.current
        Text("OutlinedUrlTextField: handle on url clicks")
        OutlinedUrlTextField(
            value = text,
            onValueChange = { text = it },
            onUrlClick = {
                Log.d("OutlinedUrlTextField", "Open URL $it")
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = editMode,
            label = { Text("Label") },
        )
        Text("Common OutlinedTextField")
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            enabled = editMode,
            label = { Text("Label") },
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text("Change outlined enabled status:")
        Button(onClick = { editMode = !editMode }) {
            Text("Enabled: $editMode")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OutlinedUrlTextFieldTheme {
        MainContent()
    }
}
