package com.example.dia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dia.ui.theme.DiaTheme

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

    Scaffold(
        topBar = { ClubCuponesTopBar() },
        bottomBar = { BottomNavigationDia() } // Re-using the bottom nav
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(DiaLightGray)
        ) {
            ClubDiaPromocionCard()
            Spacer(Modifier.height(16.dp))
            MonederoCard()
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Cupones (7)",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(allCupones) { cupon ->
                    CuponGridItem(cupon)
                }
            }
        }
    }
}

@Composable
fun ClubCuponesTopBar() {
    Column(modifier = Modifier.background(Color.White).padding(bottom = 8.dp)) {
        TopSearchBar()
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)){
             Text("Club y cupones", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}


@Composable
fun ClubDiaPromocionCard() {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DiaRed)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("CLUB", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Dia", color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.Black, lineHeight = 58.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = DiaDarkButton)
            ) {
                Text("Quiero ser del Club Dia", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun CuponGridItem(cupon: Cupon) {
    Card(
        modifier = Modifier.height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            if (cupon.estaBloqueado) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Bloqueado",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .size(20.dp)
                        .background(DiaRed, CircleShape)
                        .padding(3.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = cupon.porcentaje,
                color = DiaRed, fontWeight = FontWeight.Bold,
                fontSize = 13.sp, textAlign = TextAlign.Center
            )
            Text(
                text = cupon.descripcion,
                fontSize = 10.sp, textAlign = TextAlign.Center,
                lineHeight = 12.sp, maxLines = 2
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun ClubYCuponesScreenPreview() {
    DiaTheme {
        ClubYCuponesScreen()
    }
}
