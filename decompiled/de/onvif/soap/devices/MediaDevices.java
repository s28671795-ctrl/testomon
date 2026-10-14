/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.soap.devices;

import de.onvif.soap.OnvifDevice;
import de.onvif.soap.SOAP;
import java.net.ConnectException;
import java.util.List;
import javax.xml.soap.SOAPException;
import org.onvif.ver10.media.wsdl.GetSnapshotUri;
import org.onvif.ver10.media.wsdl.GetSnapshotUriResponse;
import org.onvif.ver10.media.wsdl.GetStreamUri;
import org.onvif.ver10.media.wsdl.GetStreamUriResponse;
import org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationOptions;
import org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationOptionsResponse;
import org.onvif.ver10.media.wsdl.GetVideoSources;
import org.onvif.ver10.media.wsdl.GetVideoSourcesResponse;
import org.onvif.ver10.media.wsdl.SetVideoEncoderConfiguration;
import org.onvif.ver10.media.wsdl.SetVideoEncoderConfigurationResponse;
import org.onvif.ver10.schema.Profile;
import org.onvif.ver10.schema.StreamSetup;
import org.onvif.ver10.schema.StreamType;
import org.onvif.ver10.schema.Transport;
import org.onvif.ver10.schema.TransportProtocol;
import org.onvif.ver10.schema.VideoEncoderConfiguration;
import org.onvif.ver10.schema.VideoEncoderConfigurationOptions;
import org.onvif.ver10.schema.VideoSource;

public class MediaDevices {
    private final OnvifDevice onvifDevice;
    private final SOAP soap;

    public MediaDevices(OnvifDevice onvifDevice) {
        this.onvifDevice = onvifDevice;
        this.soap = onvifDevice.getSoap();
    }

    public String getHTTPStreamUri(String profileToken) throws ConnectException, SOAPException {
        return this.getStreamUri(profileToken, this.createStreamSetup(TransportProtocol.HTTP));
    }

    public String getUDPStreamUri(String profileToken) throws ConnectException, SOAPException {
        return this.getStreamUri(profileToken, this.createStreamSetup(TransportProtocol.UDP));
    }

    public String getTCPStreamUri(String profileToken) throws ConnectException, SOAPException {
        return this.getStreamUri(profileToken, this.createStreamSetup(TransportProtocol.TCP));
    }

    public String getRTSPStreamUri(String profileToken) throws ConnectException, SOAPException {
        return this.getStreamUri(profileToken, this.createStreamSetup(TransportProtocol.TCP));
    }

    public String getStreamUri(String profileToken, StreamSetup streamSetup) throws SOAPException, ConnectException {
        GetStreamUri request = new GetStreamUri();
        GetStreamUriResponse response = this.soap.createMediaRequest(request, new GetStreamUriResponse(), false);
        request.setProfileToken(profileToken);
        request.setStreamSetup(streamSetup);
        return response == null ? null : this.onvifDevice.replaceLocalIpWithProxyIp(response.getMediaUri().getUri());
    }

    public static VideoEncoderConfiguration getVideoEncoderConfiguration(Profile profile) {
        return profile.getVideoEncoderConfiguration();
    }

    public VideoEncoderConfigurationOptions getVideoEncoderConfigurationOptions(String profileToken) throws SOAPException, ConnectException {
        GetVideoEncoderConfigurationOptions request = new GetVideoEncoderConfigurationOptions();
        GetVideoEncoderConfigurationOptionsResponse response = this.soap.createMediaRequest(request, new GetVideoEncoderConfigurationOptionsResponse(), false);
        request.setProfileToken(profileToken);
        return response == null ? null : response.getOptions();
    }

    public boolean setVideoEncoderConfiguration(VideoEncoderConfiguration videoEncoderConfiguration) throws SOAPException, ConnectException {
        SetVideoEncoderConfiguration request = new SetVideoEncoderConfiguration();
        SetVideoEncoderConfigurationResponse response = this.soap.createMediaRequest(request, new SetVideoEncoderConfigurationResponse(), true);
        request.setConfiguration(videoEncoderConfiguration);
        request.setForcePersistence(true);
        return response != null;
    }

    public String getSnapshotUri(String profileToken) throws SOAPException, ConnectException {
        GetSnapshotUri request = new GetSnapshotUri();
        GetSnapshotUriResponse response = this.soap.createMediaRequest(request, new GetSnapshotUriResponse(), true);
        request.setProfileToken(profileToken);
        return response == null || response.getMediaUri() == null ? null : this.onvifDevice.replaceLocalIpWithProxyIp(response.getMediaUri().getUri());
    }

    public List<VideoSource> getVideoSources() throws SOAPException, ConnectException {
        GetVideoSources request = new GetVideoSources();
        GetVideoSourcesResponse response = this.soap.createMediaRequest(request, new GetVideoSourcesResponse(), false);
        return response == null ? null : response.getVideoSources();
    }

    private StreamSetup createStreamSetup(TransportProtocol protocol) {
        StreamSetup setup = new StreamSetup();
        setup.setStream(StreamType.RTP_UNICAST);
        Transport transport = new Transport();
        transport.setProtocol(protocol);
        setup.setTransport(transport);
        return setup;
    }
}

