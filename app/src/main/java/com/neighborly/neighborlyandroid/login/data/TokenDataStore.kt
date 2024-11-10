import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

interface TokenDataStore {
    suspend fun getToken(): String
    suspend fun saveToken(token: String)
    suspend fun clearToken()  // Function to clear the token if needed
}


class TokenDataStoreImpl(private val context: Context):TokenDataStore{
    companion object {

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
    override suspend fun getToken(): String = withContext(Dispatchers.IO){
        val preferences = context.dataStore.data.first() // Fetch Preferences
        val token = preferences[PreferencesKeys.TOKEN]
        if (token.isNullOrEmpty()){
            Log.i("logs","Token in datastore is empty or null")
        }
        Log.i("logs","Getting token from datastore: "+ token)
        token.toString() // Access token with default
    }

    override suspend fun saveToken(token: String)  {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.TOKEN] = token
            Log.i("logs", "Auth token saved in datastore: $token")
        }
    }

    override suspend  fun clearToken() {
        context.dataStore.edit { settings ->
            settings.remove(PreferencesKeys.TOKEN)
            Log.i("logs","Auth token deleted from datastore")
        }
    }

    // Private class to define your DataStore keys
    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
    }


}