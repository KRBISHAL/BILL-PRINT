package com.example.data

import kotlinx.coroutines.flow.Flow

class StudioRepository(private val database: StudioDatabase) {
    val allCustomers: Flow<List<Customer>> = database.customerDao().getAllCustomers()
    val allBookings: Flow<List<Booking>> = database.bookingDao().getAllBookings()
    val allBills: Flow<List<Bill>> = database.billDao().getAllBills()
    val allServices: Flow<List<CatalogService>> = database.catalogServiceDao().getAllServices()

    suspend fun insertCustomer(customer: Customer) {
        database.customerDao().insertCustomer(customer)
    }

    suspend fun deleteCustomer(id: Int) {
        database.customerDao().deleteCustomer(id)
    }

    suspend fun insertBooking(booking: Booking) {
        database.bookingDao().insertBooking(booking)
    }

    suspend fun updateBookingStatus(id: Int, status: String) {
        database.bookingDao().updateBookingStatus(id, status)
    }

    suspend fun deleteBooking(id: Int) {
        database.bookingDao().deleteBooking(id)
    }

    suspend fun insertBill(bill: Bill): Long {
        return database.billDao().insertBill(bill)
    }

    suspend fun insertService(service: CatalogService) {
        database.catalogServiceDao().insertService(service)
    }

    suspend fun deleteService(id: Int) {
        database.catalogServiceDao().deleteService(id)
    }
}
