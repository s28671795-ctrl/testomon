/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.media.wsdl.AddAudioDecoderConfiguration;
import org.onvif.ver10.media.wsdl.AddAudioDecoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.AddAudioEncoderConfiguration;
import org.onvif.ver10.media.wsdl.AddAudioEncoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.AddAudioOutputConfiguration;
import org.onvif.ver10.media.wsdl.AddAudioOutputConfigurationResponse;
import org.onvif.ver10.media.wsdl.AddAudioSourceConfiguration;
import org.onvif.ver10.media.wsdl.AddAudioSourceConfigurationResponse;
import org.onvif.ver10.media.wsdl.AddMetadataConfiguration;
import org.onvif.ver10.media.wsdl.AddMetadataConfigurationResponse;
import org.onvif.ver10.media.wsdl.AddPTZConfiguration;
import org.onvif.ver10.media.wsdl.AddPTZConfigurationResponse;
import org.onvif.ver10.media.wsdl.AddVideoAnalyticsConfiguration;
import org.onvif.ver10.media.wsdl.AddVideoAnalyticsConfigurationResponse;
import org.onvif.ver10.media.wsdl.AddVideoEncoderConfiguration;
import org.onvif.ver10.media.wsdl.AddVideoEncoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.AddVideoSourceConfiguration;
import org.onvif.ver10.media.wsdl.AddVideoSourceConfigurationResponse;
import org.onvif.ver10.media.wsdl.Capabilities;
import org.onvif.ver10.media.wsdl.CreateOSD;
import org.onvif.ver10.media.wsdl.CreateOSDResponse;
import org.onvif.ver10.media.wsdl.CreateProfile;
import org.onvif.ver10.media.wsdl.CreateProfileResponse;
import org.onvif.ver10.media.wsdl.DeleteOSD;
import org.onvif.ver10.media.wsdl.DeleteOSDResponse;
import org.onvif.ver10.media.wsdl.DeleteProfile;
import org.onvif.ver10.media.wsdl.DeleteProfileResponse;
import org.onvif.ver10.media.wsdl.GetAudioDecoderConfiguration;
import org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurationOptions;
import org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurationOptionsResponse;
import org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurations;
import org.onvif.ver10.media.wsdl.GetAudioDecoderConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetAudioEncoderConfiguration;
import org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurationOptions;
import org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurationOptionsResponse;
import org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurations;
import org.onvif.ver10.media.wsdl.GetAudioEncoderConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetAudioOutputConfiguration;
import org.onvif.ver10.media.wsdl.GetAudioOutputConfigurationOptions;
import org.onvif.ver10.media.wsdl.GetAudioOutputConfigurationOptionsResponse;
import org.onvif.ver10.media.wsdl.GetAudioOutputConfigurationResponse;
import org.onvif.ver10.media.wsdl.GetAudioOutputConfigurations;
import org.onvif.ver10.media.wsdl.GetAudioOutputConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetAudioOutputs;
import org.onvif.ver10.media.wsdl.GetAudioOutputsResponse;
import org.onvif.ver10.media.wsdl.GetAudioSourceConfiguration;
import org.onvif.ver10.media.wsdl.GetAudioSourceConfigurationOptions;
import org.onvif.ver10.media.wsdl.GetAudioSourceConfigurationOptionsResponse;
import org.onvif.ver10.media.wsdl.GetAudioSourceConfigurationResponse;
import org.onvif.ver10.media.wsdl.GetAudioSourceConfigurations;
import org.onvif.ver10.media.wsdl.GetAudioSourceConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetAudioSources;
import org.onvif.ver10.media.wsdl.GetAudioSourcesResponse;
import org.onvif.ver10.media.wsdl.GetCompatibleAudioDecoderConfigurations;
import org.onvif.ver10.media.wsdl.GetCompatibleAudioDecoderConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetCompatibleAudioEncoderConfigurations;
import org.onvif.ver10.media.wsdl.GetCompatibleAudioEncoderConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetCompatibleAudioOutputConfigurations;
import org.onvif.ver10.media.wsdl.GetCompatibleAudioOutputConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetCompatibleAudioSourceConfigurations;
import org.onvif.ver10.media.wsdl.GetCompatibleAudioSourceConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetCompatibleMetadataConfigurations;
import org.onvif.ver10.media.wsdl.GetCompatibleMetadataConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetCompatibleVideoAnalyticsConfigurations;
import org.onvif.ver10.media.wsdl.GetCompatibleVideoAnalyticsConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetCompatibleVideoEncoderConfigurations;
import org.onvif.ver10.media.wsdl.GetCompatibleVideoEncoderConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetCompatibleVideoSourceConfigurations;
import org.onvif.ver10.media.wsdl.GetCompatibleVideoSourceConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetGuaranteedNumberOfVideoEncoderInstances;
import org.onvif.ver10.media.wsdl.GetGuaranteedNumberOfVideoEncoderInstancesResponse;
import org.onvif.ver10.media.wsdl.GetMetadataConfiguration;
import org.onvif.ver10.media.wsdl.GetMetadataConfigurationOptions;
import org.onvif.ver10.media.wsdl.GetMetadataConfigurationOptionsResponse;
import org.onvif.ver10.media.wsdl.GetMetadataConfigurationResponse;
import org.onvif.ver10.media.wsdl.GetMetadataConfigurations;
import org.onvif.ver10.media.wsdl.GetMetadataConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetOSD;
import org.onvif.ver10.media.wsdl.GetOSDOptions;
import org.onvif.ver10.media.wsdl.GetOSDOptionsResponse;
import org.onvif.ver10.media.wsdl.GetOSDResponse;
import org.onvif.ver10.media.wsdl.GetOSDs;
import org.onvif.ver10.media.wsdl.GetOSDsResponse;
import org.onvif.ver10.media.wsdl.GetProfile;
import org.onvif.ver10.media.wsdl.GetProfileResponse;
import org.onvif.ver10.media.wsdl.GetProfiles;
import org.onvif.ver10.media.wsdl.GetProfilesResponse;
import org.onvif.ver10.media.wsdl.GetServiceCapabilities;
import org.onvif.ver10.media.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.media.wsdl.GetSnapshotUri;
import org.onvif.ver10.media.wsdl.GetSnapshotUriResponse;
import org.onvif.ver10.media.wsdl.GetStreamUri;
import org.onvif.ver10.media.wsdl.GetStreamUriResponse;
import org.onvif.ver10.media.wsdl.GetVideoAnalyticsConfiguration;
import org.onvif.ver10.media.wsdl.GetVideoAnalyticsConfigurationResponse;
import org.onvif.ver10.media.wsdl.GetVideoAnalyticsConfigurations;
import org.onvif.ver10.media.wsdl.GetVideoAnalyticsConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetVideoEncoderConfiguration;
import org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationOptions;
import org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationOptionsResponse;
import org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurations;
import org.onvif.ver10.media.wsdl.GetVideoEncoderConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetVideoSourceConfiguration;
import org.onvif.ver10.media.wsdl.GetVideoSourceConfigurationOptions;
import org.onvif.ver10.media.wsdl.GetVideoSourceConfigurationOptionsResponse;
import org.onvif.ver10.media.wsdl.GetVideoSourceConfigurationResponse;
import org.onvif.ver10.media.wsdl.GetVideoSourceConfigurations;
import org.onvif.ver10.media.wsdl.GetVideoSourceConfigurationsResponse;
import org.onvif.ver10.media.wsdl.GetVideoSourceModes;
import org.onvif.ver10.media.wsdl.GetVideoSourceModesResponse;
import org.onvif.ver10.media.wsdl.GetVideoSources;
import org.onvif.ver10.media.wsdl.GetVideoSourcesResponse;
import org.onvif.ver10.media.wsdl.ProfileCapabilities;
import org.onvif.ver10.media.wsdl.RemoveAudioDecoderConfiguration;
import org.onvif.ver10.media.wsdl.RemoveAudioDecoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.RemoveAudioEncoderConfiguration;
import org.onvif.ver10.media.wsdl.RemoveAudioEncoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.RemoveAudioOutputConfiguration;
import org.onvif.ver10.media.wsdl.RemoveAudioOutputConfigurationResponse;
import org.onvif.ver10.media.wsdl.RemoveAudioSourceConfiguration;
import org.onvif.ver10.media.wsdl.RemoveAudioSourceConfigurationResponse;
import org.onvif.ver10.media.wsdl.RemoveMetadataConfiguration;
import org.onvif.ver10.media.wsdl.RemoveMetadataConfigurationResponse;
import org.onvif.ver10.media.wsdl.RemovePTZConfiguration;
import org.onvif.ver10.media.wsdl.RemovePTZConfigurationResponse;
import org.onvif.ver10.media.wsdl.RemoveVideoAnalyticsConfiguration;
import org.onvif.ver10.media.wsdl.RemoveVideoAnalyticsConfigurationResponse;
import org.onvif.ver10.media.wsdl.RemoveVideoEncoderConfiguration;
import org.onvif.ver10.media.wsdl.RemoveVideoEncoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.RemoveVideoSourceConfiguration;
import org.onvif.ver10.media.wsdl.RemoveVideoSourceConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetAudioDecoderConfiguration;
import org.onvif.ver10.media.wsdl.SetAudioDecoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetAudioEncoderConfiguration;
import org.onvif.ver10.media.wsdl.SetAudioEncoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetAudioOutputConfiguration;
import org.onvif.ver10.media.wsdl.SetAudioOutputConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetAudioSourceConfiguration;
import org.onvif.ver10.media.wsdl.SetAudioSourceConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetMetadataConfiguration;
import org.onvif.ver10.media.wsdl.SetMetadataConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetOSD;
import org.onvif.ver10.media.wsdl.SetOSDResponse;
import org.onvif.ver10.media.wsdl.SetSynchronizationPoint;
import org.onvif.ver10.media.wsdl.SetSynchronizationPointResponse;
import org.onvif.ver10.media.wsdl.SetVideoAnalyticsConfiguration;
import org.onvif.ver10.media.wsdl.SetVideoAnalyticsConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetVideoEncoderConfiguration;
import org.onvif.ver10.media.wsdl.SetVideoEncoderConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetVideoSourceConfiguration;
import org.onvif.ver10.media.wsdl.SetVideoSourceConfigurationResponse;
import org.onvif.ver10.media.wsdl.SetVideoSourceMode;
import org.onvif.ver10.media.wsdl.SetVideoSourceModeResponse;
import org.onvif.ver10.media.wsdl.StartMulticastStreaming;
import org.onvif.ver10.media.wsdl.StartMulticastStreamingResponse;
import org.onvif.ver10.media.wsdl.StopMulticastStreaming;
import org.onvif.ver10.media.wsdl.StopMulticastStreamingResponse;
import org.onvif.ver10.media.wsdl.StreamingCapabilities;
import org.onvif.ver10.media.wsdl.VideoSourceMode;
import org.onvif.ver10.media.wsdl.VideoSourceModeExtension;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/media/wsdl", "Capabilities");

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetVideoSources createGetVideoSources() {
        return new GetVideoSources();
    }

    public GetVideoSourcesResponse createGetVideoSourcesResponse() {
        return new GetVideoSourcesResponse();
    }

    public GetAudioSources createGetAudioSources() {
        return new GetAudioSources();
    }

    public GetAudioSourcesResponse createGetAudioSourcesResponse() {
        return new GetAudioSourcesResponse();
    }

    public GetAudioOutputs createGetAudioOutputs() {
        return new GetAudioOutputs();
    }

    public GetAudioOutputsResponse createGetAudioOutputsResponse() {
        return new GetAudioOutputsResponse();
    }

    public CreateProfile createCreateProfile() {
        return new CreateProfile();
    }

    public CreateProfileResponse createCreateProfileResponse() {
        return new CreateProfileResponse();
    }

    public GetProfile createGetProfile() {
        return new GetProfile();
    }

    public GetProfileResponse createGetProfileResponse() {
        return new GetProfileResponse();
    }

    public GetProfiles createGetProfiles() {
        return new GetProfiles();
    }

    public GetProfilesResponse createGetProfilesResponse() {
        return new GetProfilesResponse();
    }

    public AddVideoEncoderConfiguration createAddVideoEncoderConfiguration() {
        return new AddVideoEncoderConfiguration();
    }

    public AddVideoEncoderConfigurationResponse createAddVideoEncoderConfigurationResponse() {
        return new AddVideoEncoderConfigurationResponse();
    }

    public RemoveVideoEncoderConfiguration createRemoveVideoEncoderConfiguration() {
        return new RemoveVideoEncoderConfiguration();
    }

    public RemoveVideoEncoderConfigurationResponse createRemoveVideoEncoderConfigurationResponse() {
        return new RemoveVideoEncoderConfigurationResponse();
    }

    public AddVideoSourceConfiguration createAddVideoSourceConfiguration() {
        return new AddVideoSourceConfiguration();
    }

    public AddVideoSourceConfigurationResponse createAddVideoSourceConfigurationResponse() {
        return new AddVideoSourceConfigurationResponse();
    }

    public RemoveVideoSourceConfiguration createRemoveVideoSourceConfiguration() {
        return new RemoveVideoSourceConfiguration();
    }

    public RemoveVideoSourceConfigurationResponse createRemoveVideoSourceConfigurationResponse() {
        return new RemoveVideoSourceConfigurationResponse();
    }

    public AddAudioEncoderConfiguration createAddAudioEncoderConfiguration() {
        return new AddAudioEncoderConfiguration();
    }

    public AddAudioEncoderConfigurationResponse createAddAudioEncoderConfigurationResponse() {
        return new AddAudioEncoderConfigurationResponse();
    }

    public RemoveAudioEncoderConfiguration createRemoveAudioEncoderConfiguration() {
        return new RemoveAudioEncoderConfiguration();
    }

    public RemoveAudioEncoderConfigurationResponse createRemoveAudioEncoderConfigurationResponse() {
        return new RemoveAudioEncoderConfigurationResponse();
    }

    public AddAudioSourceConfiguration createAddAudioSourceConfiguration() {
        return new AddAudioSourceConfiguration();
    }

    public AddAudioSourceConfigurationResponse createAddAudioSourceConfigurationResponse() {
        return new AddAudioSourceConfigurationResponse();
    }

    public RemoveAudioSourceConfiguration createRemoveAudioSourceConfiguration() {
        return new RemoveAudioSourceConfiguration();
    }

    public RemoveAudioSourceConfigurationResponse createRemoveAudioSourceConfigurationResponse() {
        return new RemoveAudioSourceConfigurationResponse();
    }

    public AddPTZConfiguration createAddPTZConfiguration() {
        return new AddPTZConfiguration();
    }

    public AddPTZConfigurationResponse createAddPTZConfigurationResponse() {
        return new AddPTZConfigurationResponse();
    }

    public RemovePTZConfiguration createRemovePTZConfiguration() {
        return new RemovePTZConfiguration();
    }

    public RemovePTZConfigurationResponse createRemovePTZConfigurationResponse() {
        return new RemovePTZConfigurationResponse();
    }

    public AddVideoAnalyticsConfiguration createAddVideoAnalyticsConfiguration() {
        return new AddVideoAnalyticsConfiguration();
    }

    public AddVideoAnalyticsConfigurationResponse createAddVideoAnalyticsConfigurationResponse() {
        return new AddVideoAnalyticsConfigurationResponse();
    }

    public RemoveVideoAnalyticsConfiguration createRemoveVideoAnalyticsConfiguration() {
        return new RemoveVideoAnalyticsConfiguration();
    }

    public RemoveVideoAnalyticsConfigurationResponse createRemoveVideoAnalyticsConfigurationResponse() {
        return new RemoveVideoAnalyticsConfigurationResponse();
    }

    public AddMetadataConfiguration createAddMetadataConfiguration() {
        return new AddMetadataConfiguration();
    }

    public AddMetadataConfigurationResponse createAddMetadataConfigurationResponse() {
        return new AddMetadataConfigurationResponse();
    }

    public RemoveMetadataConfiguration createRemoveMetadataConfiguration() {
        return new RemoveMetadataConfiguration();
    }

    public RemoveMetadataConfigurationResponse createRemoveMetadataConfigurationResponse() {
        return new RemoveMetadataConfigurationResponse();
    }

    public AddAudioOutputConfiguration createAddAudioOutputConfiguration() {
        return new AddAudioOutputConfiguration();
    }

    public AddAudioOutputConfigurationResponse createAddAudioOutputConfigurationResponse() {
        return new AddAudioOutputConfigurationResponse();
    }

    public RemoveAudioOutputConfiguration createRemoveAudioOutputConfiguration() {
        return new RemoveAudioOutputConfiguration();
    }

    public RemoveAudioOutputConfigurationResponse createRemoveAudioOutputConfigurationResponse() {
        return new RemoveAudioOutputConfigurationResponse();
    }

    public AddAudioDecoderConfiguration createAddAudioDecoderConfiguration() {
        return new AddAudioDecoderConfiguration();
    }

    public AddAudioDecoderConfigurationResponse createAddAudioDecoderConfigurationResponse() {
        return new AddAudioDecoderConfigurationResponse();
    }

    public RemoveAudioDecoderConfiguration createRemoveAudioDecoderConfiguration() {
        return new RemoveAudioDecoderConfiguration();
    }

    public RemoveAudioDecoderConfigurationResponse createRemoveAudioDecoderConfigurationResponse() {
        return new RemoveAudioDecoderConfigurationResponse();
    }

    public DeleteProfile createDeleteProfile() {
        return new DeleteProfile();
    }

    public DeleteProfileResponse createDeleteProfileResponse() {
        return new DeleteProfileResponse();
    }

    public GetVideoEncoderConfigurations createGetVideoEncoderConfigurations() {
        return new GetVideoEncoderConfigurations();
    }

    public GetVideoEncoderConfigurationsResponse createGetVideoEncoderConfigurationsResponse() {
        return new GetVideoEncoderConfigurationsResponse();
    }

    public GetVideoSourceConfigurations createGetVideoSourceConfigurations() {
        return new GetVideoSourceConfigurations();
    }

    public GetVideoSourceConfigurationsResponse createGetVideoSourceConfigurationsResponse() {
        return new GetVideoSourceConfigurationsResponse();
    }

    public GetAudioEncoderConfigurations createGetAudioEncoderConfigurations() {
        return new GetAudioEncoderConfigurations();
    }

    public GetAudioEncoderConfigurationsResponse createGetAudioEncoderConfigurationsResponse() {
        return new GetAudioEncoderConfigurationsResponse();
    }

    public GetAudioSourceConfigurations createGetAudioSourceConfigurations() {
        return new GetAudioSourceConfigurations();
    }

    public GetAudioSourceConfigurationsResponse createGetAudioSourceConfigurationsResponse() {
        return new GetAudioSourceConfigurationsResponse();
    }

    public GetVideoAnalyticsConfigurations createGetVideoAnalyticsConfigurations() {
        return new GetVideoAnalyticsConfigurations();
    }

    public GetVideoAnalyticsConfigurationsResponse createGetVideoAnalyticsConfigurationsResponse() {
        return new GetVideoAnalyticsConfigurationsResponse();
    }

    public GetMetadataConfigurations createGetMetadataConfigurations() {
        return new GetMetadataConfigurations();
    }

    public GetMetadataConfigurationsResponse createGetMetadataConfigurationsResponse() {
        return new GetMetadataConfigurationsResponse();
    }

    public GetAudioOutputConfigurations createGetAudioOutputConfigurations() {
        return new GetAudioOutputConfigurations();
    }

    public GetAudioOutputConfigurationsResponse createGetAudioOutputConfigurationsResponse() {
        return new GetAudioOutputConfigurationsResponse();
    }

    public GetAudioDecoderConfigurations createGetAudioDecoderConfigurations() {
        return new GetAudioDecoderConfigurations();
    }

    public GetAudioDecoderConfigurationsResponse createGetAudioDecoderConfigurationsResponse() {
        return new GetAudioDecoderConfigurationsResponse();
    }

    public GetVideoSourceConfiguration createGetVideoSourceConfiguration() {
        return new GetVideoSourceConfiguration();
    }

    public GetVideoSourceConfigurationResponse createGetVideoSourceConfigurationResponse() {
        return new GetVideoSourceConfigurationResponse();
    }

    public GetVideoEncoderConfiguration createGetVideoEncoderConfiguration() {
        return new GetVideoEncoderConfiguration();
    }

    public GetVideoEncoderConfigurationResponse createGetVideoEncoderConfigurationResponse() {
        return new GetVideoEncoderConfigurationResponse();
    }

    public GetAudioSourceConfiguration createGetAudioSourceConfiguration() {
        return new GetAudioSourceConfiguration();
    }

    public GetAudioSourceConfigurationResponse createGetAudioSourceConfigurationResponse() {
        return new GetAudioSourceConfigurationResponse();
    }

    public GetAudioEncoderConfiguration createGetAudioEncoderConfiguration() {
        return new GetAudioEncoderConfiguration();
    }

    public GetAudioEncoderConfigurationResponse createGetAudioEncoderConfigurationResponse() {
        return new GetAudioEncoderConfigurationResponse();
    }

    public GetVideoAnalyticsConfiguration createGetVideoAnalyticsConfiguration() {
        return new GetVideoAnalyticsConfiguration();
    }

    public GetVideoAnalyticsConfigurationResponse createGetVideoAnalyticsConfigurationResponse() {
        return new GetVideoAnalyticsConfigurationResponse();
    }

    public GetMetadataConfiguration createGetMetadataConfiguration() {
        return new GetMetadataConfiguration();
    }

    public GetMetadataConfigurationResponse createGetMetadataConfigurationResponse() {
        return new GetMetadataConfigurationResponse();
    }

    public GetAudioOutputConfiguration createGetAudioOutputConfiguration() {
        return new GetAudioOutputConfiguration();
    }

    public GetAudioOutputConfigurationResponse createGetAudioOutputConfigurationResponse() {
        return new GetAudioOutputConfigurationResponse();
    }

    public GetAudioDecoderConfiguration createGetAudioDecoderConfiguration() {
        return new GetAudioDecoderConfiguration();
    }

    public GetAudioDecoderConfigurationResponse createGetAudioDecoderConfigurationResponse() {
        return new GetAudioDecoderConfigurationResponse();
    }

    public GetCompatibleVideoEncoderConfigurations createGetCompatibleVideoEncoderConfigurations() {
        return new GetCompatibleVideoEncoderConfigurations();
    }

    public GetCompatibleVideoEncoderConfigurationsResponse createGetCompatibleVideoEncoderConfigurationsResponse() {
        return new GetCompatibleVideoEncoderConfigurationsResponse();
    }

    public GetCompatibleVideoSourceConfigurations createGetCompatibleVideoSourceConfigurations() {
        return new GetCompatibleVideoSourceConfigurations();
    }

    public GetCompatibleVideoSourceConfigurationsResponse createGetCompatibleVideoSourceConfigurationsResponse() {
        return new GetCompatibleVideoSourceConfigurationsResponse();
    }

    public GetCompatibleAudioEncoderConfigurations createGetCompatibleAudioEncoderConfigurations() {
        return new GetCompatibleAudioEncoderConfigurations();
    }

    public GetCompatibleAudioEncoderConfigurationsResponse createGetCompatibleAudioEncoderConfigurationsResponse() {
        return new GetCompatibleAudioEncoderConfigurationsResponse();
    }

    public GetCompatibleAudioSourceConfigurations createGetCompatibleAudioSourceConfigurations() {
        return new GetCompatibleAudioSourceConfigurations();
    }

    public GetCompatibleAudioSourceConfigurationsResponse createGetCompatibleAudioSourceConfigurationsResponse() {
        return new GetCompatibleAudioSourceConfigurationsResponse();
    }

    public GetCompatibleVideoAnalyticsConfigurations createGetCompatibleVideoAnalyticsConfigurations() {
        return new GetCompatibleVideoAnalyticsConfigurations();
    }

    public GetCompatibleVideoAnalyticsConfigurationsResponse createGetCompatibleVideoAnalyticsConfigurationsResponse() {
        return new GetCompatibleVideoAnalyticsConfigurationsResponse();
    }

    public GetCompatibleMetadataConfigurations createGetCompatibleMetadataConfigurations() {
        return new GetCompatibleMetadataConfigurations();
    }

    public GetCompatibleMetadataConfigurationsResponse createGetCompatibleMetadataConfigurationsResponse() {
        return new GetCompatibleMetadataConfigurationsResponse();
    }

    public GetCompatibleAudioOutputConfigurations createGetCompatibleAudioOutputConfigurations() {
        return new GetCompatibleAudioOutputConfigurations();
    }

    public GetCompatibleAudioOutputConfigurationsResponse createGetCompatibleAudioOutputConfigurationsResponse() {
        return new GetCompatibleAudioOutputConfigurationsResponse();
    }

    public GetCompatibleAudioDecoderConfigurations createGetCompatibleAudioDecoderConfigurations() {
        return new GetCompatibleAudioDecoderConfigurations();
    }

    public GetCompatibleAudioDecoderConfigurationsResponse createGetCompatibleAudioDecoderConfigurationsResponse() {
        return new GetCompatibleAudioDecoderConfigurationsResponse();
    }

    public SetVideoEncoderConfiguration createSetVideoEncoderConfiguration() {
        return new SetVideoEncoderConfiguration();
    }

    public SetVideoEncoderConfigurationResponse createSetVideoEncoderConfigurationResponse() {
        return new SetVideoEncoderConfigurationResponse();
    }

    public SetVideoSourceConfiguration createSetVideoSourceConfiguration() {
        return new SetVideoSourceConfiguration();
    }

    public SetVideoSourceConfigurationResponse createSetVideoSourceConfigurationResponse() {
        return new SetVideoSourceConfigurationResponse();
    }

    public SetAudioEncoderConfiguration createSetAudioEncoderConfiguration() {
        return new SetAudioEncoderConfiguration();
    }

    public SetAudioEncoderConfigurationResponse createSetAudioEncoderConfigurationResponse() {
        return new SetAudioEncoderConfigurationResponse();
    }

    public SetAudioSourceConfiguration createSetAudioSourceConfiguration() {
        return new SetAudioSourceConfiguration();
    }

    public SetAudioSourceConfigurationResponse createSetAudioSourceConfigurationResponse() {
        return new SetAudioSourceConfigurationResponse();
    }

    public SetVideoAnalyticsConfiguration createSetVideoAnalyticsConfiguration() {
        return new SetVideoAnalyticsConfiguration();
    }

    public SetVideoAnalyticsConfigurationResponse createSetVideoAnalyticsConfigurationResponse() {
        return new SetVideoAnalyticsConfigurationResponse();
    }

    public SetMetadataConfiguration createSetMetadataConfiguration() {
        return new SetMetadataConfiguration();
    }

    public SetMetadataConfigurationResponse createSetMetadataConfigurationResponse() {
        return new SetMetadataConfigurationResponse();
    }

    public SetAudioOutputConfiguration createSetAudioOutputConfiguration() {
        return new SetAudioOutputConfiguration();
    }

    public SetAudioOutputConfigurationResponse createSetAudioOutputConfigurationResponse() {
        return new SetAudioOutputConfigurationResponse();
    }

    public SetAudioDecoderConfiguration createSetAudioDecoderConfiguration() {
        return new SetAudioDecoderConfiguration();
    }

    public SetAudioDecoderConfigurationResponse createSetAudioDecoderConfigurationResponse() {
        return new SetAudioDecoderConfigurationResponse();
    }

    public GetVideoSourceConfigurationOptions createGetVideoSourceConfigurationOptions() {
        return new GetVideoSourceConfigurationOptions();
    }

    public GetVideoSourceConfigurationOptionsResponse createGetVideoSourceConfigurationOptionsResponse() {
        return new GetVideoSourceConfigurationOptionsResponse();
    }

    public GetVideoEncoderConfigurationOptions createGetVideoEncoderConfigurationOptions() {
        return new GetVideoEncoderConfigurationOptions();
    }

    public GetVideoEncoderConfigurationOptionsResponse createGetVideoEncoderConfigurationOptionsResponse() {
        return new GetVideoEncoderConfigurationOptionsResponse();
    }

    public GetAudioSourceConfigurationOptions createGetAudioSourceConfigurationOptions() {
        return new GetAudioSourceConfigurationOptions();
    }

    public GetAudioSourceConfigurationOptionsResponse createGetAudioSourceConfigurationOptionsResponse() {
        return new GetAudioSourceConfigurationOptionsResponse();
    }

    public GetAudioEncoderConfigurationOptions createGetAudioEncoderConfigurationOptions() {
        return new GetAudioEncoderConfigurationOptions();
    }

    public GetAudioEncoderConfigurationOptionsResponse createGetAudioEncoderConfigurationOptionsResponse() {
        return new GetAudioEncoderConfigurationOptionsResponse();
    }

    public GetMetadataConfigurationOptions createGetMetadataConfigurationOptions() {
        return new GetMetadataConfigurationOptions();
    }

    public GetMetadataConfigurationOptionsResponse createGetMetadataConfigurationOptionsResponse() {
        return new GetMetadataConfigurationOptionsResponse();
    }

    public GetAudioOutputConfigurationOptions createGetAudioOutputConfigurationOptions() {
        return new GetAudioOutputConfigurationOptions();
    }

    public GetAudioOutputConfigurationOptionsResponse createGetAudioOutputConfigurationOptionsResponse() {
        return new GetAudioOutputConfigurationOptionsResponse();
    }

    public GetAudioDecoderConfigurationOptions createGetAudioDecoderConfigurationOptions() {
        return new GetAudioDecoderConfigurationOptions();
    }

    public GetAudioDecoderConfigurationOptionsResponse createGetAudioDecoderConfigurationOptionsResponse() {
        return new GetAudioDecoderConfigurationOptionsResponse();
    }

    public GetGuaranteedNumberOfVideoEncoderInstances createGetGuaranteedNumberOfVideoEncoderInstances() {
        return new GetGuaranteedNumberOfVideoEncoderInstances();
    }

    public GetGuaranteedNumberOfVideoEncoderInstancesResponse createGetGuaranteedNumberOfVideoEncoderInstancesResponse() {
        return new GetGuaranteedNumberOfVideoEncoderInstancesResponse();
    }

    public GetStreamUri createGetStreamUri() {
        return new GetStreamUri();
    }

    public GetStreamUriResponse createGetStreamUriResponse() {
        return new GetStreamUriResponse();
    }

    public StartMulticastStreaming createStartMulticastStreaming() {
        return new StartMulticastStreaming();
    }

    public StartMulticastStreamingResponse createStartMulticastStreamingResponse() {
        return new StartMulticastStreamingResponse();
    }

    public StopMulticastStreaming createStopMulticastStreaming() {
        return new StopMulticastStreaming();
    }

    public StopMulticastStreamingResponse createStopMulticastStreamingResponse() {
        return new StopMulticastStreamingResponse();
    }

    public SetSynchronizationPoint createSetSynchronizationPoint() {
        return new SetSynchronizationPoint();
    }

    public SetSynchronizationPointResponse createSetSynchronizationPointResponse() {
        return new SetSynchronizationPointResponse();
    }

    public GetSnapshotUri createGetSnapshotUri() {
        return new GetSnapshotUri();
    }

    public GetSnapshotUriResponse createGetSnapshotUriResponse() {
        return new GetSnapshotUriResponse();
    }

    public GetVideoSourceModes createGetVideoSourceModes() {
        return new GetVideoSourceModes();
    }

    public GetVideoSourceModesResponse createGetVideoSourceModesResponse() {
        return new GetVideoSourceModesResponse();
    }

    public VideoSourceMode createVideoSourceMode() {
        return new VideoSourceMode();
    }

    public SetVideoSourceMode createSetVideoSourceMode() {
        return new SetVideoSourceMode();
    }

    public SetVideoSourceModeResponse createSetVideoSourceModeResponse() {
        return new SetVideoSourceModeResponse();
    }

    public GetOSDs createGetOSDs() {
        return new GetOSDs();
    }

    public GetOSDsResponse createGetOSDsResponse() {
        return new GetOSDsResponse();
    }

    public GetOSD createGetOSD() {
        return new GetOSD();
    }

    public GetOSDResponse createGetOSDResponse() {
        return new GetOSDResponse();
    }

    public SetOSD createSetOSD() {
        return new SetOSD();
    }

    public SetOSDResponse createSetOSDResponse() {
        return new SetOSDResponse();
    }

    public GetOSDOptions createGetOSDOptions() {
        return new GetOSDOptions();
    }

    public GetOSDOptionsResponse createGetOSDOptionsResponse() {
        return new GetOSDOptionsResponse();
    }

    public CreateOSD createCreateOSD() {
        return new CreateOSD();
    }

    public CreateOSDResponse createCreateOSDResponse() {
        return new CreateOSDResponse();
    }

    public DeleteOSD createDeleteOSD() {
        return new DeleteOSD();
    }

    public DeleteOSDResponse createDeleteOSDResponse() {
        return new DeleteOSDResponse();
    }

    public ProfileCapabilities createProfileCapabilities() {
        return new ProfileCapabilities();
    }

    public StreamingCapabilities createStreamingCapabilities() {
        return new StreamingCapabilities();
    }

    public VideoSourceModeExtension createVideoSourceModeExtension() {
        return new VideoSourceModeExtension();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/media/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }
}

