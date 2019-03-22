package tw.dddssz.allschoolthings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URI;
import java.text.SimpleDateFormat;

public class CampusFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, LocationListener
{
    private GoogleMap mMap;
    private CircleImgbutton CircleImgbutton_map;
    private String TAG="trnr08=>";

    //24.1704367,120.610006
    private String Lat = "24.1704367";
    private String Lon = "120.610006";
    private String jcontent;// 地名變數
    /*** GPS*/
    private LocationManager locationMgr;
    private String provider; // 提供資料
    private TextView txtOutput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campus, container, false);
//        return inflater.inflate(R.layout.fragment_campus,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setupViewComponent();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if(getActivity()!=null) {
//            MapFragment mapFragment = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map);
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }



    private void setupViewComponent()
    {
        txtOutput = (TextView)getView().findViewById(R.id.lblOutput);
        CircleImgbutton_map = (CircleImgbutton)getView().findViewById(R.id.CircleImgbutton_map);
        // 動態調整高度 抓取使用裝置尺寸
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // String px = displayMetrics.widthPixels + " x " +
        // displayMetrics.heightPixels;
        // String dp = displayMetrics.xdpi + " x " + displayMetrics.ydpi;
        // String density = "densityDpi = " + displayMetrics.densityDpi +
        // ", density=" + displayMetrics.density + ", scaledDensity = " +
        // displayMetrics.scaledDensity;
        // myname.setText(px + "\n" + dp + "\n" +density + "\n" +
        // newscrollheight);
        int newmargin = displayMetrics.widthPixels * 1 / 6; // 設定ScrollView使用尺寸的4/5
        int newmargin1 = displayMetrics.widthPixels * 5 / 6; // 設定ScrollView使用尺寸的4/5
        //viewPager.getLayoutParams().width = newviewPagerwidth;  // 重定ScrollView大小
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(newmargin,newmargin);  //RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        fp.setMargins(newmargin1,0,0,0);
        CircleImgbutton_map.setLayoutParams(fp);
        CircleImgbutton_map.setOnClickListener(this);
    }



    @Override
    public void onClick(View v)
    {
        Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr="+Lat+"%20"+Lon+"&daddr=24.1704367%20120.610006&hl=en");
        Intent it = new Intent(Intent.ACTION_VIEW , uri);
        startActivity(it);
    }


    /* 開啟時先檢查是否有啟動GPS精緻定位 */
    @Override
    public void onStart() {
        super.onStart();

        if (initLocationProvider()) {
            nowaddress();
        } else {
            txtOutput.setText("GPS未開啟,請先開啟定位！");
        }
    }
    @Override
    public void onStop() {
        locationMgr.removeUpdates(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    /************************************************
     * GPS部份
     ***********************************************/
    /* 檢查GPS 設定GPS服務 */
    private boolean initLocationProvider() {
        locationMgr = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            return true;
        }
        return false;
    }
    // -------------------------------
    GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
        /* 監聽GPS 狀態 */
        @Override
        public void onGpsStatusChanged(int event) {
            switch (event) {
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.d(TAG, "GPS_EVENT_STARTED");
                    break;

                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.d(TAG, "GPS_EVENT_STOPPED");
                    break;

                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.d(TAG, "GPS_EVENT_FIRST_FIX");
                    break;

                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Log.d(TAG, "GPS_EVENT_SATELLITE_STATUS");
                    break;
            }
        }
    };
    /* 建立位置改變偵聽器 預先顯示上次的已知位置 */
    private void nowaddress() {
        //檢查是否有權限-------------------------------------------
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationMgr.getLastKnownLocation(provider);
        updateWithNewLocation(location);

        // 監聽 GPS Listener
        locationMgr.addGpsStatusListener(gpsListener);

        // Location Listener
        long minTime = 5000;// ms
        float minDist = 5.0f;// meter
        locationMgr.requestLocationUpdates(provider, minTime, minDist,
                this);
    }

    private void updateWithNewLocation(Location location) {
        String where = "";
        if (location != null) {

            double lng = location.getLongitude();// 經度
            double lat = location.getLatitude();// 緯度
            float speed = location.getSpeed();// 速度
            long time = location.getTime();// 時間
            String timeString = getTimeString(time);

            where = "經度: " + lng + "\n緯度: " + lat + "\n速度: " + speed + "\n時間: "
                    + timeString + "\nProvider: " + provider;
            // 標記"我的位置"
//            locations[0][1] = lat + "," + lng; // 用GPS找到的位置更換 陣列的目前位置
            Lat = String.valueOf(lat);
            Lon = String.valueOf(lng);

            // --- 呼叫 Map JS
//            webView.loadUrl(MAP_URL);
            // ---
        } else {
            where = "*位置訊號消失*";
        }
        // 位置改變顯示
        txtOutput.setText(where);
    }

    private String getTimeString(long timeInMilliseconds) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timeInMilliseconds);
    }

    //===	/* 位置變更狀態監視 */======================
    @Override
    public void onLocationChanged(Location location) {
        // 定位改變時
        updateWithNewLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                Log.v(TAG, "Status Changed: Out of Service");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.v(TAG, "Status Changed: Temporarily Unavailable");
                break;
            case LocationProvider.AVAILABLE:
                Log.v(TAG, "Status Changed: Available");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        updateWithNewLocation(null);
        Log.d(TAG, "onProviderDisabled");
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
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(24.1704367,120.610006);  //中心點位置24.171385 , 120.609917
        mMap.addMarker(new MarkerOptions().position(sydney)
                .title("中彰投分署")  //點下標記後會出現的標題
                .snippet("歡迎光臨中彰投分署")
        );
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney , 15 )  );  //後面放地圖放大倍率
    }


}
