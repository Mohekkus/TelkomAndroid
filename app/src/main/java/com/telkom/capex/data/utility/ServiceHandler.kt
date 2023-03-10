package com.telkom.capex.data.utility

data class ServiceHandler<out T>(
    val status: Status,
    val data: T?,
    val message:String?
){
    companion object{

        fun <T> success(data:T?): ServiceHandler<T>{
            return ServiceHandler(Status.SUCCESS, data, null)
        }

        fun <T> error(msg:String, data:T?): ServiceHandler<T>{
            return ServiceHandler(Status.ERROR, data, msg)
        }

        fun <T> loading(data:T?): ServiceHandler<T>{
            return ServiceHandler(Status.LOADING, data, null)
        }

    }
}
