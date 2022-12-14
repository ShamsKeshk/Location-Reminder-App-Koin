package com.udacity.project4.locationreminders.framework.model

import kotlin.Error


/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error(val exception: Throwable? = null) : Result<Nothing>()

    object Loading : Result<Nothing>()

    fun isSuccessful(): Boolean{
        return this is Success
    }

    fun isFailed(): Boolean{
        return this is Error
    }

    fun isLoading(): Boolean{
        return this is Loading
    }

    fun isEmpty(): Boolean{
        if (this is Success){
            return if (this.data == null){
                true
            }else
                this.data is List<*> && this.data.isEmpty()
        }

        return true
    }

    fun getCurrentData(): T?{
        if (this is Success){
            return this.data
        }

        return null
    }

    fun getCurrentError(): Throwable?{
        if (this is Error){
            return this.exception
        }

        return null
    }
}


