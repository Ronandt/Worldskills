package com.example.worldskills

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worldskills.ui.theme.appBarColor
import com.example.worldskills.ui.theme.primaryButtonColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navigateFeedback: () -> Unit, navigateCheckout: () -> Unit, context: Context) {
    var tabSelected by remember {mutableStateOf(1)}
    var points by remember {mutableStateOf(0)}

    var scaffoldState = rememberBottomSheetScaffoldState().apply {
        LaunchedEffect(Unit ) {
            this@apply.bottomSheetState.collapse()
        }


    }

    LaunchedEffect(Unit) {
       withContext(Dispatchers.IO) {

       }
    }

    Scaffold(topBar = {
        TopAppBar(backgroundColor = appBarColor, elevation = 0.dp) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier =Modifier.fillMaxWidth()) {
                Text(text = "OrderMyT", fontWeight = FontWeight.Bold, fontSize = 20.sp  )
                IconButton(onClick = navigateCheckout) {

                    Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart")
                }

            }
        }
    }) {
            Box(modifier = Modifier.padding(it)) {
                Column {
                    TabRow(selectedTabIndex = tabSelected, backgroundColor = appBarColor) {
                        Tab(selected = tabSelected == 0, onClick = { tabSelected = 0 }, modifier = Modifier.height(50.dp), selectedContentColor = Color(0xFFFFFFFF) ) {
                            if(tabSelected != 0 ) {
                                Text(text = "PROFILE", color = Color(0x5F000000))
                            } else {
                                Text(text = "PROFILE")
                            }

                        }

                        Tab(selected = tabSelected == 1, onClick = { tabSelected = 1 }, modifier = Modifier.height(50.dp), selectedContentColor = Color(0xFFFFFFFF)) {
                            if(tabSelected != 1 ) {
                                Text(text = "ORDER", color = Color(0x5F000000))
                            } else {
                                Text(text = "ORDER")
                            }

                        }

                        Tab(selected = tabSelected == 2, onClick = { tabSelected = 2 }, modifier = Modifier.height(50.dp),  selectedContentColor = Color(0xFFFFFFFF)) {
                            if(tabSelected != 2 ) {
                                Text(text = "HISTORY", color = Color(0x5F000000))
                            }  else {
                                Text(text = "HISTORY")
                            }

                        }
                    }



                    Column {
                        when(tabSelected) {
                            0 -> {
                                ProfileTab(navigateFeedback)
                            }
                            1-> {
                                OrderTab(context = context)
                            }
                            2 -> {
                                HistoryTab()
                            }
                        }
                    }
                }



        }
    }

}



@Composable
fun ProfileTab(navigateFeedback: () -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        androidx.compose.material.Card(modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier) {
                Image(painter = painterResource(id = R.drawable.person), contentDescription = "Profile picture", contentScale = ContentScale.Fit, modifier = Modifier.clip(
                    CircleShape))
            }

        }
    Text(text = "Rewards", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.size(10.dp))
        Card(colors = CardDefaults.cardColors(appBarColor), shape = RoundedCornerShape(20.dp),  modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)){
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight(),) {
                Text(text = "3300 Points", modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Favourite Drink", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Card(colors = CardDefaults.cardColors(appBarColor), shape = RoundedCornerShape(20.dp),  modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)){
            Row(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.bubbletea_1), contentDescription = "Bubble tea", modifier = Modifier.size(60.dp))
                Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.height(80.dp)){

                    Text("Bubble milk with ice cream"
                    )
                    Text(text = "Medium sized, Vanilla")
                }
                ChoiceButton(selected = true, text = "ORDER", small = true)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(colors = CardDefaults.cardColors(appBarColor), shape = RoundedCornerShape(20.dp), modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)){
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.dog), contentDescription = "Feedback", Modifier.size(40.dp))
                Text(text = "A need to feedback on our service?")
                ChoiceButton(selected = true, text = "FEEDBACK", small = true, onClick = navigateFeedback)
            }

        }

    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrderTab(context: Context) {

    var bottomSheetScaffold = rememberBottomSheetScaffoldState()
    var size by remember {mutableStateOf("Medium")}
    var flavour by remember {mutableStateOf("Vanilla")}
    var dropdownEnabled by remember {mutableStateOf(false)}
    var sharedPref =  LocalContext.current.getSharedPreferences("User", Context.MODE_PRIVATE)
    var tea by remember { mutableStateOf(getAllDrinks()[0])}
    var selectedItem by remember { mutableStateOf(1) }
    Spacer(modifier = Modifier
        .height(40.dp)
        .fillMaxWidth())
    BottomSheetScaffold(sheetContent = {
        Column(modifier = Modifier.height(300.dp)) {
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier
                .width(14.dp)
                .align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(text = "Total", fontWeight = FontWeight.Bold)
                    Text(text = "${CalculateCost.calculateTotalCost(selectedItem, size, flavour, tea)}", fontWeight = FontWeight.Bold)
                }
                ChoiceButton(selected = true, text ="ADD TO ORDER", onClick = {
                    SharedPrefResolver.getSession(context = context)?.getJSONObject("result")?.getString("mobile")?.let {
                        SharedPrefResolver.addCart(context, it, JSONObject().apply {
                            put("Qty", selectedItem)
                            put("name", tea)
                            put("cust", JSONArray().apply {
                                put(size)
                                put(flavour)
                            })
                        })
                    }
                    println("TEST")
                    Toast.makeText(context, "You have added an item to cart!", Toast.LENGTH_LONG).show()


                })
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

                    Text(text = "+$${
                        CalculateCost.findSizeCost(size)
                    }", fontWeight = FontWeight.Bold)
                    Text(text = "${size} Size", fontWeight = FontWeight.Bold)


            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

                Text(text = "+$${CalculateCost.flavourCost(flavour)}", fontWeight = FontWeight.Bold)
                Text(text = flavour, fontWeight = FontWeight.Bold)


            }


        }

    }) {
        Column {
            Row {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(getAllDrinks().size) {
                        androidx.compose.material.Card(modifier = Modifier
                            .size(160.dp, 170.dp)
                            .clickable {
                                tea = getAllDrinks()[it]
                            }, elevation = 10.dp) {
                            Image(painter = painterResource(R.drawable.bubbletea_1), contentDescription = "Bubble tea")
                        }

                    }


                }


            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "${tea}", modifier = Modifier
                    .padding(top = 10.dp, bottom = 60.dp)
                    .align(Alignment.CenterHorizontally), fontSize = 22.sp, )
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Quantity")
                    androidx.compose.material3.ExposedDropdownMenuBox(expanded = dropdownEnabled, onExpandedChange = {dropdownEnabled = !dropdownEnabled}) {
                        Text(text = selectedItem.toString(), modifier = Modifier.menuAnchor())
                        ExposedDropdownMenu(expanded = dropdownEnabled, onDismissRequest =  {dropdownEnabled =false}, modifier = Modifier.size(100.dp)) {
                            DropdownMenuItem(onClick = { selectedItem = 1 }) {
                                Text(text = "1")
                            }
                            DropdownMenuItem(onClick = { selectedItem = 2 }) {
                                Text(text = "2")
                            }
                            DropdownMenuItem(onClick = {  selectedItem = 3 }) {
                                Text(text = "3")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(65.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Size")
                    Row(modifier = Modifier.width(200.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ChoiceButton(selected = size == "Medium", text = "MEDIUM", onClick = {size = "Medium"})
                        ChoiceButton(selected = size == "Large", text = "LARGE", onClick = {size = "Large"})
                    }


                }
                Spacer(modifier = Modifier.height(40.dp))
                Text("Ice Cream Flavour")
                Spacer(modifier = Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                    ChoiceButton(selected = flavour == "Vanilla", text ="VANILLA", onClick = {flavour = "Vanilla"})
                    ChoiceButton(selected = flavour == "Chocolate", text ="CHOCOLATE", onClick = {flavour = "Chocolate"} )
                    ChoiceButton(selected = flavour == "Durian", text ="DURIAN", onClick = {flavour = "Durian"})


                }
            }


        }
    }

 

}

@Composable
fun HistoryTab() {
    Spacer(modifier = Modifier.height(18.dp))
    Text(text = "Recent Orders", fontWeight = FontWeight.Bold, fontSize = 20.sp, )
    Spacer(modifier = Modifier.height(30.dp))
    LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(3) {
            Card(shape = RoundedCornerShape(30.dp), modifier = Modifier
                .fillMaxWidth()
                .height(120.dp), colors = CardDefaults.cardColors(appBarColor)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.bubbletea_1), contentDescription = "Bubble tea")
                    Column(modifier = Modifier.height(80.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Bubble milk tea with ice cream")
                        Text(text = "larged size vanilla")
                    }
                    Column(modifier =  Modifier.height(80.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$8.6", fontWeight = FontWeight.Bold)
                        ChoiceButton(selected = true, text = "ORDER AGAIN", small = true)
                    }

                }
            }
        }

    }

}

@Composable
fun ChoiceButton(selected: Boolean, text: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}, small: Boolean = false) {
   androidx.compose.material.Button(onClick = onClick, colors = if(selected) ButtonDefaults.buttonColors(backgroundColor = primaryButtonColor) else ButtonDefaults.buttonColors(backgroundColor = Color.White), modifier = modifier) {
       if(small) {
           Text(text, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
       } else {
           Text(text,)
       }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePrev() {
    OrderTab(LocalContext.current)
}