/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;

public final class Names {
    public static final String WSA_NAMESPACE_NAME = "http://www.w3.org/2005/08/addressing";
    public static final String WSA_NAMESPACE_WSDL_NAME = "http://www.w3.org/2006/05/addressing/wsdl";
    public static final String WSA_NAMESPACE_WSDL_NAME_OLD = "http://www.w3.org/2005/02/addressing/wsdl";
    public static final String WSA_NAMESPACE_WSDL_METADATA = "http://www.w3.org/2007/05/addressing/metadata";
    public static final String WSA_NAMESPACE_PATTERN = "/addressing";
    public static final String WSA_REFERENCE_PARAMETERS_NAME = "ReferenceParameters";
    public static final QName WSA_REFERENCE_PARAMETERS_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ReferenceParameters");
    public static final String WSA_IS_REFERENCE_PARAMETER_NAME = "IsReferenceParameter";
    public static final QName WSA_IS_REFERENCE_PARAMETER_QNAME = new QName("http://www.w3.org/2005/08/addressing", "IsReferenceParameter");
    public static final String WSA_ADDRESS_NAME = "Address";
    public static final QName WSA_ADDRESS_QNAME = new QName("http://www.w3.org/2005/08/addressing", "Address");
    public static final String WSA_METADATA_NAME = "Metadata";
    public static final QName WSA_METADATA_QNAME = new QName("http://www.w3.org/2005/08/addressing", "Metadata");
    public static final String WSA_FROM_NAME = "From";
    public static final QName WSA_FROM_QNAME = new QName("http://www.w3.org/2005/08/addressing", "From");
    public static final String WSA_TO_NAME = "To";
    public static final QName WSA_TO_QNAME = new QName("http://www.w3.org/2005/08/addressing", "To");
    public static final String WSA_REPLYTO_NAME = "ReplyTo";
    public static final QName WSA_REPLYTO_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ReplyTo");
    public static final String WSA_FAULTTO_NAME = "FaultTo";
    public static final QName WSA_FAULTTO_QNAME = new QName("http://www.w3.org/2005/08/addressing", "FaultTo");
    public static final String WSA_ACTION_NAME = "Action";
    public static final QName WSA_ACTION_QNAME = new QName("http://www.w3.org/2005/08/addressing", "Action");
    public static final String WSA_MESSAGEID_NAME = "MessageID";
    public static final QName WSA_MESSAGEID_QNAME = new QName("http://www.w3.org/2005/08/addressing", "MessageID");
    public static final String WSA_REPLY_NAME = "reply";
    public static final String WSA_RELATIONSHIP_DELIMITER = "/";
    public static final String WSA_RELATIONSHIP_REPLY = "http://www.w3.org/2005/08/addressing/reply";
    public static final String WSA_RELATESTO_NAME = "RelatesTo";
    public static final QName WSA_RELATESTO_QNAME = new QName("http://www.w3.org/2005/08/addressing", "RelatesTo");
    public static final String WSA_RELATIONSHIPTYPE_NAME = "RelationshipType";
    public static final QName WSA_RELATIONSHIPTYPE_QNAME = new QName("http://www.w3.org/2005/08/addressing", "RelationshipType");
    public static final String WSA_ANONYMOUS_ADDRESS = "http://www.w3.org/2005/08/addressing/anonymous";
    public static final String WSA_NONE_ADDRESS = "http://www.w3.org/2005/08/addressing/none";
    public static final String WSA_UNSPECIFIED_RELATIONSHIP = "http://www.w3.org/2005/08/addressing/unspecified";
    public static final String WSA_FAULT_DELIMITER = "/fault";
    public static final String WSA_DEFAULT_FAULT_ACTION = "http://www.w3.org/2005/08/addressing/fault";
    public static final String WSA_DEFAULT_SOAP_FAULT_ACTION = "http://www.w3.org/2005/08/addressing/soap/fault";
    public static final String WSAW_ACTION_NAME = "Action";
    public static final QName WSAW_ACTION_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "Action");
    public static final String WSAW_USING_ADDRESSING_NAME = "UsingAddressing";
    public static final QName WSAW_USING_ADDRESSING_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "UsingAddressing");
    public static final String WSDL_INSTANCE_NAMESPACE_NAME = "http://www.w3.org/2004/08/wsdl-instance";
    public static final String INVALID_MAP_NAME = "InvalidMessageAddressingProperty";
    public static final QName INVALID_MAP_QNAME = new QName("http://www.w3.org/2005/08/addressing", "InvalidMessageAddressingProperty");
    public static final String MAP_REQUIRED_NAME = "MessageAddressingPropertyRequired";
    public static final QName MAP_REQUIRED_QNAME = new QName("http://www.w3.org/2005/08/addressing", "MessageAddressingPropertyRequired");
    public static final String DESTINATION_UNREACHABLE_NAME = "DestinationUnreachable";
    public static final QName DESTINATION_UNREACHABLE_QNAME = new QName("http://www.w3.org/2005/08/addressing", "DestinationUnreachable");
    public static final String ACTION_NOT_SUPPORTED_NAME = "ActionNotSupported";
    public static final QName ACTION_NOT_SUPPORTED_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ActionNotSupported");
    public static final String ENDPOINT_UNAVAILABLE_NAME = "EndpointUnavailable";
    public static final QName ENDPOINT_UNAVAILABLE_QNAME = new QName("http://www.w3.org/2005/08/addressing", "EndpointUnavailable");
    public static final String DUPLICATE_MESSAGE_ID_NAME = "DuplicateMessageID";
    public static final QName DUPLICATE_MESSAGE_ID_QNAME = new QName("http://www.w3.org/2005/08/addressing", "DuplicateMessageID");
    public static final String ACTION_MISMATCH_NAME = "ActionMismatch";
    public static final QName ACTION_MISMATCH_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ActionMismatch");
    public static final String HEADER_REQUIRED_NAME = "MessageAddressingHeaderRequired";
    public static final QName HEADER_REQUIRED_QNAME = new QName("http://www.w3.org/2005/08/addressing", "MessageAddressingHeaderRequired");
    public static final String ONLY_ANONYMOUS_ADDRESS_SUPPORTED_NAME = "OnlyAnonymousAddressSupported";
    public static final QName ONLY_ANONYMOUS_ADDRESS_SUPPORTED_QNAME = new QName("http://www.w3.org/2005/08/addressing", "OnlyAnonymousAddressSupported");
    public static final String ONLY_NONANONYMOUS_ADDRESS_SUPPORTED_NAME = "OnlyNonAnonymousAddressSupported";
    public static final QName ONLY_NONANONYMOUS_ADDRESS_SUPPORTED_QNAME = new QName("http://www.w3.org/2005/08/addressing", "OnlyNonAnonymousAddressSupported");
    public static final String INVALID_CARDINALITY_NAME = "InvalidCardinality";
    public static final QName INVALID_CARDINALITY_QNAME = new QName("http://www.w3.org/2005/08/addressing", "InvalidCardinality");
    public static final Set<QName> HEADERS;

    private Names() {
    }

    static {
        HashSet<QName> headers = new HashSet<QName>();
        headers.add(WSA_FROM_QNAME);
        headers.add(WSA_TO_QNAME);
        headers.add(WSA_REPLYTO_QNAME);
        headers.add(WSA_FAULTTO_QNAME);
        headers.add(WSA_ACTION_QNAME);
        headers.add(WSA_MESSAGEID_QNAME);
        headers.add(WSA_RELATESTO_QNAME);
        HEADERS = Collections.unmodifiableSet(headers);
    }
}

