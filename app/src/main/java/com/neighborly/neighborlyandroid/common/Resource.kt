package com.neighborly.neighborlyandroid.common
// exposed to UI layer
sealed class Resource<T>(val data: T? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
//    class AccessDenied<T>():Resource<T>()
//    class ClientError<T>():Resource<T>()
//    class ServerError<T>():Resource<T>()
    sealed class Error<T>():Resource<T>(){
        class AccessDenied<T>():Error<T>()
        class ClientError<T>():Error<T>()
        class ServerError<T>():Error<T>()
        class NetworkError<T>():Error<T>()
    }
    //class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
   // class Loading<T>() : Resource<T>()
    //data object Loading
}

//sealed class Resource<T>(){
//    data class Success<T>( val data:T): Resource<T>()
//    sealed class Error():Resource<T>(){
//        data object AccessDenied:Error()
//        data object ClientError:Error()
//        data object ServerError:Error()
//        data object NetworkError:Error() // interned probs buggin stilll
//    }
//}