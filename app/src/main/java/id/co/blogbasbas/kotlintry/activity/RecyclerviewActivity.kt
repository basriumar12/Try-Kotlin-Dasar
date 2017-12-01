package id.co.blogbasbas.kotlintry

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import id.co.blogbasbas.kotlintry.adapter.CustomAdapter
import kotlinx.android.synthetic.main.activity_recyclerview.*

class RecyclerviewActivity : AppCompatActivity() {
    var dataGambar = arrayOf(R.drawable.b1,R.drawable.b2,
                            R.drawable.b1,R.drawable.b2,
                            R.drawable.b1,R.drawable.b2)
    var judulGambar = arrayOf("A1", "A2","A1", "A2","A1", "A2")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        var layout = GridLayoutManager(applicationContext,3)

        var adapter = CustomAdapter(this, dataGambar, judulGambar)
        idRecyclerview.adapter = adapter

        idRecyclerview.layoutManager= layout


    }
}
