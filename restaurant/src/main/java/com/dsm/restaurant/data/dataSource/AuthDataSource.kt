package com.dsm.restaurant.data.dataSource

import com.dsm.restaurant.data.error.ErrorHandler
import com.dsm.restaurant.data.remote.ThikiraApi
import com.dsm.restaurant.data.remote.dto.TokenDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AuthDataSource {

    suspend fun authToken()

    suspend fun confirmEmailDuplication(email: String)

    suspend fun login(body: Any): TokenDto

    suspend fun confirmPassword(password: String)
}

class AuthDataSourceImpl(
    private val thikiraApi: ThikiraApi,
    private val errorHandler: ErrorHandler,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthDataSource {

    override suspend fun authToken() = withContext(ioDispatcher) {
        try {
            thikiraApi.authToken()
        } catch (e: Exception) {
            throw errorHandler.getNetworkException(e)
        }
    }

    override suspend fun confirmEmailDuplication(email: String) = withContext(ioDispatcher) {
        try {
            thikiraApi.confirmEmailDuplication(email)
        } catch (e: Exception) {
            throw errorHandler.getNetworkException(e)
        }
    }

    override suspend fun login(body: Any): TokenDto = withContext(ioDispatcher) {
        try {
            thikiraApi.login(body)
        } catch (e: Exception) {
            throw errorHandler.getNetworkException(e)
        }
    }

    override suspend fun confirmPassword(password: String) = withContext(ioDispatcher) {
        try {
            thikiraApi.confirmPassword(password)
        } catch (e: Exception) {
            throw errorHandler.getNetworkException(e)
        }
    }
}