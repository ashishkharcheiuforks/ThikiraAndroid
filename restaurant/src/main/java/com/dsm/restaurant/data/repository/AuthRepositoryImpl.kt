package com.dsm.restaurant.data.repository

import com.dsm.restaurant.data.dataSource.AuthDataSource
import com.dsm.restaurant.data.local.PrefStorage
import com.dsm.restaurant.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val prefStorage: PrefStorage,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override suspend fun authToken() = withContext(ioDispatcher) {
        authDataSource.authToken()
    }

    override suspend fun confirmEmailDuplication(email: String) = withContext(ioDispatcher) {
        authDataSource.confirmEmailDuplication(email)
    }

    override suspend fun confirmPassword(password: String) = withContext(ioDispatcher) {
        authDataSource.confirmPassword(password)
    }

    override suspend fun login(body: Any) = withContext(ioDispatcher) {
        val tokenDto = authDataSource.login(body)
        prefStorage.setAccessToken(tokenDto.accessToken)
        prefStorage.setRefreshToken(tokenDto.refreshToken)
    }

}