package com.xabaizhong.treemonistor.service;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Debug;
import android.util.DebugUtils;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.Map;

public class WebserviceHelper {
    static String local = "http://192.168.0.118:8055/";
    static String remote = "http://117.34.105.28:8055/";
    static String TAG = "GetWebService";

    public static boolean switch_ip = true;

    public static String getIp() {
        return switch_ip ? remote : local;
    }

    /**
     * @param url_d      接口名称
     * @param methodName 方法名称
     * @param map
     * @return
     */
/*	public static String GetWebService(String nameSpace, String methodName, String parameter,
            String parameter2,String url, String action,String action2) {
*/
    public static String GetWebService(String url_d, String methodName, Map<String, Object> map)
            throws IOException, XmlPullParserException {
        long startTime = System.nanoTime();

        String WSDL_URI = switch_ip ? "http://117.34.105.28:8055/" + url_d + ".asmx?WSDL" :
                "http://192.168.0.118:8055/" + url_d + ".asmx?WSDL";//wsdl 的uri
        String namespace = "http://tempuri.org/";//namespace
        SoapObject request = new SoapObject(namespace, methodName);
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        if (map != null)
            for (String key : map.keySet()) {
                request.addProperty(key, map.get(key));
                Log.i(TAG, "GetWebService: " + key + map.get(key));
            }
        //创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.bodyOut = request;//由于是发送请求，所以是设置bodyOut
        envelope.dotNet = true;//由于是.net开发的webservice，所以这里要设置为true
        HttpTransportSE httpTransportSE = new HttpTransportSE(WSDL_URI);

        httpTransportSE.call("http://tempuri.org/" + methodName, envelope);//调用

//        Log.i(TAG, "GetWebService: response"+ (envelope.getResponse()));
        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        Log.i(TAG, "GetWebService: using time : " + WSDL_URI + "\t" + methodName +((System.nanoTime() - startTime) / 1e9d));
        return object.getProperty(0).toString();
//        String nameSpace = "http://tempuri.org/";
//        String url = switch_ip ? remote : local + url_d + ".asmx?WSDL";
//        SoapObject soapObj = new SoapObject(nameSpace, methodName);
//        Log.i(TAG, "GetWebService: " + url + "\t" + url_d + "\t" + methodName);
//        if (map != null)
//            for (String key : map.keySet()) {
//                soapObj.addProperty(key, map.get(key));
//                Log.i(TAG, "GetWebService: " + key + map.get(key));
//            }
//
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
//        envelope.setOutputSoapObject(soapObj);
////        envelope.bodyOut = soapObj;
//        envelope.dotNet = true;
////        AndroidHttpTransport transport = new AndroidHttpTransport(url);
//        HttpTransportSE transport = new HttpTransportSE(url);
//
//        try {
//            transport.call(nameSpace + methodName, envelope);
//            Object obj = envelope.bodyIn;
//            SoapObject soapReault = (SoapObject) envelope.bodyIn;
//            return soapReault.getProperty(0).toString();
//
//        } catch (SoapFault e) {
//            Log.i(TAG, "GetWebService: SoapFault");
//
//        } catch (IOException e) {
//            Log.i(TAG, "GetWebService: IOException");
//
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//            Log.i(TAG, "GetWebService: XmlPullParserException");
//
//        } catch (IllegalArgumentException e) {
//            Log.i(TAG, "GetWebService: IllegalArgumentException");
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;

    }
}
