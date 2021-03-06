package com.dsm.restaurant.data.dataSource

import com.dsm.restaurant.data.error.ErrorHandler
import com.dsm.restaurant.data.remote.ThikiraApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AccountDataSource {

    suspend fun register(body: Any)

    suspend fun unregister()

    suspend fun changePassword(newPassword: String)
}

class AccountDataSourceImpl(
    private val thikiraApi: ThikiraApi,
    private val errorHandler: ErrorHandler,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AccountDataSource {

    override suspend fun register(body: Any) = withContext(ioDispatcher) {
        try {
            thikiraApi.register(body)
        } catch (e: Exception) {
            throw errorHandler.getNetworkException(e)
        }
    }

    override suspend fun unregister() = withContext(ioDispatcher) {
        try {
            thikiraApi.unregister()
        } catch (e: Exception) {
            throw errorHandler.getNetworkException(e)
        }
    }

    override suspend fun changePassword(newPassword: String) = withContext(ioDispatcher) {
        try {
            thikiraApi.changePassword(newPassword)
        } catch (e: Exception) {
            throw errorHandler.getNetworkException(e)
        }
    }
}