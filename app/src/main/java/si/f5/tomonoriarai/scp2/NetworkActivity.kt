package si.f5.tomonoriarai.scp2

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        val requestButton  = findViewById<Button>(R.id.requestButton)
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val userIdTextView = findViewById<TextView>(R.id.userIdTextView)

        val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val userService: UserService = retrofit.create(UserService::class.java)

        requestButton.setOnClickListener{
            runBlocking (Dispatchers.IO){
                kotlin.runCatching {
                    userService.getUser("lifeistech")
                }.onSuccess {
                    nameTextView.text = it.name
                    userIdTextView.text = it.userId
                }.onFailure {
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

