package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers ORDER BY name ASC")
    fun getAllCustomers(): Flow<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Query("DELETE FROM customers WHERE id = :id")
    suspend fun deleteCustomer(id: Int)
}

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY date DESC, time DESC")
    fun getAllBookings(): Flow<List<Booking>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Booking)

    @Query("UPDATE bookings SET status = :status WHERE id = :id")
    suspend fun updateBookingStatus(id: Int, status: String)

    @Query("DELETE FROM bookings WHERE id = :id")
    suspend fun deleteBooking(id: Int)
}

@Dao
interface BillDao {
    @Query("SELECT * FROM bills ORDER BY createdAt DESC")
    fun getAllBills(): Flow<List<Bill>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: Bill): Long
}

@Dao
interface CatalogServiceDao {
    @Query("SELECT * FROM catalog_services ORDER BY nameEn ASC")
    fun getAllServices(): Flow<List<CatalogService>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: CatalogService)

    @Query("DELETE FROM catalog_services WHERE id = :id")
    suspend fun deleteService(id: Int)
}

@Database(entities = [Customer::class, Booking::class, Bill::class, CatalogService::class], version = 2, exportSchema = false)
abstract class StudioDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun bookingDao(): BookingDao
    abstract fun billDao(): BillDao
    abstract fun catalogServiceDao(): CatalogServiceDao

    companion object {
        @Volatile
        private var INSTANCE: StudioDatabase? = null

        fun getDatabase(context: Context): StudioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudioDatabase::class.java,
                    "studio_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
