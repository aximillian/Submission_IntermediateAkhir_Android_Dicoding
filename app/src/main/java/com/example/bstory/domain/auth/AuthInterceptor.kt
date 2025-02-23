package com.example.bstory.domain.auth

import com.example.bstory.config.PreferenceConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor (
    private val requestHeaders: HashMap<String, String>,
    private val preferenceConfig: PreferenceConfig
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        mapRequestHeaders()

        val request = mapHeaders(chain)

        return chain.proceed(request)
    }

    private fun mapRequestHeaders() {
        val token = preferenceConfig.getToken
        requestHeaders["Authorization"] = "Bearer $token"
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()

        val requestBuilder = original.newBuilder()

        for ((key, value) in requestHeaders) {
            requestBuilder.addHeader(key, value)
        }
        return requestBuilder.build()
    }

}