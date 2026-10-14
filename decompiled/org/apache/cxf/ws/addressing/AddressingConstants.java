/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.ws.addressing.Names;

public class AddressingConstants {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AddressingConstants.class);

    public String getNamespaceURI() {
        return "http://www.w3.org/2005/08/addressing";
    }

    public String getWSDLNamespaceURI() {
        return "http://www.w3.org/2006/05/addressing/wsdl";
    }

    public QName getWSDLExtensibilityQName() {
        return Names.WSAW_USING_ADDRESSING_QNAME;
    }

    public QName getWSDLActionQName() {
        return Names.WSAW_ACTION_QNAME;
    }

    public String getAnonymousURI() {
        return "http://www.w3.org/2005/08/addressing/anonymous";
    }

    public String getNoneURI() {
        return "http://www.w3.org/2005/08/addressing/none";
    }

    public QName getFromQName() {
        return Names.WSA_FROM_QNAME;
    }

    public QName getToQName() {
        return Names.WSA_TO_QNAME;
    }

    public QName getReplyToQName() {
        return Names.WSA_REPLYTO_QNAME;
    }

    public QName getFaultToQName() {
        return Names.WSA_FAULTTO_QNAME;
    }

    public QName getActionQName() {
        return Names.WSA_ACTION_QNAME;
    }

    public QName getMessageIDQName() {
        return Names.WSA_MESSAGEID_QNAME;
    }

    public String getRelationshipReply() {
        return "http://www.w3.org/2005/08/addressing/reply";
    }

    public QName getRelatesToQName() {
        return Names.WSA_RELATESTO_QNAME;
    }

    public QName getRelationshipTypeQName() {
        return Names.WSA_RELATIONSHIPTYPE_QNAME;
    }

    public QName getMetadataQName() {
        return Names.WSA_METADATA_QNAME;
    }

    public QName getAddressQName() {
        return Names.WSA_ADDRESS_QNAME;
    }

    public QName getIsReferenceParameterQName() {
        return Names.WSA_IS_REFERENCE_PARAMETER_QNAME;
    }

    public QName getInvalidMapQName() {
        return Names.INVALID_MAP_QNAME;
    }

    public QName getMapRequiredQName() {
        return Names.MAP_REQUIRED_QNAME;
    }

    public QName getDestinationUnreachableQName() {
        return Names.DESTINATION_UNREACHABLE_QNAME;
    }

    public QName getActionNotSupportedQName() {
        return Names.ACTION_NOT_SUPPORTED_QNAME;
    }

    public QName getEndpointUnavailableQName() {
        return Names.ENDPOINT_UNAVAILABLE_QNAME;
    }

    public String getDefaultFaultAction() {
        return "http://www.w3.org/2005/08/addressing/fault";
    }

    public String getActionNotSupportedText() {
        return BUNDLE.getString("ACTION_NOT_SUPPORTED_MSG");
    }

    public String getDestinationUnreachableText() {
        return BUNDLE.getString("DESTINATION_UNREACHABLE_MSG");
    }

    public String getEndpointUnavailableText() {
        return BUNDLE.getString("ENDPOINT_UNAVAILABLE_MSG");
    }

    public String getInvalidMapText() {
        return BUNDLE.getString("INVALID_MAP_MSG");
    }

    public String getMapRequiredText() {
        return BUNDLE.getString("MAP_REQUIRED_MSG");
    }

    public String getDuplicateMessageIDText() {
        return BUNDLE.getString("DUPLICATE_MESSAGE_ID_MSG");
    }
}

