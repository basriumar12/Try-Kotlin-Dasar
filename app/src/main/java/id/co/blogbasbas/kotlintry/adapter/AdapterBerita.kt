package id.co.blogbasbas.kotlintry.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.co.blogbasbas.kotlintry.R
import id.co.blogbasbas.kotlintry.response.BeritaItem

/**
 * Created by Server on 30/11/2017.
 */
class AdapterBerita (context: AppCompatActivity, data: List<BeritaItem?>?) : RecyclerView.Adapter<AdapterBerita.MyHolder>() {

    var  con : AppCompatActivity? = null
    var data : List<BeritaItem>? = null
    init {
        con = context
        this.data = data as List<BeritaItem>?

    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
         holder?.judulBerita?.text = data?.get(position)?.judul
        Picasso.with(con).load("http://192.168.1.107/server_berita/foto_berita/" +data?.get(position)?.gambar)
              .placeholder(R.drawable.b2).error(R.id.imgBerita)
                .into(holder?.gambarBerita)


        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
    return  data!!.size
    //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder{

        var v = LayoutInflater.from(con).inflate(R.layout.list_berita, parent, false)
        return MyHolder(v)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var judulBerita = itemView?.findViewById<TextView>(R.id.txtJudulBerita)
        var gambarBerita = itemView?.findViewById<ImageView>(R.id.imgBerita)

    }
}