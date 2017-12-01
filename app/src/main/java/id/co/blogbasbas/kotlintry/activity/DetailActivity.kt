package id.co.blogbasbas.kotlintry

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var txt = intent.getStringExtra("JUDUL")
        var img = intent.getIntExtra("DATA", 0)


        txtDetailJudul.setText(txt)
        imgDetail.setImageResource(img)
    }
}
