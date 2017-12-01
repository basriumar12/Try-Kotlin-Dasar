package id.co.blogbasbas.kotlintry.helper

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import id.co.blogbasbas.kotlintry.helper.HeroHelper.d

import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.math.BigDecimal
import java.math.RoundingMode
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.UUID
import java.util.regex.Matcher
import java.util.regex.Pattern


@SuppressLint("SimpleDateFormat")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
object HeroHelper {
    private val DEBUG = 1
    val APP = "ojekOnlineBatch2-app"
    val BASE_URL = "http://gojeg.manggaleh.net/api/"
    val BASE_URL_IMAGE = "http://gojeg.manggaleh.net/img/"

    //BASE_URL + daftar
    //BASE_URL + login
    fun alert(context: Context, title: String, message: String) {
        val alertDialog = AlertDialog.Builder(context).create()

        // Setting Dialog Title
        alertDialog.setTitle(title)

        // Setting Dialog Message
        alertDialog.setMessage(message)


        // Setting OK Button
        alertDialog.setButton("OK") { dialog, which -> }

        // Showing Alert Message
        alertDialog.show()


    }


    @Throws(Exception::class)
    fun convertStreamToString(`is`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n")
        }
        return sb.toString()
    }


    fun error(e: Exception) {
        if (DEBUG == 1) {
            e.printStackTrace()
        }
    }

    fun error(e: Exception, x: String) {
        if (DEBUG == 1) {
            log(x)
            e.printStackTrace()
        }

    }

    fun showSettingGps(context: Context) {
        val alertBuilder = AlertDialog.Builder(context)

        alertBuilder.setTitle("GPS Setting")
        alertBuilder
                .setMessage("GPS is not enabled. Do you want to go to settings menu?")

        alertBuilder.setPositiveButton("Setting"
        ) { dialog, which ->
            val intent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        alertBuilder.setNegativeButton("Cancel"
        ) { dialog, which -> dialog.cancel() }
        alertBuilder.show()
    }

    fun isOnline(c: Context): Boolean {
        val cm = c
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            true
        } else false
    }

    // peringatan jika internet tidak konek
    fun alertMessageNoInternet(c: Context) {
        val builder = AlertDialog.Builder(c)
        builder.setMessage(
                "Anda tidak terkoneksi dengan internet, Silahkan Aktifkan Internet Anda terlebih dahulu.")
                .setCancelable(false)
                .setTitle("Informasi Internet")
                .setNegativeButton("Tutup"
                ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }


    fun tglJamSekarangFile(): String {
        val formatter1 = SimpleDateFormat("yyyyMMdd_HHmmss")

        val now = Date()
        HeroHelper.pre("tgl simpan: " + formatter1.format(now))
        return formatter1.format(now)
    }

    fun getFileScan(fileName: String): File {
        val mediaStorageDir = File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "simvasi")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                Log.d("error", "Failed to create directory")
            }
        }

        return File(mediaStorageDir, fileName)
    }

    fun saveFile(bmp: Bitmap, nama: String): File? {

        val mediaStorageDir = File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "panenid")

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                Log.d("error", "Failed to create directory")
            }
        }

        var file: File? = null
        try {
            file = File(mediaStorageDir, nama + ".png")
            val fOut = FileOutputStream(file)

            bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            fOut.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }


        return file
    }

    fun timeConversion(totalSeconds: Int): String {

        val MINUTES_IN_AN_HOUR = 60
        val SECONDS_IN_A_MINUTE = 60

        val seconds = totalSeconds % SECONDS_IN_A_MINUTE
        val totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE
        val minutes = totalMinutes % MINUTES_IN_AN_HOUR
        val hours = totalMinutes / MINUTES_IN_AN_HOUR

        return if (hours == 0) {
            minutes.toString() + " minutes"
        } else {
            hours.toString() + " hours " + minutes + " minutes "
        }
        // return hours + " hours " + minutes + " minutes " + seconds +
        // " seconds";

    }

    fun timeConversion2(totalSeconds: Int): String {

        val MINUTES_IN_AN_HOUR = 60
        val SECONDS_IN_A_MINUTE = 60

        val seconds = totalSeconds % SECONDS_IN_A_MINUTE
        val totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE
        val minutes = totalMinutes % MINUTES_IN_AN_HOUR
        val hours = totalMinutes / MINUTES_IN_AN_HOUR

        // return hours + " hours " + minutes + " minutes " + seconds +
        // " seconds";
        return String.format("%02d", hours) + ":"
        +String.format("%02d", minutes) + ":"
        +String.format("%02d", seconds)
    }

    fun pesan(c: Context, msg: String) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show()
    }

    fun isEmpty(etText: EditText): Boolean {
        return if (etText.text.toString().trim { it <= ' ' }.length > 0) {
            false
        } else {
            true
        }
    }

    fun isCompare(etText: EditText, ex: EditText): Boolean {
        val a = etText.text.toString()
        val b = ex.text.toString()
        return if (a == b) {
            false
        } else {
            true
        }
    }

    fun isBatasUsia(etText: EditText, batas: Int): Boolean {
        val a = etText.text.toString()
        val usia = strTodate(a)
        val sekarang = dateAddYear(strTodate(tglSekarang()), batas)
        return if (usia!!.before(sekarang)) {
            false
        } else {
            true
        }
    }

    fun pre(pesan: String) {
        try {
            if (DEBUG == 1) {
                println(pesan)
            }
        } catch (e: Exception) {
            // TODO: handle exception
        }

    }

    fun isBatasTanggal(etText: EditText, batas: Int): Boolean {
        val a = etText.text.toString()
        val usia = strTodate(a)
        val sekarang = dateKurang(strTodate(tglSekarang()), batas)
        pre("tanggal pilih : " + usia!!.toString() + " --- tanggal batas : "
                + sekarang!!.toString())
        return if (usia.after(sekarang)) {
            false
        } else {
            true
        }
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    fun isEmailValid(email: EditText): Boolean {
        var isValid = false

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val inputStr = email.text.toString()

        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    fun minLength(etText: EditText, jmlh: Int): Boolean {
        return if (etText.text.toString().trim { it <= ' ' }.length >= jmlh) {
            false
        } else {
            true
        }
    }

    // untuk check koneksi internet
    fun isOnline(cm: ConnectivityManager): Boolean {
        val netInfo = cm.activeNetworkInfo
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            true
        } else false
    }

    // md5 encrypt function
    fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                    .getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            for (i in messageDigest.indices)
                hexString.append(Integer.toHexString(0xFF and messageDigest[i]))
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun tglSekarang(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        return dateFormat.format(date)
    }

    fun tglSekarang(format: String): String {
        val dateFormat = SimpleDateFormat(format)
        val date = Date()
        return dateFormat.format(date)
    }

    fun tglSekarangNyatu(): String {
        val dateFormat = SimpleDateFormat("ddMMyy_HHmm")
        val date = Date()
        return dateFormat.format(date)
    }

    fun jamSekarang(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        val date = Date()
        return dateFormat.format(date)
    }

    fun jamSekarang2(): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val date = Date()
        return dateFormat.format(date)
    }

    fun tglJamSekarang(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val date = Date()
        return dateFormat.format(date)
    }

    fun tglJamSekarang2(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
        val date = Date()
        return dateFormat.format(date)
    }

    fun tglJamSql(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date()
        return dateFormat.format(date)
    }

    fun strTodate(data: String): Date? {
        val df = SimpleDateFormat("dd-MM-yyyy")
        var startDate: Date? = null
        val newDateString = ""
        try {
            startDate = df.parse(data)
            // newDateString = df.format(startDate);
            println(newDateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return startDate
    }

    fun strTodate(data: String, format: String): Date? {
        val df = SimpleDateFormat(format)
        var startDate: Date? = null
        val newDateString = ""
        try {
            startDate = df.parse(data)
            // newDateString = df.format(startDate);
            println(newDateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return startDate
    }

    fun formatInt(data: Int): String {
        val nft = DecimalFormat("#00.###")
        return nft.format(data.toLong())
    }

    fun dateToString(data: Date): String {
        val df = SimpleDateFormat("dd-MM-yyyy")
        var newDateString = ""
        newDateString = df.format(data)
        return newDateString
    }

    fun dateAdd(`in`: Date?, daysToAdd: Int): Date? {
        if (`in` == null) {
            return null
        }
        val cal = Calendar.getInstance()
        cal.time = `in`
        cal.add(Calendar.DAY_OF_MONTH, daysToAdd)
        return cal.time

    }

    fun dateKurang(`in`: Date?, daysToAdd: Int): Date? {
        if (`in` == null) {
            return null
        }
        val cal = Calendar.getInstance()
        cal.time = `in`
        cal.add(Calendar.DAY_OF_MONTH, -daysToAdd)
        return cal.time

    }

    fun dateAddYear(`in`: Date?, tahun: Int): Date? {
        if (`in` == null) {
            return null
        }
        val cal = Calendar.getInstance()
        cal.time = `in`
        cal.add(Calendar.YEAR, tahun)
        return cal.time

    }

    fun toRupiahFormat(nominal: String): String {

        val df = DecimalFormat.getCurrencyInstance() as DecimalFormat
        val dfs = DecimalFormatSymbols()
        dfs.currencySymbol = ""
        dfs.monetaryDecimalSeparator = ','
        dfs.groupingSeparator = '.'
        df.decimalFormatSymbols = dfs

        // String rupiah = df.format(Double.parseDouble(nominal)) + ",-";

        return df.format(d(nominal))
    }

    fun toRupiahFormat2(nominal: String): String {

        val df = DecimalFormat.getCurrencyInstance() as DecimalFormat

        val dfs = DecimalFormatSymbols()
        dfs.currencySymbol = ""
        dfs.monetaryDecimalSeparator = ','
        dfs.groupingSeparator = '.'
        df.decimalFormatSymbols = dfs

        // String rupiah = df.format(Double.parseDouble(nominal)) + ",-";
        //df.setDecimalSeparatorAlwaysShown(desimal);
        df.maximumFractionDigits = 0

        return df.format(d(nominal))
    }

    fun getDeviceId(context: Context): String {
        val deviceId = (context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
        return deviceId ?: Build.SERIAL
    }

    fun getDeviceUUID(context: Context): String {
        val tm = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val tmDevice: String
        val tmSerial: String
        val androidId: String
        tmDevice = "" + tm.deviceId
        tmSerial = "" + tm.simSerialNumber
        androidId = "" + Secure.getString(context.contentResolver,
                Secure.ANDROID_ID)

        val deviceMobileNo = tm.line1Number

        val deviceUuid = UUID(androidId.hashCode().toLong(),
                tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode())
        return deviceUuid.toString()

    }

    fun alert(context: Context, title: String, message: String,
              status: Boolean?) {
        val alertDialog = AlertDialog.Builder(context).create()

        // Setting Dialog Title
        alertDialog.setTitle(title)

        // Setting Dialog Message
        alertDialog.setMessage(message)

        if (status != null)
        // Setting alert dialog icon
        //            alertDialog.setIcon((status) ? R.drawable.successicon
        //                    : R.drawable.erroricon);

        // Setting OK Button
            alertDialog.setButton("OK") { dialog, which -> }

        // Showing Alert Message
        alertDialog.show()
    }

    fun log(pesan: String) {
        if (DEBUG == 1) {
            Log.d(APP, pesan)
        }
    }

    fun writeFile(fileName: String): OutputStream? {
        var fileOut: OutputStream? = null
        try {
            val file = File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

            if (file.exists()) {
                fileOut = FileOutputStream(file)
            } else {
                file.createNewFile()
                fileOut = FileOutputStream(file)
            }

        } catch (e: IOException) {
            log(e.toString())
            e.printStackTrace()

        }

        return fileOut
    }

    fun getFile(fileName: String): File {

        return File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
    }

    fun d(transPokok: String): Double? {
        var x: Double? = 0.0
        try {
            x = java.lang.Double.parseDouble(transPokok)
        } catch (e: Exception) {
            // TODO: handle exception
        }

        return x
    }

    fun s(jml: Double?): String {
        // TODO Auto-generated method stub
        return jml.toString()
    }

    //hitung masa panen
    fun posisiPanen(tglTanam: String, masapanen: String): Int {
        var tglTanam = tglTanam
        var hasil = 1

        val x = tglTanam.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        tglTanam = x[2] + "-" + x[1] + "-" + x[0]
        //hitung selisih tanggal sekarang dengan tanggal tanam
        val selisih = getBedaHari(strTodate(tglSekarang()), strTodate(tglTanam))

        val range = Integer.parseInt(masapanen).toInt() / 3
        val range1 = range + range
        val range2 = range + range1


        if (selisih < range) {
            hasil = 1
        } else if (selisih >= range && selisih <= range1) {
            hasil = 2
        } else if (selisih > range1) {
            hasil = 3
        }

        log("selesih : " + selisih.toString() + ", range :"
                + range.toString() + ", hasil :" + hasil.toString())


        return hasil
    }

    fun getBedaHari(dateOne: Date?, dateTwo: Date?): Int {
        val timeDiff = Math.abs(dateOne!!.time - dateTwo!!.time)
        return (timeDiff / (1000 * 60 * 60 * 24)).toInt()
    }

    fun tglToInd(tgl: String): String {
        val x = tgl.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return x[2] + " " + getBulan(x[1]) + " " + x[0]
    }

    fun tglToInd3(tgl: String): String {
        val x = tgl.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return x[2] + " " + getBulan(x[1]) + " " + x[0]
    }

    fun tglJamToInd(tgl: String): String {
        val x = tgl.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val x1 = x[2].split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return x1[0] + " " + getBulan(x[1]) + " " + x[0] + " " + x1[1]
    }

    fun tglToIndBlnThn(tgl: String): String {
        val x = tgl.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return getBulan(x[1]) + " " + x[0]
    }

    fun tglToInd2(tgl: String): String {
        val x = tgl.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return x[2] + "/" + x[1] + "/" + x[0].substring(2)
    }

    fun getBulan(i: String): String {
        var hasil = ""
        if (i.equals("01", ignoreCase = true)) {
            hasil = "Januari"
        } else if (i.equals("02", ignoreCase = true)) {
            hasil = "Februari"
        } else if (i.equals("03", ignoreCase = true)) {
            hasil = "Maret"
        } else if (i.equals("04", ignoreCase = true)) {
            hasil = "April"
        } else if (i.equals("05", ignoreCase = true)) {
            hasil = "Mei"
        } else if (i.equals("06", ignoreCase = true)) {
            hasil = "Juni"
        } else if (i.equals("07", ignoreCase = true)) {
            hasil = "Juli"
        } else if (i.equals("08", ignoreCase = true)) {
            hasil = "Agustus"
        } else if (i.equals("09", ignoreCase = true)) {
            hasil = "September"
        } else if (i.equals("10", ignoreCase = true)) {
            hasil = "Oktober"
        } else if (i.equals("11", ignoreCase = true)) {
            hasil = "November"
        } else if (i.equals("12", ignoreCase = true)) {
            hasil = "Desember"
        }

        return hasil
    }

    fun round(value: Double, places: Int): Double {
        if (places < 0) throw IllegalArgumentException()

        var bd = BigDecimal(value)
        bd = bd.setScale(places, RoundingMode.HALF_UP)
        return bd.toDouble()
    }

    fun round(value: Double): Double {
        if (2 < 0) throw IllegalArgumentException()

        var bd = BigDecimal(value)
        bd = bd.setScale(2, RoundingMode.HALF_UP)
        return bd.toDouble()
    }

    fun round(value: String): String {
        try {
            val dx = d(value)!!
            if (2 < 0) throw IllegalArgumentException()

            var bd = BigDecimal(dx)
            bd = bd.setScale(2, RoundingMode.HALF_UP)


            return s(bd.toDouble())
        } catch (e: Exception) {
            e.printStackTrace()

            return value
        }

    }

    fun statusTransaksi(status: String): String {
        var hasil = "proses"

        when (Integer.parseInt(status)) {
            1 -> hasil = "Proses"
            2 -> hasil = "Belum Transfer"
            3 -> hasil = "Sudah Transfer"
            4 -> hasil = "Selesai"
            9 -> hasil = "hapus"
        }

        return hasil
    }

    /*public static Bitmap getBitmap(String url,Context context)
    {
        FileCache fileCache=new FileCache(context);
        MemoryCache memoryCache=new MemoryCache();
        File f=fileCache.getFile(url);
        //from SD cache
        //CHECK : if trying to decode file which not exist in cache return null
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;
        // Download image file from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            // Constructs a new FileOutputStream that writes to file
            // if file not exist then it will create file
            OutputStream os = new FileOutputStream(f);
            // See Utils class CopyStream method
            // It will each pixel from input stream and
            // write pixels to output stream (file)
            CopyStream(is, os);
            os.close();
            conn.disconnect();
            //Now file created and going to resize file with defined height
            // Decodes image and scales it to reduce memory consumption
            b = decodeFile(f);
            return bitmap;

        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }*/

    //Decodes image and scales it to reduce memory consumption
    private fun decodeFile(f: File): Bitmap? {

        try {
            //Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            val stream1 = FileInputStream(f)
            BitmapFactory.decodeStream(stream1, null, o)
            stream1.close()
            //Find the correct scale value. It should be the power of 2.
            // Set width/height of recreated image
            val REQUIRED_SIZE = 85
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }

            //decode with current scale values
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            val stream2 = FileInputStream(f)
            val bitmap = BitmapFactory.decodeStream(stream2, null, o2)
            stream2.close()
            return bitmap

        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun CopyStream(`is`: InputStream, os: OutputStream) {
        val buffer_size = 1024
        try {
            val bytes = ByteArray(buffer_size)
            while (true) {
                val count = `is`.read(bytes, 0, buffer_size)
                if (count == -1)
                    break
                os.write(bytes, 0, count)
            }
        } catch (ex: Exception) {
        }

    }

    fun getShelter(status: String): String {
        var hasil = "Manggarai"

        when (Integer.parseInt(status)) {
            1 -> hasil = "Manggarai"
            2 -> hasil = "Dukuh Atas"
        }

        return hasil
    }

    fun getExtension(f: File): String? {
        var ext: String? = null
        val s = f.name
        val i = s.lastIndexOf('.')

        if (i > 0 && i < s.length - 1) {
            ext = s.substring(i + 1).toLowerCase()
        }
        return ext
    }

    fun base64ToBitmap(b64: String): Bitmap {
        val imageAsBytes = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun distance(lat1: Double, lng1: Double, lat2: Double, lng2: Double, unit: String): Double {
        var earthRadius = 6371.0
        if (unit.equals("M", ignoreCase = true)) {
            earthRadius = (6371 * 1000).toDouble()
        } else if (unit.equals("N", ignoreCase = true)) {
            earthRadius = 6371.75
        }
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val sindLat = Math.sin(dLat / 2)
        val sindLng = Math.sin(dLng / 2)

        val a = Math.pow(sindLat, 2.0) + Math.pow(sindLng, 2.0) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c

    }

}
