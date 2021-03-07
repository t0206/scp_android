package si.f5.tomonoriarai.scp2

import retrofit2.http.GET

interface LogService{
    @GET("/logs")
    suspend fun getRecentLogText(): LogText
}