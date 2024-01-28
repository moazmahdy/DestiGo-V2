package com.mobilebreakero.trips.screens.addplaces

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.components.AnimateIcon
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIP_DETAILS
import com.mobilebreakero.data.repoimpl.GenerateRandomIdNumber
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.trips.TripsViewModel
import com.mobilebreakero.trips.screens.plan.selectedLocationpass
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddPlacesScreen(
    navController: NavController,
    viewModel: TripsViewModel = hiltViewModel(),
    tripId: String,
    screenRoot: String
) {

    var location by remember { mutableStateOf("") }
    location = selectedLocationpass

    LaunchedEffect(Unit) {
        viewModel.getSearchedResult(location, "en_US", "")
    }

    var searchText by remember { mutableStateOf("") }
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    val coroutineScope = rememberCoroutineScope { ioDispatcher }
    val searchResults by viewModel.searchResult.collectAsState()
    var placeName by remember { mutableStateOf("") }
    var placeAddress by remember { mutableStateOf("") }
    var placeTripId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = CenterHorizontally
    ) {

        SearchTextField(text = searchText, onValueChange = {
            searchText = it
            coroutineScope.launch {
                viewModel.getSearchedResult(
                    query = searchText,
                    filter = "",
                    language = "en",
                )
            }
        }, label = "Search")

        SearchButton(
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .height(50.dp)
                .width(320.dp),
            onClick = {
                if (screenRoot.isEmpty()) {
                    navController.navigate("chooseCover/$tripId")
                } else if (screenRoot == TRIP_DETAILS) {
                    navController.navigate("tripDetails/$tripId")
                } else {
                    navController.navigate("chooseCover/$tripId")
                }
            },
            content = {
                Text(text = "Next", fontSize = 12.sp, color = Color.White)
            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        LazyColumn(
            modifier = Modifier.height(500.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = CenterHorizontally
        ) {

            val results = when (val response = searchResults) {
                is Response.Success -> response.data
                else -> emptyList()
            }

            items(results.size) { index ->
                results[index]?.let { result ->

                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .background(Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    clip = true,
                                    ambientColor = Color(0xFFD5E1FF)
                                )
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(20.dp))
                                .fillMaxWidth()
                                .background(Color(0xFFD5E1FF))
                        ) {

                            Column(modifier = Modifier.fillMaxSize()) {

                                result.name?.let {
                                    Text(
                                        text = it,
                                        fontSize = 22.sp,
                                        color = Color.Black,
                                        modifier = Modifier.padding(10.dp, bottom = 3.dp)
                                    )
                                }

                                result.addressObj?.addressString?.let {
                                    Text(
                                        text = it,
                                        fontSize = 11.sp,
                                        color = Color.Black,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    SearchButton(
                                        buttonColor = Color.Transparent,
                                        modifier = Modifier
                                            .height(35.dp)
                                            .align(CenterVertically)
                                            .width(120.dp),
                                        onClick = {
                                            navController.navigate("details/${result.locationId}")
                                        },
                                        border = BorderStroke(1.dp, Color(0xff4F80FF)),
                                        content = {
                                            AnimateIcon(
                                                modifier = Modifier.padding(5.dp),
                                                color = Color(0xff4F80FF)
                                            )

                                            Text(
                                                text = "View Details",
                                                fontSize = 12.sp,
                                                color = Color(0xff4F80FF),
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                        }
                                    )
                                    val context = LocalContext.current
                                    SearchButton(
                                        buttonColor = Color.Transparent,
                                        modifier = Modifier
                                            .height(35.dp)
                                            .align(CenterVertically)
                                            .width(120.dp),
                                        onClick = {
                                            Toast.makeText(
                                                context,
                                                "Place Added to Trip Successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            placeName =
                                                if (result.name != null) result.name!! else ""
                                            placeAddress =
                                                if (result.locationId != null) result.locationId!! else ""
                                            placeTripId = GenerateRandomIdNumber().toString()
                                            try {
                                                viewModel.savePlaces(
                                                    placeName,
                                                    placeAddress,
                                                    tripId,
                                                    placeTripId
                                                )
                                            } catch (e: Exception) {
                                                Log.d("AddPlacesScreen", "Error: ${e.message}")
                                            }
                                        },

                                        border = BorderStroke(1.dp, Color(0xff4F80FF)),
                                        content = {
                                            AnimateIcon(
                                                modifier = Modifier.padding(5.dp),
                                                color = Color(0xff4F80FF)
                                            )
                                            Text(
                                                text = "Add Place to Trip",
                                                fontSize = 12.sp,
                                                color = Color(0xff4F80FF),
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    var textFieldText by remember { mutableStateOf(text) }

    val containerColor = Color(0xFFEFEEEE)
    TextField(
        value = textFieldText,
        onValueChange = {
            textFieldText = it
            onValueChange(it)
        },
        modifier = Modifier
            .width(360.dp)
            .wrapContentHeight()
            .padding(20.dp, 12.dp, 20.dp, 12.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(22.dp),
                clip = true
            ),
        shape = RoundedCornerShape(22.dp),
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        label = { Text(text = label) },
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon",
                tint = Color.Black
            )
        }
    )

}

@Composable
fun SearchButton(
    onClick: () -> Unit,
    buttonColor: Color? = Color.White,
    border: BorderStroke? = null,
    modifier: Modifier,
    content: @Composable () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(buttonColor!!),
        border = border
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            content()
        }
    }

}