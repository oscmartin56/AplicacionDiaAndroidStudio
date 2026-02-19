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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores corporativos compartidos
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

    // Solo el contenido, sin Scaffold propio
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DiaLightGray),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item { TopSearchBar() }
        item { WelcomeBanner() }
        item { ClubDiaCard() }
        item { MonederoCard() }
        item {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonBlancoIcono("Comprar", DiaAccentOrange, Modifier.weight(1f))
                BotonBlancoIcono("Folletos", DiaAccentBlue, Modifier.weight(1f))
            }
        }
        item { GanaPremiosCard() }
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
            Icon(Icons.Filled.AccountCircle, null, modifier = Modifier.size(45.dp), tint = Color.LightGray)
            Text(
                "Bienvenido a tu\nmejor Dia",
                modifier = Modifier.padding(start = 12.dp).weight(1f),
                fontWeight = FontWeight.Bold, fontSize = 15.sp, lineHeight = 18.sp
            )
            Button(
                onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = DiaDarkButton),
                shape = RoundedCornerShape(8.dp)
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
                Text("CLUB", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Dia", color = Color.White, fontWeight = FontWeight.Black, fontSize = 42.sp, lineHeight = 44.sp)
                Text("Cupones y descuentos", color = Color.White, fontSize = 12.sp)
            }
            Icon(Icons.Filled.SentimentVerySatisfied, null, tint=Color.Green, modifier = Modifier.padding(end=16.dp).size(50.dp))
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
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Mi monedero", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Acumular ahorro es fácil", fontSize = 12.sp, color = Color.Gray)
                }
                Text("0,00€", fontSize = 24.sp, fontWeight = FontWeight.Black)
            }
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
            Icon(Icons.Filled.CardGiftcard, null, tint = DiaRed, modifier = Modifier.size(40.dp))
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
        modifier = Modifier.width(155.dp).height(180.dp), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (cupon.estaBloqueado) {
                Icon(
                    imageVector = Icons.Default.Lock, null, tint = Color.White,
                    modifier = Modifier.size(22.dp).background(DiaRed, CircleShape).padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(cupon.porcentaje, color = DiaRed, fontWeight = FontWeight.Bold, fontSize = 15.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Text(cupon.descripcion, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), maxLines = 2)
        }
    }
}