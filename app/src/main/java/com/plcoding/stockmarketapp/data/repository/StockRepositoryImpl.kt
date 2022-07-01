package com.plcoding.stockmarketapp.data.repository

import com.plcoding.stockmarketapp.data.local.CompanyListingEntity
import com.plcoding.stockmarketapp.data.local.StockDatabase
import com.plcoding.stockmarketapp.data.mapper.toCompanyListing
import com.plcoding.stockmarketapp.data.remote.StockApi
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase
) : StockRepository {
    private val dao = db.dao
    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<List<CompanyListing>> {
        val localListings = dao.seachCompanyListing(query)
        val map = localListings.map { localListing ->
            localListing.toCompanyListing()
        }
        return flow{
            emit(map)
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val loadFromCache = !isDbEmpty && !fetchFromRemote
            if(loadFromCache){
                return@flow
            }

            val remoteListings = api.getListings(StockApi.API_KET).byteStream()
        }
    }
}