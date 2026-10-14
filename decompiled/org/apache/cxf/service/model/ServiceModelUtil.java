/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaSequence;

public final class ServiceModelUtil {
    private ServiceModelUtil() {
    }

    public static Service getService(Exchange exchange) {
        return exchange.getService();
    }

    public static String getTargetNamespace(Exchange exchange) {
        return ServiceModelUtil.getService(exchange).getServiceInfos().get(0).getTargetNamespace();
    }

    public static BindingOperationInfo getOperation(Exchange exchange, String opName) {
        Endpoint ep = exchange.getEndpoint();
        if (ep == null) {
            return null;
        }
        BindingInfo service2 = ep.getEndpointInfo().getBinding();
        for (BindingOperationInfo b : service2.getOperations()) {
            if (!b.getName().getLocalPart().equals(opName)) continue;
            return b;
        }
        return null;
    }

    public static BindingOperationInfo getOperation(Exchange exchange, QName opName) {
        Endpoint ep = exchange.getEndpoint();
        if (ep == null) {
            return null;
        }
        BindingInfo service2 = ep.getEndpointInfo().getBinding();
        return service2.getOperation(opName);
    }

    public static BindingOperationInfo getOperationForWrapperElement(Exchange exchange, QName opName, boolean output) {
        Endpoint ep = exchange.getEndpoint();
        if (ep == null) {
            return null;
        }
        BindingInfo service2 = ep.getEndpointInfo().getBinding();
        Map wrapperMap = CastUtils.cast(service2.getProperty(output ? "ServiceModel.WRAPPER.MAP_OUT" : "ServiceModel.WRAPPER.MAP", Map.class));
        if (wrapperMap == null) {
            wrapperMap = new HashMap();
            for (BindingOperationInfo b : service2.getOperations()) {
                if (b.isUnwrappedCapable()) {
                    MessagePartInfo part = null;
                    if (output && b.getOutput() != null && !b.getOutput().getMessageParts().isEmpty()) {
                        part = b.getOutput().getMessageParts().get(0);
                    } else if (!output && !b.getInput().getMessageParts().isEmpty()) {
                        part = b.getInput().getMessageParts().get(0);
                    }
                    if (part == null) continue;
                    wrapperMap.put(part.getConcreteName(), b);
                    continue;
                }
                BindingMessageInfo info = output ? b.getOutput() : b.getInput();
                if (info == null || info.getMessageParts().size() != 1) continue;
                wrapperMap.put(info.getMessageParts().get(0).getConcreteName(), b);
            }
            service2.setProperty(output ? "ServiceModel.WRAPPER.MAP_OUT" : "ServiceModel.WRAPPER.MAP", wrapperMap);
        }
        return (BindingOperationInfo)wrapperMap.get(opName);
    }

    public static SchemaInfo getSchema(ServiceInfo serviceInfo, MessagePartInfo messagePartInfo) {
        String tns = messagePartInfo.isElement() ? messagePartInfo.getElementQName().getNamespaceURI() : messagePartInfo.getTypeQName().getNamespaceURI();
        for (SchemaInfo schema : serviceInfo.getSchemas()) {
            if (!tns.equals(schema.getNamespaceURI())) continue;
            return schema;
        }
        return null;
    }

    public static List<String> getOperationInputPartNames(OperationInfo operation) {
        ArrayList<String> names = new ArrayList<String>();
        List<MessagePartInfo> parts = operation.getInput().getMessageParts();
        if (parts == null || parts.isEmpty()) {
            return names;
        }
        for (MessagePartInfo part : parts) {
            XmlSchemaAnnotated schema = part.getXmlSchema();
            if (schema instanceof XmlSchemaElement && ((XmlSchemaElement)schema).getSchemaType() instanceof XmlSchemaComplexType) {
                XmlSchemaElement element = (XmlSchemaElement)schema;
                XmlSchemaComplexType cplxType = (XmlSchemaComplexType)element.getSchemaType();
                XmlSchemaSequence seq = (XmlSchemaSequence)cplxType.getParticle();
                if (seq == null || seq.getItems() == null) {
                    return names;
                }
                for (int i = 0; i < seq.getItems().size(); ++i) {
                    XmlSchemaElement elChild = (XmlSchemaElement)seq.getItems().get(i);
                    names.add(elChild.getName());
                }
                continue;
            }
            names.add(part.getConcreteName().getLocalPart());
        }
        return names;
    }

    public static EndpointInfo findBestEndpointInfo(QName qn, List<ServiceInfo> serviceInfos) {
        for (ServiceInfo serviceInfo : serviceInfos) {
            Collection<EndpointInfo> eps = serviceInfo.getEndpoints();
            for (EndpointInfo ep : eps) {
                if (!ep.getInterface().getName().equals(qn)) continue;
                return ep;
            }
        }
        EndpointInfo best = null;
        for (ServiceInfo serviceInfo : serviceInfos) {
            Collection<EndpointInfo> eps = serviceInfo.getEndpoints();
            for (EndpointInfo ep : eps) {
                if (best == null) {
                    best = ep;
                }
                if (!"http://schemas.xmlsoap.org/wsdl/soap/".equals(ep.getTransportId())) continue;
                return ep;
            }
        }
        return best;
    }

    public static QName getServiceQName(EndpointInfo ei) {
        InterfaceInfo ii = ei.getInterface();
        if (ii != null) {
            return ii.getName();
        }
        if (ei.getService() != null) {
            return ei.getService().getName();
        }
        return ei.getName();
    }
}

