/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

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
import org.onvif.ver10.recording.wsdl.Capabilities;
import org.onvif.ver10.recording.wsdl.ExportRecordedDataResponse;
import org.onvif.ver10.recording.wsdl.GetExportRecordedDataState;
import org.onvif.ver10.recording.wsdl.GetExportRecordedDataStateResponse;
import org.onvif.ver10.recording.wsdl.ObjectFactory;
import org.onvif.ver10.recording.wsdl.RecordingOptions;
import org.onvif.ver10.recording.wsdl.StopExportRecordedData;
import org.onvif.ver10.recording.wsdl.StopExportRecordedDataResponse;
import org.onvif.ver10.schema.GetRecordingJobsResponseItem;
import org.onvif.ver10.schema.GetRecordingsResponseItem;
import org.onvif.ver10.schema.RecordingConfiguration;
import org.onvif.ver10.schema.RecordingJobConfiguration;
import org.onvif.ver10.schema.RecordingJobStateInformation;
import org.onvif.ver10.schema.SearchScope;
import org.onvif.ver10.schema.StorageReferencePath;
import org.onvif.ver10.schema.TrackConfiguration;

@WebService(targetNamespace="http://www.onvif.org/ver10/recording/wsdl", name="RecordingPort")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface RecordingPort {
    @WebMethod(operationName="GetRecordings", action="http://www.onvif.org/ver10/recording/wsdl/GetRecordings")
    @RequestWrapper(localName="GetRecordings", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordings")
    @ResponseWrapper(localName="GetRecordingsResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingsResponse")
    @WebResult(name="RecordingItem", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public List<GetRecordingsResponseItem> getRecordings();

    @WebMethod(operationName="DeleteTrack", action="http://www.onvif.org/ver10/recording/wsdl/DeleteTrack")
    @RequestWrapper(localName="DeleteTrack", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.DeleteTrack")
    @ResponseWrapper(localName="DeleteTrackResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.DeleteTrackResponse")
    public void deleteTrack(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1, @WebParam(name="TrackToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var2);

    @WebMethod(operationName="DeleteRecording", action="http://www.onvif.org/ver10/recording/wsdl/DeleteRecording")
    @RequestWrapper(localName="DeleteRecording", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.DeleteRecording")
    @ResponseWrapper(localName="DeleteRecordingResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.DeleteRecordingResponse")
    public void deleteRecording(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1);

    @WebMethod(operationName="CreateTrack", action="http://www.onvif.org/ver10/recording/wsdl/CreateTrack")
    @RequestWrapper(localName="CreateTrack", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.CreateTrack")
    @ResponseWrapper(localName="CreateTrackResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.CreateTrackResponse")
    @WebResult(name="TrackToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public String createTrack(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1, @WebParam(name="TrackConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") TrackConfiguration var2);

    @WebMethod(operationName="GetRecordingJobs", action="http://www.onvif.org/ver10/recording/wsdl/GetRecordingJobs")
    @RequestWrapper(localName="GetRecordingJobs", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingJobs")
    @ResponseWrapper(localName="GetRecordingJobsResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingJobsResponse")
    @WebResult(name="JobItem", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public List<GetRecordingJobsResponseItem> getRecordingJobs();

    @WebMethod(operationName="SetRecordingJobConfiguration", action="http://www.onvif.org/ver10/recording/wsdl/SetRecordingJobConfiguration")
    @RequestWrapper(localName="SetRecordingJobConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.SetRecordingJobConfiguration")
    @ResponseWrapper(localName="SetRecordingJobConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.SetRecordingJobConfigurationResponse")
    public void setRecordingJobConfiguration(@WebParam(name="JobToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1, @WebParam(mode=WebParam.Mode.INOUT, name="JobConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") Holder<RecordingJobConfiguration> var2);

    @WebMethod(operationName="SetRecordingConfiguration", action="http://www.onvif.org/ver10/recording/wsdl/SetRecordingConfiguration")
    @RequestWrapper(localName="SetRecordingConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.SetRecordingConfiguration")
    @ResponseWrapper(localName="SetRecordingConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.SetRecordingConfigurationResponse")
    public void setRecordingConfiguration(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1, @WebParam(name="RecordingConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") RecordingConfiguration var2);

    @WebMethod(operationName="GetRecordingConfiguration", action="http://www.onvif.org/ver10/recording/wsdl/GetRecordingConfiguration")
    @RequestWrapper(localName="GetRecordingConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingConfiguration")
    @ResponseWrapper(localName="GetRecordingConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingConfigurationResponse")
    @WebResult(name="RecordingConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public RecordingConfiguration getRecordingConfiguration(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1);

    @WebMethod(operationName="GetExportRecordedDataState", action="http://www.onvif.org/ver10/recording/wsdl/GetExportRecordedDataState")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetExportRecordedDataStateResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", partName="parameters")
    public GetExportRecordedDataStateResponse getExportRecordedDataState(@WebParam(partName="parameters", name="GetExportRecordedDataState", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") GetExportRecordedDataState var1);

    @WebMethod(operationName="SetRecordingJobMode", action="http://www.onvif.org/ver10/recording/wsdl/SetRecordingJobMode")
    @RequestWrapper(localName="SetRecordingJobMode", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.SetRecordingJobMode")
    @ResponseWrapper(localName="SetRecordingJobModeResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.SetRecordingJobModeResponse")
    public void setRecordingJobMode(@WebParam(name="JobToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1, @WebParam(name="Mode", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var2);

    @WebMethod(operationName="GetTrackConfiguration", action="http://www.onvif.org/ver10/recording/wsdl/GetTrackConfiguration")
    @RequestWrapper(localName="GetTrackConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetTrackConfiguration")
    @ResponseWrapper(localName="GetTrackConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetTrackConfigurationResponse")
    @WebResult(name="TrackConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public TrackConfiguration getTrackConfiguration(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1, @WebParam(name="TrackToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var2);

    @WebMethod(operationName="ExportRecordedData", action="http://www.onvif.org/ver10/recording/wsdl/ExportRecordedData")
    @RequestWrapper(localName="ExportRecordedData", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.ExportRecordedData")
    @ResponseWrapper(localName="ExportRecordedDataResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.ExportRecordedDataResponse")
    public void exportRecordedData(@WebParam(name="SearchScope", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") SearchScope var1, @WebParam(name="FileFormat", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var2, @WebParam(name="StorageDestination", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") StorageReferencePath var3, @WebParam(mode=WebParam.Mode.OUT, name="OperationToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") Holder<String> var4, @WebParam(mode=WebParam.Mode.OUT, name="FileNames", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") Holder<List<String>> var5, @WebParam(mode=WebParam.Mode.OUT, name="Extension", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") Holder<ExportRecordedDataResponse.Extension> var6);

    @WebMethod(operationName="CreateRecording", action="http://www.onvif.org/ver10/recording/wsdl/CreateRecording")
    @RequestWrapper(localName="CreateRecording", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.CreateRecording")
    @ResponseWrapper(localName="CreateRecordingResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.CreateRecordingResponse")
    @WebResult(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public String createRecording(@WebParam(name="RecordingConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") RecordingConfiguration var1);

    @WebMethod(operationName="SetTrackConfiguration", action="http://www.onvif.org/ver10/recording/wsdl/SetTrackConfiguration")
    @RequestWrapper(localName="SetTrackConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.SetTrackConfiguration")
    @ResponseWrapper(localName="SetTrackConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.SetTrackConfigurationResponse")
    public void setTrackConfiguration(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1, @WebParam(name="TrackToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var2, @WebParam(name="TrackConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") TrackConfiguration var3);

    @WebMethod(operationName="CreateRecordingJob", action="http://www.onvif.org/ver10/recording/wsdl/CreateRecordingJob")
    @RequestWrapper(localName="CreateRecordingJob", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.CreateRecordingJob")
    @ResponseWrapper(localName="CreateRecordingJobResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.CreateRecordingJobResponse")
    public void createRecordingJob(@WebParam(mode=WebParam.Mode.INOUT, name="JobConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") Holder<RecordingJobConfiguration> var1, @WebParam(mode=WebParam.Mode.OUT, name="JobToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") Holder<String> var2);

    @WebMethod(operationName="GetRecordingJobConfiguration", action="http://www.onvif.org/ver10/recording/wsdl/GetRecordingJobConfiguration")
    @RequestWrapper(localName="GetRecordingJobConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingJobConfiguration")
    @ResponseWrapper(localName="GetRecordingJobConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingJobConfigurationResponse")
    @WebResult(name="JobConfiguration", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public RecordingJobConfiguration getRecordingJobConfiguration(@WebParam(name="JobToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1);

    @WebMethod(operationName="StopExportRecordedData", action="http://www.onvif.org/ver10/recording/wsdl/StopExportRecordedData")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="StopExportRecordedDataResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", partName="parameters")
    public StopExportRecordedDataResponse stopExportRecordedData(@WebParam(partName="parameters", name="StopExportRecordedData", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") StopExportRecordedData var1);

    @WebMethod(operationName="GetRecordingOptions", action="http://www.onvif.org/ver10/recording/wsdl/GetRecordingOptions")
    @RequestWrapper(localName="GetRecordingOptions", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingOptions")
    @ResponseWrapper(localName="GetRecordingOptionsResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingOptionsResponse")
    @WebResult(name="Options", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public RecordingOptions getRecordingOptions(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1);

    @WebMethod(operationName="DeleteRecordingJob", action="http://www.onvif.org/ver10/recording/wsdl/DeleteRecordingJob")
    @RequestWrapper(localName="DeleteRecordingJob", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.DeleteRecordingJob")
    @ResponseWrapper(localName="DeleteRecordingJobResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.DeleteRecordingJobResponse")
    public void deleteRecordingJob(@WebParam(name="JobToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/recording/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public Capabilities getServiceCapabilities();

    @WebMethod(operationName="GetRecordingJobState", action="http://www.onvif.org/ver10/recording/wsdl/GetRecordingJobState")
    @RequestWrapper(localName="GetRecordingJobState", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingJobState")
    @ResponseWrapper(localName="GetRecordingJobStateResponse", targetNamespace="http://www.onvif.org/ver10/recording/wsdl", className="org.onvif.ver10.recording.wsdl.GetRecordingJobStateResponse")
    @WebResult(name="State", targetNamespace="http://www.onvif.org/ver10/recording/wsdl")
    public RecordingJobStateInformation getRecordingJobState(@WebParam(name="JobToken", targetNamespace="http://www.onvif.org/ver10/recording/wsdl") String var1);
}

