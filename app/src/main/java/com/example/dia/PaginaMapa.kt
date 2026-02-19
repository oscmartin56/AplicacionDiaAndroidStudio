package com.example.dia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    val posmapa = LatLng(41.647583, -0.8961228)
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(posmapa, 13.5f)
    }
    val uriHandler = LocalUriHandler.current

    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar integrada
        CenterAlignedTopAppBar(
            title = { Text("Tiendas", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            actions = {
                TextButton(onClick = { uriHandler.openUri("https://www.dia.es/l/atencion-al-cliente") }) {
                    Text("Ayuda")
                }
            }
        )

        BuscadorMapa(cameraPositionState)

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(state = MarkerState(position = LatLng(41.6485, -0.8950)), title = "Dia 1")
                Marker(state = MarkerState(position = LatLng(41.6495, -0.8940)), title = "Dia 2")
            }

            Button(
                onClick = { /* Lógica de búsqueda en zona */ },
                modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null)
                Text(" Buscar en esta zona")
            }
        }
    }
}

@Composable
fun BuscadorMapa(cameraState: CameraPositionState) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Localidad, provincia o CP") },
        leadingIcon = { Icon(Icons.Default.Search, null) },
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
    )
}