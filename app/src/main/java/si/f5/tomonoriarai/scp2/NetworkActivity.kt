package si.f5.tomonoriarai.scp2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val scrollView = findViewById<ScrollView>(R.id.scrollView)

        val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:7001")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val userService: UserService = retrofit.create(UserService::class.java)
        val logService: LogService = retrofit.create(LogService::class.java)

        requestButton.setOnClickListener{
            runBlocking (Dispatchers.IO){
                runCatching {
                    //userService.getUser("lifeistech")
                    logService.getRecentLogText(); 
                }
            }.onSuccess {
                nameTextView.text = it.body
                scrollView.fullScroll(View.FOCUS_DOWN)
//                scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }
                val lastChild: View = scrollView.getChildAt(scrollView.childCount - 1)
                val bottom: Int = lastChild.bottom + scrollView.paddingBottom
                val sy: Int = scrollView.scrollY
                val sh: Int = scrollView.height
                val delta = bottom - (sy + sh)
                scrollView.smoothScrollBy(0, delta)
            }.onFailure {
                println("[Error] Failed to getting data from the server! ")
                println(it.localizedMessage)
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

