package com.example.worldskills

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun EditScreen(index: Int, context : Context, navigateBack: () -> Unit) {

        var bottomSheetScaffold = rememberBottomSheetScaffoldState()
         var selectedOne =
             remember {SharedPrefResolver.getSession(context)?.getJSONObject("result")?.getString("mobile")
                 ?.let {
                     SharedPrefResolver.getCart(context, it).getJSONArray("prefered_order").getJSONObject(index)
                 } }
        var size by remember { mutableStateOf(selectedOne!!.getJSONArray("cust")[0].toString()) }
        var flavour by remember { mutableStateOf(selectedOne!!.getJSONArray("cust")[1].toString()) }
        var dropdownEnabled by remember { mutableStateOf(false) }
        var sharedPref =  LocalContext.current.getSharedPreferences("User", Context.MODE_PRIVATE)
        var tea by remember { mutableStateOf(selectedOne?.getString("name")) }
        var selectedItem by remember { mutableStateOf(selectedOne!!.getInt("Qty")) }

        Spacer(modifier = Modifier
            .height(40.dp)
            .fillMaxWidth())
        BottomSheetScaffold(topBar = {
                                     TopAppBar(navigationIcon = {
                                                                IconButton(onClick = navigateBack) {
                                                                    Icon(
                                                                        Icons.Default.ArrowBack,
                                                                        contentDescription = "Go back"
                                                                    )
                                                                }

                                     }, title = { Text(text = "Edit", fontWeight = FontWeight.Bold, fontSize = 20.sp)})
        }, sheetContent = {
            Column(modifier = Modifier.height(350.dp)) {
                Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier
                    .width(14.dp)
                    .align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(text = "Total", fontWeight = FontWeight.Bold)
                        Text(text = "${CalculateCost.calculateTotalCost(selectedItem, size, flavour, tea!!)}", fontWeight = FontWeight.Bold)
                    }
                    ChoiceButton(selected = true, text ="EDIT ORDER", onClick = {
                        SharedPrefResolver.getSession(context = context)?.getJSONObject("result")?.getString("mobile")?.let {
                            SharedPrefResolver.editCart(context, it, JSONObject().apply {
                                put("Qty", selectedItem)
                                put("name", tea)
                                put("cust", JSONArray().apply {
                                    put(size)
                                    put(flavour)
                                })
                            }, index = index)
                        }
                        println("TEST")
                        Toast.makeText(context, "You have edited an item!", Toast.LENGTH_LONG).show()


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
                            Card(modifier = Modifier
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
                        ExposedDropdownMenuBox(expanded = dropdownEnabled, onExpandedChange = {dropdownEnabled = !dropdownEnabled}) {
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