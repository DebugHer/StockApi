package com.plcoding.stockmarketapp.data.local

import androidx.room.*

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListing(companyListingEntity: CompanyListingEntity)

    @Delete
    suspend fun clearCompanyListings()

    @Query("""
        SELECT * 
        FROM companylistingentity
        WHERE LOWER(name) LIKE LOWER(:query) OR LOWER(symbol) LIKE LOWER(:query)
    """)
    suspend fun seachCompanyListing(query: String) : List<CompanyListingEntity>
}