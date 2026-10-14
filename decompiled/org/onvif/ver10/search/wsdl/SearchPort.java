/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.schema.EventFilter;
import org.onvif.ver10.schema.FindEventResultList;
import org.onvif.ver10.schema.FindMetadataResultList;
import org.onvif.ver10.schema.FindPTZPositionResultList;
import org.onvif.ver10.schema.FindRecordingResultList;
import org.onvif.ver10.schema.MediaAttributes;
import org.onvif.ver10.schema.MetadataFilter;
import org.onvif.ver10.schema.PTZPositionFilter;
import org.onvif.ver10.schema.RecordingInformation;
import org.onvif.ver10.schema.RecordingSummary;
import org.onvif.ver10.schema.SearchScope;
import org.onvif.ver10.schema.SearchState;
import org.onvif.ver10.search.wsdl.Capabilities;
import org.onvif.ver10.search.wsdl.ObjectFactory;

@WebService(targetNamespace="http://www.onvif.org/ver10/search/wsdl", name="SearchPort")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface SearchPort {
    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/search/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public Capabilities getServiceCapabilities();

    @WebMethod(operationName="FindPTZPosition", action="http://www.onvif.org/ver10/search/wsdl/FindPTZPosition")
    @RequestWrapper(localName="FindPTZPosition", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.FindPTZPosition")
    @ResponseWrapper(localName="FindPTZPositionResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.FindPTZPositionResponse")
    @WebResult(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public String findPTZPosition(@WebParam(name="StartPoint", targetNamespace="http://www.onvif.org/ver10/search/wsdl") XMLGregorianCalendar var1, @WebParam(name="EndPoint", targetNamespace="http://www.onvif.org/ver10/search/wsdl") XMLGregorianCalendar var2, @WebParam(name="Scope", targetNamespace="http://www.onvif.org/ver10/search/wsdl") SearchScope var3, @WebParam(name="SearchFilter", targetNamespace="http://www.onvif.org/ver10/search/wsdl") PTZPositionFilter var4, @WebParam(name="MaxMatches", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var5, @WebParam(name="KeepAliveTime", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Duration var6);

    @WebMethod(operationName="FindEvents", action="http://www.onvif.org/ver10/search/wsdl/FindEvents")
    @RequestWrapper(localName="FindEvents", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.FindEvents")
    @ResponseWrapper(localName="FindEventsResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.FindEventsResponse")
    @WebResult(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public String findEvents(@WebParam(name="StartPoint", targetNamespace="http://www.onvif.org/ver10/search/wsdl") XMLGregorianCalendar var1, @WebParam(name="EndPoint", targetNamespace="http://www.onvif.org/ver10/search/wsdl") XMLGregorianCalendar var2, @WebParam(name="Scope", targetNamespace="http://www.onvif.org/ver10/search/wsdl") SearchScope var3, @WebParam(name="SearchFilter", targetNamespace="http://www.onvif.org/ver10/search/wsdl") EventFilter var4, @WebParam(name="IncludeStartState", targetNamespace="http://www.onvif.org/ver10/search/wsdl") boolean var5, @WebParam(name="MaxMatches", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var6, @WebParam(name="KeepAliveTime", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Duration var7);

    @WebMethod(operationName="GetRecordingSearchResults", action="http://www.onvif.org/ver10/search/wsdl/GetRecordingSearchResults")
    @RequestWrapper(localName="GetRecordingSearchResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetRecordingSearchResults")
    @ResponseWrapper(localName="GetRecordingSearchResultsResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetRecordingSearchResultsResponse")
    @WebResult(name="ResultList", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public FindRecordingResultList getRecordingSearchResults(@WebParam(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl") String var1, @WebParam(name="MinResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var2, @WebParam(name="MaxResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var3, @WebParam(name="WaitTime", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Duration var4);

    @WebMethod(operationName="GetRecordingSummary", action="http://www.onvif.org/ver10/search/wsdl/GetRecordingSummary")
    @RequestWrapper(localName="GetRecordingSummary", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetRecordingSummary")
    @ResponseWrapper(localName="GetRecordingSummaryResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetRecordingSummaryResponse")
    @WebResult(name="Summary", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public RecordingSummary getRecordingSummary();

    @WebMethod(operationName="GetPTZPositionSearchResults", action="http://www.onvif.org/ver10/search/wsdl/GetPTZPositionSearchResults")
    @RequestWrapper(localName="GetPTZPositionSearchResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetPTZPositionSearchResults")
    @ResponseWrapper(localName="GetPTZPositionSearchResultsResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetPTZPositionSearchResultsResponse")
    @WebResult(name="ResultList", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public FindPTZPositionResultList getPTZPositionSearchResults(@WebParam(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl") String var1, @WebParam(name="MinResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var2, @WebParam(name="MaxResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var3, @WebParam(name="WaitTime", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Duration var4);

    @WebMethod(operationName="GetSearchState", action="http://www.onvif.org/ver10/search/wsdl/GetSearchState")
    @RequestWrapper(localName="GetSearchState", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetSearchState")
    @ResponseWrapper(localName="GetSearchStateResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetSearchStateResponse")
    @WebResult(name="State", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public SearchState getSearchState(@WebParam(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl") String var1);

    @WebMethod(operationName="FindMetadata", action="http://www.onvif.org/ver10/search/wsdl/FindMetadata")
    @RequestWrapper(localName="FindMetadata", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.FindMetadata")
    @ResponseWrapper(localName="FindMetadataResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.FindMetadataResponse")
    @WebResult(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public String findMetadata(@WebParam(name="StartPoint", targetNamespace="http://www.onvif.org/ver10/search/wsdl") XMLGregorianCalendar var1, @WebParam(name="EndPoint", targetNamespace="http://www.onvif.org/ver10/search/wsdl") XMLGregorianCalendar var2, @WebParam(name="Scope", targetNamespace="http://www.onvif.org/ver10/search/wsdl") SearchScope var3, @WebParam(name="MetadataFilter", targetNamespace="http://www.onvif.org/ver10/search/wsdl") MetadataFilter var4, @WebParam(name="MaxMatches", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var5, @WebParam(name="KeepAliveTime", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Duration var6);

    @WebMethod(operationName="GetEventSearchResults", action="http://www.onvif.org/ver10/search/wsdl/GetEventSearchResults")
    @RequestWrapper(localName="GetEventSearchResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetEventSearchResults")
    @ResponseWrapper(localName="GetEventSearchResultsResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetEventSearchResultsResponse")
    @WebResult(name="ResultList", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public FindEventResultList getEventSearchResults(@WebParam(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl") String var1, @WebParam(name="MinResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var2, @WebParam(name="MaxResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var3, @WebParam(name="WaitTime", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Duration var4);

    @WebMethod(operationName="EndSearch", action="http://www.onvif.org/ver10/search/wsdl/EndSearch")
    @RequestWrapper(localName="EndSearch", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.EndSearch")
    @ResponseWrapper(localName="EndSearchResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.EndSearchResponse")
    @WebResult(name="Endpoint", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public XMLGregorianCalendar endSearch(@WebParam(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl") String var1);

    @WebMethod(operationName="GetMediaAttributes", action="http://www.onvif.org/ver10/search/wsdl/GetMediaAttributes")
    @RequestWrapper(localName="GetMediaAttributes", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetMediaAttributes")
    @ResponseWrapper(localName="GetMediaAttributesResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetMediaAttributesResponse")
    @WebResult(name="MediaAttributes", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public List<MediaAttributes> getMediaAttributes(@WebParam(name="RecordingTokens", targetNamespace="http://www.onvif.org/ver10/search/wsdl") List<String> var1, @WebParam(name="Time", targetNamespace="http://www.onvif.org/ver10/search/wsdl") XMLGregorianCalendar var2);

    @WebMethod(operationName="GetMetadataSearchResults", action="http://www.onvif.org/ver10/search/wsdl/GetMetadataSearchResults")
    @RequestWrapper(localName="GetMetadataSearchResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetMetadataSearchResults")
    @ResponseWrapper(localName="GetMetadataSearchResultsResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetMetadataSearchResultsResponse")
    @WebResult(name="ResultList", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public FindMetadataResultList getMetadataSearchResults(@WebParam(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl") String var1, @WebParam(name="MinResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var2, @WebParam(name="MaxResults", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var3, @WebParam(name="WaitTime", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Duration var4);

    @WebMethod(operationName="GetRecordingInformation", action="http://www.onvif.org/ver10/search/wsdl/GetRecordingInformation")
    @RequestWrapper(localName="GetRecordingInformation", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetRecordingInformation")
    @ResponseWrapper(localName="GetRecordingInformationResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.GetRecordingInformationResponse")
    @WebResult(name="RecordingInformation", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public RecordingInformation getRecordingInformation(@WebParam(name="RecordingToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl") String var1);

    @WebMethod(operationName="FindRecordings", action="http://www.onvif.org/ver10/search/wsdl/FindRecordings")
    @RequestWrapper(localName="FindRecordings", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.FindRecordings")
    @ResponseWrapper(localName="FindRecordingsResponse", targetNamespace="http://www.onvif.org/ver10/search/wsdl", className="org.onvif.ver10.search.wsdl.FindRecordingsResponse")
    @WebResult(name="SearchToken", targetNamespace="http://www.onvif.org/ver10/search/wsdl")
    public String findRecordings(@WebParam(name="Scope", targetNamespace="http://www.onvif.org/ver10/search/wsdl") SearchScope var1, @WebParam(name="MaxMatches", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Integer var2, @WebParam(name="KeepAliveTime", targetNamespace="http://www.onvif.org/ver10/search/wsdl") Duration var3);
}

