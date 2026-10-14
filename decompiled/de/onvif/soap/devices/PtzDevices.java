/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.soap.devices;

import de.onvif.soap.OnvifDevice;
import de.onvif.soap.SOAP;
import java.util.List;
import java.util.function.Function;
import javax.xml.soap.SOAPException;
import org.onvif.ver10.schema.FloatRange;
import org.onvif.ver10.schema.PTZConfiguration;
import org.onvif.ver10.schema.PTZNode;
import org.onvif.ver10.schema.PTZPreset;
import org.onvif.ver10.schema.PTZSpeed;
import org.onvif.ver10.schema.PTZStatus;
import org.onvif.ver10.schema.PTZVector;
import org.onvif.ver10.schema.Profile;
import org.onvif.ver10.schema.Space2DDescription;
import org.onvif.ver10.schema.Vector1D;
import org.onvif.ver10.schema.Vector2D;
import org.onvif.ver20.ptz.wsdl.AbsoluteMove;
import org.onvif.ver20.ptz.wsdl.AbsoluteMoveResponse;
import org.onvif.ver20.ptz.wsdl.ContinuousMove;
import org.onvif.ver20.ptz.wsdl.ContinuousMoveResponse;
import org.onvif.ver20.ptz.wsdl.GetNode;
import org.onvif.ver20.ptz.wsdl.GetNodeResponse;
import org.onvif.ver20.ptz.wsdl.GetNodes;
import org.onvif.ver20.ptz.wsdl.GetNodesResponse;
import org.onvif.ver20.ptz.wsdl.GetPresets;
import org.onvif.ver20.ptz.wsdl.GetPresetsResponse;
import org.onvif.ver20.ptz.wsdl.GetStatus;
import org.onvif.ver20.ptz.wsdl.GetStatusResponse;
import org.onvif.ver20.ptz.wsdl.GotoPreset;
import org.onvif.ver20.ptz.wsdl.GotoPresetResponse;
import org.onvif.ver20.ptz.wsdl.RelativeMove;
import org.onvif.ver20.ptz.wsdl.RelativeMoveResponse;
import org.onvif.ver20.ptz.wsdl.RemovePreset;
import org.onvif.ver20.ptz.wsdl.RemovePresetResponse;
import org.onvif.ver20.ptz.wsdl.SetHomePosition;
import org.onvif.ver20.ptz.wsdl.SetHomePositionResponse;
import org.onvif.ver20.ptz.wsdl.SetPreset;
import org.onvif.ver20.ptz.wsdl.SetPresetResponse;
import org.onvif.ver20.ptz.wsdl.Stop;
import org.onvif.ver20.ptz.wsdl.StopResponse;

public class PtzDevices {
    private final OnvifDevice onvifDevice;
    private final SOAP soap;

    public PtzDevices(OnvifDevice onvifDevice) {
        this.onvifDevice = onvifDevice;
        this.soap = onvifDevice.getSoap();
    }

    public boolean isPtzOperationsSupported(String profileToken) {
        return this.getPTZConfiguration(profileToken) != null;
    }

    public PTZConfiguration getPTZConfiguration(String profileToken) {
        if (this.isEmpty(profileToken)) {
            return null;
        }
        Profile profile = this.getProfile(profileToken);
        return profile.getPTZConfiguration();
    }

    private boolean isEmpty(String value) {
        return value == null || value.equals("");
    }

    private Profile getProfile(String profileToken) {
        Profile profile = this.onvifDevice.getDevices().getProfile(profileToken);
        if (profile == null) {
            throw new IllegalArgumentException("No profile available for token: " + profileToken);
        }
        return profile;
    }

    private List<PTZNode> getNodes() {
        return this.soap.createDeviceRequest(new GetNodes(), new GetNodesResponse(), true).getPTZNode();
    }

    private PTZNode getNode(String profileToken) {
        return this.getNode(this.getPTZConfiguration(profileToken));
    }

    private PTZNode getNode(PTZConfiguration ptzConfiguration) {
        if (ptzConfiguration == null) {
            return null;
        }
        GetNode request = new GetNode();
        request.setNodeToken(ptzConfiguration.getNodeToken());
        return this.soap.createDeviceRequest(request, new GetNodeResponse(), true).getPTZNode();
    }

    private Space2DDescription getAbsolutePanTiltPositionSpace(String profileToken) {
        return this.getNode(profileToken).getSupportedPTZSpaces().getAbsolutePanTiltPositionSpace().get(0);
    }

    public FloatRange getPanSpaces(String profileToken) {
        return this.getAbsolutePanTiltPositionSpace(profileToken).getXRange();
    }

    public FloatRange getTiltSpaces(String profileToken) {
        return this.getAbsolutePanTiltPositionSpace(profileToken).getYRange();
    }

    public FloatRange getZoomSpaces(String profileToken) {
        return this.getNode(profileToken).getSupportedPTZSpaces().getAbsoluteZoomPositionSpace().get(0).getXRange();
    }

    private boolean isMoveSupported(String profileToken, Function<PTZConfiguration, String> func) {
        Profile profile = this.onvifDevice.getDevices().getProfile(profileToken);
        try {
            return func.apply(profile.getPTZConfiguration()) != null;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
    }

    public boolean isAbsoluteMoveSupported(String profileToken) {
        return this.isMoveSupported(profileToken, PTZConfiguration::getDefaultAbsolutePantTiltPositionSpace);
    }

    public boolean isRelativeMoveSupported(String profileToken) {
        return this.isMoveSupported(profileToken, PTZConfiguration::getDefaultRelativePanTiltTranslationSpace);
    }

    public boolean isContinuousMoveSupported(String profileToken) {
        return this.isMoveSupported(profileToken, PTZConfiguration::getDefaultContinuousPanTiltVelocitySpace);
    }

    private PTZVector createPTZVector(float x, float y, float zoom) {
        Vector2D panTiltVector = new Vector2D();
        panTiltVector.setX(x);
        panTiltVector.setY(y);
        Vector1D zoomVector = new Vector1D();
        zoomVector.setX(zoom);
        PTZVector ptzVector = new PTZVector();
        ptzVector.setPanTilt(panTiltVector);
        ptzVector.setZoom(zoomVector);
        return ptzVector;
    }

    public boolean absoluteMove(String profileToken, float x, float y, float zoom) throws SOAPException {
        PTZNode node = this.getNode(profileToken);
        if (node != null) {
            FloatRange xRange = node.getSupportedPTZSpaces().getAbsolutePanTiltPositionSpace().get(0).getXRange();
            FloatRange yRange = node.getSupportedPTZSpaces().getAbsolutePanTiltPositionSpace().get(0).getYRange();
            FloatRange zRange = node.getSupportedPTZSpaces().getAbsoluteZoomPositionSpace().get(0).getXRange();
            if (zoom < zRange.getMin() || zoom > zRange.getMax()) {
                throw new IllegalArgumentException("Bad value for zoom: " + zoom);
            }
            if (x < xRange.getMin() || x > xRange.getMax()) {
                throw new IllegalArgumentException("Bad value for pan:/x " + x);
            }
            if (y < yRange.getMin() || y > yRange.getMax()) {
                throw new IllegalArgumentException("Bad value for tilt/y: " + y);
            }
        }
        AbsoluteMove request = new AbsoluteMove();
        request.setPosition(this.createPTZVector(x, y, zoom));
        request.setProfileToken(profileToken);
        AbsoluteMoveResponse response = this.soap.createPtzRequest(request, new AbsoluteMoveResponse(), true);
        return response != null;
    }

    public boolean relativeMove(String profileToken, float x, float y, float zoom) {
        RelativeMove request = new RelativeMove();
        request.setProfileToken(profileToken);
        request.setTranslation(this.createPTZVector(x, y, zoom));
        RelativeMoveResponse response = this.soap.createPtzRequest(request, new RelativeMoveResponse(), true);
        return response != null;
    }

    public boolean continuousMove(String profileToken, float x, float y, float zoom) {
        ContinuousMove request = new ContinuousMove();
        request.setProfileToken(profileToken);
        Vector2D panTiltVector = new Vector2D();
        panTiltVector.setX(x);
        panTiltVector.setY(y);
        Vector1D zoomVector = new Vector1D();
        zoomVector.setX(zoom);
        PTZSpeed ptzSpeed = new PTZSpeed();
        ptzSpeed.setPanTilt(panTiltVector);
        ptzSpeed.setZoom(zoomVector);
        request.setVelocity(ptzSpeed);
        ContinuousMoveResponse response = this.soap.createPtzRequest(request, new ContinuousMoveResponse(), true);
        return response != null;
    }

    public boolean stopMove(String profileToken) {
        Stop request = new Stop();
        request.setPanTilt(true);
        request.setZoom(true);
        request.setProfileToken(profileToken);
        StopResponse response = this.soap.createPtzRequest(request, new StopResponse(), true);
        return response != null;
    }

    public PTZStatus getStatus(String profileToken) {
        GetStatus request = new GetStatus();
        request.setProfileToken(profileToken);
        GetStatusResponse response = this.soap.createPtzRequest(request, new GetStatusResponse(), false);
        return response == null ? null : response.getPTZStatus();
    }

    public PTZVector getPosition(String profileToken) {
        PTZStatus status = this.getStatus(profileToken);
        return status == null ? null : status.getPosition();
    }

    public boolean setHomePosition(String profileToken) {
        SetHomePosition request = new SetHomePosition();
        request.setProfileToken(profileToken);
        SetHomePositionResponse response = this.soap.createPtzRequest(request, new SetHomePositionResponse(), true);
        return response != null;
    }

    public List<PTZPreset> getPresets(String profileToken) {
        GetPresets request = new GetPresets();
        request.setProfileToken(profileToken);
        GetPresetsResponse response = this.soap.createPtzRequest(request, new GetPresetsResponse(), true);
        return response == null ? null : response.getPreset();
    }

    public String setPreset(String presetName, String presetToken, String profileToken) {
        SetPreset request = new SetPreset();
        request.setProfileToken(profileToken);
        request.setPresetName(presetName);
        request.setPresetToken(presetToken);
        SetPresetResponse response = this.soap.createPtzRequest(request, new SetPresetResponse(), true);
        return response == null ? null : response.getPresetToken();
    }

    public String setPreset(String presetName, String profileToken) {
        return this.setPreset(presetName, null, profileToken);
    }

    public boolean removePreset(String presetToken, String profileToken) {
        RemovePreset request = new RemovePreset();
        request.setProfileToken(profileToken);
        request.setPresetToken(presetToken);
        RemovePresetResponse response = this.soap.createPtzRequest(request, new RemovePresetResponse(), true);
        return response != null;
    }

    public boolean gotoPreset(String presetToken, String profileToken) {
        GotoPreset request = new GotoPreset();
        request.setProfileToken(profileToken);
        request.setPresetToken(presetToken);
        GotoPresetResponse response = this.soap.createPtzRequest(request, new GotoPresetResponse(), true);
        return response != null;
    }
}

