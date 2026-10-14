/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.imaging.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.schema.FocusMove;
import org.onvif.ver10.schema.ImagingOptions20;
import org.onvif.ver10.schema.ImagingSettings20;
import org.onvif.ver10.schema.ImagingStatus20;
import org.onvif.ver10.schema.MoveOptions20;
import org.onvif.ver20.imaging.wsdl.Capabilities;
import org.onvif.ver20.imaging.wsdl.ObjectFactory;

@WebService(targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", name="ImagingPort")
@XmlSeeAlso(value={ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface ImagingPort {
    @WebMethod(operationName="GetStatus", action="http://www.onvif.org/ver20/imaging/wsdl/GetStatus")
    @RequestWrapper(localName="GetStatus", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetStatus")
    @ResponseWrapper(localName="GetStatusResponse", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetStatusResponse")
    @WebResult(name="Status", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl")
    public ImagingStatus20 getStatus(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") String var1);

    @WebMethod(operationName="GetOptions", action="http://www.onvif.org/ver20/imaging/wsdl/GetOptions")
    @RequestWrapper(localName="GetOptions", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetOptions")
    @ResponseWrapper(localName="GetOptionsResponse", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetOptionsResponse")
    @WebResult(name="ImagingOptions", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl")
    public ImagingOptions20 getOptions(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") String var1);

    @WebMethod(operationName="GetImagingSettings", action="http://www.onvif.org/ver20/imaging/wsdl/GetImagingSettings")
    @RequestWrapper(localName="GetImagingSettings", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetImagingSettings")
    @ResponseWrapper(localName="GetImagingSettingsResponse", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetImagingSettingsResponse")
    @WebResult(name="ImagingSettings", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl")
    public ImagingSettings20 getImagingSettings(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") String var1);

    @WebMethod(operationName="Move", action="http://www.onvif.org/ver20/imaging/wsdl/Move")
    @RequestWrapper(localName="Move", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.Move")
    @ResponseWrapper(localName="MoveResponse", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.MoveResponse")
    public void move(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") String var1, @WebParam(name="Focus", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") FocusMove var2);

    @WebMethod(operationName="Stop", action="http://www.onvif.org/ver20/imaging/wsdl/FocusStop")
    @RequestWrapper(localName="Stop", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.Stop")
    @ResponseWrapper(localName="StopResponse", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.StopResponse")
    public void stop(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") String var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver20/imaging/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl")
    public Capabilities getServiceCapabilities();

    @WebMethod(operationName="SetImagingSettings", action="http://www.onvif.org/ver20/imaging/wsdl/SetImagingSettings")
    @RequestWrapper(localName="SetImagingSettings", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.SetImagingSettings")
    @ResponseWrapper(localName="SetImagingSettingsResponse", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.SetImagingSettingsResponse")
    public void setImagingSettings(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") String var1, @WebParam(name="ImagingSettings", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") ImagingSettings20 var2, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") Boolean var3);

    @WebMethod(operationName="GetMoveOptions", action="http://www.onvif.org/ver20/imaging/wsdl/GetMoveOptions")
    @RequestWrapper(localName="GetMoveOptions", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetMoveOptions")
    @ResponseWrapper(localName="GetMoveOptionsResponse", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl", className="org.onvif.ver20.imaging.wsdl.GetMoveOptionsResponse")
    @WebResult(name="MoveOptions", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl")
    public MoveOptions20 getMoveOptions(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver20/imaging/wsdl") String var1);
}

