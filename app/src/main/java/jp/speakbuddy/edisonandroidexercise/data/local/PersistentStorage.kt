package jp.speakbuddy.edisonandroidexercise.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import jp.speakbuddy.edisonandroidexercise.Constants.DataStore.EMPTY_JSON_STRING
import jp.speakbuddy.edisonandroidexercise.Constants.DataStore.OPERATION_SUCCESS
import jp.speakbuddy.edisonandroidexercise.data.local.`interface`.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class PersistentStorage<T> constructor(
    private val gson: Gson,
    private val type: java.lang.reflect.Type,
    private val preferenceKey: Preferences.Key<String>,
    private val dataStore: DataStore<Preferences>
) : Storage<T> {

    override fun insert(data: T): Flow<Int> {
        return channelFlow {
            val cachedDataClone = getAll().first().toMutableList()
            cachedDataClone.add(data)
            dataStore.edit {
                val jsonString = gson.toJson(cachedDataClone, type)
                it[preferenceKey] = jsonString
                send(OPERATION_SUCCESS)
            }
        }
    }

    override fun insert(data: List<T>): Flow<Int> {
        return channelFlow {
            val cachedDataClone = getAll().first().toMutableList()
            cachedDataClone.addAll(data)
            dataStore.edit {
                val jsonString = gson.toJson(cachedDataClone, type)
                it[preferenceKey] = jsonString
                send(OPERATION_SUCCESS)
            }
        }
    }

    override fun getAll(): Flow<List<T>> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[preferenceKey] ?: EMPTY_JSON_STRING
            val elements = gson.fromJson<List<T>>(jsonString, type)
            elements
        }
    }

    override fun clearAll(): Flow<Int> {
        return channelFlow {
            dataStore.edit {
                it.remove(preferenceKey)
                send(OPERATION_SUCCESS)
            }
        }
    }

    override fun get(where: (T) -> Boolean): Flow<T> {
        return channelFlow {
            val data = getAll().first().first(where)
            send(data)
        }
    }
}