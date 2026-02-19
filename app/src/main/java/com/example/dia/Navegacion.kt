package com.example.dia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NavViewModel : ViewModel() {
    var state by mutableStateOf(NavState())
        private set

    init {
        viewModelScope.launch {
            delay(2000)
            handleIntent(NavIntent.CambiarPantalla(Screen.Dia.route))
        }
    }

    fun handleIntent(intent: NavIntent) {
        when (intent) {
            is NavIntent.CambiarPantalla -> {
                state = state.copy(rutaActual = intent.ruta)
            }
            is NavIntent.IrADetalle -> {
                state = state.copy(rutaActual = "detalle", cuponSeleccionado = intent.cupon)
            }
        }
    }
}

data class NavState(
    val rutaActual: String = "splash",
    val cuponSeleccionado: Cupon? = null
)

sealed class NavIntent {
    data class CambiarPantalla(val ruta: String) : NavIntent()
    data class IrADetalle(val cupon: Cupon) : NavIntent()
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector?) {
    object Inicio : Screen("inicio", "Comprar", Icons.Default.Menu)
    object Tiendas : Screen("tiendas", "Tiendas", Icons.Default.LocationOn)
    object Dia : Screen("inicio_dia", "Dia", null)
    object ClubDia : Screen("club_dia", "Club Dia", Icons.Default.CreditCard)
    object MiCuenta : Screen("mi_cuenta", "Mi cuenta", Icons.Default.Person)
}

val navItems = listOf(
    Screen.Inicio,
    Screen.Tiendas,
    Screen.Dia,
    Screen.ClubDia,
    Screen.MiCuenta,
)

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE21F1D)),
        contentAlignment = Alignment.Center
    ) {
        Text("Dia", color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun DetalleFilaInfo(icon: ImageVector, titulo: String, sub: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(24.dp))
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(titulo, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(sub, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun CuponDetalleScreen(cupon: Cupon, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
            Text("Detalle del cupón", modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color(0xFFFFEFE6)), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.CardGiftcard, null, modifier = Modifier.size(100.dp), tint = Color(0xFFE50014))
        }
        Column(modifier = Modifier.padding(24.dp)) {
            Text(cupon.porcentaje, fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFFE50014))
            Text(cupon.descripcion, fontSize = 18.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(vertical = 8.dp))
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text("Información adicional", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            DetalleFilaInfo(Icons.Default.CheckCircle, "Promoción verificada", "Válido en todas las tiendas Dia")
            DetalleFilaInfo(Icons.Default.Info, "Condiciones", "No acumulable a otras ofertas")
            DetalleFilaInfo(Icons.Default.DateRange, "Caducidad", "Válido hasta el 31/12/2026")
        }
    }
}

@Composable
fun AppNavegacionPrincipal(navViewModel: NavViewModel = viewModel()) {
    val state = navViewModel.state
    val esSplash = state.rutaActual == "splash"
    val esDetalle = state.rutaActual == "detalle"

    Scaffold(
        bottomBar = {
            if (!esSplash && !esDetalle) {
                AppBottomNavBarSimple(
                    rutaActual = state.rutaActual,
                    onCambiarPantalla = { navViewModel.handleIntent(NavIntent.CambiarPantalla(it)) }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(if (esSplash || esDetalle) PaddingValues(0.dp) else innerPadding)) {
            when (state.rutaActual) {
                "splash" -> SplashScreen()
                "detalle" -> state.cuponSeleccionado?.let { cupon ->
                    CuponDetalleScreen(cupon) { navViewModel.handleIntent(NavIntent.CambiarPantalla(Screen.Dia.route)) }
                }
                Screen.MiCuenta.route -> paginaInicioSesion()
                Screen.Inicio.route -> PaginaListado()
                Screen.Tiendas.route -> MapScreen()
                Screen.ClubDia.route -> ClubYCuponesScreen()
                Screen.Dia.route -> AppDiaReplicaFull(navViewModel)
            }
        }
    }
}

@Composable
fun AppBottomNavBarSimple(rutaActual: String, onCambiarPantalla: (String) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        navItems.forEach { screen ->
            val isSelected = rutaActual == screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onCambiarPantalla(screen.route) },
                icon = {
                    if (screen.label == "Dia") {
                        Box(modifier = Modifier.size(56.dp).background(Color.Red, RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                            Text("Dia", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    } else Icon(screen.icon!!, contentDescription = screen.label)
                },
                label = if (screen.label != "Dia") { { Text(screen.label, fontSize = 10.sp) } } else null,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}