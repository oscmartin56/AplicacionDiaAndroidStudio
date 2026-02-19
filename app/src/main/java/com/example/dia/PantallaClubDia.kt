package com.example.dia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClubYCuponesScreen() {
    val allCupones = listOf(
        Cupon(1, "20% dto.", "Comprando 2 uds. de Leche..."),
        Cupon(2, "20% dto.", "COLACAO"),
        Cupon(3, "25% dto.", "SKIP y MIMOSIN"),
        Cupon(4, "15% dto.", "Coca-Cola"),
        Cupon(5, "30% dto.", "NIVEA"),
        Cupon(6, "10% dto.", "Toallitas para bebé")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DiaLightGray)
    ) {
        // Encabezado
        Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
            Text("Club y cupones", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Dos columnas para que se vea mejor
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tarjeta grande de promoción del club
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                ClubDiaPromocionCard()
            }

            // Título de sección
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Text("Tus cupones", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
            }

            // Rejilla de cupones
            items(allCupones) { cupon ->
                CuponGridItem(cupon)
            }
        }
    }
}

@Composable
fun ClubDiaPromocionCard() {
    Card(
        modifier = Modifier.fillMaxWidth().height(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DiaRed)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("CLUB", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Dia", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Black)
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = DiaDarkButton),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Quiero ser del Club Dia")
            }
        }
    }
}

@Composable
fun CuponGridItem(cupon: Cupon) {
    Card(
        modifier = Modifier.height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            if (cupon.estaBloqueado) {
                Icon(
                    imageVector = Icons.Default.Lock, null, tint = Color.White,
                    modifier = Modifier.align(Alignment.Start).size(18.dp).background(DiaRed, CircleShape).padding(3.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(cupon.porcentaje, color = DiaRed, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(cupon.descripcion, fontSize = 10.sp, textAlign = TextAlign.Center, lineHeight = 12.sp, maxLines = 2)
        }
    }
}