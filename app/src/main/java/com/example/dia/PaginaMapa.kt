package com.example.dia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MapScreen() {

    val posmapa = LatLng(41.647583, -0.8961228)
    val posmar1 = LatLng(41.6485, -0.8950)
    val posmar2 = LatLng(41.6495, -0.8940)
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(posmapa, 13.5f)
    }
    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    @Composable
    fun TopBar() {
        val uriHandler = LocalUriHandler.current
        TopAppBar(
            title = { },
            actions = {
                TextButton(onClick = { uriHandler.openUri("https://www.dia.es/l/atencion-al-cliente") }) {
                    Text("Ayuda")
                }
            }
        )
    }
    class Tienda(val nombre: String, val ubicacion: LatLng)

    val listaTiendas = listOf(
        Tienda("Dia 1", LatLng(41.6485, -0.8950)),
        Tienda("Dia 2", LatLng(41.6495, -0.8940))
    )
    @Composable
    fun Buscador(cameraState: CameraPositionState, scope: CoroutineScope, tiendas: List<Tienda>) {
        Text(
            text = "Busca la tienda en el mapa",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            )
        var name by remember { mutableStateOf("") }

        TextField(
            value = name,
            onValueChange = { input ->
                name = input
                val encontrada = listaTiendas.find { it.nombre.equals(input, ignoreCase = true) }
                if (encontrada != null) {
                    scope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(encontrada.ubicacion, 16f),
                            1000
                        )
                    }
                }
            },
            label = { Text("Localidad, provincia o CP") },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp)
        )

    }

    Scaffold(
        topBar = { TopBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Buscador(cameraPositionState, coroutineScope, listaTiendas)
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    uiSettings = uiSettings
                ) {
                    Marker(
                        state = MarkerState(position = posmar1),
                        title = "Dia 1"
                    )
                    Marker(
                        state = MarkerState(position = posmar2),
                        title = "Dia 2"
                    )
                }
                Button(
                    onClick = {
                        val newLatLng = LatLng(41.6485, -0.8950)
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(newLatLng, 16f),
                                2000
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "",
                    )

                    Text(" Buscar en esta zona")
                }
            }
        }
    }
}