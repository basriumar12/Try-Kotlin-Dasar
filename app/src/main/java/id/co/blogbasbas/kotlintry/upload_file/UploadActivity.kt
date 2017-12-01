package id.co.blogbasbas.kotlintry

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {
    var PICK_PDF_REQUEST = 1
    var filePath : Uri? = null
    var STOARAGE_PERMISSION_CODE = 2
    var UPLOAD_URL = "http://192.168.1.104/server_berita//upload.php"
    var UPLOAD_URL_PDF = "http://192.168.1.104/server_berita//getPdf.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)


        buttonChoose.setOnClickListener {
            var intent = Intent()
            intent.setType("application/ectet-stream")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent,"Select File"), PICK_PDF_REQUEST)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == PICK_PDF_REQUEST && data != null) {
            filePath = data.data
        }
    }
}
