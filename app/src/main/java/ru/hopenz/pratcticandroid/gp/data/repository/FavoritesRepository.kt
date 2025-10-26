package ru.hopenz.pratcticandroid.gp.data.repository

import kotlinx.coroutines.flow.Flow
import ru.hopenz.pratcticandroid.gp.data.local.FavoriteDao
import ru.hopenz.pratcticandroid.gp.data.local.FavoriteEntity

class FavoritesRepository(private val dao: FavoriteDao) {
    val favorites: Flow<List<FavoriteEntity>> = dao.getAllFavorites()

    suspend fun addFavorite(favorite: FavoriteEntity) {
        dao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(id: Int) {
        dao.removeFavorite(id)
    }
}