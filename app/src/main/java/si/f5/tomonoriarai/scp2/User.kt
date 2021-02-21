package si.f5.tomonoriarai.scp2

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("login") val userId: String,
        val name : String
)
