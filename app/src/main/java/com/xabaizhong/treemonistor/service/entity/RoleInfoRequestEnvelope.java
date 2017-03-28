/*
package com.xabaizhong.treemonistor.service.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

*/
/**
 * Created by admin on 2017/3/28.
 *//*


@Root(name = "soapenv:Envelope")
@NamespaceList({
       */
/* @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/encoding/", prefix = "enc"),*//*

        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soapenv"),
        @Namespace(reference = "http://tempuri.org/", prefix = "tem")
})
public class RoleInfoRequestEnvelope {
    @Element(name = "soapenv:Body", required = false)
    public RoleInfoRequestBody body;

    @Element(name = "soapenv:Header", required = false)
    public String aHeader;

}*/
