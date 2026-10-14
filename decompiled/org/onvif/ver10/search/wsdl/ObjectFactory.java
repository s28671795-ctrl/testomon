/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.search.wsdl.Capabilities;
import org.onvif.ver10.search.wsdl.EndSearch;
import org.onvif.ver10.search.wsdl.EndSearchResponse;
import org.onvif.ver10.search.wsdl.FindEvents;
import org.onvif.ver10.search.wsdl.FindEventsResponse;
import org.onvif.ver10.search.wsdl.FindMetadata;
import org.onvif.ver10.search.wsdl.FindMetadataResponse;
import org.onvif.ver10.search.wsdl.FindPTZPosition;
import org.onvif.ver10.search.wsdl.FindPTZPositionResponse;
import org.onvif.ver10.search.wsdl.FindRecordings;
import org.onvif.ver10.search.wsdl.FindRecordingsResponse;
import org.onvif.ver10.search.wsdl.GetEventSearchResults;
import org.onvif.ver10.search.wsdl.GetEventSearchResultsResponse;
import org.onvif.ver10.search.wsdl.GetMediaAttributes;
import org.onvif.ver10.search.wsdl.GetMediaAttributesResponse;
import org.onvif.ver10.search.wsdl.GetMetadataSearchResults;
import org.onvif.ver10.search.wsdl.GetMetadataSearchResultsResponse;
import org.onvif.ver10.search.wsdl.GetPTZPositionSearchResults;
import org.onvif.ver10.search.wsdl.GetPTZPositionSearchResultsResponse;
import org.onvif.ver10.search.wsdl.GetRecordingInformation;
import org.onvif.ver10.search.wsdl.GetRecordingInformationResponse;
import org.onvif.ver10.search.wsdl.GetRecordingSearchResults;
import org.onvif.ver10.search.wsdl.GetRecordingSearchResultsResponse;
import org.onvif.ver10.search.wsdl.GetRecordingSummary;
import org.onvif.ver10.search.wsdl.GetRecordingSummaryResponse;
import org.onvif.ver10.search.wsdl.GetSearchState;
import org.onvif.ver10.search.wsdl.GetSearchStateResponse;
import org.onvif.ver10.search.wsdl.GetServiceCapabilities;
import org.onvif.ver10.search.wsdl.GetServiceCapabilitiesResponse;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/search/wsdl", "Capabilities");

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetRecordingSummary createGetRecordingSummary() {
        return new GetRecordingSummary();
    }

    public GetRecordingSummaryResponse createGetRecordingSummaryResponse() {
        return new GetRecordingSummaryResponse();
    }

    public GetRecordingInformation createGetRecordingInformation() {
        return new GetRecordingInformation();
    }

    public GetRecordingInformationResponse createGetRecordingInformationResponse() {
        return new GetRecordingInformationResponse();
    }

    public GetMediaAttributes createGetMediaAttributes() {
        return new GetMediaAttributes();
    }

    public GetMediaAttributesResponse createGetMediaAttributesResponse() {
        return new GetMediaAttributesResponse();
    }

    public FindRecordings createFindRecordings() {
        return new FindRecordings();
    }

    public FindRecordingsResponse createFindRecordingsResponse() {
        return new FindRecordingsResponse();
    }

    public GetRecordingSearchResults createGetRecordingSearchResults() {
        return new GetRecordingSearchResults();
    }

    public GetRecordingSearchResultsResponse createGetRecordingSearchResultsResponse() {
        return new GetRecordingSearchResultsResponse();
    }

    public FindEvents createFindEvents() {
        return new FindEvents();
    }

    public FindEventsResponse createFindEventsResponse() {
        return new FindEventsResponse();
    }

    public GetEventSearchResults createGetEventSearchResults() {
        return new GetEventSearchResults();
    }

    public GetEventSearchResultsResponse createGetEventSearchResultsResponse() {
        return new GetEventSearchResultsResponse();
    }

    public FindPTZPosition createFindPTZPosition() {
        return new FindPTZPosition();
    }

    public FindPTZPositionResponse createFindPTZPositionResponse() {
        return new FindPTZPositionResponse();
    }

    public GetPTZPositionSearchResults createGetPTZPositionSearchResults() {
        return new GetPTZPositionSearchResults();
    }

    public GetPTZPositionSearchResultsResponse createGetPTZPositionSearchResultsResponse() {
        return new GetPTZPositionSearchResultsResponse();
    }

    public FindMetadata createFindMetadata() {
        return new FindMetadata();
    }

    public FindMetadataResponse createFindMetadataResponse() {
        return new FindMetadataResponse();
    }

    public GetMetadataSearchResults createGetMetadataSearchResults() {
        return new GetMetadataSearchResults();
    }

    public GetMetadataSearchResultsResponse createGetMetadataSearchResultsResponse() {
        return new GetMetadataSearchResultsResponse();
    }

    public GetSearchState createGetSearchState() {
        return new GetSearchState();
    }

    public GetSearchStateResponse createGetSearchStateResponse() {
        return new GetSearchStateResponse();
    }

    public EndSearch createEndSearch() {
        return new EndSearch();
    }

    public EndSearchResponse createEndSearchResponse() {
        return new EndSearchResponse();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/search/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }
}

