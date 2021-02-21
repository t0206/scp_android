package si.f5.tomonoriarai.scp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val connectButton = findViewById<Button>(R.id.connectButton)
        connectButton.setOnClickListener {
            val intent = Intent(this, NetworkActivity::class.java)
            startActivity(intent)
        }
    }
}