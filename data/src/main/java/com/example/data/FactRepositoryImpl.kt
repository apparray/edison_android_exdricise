package com.example.data

import android.content.Context
import com.example.data.datastore.UserStore
import com.example.data.repository.dataSource.FactRemoteDataSource
import com.example.domain.model.Fact
import com.example.domain.repository.FactRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class FactRepositoryImpl (
    private val factRemoteDataSource: FactRemoteDataSource,
    private val context: Context
): FactRepository {
    override suspend fun getFacts() = responseToRequest(factRemoteDataSource.getDataSourceFacts())

    private fun responseToRequest(response: Response<Fact>): Resource<Fact> {
        if(response.isSuccessful){
            response.body()?.let {result->
                val userStore = UserStore(context = context)
                runBlocking {
                    userStore.saveDetails(result)
                }

                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}