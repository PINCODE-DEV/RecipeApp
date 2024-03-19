package com.softanime.recipeapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.softanime.recipeapp.data.models.register.BodyRegister
import com.softanime.recipeapp.data.models.register.RegisterStoredModel
import com.softanime.recipeapp.data.sources.RemoteDataSource
import com.softanime.recipeapp.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class RegisterRepo @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remote: RemoteDataSource) {

    // Stored User Info
    private object StoredKeys{
        val username = stringPreferencesKey(Constants.REGISTER_USERNAME)
        val hash = stringPreferencesKey(Constants.REGISTER_HASH)
    }

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(Constants.REGISTER_INFO)

    // Save User Info
    suspend fun saveUserInfo(username: String, hash: String){
        context.dataStore.edit {
            it[StoredKeys.username] = username
            it[StoredKeys.hash] = hash
        }
    }

    // Read User Info
    val readUserInfo : Flow<RegisterStoredModel> = context.dataStore.data
        .catch { e ->
            if (e is IOException)
                emit(emptyPreferences())
            else
                throw e
        }.map {
            val username = it[StoredKeys.username] ?: ""
            val hash = it[StoredKeys.hash] ?: ""

            RegisterStoredModel(username.toString(),hash.toString())
        }

    // Api Requests
    suspend fun postRegister(apiKey: String, body: BodyRegister) = remote.postRegister(apiKey, body)
}