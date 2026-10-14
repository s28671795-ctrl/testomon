/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.receiver.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.receiver.wsdl.Capabilities;
import org.onvif.ver10.receiver.wsdl.ObjectFactory;
import org.onvif.ver10.schema.Receiver;
import org.onvif.ver10.schema.ReceiverConfiguration;
import org.onvif.ver10.schema.ReceiverMode;
import org.onvif.ver10.schema.ReceiverStateInformation;

@WebService(targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", name="ReceiverPort")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface ReceiverPort {
    @WebMethod(operationName="GetReceivers", action="http://www.onvif.org/ver10/receiver/wsdl/GetReceivers")
    @RequestWrapper(localName="GetReceivers", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.GetReceivers")
    @ResponseWrapper(localName="GetReceiversResponse", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.GetReceiversResponse")
    @WebResult(name="Receivers", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl")
    public List<Receiver> getReceivers();

    @WebMethod(operationName="CreateReceiver", action="http://www.onvif.org/ver10/receiver/wsdl/CreateReceiver")
    @RequestWrapper(localName="CreateReceiver", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.CreateReceiver")
    @ResponseWrapper(localName="CreateReceiverResponse", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.CreateReceiverResponse")
    @WebResult(name="Receiver", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl")
    public Receiver createReceiver(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl") ReceiverConfiguration var1);

    @WebMethod(operationName="ConfigureReceiver", action="http://www.onvif.org/ver10/receiver/wsdl/ConfigureReceiver")
    @RequestWrapper(localName="ConfigureReceiver", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.ConfigureReceiver")
    @ResponseWrapper(localName="ConfigureReceiverResponse", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.ConfigureReceiverResponse")
    public void configureReceiver(@WebParam(name="ReceiverToken", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl") String var1, @WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl") ReceiverConfiguration var2);

    @WebMethod(operationName="GetReceiverState", action="http://www.onvif.org/ver10/receiver/wsdl/GetReceiverState")
    @RequestWrapper(localName="GetReceiverState", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.GetReceiverState")
    @ResponseWrapper(localName="GetReceiverStateResponse", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.GetReceiverStateResponse")
    @WebResult(name="ReceiverState", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl")
    public ReceiverStateInformation getReceiverState(@WebParam(name="ReceiverToken", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl") String var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/receiver/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl")
    public Capabilities getServiceCapabilities();

    @WebMethod(operationName="DeleteReceiver", action="http://www.onvif.org/ver10/receiver/wsdl/DeleteReceiver")
    @RequestWrapper(localName="DeleteReceiver", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.DeleteReceiver")
    @ResponseWrapper(localName="DeleteReceiverResponse", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.DeleteReceiverResponse")
    public void deleteReceiver(@WebParam(name="ReceiverToken", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl") String var1);

    @WebMethod(operationName="SetReceiverMode", action="http://www.onvif.org/ver10/receiver/wsdl/SetReceiverMode")
    @RequestWrapper(localName="SetReceiverMode", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.SetReceiverMode")
    @ResponseWrapper(localName="SetReceiverModeResponse", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.SetReceiverModeResponse")
    public void setReceiverMode(@WebParam(name="ReceiverToken", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl") String var1, @WebParam(name="Mode", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl") ReceiverMode var2);

    @WebMethod(operationName="GetReceiver", action="http://www.onvif.org/ver10/receiver/wsdl/GetReceiver")
    @RequestWrapper(localName="GetReceiver", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.GetReceiver")
    @ResponseWrapper(localName="GetReceiverResponse", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl", className="org.onvif.ver10.receiver.wsdl.GetReceiverResponse")
    @WebResult(name="Receiver", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl")
    public Receiver getReceiver(@WebParam(name="ReceiverToken", targetNamespace="http://www.onvif.org/ver10/receiver/wsdl") String var1);
}

