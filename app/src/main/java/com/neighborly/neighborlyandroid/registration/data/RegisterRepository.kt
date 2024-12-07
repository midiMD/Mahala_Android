package com.neighborly.neighborlyandroid.registration.data
import android.util.Log
import com.google.gson.Gson
import com.neighborly.neighborlyandroid.common.models.House
import com.neighborly.neighborlyandroid.common.models.User
import com.neighborly.neighborlyandroid.common.networking.LoginService
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiRequest
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class UserRegisterApiResponseState(
    var accepted:Boolean = false,
    val emailAlreadyExists:Boolean=false,
    val errors:List<UserRegisterApiResponse.ErrorDetails> ?= null

)

class RegisterRepository(private val apiService: LoginService) {
    suspend fun makeRegisterRequest(email:String, password:String, fullName: String): UserRegisterApiResponseState = withContext(Dispatchers.IO){
        //Network and local storage IO operations should be done in IO Context
        val requestBody = UserRegisterApiRequest(email,password,fullName)
        var userRegisterApiResponseState: UserRegisterApiResponseState = UserRegisterApiResponseState()
        try {
            val response = apiService.register(requestBody) //sticky is heya

            if(response.isSuccessful){
                userRegisterApiResponseState = UserRegisterApiResponseState(accepted = true)

            } else {
                val apiError = Gson().fromJson(
                    response.errorBody()!!.string(),
                    UserRegisterApiResponse.Error::class.java
                )

                if ((apiError.errors!!.size == 1) && apiError.errors[0].code=="unique" ){
                    userRegisterApiResponseState = UserRegisterApiResponseState(emailAlreadyExists =true, errors =null )
                }else{
                    userRegisterApiResponseState = UserRegisterApiResponseState( errors=apiError.errors )

                }
            }

        }catch (e:Exception){
            e.message?.let { Log.d("RegisterRepositoryError", it) }
        }

        userRegisterApiResponseState

         // no "return" because we are using withContext
    }
}