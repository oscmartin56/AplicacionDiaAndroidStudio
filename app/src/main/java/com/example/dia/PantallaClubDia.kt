package com.example.dia

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Entity(tableName = "cupones")
data class CuponEntity(
    @PrimaryKey val id: Int,
    val porcentaje: String,
    val descripcion: String
)

@Dao
interface CuponDao {
    @Query("SELECT * FROM cupones")
    fun getCupones(): Flow<List<CuponEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCupon(cupon: CuponEntity)

    @Update
    suspend fun updateCupon(cupon: CuponEntity)

    @Delete
    suspend fun deleteCupon(cupon: CuponEntity)
}

@Database(entities = [CuponEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cuponDao(): CuponDao
}

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null
    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "cupones_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}

class CuponViewModel(private val dao: CuponDao) : ViewModel() {
    val cuponesRoom: StateFlow<List<CuponEntity>> = dao.getCupones()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addOrUpdateCupon(id: Int, porcentaje: String, descripcion: String, existe: Boolean) {
        viewModelScope.launch {
            val entity = CuponEntity(id, porcentaje, descripcion)
            if (existe) dao.updateCupon(entity) else dao.insertCupon(entity)
        }
    }

    fun deleteCupon(id: Int, porcentaje: String, descripcion: String) {
        viewModelScope.launch {
            dao.deleteCupon(CuponEntity(id, porcentaje, descripcion))
        }
    }
}

@Composable
fun ClubYCuponesScreen() {
    val context = LocalContext.current
    val db = remember { DatabaseProvider.getDatabase(context) }
    val vm: CuponViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CuponViewModel(db.cuponDao()) as T
            }
        }
    )

    val cuponesGuardados by vm.cuponesRoom.collectAsState()

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
        Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Club y cupones", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.weight(1f))
                Text("Guardados: ${cuponesGuardados.size}", fontSize = 12.sp, color = DiaRed)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                ClubDiaPromocionCard()
            }

            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Text("Tus cupones", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
            }

            items(allCupones) { cupon ->
                val estaGuardado = cuponesGuardados.any { it.id == cupon.id }
                CuponGridItem(
                    cupon = cupon,
                    isSaved = estaGuardado,
                    onAction = {
                        vm.addOrUpdateCupon(cupon.id, cupon.porcentaje, cupon.descripcion, estaGuardado)
                    },
                    onDelete = {
                        vm.deleteCupon(cupon.id, cupon.porcentaje, cupon.descripcion)
                    }
                )
            }
        }
    }
}

@Composable
fun CuponGridItem(cupon: Cupon, isSaved: Boolean, onAction: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .clickable { onAction() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            if (cupon.estaBloqueado) {
                Icon(
                    imageVector = Icons.Default.Lock, null, tint = Color.White,
                    modifier = Modifier.align(Alignment.TopStart).size(18.dp).background(DiaRed, CircleShape).padding(3.dp)
                )
            }

            if (isSaved) {
                IconButton(
                    onClick = { onDelete() },
                    modifier = Modifier.align(Alignment.TopEnd).size(24.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                }
            }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(cupon.porcentaje, color = DiaRed, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(cupon.descripcion, fontSize = 10.sp, textAlign = TextAlign.Center, lineHeight = 12.sp, maxLines = 2)
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