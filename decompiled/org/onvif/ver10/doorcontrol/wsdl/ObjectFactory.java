/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlRegistry;
import org.onvif.ver10.doorcontrol.wsdl.AccessDoor;
import org.onvif.ver10.doorcontrol.wsdl.AccessDoorExtension;
import org.onvif.ver10.doorcontrol.wsdl.AccessDoorResponse;
import org.onvif.ver10.doorcontrol.wsdl.BlockDoor;
import org.onvif.ver10.doorcontrol.wsdl.BlockDoorResponse;
import org.onvif.ver10.doorcontrol.wsdl.DoorCapabilities;
import org.onvif.ver10.doorcontrol.wsdl.DoorFault;
import org.onvif.ver10.doorcontrol.wsdl.DoorInfo;
import org.onvif.ver10.doorcontrol.wsdl.DoorInfoBase;
import org.onvif.ver10.doorcontrol.wsdl.DoorState;
import org.onvif.ver10.doorcontrol.wsdl.DoorTamper;
import org.onvif.ver10.doorcontrol.wsdl.DoubleLockDoor;
import org.onvif.ver10.doorcontrol.wsdl.DoubleLockDoorResponse;
import org.onvif.ver10.doorcontrol.wsdl.GetDoorInfo;
import org.onvif.ver10.doorcontrol.wsdl.GetDoorInfoList;
import org.onvif.ver10.doorcontrol.wsdl.GetDoorInfoListResponse;
import org.onvif.ver10.doorcontrol.wsdl.GetDoorInfoResponse;
import org.onvif.ver10.doorcontrol.wsdl.GetDoorState;
import org.onvif.ver10.doorcontrol.wsdl.GetDoorStateResponse;
import org.onvif.ver10.doorcontrol.wsdl.GetServiceCapabilities;
import org.onvif.ver10.doorcontrol.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.doorcontrol.wsdl.LockDoor;
import org.onvif.ver10.doorcontrol.wsdl.LockDoorResponse;
import org.onvif.ver10.doorcontrol.wsdl.LockDownDoor;
import org.onvif.ver10.doorcontrol.wsdl.LockDownDoorResponse;
import org.onvif.ver10.doorcontrol.wsdl.LockDownReleaseDoor;
import org.onvif.ver10.doorcontrol.wsdl.LockDownReleaseDoorResponse;
import org.onvif.ver10.doorcontrol.wsdl.LockOpenDoor;
import org.onvif.ver10.doorcontrol.wsdl.LockOpenDoorResponse;
import org.onvif.ver10.doorcontrol.wsdl.LockOpenReleaseDoor;
import org.onvif.ver10.doorcontrol.wsdl.LockOpenReleaseDoorResponse;
import org.onvif.ver10.doorcontrol.wsdl.ServiceCapabilities;
import org.onvif.ver10.doorcontrol.wsdl.UnlockDoor;
import org.onvif.ver10.doorcontrol.wsdl.UnlockDoorResponse;

@XmlRegistry
public class ObjectFactory {
    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public ServiceCapabilities createServiceCapabilities() {
        return new ServiceCapabilities();
    }

    public GetDoorInfoList createGetDoorInfoList() {
        return new GetDoorInfoList();
    }

    public GetDoorInfoListResponse createGetDoorInfoListResponse() {
        return new GetDoorInfoListResponse();
    }

    public DoorInfo createDoorInfo() {
        return new DoorInfo();
    }

    public GetDoorInfo createGetDoorInfo() {
        return new GetDoorInfo();
    }

    public GetDoorInfoResponse createGetDoorInfoResponse() {
        return new GetDoorInfoResponse();
    }

    public GetDoorState createGetDoorState() {
        return new GetDoorState();
    }

    public GetDoorStateResponse createGetDoorStateResponse() {
        return new GetDoorStateResponse();
    }

    public DoorState createDoorState() {
        return new DoorState();
    }

    public AccessDoor createAccessDoor() {
        return new AccessDoor();
    }

    public AccessDoorExtension createAccessDoorExtension() {
        return new AccessDoorExtension();
    }

    public AccessDoorResponse createAccessDoorResponse() {
        return new AccessDoorResponse();
    }

    public LockDoor createLockDoor() {
        return new LockDoor();
    }

    public LockDoorResponse createLockDoorResponse() {
        return new LockDoorResponse();
    }

    public UnlockDoor createUnlockDoor() {
        return new UnlockDoor();
    }

    public UnlockDoorResponse createUnlockDoorResponse() {
        return new UnlockDoorResponse();
    }

    public BlockDoor createBlockDoor() {
        return new BlockDoor();
    }

    public BlockDoorResponse createBlockDoorResponse() {
        return new BlockDoorResponse();
    }

    public LockDownDoor createLockDownDoor() {
        return new LockDownDoor();
    }

    public LockDownDoorResponse createLockDownDoorResponse() {
        return new LockDownDoorResponse();
    }

    public LockDownReleaseDoor createLockDownReleaseDoor() {
        return new LockDownReleaseDoor();
    }

    public LockDownReleaseDoorResponse createLockDownReleaseDoorResponse() {
        return new LockDownReleaseDoorResponse();
    }

    public LockOpenDoor createLockOpenDoor() {
        return new LockOpenDoor();
    }

    public LockOpenDoorResponse createLockOpenDoorResponse() {
        return new LockOpenDoorResponse();
    }

    public LockOpenReleaseDoor createLockOpenReleaseDoor() {
        return new LockOpenReleaseDoor();
    }

    public LockOpenReleaseDoorResponse createLockOpenReleaseDoorResponse() {
        return new LockOpenReleaseDoorResponse();
    }

    public DoubleLockDoor createDoubleLockDoor() {
        return new DoubleLockDoor();
    }

    public DoubleLockDoorResponse createDoubleLockDoorResponse() {
        return new DoubleLockDoorResponse();
    }

    public DoorInfoBase createDoorInfoBase() {
        return new DoorInfoBase();
    }

    public DoorCapabilities createDoorCapabilities() {
        return new DoorCapabilities();
    }

    public DoorTamper createDoorTamper() {
        return new DoorTamper();
    }

    public DoorFault createDoorFault() {
        return new DoorFault();
    }
}

