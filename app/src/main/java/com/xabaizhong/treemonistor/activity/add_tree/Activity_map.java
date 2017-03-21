package com.xabaizhong.treemonistor.activity.add_tree;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xabaizhong.treemonistor.R;
import com.xabaizhong.treemonistor.base.Activity_base;
import com.xabaizhong.treemonistor.base.App;
import com.xabaizhong.treemonistor.service.LocationService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING;

/**
 * Created by admin on 2017/3/7.
 */

public class Activity_map extends Activity_base {


    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.flag_center)
    ImageView flag;

    BaiduMap baiduMap;


    LocationService locationService;
    @BindView(R.id.center)
    ImageView center;
    @BindView(R.id.btn_mode)
    Button btnMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initSource();
        initilize();

    }


    private void initSource() {
        baiduMap = bmapView.getMap();
        initBaiduMap();
        n();
        flag.setAnimation(null);
    }
    BitmapDescriptor mIconLocation;
    void initBaiduMap() {
        baiduMap.setMyLocationEnabled(true);
        mLocationMode = FOLLOWING;
        BitmapDescriptor mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mLocationMode,true,mIconLocation));
        btnMode.setText("跟随模式");
    }

    private void initilize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestpermission();
        } else {
            init();
        }
    }


    private void init() {


        locationService = ((App) getApplicationContext()).locationService;
        locationService.setLocationOption(getDefaultOption());
        locationService.registerListener(myListener);
        locationService.start();
    }


    private static final int BAIDU_READ_PHONE_STATE = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestpermission() {
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)

            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);


        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    flag = (grantResults[i] == 0);
                    if (!flag) {
                        break;
                    }
                }
                if (flag) {
                    init();
                    showToast("suc");
                } else {
                    finish();
                }
                break;

            default:
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void stopLoc() {
        locationService.unregisterListener(myListener);
        locationService.stop();
    }

    @Override
    protected void onDestroy() {
        stopLoc();

        super.onDestroy();
    }


    public BDLocationListener myListener = new MyLocationListener(this);


    boolean isFirst = true;

    /**
     * 首次定位 移动到mapview 中间位置
     *
     * @param location
     */
    private void navigateTo(LatLng location) {
        //如果是第一次创建，就获取位置信息并且将地图移到当前位置
        //为防止地图被反复移动，所以就只在第一次创建时执行
        if (isFirst) {
            //LatLng对象主要用来存放经纬度
            //zoomTo是用来设置百度地图的缩放级别，范围为3~19，数值越大越精确
            LatLng ll = new LatLng(location.latitude, location.longitude);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirst = false;
        }

        //封装设备当前位置并且显示在地图上
        //由于设备在地图上显示的位置会根据我们当前位置而改变，所以写到if外面
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.latitude);
        locationBuilder.longitude(location.longitude);
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);

    }

    LatLng currentLatatLng = new LatLng(0.0, 0.0);

    private static final double EARTH_RADIUS = 6370996.81;

    private static double radian(double d) {
        return d * Math.PI / 180.0;
    }

    boolean checkNeedUpdate(LatLng latLng) {
        if (currentLatatLng.longitude * currentLatatLng.latitude == 0)
            return true;
        if (distanceOfTwoPoints(currentLatatLng, latLng) < 5000) {
            return false;
        }
        return true;
    }

    /**
     * @param lat1
     * @param lat2
     * @return m
     */
    public double distanceOfTwoPoints(LatLng lat1, LatLng lat2) {
        double radLat1 = radian(lat1.latitude);
        double radLat2 = radian(lat2.latitude);
        double a = radLat1 - radLat2;
        double b = radian(lat2.longitude) - radian(lat2.longitude);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        double ss = s * 1.0936132983377;
        return ss;
    }


    private void n() {

        final LocationMode locationMode = FOLLOWING;
        baiduMap.setMyLocationConfigeration
                (new MyLocationConfiguration(locationMode, true, null));

        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                Log.i(TAG, "onMapStatusChange: _____________");
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng latLng = mapStatus.target;
                currentLatatLng = latLng;
                Log.i(TAG, "onMapStatusChangeFinish: " + latLng.latitude + "\t" + latLng.longitude);
                GeoCoder geoCoder = GeoCoder.newInstance();
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                        locationBox.setProvince(reverseGeoCodeResult.getAddressDetail().province);
                        locationBox.setCity(reverseGeoCodeResult.getAddressDetail().city);
                        locationBox.setDistrict(reverseGeoCodeResult.getAddressDetail().district);
                        locationBox.setStreet(reverseGeoCodeResult.getAddressDetail().street);
                        locationBox.setSematicDescription(reverseGeoCodeResult.getSematicDescription());
                        locationBox.setLon(reverseGeoCodeResult.getLocation().longitude);
                        locationBox.setLat(reverseGeoCodeResult.getLocation().latitude);
                    }
                });

            }
        });
    }


    private LocationClientOption getDefaultOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        return option;
    }

    LocationMode mLocationMode;

    @OnClick({R.id.btn, R.id.btn_mode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Intent i = new Intent();
                i.putExtra("location", locationBox);
                setResult(100, i);
                if (locationBox.check()) {
                    finish();
                } else {
                    showToast("未获取到地理位置的信息,请检查网络是否连接");
                }
                break;
            case R.id.btn_mode:
                switch (mLocationMode) {
                    case FOLLOWING:
                        mLocationMode = LocationMode.NORMAL;
                        btnMode.setText("默认模式");
                        break;
                    case NORMAL:
                        mLocationMode = FOLLOWING;
                        btnMode.setText("跟随模式");
                        break;
                }
                baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mLocationMode,true,mIconLocation));
                break;
        }
    }


    /*custom location listener */
    static class MyLocationListener implements BDLocationListener {
        Activity_map activity_map;

        public MyLocationListener(Activity_map activity_map) {
            this.activity_map = activity_map;
        }

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                activity_map.navigateTo(latLng);

                if (activity_map.mLocationMode == FOLLOWING) {
                    Log.i("listener", "onReceiveLocation: follow ing");
                    activity_map.locationBox.setProvince(location.getProvince());
                    activity_map.locationBox.setCity(location.getCity());
                    activity_map.locationBox.setDistrict(location.getDistrict());
                    activity_map.locationBox.setStreet(location.getStreet());
                    activity_map.locationBox.setSematicDescription(location.getLocationDescribe());
                    activity_map.locationBox.setLat(location.getLatitude());
                    activity_map.locationBox.setLon(location.getLongitude());
                    location.getAltitude();
                }
            }
            activity_map.baiduLog(location);

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private void baiduLog(BDLocation location) {
        //获取定位结果
        StringBuffer sb = new StringBuffer(256);

        sb.append("time : ");
        sb.append(location.getTime());    //获取定位时间

        sb.append("\nerror code : ");
        sb.append(location.getLocType());    //获取类型类型

        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());    //获取纬度信息

        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());    //获取经度信息

        sb.append("\nradius : ");
        sb.append(location.getRadius());    //获取定位精准度

        if (location.getLocType() == BDLocation.TypeGpsLocation) {

            // GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());    // 单位：公里每小时

            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());    //获取卫星数

            sb.append("\nheight : ");
            sb.append(location.getAltitude());    //获取海拔高度信息，单位米

            sb.append("\ndirection : ");
            sb.append(location.getDirection());    //获取方向信息，单位度

            sb.append("\naddr : ");
            sb.append(location.getAddrStr());    //获取地址信息

            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

            // 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());    //获取地址信息
            sb.append("\naddr");
            sb.append(location.getCity());
            sb.append("\noperationers : ");
            sb.append(location.getOperators());    //获取运营商信息

            sb.append("\ndescribe : ");
            sb.append("网络定位成功");

        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

            // 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");

        } else if (location.getLocType() == BDLocation.TypeServerError) {

            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

        }

        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());    //位置语义化信息
        List<Poi> list = location.getPoiList();    // POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }

        Log.i("BaiduLocationApiDem", sb.toString());
    }


    LocationBox locationBox = new LocationBox();

    public static class LocationBox implements Parcelable {
        private String province;
        private String city;
        private String district;
        private String street;
        private String sematicDescription;
        private Double lat;
        private Double lon;

        public LocationBox() {
        }

        protected LocationBox(Parcel in) {
            province = in.readString();
            city = in.readString();
            district = in.readString();
            street = in.readString();
            sematicDescription = in.readString();
        }

        public static final Creator<LocationBox> CREATOR = new Creator<LocationBox>() {
            @Override
            public LocationBox createFromParcel(Parcel in) {
                return new LocationBox(in);
            }

            @Override
            public LocationBox[] newArray(int size) {
                return new LocationBox[size];
            }
        };

        public boolean check() {
            if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(district)) {
                return false;
            }
            return true;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(province);
            dest.writeString(city);
            dest.writeString(district);
            dest.writeString(street);
            dest.writeString(sematicDescription);
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSematicDescription() {
            return sematicDescription;
        }

        public void setSematicDescription(String sematicDescription) {
            this.sematicDescription = sematicDescription;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }
    }
}
