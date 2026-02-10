package com.example.dia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dia.ui.theme.DiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiaTheme {
                PantallaClubDia()
            }
        }
    }
}

// Define qué campos tiene un cupón según lo que vemos en la app
data class Cupon(
    val id: Int,
    val porcentaje: String, // Ej: "20% dto."
    val descripcion: String, // Ej: "COLACAO"
    val estaBloqueado: Boolean = true // Por defecto salen con candado
)

@Composable
fun CardCupon(cupon: Cupon) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icono de candado arriba a la izquierda (como en la captura)
            if (cupon.estaBloqueado) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Bloqueado",
                    tint = Color.Red,
                    modifier = Modifier.align(Alignment.Start).size(20.dp)
                )
            }

            // Espacio para la imagen del producto (puedes usar Coil aquí)
            Spacer(modifier = Modifier.height(60.dp))

            // Textos del cupón
            Text(
                text = cupon.porcentaje,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
            Text(
                text = cupon.descripcion,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PantallaClubDia() {
    // Lista de ejemplo basada en tus capturas
    val listaCupones = listOf(
        Cupon(1, "20% dto.", "Comprando 2 uds. de Leche..."),
        Cupon(2, "20% dto.", "COLACAO"),
        Cupon(3, "25% dto.", "SKIP y MIMOSIN")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Tarjeta Roja Principal
        Card(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE50014)) // Rojo Dia
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("CLUB Dia", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Button(
                    onClick = { /* Acción */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text("Quiero ser del Club Dia")
                }
            }
        }

        Text(
            text = "Cupones (${"$"}{listaCupones.size})",
            modifier = Modifier.padding(vertical = 16.dp),
            fontWeight = FontWeight.Bold
        )

        // Rejilla de cupones (2 columnas)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(listaCupones) { cupon ->
                CardCupon(cupon)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaClubDiaPreview() {
    DiaTheme {
        PantallaClubDia()
    }
}