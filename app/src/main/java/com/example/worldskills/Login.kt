package com.example.worldskills

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worldskills.ui.theme.primaryButtonColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navigate: () -> Unit, context: Context) {
    var mobileNumber by remember {mutableStateOf("")}
    var scope = rememberCoroutineScope()
    var scaffold = rememberScaffoldState()
    var loading by remember {mutableStateOf(false)}
    var sharedPref = LocalContext.current.getSharedPreferences("User", Context.MODE_PRIVATE)
    Scaffold() {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(10.dp)) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", modifier = Modifier.size(400.dp), contentScale = ContentScale.Fit)
            OutlinedTextField(value = mobileNumber, onValueChange = {mobileNumber = it}, label = {Text("Mobile")}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
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
            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = primaryButtonColor)) {
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