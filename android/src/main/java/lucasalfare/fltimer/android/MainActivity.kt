package lucasalfare.fltimer.android

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(applicationContext)
        tv.text = "biluuuuuuuuuu"
        setContentView(tv)
    }
}