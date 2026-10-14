/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.security.sts.provider.STSException;
import org.apache.cxf.ws.security.sts.provider.model.ObjectFactory;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenType;
import org.apache.cxf.ws.security.sts.provider.operation.CancelOperation;
import org.apache.cxf.ws.security.sts.provider.operation.IssueOperation;
import org.apache.cxf.ws.security.sts.provider.operation.IssueSingleOperation;
import org.apache.cxf.ws.security.sts.provider.operation.KeyExchangeTokenOperation;
import org.apache.cxf.ws.security.sts.provider.operation.RenewOperation;
import org.apache.cxf.ws.security.sts.provider.operation.RequestCollectionOperation;
import org.apache.cxf.ws.security.sts.provider.operation.ValidateOperation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@ServiceMode(value=Service.Mode.PAYLOAD)
public class SecurityTokenServiceProvider
implements Provider<Source> {
    private static final String WSTRUST_13_NAMESPACE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
    private static final String WSTRUST_REQUESTTYPE_ELEMENTNAME = "RequestType";
    private static final String WSTRUST_REQUESTTYPE_ISSUE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue";
    private static final String WSTRUST_REQUESTTYPE_CANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Cancel";
    private static final String WSTRUST_REQUESTTYPE_RENEW = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Renew";
    private static final String WSTRUST_REQUESTTYPE_VALIDATE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Validate";
    private static final String WSTRUST_REQUESTTYPE_REQUESTCOLLECTION = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RequestCollection";
    private static final String WSTRUST_REQUESTTYPE_KEYEXCHANGETOKEN = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/KeyExchangeToken";
    private static final Map<String, Method> OPERATION_METHODS = new HashMap<String, Method>();
    protected JAXBContext jaxbContext;
    protected Set<Class<?>> jaxbContextClasses;
    private CancelOperation cancelOperation;
    private IssueOperation issueOperation;
    private IssueSingleOperation issueSingleOperation;
    private KeyExchangeTokenOperation keyExchangeTokenOperation;
    private RenewOperation renewOperation;
    private RequestCollectionOperation requestCollectionOperation;
    private ValidateOperation validateOperation;
    private Map<String, Object> operationMap = new HashMap<String, Object>();
    @Resource
    private WebServiceContext context;

    public SecurityTokenServiceProvider() throws Exception {
        HashSet classes = new HashSet();
        classes.add(ObjectFactory.class);
        classes.add(org.apache.cxf.ws.security.sts.provider.model.wstrust14.ObjectFactory.class);
        JAXBContextCache.CachedContextAndSchemas cache = JAXBContextCache.getCachedContextAndSchemas(classes, null, null, null, false);
        this.jaxbContext = cache.getContext();
        this.jaxbContextClasses = cache.getClasses();
    }

    public void setCancelOperation(CancelOperation cancelOperation) {
        this.cancelOperation = cancelOperation;
        this.operationMap.put(WSTRUST_REQUESTTYPE_CANCEL, cancelOperation);
    }

    public void setIssueOperation(IssueOperation issueOperation) {
        this.issueOperation = issueOperation;
        this.operationMap.put(WSTRUST_REQUESTTYPE_ISSUE, issueOperation);
    }

    public void setIssueSingleOperation(IssueSingleOperation issueSingleOperation) {
        this.issueSingleOperation = issueSingleOperation;
        try {
            Method m4 = IssueSingleOperation.class.getDeclaredMethod("issueSingle", RequestSecurityTokenType.class, Principal.class, Map.class);
            OPERATION_METHODS.put(WSTRUST_REQUESTTYPE_ISSUE, m4);
            this.operationMap.put(WSTRUST_REQUESTTYPE_ISSUE, issueSingleOperation);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setKeyExchangeTokenOperation(KeyExchangeTokenOperation keyExchangeTokenOperation) {
        this.keyExchangeTokenOperation = keyExchangeTokenOperation;
        this.operationMap.put(WSTRUST_REQUESTTYPE_KEYEXCHANGETOKEN, keyExchangeTokenOperation);
    }

    public void setRenewOperation(RenewOperation renewOperation) {
        this.renewOperation = renewOperation;
        this.operationMap.put(WSTRUST_REQUESTTYPE_RENEW, renewOperation);
    }

    public void setRequestCollectionOperation(RequestCollectionOperation requestCollectionOperation) {
        this.requestCollectionOperation = requestCollectionOperation;
        this.operationMap.put(WSTRUST_REQUESTTYPE_REQUESTCOLLECTION, requestCollectionOperation);
    }

    public void setValidateOperation(ValidateOperation validateOperation) {
        this.validateOperation = validateOperation;
        this.operationMap.put(WSTRUST_REQUESTTYPE_VALIDATE, validateOperation);
    }

    @Override
    public Source invoke(Source request) {
        JAXBSource response = null;
        try {
            Object tokenResponse;
            Object obj = this.convertToJAXBObject(request);
            Object operationImpl = null;
            Method method2 = null;
            if (obj instanceof RequestSecurityTokenCollectionType) {
                operationImpl = this.operationMap.get(WSTRUST_REQUESTTYPE_REQUESTCOLLECTION);
                method2 = OPERATION_METHODS.get(WSTRUST_REQUESTTYPE_REQUESTCOLLECTION);
            } else {
                RequestSecurityTokenType rst = (RequestSecurityTokenType)obj;
                List<Object> objectList = rst.getAny();
                for (Object o : objectList) {
                    QName qname;
                    if (!(o instanceof JAXBElement) || !(qname = ((JAXBElement)o).getName()).equals(new QName(WSTRUST_13_NAMESPACE, WSTRUST_REQUESTTYPE_ELEMENTNAME))) continue;
                    String val = ((JAXBElement)o).getValue().toString();
                    operationImpl = this.operationMap.get(val);
                    method2 = OPERATION_METHODS.get(val);
                    break;
                }
            }
            if (operationImpl == null || method2 == null) {
                throw new Exception("Implementation for this operation not found.");
            }
            if ((obj = method2.invoke(operationImpl, obj, this.context.getUserPrincipal(), this.context.getMessageContext())) == null) {
                throw new Exception("Error in implementation class.");
            }
            if (obj instanceof RequestSecurityTokenResponseCollectionType) {
                tokenResponse = (RequestSecurityTokenResponseCollectionType)obj;
                response = new JAXBSource(this.jaxbContext, new ObjectFactory().createRequestSecurityTokenResponseCollection((RequestSecurityTokenResponseCollectionType)tokenResponse));
            } else {
                tokenResponse = (RequestSecurityTokenResponseType)obj;
                response = new JAXBSource(this.jaxbContext, new ObjectFactory().createRequestSecurityTokenResponse((RequestSecurityTokenResponseType)tokenResponse));
            }
        }
        catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            throw this.createSOAPFault(cause);
        }
        catch (Exception ex) {
            throw this.createSOAPFault(ex);
        }
        return response;
    }

    private SoapFault createSOAPFault(Throwable ex) {
        SoapFault fault;
        MessageContext messageContext;
        SoapVersion soapVersion;
        String faultString = "Internal STS error";
        QName faultCode = null;
        if (ex != null) {
            if (ex instanceof STSException && ((STSException)ex).getFaultCode() != null) {
                faultCode = ((STSException)ex).getFaultCode();
            }
            faultString = ex.getMessage();
        }
        if ((soapVersion = (SoapVersion)(messageContext = this.context.getMessageContext()).get(SoapVersion.class.getName())).getVersion() == 1.1 && faultCode != null) {
            fault = new SoapFault(faultString, faultCode);
        } else {
            fault = new SoapFault(faultString, soapVersion.getSender());
            if (soapVersion.getVersion() != 1.1 && faultCode != null) {
                fault.setSubCode(faultCode);
            }
        }
        return fault;
    }

    private Object convertToJAXBObject(Source source2) throws Exception {
        Document d = StaxUtils.read(source2);
        Binder<Node> binder = this.jaxbContext.createBinder();
        JAXBElement jaxbElement = (JAXBElement)binder.unmarshal(d);
        this.walkDom("", d.getDocumentElement(), binder, null);
        return jaxbElement.getValue();
    }

    private void walkDom(String pfx, Element element, Binder<Node> binder, Object parent) {
        try {
            Object old;
            Field f;
            Object o = binder.getJAXBNode(element);
            if (o instanceof JAXBElement) {
                o = ((JAXBElement)o).getValue();
            }
            if (o == null && parent != null && (f = parent.getClass().getDeclaredField("any")).getAnnotation(XmlAnyElement.class) != null && (old = ReflectionUtil.setAccessible(f).get(parent)) instanceof Element && DOMUtils.getElementQName(element).equals(DOMUtils.getElementQName((Element)old))) {
                ReflectionUtil.setAccessible(f).set(parent, element);
            }
            if (o == null) {
                return;
            }
            for (Node nd = element.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
                if (!(nd instanceof Element)) continue;
                this.walkDom(pfx + "  ", (Element)nd, binder, o);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public CancelOperation getCancelOperation() {
        return this.cancelOperation;
    }

    public IssueOperation getIssueOperation() {
        return this.issueOperation;
    }

    public IssueSingleOperation getIssueSingleOperation() {
        return this.issueSingleOperation;
    }

    public KeyExchangeTokenOperation getKeyExchangeTokenOperation() {
        return this.keyExchangeTokenOperation;
    }

    public RenewOperation getRenewOperation() {
        return this.renewOperation;
    }

    public RequestCollectionOperation getRequestCollectionOperation() {
        return this.requestCollectionOperation;
    }

    public ValidateOperation getValidateOperation() {
        return this.validateOperation;
    }

    static {
        try {
            Method m4 = IssueOperation.class.getDeclaredMethod("issue", RequestSecurityTokenType.class, Principal.class, Map.class);
            OPERATION_METHODS.put(WSTRUST_REQUESTTYPE_ISSUE, m4);
            m4 = CancelOperation.class.getDeclaredMethod("cancel", RequestSecurityTokenType.class, Principal.class, Map.class);
            OPERATION_METHODS.put(WSTRUST_REQUESTTYPE_CANCEL, m4);
            m4 = RenewOperation.class.getDeclaredMethod("renew", RequestSecurityTokenType.class, Principal.class, Map.class);
            OPERATION_METHODS.put(WSTRUST_REQUESTTYPE_RENEW, m4);
            m4 = ValidateOperation.class.getDeclaredMethod("validate", RequestSecurityTokenType.class, Principal.class, Map.class);
            OPERATION_METHODS.put(WSTRUST_REQUESTTYPE_VALIDATE, m4);
            m4 = KeyExchangeTokenOperation.class.getDeclaredMethod("keyExchangeToken", RequestSecurityTokenType.class, Principal.class, Map.class);
            OPERATION_METHODS.put(WSTRUST_REQUESTTYPE_KEYEXCHANGETOKEN, m4);
            m4 = RequestCollectionOperation.class.getDeclaredMethod("requestCollection", RequestSecurityTokenCollectionType.class, Principal.class, Map.class);
            OPERATION_METHODS.put(WSTRUST_REQUESTTYPE_REQUESTCOLLECTION, m4);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

