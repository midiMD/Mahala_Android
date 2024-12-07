import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private val mockToken: String = "bf559e9d4f310dea47ebea2f1b61da48219a764d" // for lady.day@gmail.com pass3


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