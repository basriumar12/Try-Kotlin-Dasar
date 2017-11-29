package id.co.blogbasbas.kotlintry

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.content_input_data.*


class InputDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        btnHitung.setOnClickListener {
         var inputan1 = edtAngka1.text.toString()
         var inputan2 = edtAngka2.text.toString()
             if (inputan1.isEmpty() || inputan2.isEmpty()){
                 var toast = Toast.makeText(this@InputDataActivity,"Harus Di isi formnya", Toast.LENGTH_LONG)
                 toast.show()
             } else{
                 var parsing1 = inputan1.toInt()
                 var parsing2 = inputan2.toInt()


                 var hasil = parsing1 * parsing2
                 txtHasil.text = ("Hasilnya " +hasil)
                // txtHasil.text = hasil.toString()
             }
        }

        btnclear.setOnClickListener {
            edtAngka1.setText("")
            edtAngka2.setText("")
            txtHasil.setText(" Hasilnya : ")
        }
        btnPindah.setOnClickListener {
            
        }
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(View.OnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        })
    }

}
