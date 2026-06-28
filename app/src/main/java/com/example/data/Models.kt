package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String,
    val email: String,
    val language: String // "EN" or "ES"
)

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerName: String,
    val packageName: String,
    val price: Double,
    val date: String,
    val time: String,
    val status: String // "Pending", "Confirmed", "Completed"
)

@Entity(tableName = "bills")
data class Bill(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val customerName: String,
    val language: String, // "EN" or "ES"
    val itemsJson: String, // JSON serialization of List<BillItem>
    val totalAmount: Double,
    val discountAmount: Double = 0.0,
    val advancePayment: Double = 0.0
)

@Entity(tableName = "catalog_services")
data class CatalogService(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nameEn: String,
    val nameEs: String,
    val defaultPrice: Double
)

data class BillItem(
    val nameEn: String,
    val nameEs: String,
    val quantity: Int,
    val unitPrice: Double
)
