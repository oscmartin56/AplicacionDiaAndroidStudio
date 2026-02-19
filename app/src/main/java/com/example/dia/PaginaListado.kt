package com.example.dia

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val listaMaestraProductos = listOf(
    "Queso Manchego Curado", "Queso de Cabra", "Arroz Brillante Sabroz", "Arroz Vaporizado",
    "Leche Semidesnatada", "Yogur Griego Natural", "Pechuga de Pollo", "Filete de Ternera",
    "Salmón Ahumado", "Merluza del Pincho", "Manzana Royal Gala", "Plátano de Canarias",
    "Tomate Rama", "Lechuga Iceberg", "Aceite de Oliva Virgen Extra", "Vinagre de Jerez",
    "Pasta Galets", "Espaguetis Integrales", "Café Molido Tueste Natural", "Cacao en Polvo",
    "Galletas María", "Pan de Molde Artesano", "Pizza Casa Tarradellas", "Lasaña de Carne",
    "Patatas Chips Sal", "Nueces Peladas", "Refresco de Cola", "Zumo de Naranja 100%",
    "Cerveza Alhambra", "Vino Tinto Rioja", "Detergente Líquido", "Papel Higiénico",
    "Gel de Baño Fresh", "Champú Reparador"
)

data class Categoria(
    val nombre: String,
    val icono: Int,
    val subItems: List<String> = listaMaestraProductos.shuffled().take(4)
)

@Composable
fun PaginaListado() {
    Scaffold(
        topBar = { ToppBar() }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ListadoCategorias()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListadoCategorias() {
    val categorias = listOf(
        Categoria("Freidora de aire - Airfryer", R.drawable.ic_airfryer),
        Categoria("Sin gluten", R.drawable.ic_gluten),
        Categoria("Frutas", R.drawable.ic_frutas),
        Categoria("Verduras", R.drawable.ic_verduras),
        Categoria("Carnes", R.drawable.ic_carnes),
        Categoria("Pescados y mariscos", R.drawable.ic_pescado),
        Categoria("Charcutería y quesos", R.drawable.ic_queso),
        Categoria("Huevos, leche y mantequilla", R.drawable.ic_frutas),
        Categoria("Panadería", R.drawable.ic_verduras),
        Categoria("Yogures y postres", R.drawable.ic_queso),
        Categoria("Congelados", R.drawable.ic_pescado),
        Categoria("Aceites, salsas y especias", R.drawable.ic_airfryer),
        Categoria("Conservas, caldos y cremas", R.drawable.ic_gluten),
        Categoria("Azúcar, chocolates y caramelos", R.drawable.ic_queso),
        Categoria("Café, cacao e infusiones", R.drawable.ic_airfryer),
        Categoria("Galletas, bollos y cereales", R.drawable.ic_gluten),
        Categoria("Platos preparados y pizzas", R.drawable.ic_pescado),
        Categoria("Aperitivos y frutos secos", R.drawable.ic_frutas),
        Categoria("Agua y refrescos", R.drawable.ic_airfryer),
        Categoria("Zumos y smoothies", R.drawable.ic_verduras),
        Categoria("Cervezas, vinos y licores", R.drawable.ic_queso),
        Categoria("Limpieza y hogar", R.drawable.ic_gluten)
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        categorias.forEach { categoria ->
            val expandedState = mutableStateOf(false)

            // LA CABECERA STICKY
            stickyHeader(key = categoria.nombre) {
                var expandido by remember { expandedState }

                FilaCabeceraSticky(
                    categoria = categoria,
                    isExpanded = expandido,
                    onToggle = { expandido = !expandido }
                )
            }

            item {
                var expandido by remember { expandedState }
                if (expandido) {
                    Column(modifier = Modifier.background(Color(0xFFF5F5F5))) {
                        categoria.subItems.forEach { subItem ->
                            Text(
                                text = subItem,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 62.dp, top = 16.dp, bottom = 16.dp),
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
            }
        }
    }
}

@Composable
fun FilaCabeceraSticky(
    categoria: Categoria,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onToggle() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = categoria.icono),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = categoria.nombre,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Icon(
            imageVector = if (isExpanded) Icons.Default.Remove else Icons.Default.Add,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Composable
fun ToppBar() {
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "", onValueChange = {},
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
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