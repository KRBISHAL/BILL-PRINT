package com.example.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StudioViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StudioRepository(StudioDatabase.getDatabase(application))

    val customers: StateFlow<List<Customer>> = repository.allCustomers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val bookings: StateFlow<List<Booking>> = repository.allBookings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val bills: StateFlow<List<Bill>> = repository.allBills.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val services: StateFlow<List<CatalogService>> = repository.allServices.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Pre-populate default services if database catalog is empty
        viewModelScope.launch {
            try {
                val current = repository.allServices.first()
                if (current.isEmpty()) {
                    val defaults = listOf(
                        CatalogService(nameEn = "Wedding Portrait Session", nameEs = "Sesión de Retrato de Bodas", defaultPrice = 250.0),
                        CatalogService(nameEn = "Express Retouch (x5)", nameEs = "Retoque Express (x5)", defaultPrice = 75.0),
                        CatalogService(nameEn = "Studio Rental Session", nameEs = "Alquiler de Estudio", defaultPrice = 100.0),
                        CatalogService(nameEn = "Corporate Headshot", nameEs = "Retrato Corporativo", defaultPrice = 120.0),
                        CatalogService(nameEn = "Outdoor Golden Hour", nameEs = "Exterior en Hora Dorada", defaultPrice = 180.0),
                        CatalogService(nameEn = "Studio Rental Credit", nameEs = "Crédito Alquiler Estudio", defaultPrice = -20.0)
                    )
                    defaults.forEach { repository.insertService(it) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Language state: "EN" or "ES"
    private val _language = MutableStateFlow("EN")
    val language: StateFlow<String> = _language.asStateFlow()

    fun toggleLanguage() {
        _language.value = if (_language.value == "EN") "ES" else "EN"
    }

    // POS Active State
    private val _posCustomerName = MutableStateFlow("")
    val posCustomerName: StateFlow<String> = _posCustomerName.asStateFlow()

    private val _posItems = MutableStateFlow<List<BillItem>>(emptyList())
    val posItems: StateFlow<List<BillItem>> = _posItems.asStateFlow()

    fun setPosCustomer(name: String) {
        _posCustomerName.value = name
    }

    fun addPosItem(item: BillItem) {
        val current = _posItems.value.toMutableList()
        val index = current.indexOfFirst { it.nameEn == item.nameEn }
        if (index >= 0) {
            val existing = current[index]
            current[index] = existing.copy(quantity = existing.quantity + item.quantity)
        } else {
            current.add(item)
        }
        _posItems.value = current
    }

    fun removePosItem(item: BillItem) {
        val current = _posItems.value.toMutableList()
        val index = current.indexOfFirst { it.nameEn == item.nameEn }
        if (index >= 0) {
            val existing = current[index]
            if (existing.quantity > 1) {
                current[index] = existing.copy(quantity = existing.quantity - 1)
            } else {
                current.removeAt(index)
            }
        }
        _posItems.value = current
    }

    fun clearPos() {
        _posCustomerName.value = ""
        _posItems.value = emptyList()
    }

    // DB Operations
    fun registerCustomer(name: String, phone: String, email: String, lang: String) {
        viewModelScope.launch {
            repository.insertCustomer(Customer(name = name, phone = phone, email = email, language = lang))
        }
    }

    fun deleteCustomer(id: Int) {
        viewModelScope.launch {
            repository.deleteCustomer(id)
        }
    }

    fun bookPackage(customerName: String, packageName: String, price: Double, date: String, time: String) {
        viewModelScope.launch {
            repository.insertBooking(
                Booking(
                    customerName = customerName,
                    packageName = packageName,
                    price = price,
                    date = date,
                    time = time,
                    status = "Pending"
                )
            )
        }
    }

    fun updateBookingStatus(id: Int, status: String) {
        viewModelScope.launch {
            repository.updateBookingStatus(id, status)
        }
    }

    fun deleteBooking(id: Int) {
        viewModelScope.launch {
            repository.deleteBooking(id)
        }
    }

    fun saveBillAndPrint(context: Context, discount: Double = 0.0, advance: Double = 0.0) {
        val customerName = _posCustomerName.value.ifBlank { "Walk-in Customer" }
        val items = _posItems.value
        val subtotal = items.sumOf { it.quantity * it.unitPrice }
        val finalTotal = maxOf(0.0, subtotal - discount)
        if (items.isEmpty()) return

        val bill = Bill(
            customerName = customerName,
            language = _language.value,
            itemsJson = JsonUtils.toJson(items),
            totalAmount = finalTotal,
            discountAmount = discount,
            advancePayment = advance
        )

        viewModelScope.launch {
            val generatedId = repository.insertBill(bill)
            val finalBill = bill.copy(id = generatedId.toInt())
            PrintUtils.printBill(context, finalBill)
            clearPos()
        }
    }

    fun reprintBill(context: Context, bill: Bill) {
        PrintUtils.printBill(context, bill)
    }

    fun addOrUpdateService(id: Int = 0, nameEn: String, nameEs: String, defaultPrice: Double) {
        viewModelScope.launch {
            repository.insertService(
                CatalogService(
                    id = id,
                    nameEn = nameEn,
                    nameEs = nameEs,
                    defaultPrice = defaultPrice
                )
            )
        }
    }

    fun deleteService(id: Int) {
        viewModelScope.launch {
            repository.deleteService(id)
        }
    }
}
