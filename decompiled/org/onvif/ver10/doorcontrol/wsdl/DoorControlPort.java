/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.Duration;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.doorcontrol.wsdl.AccessDoorExtension;
import org.onvif.ver10.doorcontrol.wsdl.DoorInfo;
import org.onvif.ver10.doorcontrol.wsdl.DoorState;
import org.onvif.ver10.doorcontrol.wsdl.ObjectFactory;
import org.onvif.ver10.doorcontrol.wsdl.ServiceCapabilities;

@WebService(targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", name="DoorControlPort")
@XmlSeeAlso(value={ObjectFactory.class, org.onvif.ver10.pacs.ObjectFactory.class})
public interface DoorControlPort {
    @WebMethod(operationName="AccessDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/AccessDoor")
    @RequestWrapper(localName="AccessDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.AccessDoor")
    @ResponseWrapper(localName="AccessDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.AccessDoorResponse")
    public void accessDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1, @WebParam(name="UseExtendedTime", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") Boolean var2, @WebParam(name="AccessTime", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") Duration var3, @WebParam(name="OpenTooLongTime", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") Duration var4, @WebParam(name="PreAlarmTime", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") Duration var5, @WebParam(name="Extension", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") AccessDoorExtension var6);

    @WebMethod(operationName="UnlockDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/UnlockDoor")
    @RequestWrapper(localName="UnlockDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.UnlockDoor")
    @ResponseWrapper(localName="UnlockDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.UnlockDoorResponse")
    public void unlockDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);

    @WebMethod(operationName="LockDownReleaseDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/LockDownReleaseDoor")
    @RequestWrapper(localName="LockDownReleaseDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockDownReleaseDoor")
    @ResponseWrapper(localName="LockDownReleaseDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockDownReleaseDoorResponse")
    public void lockDownReleaseDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);

    @WebMethod(operationName="GetDoorInfoList", action="http://www.onvif.org/ver10/doorcontrol/wsdl/GetDoorInfoList")
    @RequestWrapper(localName="GetDoorInfoList", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.GetDoorInfoList")
    @ResponseWrapper(localName="GetDoorInfoListResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.GetDoorInfoListResponse")
    public void getDoorInfoList(@WebParam(name="Limit", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") Integer var1, @WebParam(name="StartReference", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var2, @WebParam(mode=WebParam.Mode.OUT, name="NextStartReference", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") Holder<String> var3, @WebParam(mode=WebParam.Mode.OUT, name="DoorInfo", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") Holder<List<DoorInfo>> var4);

    @WebMethod(operationName="LockOpenDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/LockOpenDoor")
    @RequestWrapper(localName="LockOpenDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockOpenDoor")
    @ResponseWrapper(localName="LockOpenDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockOpenDoorResponse")
    public void lockOpenDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);

    @WebMethod(operationName="DoubleLockDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/DoubleLockDoor")
    @RequestWrapper(localName="DoubleLockDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.DoubleLockDoor")
    @ResponseWrapper(localName="DoubleLockDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.DoubleLockDoorResponse")
    public void doubleLockDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);

    @WebMethod(operationName="LockDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/LockDoor")
    @RequestWrapper(localName="LockDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockDoor")
    @ResponseWrapper(localName="LockDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockDoorResponse")
    public void lockDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);

    @WebMethod(operationName="LockOpenReleaseDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/LockOpenReleaseDoor")
    @RequestWrapper(localName="LockOpenReleaseDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockOpenReleaseDoor")
    @ResponseWrapper(localName="LockOpenReleaseDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockOpenReleaseDoorResponse")
    public void lockOpenReleaseDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);

    @WebMethod(operationName="GetDoorInfo", action="http://www.onvif.org/ver10/doorcontrol/wsdl/GetDoorInfo")
    @RequestWrapper(localName="GetDoorInfo", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.GetDoorInfo")
    @ResponseWrapper(localName="GetDoorInfoResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.GetDoorInfoResponse")
    @WebResult(name="DoorInfo", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl")
    public List<DoorInfo> getDoorInfo(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") List<String> var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/doorcontrol/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl")
    public ServiceCapabilities getServiceCapabilities();

    @WebMethod(operationName="LockDownDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/LockDownDoor")
    @RequestWrapper(localName="LockDownDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockDownDoor")
    @ResponseWrapper(localName="LockDownDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.LockDownDoorResponse")
    public void lockDownDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);

    @WebMethod(operationName="BlockDoor", action="http://www.onvif.org/ver10/doorcontrol/wsdl/BlockDoor")
    @RequestWrapper(localName="BlockDoor", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.BlockDoor")
    @ResponseWrapper(localName="BlockDoorResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.BlockDoorResponse")
    public void blockDoor(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);

    @WebMethod(operationName="GetDoorState", action="http://www.onvif.org/ver10/doorcontrol/wsdl/GetDoorState")
    @RequestWrapper(localName="GetDoorState", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.GetDoorState")
    @ResponseWrapper(localName="GetDoorStateResponse", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl", className="org.onvif.ver10.doorcontrol.wsdl.GetDoorStateResponse")
    @WebResult(name="DoorState", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl")
    public DoorState getDoorState(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/doorcontrol/wsdl") String var1);
}

