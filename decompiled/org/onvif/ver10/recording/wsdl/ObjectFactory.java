/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.recording.wsdl.Capabilities;
import org.onvif.ver10.recording.wsdl.CreateRecording;
import org.onvif.ver10.recording.wsdl.CreateRecordingJob;
import org.onvif.ver10.recording.wsdl.CreateRecordingJobResponse;
import org.onvif.ver10.recording.wsdl.CreateRecordingResponse;
import org.onvif.ver10.recording.wsdl.CreateTrack;
import org.onvif.ver10.recording.wsdl.CreateTrackResponse;
import org.onvif.ver10.recording.wsdl.DeleteRecording;
import org.onvif.ver10.recording.wsdl.DeleteRecordingJob;
import org.onvif.ver10.recording.wsdl.DeleteRecordingJobResponse;
import org.onvif.ver10.recording.wsdl.DeleteRecordingResponse;
import org.onvif.ver10.recording.wsdl.DeleteTrack;
import org.onvif.ver10.recording.wsdl.DeleteTrackResponse;
import org.onvif.ver10.recording.wsdl.ExportRecordedData;
import org.onvif.ver10.recording.wsdl.ExportRecordedDataResponse;
import org.onvif.ver10.recording.wsdl.GetExportRecordedDataState;
import org.onvif.ver10.recording.wsdl.GetExportRecordedDataStateResponse;
import org.onvif.ver10.recording.wsdl.GetRecordingConfiguration;
import org.onvif.ver10.recording.wsdl.GetRecordingConfigurationResponse;
import org.onvif.ver10.recording.wsdl.GetRecordingJobConfiguration;
import org.onvif.ver10.recording.wsdl.GetRecordingJobConfigurationResponse;
import org.onvif.ver10.recording.wsdl.GetRecordingJobState;
import org.onvif.ver10.recording.wsdl.GetRecordingJobStateResponse;
import org.onvif.ver10.recording.wsdl.GetRecordingJobs;
import org.onvif.ver10.recording.wsdl.GetRecordingJobsResponse;
import org.onvif.ver10.recording.wsdl.GetRecordingOptions;
import org.onvif.ver10.recording.wsdl.GetRecordingOptionsResponse;
import org.onvif.ver10.recording.wsdl.GetRecordings;
import org.onvif.ver10.recording.wsdl.GetRecordingsResponse;
import org.onvif.ver10.recording.wsdl.GetServiceCapabilities;
import org.onvif.ver10.recording.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.recording.wsdl.GetTrackConfiguration;
import org.onvif.ver10.recording.wsdl.GetTrackConfigurationResponse;
import org.onvif.ver10.recording.wsdl.JobOptions;
import org.onvif.ver10.recording.wsdl.RecordingOptions;
import org.onvif.ver10.recording.wsdl.SetRecordingConfiguration;
import org.onvif.ver10.recording.wsdl.SetRecordingConfigurationResponse;
import org.onvif.ver10.recording.wsdl.SetRecordingJobConfiguration;
import org.onvif.ver10.recording.wsdl.SetRecordingJobConfigurationResponse;
import org.onvif.ver10.recording.wsdl.SetRecordingJobMode;
import org.onvif.ver10.recording.wsdl.SetRecordingJobModeResponse;
import org.onvif.ver10.recording.wsdl.SetTrackConfiguration;
import org.onvif.ver10.recording.wsdl.SetTrackConfigurationResponse;
import org.onvif.ver10.recording.wsdl.StopExportRecordedData;
import org.onvif.ver10.recording.wsdl.StopExportRecordedDataResponse;
import org.onvif.ver10.recording.wsdl.TrackOptions;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/recording/wsdl", "Capabilities");

    public ExportRecordedDataResponse createExportRecordedDataResponse() {
        return new ExportRecordedDataResponse();
    }

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public CreateRecording createCreateRecording() {
        return new CreateRecording();
    }

    public CreateRecordingResponse createCreateRecordingResponse() {
        return new CreateRecordingResponse();
    }

    public DeleteRecording createDeleteRecording() {
        return new DeleteRecording();
    }

    public DeleteRecordingResponse createDeleteRecordingResponse() {
        return new DeleteRecordingResponse();
    }

    public GetRecordings createGetRecordings() {
        return new GetRecordings();
    }

    public GetRecordingsResponse createGetRecordingsResponse() {
        return new GetRecordingsResponse();
    }

    public SetRecordingConfiguration createSetRecordingConfiguration() {
        return new SetRecordingConfiguration();
    }

    public SetRecordingConfigurationResponse createSetRecordingConfigurationResponse() {
        return new SetRecordingConfigurationResponse();
    }

    public GetRecordingConfiguration createGetRecordingConfiguration() {
        return new GetRecordingConfiguration();
    }

    public GetRecordingConfigurationResponse createGetRecordingConfigurationResponse() {
        return new GetRecordingConfigurationResponse();
    }

    public CreateTrack createCreateTrack() {
        return new CreateTrack();
    }

    public CreateTrackResponse createCreateTrackResponse() {
        return new CreateTrackResponse();
    }

    public DeleteTrack createDeleteTrack() {
        return new DeleteTrack();
    }

    public DeleteTrackResponse createDeleteTrackResponse() {
        return new DeleteTrackResponse();
    }

    public GetTrackConfiguration createGetTrackConfiguration() {
        return new GetTrackConfiguration();
    }

    public GetTrackConfigurationResponse createGetTrackConfigurationResponse() {
        return new GetTrackConfigurationResponse();
    }

    public SetTrackConfiguration createSetTrackConfiguration() {
        return new SetTrackConfiguration();
    }

    public SetTrackConfigurationResponse createSetTrackConfigurationResponse() {
        return new SetTrackConfigurationResponse();
    }

    public CreateRecordingJob createCreateRecordingJob() {
        return new CreateRecordingJob();
    }

    public CreateRecordingJobResponse createCreateRecordingJobResponse() {
        return new CreateRecordingJobResponse();
    }

    public DeleteRecordingJob createDeleteRecordingJob() {
        return new DeleteRecordingJob();
    }

    public DeleteRecordingJobResponse createDeleteRecordingJobResponse() {
        return new DeleteRecordingJobResponse();
    }

    public GetRecordingJobs createGetRecordingJobs() {
        return new GetRecordingJobs();
    }

    public GetRecordingJobsResponse createGetRecordingJobsResponse() {
        return new GetRecordingJobsResponse();
    }

    public SetRecordingJobConfiguration createSetRecordingJobConfiguration() {
        return new SetRecordingJobConfiguration();
    }

    public SetRecordingJobConfigurationResponse createSetRecordingJobConfigurationResponse() {
        return new SetRecordingJobConfigurationResponse();
    }

    public GetRecordingJobConfiguration createGetRecordingJobConfiguration() {
        return new GetRecordingJobConfiguration();
    }

    public GetRecordingJobConfigurationResponse createGetRecordingJobConfigurationResponse() {
        return new GetRecordingJobConfigurationResponse();
    }

    public SetRecordingJobMode createSetRecordingJobMode() {
        return new SetRecordingJobMode();
    }

    public SetRecordingJobModeResponse createSetRecordingJobModeResponse() {
        return new SetRecordingJobModeResponse();
    }

    public GetRecordingJobState createGetRecordingJobState() {
        return new GetRecordingJobState();
    }

    public GetRecordingJobStateResponse createGetRecordingJobStateResponse() {
        return new GetRecordingJobStateResponse();
    }

    public GetRecordingOptions createGetRecordingOptions() {
        return new GetRecordingOptions();
    }

    public GetRecordingOptionsResponse createGetRecordingOptionsResponse() {
        return new GetRecordingOptionsResponse();
    }

    public RecordingOptions createRecordingOptions() {
        return new RecordingOptions();
    }

    public ExportRecordedData createExportRecordedData() {
        return new ExportRecordedData();
    }

    public ExportRecordedDataResponse.Extension createExportRecordedDataResponseExtension() {
        return new ExportRecordedDataResponse.Extension();
    }

    public StopExportRecordedData createStopExportRecordedData() {
        return new StopExportRecordedData();
    }

    public StopExportRecordedDataResponse createStopExportRecordedDataResponse() {
        return new StopExportRecordedDataResponse();
    }

    public GetExportRecordedDataState createGetExportRecordedDataState() {
        return new GetExportRecordedDataState();
    }

    public GetExportRecordedDataStateResponse createGetExportRecordedDataStateResponse() {
        return new GetExportRecordedDataStateResponse();
    }

    public JobOptions createJobOptions() {
        return new JobOptions();
    }

    public TrackOptions createTrackOptions() {
        return new TrackOptions();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/recording/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }
}

