package id.co.blogbasbas.kotlintry.adapter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.co.blogbasbas.kotlintry.DetailActivity
import id.co.blogbasbas.kotlintry.R

/**
 * Created by Server on 29/11/2017.
 */
class CustomAdapter (recyclerviewActivity: AppCompatActivity, dataGambar: Array<Int>, judulGambar : Array<String>
                     ) : RecyclerView.Adapter<CustomAdapter.MyHolder>() {

    var  context : AppCompatActivity? = null
    var dataGambar : Array<Int>? = null
    var judulGambar : Array<String>? = null
    init {
        this.dataGambar = dataGambar
        this.judulGambar = judulGambar
        context = recyclerviewActivity

    }



    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder?.text?.text = judulGambar?.get(position)
        holder?.image?.setImageResource(dataGambar?.get(position)!!)

        holder?.itemView?.setOnClickListener {

         var intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("JUDUL", judulGambar?.get(position))
            intent.putExtra("DATA", dataGambar?.get(position))

            context?.startActivity(intent)
        }

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyHolder(v)

  //      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return judulGambar!!.size
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var image = itemView?.findViewById<ImageView>(R.id.imgListItem)
        var text = itemView?.findViewById<TextView>(R.id.txtJudul)

    }
}