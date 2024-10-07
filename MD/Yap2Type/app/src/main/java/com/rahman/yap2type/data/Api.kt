package com.rahman.yap2type.data

import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("summarize")
    suspend fun summarize(@Body request: SummarizeRequest): SummarizeResponse
}
