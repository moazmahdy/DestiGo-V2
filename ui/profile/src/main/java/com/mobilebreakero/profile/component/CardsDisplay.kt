package com.mobilebreakero.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.navigation_core.NavigationRoutes.ACCOUNT_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.SAVED_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.YOUR_POSTS_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.YOUR_TRIPS_SCREEN

@Composable
fun CardsDisplay(
    navController: NavController,
) {

    val items = listOf(
        ListItem(Color(0xFFD5E1FF), YOUR_POSTS_SCREEN),
        ListItem(Color(0xFFEDFDF6), YOUR_TRIPS_SCREEN),
        ListItem(Color(0xFFEBEBEB), ACCOUNT_SCREEN),
        ListItem(Color(0xFFF9FDED), SAVED_SCREEN),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(380.dp)
    ) {
        itemsIndexed(items = items, itemContent = { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(1f)
                    .padding(15.dp)
                    .shadow(
                        5.dp,
                        shape = RoundedCornerShape(22.dp)
                    )
                    .clickable { navController.navigate(item.textString) }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(item.color)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        item.textString,
                        fontSize = 24.sp, fontWeight = FontWeight.Bold
                    )
                }
            }
        })
    }
}

data class ListItem(
    val color: Color,
    val textString: String
)