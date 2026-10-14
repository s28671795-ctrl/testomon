/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.imaging.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver20.imaging.wsdl.Capabilities;
import org.onvif.ver20.imaging.wsdl.GetImagingSettings;
import org.onvif.ver20.imaging.wsdl.GetImagingSettingsResponse;
import org.onvif.ver20.imaging.wsdl.GetMoveOptions;
import org.onvif.ver20.imaging.wsdl.GetMoveOptionsResponse;
import org.onvif.ver20.imaging.wsdl.GetOptions;
import org.onvif.ver20.imaging.wsdl.GetOptionsResponse;
import org.onvif.ver20.imaging.wsdl.GetServiceCapabilities;
import org.onvif.ver20.imaging.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver20.imaging.wsdl.GetStatus;
import org.onvif.ver20.imaging.wsdl.GetStatusResponse;
import org.onvif.ver20.imaging.wsdl.Move;
import org.onvif.ver20.imaging.wsdl.MoveResponse;
import org.onvif.ver20.imaging.wsdl.SetImagingSettings;
import org.onvif.ver20.imaging.wsdl.SetImagingSettingsResponse;
import org.onvif.ver20.imaging.wsdl.Stop;
import org.onvif.ver20.imaging.wsdl.StopResponse;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver20/imaging/wsdl", "Capabilities");

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetImagingSettings createGetImagingSettings() {
        return new GetImagingSettings();
    }

    public GetImagingSettingsResponse createGetImagingSettingsResponse() {
        return new GetImagingSettingsResponse();
    }

    public SetImagingSettings createSetImagingSettings() {
        return new SetImagingSettings();
    }

    public SetImagingSettingsResponse createSetImagingSettingsResponse() {
        return new SetImagingSettingsResponse();
    }

    public GetOptions createGetOptions() {
        return new GetOptions();
    }

    public GetOptionsResponse createGetOptionsResponse() {
        return new GetOptionsResponse();
    }

    public Move createMove() {
        return new Move();
    }

    public MoveResponse createMoveResponse() {
        return new MoveResponse();
    }

    public GetMoveOptions createGetMoveOptions() {
        return new GetMoveOptions();
    }

    public GetMoveOptionsResponse createGetMoveOptionsResponse() {
        return new GetMoveOptionsResponse();
    }

    public Stop createStop() {
        return new Stop();
    }

    public StopResponse createStopResponse() {
        return new StopResponse();
    }

    public GetStatus createGetStatus() {
        return new GetStatus();
    }

    public GetStatusResponse createGetStatusResponse() {
        return new GetStatusResponse();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver20/imaging/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }
}

