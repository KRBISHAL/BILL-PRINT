package com.example

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.theme.MyApplicationTheme
import java.text.SimpleDateFormat
import java.util.*

// Styling Theme Colors matching Clean Utility / Minimal Style
val StudioCream = Color(0xFFFDF8F6)
val StudioPurple = Color(0xFF6750A4)
val StudioLightPurple = Color(0xFFE8DEF8)
val StudioDarkPurple = Color(0xFF1D192B)
val StudioSlate = Color(0xFF49454F)
val StudioBorder = Color(0xFFCAC4D0)
val StudioLightBorder = Color(0xFFE7E0EC)
val StudioGreen = Color(0xFF2E7D32)

class MainActivity : ComponentActivity() {
    private val viewModel: StudioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel)
            }
        }
    }
}

// Translations Object for Bilingual English/Spanish support
object Loc {
    private val t = mapOf(
        "EN" to mapOf(
            "app_name" to "360 Graphy",
            "tagline" to "Bilingual Photo Studio POS & Bookings",
            "nav_home" to "Home",
            "nav_booking" to "Bookings",
            "nav_pos" to "POS Billing",
            "nav_history" to "History Log",
            "stats_clients" to "Registered Clients",
            "stats_bookings" to "Scheduled Sessions",
            "stats_revenue" to "Total Invoiced",
            "register_title" to "Register New Client",
            "register_desc" to "Add customer details to database",
            "name" to "Full Name",
            "phone" to "Phone Number",
            "email" to "Email Address",
            "language" to "Preferred Language",
            "btn_register" to "Register Customer",
            "registered_list" to "Registered Studio Clients",
            "no_clients" to "No clients registered yet.",
            "book_title" to "Schedule Photo Session",
            "pkg_select" to "Select Session Package",
            "price" to "Price ($)",
            "date" to "Date (YYYY-MM-DD)",
            "time" to "Time (HH:MM)",
            "btn_book" to "Book Session",
            "upcoming" to "Upcoming Bookings & Statuses",
            "no_bookings" to "No bookings scheduled.",
            "pos_title" to "POS Studio Billing",
            "cart" to "Invoice Items",
            "add_item" to "Add Item",
            "btn_print_save" to "Print & Save",
            "history_title" to "Invoice History Log",
            "btn_reprint" to "Re-print",
            "no_history" to "No past invoices found.",
            "empty_cart" to "Your invoice cart is empty.",
            "select_client" to "Assign Customer",
            "popular_packages" to "Popular Packages & Services",
            "custom_item" to "Custom Item Name",
            "btn_add_custom" to "Add Custom",
            "booking_added" to "Booking successfully created",
            "booking_deleted" to "Booking deleted",
            "client_added" to "Client successfully registered",
            "select_registered_client" to "Select Registered Client",
            "or_enter_custom" to "Or enter custom customer name",
            "active_client" to "Active Client",
            "pos_total" to "Total Amount",
            "search_invoice" to "Search by customer name or invoice #...",
            "reprint_success" to "Reprinting Invoice #",
            "print_saved_success" to "Invoice saved & print job initiated!",
            "delete_client" to "Delete client",
            "delete_client_confirm" to "Client successfully removed",
            "profile_title" to "Customer Profile",
            "contact_info" to "Contact Information",
            "booking_history" to "Historical Bookings",
            "billing_history" to "Historical Invoices",
            "view_invoice" to "Invoice Receipt Copy",
            "close" to "Close",
            "no_bookings_client" to "No past bookings for this customer.",
            "no_bills_client" to "No past bills for this customer."
        ),
        "ES" to mapOf(
            "app_name" to "360 Graphy",
            "tagline" to "POS y Reservas de Estudio Fotográfico",
            "nav_home" to "Inicio",
            "nav_booking" to "Reservas",
            "nav_pos" to "Facturación POS",
            "nav_history" to "Historial",
            "stats_clients" to "Clientes Registrados",
            "stats_bookings" to "Sesiones Programadas",
            "stats_revenue" to "Facturado Total",
            "register_title" to "Registrar Nuevo Cliente",
            "register_desc" to "Agregar detalles del cliente a la base de datos",
            "name" to "Nombre Completo",
            "phone" to "Teléfono",
            "email" to "Correo Electrónico",
            "language" to "Idioma Preferido",
            "btn_register" to "Registrar Cliente",
            "registered_list" to "Clientes de Estudio Registrados",
            "no_clients" to "No hay clientes registrados.",
            "book_title" to "Programar Sesión Fotográfica",
            "pkg_select" to "Seleccionar Paquete de Sesión",
            "price" to "Precio ($)",
            "date" to "Fecha (AAAA-MM-DD)",
            "time" to "Hora (HH:MM)",
            "btn_book" to "Reservar Sesión",
            "upcoming" to "Próximas Sesiones y Estados",
            "no_bookings" to "No hay sesiones programadas.",
            "pos_title" to "Facturación POS de Estudio",
            "cart" to "Artículos de Factura",
            "add_item" to "Agregar Artículo",
            "btn_print_save" to "Imprimir y Guardar",
            "history_title" to "Historial de Facturas",
            "btn_reprint" to "Re-imprimir",
            "no_history" to "No se encontraron facturas.",
            "empty_cart" to "El carrito de facturación está vacío.",
            "select_client" to "Asignar Cliente",
            "popular_packages" to "Paquetes Populares y Servicios",
            "custom_item" to "Nombre de Artículo Personalizado",
            "btn_add_custom" to "Agregar",
            "booking_added" to "Reserva creada con éxito",
            "booking_deleted" to "Reserva eliminada",
            "client_added" to "Cliente registrado con éxito",
            "select_registered_client" to "Seleccionar Cliente Registrado",
            "or_enter_custom" to "O ingrese un nombre personalizado",
            "active_client" to "Cliente Activo",
            "pos_total" to "Total a Pagar",
            "search_invoice" to "Buscar por cliente o nº de factura...",
            "reprint_success" to "Re-imprimiendo Factura #",
            "print_saved_success" to "¡Factura guardada e impresión iniciada!",
            "delete_client" to "Eliminar cliente",
            "delete_client_confirm" to "Cliente eliminado con éxito",
            "profile_title" to "Perfil del Cliente",
            "contact_info" to "Información de Contacto",
            "booking_history" to "Historial de Reservas",
            "billing_history" to "Historial de Facturas",
            "view_invoice" to "Copia de Recibo de Factura",
            "close" to "Cerrar",
            "no_bookings_client" to "No hay reservas previas para este cliente.",
            "no_bills_client" to "No hay facturas previas para este cliente."
        )
    )

    fun get(key: String, lang: String): String {
        return t[lang]?.get(key) ?: key
    }
}

// Popular studio services & photo packages
data class PackageInfo(val nameEn: String, val nameEs: String, val defaultPrice: Double)

val studioPackages = listOf(
    PackageInfo("Wedding Portrait Session", "Sesión de Retrato de Bodas", 250.0),
    PackageInfo("Express Retouch (x5)", "Retoque Express (x5)", 75.0),
    PackageInfo("Studio Rental Session", "Alquiler de Estudio", 100.0),
    PackageInfo("Corporate Headshot", "Retrato Corporativo", 120.0),
    PackageInfo("Outdoor Golden Hour", "Exterior en Hora Dorada", 180.0),
    PackageInfo("Studio Rental Credit", "Crédito Alquiler Estudio", -20.0)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(viewModel: StudioViewModel) {
    val currentLang by viewModel.language.collectAsState()
    var currentTab by remember { mutableStateOf("home") } // "home", "bookings", "pos", "history", "settings"
    val context = LocalContext.current

    var selectedCustomerProfile by remember { mutableStateOf<Customer?>(null) }
    var selectedBillReceipt by remember { mutableStateOf<Bill?>(null) }

    val customers by viewModel.customers.collectAsState()
    val bookings by viewModel.bookings.collectAsState()
    val bills by viewModel.bills.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(StudioCream),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StudioCream,
                    titleContentColor = StudioDarkPurple
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(StudioPurple),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "360",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text(
                                text = Loc.get("app_name", currentLang),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = Loc.get("tagline", currentLang),
                                fontSize = 11.sp,
                                color = StudioSlate
                            )
                        }
                    }
                },
                actions = {
                    // Bilingual Switch (Pill Button)
                    Row(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(50))
                            .background(StudioLightBorder)
                            .padding(2.dp)
                            .clickable { viewModel.toggleLanguage() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (currentLang == "EN") Color.White else Color.Transparent)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "EN",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (currentLang == "EN") StudioPurple else StudioSlate
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (currentLang == "ES") Color.White else Color.Transparent)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "ES",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (currentLang == "ES") StudioPurple else StudioSlate
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            // Navigation Bar styled beautifully matching Clean Utility / Minimal style
            NavigationBar(
                containerColor = StudioLightBorder,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .height(72.dp)
            ) {
                NavigationBarItem(
                    selected = currentTab == "home",
                    onClick = { currentTab = "home" },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text(Loc.get("nav_home", currentLang), fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioDarkPurple,
                        selectedTextColor = StudioDarkPurple,
                        indicatorColor = StudioLightPurple,
                        unselectedIconColor = StudioSlate,
                        unselectedTextColor = StudioSlate
                    )
                )
                NavigationBarItem(
                    selected = currentTab == "bookings",
                    onClick = { currentTab = "bookings" },
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Bookings") },
                    label = { Text(Loc.get("nav_booking", currentLang), fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioDarkPurple,
                        selectedTextColor = StudioDarkPurple,
                        indicatorColor = StudioLightPurple,
                        unselectedIconColor = StudioSlate,
                        unselectedTextColor = StudioSlate
                    )
                )
                NavigationBarItem(
                    selected = currentTab == "pos",
                    onClick = { currentTab = "pos" },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "POS") },
                    label = { Text(Loc.get("nav_pos", currentLang), fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioDarkPurple,
                        selectedTextColor = StudioDarkPurple,
                        indicatorColor = StudioLightPurple,
                        unselectedIconColor = StudioSlate,
                        unselectedTextColor = StudioSlate
                    )
                )
                NavigationBarItem(
                    selected = currentTab == "history",
                    onClick = { currentTab = "history" },
                    icon = { Icon(Icons.Default.Receipt, contentDescription = "History") },
                    label = { Text(Loc.get("nav_history", currentLang), fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioDarkPurple,
                        selectedTextColor = StudioDarkPurple,
                        indicatorColor = StudioLightPurple,
                        unselectedIconColor = StudioSlate,
                        unselectedTextColor = StudioSlate
                    )
                )
                NavigationBarItem(
                    selected = currentTab == "settings",
                    onClick = { currentTab = "settings" },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text(if (currentLang == "ES") "Ajustes" else "Settings", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioDarkPurple,
                        selectedTextColor = StudioDarkPurple,
                        indicatorColor = StudioLightPurple,
                        unselectedIconColor = StudioSlate,
                        unselectedTextColor = StudioSlate
                    )
                )
            }
        },
        containerColor = StudioCream
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(StudioCream)
        ) {
            when (currentTab) {
                "home" -> HomeScreen(viewModel, currentLang, onViewCustomerProfile = { selectedCustomerProfile = it })
                "bookings" -> BookingsScreen(viewModel, currentLang)
                "pos" -> PosScreen(viewModel, currentLang)
                "history" -> HistoryScreen(viewModel, currentLang, onViewBillReceipt = { selectedBillReceipt = it })
                "settings" -> SettingsScreen(viewModel, currentLang)
            }

            // Customer Profile Dialog Overlay
            selectedCustomerProfile?.let { customer ->
                CustomerProfileDialog(
                    customer = customer,
                    bookings = bookings,
                    bills = bills,
                    lang = currentLang,
                    onDismiss = { selectedCustomerProfile = null },
                    onReprintBill = { bill -> viewModel.reprintBill(context, bill) },
                    onViewBill = { bill -> selectedBillReceipt = bill }
                )
            }

            // Receipt Visual Copy Dialog Overlay
            selectedBillReceipt?.let { bill ->
                BillReceiptDialog(
                    bill = bill,
                    lang = currentLang,
                    onDismiss = { selectedBillReceipt = null },
                    onReprint = { viewModel.reprintBill(context, bill) }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: StudioViewModel, lang: String, onViewCustomerProfile: (Customer) -> Unit) {
    val customers by viewModel.customers.collectAsState()
    val bookings by viewModel.bookings.collectAsState()
    val bills by viewModel.bills.collectAsState()
    val context = LocalContext.current

    var showRegisterDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Image
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.photo_studio_banner),
                        contentDescription = "Studio Workspace",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Semi-transparent overlay for text readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.45f))
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = Loc.get("app_name", lang),
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = Loc.get("tagline", lang),
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Stats Cards Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(Loc.get("stats_clients", lang), fontSize = 10.sp, color = StudioSlate, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${customers.size}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = StudioPurple)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(Loc.get("stats_bookings", lang), fontSize = 10.sp, color = StudioSlate, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${bookings.filter { it.status != "Completed" }.size}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = StudioPurple)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(Loc.get("stats_revenue", lang), fontSize = 10.sp, color = StudioSlate, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("$${String.format(Locale.US, "%.0f", bills.sumOf { it.totalAmount })}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = StudioGreen)
                    }
                }
            }
        }

        // Performance Dashboard
        item {
            val isEs = lang == "ES"
            
            // 1. Calculate popular services from bills
            val popularServices = remember(bills) {
                val serviceCounts = mutableMapOf<String, Int>()
                bills.forEach { b ->
                    val items = JsonUtils.fromJson(b.itemsJson)
                    items.forEach { item ->
                        val serviceName = if (isEs) {
                            item.nameEs.ifBlank { item.nameEn }
                        } else {
                            item.nameEn.ifBlank { item.nameEs }
                        }
                        if (serviceName.isNotBlank()) {
                            serviceCounts[serviceName] = (serviceCounts[serviceName] ?: 0) + item.quantity
                        }
                    }
                }
                serviceCounts.toList().sortedByDescending { it.second }.take(3)
            }
            
            // 2. Calculate booking completion statistics
            val totalBookings = bookings.size
            val completedBookings = bookings.count { it.status == "Completed" }
            val completionRate = if (totalBookings > 0) (completedBookings.toFloat() / totalBookings.toFloat()) else 0f
            
            // 3. Calculate detailed revenue tracking
            var totalOutstanding = 0.0
            var totalCollected = 0.0
            bills.forEach { b ->
                val advance = b.advancePayment
                if (advance > 0.0) {
                    totalCollected += advance
                    totalOutstanding += maxOf(0.0, b.totalAmount - advance)
                } else {
                    totalCollected += b.totalAmount
                }
            }
            val grandTotalBilled = totalCollected + totalOutstanding
            val collectionRate = if (grandTotalBilled > 0.0) (totalCollected / grandTotalBilled).toFloat() else 1f

            // 4. Next upcoming bookings
            val activeUpcomingBookings = remember(bookings) {
                bookings.filter { it.status == "Pending" || it.status == "Confirmed" }
                    .sortedBy { it.date }
                    .take(2)
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, StudioLightBorder),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Dashboard Title
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = "Analytics",
                            tint = StudioPurple,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = if (isEs) "Tablero de Rendimiento" else "Performance Dashboard",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = StudioDarkPurple
                        )
                    }

                    // Section: Booking Progress Tracker
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isEs) "Sesiones Completadas" else "Session Completion Rate",
                                fontSize = 11.sp,
                                color = StudioSlate,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "$completedBookings / $totalBookings (${String.format(Locale.US, "%.0f", completionRate * 100)}%)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = StudioPurple
                            )
                        }
                        
                        // Custom Linear Progress bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(StudioLightBorder)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(fraction = maxOf(0.01f, completionRate))
                                    .background(StudioPurple)
                            )
                        }
                    }

                    Divider(color = StudioLightBorder.copy(alpha = 0.5f))

                    // Section: Detailed Revenue Collection & Outstanding Balance Tracker
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isEs) "Estructura de Cobros" else "Revenue Collections Breakdown",
                                fontSize = 11.sp,
                                color = StudioSlate,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "${String.format(Locale.US, "%.0f", collectionRate * 100)}% " + (if (isEs) "Cobrado" else "Collected"),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = StudioGreen
                            )
                        }

                        // Split Progress Bar: Green for Collected, Red/Orange for Outstanding
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(StudioLightBorder)
                        ) {
                            Row(modifier = Modifier.fillMaxSize()) {
                                if (grandTotalBilled > 0.0) {
                                    val collectedFraction = (totalCollected / grandTotalBilled).toFloat()
                                    val outstandingFraction = (totalOutstanding / grandTotalBilled).toFloat()
                                    
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(maxOf(0.01f, collectedFraction))
                                            .background(StudioGreen)
                                    )
                                    if (outstandingFraction > 0f) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .weight(maxOf(0.01f, outstandingFraction))
                                                .background(Color(0xFFE57373))
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth()
                                            .background(StudioLightBorder)
                                    )
                                }
                            }
                        }

                        // Collection Details Label
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(StudioGreen))
                                Text(
                                    text = (if (isEs) "Recaudado: " else "Collected: ") + "$${String.format(Locale.US, "%.0f", totalCollected)}",
                                    fontSize = 10.sp,
                                    color = StudioSlate
                                )
                            }
                            if (totalOutstanding > 0.0) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFE57373)))
                                    Text(
                                        text = (if (isEs) "Pendiente: " else "Outstanding: ") + "$${String.format(Locale.US, "%.0f", totalOutstanding)}",
                                        fontSize = 10.sp,
                                        color = StudioSlate
                                    )
                                }
                            }
                        }
                    }

                    Divider(color = StudioLightBorder.copy(alpha = 0.5f))

                    // Section: Popular Services (Top Sellers)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = if (isEs) "Servicios más Solicitados" else "Top Performance Services",
                            fontSize = 11.sp,
                            color = StudioSlate,
                            fontWeight = FontWeight.Bold
                        )
                        
                        if (popularServices.isEmpty()) {
                            Text(
                                text = if (isEs) "Sin registros de facturación aún." else "No sales transactions recorded yet.",
                                fontSize = 11.sp,
                                color = StudioSlate,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        } else {
                            val maxCount = popularServices.maxOf { it.second }.toFloat()
                            popularServices.forEach { (name, count) ->
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(name, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = StudioDarkPurple)
                                        Text("$count ${if (isEs) "ventas" else "sold"}", fontSize = 10.sp, color = StudioSlate, fontWeight = FontWeight.Bold)
                                    }
                                    // Soft violet bar
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(5.dp)
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(StudioLightBorder.copy(alpha = 0.5f))
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth(fraction = count / maxCount)
                                                .background(StudioPurple.copy(alpha = 0.7f))
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Section: Next Upcoming Shoots
                    if (activeUpcomingBookings.isNotEmpty()) {
                        Divider(color = StudioLightBorder.copy(alpha = 0.5f))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isEs) "Próximas Sesiones Agendadas" else "Next Upcoming Shoots",
                                    fontSize = 11.sp,
                                    color = StudioSlate,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${activeUpcomingBookings.size} total",
                                    fontSize = 10.sp,
                                    color = StudioPurple,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            activeUpcomingBookings.forEach { booking ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(StudioCream.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                                        .border(0.5.dp, StudioLightBorder, RoundedCornerShape(10.dp))
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(booking.customerName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = StudioDarkPurple)
                                        Text(booking.packageName, fontSize = 10.sp, color = StudioSlate)
                                        Text(
                                            text = "${booking.date} @ ${booking.time}",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = StudioPurple,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }
                                    
                                    // Small status badge
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(
                                                if (booking.status == "Confirmed") Color(0xFFCFF4FC) else Color(0xFFFFF3CD)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            booking.status.uppercase(),
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (booking.status == "Confirmed") Color(0xFF055160) else Color(0xFF664D03)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Customer List Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Loc.get("registered_list", lang),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = StudioDarkPurple
                )
                
                Button(
                    onClick = { showRegisterDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Customer", modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (lang == "ES") "Nuevo" else "New Client", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            if (showRegisterDialog) {
                var nameInput by remember { mutableStateOf("") }
                var phoneInput by remember { mutableStateOf("") }
                var emailInput by remember { mutableStateOf("") }
                var preferredLang by remember { mutableStateOf(lang) }
                AlertDialog(
                    onDismissRequest = { showRegisterDialog = false },
                    title = { Text(Loc.get("register_title", lang), color = StudioDarkPurple, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedTextField(
                                value = nameInput,
                                onValueChange = { nameInput = it },
                                label = { Text(Loc.get("name", lang)) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = customTextFieldColors()
                            )
                            OutlinedTextField(
                                value = phoneInput,
                                onValueChange = { phoneInput = it },
                                label = { Text(Loc.get("phone", lang)) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = customTextFieldColors()
                            )
                            OutlinedTextField(
                                value = emailInput,
                                onValueChange = { emailInput = it },
                                label = { Text(Loc.get("email", lang)) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = customTextFieldColors()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(Loc.get("language", lang), fontSize = 13.sp, color = StudioSlate)
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(StudioLightBorder)
                                        .padding(2.dp)
                                ) {
                                    Button(
                                        onClick = { preferredLang = "EN" },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (preferredLang == "EN") Color.White else Color.Transparent,
                                            contentColor = if (preferredLang == "EN") StudioPurple else StudioSlate
                                        ),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        modifier = Modifier.height(28.dp),
                                        elevation = null
                                    ) {
                                        Text("EN", fontSize = 10.sp)
                                    }
                                    Button(
                                        onClick = { preferredLang = "ES" },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (preferredLang == "ES") Color.White else Color.Transparent,
                                            contentColor = if (preferredLang == "ES") StudioPurple else StudioSlate
                                        ),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        modifier = Modifier.height(28.dp),
                                        elevation = null
                                    ) {
                                        Text("ES", fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (nameInput.isNotBlank()) {
                                    viewModel.registerCustomer(nameInput, phoneInput, emailInput, preferredLang)
                                    Toast.makeText(context, Loc.get("client_added", lang), Toast.LENGTH_SHORT).show()
                                    showRegisterDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = StudioPurple)
                        ) {
                            Text(if (lang == "ES") "Registrar" else "Register", fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { showRegisterDialog = false }) {
                            Text(if (lang == "ES") "Cancelar" else "Cancel")
                        }
                    }
                )
            }
        }

        // Clients list
        if (customers.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(Loc.get("no_clients", lang), color = StudioSlate, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(customers) { customer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onViewCustomerProfile(customer) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(StudioLightPurple),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    customer.name.take(1).uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    color = StudioDarkPurple
                                )
                            }
                            Column {
                                Text(customer.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                if (customer.phone.isNotBlank()) {
                                    Text(customer.phone, fontSize = 11.sp, color = StudioSlate)
                                }
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Quick Action: load into POS
                            IconButton(
                                onClick = {
                                    viewModel.setPosCustomer(customer.name)
                                    Toast.makeText(context, "${Loc.get("active_client", lang)}: ${customer.name}", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "POS",
                                    tint = StudioPurple,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            // Delete
                            IconButton(
                                onClick = {
                                    viewModel.deleteCustomer(customer.id)
                                    Toast.makeText(context, Loc.get("delete_client_confirm", lang), Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red.copy(alpha = 0.7f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingsScreen(viewModel: StudioViewModel, lang: String) {
    val bookings by viewModel.bookings.collectAsState()
    val customers by viewModel.customers.collectAsState()
    val context = LocalContext.current

    var selectedCustomerName by remember { mutableStateOf("") }
    var selectedPackage by remember { mutableStateOf(studioPackages.first()) }
    var customPriceInput by remember { mutableStateOf(studioPackages.first().defaultPrice.toString()) }
    var bookingDate by remember { mutableStateOf("") }
    var bookingTime by remember { mutableStateOf("") }

    // Auto update price when package changes
    LaunchedEffect(selectedPackage) {
        customPriceInput = selectedPackage.defaultPrice.toString()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Book Session Form
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, StudioLightBorder),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(Loc.get("book_title", lang), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = StudioDarkPurple)

                    // Client Selector (Horizontal scroll list of chips or free text input)
                    Text(Loc.get("select_client", lang), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = StudioSlate)
                    
                    if (customers.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            customers.forEach { c ->
                                val isSelected = selectedCustomerName == c.name
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedCustomerName = c.name },
                                    label = { Text(c.name, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = StudioLightPurple,
                                        selectedLabelColor = StudioDarkPurple
                                    )
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = selectedCustomerName,
                        onValueChange = { selectedCustomerName = it },
                        label = { Text(if (customers.isEmpty()) Loc.get("name", lang) else Loc.get("or_enter_custom", lang)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )

                    // Package Selection
                    Text(Loc.get("pkg_select", lang), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = StudioSlate)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        studioPackages.forEach { pkg ->
                            val isSelected = selectedPackage == pkg
                            val displayName = if (lang == "ES") pkg.nameEs else pkg.nameEn
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedPackage = pkg },
                                label = { Text(displayName, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = StudioLightPurple,
                                    selectedLabelColor = StudioDarkPurple
                                )
                            )
                        }
                    }

                    // Price and Date/Time row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = customPriceInput,
                            onValueChange = { customPriceInput = it },
                            label = { Text(Loc.get("price", lang)) },
                            modifier = Modifier.weight(1f),
                            colors = customTextFieldColors()
                        )
                        OutlinedTextField(
                            value = bookingDate,
                            onValueChange = { bookingDate = it },
                            label = { Text(Loc.get("date", lang)) },
                            modifier = Modifier.weight(1.5f),
                            placeholder = { Text("YYYY-MM-DD") },
                            colors = customTextFieldColors()
                        )
                    }

                    OutlinedTextField(
                        value = bookingTime,
                        onValueChange = { bookingTime = it },
                        label = { Text(Loc.get("time", lang)) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("HH:MM") },
                        colors = customTextFieldColors()
                    )

                    Button(
                        onClick = {
                            if (selectedCustomerName.isNotBlank() && bookingDate.isNotBlank()) {
                                val price = customPriceInput.toDoubleOrNull() ?: selectedPackage.defaultPrice
                                val displayName = if (lang == "ES") selectedPackage.nameEs else selectedPackage.nameEn
                                viewModel.bookPackage(selectedCustomerName, displayName, price, bookingDate, bookingTime)
                                Toast.makeText(context, Loc.get("booking_added", lang), Toast.LENGTH_SHORT).show()
                                selectedCustomerName = ""
                                bookingDate = ""
                                bookingTime = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Book")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(Loc.get("btn_book", lang), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // List Bookings Header
        item {
            Text(
                text = Loc.get("upcoming", lang),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = StudioDarkPurple,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (bookings.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(Loc.get("no_bookings", lang), color = StudioSlate, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(bookings) { booking ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(booking.customerName, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                Text(booking.packageName, fontSize = 12.sp, color = StudioSlate)
                            }
                            // Interactive Status Badge cycling on click
                            val badgeColor = when (booking.status) {
                                "Completed" -> Color(0xFFD1E7DD)
                                "Confirmed" -> Color(0xFFCFF4FC)
                                else -> Color(0xFFFFF3CD)
                            }
                            val badgeTextColor = when (booking.status) {
                                "Completed" -> Color(0xFF0F5132)
                                "Confirmed" -> Color(0xFF055160)
                                else -> Color(0xFF664D03)
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(badgeColor)
                                    .clickable {
                                        val nextStatus = when (booking.status) {
                                            "Pending" -> "Confirmed"
                                            "Confirmed" -> "Completed"
                                            else -> "Pending"
                                        }
                                        viewModel.updateBookingStatus(booking.id, nextStatus)
                                    }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    booking.status.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = badgeTextColor
                                )
                            }
                        }

                        Divider(color = StudioLightBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Schedule, contentDescription = "Time", tint = StudioSlate, modifier = Modifier.size(14.dp))
                                Text("${booking.date} • ${booking.time}", fontSize = 11.sp, color = StudioSlate)
                            }
                            Text(
                                "$${String.format(Locale.US, "%.2f", booking.price)}",
                                fontWeight = FontWeight.Bold,
                                color = StudioPurple,
                                fontSize = 14.sp
                            )
                        }

                        // Bottom action buttons inside booking card
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Load to POS
                            TextButton(
                                onClick = {
                                    // Set client name & add package directly to POS!
                                    viewModel.setPosCustomer(booking.customerName)
                                    viewModel.addPosItem(
                                        BillItem(
                                            nameEn = booking.packageName,
                                            nameEs = booking.packageName,
                                            quantity = 1,
                                            unitPrice = booking.price
                                        )
                                    )
                                    Toast.makeText(context, "${booking.packageName} added to POS Cart", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = StudioPurple)
                            ) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Add to POS", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("POS Cart", fontSize = 12.sp)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = {
                                    viewModel.deleteBooking(booking.id)
                                    Toast.makeText(context, Loc.get("booking_deleted", lang), Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red.copy(alpha = 0.6f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PosScreen(viewModel: StudioViewModel, lang: String) {
    val customers by viewModel.customers.collectAsState()
    val posCustomerName by viewModel.posCustomerName.collectAsState()
    val posItems by viewModel.posItems.collectAsState()
    val services by viewModel.services.collectAsState()
    val context = LocalContext.current

    var customItemName by remember { mutableStateOf("") }
    var customItemPrice by remember { mutableStateOf("") }
    var discountInput by remember { mutableStateOf("") }
    var advanceInput by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Customer Assignment
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, StudioLightBorder),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(Loc.get("select_client", lang), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = StudioDarkPurple)
                    
                    if (customers.isNotEmpty()) {
                        Text(Loc.get("select_registered_client", lang), fontSize = 11.sp, color = StudioSlate)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            customers.forEach { c ->
                                val isSelected = posCustomerName == c.name
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setPosCustomer(c.name) },
                                    label = { Text(c.name, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = StudioLightPurple,
                                        selectedLabelColor = StudioDarkPurple
                                    )
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = posCustomerName,
                        onValueChange = { viewModel.setPosCustomer(it) },
                        label = { Text(Loc.get("name", lang)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )
                }
            }
        }

        // Popular Packages Quick Addition
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, StudioLightBorder),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(Loc.get("popular_packages", lang), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = StudioDarkPurple)
                    
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        services.forEach { pkg ->
                            val displayName = if (lang == "ES") pkg.nameEs else pkg.nameEn
                            Button(
                                onClick = {
                                    viewModel.addPosItem(
                                        BillItem(
                                            nameEn = pkg.nameEn,
                                            nameEs = pkg.nameEs,
                                            quantity = 1,
                                            unitPrice = pkg.defaultPrice
                                        )
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = StudioLightBorder,
                                    contentColor = StudioDarkPurple
                                ),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(36.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("$displayName ($${pkg.defaultPrice})", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // Custom Item addition
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, StudioLightBorder),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(Loc.get("custom_item", lang), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = StudioDarkPurple)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = customItemName,
                            onValueChange = { customItemName = it },
                            label = { Text(Loc.get("custom_item", lang)) },
                            modifier = Modifier.weight(1.5f),
                            colors = customTextFieldColors()
                        )
                        OutlinedTextField(
                            value = customItemPrice,
                            onValueChange = { customItemPrice = it },
                            label = { Text(Loc.get("price", lang)) },
                            modifier = Modifier.weight(1f),
                            colors = customTextFieldColors()
                        )
                    }
                    Button(
                        onClick = {
                            if (customItemName.isNotBlank() && customItemPrice.isNotBlank()) {
                                val price = customItemPrice.toDoubleOrNull() ?: 0.0
                                viewModel.addPosItem(
                                    BillItem(
                                        nameEn = customItemName,
                                        nameEs = customItemName,
                                        quantity = 1,
                                        unitPrice = price
                                    )
                                )
                                customItemName = ""
                                customItemPrice = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(Loc.get("btn_add_custom", lang), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Cart Section
        item {
            Text(
                text = Loc.get("cart", lang),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = StudioDarkPurple,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (posItems.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(Loc.get("empty_cart", lang), color = StudioSlate, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(posItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            val name = if (lang == "ES") item.nameEs else item.nameEn
                            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text("$${String.format(Locale.US, "%.2f", item.unitPrice)} x ${item.quantity}", fontSize = 12.sp, color = StudioSlate)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            IconButton(
                                onClick = { viewModel.removePosItem(item) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = StudioPurple)
                            }
                            Text("${item.quantity}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            IconButton(
                                onClick = {
                                    viewModel.addPosItem(item.copy(quantity = 1))
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", tint = StudioPurple)
                            }
                        }
                    }
                }
            }

            // POS Invoice totals & trigger
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = StudioLightPurple),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val isEs = lang == "ES"
                        val subtotal = posItems.sumOf { it.quantity * it.unitPrice }
                        val discount = discountInput.toDoubleOrNull() ?: 0.0
                        val advance = advanceInput.toDoubleOrNull() ?: 0.0
                        val finalTotal = maxOf(0.0, subtotal - discount)
                        val balanceDue = maxOf(0.0, finalTotal - advance)

                        // Subtotal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isEs) "Subtotal:" else "Subtotal:",
                                fontWeight = FontWeight.SemiBold,
                                color = StudioDarkPurple,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", subtotal)}",
                                fontWeight = FontWeight.SemiBold,
                                color = StudioDarkPurple,
                                fontSize = 15.sp
                            )
                        }

                        // Discount & Advance Input Box
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = discountInput,
                                    onValueChange = { discountInput = it },
                                    label = { Text(if (isEs) "Descuento ($)" else "Discount ($)") },
                                    modifier = Modifier.weight(1f),
                                    colors = customTextFieldColors()
                                )
                                OutlinedTextField(
                                    value = advanceInput,
                                    onValueChange = { advanceInput = it },
                                    label = { Text(if (isEs) "Anticipo ($)" else "Advance ($)") },
                                    modifier = Modifier.weight(1f),
                                    colors = customTextFieldColors()
                                )
                            }
                        }

                        Divider(color = StudioBorder.copy(alpha = 0.3f))

                        // Final Total
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = Loc.get("pos_total", lang),
                                fontWeight = FontWeight.Bold,
                                color = StudioDarkPurple,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", finalTotal)}",
                                fontWeight = FontWeight.Bold,
                                color = StudioPurple,
                                fontSize = 20.sp
                            )
                        }

                        // Balance Due (if advance is present)
                        if (advance > 0.0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isEs) "Saldo Pendiente:" else "Balance Due:",
                                    fontWeight = FontWeight.Bold,
                                    color = StudioPurple,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", balanceDue)}",
                                    fontWeight = FontWeight.Bold,
                                    color = StudioPurple,
                                    fontSize = 22.sp
                                )
                            }
                        }

                        Button(
                            onClick = {
                                val d = discountInput.toDoubleOrNull() ?: 0.0
                                val a = advanceInput.toDoubleOrNull() ?: 0.0
                                viewModel.saveBillAndPrint(context, discount = d, advance = a)
                                Toast.makeText(context, Loc.get("print_saved_success", lang), Toast.LENGTH_LONG).show()
                                discountInput = ""
                                advanceInput = ""
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(50)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Print & Save")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(Loc.get("btn_print_save", lang), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(viewModel: StudioViewModel, lang: String, onViewBillReceipt: (Bill) -> Unit) {
    val bills by viewModel.bills.collectAsState()
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    val filteredBills = bills.filter {
        val q = searchQuery.trim().lowercase()
        it.customerName.contains(q, ignoreCase = true) ||
        it.id.toString() == q ||
        (q.startsWith("#") && it.id.toString() == q.drop(1)) ||
        (q.startsWith("inv") && it.id.toString().contains(q.replace("inv", "").replace("#", "").trim()))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search & Filter header
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(Loc.get("search_invoice", lang)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Receipt, contentDescription = "Search") },
                colors = customTextFieldColors()
            )
        }

        if (filteredBills.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(Loc.get("no_history", lang), color = StudioSlate, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(filteredBills) { bill ->
                val items = remember(bill.itemsJson) { JsonUtils.fromJson(bill.itemsJson) }
                val dateStr = remember(bill.createdAt) {
                    val sdf = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
                    sdf.format(Date(bill.createdAt))
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onViewBillReceipt(bill) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "INV #${bill.id}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StudioPurple
                                )
                                Text(
                                    text = bill.customerName,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = StudioDarkPurple
                                )
                            }
                            Text(
                                text = dateStr,
                                fontSize = 11.sp,
                                color = StudioSlate
                            )
                        }

                        Divider(color = StudioLightBorder)

                        // Brief itemized list inside the card
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            items.forEach { item ->
                                val name = if (lang == "ES") item.nameEs else item.nameEn
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "$name x ${item.quantity}",
                                        fontSize = 12.sp,
                                        color = StudioSlate,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "$${String.format(Locale.US, "%.2f", item.quantity * item.unitPrice)}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Divider(color = StudioLightBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Total:",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", bill.totalAmount)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StudioPurple
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // View Copy Button
                                OutlinedButton(
                                    onClick = { onViewBillReceipt(bill) },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StudioPurple),
                                    border = BorderStroke(1.dp, StudioPurple),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp),
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Icon(Icons.Default.Visibility, contentDescription = "View", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (lang == "ES") "Ver" else "View", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                // Re-print button matching Clean Utility Style
                                Button(
                                    onClick = {
                                        viewModel.reprintBill(context, bill)
                                        Toast.makeText(context, "${Loc.get("reprint_success", lang)}${bill.id}", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp),
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Reprint", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(Loc.get("btn_reprint", lang), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// FlowRow layout implementation for composing buttons nicely
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.ui.layout.Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        var x = 0
        var y = 0
        var maxRowHeight = 0
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            if (x + placeable.width > constraints.maxWidth) {
                x = 0
                y += maxRowHeight + verticalArrangement.let { 8.dp.roundToPx() }
                maxRowHeight = 0
            }
            x += placeable.width + horizontalArrangement.let { 8.dp.roundToPx() }
            maxRowHeight = maxOf(maxRowHeight, placeable.height)
            placeable
        }

        var currentX = 0
        var currentY = 0
        var currentRowHeight = 0
        layout(
            width = constraints.maxWidth,
            height = maxOf(y + maxRowHeight, 0)
        ) {
            placeables.forEach { placeable ->
                if (currentX + placeable.width > constraints.maxWidth) {
                    currentX = 0
                    currentY += currentRowHeight + verticalArrangement.let { 8.dp.roundToPx() }
                    currentRowHeight = 0
                }
                placeable.placeRelative(currentX, currentY)
                currentX += placeable.width + horizontalArrangement.let { 8.dp.roundToPx() }
                currentRowHeight = maxOf(currentRowHeight, placeable.height)
            }
        }
    }
}

@Composable
fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = StudioPurple,
    unfocusedBorderColor = StudioBorder,
    focusedLabelColor = StudioPurple,
    unfocusedLabelColor = StudioSlate,
    cursorColor = StudioPurple
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerProfileDialog(
    customer: Customer,
    bookings: List<Booking>,
    bills: List<Bill>,
    lang: String,
    onDismiss: () -> Unit,
    onReprintBill: (Bill) -> Unit,
    onViewBill: (Bill) -> Unit
) {
    val customerBookings = remember(customer.name, bookings) {
        bookings.filter { it.customerName.equals(customer.name, ignoreCase = true) }
    }
    val customerBills = remember(customer.name, bills) {
        bills.filter { it.customerName.equals(customer.name, ignoreCase = true) }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(vertical = 24.dp),
        content = {
            Card(
                colors = CardDefaults.cardColors(containerColor = StudioCream),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, StudioLightBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Title and Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(StudioPurple),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = customer.name.take(1).uppercase(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                            Column {
                                Text(
                                    text = customer.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StudioDarkPurple
                                )
                                Text(
                                    text = Loc.get("profile_title", lang),
                                    fontSize = 12.sp,
                                    color = StudioSlate
                                )
                            }
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = StudioSlate)
                        }
                    }

                    Divider(color = StudioLightBorder)

                    // Contact Info
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = Loc.get("contact_info", lang),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = StudioPurple
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Phone", tint = StudioSlate, modifier = Modifier.size(16.dp))
                            Text(
                                text = customer.phone.ifBlank { "-" },
                                fontSize = 13.sp,
                                color = StudioDarkPurple
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Email, contentDescription = "Email", tint = StudioSlate, modifier = Modifier.size(16.dp))
                            Text(
                                text = customer.email.ifBlank { "-" },
                                fontSize = 13.sp,
                                color = StudioDarkPurple
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Language, contentDescription = "Language", tint = StudioSlate, modifier = Modifier.size(16.dp))
                            Text(
                                text = "${Loc.get("language", lang)}: ${customer.language}",
                                fontSize = 13.sp,
                                color = StudioDarkPurple
                            )
                        }
                    }

                    Divider(color = StudioLightBorder)

                    // Historical Bookings & Bills in a dual-column or simple list
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .heightIn(max = 240.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        item {
                            Text(
                                text = "${Loc.get("booking_history", lang)} (${customerBookings.size})",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = StudioDarkPurple,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        if (customerBookings.isEmpty()) {
                            item {
                                Text(
                                    text = Loc.get("no_bookings_client", lang),
                                    fontSize = 12.sp,
                                    color = StudioSlate,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        } else {
                            items(customerBookings) { booking ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    border = BorderStroke(1.dp, StudioLightBorder),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(booking.packageName, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                            Text("${booking.date} • ${booking.time}", fontSize = 11.sp, color = StudioSlate)
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(
                                                text = "$${String.format(Locale.US, "%.2f", booking.price)}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = StudioPurple
                                            )
                                            val badgeColor = when (booking.status) {
                                                "Completed" -> Color(0xFFD1E7DD)
                                                "Confirmed" -> Color(0xFFCFF4FC)
                                                else -> Color(0xFFFFF3CD)
                                            }
                                            val badgeTextColor = when (booking.status) {
                                                "Completed" -> Color(0xFF0F5132)
                                                "Confirmed" -> Color(0xFF055160)
                                                else -> Color(0xFF664D03)
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(badgeColor)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(booking.status.uppercase(), fontSize = 8.sp, fontWeight = FontWeight.Bold, color = badgeTextColor)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${Loc.get("billing_history", lang)} (${customerBills.size})",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = StudioDarkPurple,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        if (customerBills.isEmpty()) {
                            item {
                                Text(
                                    text = Loc.get("no_bills_client", lang),
                                    fontSize = 12.sp,
                                    color = StudioSlate,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        } else {
                            items(customerBills) { bill ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    border = BorderStroke(1.dp, StudioLightBorder),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text("INV #${bill.id}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = StudioPurple)
                                            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                                            Text(sdf.format(Date(bill.createdAt)), fontSize = 11.sp, color = StudioSlate)
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(
                                                text = "$${String.format(Locale.US, "%.2f", bill.totalAmount)}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = StudioGreen
                                            )
                                            IconButton(
                                                onClick = { onViewBill(bill) },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(Icons.Default.Visibility, contentDescription = "View Copy", tint = StudioPurple, modifier = Modifier.size(16.dp))
                                            }
                                            IconButton(
                                                onClick = { onReprintBill(bill) },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(Icons.Default.Refresh, contentDescription = "Reprint Copy", tint = StudioPurple, modifier = Modifier.size(16.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(Loc.get("close", lang), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillReceiptDialog(
    bill: Bill,
    lang: String,
    onDismiss: () -> Unit,
    onReprint: () -> Unit
) {
    val items = remember(bill.itemsJson) { JsonUtils.fromJson(bill.itemsJson) }
    val isEs = lang == "ES"
    val sdf = remember(bill.createdAt) {
        SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
    }
    val dateStr = sdf.format(Date(bill.createdAt))

    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("studio_prefs", Context.MODE_PRIVATE) }
    val compName = remember { prefs.getString("comp_name", "360 GRAPHY") ?: "360 GRAPHY" }
    val compSlogan = remember { prefs.getString("comp_slogan", "IMMERSIVE VISUALS") ?: "IMMERSIVE VISUALS" }
    val compQuote = remember { prefs.getString("comp_quote", if (isEs) "Capturando momentos hoy, creando recuerdos para toda la vida." else "Capturing moments today, creating memories for a lifetime.") ?: "" }

    val subtotal = items.sumOf { it.quantity * it.unitPrice }
    val discount = bill.discountAmount
    val totalAfterDiscount = maxOf(0.0, subtotal - discount)
    val advance = bill.advancePayment
    val balanceDue = maxOf(0.0, totalAfterDiscount - advance)

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.90f)
            .padding(16.dp),
        content = {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, StudioBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Header with a visual ticket rip style
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = Loc.get("view_invoice", lang),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = StudioSlate
                            )
                            IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = StudioSlate)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(StudioPurple),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("360", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Text(compName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = StudioDarkPurple)
                        if (compSlogan.isNotBlank()) {
                            Text(compSlogan, fontSize = 11.sp, color = StudioSlate)
                        }
                    }

                    // Ticket separator line (dashed visual)
                    DashedDivider()

                    // Invoice Metadata
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Invoice ID / No:", fontSize = 12.sp, color = StudioSlate)
                            Text("#${bill.id}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = StudioPurple)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Date / Fecha:", fontSize = 12.sp, color = StudioSlate)
                            Text(dateStr, fontSize = 12.sp, color = StudioDarkPurple)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Customer / Cliente:", fontSize = 12.sp, color = StudioSlate)
                            Text(bill.customerName, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = StudioDarkPurple)
                        }
                    }

                    DashedDivider()

                    // Itemized list of bill
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 160.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items.forEach { item ->
                            val displayName = if (isEs) item.nameEs else item.nameEn
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(displayName, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = StudioDarkPurple)
                                    Text("$${String.format(Locale.US, "%.2f", item.unitPrice)} x ${item.quantity}", fontSize = 11.sp, color = StudioSlate)
                                }
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", item.quantity * item.unitPrice)}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = StudioDarkPurple
                                )
                            }
                        }
                    }

                    DashedDivider()

                    // Totals Section breakdown
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(if (isEs) "Subtotal:" else "Subtotal:", fontSize = 12.sp, color = StudioSlate)
                            Text("$${String.format(Locale.US, "%.2f", subtotal)}", fontSize = 12.sp, color = StudioDarkPurple)
                        }
                        if (discount > 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(if (isEs) "Descuento:" else "Discount:", fontSize = 12.sp, color = Color(0xFFB3261E))
                                Text("-$${String.format(Locale.US, "%.2f", discount)}", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFFB3261E))
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(if (isEs) "Total Facturado:" else "Total Billed:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = StudioDarkPurple)
                            Text("$${String.format(Locale.US, "%.2f", totalAfterDiscount)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = StudioDarkPurple)
                        }
                        if (advance > 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(if (isEs) "Anticipo / Adelanto:" else "Advance Payment:", fontSize = 12.sp, color = StudioSlate)
                                Text("-$${String.format(Locale.US, "%.2f", advance)}", fontSize = 12.sp, color = StudioPurple)
                            }
                        }
                    }

                    DashedDivider()

                    // Grand total / Balance Due
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (advance > 0) {
                                if (isEs) "SALDO PENDIENTE" else "BALANCE DUE"
                            } else {
                                if (isEs) "TOTAL A PAGAR" else "TOTAL AMOUNT"
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = StudioDarkPurple
                        )
                        Text(
                            text = if (advance > 0) {
                                "$${String.format(Locale.US, "%.2f", balanceDue)}"
                            } else {
                                "$${String.format(Locale.US, "%.2f", totalAfterDiscount)}"
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = StudioPurple
                        )
                    }

                    // Quote Footer
                    if (compQuote.isNotBlank()) {
                        Text(
                            text = "\"$compQuote\"",
                            fontSize = 11.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = StudioPurple,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Dialog Actions (Reprint & Close)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = StudioSlate),
                            border = BorderStroke(1.dp, StudioBorder),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(Loc.get("close", lang), fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                onReprint()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(50)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reprint Copy", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(Loc.get("btn_reprint", lang), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DashedDivider() {
    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        val pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        drawLine(
            color = StudioBorder,
            start = androidx.compose.ui.geometry.Offset(0f, 0f),
            end = androidx.compose.ui.geometry.Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = 2f
        )
    }
}

// Beautiful and robust Settings & Service Catalog Management Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: StudioViewModel, lang: String) {
    val context = LocalContext.current
    val services by viewModel.services.collectAsState()
    val isEs = lang == "ES"

    val prefs = remember { context.getSharedPreferences("studio_prefs", Context.MODE_PRIVATE) }
    var compName by remember { mutableStateOf(prefs.getString("comp_name", "360 GRAPHY") ?: "360 GRAPHY") }
    var compSlogan by remember { mutableStateOf(prefs.getString("comp_slogan", "IMMERSIVE VISUALS") ?: "IMMERSIVE VISUALS") }
    var compPhone by remember { mutableStateOf(prefs.getString("comp_phone", "+1 (360) 555-0199") ?: "+1 (360) 555-0199") }
    var compEmail by remember { mutableStateOf(prefs.getString("comp_email", "hello@360graphy.com") ?: "hello@360graphy.com") }
    var compAddress by remember { mutableStateOf(prefs.getString("comp_address", "100 Creative Studio Lane, New York, NY") ?: "100 Creative Studio Lane, New York, NY") }
    var compWebsite by remember { mutableStateOf(prefs.getString("comp_website", "www.360graphy.com") ?: "www.360graphy.com") }
    var compTaxId by remember { mutableStateOf(prefs.getString("comp_tax_id", "TX-360-GRAPH") ?: "TX-360-GRAPH") }
    var compQuote by remember { mutableStateOf(prefs.getString("comp_quote", if (isEs) "Capturando momentos hoy, creando recuerdos para toda la vida." else "Capturing moments today, creating memories for a lifetime.") ?: "Capturing moments today, creating memories for a lifetime.") }

    var showAddDialog by remember { mutableStateOf(false) }
    var editingService by remember { mutableStateOf<CatalogService?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section: Company Details
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, StudioLightBorder),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (isEs) "Detalles de la Empresa" else "Company Settings",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = StudioDarkPurple
                    )
                    Text(
                        text = if (isEs) "Esta información aparecerá impresa en todos los recibos." else "This information will be printed on all invoices and receipts.",
                        fontSize = 11.sp,
                        color = StudioSlate
                    )

                    OutlinedTextField(
                        value = compName,
                        onValueChange = { compName = it },
                        label = { Text(if (isEs) "Nombre de la Empresa" else "Company Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )

                    OutlinedTextField(
                        value = compSlogan,
                        onValueChange = { compSlogan = it },
                        label = { Text(if (isEs) "Eslogan / Subtítulo" else "Slogan / Tagline") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = compPhone,
                            onValueChange = { compPhone = it },
                            label = { Text(if (isEs) "Teléfono" else "Phone") },
                            modifier = Modifier.weight(1f),
                            colors = customTextFieldColors()
                        )
                        OutlinedTextField(
                            value = compEmail,
                            onValueChange = { compEmail = it },
                            label = { Text("Email") },
                            modifier = Modifier.weight(1.2f),
                            colors = customTextFieldColors()
                        )
                    }

                    OutlinedTextField(
                        value = compAddress,
                        onValueChange = { compAddress = it },
                        label = { Text(if (isEs) "Dirección Física" else "Physical Address") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = compWebsite,
                            onValueChange = { compWebsite = it },
                            label = { Text(if (isEs) "Sitio Web" else "Website") },
                            modifier = Modifier.weight(1f),
                            colors = customTextFieldColors()
                        )
                        OutlinedTextField(
                            value = compTaxId,
                            onValueChange = { compTaxId = it },
                            label = { Text(if (isEs) "Identificación Tributaria (Tax ID)" else "Tax ID / VAT No") },
                            modifier = Modifier.weight(1f),
                            colors = customTextFieldColors()
                        )
                    }

                    OutlinedTextField(
                        value = compQuote,
                        onValueChange = { compQuote = it },
                        label = { Text(if (isEs) "Cita del Estudio (Pie de Recibo)" else "Studio Quote (Footer)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors(),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            prefs.edit().apply {
                                putString("comp_name", compName)
                                putString("comp_slogan", compSlogan)
                                putString("comp_phone", compPhone)
                                putString("comp_email", compEmail)
                                putString("comp_address", compAddress)
                                putString("comp_website", compWebsite)
                                putString("comp_tax_id", compTaxId)
                                putString("comp_quote", compQuote)
                                apply()
                            }
                            Toast.makeText(
                                context,
                                if (isEs) "Detalles de la empresa guardados correctamente" else "Company settings updated successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Save", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isEs) "Guardar Configuración" else "Save Company Details", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Section: Services Catalog Management
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEs) "Catálogo de Servicios" else "Service Catalog",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = StudioDarkPurple
                )
                
                Button(
                    onClick = { showAddDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = StudioPurple),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Service", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isEs) "Nuevo" else "New Service", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (services.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isEs) "No hay servicios registrados en el catálogo." else "No service items registered yet.",
                            color = StudioSlate,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
            items(services) { service ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, StudioLightBorder),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isEs) service.nameEs else service.nameEn,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = StudioDarkPurple
                            )
                            Text(
                                text = "En: ${service.nameEn} | Es: ${service.nameEs}",
                                fontSize = 10.sp,
                                color = StudioSlate
                            )
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", service.defaultPrice)}",
                                fontWeight = FontWeight.Bold,
                                color = StudioPurple,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                        
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            IconButton(onClick = { editingService = service }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = StudioPurple)
                            }
                            IconButton(onClick = {
                                viewModel.deleteService(service.id)
                                Toast.makeText(
                                    context,
                                    if (isEs) "Servicio eliminado" else "Service deleted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFB3261E))
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Service Dialog
    if (showAddDialog) {
        var nameEn by remember { mutableStateOf("") }
        var nameEs by remember { mutableStateOf("") }
        var priceInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(if (isEs) "Agregar Nuevo Servicio" else "Add New Service Item") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = nameEn,
                        onValueChange = { nameEn = it },
                        label = { Text("English Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )
                    OutlinedTextField(
                        value = nameEs,
                        onValueChange = { nameEs = it },
                        label = { Text("Nombre en Español") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )
                    OutlinedTextField(
                        value = priceInput,
                        onValueChange = { priceInput = it },
                        label = { Text(if (isEs) "Precio ($)" else "Price ($)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val price = priceInput.toDoubleOrNull() ?: 0.0
                        if (nameEn.isNotBlank() && nameEs.isNotBlank()) {
                            viewModel.addOrUpdateService(
                                nameEn = nameEn,
                                nameEs = nameEs,
                                defaultPrice = price
                            )
                            showAddDialog = false
                            Toast.makeText(context, if (isEs) "Servicio agregado" else "Service added successfully!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StudioPurple)
                ) {
                    Text(if (isEs) "Agregar" else "Add", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showAddDialog = false }) {
                    Text(if (isEs) "Cancelar" else "Cancel")
                }
            }
        )
    }

    // Edit Service Dialog
    editingService?.let { service ->
        var nameEn by remember { mutableStateOf(service.nameEn) }
        var nameEs by remember { mutableStateOf(service.nameEs) }
        var priceInput by remember { mutableStateOf(service.defaultPrice.toString()) }

        AlertDialog(
            onDismissRequest = { editingService = null },
            title = { Text(if (isEs) "Modificar Servicio" else "Modify Service Item") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = nameEn,
                        onValueChange = { nameEn = it },
                        label = { Text("English Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )
                    OutlinedTextField(
                        value = nameEs,
                        onValueChange = { nameEs = it },
                        label = { Text("Nombre en Español") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )
                    OutlinedTextField(
                        value = priceInput,
                        onValueChange = { priceInput = it },
                        label = { Text(if (isEs) "Precio ($)" else "Price ($)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = customTextFieldColors()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val price = priceInput.toDoubleOrNull() ?: 0.0
                        if (nameEn.isNotBlank() && nameEs.isNotBlank()) {
                            viewModel.addOrUpdateService(
                                id = service.id,
                                nameEn = nameEn,
                                nameEs = nameEs,
                                defaultPrice = price
                            )
                            editingService = null
                            Toast.makeText(context, if (isEs) "Servicio actualizado" else "Service updated successfully!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StudioPurple)
                ) {
                    Text(if (isEs) "Actualizar" else "Update", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { editingService = null }) {
                    Text(if (isEs) "Cancelar" else "Cancel")
                }
            }
        )
    }
}
