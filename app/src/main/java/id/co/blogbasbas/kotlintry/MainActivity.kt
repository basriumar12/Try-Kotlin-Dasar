package id.co.blogbasbas.kotlintry

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
/*
    //deklarsi global
    var btn1 :Button? =null

    var btn2 :Button? =null
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn1 = findViewById(R.id.toast)
          var btn2 = findViewById(R.id.alert)
        var input = findViewById(R.id.input)
        //alert
        btn2.setOnClickListener {
            //event klik button
        val alert = AlertDialog.Builder(this)
            alert.setTitle("INI ALERT")
            alert.setMessage("apakah ingin keluar coy ?")
            alert.setPositiveButton("Yes",  DialogInterface.OnClickListener { dialogInterface, i ->

                Log.v(" Hallo"," klik button yes")
            })

            alert.setNegativeButton("no", DialogInterface.OnClickListener { dialogInterface, i ->
                Log.v(" Hallo"," klik button No")

            })
            alert.show()
        }

        //toast
        btn1.setOnClickListener {
            var toast = Toast.makeText(this@MainActivity, "Ini toast", Toast.LENGTH_LONG)
            toast.show()
        }
        //intent
        input.setOnClickListener {
            var intent = Intent(this@MainActivity, InputDataActivity::class.java)
            startActivity(intent)
        }

        //akses listactivity
        listView.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListviewActivity::class.java))
        }
                //akses recyclerview
        recyclerView.setOnClickListener {
            startActivity(Intent(this@MainActivity, RecyclerviewActivity::class.java))
        }

    }
}
