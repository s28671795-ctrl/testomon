/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.media.wsdl.Capabilities;
import org.onvif.ver10.media.wsdl.CreateOSD;
import org.onvif.ver10.media.wsdl.CreateOSDResponse;
import org.onvif.ver10.media.wsdl.DeleteOSD;
import org.onvif.ver10.media.wsdl.DeleteOSDResponse;
import org.onvif.ver10.media.wsdl.GetOSD;
import org.onvif.ver10.media.wsdl.GetOSDOptions;
import org.onvif.ver10.media.wsdl.GetOSDOptionsResponse;
import org.onvif.ver10.media.wsdl.GetOSDResponse;
import org.onvif.ver10.media.wsdl.ObjectFactory;
import org.onvif.ver10.media.wsdl.SetOSD;
import org.onvif.ver10.media.wsdl.SetOSDResponse;
import org.onvif.ver10.media.wsdl.VideoSourceMode;
import org.onvif.ver10.schema.AudioDecoderConfiguration;
import org.onvif.ver10.schema.AudioDecoderConfigurationOptions;
import org.onvif.ver10.schema.AudioEncoderConfiguration;
import org.onvif.ver10.schema.AudioEncoderConfigurationOptions;
import org.onvif.ver10.schema.AudioOutput;
import org.onvif.ver10.schema.AudioOutputConfiguration;
import org.onvif.ver10.schema.AudioOutputConfigurationOptions;
import org.onvif.ver10.schema.AudioSource;
import org.onvif.ver10.schema.AudioSourceConfiguration;
import org.onvif.ver10.schema.AudioSourceConfigurationOptions;
import org.onvif.ver10.schema.MediaUri;
import org.onvif.ver10.schema.MetadataConfiguration;
import org.onvif.ver10.schema.MetadataConfigurationOptions;
import org.onvif.ver10.schema.OSDConfiguration;
import org.onvif.ver10.schema.Profile;
import org.onvif.ver10.schema.StreamSetup;
import org.onvif.ver10.schema.VideoAnalyticsConfiguration;
import org.onvif.ver10.schema.VideoEncoderConfiguration;
import org.onvif.ver10.schema.VideoEncoderConfigurationOptions;
import org.onvif.ver10.schema.VideoSource;
import org.onvif.ver10.schema.VideoSourceConfiguration;
import org.onvif.ver10.schema.VideoSourceConfigurationOptions;

@WebService(targetNamespace="http://www.onvif.org/ver10/media/wsdl", name="Media")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface Media {
    @WebMethod(operationName="RemovePTZConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemovePTZConfiguration")
    @RequestWrapper(localName="RemovePTZConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemovePTZConfiguration")
    @ResponseWrapper(localName="RemovePTZConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemovePTZConfigurationResponse")
    public void removePTZConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="RemoveVideoSourceConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemoveVideoSourceConfiguration")
    @RequestWrapper(localName="RemoveVideoSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveVideoSourceConfiguration")
    @ResponseWrapper(localName="RemoveVideoSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveVideoSourceConfigurationResponse")
    public void removeVideoSourceConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="StopMulticastStreaming", action="http://www.onvif.org/ver10/media/wsdl/StopMulticastStreaming")
    @RequestWrapper(localName="StopMulticastStreaming", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.StopMulticastStreaming")
    @ResponseWrapper(localName="StopMulticastStreamingResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.StopMulticastStreamingResponse")
    public void stopMulticastStreaming(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="CreateProfile", action="http://www.onvif.org/ver10/media/wsdl/CreateProfile")
    @RequestWrapper(localName="CreateProfile", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.CreateProfile")
    @ResponseWrapper(localName="CreateProfileResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.CreateProfileResponse")
    @WebResult(name="Profile", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public Profile createProfile(@WebParam(name="Name", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="AddVideoSourceConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddVideoSourceConfiguration")
    @RequestWrapper(localName="AddVideoSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddVideoSourceConfiguration")
    @ResponseWrapper(localName="AddVideoSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddVideoSourceConfigurationResponse")
    public void addVideoSourceConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetVideoEncoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/GetVideoEncoderConfiguration")
    @RequestWrapper(localName="GetVideoEncoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoEncoderConfiguration")
    @ResponseWrapper(localName="GetVideoEncoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationResponse")
    @WebResult(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public VideoEncoderConfiguration getVideoEncoderConfiguration(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/media/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public Capabilities getServiceCapabilities();

    @WebMethod(operationName="GetVideoEncoderConfigurationOptions", action="http://www.onvif.org/ver10/media/wsdl/GetVideoEncoderConfigurationOptions")
    @RequestWrapper(localName="GetVideoEncoderConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationOptions")
    @ResponseWrapper(localName="GetVideoEncoderConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public VideoEncoderConfigurationOptions getVideoEncoderConfigurationOptions(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetOSDs", action="http://www.onvif.org/ver10/media/wsdl/GetOSDs")
    @RequestWrapper(localName="GetOSDs", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetOSDs")
    @ResponseWrapper(localName="GetOSDsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetOSDsResponse")
    @WebResult(name="OSDs", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<OSDConfiguration> getOSDs(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="RemoveAudioEncoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemoveAudioEncoderConfiguration")
    @RequestWrapper(localName="RemoveAudioEncoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveAudioEncoderConfiguration")
    @ResponseWrapper(localName="RemoveAudioEncoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveAudioEncoderConfigurationResponse")
    public void removeAudioEncoderConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetCompatibleAudioEncoderConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetCompatibleAudioEncoderConfigurations")
    @RequestWrapper(localName="GetCompatibleAudioEncoderConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleAudioEncoderConfigurations")
    @ResponseWrapper(localName="GetCompatibleAudioEncoderConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleAudioEncoderConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioEncoderConfiguration> getCompatibleAudioEncoderConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetAudioEncoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/GetAudioEncoderConfiguration")
    @RequestWrapper(localName="GetAudioEncoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioEncoderConfiguration")
    @ResponseWrapper(localName="GetAudioEncoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurationResponse")
    @WebResult(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public AudioEncoderConfiguration getAudioEncoderConfiguration(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetAudioOutputConfiguration", action="http://www.onvif.org/ver10/media/wsdl/GetAudioOutputConfiguration")
    @RequestWrapper(localName="GetAudioOutputConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioOutputConfiguration")
    @ResponseWrapper(localName="GetAudioOutputConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioOutputConfigurationResponse")
    @WebResult(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public AudioOutputConfiguration getAudioOutputConfiguration(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="SetAudioOutputConfiguration", action="http://www.onvif.org/ver10/media/wsdl/SetAudioOutputConfiguration")
    @RequestWrapper(localName="SetAudioOutputConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetAudioOutputConfiguration")
    @ResponseWrapper(localName="SetAudioOutputConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetAudioOutputConfigurationResponse")
    public void setAudioOutputConfiguration(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl") AudioOutputConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver10/media/wsdl") boolean var2);

    @WebMethod(operationName="GetSnapshotUri", action="http://www.onvif.org/ver10/media/wsdl/GetSnapshotUri")
    @RequestWrapper(localName="GetSnapshotUri", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetSnapshotUri")
    @ResponseWrapper(localName="GetSnapshotUriResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetSnapshotUriResponse")
    @WebResult(name="MediaUri", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public MediaUri getSnapshotUri(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="RemoveVideoEncoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemoveVideoEncoderConfiguration")
    @RequestWrapper(localName="RemoveVideoEncoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveVideoEncoderConfiguration")
    @ResponseWrapper(localName="RemoveVideoEncoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveVideoEncoderConfigurationResponse")
    public void removeVideoEncoderConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetStreamUri", action="http://www.onvif.org/ver10/media/wsdl/GetStreamUri")
    @RequestWrapper(localName="GetStreamUri", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetStreamUri")
    @ResponseWrapper(localName="GetStreamUriResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetStreamUriResponse")
    @WebResult(name="MediaUri", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public MediaUri getStreamUri(@WebParam(name="StreamSetup", targetNamespace="http://www.onvif.org/ver10/media/wsdl") StreamSetup var1, @WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="CreateOSD", action="http://www.onvif.org/ver10/media/wsdl/CreateOSD")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="CreateOSDResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", partName="parameters")
    public CreateOSDResponse createOSD(@WebParam(partName="parameters", name="CreateOSD", targetNamespace="http://www.onvif.org/ver10/media/wsdl") CreateOSD var1);

    @WebMethod(operationName="GetVideoSourceConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetVideoSourceConfigurations")
    @RequestWrapper(localName="GetVideoSourceConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourceConfigurations")
    @ResponseWrapper(localName="GetVideoSourceConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourceConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<VideoSourceConfiguration> getVideoSourceConfigurations();

    @WebMethod(operationName="AddVideoAnalyticsConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddVideoAnalyticsConfiguration")
    @RequestWrapper(localName="AddVideoAnalyticsConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddVideoAnalyticsConfiguration")
    @ResponseWrapper(localName="AddVideoAnalyticsConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddVideoAnalyticsConfigurationResponse")
    public void addVideoAnalyticsConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="StartMulticastStreaming", action="http://www.onvif.org/ver10/media/wsdl/StartMulticastStreaming")
    @RequestWrapper(localName="StartMulticastStreaming", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.StartMulticastStreaming")
    @ResponseWrapper(localName="StartMulticastStreamingResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.StartMulticastStreamingResponse")
    public void startMulticastStreaming(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="SetSynchronizationPoint", action="http://www.onvif.org/ver10/media/wsdl/SetSynchronizationPoint")
    @RequestWrapper(localName="SetSynchronizationPoint", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetSynchronizationPoint")
    @ResponseWrapper(localName="SetSynchronizationPointResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetSynchronizationPointResponse")
    public void setSynchronizationPoint(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="AddAudioSourceConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddAudioSourceConfiguration")
    @RequestWrapper(localName="AddAudioSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddAudioSourceConfiguration")
    @ResponseWrapper(localName="AddAudioSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddAudioSourceConfigurationResponse")
    public void addAudioSourceConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="AddAudioOutputConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddAudioOutputConfiguration")
    @RequestWrapper(localName="AddAudioOutputConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddAudioOutputConfiguration")
    @ResponseWrapper(localName="AddAudioOutputConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddAudioOutputConfigurationResponse")
    public void addAudioOutputConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetCompatibleMetadataConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetCompatibleMetadataConfigurations")
    @RequestWrapper(localName="GetCompatibleMetadataConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleMetadataConfigurations")
    @ResponseWrapper(localName="GetCompatibleMetadataConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleMetadataConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<MetadataConfiguration> getCompatibleMetadataConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetMetadataConfigurationOptions", action="http://www.onvif.org/ver10/media/wsdl/GetMetadataConfigurationOptions")
    @RequestWrapper(localName="GetMetadataConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetMetadataConfigurationOptions")
    @ResponseWrapper(localName="GetMetadataConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetMetadataConfigurationOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public MetadataConfigurationOptions getMetadataConfigurationOptions(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="SetAudioDecoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/SetAudioDecoderConfiguration")
    @RequestWrapper(localName="SetAudioDecoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetAudioDecoderConfiguration")
    @ResponseWrapper(localName="SetAudioDecoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetAudioDecoderConfigurationResponse")
    public void setAudioDecoderConfiguration(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl") AudioDecoderConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver10/media/wsdl") boolean var2);

    @WebMethod(operationName="GetAudioOutputConfigurationOptions", action="http://www.onvif.org/ver10/media/wsdl/GetAudioOutputConfigurationOptions")
    @RequestWrapper(localName="GetAudioOutputConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioOutputConfigurationOptions")
    @ResponseWrapper(localName="GetAudioOutputConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioOutputConfigurationOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public AudioOutputConfigurationOptions getAudioOutputConfigurationOptions(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="SetVideoSourceConfiguration", action="http://www.onvif.org/ver10/media/wsdl/SetVideoSourceConfiguration")
    @RequestWrapper(localName="SetVideoSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetVideoSourceConfiguration")
    @ResponseWrapper(localName="SetVideoSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetVideoSourceConfigurationResponse")
    public void setVideoSourceConfiguration(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl") VideoSourceConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver10/media/wsdl") boolean var2);

    @WebMethod(operationName="GetCompatibleAudioOutputConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetCompatibleAudioOutputConfigurations")
    @RequestWrapper(localName="GetCompatibleAudioOutputConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleAudioOutputConfigurations")
    @ResponseWrapper(localName="GetCompatibleAudioOutputConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleAudioOutputConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioOutputConfiguration> getCompatibleAudioOutputConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetVideoEncoderConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetVideoEncoderConfigurations")
    @RequestWrapper(localName="GetVideoEncoderConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurations")
    @ResponseWrapper(localName="GetVideoEncoderConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<VideoEncoderConfiguration> getVideoEncoderConfigurations();

    @WebMethod(operationName="SetVideoAnalyticsConfiguration", action="http://www.onvif.org/ver10/media/wsdl/SetVideoAnalyticsConfiguration")
    @RequestWrapper(localName="SetVideoAnalyticsConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetVideoAnalyticsConfiguration")
    @ResponseWrapper(localName="SetVideoAnalyticsConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetVideoAnalyticsConfigurationResponse")
    public void setVideoAnalyticsConfiguration(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl") VideoAnalyticsConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver10/media/wsdl") boolean var2);

    @WebMethod(operationName="GetAudioEncoderConfigurationOptions", action="http://www.onvif.org/ver10/media/wsdl/GetAudioEncoderConfigurationOptions")
    @RequestWrapper(localName="GetAudioEncoderConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurationOptions")
    @ResponseWrapper(localName="GetAudioEncoderConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurationOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public AudioEncoderConfigurationOptions getAudioEncoderConfigurationOptions(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetProfiles", action="http://www.onvif.org/ver10/media/wsdl/GetProfiles")
    @RequestWrapper(localName="GetProfiles", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetProfiles")
    @ResponseWrapper(localName="GetProfilesResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetProfilesResponse")
    @WebResult(name="Profiles", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<Profile> getProfiles();

    @WebMethod(operationName="AddMetadataConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddMetadataConfiguration")
    @RequestWrapper(localName="AddMetadataConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddMetadataConfiguration")
    @ResponseWrapper(localName="AddMetadataConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddMetadataConfigurationResponse")
    public void addMetadataConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="SetAudioEncoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/SetAudioEncoderConfiguration")
    @RequestWrapper(localName="SetAudioEncoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetAudioEncoderConfiguration")
    @ResponseWrapper(localName="SetAudioEncoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetAudioEncoderConfigurationResponse")
    public void setAudioEncoderConfiguration(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl") AudioEncoderConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver10/media/wsdl") boolean var2);

    @WebMethod(operationName="GetVideoSourceConfigurationOptions", action="http://www.onvif.org/ver10/media/wsdlGetVideoSourceConfigurationOptions/")
    @RequestWrapper(localName="GetVideoSourceConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourceConfigurationOptions")
    @ResponseWrapper(localName="GetVideoSourceConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourceConfigurationOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public VideoSourceConfigurationOptions getVideoSourceConfigurationOptions(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetGuaranteedNumberOfVideoEncoderInstances", action="http://www.onvif.org/ver10/media/wsdl/GetGuaranteedNumberOfVideoEncoderInstances")
    @RequestWrapper(localName="GetGuaranteedNumberOfVideoEncoderInstances", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetGuaranteedNumberOfVideoEncoderInstances")
    @ResponseWrapper(localName="GetGuaranteedNumberOfVideoEncoderInstancesResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetGuaranteedNumberOfVideoEncoderInstancesResponse")
    public void getGuaranteedNumberOfVideoEncoderInstances(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(mode=WebParam.Mode.OUT, name="TotalNumber", targetNamespace="http://www.onvif.org/ver10/media/wsdl") Holder<Integer> var2, @WebParam(mode=WebParam.Mode.OUT, name="JPEG", targetNamespace="http://www.onvif.org/ver10/media/wsdl") Holder<Integer> var3, @WebParam(mode=WebParam.Mode.OUT, name="H264", targetNamespace="http://www.onvif.org/ver10/media/wsdl") Holder<Integer> var4, @WebParam(mode=WebParam.Mode.OUT, name="MPEG4", targetNamespace="http://www.onvif.org/ver10/media/wsdl") Holder<Integer> var5);

    @WebMethod(operationName="GetProfile", action="http://www.onvif.org/ver10/media/wsdlGetProfile/")
    @RequestWrapper(localName="GetProfile", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetProfile")
    @ResponseWrapper(localName="GetProfileResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetProfileResponse")
    @WebResult(name="Profile", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public Profile getProfile(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetVideoAnalyticsConfiguration", action="http://www.onvif.org/ver10/media/wsdl/GetVideoAnalyticsConfiguration")
    @RequestWrapper(localName="GetVideoAnalyticsConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoAnalyticsConfiguration")
    @ResponseWrapper(localName="GetVideoAnalyticsConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoAnalyticsConfigurationResponse")
    @WebResult(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public VideoAnalyticsConfiguration getVideoAnalyticsConfiguration(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetAudioOutputConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetAudioOutputConfigurations")
    @RequestWrapper(localName="GetAudioOutputConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioOutputConfigurations")
    @ResponseWrapper(localName="GetAudioOutputConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioOutputConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioOutputConfiguration> getAudioOutputConfigurations();

    @WebMethod(operationName="SetOSD", action="http://www.onvif.org/ver10/media/wsdl/SetOSD")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="SetOSDResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", partName="parameters")
    public SetOSDResponse setOSD(@WebParam(partName="parameters", name="SetOSD", targetNamespace="http://www.onvif.org/ver10/media/wsdl") SetOSD var1);

    @WebMethod(operationName="AddPTZConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddPTZConfiguration")
    @RequestWrapper(localName="AddPTZConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddPTZConfiguration")
    @ResponseWrapper(localName="AddPTZConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddPTZConfigurationResponse")
    public void addPTZConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetAudioOutputs", action="http://www.onvif.org/ver10/media/wsdl/GetAudioOutputs")
    @RequestWrapper(localName="GetAudioOutputs", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioOutputs")
    @ResponseWrapper(localName="GetAudioOutputsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioOutputsResponse")
    @WebResult(name="AudioOutputs", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioOutput> getAudioOutputs();

    @WebMethod(operationName="GetCompatibleVideoAnalyticsConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetCompatibleVideoAnalyticsConfigurations")
    @RequestWrapper(localName="GetCompatibleVideoAnalyticsConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleVideoAnalyticsConfigurations")
    @ResponseWrapper(localName="GetCompatibleVideoAnalyticsConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleVideoAnalyticsConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<VideoAnalyticsConfiguration> getCompatibleVideoAnalyticsConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetVideoAnalyticsConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetVideoAnalyticsConfigurations")
    @RequestWrapper(localName="GetVideoAnalyticsConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoAnalyticsConfigurations")
    @ResponseWrapper(localName="GetVideoAnalyticsConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoAnalyticsConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<VideoAnalyticsConfiguration> getVideoAnalyticsConfigurations();

    @WebMethod(operationName="GetCompatibleAudioSourceConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetCompatibleAudioSourceConfigurations")
    @RequestWrapper(localName="GetCompatibleAudioSourceConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleAudioSourceConfigurations")
    @ResponseWrapper(localName="GetCompatibleAudioSourceConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleAudioSourceConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioSourceConfiguration> getCompatibleAudioSourceConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetVideoSourceModes", action="http://www.onvif.org/ver10/media/wsdl/GetVideoSourceModes")
    @RequestWrapper(localName="GetVideoSourceModes", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourceModes")
    @ResponseWrapper(localName="GetVideoSourceModesResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourceModesResponse")
    @WebResult(name="VideoSourceModes", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<VideoSourceMode> getVideoSourceModes(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetAudioSourceConfigurationOptions", action="http://www.onvif.org/ver10/media/wsdl/GetAudioSourceConfigurationOptions")
    @RequestWrapper(localName="GetAudioSourceConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioSourceConfigurationOptions")
    @ResponseWrapper(localName="GetAudioSourceConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioSourceConfigurationOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public AudioSourceConfigurationOptions getAudioSourceConfigurationOptions(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetAudioSources", action="http://www.onvif.org/ver10/media/wsdl/GetAudioSources")
    @RequestWrapper(localName="GetAudioSources", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioSources")
    @ResponseWrapper(localName="GetAudioSourcesResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioSourcesResponse")
    @WebResult(name="AudioSources", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioSource> getAudioSources();

    @WebMethod(operationName="GetVideoSources", action="http://www.onvif.org/ver10/media/wsdlGetVideoSources/")
    @RequestWrapper(localName="GetVideoSources", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSources")
    @ResponseWrapper(localName="GetVideoSourcesResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourcesResponse")
    @WebResult(name="VideoSources", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<VideoSource> getVideoSources();

    @WebMethod(operationName="GetAudioDecoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/GetAudioDecoderConfiguration")
    @RequestWrapper(localName="GetAudioDecoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioDecoderConfiguration")
    @ResponseWrapper(localName="GetAudioDecoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurationResponse")
    @WebResult(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public AudioDecoderConfiguration getAudioDecoderConfiguration(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetAudioSourceConfiguration", action="http://www.onvif.org/ver10/media/wsdl/GetAudioSourceConfiguration")
    @RequestWrapper(localName="GetAudioSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioSourceConfiguration")
    @ResponseWrapper(localName="GetAudioSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioSourceConfigurationResponse")
    @WebResult(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public AudioSourceConfiguration getAudioSourceConfiguration(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetCompatibleVideoSourceConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetCompatibleVideoSourceConfigurations")
    @RequestWrapper(localName="GetCompatibleVideoSourceConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleVideoSourceConfigurations")
    @ResponseWrapper(localName="GetCompatibleVideoSourceConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleVideoSourceConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<VideoSourceConfiguration> getCompatibleVideoSourceConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetCompatibleAudioDecoderConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetCompatibleAudioDecoderConfigurations")
    @RequestWrapper(localName="GetCompatibleAudioDecoderConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleAudioDecoderConfigurations")
    @ResponseWrapper(localName="GetCompatibleAudioDecoderConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleAudioDecoderConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioDecoderConfiguration> getCompatibleAudioDecoderConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="AddVideoEncoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddVideoEncoderConfiguration")
    @RequestWrapper(localName="AddVideoEncoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddVideoEncoderConfiguration")
    @ResponseWrapper(localName="AddVideoEncoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddVideoEncoderConfigurationResponse")
    public void addVideoEncoderConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetMetadataConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetMetadataConfigurations")
    @RequestWrapper(localName="GetMetadataConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetMetadataConfigurations")
    @ResponseWrapper(localName="GetMetadataConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetMetadataConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<MetadataConfiguration> getMetadataConfigurations();

    @WebMethod(operationName="SetAudioSourceConfiguration", action="http://www.onvif.org/ver10/media/wsdl/SetAudioSourceConfiguration")
    @RequestWrapper(localName="SetAudioSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetAudioSourceConfiguration")
    @ResponseWrapper(localName="SetAudioSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetAudioSourceConfigurationResponse")
    public void setAudioSourceConfiguration(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl") AudioSourceConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver10/media/wsdl") boolean var2);

    @WebMethod(operationName="DeleteOSD", action="http://www.onvif.org/ver10/media/wsdl/DeleteOSD")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="DeleteOSDResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", partName="parameters")
    public DeleteOSDResponse deleteOSD(@WebParam(partName="parameters", name="DeleteOSD", targetNamespace="http://www.onvif.org/ver10/media/wsdl") DeleteOSD var1);

    @WebMethod(operationName="RemoveAudioDecoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemoveAudioDecoderConfiguration")
    @RequestWrapper(localName="RemoveAudioDecoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveAudioDecoderConfiguration")
    @ResponseWrapper(localName="RemoveAudioDecoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveAudioDecoderConfigurationResponse")
    public void removeAudioDecoderConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="DeleteProfile", action="http://www.onvif.org/ver10/media/wsdl/DeleteProfile")
    @RequestWrapper(localName="DeleteProfile", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.DeleteProfile")
    @ResponseWrapper(localName="DeleteProfileResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.DeleteProfileResponse")
    public void deleteProfile(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetCompatibleVideoEncoderConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetCompatibleVideoEncoderConfigurations")
    @RequestWrapper(localName="GetCompatibleVideoEncoderConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleVideoEncoderConfigurations")
    @ResponseWrapper(localName="GetCompatibleVideoEncoderConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetCompatibleVideoEncoderConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<VideoEncoderConfiguration> getCompatibleVideoEncoderConfigurations(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetMetadataConfiguration", action="http://www.onvif.org/ver10/media/wsdl/GetMetadataConfiguration")
    @RequestWrapper(localName="GetMetadataConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetMetadataConfiguration")
    @ResponseWrapper(localName="GetMetadataConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetMetadataConfigurationResponse")
    @WebResult(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public MetadataConfiguration getMetadataConfiguration(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="SetVideoEncoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/SetVideoEncoderConfiguration")
    @RequestWrapper(localName="SetVideoEncoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetVideoEncoderConfiguration")
    @ResponseWrapper(localName="SetVideoEncoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetVideoEncoderConfigurationResponse")
    public void setVideoEncoderConfiguration(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl") VideoEncoderConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver10/media/wsdl") boolean var2);

    @WebMethod(operationName="GetAudioDecoderConfigurationOptions", action="http://www.onvif.org/ver10/media/wsdl/GetAudioDecoderConfigurationOptions")
    @RequestWrapper(localName="GetAudioDecoderConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurationOptions")
    @ResponseWrapper(localName="GetAudioDecoderConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurationOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public AudioDecoderConfigurationOptions getAudioDecoderConfigurationOptions(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="GetOSDOptions", action="http://www.onvif.org/ver10/media/wsdl/GetOSDOptions")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetOSDOptionsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", partName="parameters")
    public GetOSDOptionsResponse getOSDOptions(@WebParam(partName="parameters", name="GetOSDOptions", targetNamespace="http://www.onvif.org/ver10/media/wsdl") GetOSDOptions var1);

    @WebMethod(operationName="AddAudioEncoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddAudioEncoderConfiguration")
    @RequestWrapper(localName="AddAudioEncoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddAudioEncoderConfiguration")
    @ResponseWrapper(localName="AddAudioEncoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddAudioEncoderConfigurationResponse")
    public void addAudioEncoderConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="AddAudioDecoderConfiguration", action="http://www.onvif.org/ver10/media/wsdl/AddAudioDecoderConfiguration")
    @RequestWrapper(localName="AddAudioDecoderConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddAudioDecoderConfiguration")
    @ResponseWrapper(localName="AddAudioDecoderConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.AddAudioDecoderConfigurationResponse")
    public void addAudioDecoderConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);

    @WebMethod(operationName="RemoveVideoAnalyticsConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemoveVideoAnalyticsConfiguration")
    @RequestWrapper(localName="RemoveVideoAnalyticsConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveVideoAnalyticsConfiguration")
    @ResponseWrapper(localName="RemoveVideoAnalyticsConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveVideoAnalyticsConfigurationResponse")
    public void removeVideoAnalyticsConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="RemoveMetadataConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemoveMetadataConfiguration")
    @RequestWrapper(localName="RemoveMetadataConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveMetadataConfiguration")
    @ResponseWrapper(localName="RemoveMetadataConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveMetadataConfigurationResponse")
    public void removeMetadataConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="RemoveAudioOutputConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemoveAudioOutputConfiguration")
    @RequestWrapper(localName="RemoveAudioOutputConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveAudioOutputConfiguration")
    @ResponseWrapper(localName="RemoveAudioOutputConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveAudioOutputConfigurationResponse")
    public void removeAudioOutputConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetAudioEncoderConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetAudioEncoderConfigurations")
    @RequestWrapper(localName="GetAudioEncoderConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurations")
    @ResponseWrapper(localName="GetAudioEncoderConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioEncoderConfiguration> getAudioEncoderConfigurations();

    @WebMethod(operationName="SetMetadataConfiguration", action="http://www.onvif.org/ver10/media/wsdl/SetMetadataConfiguration")
    @RequestWrapper(localName="SetMetadataConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetMetadataConfiguration")
    @ResponseWrapper(localName="SetMetadataConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetMetadataConfigurationResponse")
    public void setMetadataConfiguration(@WebParam(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl") MetadataConfiguration var1, @WebParam(name="ForcePersistence", targetNamespace="http://www.onvif.org/ver10/media/wsdl") boolean var2);

    @WebMethod(operationName="GetVideoSourceConfiguration", action="http://www.onvif.org/ver10/media/wsdl/GetVideoSourceConfiguration")
    @RequestWrapper(localName="GetVideoSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourceConfiguration")
    @ResponseWrapper(localName="GetVideoSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetVideoSourceConfigurationResponse")
    @WebResult(name="Configuration", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public VideoSourceConfiguration getVideoSourceConfiguration(@WebParam(name="ConfigurationToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetAudioDecoderConfigurations", action="http://www.onvif.org/ver10/media/wsdl/GetAudioDecoderConfigurations")
    @RequestWrapper(localName="GetAudioDecoderConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurations")
    @ResponseWrapper(localName="GetAudioDecoderConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioDecoderConfiguration> getAudioDecoderConfigurations();

    @WebMethod(operationName="RemoveAudioSourceConfiguration", action="http://www.onvif.org/ver10/media/wsdl/RemoveAudioSourceConfiguration")
    @RequestWrapper(localName="RemoveAudioSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveAudioSourceConfiguration")
    @ResponseWrapper(localName="RemoveAudioSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.RemoveAudioSourceConfigurationResponse")
    public void removeAudioSourceConfiguration(@WebParam(name="ProfileToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1);

    @WebMethod(operationName="GetOSD", action="http://www.onvif.org/ver10/media/wsdl/GetOSD")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetOSDResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", partName="parameters")
    public GetOSDResponse getOSD(@WebParam(partName="parameters", name="GetOSD", targetNamespace="http://www.onvif.org/ver10/media/wsdl") GetOSD var1);

    @WebMethod(operationName="GetAudioSourceConfigurations", action="http://www.onvif.org/ver10/media/wsdlGetAudioSourceConfigurations/")
    @RequestWrapper(localName="GetAudioSourceConfigurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioSourceConfigurations")
    @ResponseWrapper(localName="GetAudioSourceConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.GetAudioSourceConfigurationsResponse")
    @WebResult(name="Configurations", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public List<AudioSourceConfiguration> getAudioSourceConfigurations();

    @WebMethod(operationName="SetVideoSourceMode", action="http://www.onvif.org/ver10/media/wsdl/SetVideoSourceMode")
    @RequestWrapper(localName="SetVideoSourceMode", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetVideoSourceMode")
    @ResponseWrapper(localName="SetVideoSourceModeResponse", targetNamespace="http://www.onvif.org/ver10/media/wsdl", className="org.onvif.ver10.media.wsdl.SetVideoSourceModeResponse")
    @WebResult(name="Reboot", targetNamespace="http://www.onvif.org/ver10/media/wsdl")
    public boolean setVideoSourceMode(@WebParam(name="VideoSourceToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var1, @WebParam(name="VideoSourceModeToken", targetNamespace="http://www.onvif.org/ver10/media/wsdl") String var2);
}

