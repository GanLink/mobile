package com.ganlink.pe.features.farmmanagement.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.ganlink.pe.features.farmmanagement.domain.models.FarmSettingsState
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.farmSettingsDataStore by preferencesDataStore(name = "farm_settings")

@Singleton
class FarmSettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val notifications = booleanPreferencesKey("notifications_enabled")
        val darkMode = booleanPreferencesKey("dark_mode_enabled")
        val autoSync = booleanPreferencesKey("auto_sync_enabled")
    }

    val settings: Flow<FarmSettingsState> = context.farmSettingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs -> prefs.toState() }

    private fun Preferences.toState(): FarmSettingsState = FarmSettingsState(
        notificationsEnabled = this[Keys.notifications] ?: true,
        darkModeEnabled = this[Keys.darkMode] ?: false,
        autoSyncEnabled = this[Keys.autoSync] ?: true
    )

    suspend fun updateNotifications(enabled: Boolean) {
        context.farmSettingsDataStore.edit { it[Keys.notifications] = enabled }
    }

    suspend fun updateDarkMode(enabled: Boolean) {
        context.farmSettingsDataStore.edit { it[Keys.darkMode] = enabled }
    }

    suspend fun updateAutoSync(enabled: Boolean) {
        context.farmSettingsDataStore.edit { it[Keys.autoSync] = enabled }
    }

    suspend fun resetDefaults() {
        context.farmSettingsDataStore.edit {
            it[Keys.notifications] = true
            it[Keys.darkMode] = false
            it[Keys.autoSync] = true
        }
    }
}
