package com.example.dia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MapScreen() {

    val posmapa = LatLng(41.647583, -0.8961228)
    val posmar1 = LatLng(41.6485, -0.8950)
    val posmar2 = LatLng(41.6495, -0.8940)
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

    @Composable
    fun Buscador() {
        Text(
            text = "Busca la tienda en el mapa",
            style = MaterialTheme.typography.titleLarge,
            )
        var name by remember { mutableStateOf("") }

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Localidad, provincia o CP") },
            modifier = Modifier.fillMaxWidth()
        )

    }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationDia() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Buscador()
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings
            )
            {
                Marker(
                    state = MarkerState(position = posmar1),
                    title = "Dia 1"
                )
                Marker(
                    state = MarkerState(position = posmar2),
                    title = "Dia 2"
                )
            }
        }
    }
}