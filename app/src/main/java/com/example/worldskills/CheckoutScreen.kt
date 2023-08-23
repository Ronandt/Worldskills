package com.example.worldskills

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.worldskills.ui.theme.appBarColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navigateBack: () -> Unit, context: Context, navigateOrder: () -> Unit, navController: NavController) {
    var session = SharedPrefResolver.getSession(context)
    var networkState = NetworkObserver.observe(context).collectAsState(initial = false)
    var mobileNumb = session?.getJSONObject("result")?.getString("mobile")
    var cart = mobileNumb?.let { SharedPrefResolver.getCart(context, mobileNumber = it) }
    var total by remember {mutableStateOf(0f)}
    LaunchedEffect(Unit) {
        for(x in 0 until (cart?.getJSONArray("prefered_order")?.length() ?: 0)) {
            var obj = cart?.getJSONArray("prefered_order")!!.getJSONObject(x)
            total += CalculateCost.calculateTotalCost(obj.getInt("Qty"), tea = obj.getString("name"), flavour = obj.getJSONArray("cust").getString(1), size = obj.getJSONArray("cust").getString(0))
        }
    }
    Scaffold(topBar = {
        TopAppBar(backgroundColor = appBarColor) {
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
               IconButton(onClick = navigateBack) {
                   Icon( Icons.Default.ArrowBack, contentDescription ="go back" )
               }
                Text(text = "Checkout", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

        }
    }){
        Box(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)) {
                LazyColumn(modifier = Modifier.heightIn(0.dp, 400.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {



                    items(cart?.getJSONArray("prefered_order")!!.length()) {
                        var obj = remember {cart?.getJSONArray("prefered_order")!!.getJSONObject(it)}
                        Card(shape = RoundedCornerShape(30.dp), modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp), colors = CardDefaults.cardColors(appBarColor)) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                                Image(painter = painterResource(R.drawable.bubbletea_1), contentDescription = "Bubble tea")
                                Column(modifier = Modifier.height(80.dp), verticalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Bubble milk tea with ice cream")
                                    Text(text = "${ obj.getJSONArray("cust").getString(1)}, ${obj.getJSONArray("cust").getString(0)}")
                                }
                                Column(modifier =  Modifier.height(80.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "$${ CalculateCost.calculateTotalCost(obj.getInt("Qty"), tea = obj.getString("name"), flavour = obj.getJSONArray("cust").getString(1), size = obj.getJSONArray("cust").getString(0))}", fontWeight = FontWeight.Bold)
                                    ChoiceButton(selected = true, text = "EDIT", small = true, onClick = { navController.navigate("edit/${it}") })
                                }

                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)){
                        Row(horizontalArrangement = Arrangement.SpaceBetween,  modifier = Modifier.fillMaxWidth()){
                            Text(text = "Sub-Total", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "${total}", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween,  modifier = Modifier.fillMaxWidth()){
                            Text(text = "GST", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "${"%.2f".format(total * 0.07)}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween,  modifier = Modifier.fillMaxWidth()){
                            Text(text = "Total Due", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "${"%.2f".format(total * 0.07 + total)}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        var scope = rememberCoroutineScope()
                        ChoiceButton(selected = true, text = "PAY", modifier = Modifier.width(200.dp), onClick = {
                            if(networkState.value) {
                                scope.launch(Dispatchers.IO) {
                                    var mobile =SharedPrefResolver.getSession(context = context)?.getJSONObject("result")!!.getString("mobile")
                                    CallAPI.pay(mobileNumber = mobile,cart = SharedPrefResolver.getCart(context = context, mobileNumber = mobile))
                                    SharedPrefResolver.clearCart(context, mobile)
                                    withContext(Dispatchers.Main) {

                                        Toast.makeText(context, "Ordered items!", Toast.LENGTH_LONG).show()
                                        navigateOrder()
                                    }
                                }
                            } else {
                                Toast.makeText(context, "You do not have internet, try again!", Toast.LENGTH_LONG).show()
                            }




                        })
                    }
                }

            }


        }
    }

}