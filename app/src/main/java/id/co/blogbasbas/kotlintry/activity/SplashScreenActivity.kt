package id.co.blogbasbas.kotlintry

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var handler = Handler()
        handler.postDelayed(Runnable {

            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()

        }, 5000)

    }
}
