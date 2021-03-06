package com.dsm.restaurant.data.repository

import com.dsm.restaurant.data.dataSource.MenuCategoryDataSource
import com.dsm.restaurant.data.dataSource.MenuDataSource
import com.dsm.restaurant.data.local.dto.MenuLocalDto
import com.dsm.restaurant.data.remote.dto.MenuDto
import com.dsm.restaurant.domain.entity.MenuRegistrationEntity
import com.dsm.restaurant.domain.repository.MenuRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MenuRepositoryImpl(
    private val menuDataSource: MenuDataSource,
    private val menuCategoryDataSource: MenuCategoryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MenuRepository {

    override suspend fun getMenuList(categoryName: String, forceUpdate: Boolean) = withContext(ioDispatcher) {
        val menuCategoryId = menuCategoryDataSource.getLocalMenuCategoryIdByName(categoryName)
        if (!forceUpdate) {
            menuDataSource.getLocalMenuList(menuCategoryId)?.let {
                if (it.isNotEmpty()) return@withContext it.map(MenuLocalDto::toEntity)
            }
        }

        menuDataSource.getRemoteMenuList(menuCategoryId).let {
            menuDataSource.deleteAllLocalMenu(menuCategoryId)
            menuDataSource.insertLocalMenuList(it.map { it.toLocalDto(menuCategoryId) })
            return@withContext it.map(MenuDto::toEntity)
        }
    }

    override suspend fun uploadMenu(menuRegistrationEntity: MenuRegistrationEntity) = withContext(ioDispatcher) {
        menuDataSource.uploadRemoteMenu(menuRegistrationEntity.toDto())
    }
}