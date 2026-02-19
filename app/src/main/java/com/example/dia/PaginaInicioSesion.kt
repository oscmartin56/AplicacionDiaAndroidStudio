package com.example.dia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dia.DiaRed


@Preview
@Composable
fun paginaInicioSesion() {
    Scaffold(
//        bottomBar = { BottomBar() },
        topBar = { TopBar() },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}

@Composable
fun content() {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            IdiomaSimple()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Aún no te conocemos",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.llama),
                contentDescription = "Llama Dia",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(56.dp)),
                contentScale = ContentScale.Crop
            )

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = DiaRed),
                modifier = Modifier.padding(top = 30.dp)
            ) {
                Text("Quiero ser del Club Dia", color = Color.White)
            }

            Text(
                text = "¿Ya lo eres? Inicia sesión",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}
@Composable
fun IdiomaSimple() {
    var expanded by remember { mutableStateOf(false) }
    var seleccionado by remember { mutableStateOf("Español") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.bandera), contentDescription = "bandera españa", modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(text = seleccionado, fontWeight = FontWeight.Bold)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("Español", "Catalá", "Português", "English").forEach { idioma ->
                DropdownMenuItem(
                    text = { Text(idioma) },
                    onClick = {
                        seleccionado = idioma
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "", onValueChange = {},
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            placeholder = { Text("Buscar...", fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            trailingIcon = { Icon(Icons.Default.Mic, null, tint = DiaRed) },
            shape = RoundedCornerShape(25.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                focusedBorderColor = DiaRed
            )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.ShoppingCart, null, modifier = Modifier.size(24.dp))
            Text("0,00 €", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BottomBar() {
    var selectedItem by remember { mutableStateOf(2) } // "Dia" is selected by default
    val navItems = listOf(
        "Comprar" to Icons.Default.Menu,
        "Tiendas" to Icons.Default.LocationOn,
        "Dia" to null, // Special case
        "Club Dia" to Icons.Default.CreditCard,
        "Mi cuenta" to Icons.Default.Person
    )

    NavigationBar(containerColor = Color.White) {
        navItems.forEachIndexed { index, (label, icon) ->
            val isSelected = selectedItem == index
            if (label == "Dia") {
                NavigationBarItem(
                    selected = isSelected, onClick = { selectedItem = index },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(DiaRed, RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Dia",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
                )
            } else {
                NavigationBarItem(
                    selected = isSelected, onClick = { selectedItem = index },
                    icon = { Icon(icon as ImageVector, label) },
                    label = { Text(label, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DiaDarkButton, selectedTextColor = DiaDarkButton,
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}