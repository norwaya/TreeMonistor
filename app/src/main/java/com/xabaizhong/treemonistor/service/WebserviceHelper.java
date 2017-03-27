package com.xabaizhong.treemonistor.service;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.ConnectException;

public class WebserviceHelper {

	/**
	 * 
	 * @param nameSpace
	 * @param methodName
	 * @param parameter
	 * @param url
	 * @param action
	 * @return
	 */
/*	public static String GetWebService(String nameSpace, String methodName, String parameter,
			String parameter2,String url, String action,String action2) {
*/
	public static String GetWebService(String nameSpace, String methodName, String parameter,
                                       String url, String action) {
		String TAG = "GetWebService";
		SoapObject soapObj = new SoapObject(nameSpace, methodName);
		soapObj.addProperty(parameter, action);
		//soapObj.addProperty(parameter2, action2);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.bodyOut = soapObj;
		envelope.dotNet = true;

		HttpTransportSE transport = new HttpTransportSE(url);

		try {
			/**
			 * ���������ȡ����
			 */
			transport.call(nameSpace + methodName, envelope);
			SoapObject soapReault = (SoapObject) envelope.bodyIn;
			Log.i("main", "GetWebService: "+soapReault);
			String result = soapReault.getProperty(0).toString();
			return result;

		} catch (SoapFault e) {
			Log.i(TAG, "GetWebService: SoapFault");
			return e.getMessage();

		} catch (ConnectException e) {
			Log.i(TAG, "GetWebService: ConnectException");
			/**
			 * ����������
			 */
			return "ConnectException";

		} catch (IOException e) {
			Log.i(TAG, "GetWebService: IOException");
			return e.getMessage();

		} catch (XmlPullParserException e) {
			Log.i(TAG, "GetWebService: XmlPullParserException");
			return e.getMessage();

		} catch (Exception e) {
			Log.i(TAG, "GetWebService: Exception");
			return e.getMessage();

		}
	}
}
