package com.example.data.datastore

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.model.Fact
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStore @Inject constructor(private val context: Context) {
    companion object {
         val Context.dataStore: DataStore<Preferences> by preferencesDataStore("FACT")
         val factDesc = stringPreferencesKey("FACT")
         val factLength = intPreferencesKey("length")
    }

    suspend fun saveDetails(fact: Fact?) {
        context.dataStore.edit {
            it[factDesc] = fact?.fact ?: ""
            it[factLength] = fact?.length ?: 0
        }
    }

     fun getDetails() = context.dataStore.data.map {
        context.dataStore.edit {
            it[factDesc] = it[factDesc]?: ""
            it[factLength] = it[factLength] ?: 0
        }
    }
}