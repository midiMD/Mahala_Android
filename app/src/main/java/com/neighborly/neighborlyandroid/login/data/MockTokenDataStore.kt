import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private val mockToken: String = "851da0a9d8f6cc7cd6cf9c94eccadc1e454d2a34"


class MockTokenDataStoreImpl(private val context: Context):TokenDataStore{
    companion object {

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "mockSettings")
    }
    override suspend fun getToken(): String = withContext(Dispatchers.IO){
        return@withContext mockToken
    }

    override suspend fun saveToken(token: String)  {
        context.dataStore.edit { mockSettings ->
            mockSettings[PreferencesKeys.TOKEN] = token
        }
    }

    override suspend  fun clearToken() {
        context.dataStore.edit { mockSettings ->
            mockSettings.remove(PreferencesKeys.TOKEN)
        }
    }

    // Private class to define your DataStore keys
    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
    }


}