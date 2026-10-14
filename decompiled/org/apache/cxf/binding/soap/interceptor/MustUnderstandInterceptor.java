/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.HeaderUtil;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.OneWayProcessorInterceptor;

public class MustUnderstandInterceptor
extends AbstractSoapInterceptor {
    public static final String UNKNOWNS = "MustUnderstand.UNKNOWNS";
    private static final Logger LOG = LogUtils.getL7dLogger(MustUnderstandInterceptor.class);
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private MustUnderstandEndingInterceptor ending = new MustUnderstandEndingInterceptor();

    public MustUnderstandInterceptor() {
        super("pre-protocol");
    }

    public MustUnderstandInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) {
        Set<QName> paramHeaders = HeaderUtil.getHeaderQNameInOperationParam(soapMessage);
        if (soapMessage.getHeaders().isEmpty() && paramHeaders.isEmpty()) {
            return;
        }
        SoapVersion soapVersion = soapMessage.getVersion();
        HashSet<Header> mustUnderstandHeaders = new HashSet<Header>();
        HashSet<URI> serviceRoles = new HashSet<URI>();
        HashSet<QName> notUnderstandHeaders = new HashSet<QName>();
        HashSet<Header> ultimateReceiverHeaders = new HashSet<Header>();
        HashSet<QName> mustUnderstandQNames = new HashSet<QName>();
        this.initServiceSideInfo(mustUnderstandQNames, soapMessage, serviceRoles, paramHeaders);
        this.buildMustUnderstandHeaders(mustUnderstandHeaders, soapMessage, serviceRoles, ultimateReceiverHeaders);
        this.checkUnderstand(mustUnderstandHeaders, mustUnderstandQNames, notUnderstandHeaders);
        if (!notUnderstandHeaders.isEmpty()) {
            if (!this.isRequestor(soapMessage)) {
                soapMessage.put(UNKNOWNS, (Object)notUnderstandHeaders);
                soapMessage.getInterceptorChain().add(this.ending);
            } else {
                throw new SoapFault(new Message("MUST_UNDERSTAND", BUNDLE, notUnderstandHeaders), soapVersion.getMustUnderstand());
            }
        }
        if (!ultimateReceiverHeaders.isEmpty() && !this.isRequestor(soapMessage)) {
            this.checkUltimateReceiverHeaders(ultimateReceiverHeaders, mustUnderstandQNames, soapMessage);
        }
    }

    @Override
    public void handleFault(SoapMessage msg) {
        Set unknowns = CastUtils.cast((Set)msg.get(UNKNOWNS));
        if (msg.getExchange().getBindingOperationInfo() == null && unknowns != null && !unknowns.isEmpty()) {
            msg.setContent(Exception.class, new SoapFault(new Message("MUST_UNDERSTAND", BUNDLE, unknowns), msg.getVersion().getMustUnderstand()));
        }
    }

    private void checkUltimateReceiverHeaders(Set<Header> ultimateReceiverHeaders, Set<QName> mustUnderstandQNames, SoapMessage soapMessage) {
        soapMessage.getInterceptorChain().add(new UltimateReceiverMustUnderstandInterceptor(mustUnderstandQNames));
        Object o = soapMessage.getContextualProperty("endpoint-processes-headers");
        if (o == null) {
            o = Collections.EMPTY_LIST;
        }
        Collection<Object> o2 = o instanceof Collection ? CastUtils.cast((Collection)o) : Collections.singleton(o);
        for (Object obj : o2) {
            QName qn = obj instanceof QName ? (QName)obj : QName.valueOf((String)obj);
            Iterator<Header> hit = ultimateReceiverHeaders.iterator();
            while (hit.hasNext()) {
                Header h2 = hit.next();
                if (!qn.equals(h2.getName())) continue;
                hit.remove();
            }
        }
        if (!ultimateReceiverHeaders.isEmpty()) {
            HashSet<QName> notFound = new HashSet<QName>();
            for (Header h3 : ultimateReceiverHeaders) {
                if (mustUnderstandQNames.contains(h3.getName())) continue;
                notFound.add(h3.getName());
            }
            if (!notFound.isEmpty()) {
                soapMessage.put(UNKNOWNS, (Object)notFound);
                soapMessage.getInterceptorChain().add(this.ending);
            }
        }
    }

    private void initServiceSideInfo(Set<QName> mustUnderstandQNames, SoapMessage soapMessage, Set<URI> serviceRoles, Set<QName> paramHeaders) {
        if (paramHeaders != null) {
            mustUnderstandQNames.addAll(paramHeaders);
        }
        for (Interceptor interceptorInstance : soapMessage.getInterceptorChain()) {
            Set<QName> understoodHeaders;
            if (!(interceptorInstance instanceof SoapInterceptor)) continue;
            SoapInterceptor si = (SoapInterceptor)interceptorInstance;
            Set<URI> roles = si.getRoles();
            if (roles != null) {
                serviceRoles.addAll(roles);
            }
            if ((understoodHeaders = si.getUnderstoodHeaders()) == null) continue;
            mustUnderstandQNames.addAll(understoodHeaders);
        }
    }

    private void buildMustUnderstandHeaders(Set<Header> mustUnderstandHeaders, SoapMessage soapMessage, Set<URI> serviceRoles, Set<Header> ultimateReceiverHeaders) {
        for (Header header : soapMessage.getHeaders()) {
            if (!(header instanceof SoapHeader) || !((SoapHeader)header).isMustUnderstand()) continue;
            String role = ((SoapHeader)header).getActor();
            if (!StringUtils.isEmpty(role)) {
                if ((role = role.trim()).equals(soapMessage.getVersion().getNextRole())) {
                    mustUnderstandHeaders.add(header);
                    continue;
                }
                if (role.equals(soapMessage.getVersion().getUltimateReceiverRole())) {
                    ultimateReceiverHeaders.add(header);
                    continue;
                }
                for (URI roleFromBinding : serviceRoles) {
                    if (!role.equals(roleFromBinding.toString())) continue;
                    mustUnderstandHeaders.add(header);
                }
                continue;
            }
            ultimateReceiverHeaders.add(header);
        }
    }

    private void checkUnderstand(Set<Header> mustUnderstandHeaders, Set<QName> mustUnderstandQNames, Set<QName> notUnderstandHeaders) {
        for (Header header : mustUnderstandHeaders) {
            QName qname = header.getName();
            if (mustUnderstandQNames.contains(qname)) continue;
            notUnderstandHeaders.add(header.getName());
        }
    }

    public static class MustUnderstandEndingInterceptor
    extends AbstractSoapInterceptor {
        public MustUnderstandEndingInterceptor() {
            super("pre-logical");
            this.addAfter(OneWayProcessorInterceptor.class.getName());
        }

        public MustUnderstandEndingInterceptor(String phase) {
            super(phase);
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            if (message.get(MustUnderstandInterceptor.UNKNOWNS) != null) {
                Set unknowns = CastUtils.cast((Set)message.get(MustUnderstandInterceptor.UNKNOWNS));
                Set<QName> paramHeaders = HeaderUtil.getHeaderQNameInOperationParam(message);
                unknowns.removeAll(paramHeaders);
                message.remove(MustUnderstandInterceptor.UNKNOWNS);
                if (!unknowns.isEmpty()) {
                    throw new SoapFault(new Message("MUST_UNDERSTAND", BUNDLE, unknowns), message.getVersion().getMustUnderstand());
                }
            }
        }
    }

    private static class UltimateReceiverMustUnderstandInterceptor
    extends AbstractSoapInterceptor {
        Set<QName> knownHeaders;

        UltimateReceiverMustUnderstandInterceptor(Set<QName> knownHeaders) {
            super("invoke");
            this.knownHeaders = knownHeaders;
        }

        @Override
        public void handleMessage(SoapMessage soapMessage) throws Fault {
            SoapVersion soapVersion = soapMessage.getVersion();
            HashSet<QName> notFound = new HashSet<QName>();
            List<Header> heads = soapMessage.getHeaders();
            for (Header header : heads) {
                if (!(header instanceof SoapHeader) || !((SoapHeader)header).isMustUnderstand() || header.getDirection() != Header.Direction.DIRECTION_IN || this.knownHeaders.contains(header.getName()) || !StringUtils.isEmpty(((SoapHeader)header).getActor()) && !soapVersion.getUltimateReceiverRole().equals(((SoapHeader)header).getActor())) continue;
                notFound.add(header.getName());
            }
            if (!notFound.isEmpty()) {
                soapMessage.remove(MustUnderstandInterceptor.UNKNOWNS);
                throw new SoapFault(new Message("MUST_UNDERSTAND", BUNDLE, notFound), soapVersion.getMustUnderstand());
            }
        }
    }
}

