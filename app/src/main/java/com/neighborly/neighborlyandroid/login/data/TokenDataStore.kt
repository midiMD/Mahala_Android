import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

interface TokenDataStoreInterface {
    suspend fun getToken(): String
    suspend fun saveToken(token: String)
    suspend fun clearToken()  // Function to clear the token if needed
}


//class TokenDataStore(private val context: Context) : DataStoreManager {
//
//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
//
//
//    override suspend fun getToken(): String = withContext(Dispatchers.IO) {
//        val preferences = context.dataStore.data.first() // Fetch Preferences
//        preferences[PreferencesKeys.TOKEN] ?: "" // Access token with default
//    }
//
//    override suspend fun saveToken(token: String)  {
//        context.dataStore.edit { settings ->
//            settings[PreferencesKeys.TOKEN] = token
//        }
//    }
//
//    override suspend  fun clearToken() {
//        context.dataStore.edit { settings ->
//            settings.remove(PreferencesKeys.TOKEN)
//        }
//    }
//
//    // Private class to define your DataStore keys
//    private object PreferencesKeys {
//        val TOKEN = stringPreferencesKey("token")
//    }
//}

class TokenDataStore(private val context: Context):TokenDataStoreInterface{
    companion object {

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
    override suspend fun getToken(): String = withContext(Dispatchers.IO){
        val preferences = context.dataStore.data.first() // Fetch Preferences
        preferences[PreferencesKeys.TOKEN] ?: "" // Access token with default
    }

    override suspend fun saveToken(token: String)  {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.TOKEN] = token
        }
    }

    override suspend  fun clearToken() {
        context.dataStore.edit { settings ->
            settings.remove(PreferencesKeys.TOKEN)
        }
    }

    // Private class to define your DataStore keys
    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
    }


}