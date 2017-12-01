package id.co.blogbasbas.kotlintry

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_listview.*

class ListviewActivity : AppCompatActivity() {

    //deklarsi array item
    var data = arrayOf("Aku", "Sayang ", " Kamu ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)
        //get adapater untuk tampung array
        var adapter1 = ArrayAdapter(this@ListviewActivity,android.R.layout.simple_list_item_1,data)


        //set adapter
        idlistview.adapter = adapter1
        idlistview.setOnItemClickListener { adapterView, view, i, l ->
            Snackbar.make(view, data[i], Snackbar.LENGTH_LONG).show()


        }


    }
}
