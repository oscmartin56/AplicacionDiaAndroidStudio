package com.example.dia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String, val label: String, val icon: ImageVector?) {
    object Inicio : Screen("inicio", "Comprar", Icons.Default.Menu)
    object Tiendas : Screen("tiendas", "Tiendas", Icons.Default.LocationOn)
    object Dia : Screen("inicio_dia", "Dia", null) // Ruta única para el botón central
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
fun AppNavegacionPrincipal() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomNavBar(navController = navController) }
    ) { innerPadding ->
        // startDestination definido en Screen.Inicio.route para que no empiece en el mapa
        NavHost(
            navController,
            startDestination = Screen.Inicio.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Inicio.route) { AppDiaReplicaFull() }
            composable(Screen.Tiendas.route) { MapScreen() }
            composable(Screen.ClubDia.route) { ClubYCuponesScreen() }
            composable(Screen.MiCuenta.route) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Pantalla Mi Cuenta")
                }
            }
            // El botón central también redirige a Inicio
            composable("inicio_dia") { AppDiaReplicaFull() }
        }
    }
}

@Composable
fun AppBottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(containerColor = Color.White) {
        navItems.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (screen.label == "Dia") {
                        Box(
                            modifier = Modifier.size(56.dp).background(DiaRed, RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Dia", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    } else {
                        Icon(screen.icon!!, contentDescription = screen.label)
                    }
                },
                label = if (screen.label != "Dia") { { Text(screen.label, fontSize = 10.sp) } } else null,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DiaDarkButton,
                    selectedTextColor = DiaDarkButton,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}