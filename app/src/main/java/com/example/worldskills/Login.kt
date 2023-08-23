package com.example.worldskills

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worldskills.ui.theme.primaryButtonColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navigate: () -> Unit, context: Context) {
    var mobileNumber by remember {mutableStateOf("")}
    var scope = rememberCoroutineScope()
    var scaffold = rememberScaffoldState()
    var typed by remember { mutableStateOf(false)}
    var error by remember { mutableStateOf(false)}
    var networkState = NetworkObserver.observe(context).collectAsState(initial = false)
    var loading by remember {mutableStateOf(false)}

    var sharedPref = LocalContext.current.getSharedPreferences("User", Context.MODE_PRIVATE)
    Scaffold() {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(10.dp)) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", modifier = Modifier.size(400.dp), contentScale = ContentScale.Fit)
            androidx.compose.material3.OutlinedTextField(value = mobileNumber, onValueChange = {

                mobileNumber = it
                typed = true

                                                                    }, label = {Text("Mobile")}, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), isError = typed && !android.util.Patterns.PHONE.matcher(mobileNumber).matches(), supportingText = {
if(typed && !android.util.Patterns.PHONE.matcher(mobileNumber).matches()) {
    Text(text = "Improper phone number", color = Color.Red)
}

            })
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                if(!networkState.value) {
                    Toast.makeText(context, "You do not have internet, try again!", Toast.LENGTH_LONG).show()
                } else {
                    scope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                            loading = true
                        }

                        var login = CallAPI.login(mobileNumber)
                        println(login)
                        SharedPrefResolver.setSession(context = context, string = login)
                        withContext(Dispatchers.Main) {
                            loading = false
                            navigate()
                        }


                        //scaffold.snackbarHostState.showSnackbar("Logged in!")



                    }
                }

            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = primaryButtonColor), enabled = android.util.Patterns.PHONE.matcher(mobileNumber).matches()) {

                if(loading) {
                    Text(text = "Loading")
                } else {
                    Text("Submit")
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginPrev() {
    LoginScreen({}, LocalContext.current)
}