/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.soap.devices;

import de.onvif.soap.OnvifDevice;
import de.onvif.soap.SOAP;
import org.onvif.ver10.schema.AbsoluteFocus;
import org.onvif.ver10.schema.FocusMove;
import org.onvif.ver10.schema.ImagingOptions20;
import org.onvif.ver10.schema.ImagingSettings20;
import org.onvif.ver20.imaging.wsdl.GetImagingSettings;
import org.onvif.ver20.imaging.wsdl.GetImagingSettingsResponse;
import org.onvif.ver20.imaging.wsdl.GetOptions;
import org.onvif.ver20.imaging.wsdl.GetOptionsResponse;
import org.onvif.ver20.imaging.wsdl.Move;
import org.onvif.ver20.imaging.wsdl.MoveResponse;
import org.onvif.ver20.imaging.wsdl.SetImagingSettings;
import org.onvif.ver20.imaging.wsdl.SetImagingSettingsResponse;

public class ImagingDevices {
    private final SOAP soap;

    public ImagingDevices(OnvifDevice onvifDevice) {
        this.soap = onvifDevice.getSoap();
    }

    public ImagingOptions20 getOptions(String videoSourceToken) {
        if (videoSourceToken == null) {
            return null;
        }
        GetOptions request = new GetOptions();
        request.setVideoSourceToken(videoSourceToken);
        GetOptionsResponse response = this.soap.createImagingRequest(request, new GetOptionsResponse(), false);
        return response != null ? response.getImagingOptions() : null;
    }

    public boolean moveFocus(String videoSourceToken, float absoluteFocusValue) {
        if (videoSourceToken == null) {
            return false;
        }
        Move request = new Move();
        AbsoluteFocus absoluteFocus = new AbsoluteFocus();
        absoluteFocus.setPosition(absoluteFocusValue);
        FocusMove focusMove = new FocusMove();
        focusMove.setAbsolute(absoluteFocus);
        request.setVideoSourceToken(videoSourceToken);
        request.setFocus(focusMove);
        MoveResponse response = this.soap.createImagingRequest(request, new MoveResponse(), true);
        return response != null;
    }

    public ImagingSettings20 getImagingSettings(String videoSourceToken) {
        if (videoSourceToken == null) {
            return null;
        }
        GetImagingSettings request = new GetImagingSettings();
        request.setVideoSourceToken(videoSourceToken);
        GetImagingSettingsResponse response = this.soap.createImagingRequest(request, new GetImagingSettingsResponse(), true);
        return response != null ? response.getImagingSettings() : null;
    }

    public boolean setImagingSettings(String videoSourceToken, ImagingSettings20 imagingSettings) {
        if (videoSourceToken == null) {
            return false;
        }
        SetImagingSettings request = new SetImagingSettings();
        request.setVideoSourceToken(videoSourceToken);
        request.setImagingSettings(imagingSettings);
        SetImagingSettingsResponse response = this.soap.createImagingRequest(request, new SetImagingSettingsResponse(), true);
        return response != null;
    }
}

