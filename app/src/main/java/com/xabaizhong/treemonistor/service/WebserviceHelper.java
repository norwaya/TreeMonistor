package com.xabaizhong.treemonistor.service;

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
//    static String base = "http://192.168.0.118:8055/";
    static String base = "http://117.34.105.28:8055/";
    static String TAG = "GetWebService";

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
            throws ConnectException {
        String nameSpace = "http://tempuri.org/";
//        String nameSpace = "http://tempuri.org/";
        String url = base + url_d + ".asmx?wsdl";

        SoapObject soapObj = new SoapObject(nameSpace, methodName);
        Log.i(TAG, "GetWebService: " + url_d + "\t" + methodName);
        if (map != null)
            for (String key : map.keySet()) {
                soapObj.addProperty(key, map.get(key));
                Log.i(TAG, "GetWebService: " + key + map.get(key));
            }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObj;
        envelope.dotNet = true;

        HttpTransportSE transport = new HttpTransportSE(url);

        try {
            transport.call(nameSpace + methodName, envelope);
            Object obj = envelope.bodyIn;
            SoapObject soapReault = (SoapObject) envelope.bodyIn;
            return soapReault.getProperty(0).toString();

        } catch (SoapFault e) {
            Log.i(TAG, "GetWebService: SoapFault");

        } catch (IOException e) {
            Log.i(TAG, "GetWebService: IOException");

        } catch (XmlPullParserException e) {
            Log.i(TAG, "GetWebService: XmlPullParserException");

        } catch (IllegalArgumentException e) {
            Log.i(TAG, "GetWebService: IllegalArgumentException");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
