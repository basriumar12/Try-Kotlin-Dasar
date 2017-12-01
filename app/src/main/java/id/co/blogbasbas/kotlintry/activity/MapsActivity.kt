package id.co.blogbasbas.kotlintry

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import id.co.blogbasbas.kotlintry.helper.DirectionMapsV2
import id.co.blogbasbas.kotlintry.helper.GPSTracker
import id.co.blogbasbas.kotlintry.network.ApiService
import id.co.blogbasbas.kotlintry.responRoute.ResponseDirection
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    var koordinat : LatLng? = null
    var lat1 : Double? = null
    var lon1 : Double? = null
     var lat2 : Double? = null
    var lon2 : Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //permission marsmellow
        var permission = (android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(permission), 2)

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //tahap 1
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //deklarsa class gps  >> tahap 5
        var gps = GPSTracker(this)
        if (gps.canGetLocation()){
             lat1 = gps.latitude
             lon1 = gps.longitude

            var lokasiku = LatLng(lat1!!, lon1!!)
            mMap!!.addMarker(MarkerOptions().position(lokasiku))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(lokasiku))
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiku,13f))

            //convert koordinat jadi nama lokasi
            var geocoder = Geocoder(this, Locale.getDefault())
            var lokasi = geocoder.getFromLocation(lat1!!,lon1!!, 2)
            //ambil jalan & negara
            var name = lokasi.get(0).getAddressLine(0)+","+lokasi.get(0).countryName
            var name2 = lokasi.get(1).countryName
            mMap!!.addMarker(MarkerOptions().position(lokasiku).title(name).snippet(name2))

        } else {
            gps.showSettingGps()
        }


        // Add a marker in Sydney and move the camera
      /*  val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions()
                .position(sydney)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)) //icon marker default
                .title("This Marker"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/


        //tahap 4
        dariMana.setOnClickListener {
            var intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this)
            startActivityForResult(intent, 10)
        }

        mauKemana.setOnClickListener {
            var intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this)
            startActivityForResult(intent, 11)

        }

        //icon marker drawable
        /*  // mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_add))
              //  .position(mapCenter)
                .flat(true)
                .rotation(245));*/
    }
    //tahap 2
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    //tahap 3
    if (requestCode == 10 && data != null){
        //get data klik user / tahap 4
        var place = PlaceAutocomplete.getPlace(this, data)
        //get koordinat
          lat1 = place.latLng.latitude
          lon1 = place.latLng.longitude
         koordinat = place.latLng
        //ambil nama lokasi
        var nameLocation = place.address.toString()


        //tambahkan marker sesuai koordinat di atas
        mMap?.addMarker(MarkerOptions().position(koordinat!!)
                .snippet(place.name.toString())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title(nameLocation))
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(koordinat,15f))
        //atktvkan zoom
        mMap?.uiSettings?.isZoomControlsEnabled = true

        var ltbon = LatLngBounds.builder()
        ltbon.include(koordinat)
        mMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(ltbon.build(), 12))
        dariMana.text = nameLocation



    } else if (requestCode ==11 && data != null){
        var place = PlaceAutocomplete.getPlace(this, data)
        //get koordinat
          lat2 = place.latLng.latitude
          lon2 = place.latLng.longitude
        koordinat = place.latLng
        //ambil nama lokasi
        var nameLocation = place.address.toString()


        //tambahkan marker sesuai koordinat di atas
        mMap?.addMarker(MarkerOptions().position(koordinat!!)
                .snippet(place.name.toString())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .title(nameLocation))
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(koordinat,15f))
        //atktvkan zoom
        mMap?.uiSettings?.isZoomControlsEnabled = true
        var ltbon = LatLngBounds.builder()
        ltbon.include(koordinat)
        mMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(ltbon.build(), 12))
        mauKemana.text = nameLocation
        actionRoute()
    }

    }

    //ke 6
    private fun actionRoute() {
        var retrofit = Retrofit.Builder()
                //buat respon dan ambil alamat
                //https://developers.google.com/maps/documentation/directions/
                .baseUrl("https://maps.googleapis.com/maps/api/directions/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var serviceRoute = retrofit.create(ApiService::class.java)
        serviceRoute.route(lat1.toString()+","+lon1.toString(), lat2.toString()+","+lon2.toString())
                .enqueue(object : Callback<ResponseDirection> {
                    override fun onResponse(call: retrofit2.Call<ResponseDirection>?, response: Response<ResponseDirection>?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    if (response?.isSuccessful!!){
                        //ambil json object route
                        var route = response.body()?.routes
                        //setelah ambil route ambil 0
                        //get json object 0
                        var nol = route?.get(0)
                        //get objet overview dari object nol
                        var overview = nol?.overviewPolyline
                        //ambil object string points
                        var point = overview?.points
                        //gambarkan di maps

                        var direction = DirectionMapsV2(this@MapsActivity)
                        direction.gambarRoute(mMap!!, point!!)
                        var legs = route?.get(0)?.legs

                        var distance = legs?.get(0)?.distance
                        var dist =Math.ceil(distance?.value?.toDouble()!!/1000)
                        jarak.setText(distance?.text.toString())

                        var duration = legs?.get(0)?.duration
                        time.setText(duration?.text.toString())

                        harga.setText("Rp. " + (dist* 2500).toString())



                    }


                    }

                    override fun onFailure(call: retrofit2.Call<ResponseDirection>?, t: Throwable?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        Log.d("error "," respon ", t)
                    }
                })

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
