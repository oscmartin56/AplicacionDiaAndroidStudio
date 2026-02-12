package com.example.dia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dia.ui.theme.DiaTheme

// --- COLORES CORPORATIVOS ---
val DiaRed = Color(0xFFE50014)
val DiaLightGray = Color(0xFFF7F7F7)
val DiaDarkButton = Color(0xFF424242)
val DiaAccentBlue = Color(0xFFE0F7FA)
val DiaAccentOrange = Color(0xFFFFEFE6)

@Composable
fun AppDiaReplicaFull() {
    val cuponesList = listOf(
        Cupon(1, "20% dto.", "Comprando 2 uds. de Leche..."),
        Cupon(2, "20% dto.", "COLACAO"),
        Cupon(3, "25% dto.", "SKIP y MIMOSIN")
    )

    Scaffold(
        bottomBar = { BottomNavigationDia() }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(DiaLightGray),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // 1. BARRA DE BÚSQUEDA SUPERIOR
            item { TopSearchBar() }

            // 2. BANNER BIENVENIDA
            item { WelcomeBanner() }

            // 3. TARJETA CLUB DIA (ROJA)
            item { ClubDiaCard() }

            // 4. MI MONEDERO
            item { MonederoCard() }

            // 5. BOTONES DE ACCIÓN (COMPRAR / FOLLETOS)
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BotonBlancoIcono("Comprar", DiaAccentOrange, Modifier.weight(1f))
                    BotonBlancoIcono("Folletos", DiaAccentBlue, Modifier.weight(1f))
                }
            }

            // 6. GANA PREMIOS
            item { GanaPremiosCard() }

            // 7. SECCIÓN CUPONES HORIZONTAL
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Todos mis cupones (7)", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF006064))
                        Text("+ Añadir código de cupón", color = Color.Gray, fontSize = 11.sp)
                    }
                }
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cuponesList) { cupon ->
                        CuponIndividualCard(cupon)
                    }
                }
            }
        }
    }
}

@Composable
fun TopSearchBar() {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "", onValueChange = {},
            modifier = Modifier.weight(1f).height(50.dp),
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
fun WelcomeBanner() {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.AccountCircle, contentDescription = "Avatar", modifier = Modifier.size(45.dp), tint = Color.LightGray)
            Text(
                "Bienvenido a tu\nmejor Dia",
                modifier = Modifier.padding(start = 12.dp).weight(1f),
                fontWeight = FontWeight.Bold, fontSize = 15.sp, lineHeight = 18.sp
            )
            Button(
                onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = DiaDarkButton),
                shape = RoundedCornerShape(8.dp), contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text("Identifícate", fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun ClubDiaCard() {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(120.dp),
        colors = CardDefaults.cardColors(containerColor = DiaRed),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(start = 20.dp).weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("CLUB", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Icon(Icons.Default.Lock, contentDescription = null, tint = DiaRed, modifier = Modifier.padding(start = 8.dp).size(16.dp).background(Color.White, CircleShape).padding(2.dp))
                }
                Text("Dia", color = Color.White, fontWeight = FontWeight.Black, fontSize = 42.sp, lineHeight = 44.sp)
                Text("Cupones y descuentos", color = Color.White, fontSize = 12.sp)
            }
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                Text("¡Empieza a usarla ya!", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Icon(Icons.Filled.SentimentVerySatisfied, contentDescription = "Mascot", tint=Color.Green, modifier = Modifier.size(40.dp))
            }
        }
    }
}

@Composable
fun MonederoCard() {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Mi monedero", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Acumular ahorro es más fácil con Dia", fontSize = 12.sp, color = Color.Gray)
                }
                Text("0,00€", fontSize = 24.sp, fontWeight = FontWeight.Black)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth(0.4f).height(4.dp).clip(CircleShape).background(Color.Cyan.copy(alpha=0.5f)))
        }
    }
}

@Composable
fun GanaPremiosCard() {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Gana premios con Dia", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Icon(Icons.Filled.CardGiftcard, contentDescription = "Gift", tint = DiaRed, modifier = Modifier.size(40.dp))
        }
    }
}


@Composable
fun BotonBlancoIcono(texto: String, iconColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(58.dp), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(texto, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Box(modifier = Modifier.size(24.dp).background(iconColor, CircleShape))
        }
    }
}

@Composable
fun CuponIndividualCard(cupon: Cupon) {
    Card(
        modifier = Modifier.width(155.dp).height(210.dp), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (cupon.estaBloqueado) {
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = null, tint = Color.White,
                    modifier = Modifier.size(22.dp).background(DiaRed, CircleShape).padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f)) // Espacio para la imagen del producto
            Text(
                text = cupon.porcentaje, color = DiaRed, fontWeight = FontWeight.Bold,
                fontSize = 15.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = cupon.descripcion, fontSize = 11.sp, textAlign = TextAlign.Center,
                lineHeight = 13.sp, modifier = Modifier.fillMaxWidth(), maxLines = 2
            )
        }
    }
}

@Composable
fun BottomNavigationDia() {
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
                            modifier = Modifier.size(56.dp).background(DiaRed, RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Dia", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
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


@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun DefaultPreview() {
    DiaTheme {
        AppDiaReplicaFull()
    }
}
