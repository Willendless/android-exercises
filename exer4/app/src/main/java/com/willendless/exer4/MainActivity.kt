package com.willendless.exer4

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.location.*
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var locationClient: LocationClient
    private var isFirstLocate = true
    private lateinit var map: BaiduMap

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SDKInitializer.initialize(applicationContext)
        locationClient = LocationClient(applicationContext)
        locationClient.registerLocationListener(MyLocationListener())

        setContentView(R.layout.activity_main)
        map = mapView.map
        map.isMyLocationEnabled = true

        val permissionList = ArrayList<String>()
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(this, it)
                != PackageManager.PERMISSION_GRANTED)
                    permissionList.add(it)
        }
        if (permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionList.toTypedArray(), 1
            );
        } else {
            requestLocation()
        }

        button.setOnClickListener {
            val ecnu = LatLng(31.233168, 121.412119)
            map.animateMapStatus(MapStatusUpdateFactory.newLatLng(ecnu))
        }
    }

    // 开始周期性更新位置信息
    private fun requestLocation() {
        locationClient.locOption = LocationClientOption().apply {
            // 每5s更新一次
            setScanSpan(5000)
            locationMode = LocationClientOption.LocationMode.Hight_Accuracy
            setIsNeedAddress(true)
            setIsNeedLocationDescribe(true)
            setIsNeedLocationPoiList(true)
        }
        locationClient.start()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.stop()
        mapView.onDestroy()
        map.isMyLocationEnabled = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty()) {
                for (r in grantResults) {
                    if (r != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(
                            this,
                            "必须同意所有权限才能使用本程序",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        return
                    }
                }
                requestLocation()
            } else {
                Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun navigateTo(location: BDLocation) {
        if (isFirstLocate) {
            Log.d("MainActivity", " " + location.latitude)
            Log.d("MainActivity", " " + location.longitude)
            Toast.makeText(this, "nav to " + location.addrStr, Toast.LENGTH_SHORT).show()
            val ll = LatLng(location.latitude, location.longitude)
            var update = MapStatusUpdateFactory.newLatLng(ll)
            map.animateMapStatus(update)
            update = MapStatusUpdateFactory.zoomTo(17f)
            map.animateMapStatus(update)
            isFirstLocate = false
        }

        val locationBuilder = MyLocationData.Builder()
        locationBuilder.latitude(location.latitude)
        locationBuilder.longitude(location.longitude)
        map.setMyLocationData(locationBuilder.build())
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(p0: BDLocation?) {
            if (p0 != null && (p0.locType == BDLocation.TypeNetWorkLocation ||
                        p0.locType == BDLocation.TypeGpsLocation)) {
                val locationText = StringBuilder()
                locationText.append("纬度:").append(p0.latitude).append("\n")
                locationText.append("经度:").append(p0.longitude).append("\n")
                locationText.append("国家:").append(p0.country).append("\n")
                locationText.append("省:").append(p0.province).append("\n")
                locationText.append("市:").append(p0.city).append("\n")
                locationText.append("区:").append(p0.district).append("\n")
                locationText.append("街道:").append(p0.street).append("\n")
                locationText.append("定位方式:")
                if (p0.locType == BDLocation.TypeGpsLocation)
                    locationText.append("GPS")
                else if (p0.locType == BDLocation.TypeNetWorkLocation)
                    locationText.append("网络")

                textView.text = locationText
                navigateTo(p0)
            }
        }
    }
}