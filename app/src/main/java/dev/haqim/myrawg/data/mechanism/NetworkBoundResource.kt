package dev.haqim.myrawg.data.mechanism

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


/*
*
* RequestType: Data type that used to catch network response a.k.a inserted data type
* ResultType: Data type that expected as return data a.k.a output data type
* */
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow{
        emit(Resource.Loading())
        try {
            val currentLocalData = loadCurrentLocalData()
            val apiResponse = requestFromRemote()
            if(apiResponse.isSuccess){
                apiResponse.getOrNull()?.let { res ->
                    onFetchSuccess(res)
                    emitAll(
                        loadResult(res).map {
                            Resource.Success(it)
                        }
                    )
                }

            }else{
                onFetchFailed()
                emit(
                    Resource.Error(
                        message = apiResponse
                            .exceptionOrNull()
                            ?.localizedMessage ?: "Unknown error",
                        data = currentLocalData
                    )
                )
            }
        }catch (e: Exception){
            onFetchFailed()
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Unknown error"
                )
            )
        }
    }

    protected abstract suspend fun requestFromRemote(): Result<RequestType>

    /**
     * Load from network to be returned and consumed. Convert data from network to model here
     *
     * @param data
     * @return
     */
    protected abstract fun loadResult(data: RequestType): Flow<ResultType>

    /**
     * Load current data from local storage
     *
     */
    protected open suspend fun loadCurrentLocalData(): ResultType? = null
    

    protected open suspend fun onFetchSuccess(data: RequestType) {}

    protected open fun onFetchFailed() {}
    fun asFlow(): Flow<Resource<ResultType>> = result

}