package com.example.zijie.card;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;

import java.util.ArrayList;
import java.util.List;

public class loginActivity extends AppCompatActivity  {
    public LocationClient locationClient;
    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap ;
    private boolean status= true;

    private void requestLocation()
    {
        init();
        locationClient.start();
    }

    private void locationTo(BDLocation location)
    {
        if(status)
        {
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(15f);
            baiduMap.animateMapStatus(update);
            status = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);
    }


    private  void init()
    {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
//        baiduMap.setMyLocationEnabled(true);
        setContentView(R.layout.activity_login);
        mapView = findViewById(R.id.map);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);


        positionText = findViewById(R.id.location);
        Button select_time = findViewById(R.id.select_time);
        List<String> permissionList = new ArrayList<>();
        //权限申请
        if(ContextCompat.checkSelfPermission(loginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(loginActivity.this,
                Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(loginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty())
        {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(loginActivity.this,permissions,1);

        }
        else
        {
            requestLocation();
        }




        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopWindow popWindow = new PopWindow.Builder(loginActivity.this)
                        .setStyle(PopWindow.PopWindowStyle.PopUp)
                        .setTitle("选择一个时间")
                        .addItemAction(new PopItemAction("今天"))
                        .addItemAction(new PopItemAction("明天",PopItemAction.PopItemStyle.Normal))
                        .addItemAction(new PopItemAction("后天",PopItemAction.PopItemStyle.Normal,
                                new PopItemAction.OnClickListener()
                                {
                                    @Override
                                    public void onClick() {
                                        Toast.makeText(loginActivity.this, "选项3", Toast.LENGTH_SHORT).show();
                                    }
                                }))
                        .addItemAction(new PopItemAction("确定",PopItemAction.PopItemStyle.Warning,new PopItemAction.OnClickListener()
                        {
                            @Override
                            public void onClick() {
                                Toast.makeText(loginActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            }
                        }))
                        .addItemAction(new PopItemAction("取消",PopItemAction.PopItemStyle.Cancel))
                        .create();
                popWindow.show();
            }
        });


    }
//    private  void requestLocation()
//    {
//        locationClient.start();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults.length>0)
            {
                for(int result : grantResults)
                {
                    if(result != PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this,"必须同意权限！",Toast.LENGTH_SHORT).show();
                    }
                }
                requestLocation();
            }
            else
            {
                Toast.makeText(this,"位置问题",Toast.LENGTH_SHORT).show();

            }
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(location.getLatitude())
                            .append("\n");
                    currentPosition.append("经度：").append(location.getLongitude())
                            .append("\n");
            currentPosition.append("国家：").append(location.getCountry())
                    .append("\n");
            currentPosition.append("省：").append(location.getProvince())
                    .append("\n");
            currentPosition.append("市：").append(location.getCity())
                    .append("\n");
            currentPosition.append("区：").append(location.getDistrict())
                    .append("\n");
            currentPosition.append("街道：").append(location.getStreet())
                    .append("\n");
                    currentPosition.append("定位方式");

                    if(location.getLocType() == BDLocation.TypeGpsLocation)
                    {
                        currentPosition.append("gps");
                        locationTo(location);
                    }
                    else if(location.getLocType() == BDLocation.TypeNetWorkLocation)
                    {
                        currentPosition.append("网络");
                        locationTo(location);
                    }
                    positionText.setText(currentPosition);



        }
    }
}
