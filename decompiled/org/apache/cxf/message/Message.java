/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.util.Collection;
import java.util.Set;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.StringMap;
import org.apache.cxf.transport.Destination;

public interface Message
extends StringMap {
    public static final String TRANSPORT = "org.apache.cxf.transport";
    public static final String REST_MESSAGE = "org.apache.cxf.rest.message";
    public static final String REQUESTOR_ROLE = "org.apache.cxf.client";
    public static final String INBOUND_MESSAGE = "org.apache.cxf.message.inbound";
    public static final String INVOCATION_CONTEXT = "org.apache.cxf.invocation.context";
    public static final String SERVICE_OBJECT = "org.apache.cxf.service.object";
    public static final String MIME_HEADERS = "org.apache.cxf.mime.headers";
    public static final String ASYNC_POST_RESPONSE_DISPATCH = "org.apache.cxf.async.post.response.dispatch";
    public static final String DECOUPLED_CHANNEL_MESSAGE = "decoupled.channel.message";
    public static final String PARTIAL_RESPONSE_MESSAGE = "org.apache.cxf.partial.response";
    public static final String EMPTY_PARTIAL_RESPONSE_MESSAGE = "org.apache.cxf.partial.response.empty";
    public static final String ONE_WAY_REQUEST = "OnewayRequest";
    public static final String PROCESS_ONEWAY_RESPONSE = "org.apache.cxf.transport.processOneWayResponse";
    public static final String PROCESS_202_RESPONSE_ONEWAY_OR_PARTIAL = "org.apache.cxf.transport.process202Response";
    public static final String THREAD_CONTEXT_SWITCHED = "thread.context.switched";
    public static final String ROBUST_ONEWAY = "org.apache.cxf.oneway.robust";
    public static final String HTTP_REQUEST_METHOD = "org.apache.cxf.request.method";
    public static final String REQUEST_URI = "org.apache.cxf.request.uri";
    public static final String REQUEST_URL = "org.apache.cxf.request.url";
    public static final String PROTOCOL_HEADERS = Message.class.getName() + ".PROTOCOL_HEADERS";
    public static final String RESPONSE_CODE = Message.class.getName() + ".RESPONSE_CODE";
    public static final String ERROR_MESSAGE = Message.class.getName() + ".ERROR_MESSAGE";
    public static final String ENDPOINT_ADDRESS = Message.class.getName() + ".ENDPOINT_ADDRESS";
    public static final String PATH_INFO = Message.class.getName() + ".PATH_INFO";
    public static final String QUERY_STRING = Message.class.getName() + ".QUERY_STRING";
    public static final String PROPOGATE_EXCEPTION = Message.class.getName() + ".PROPOGATE_EXCEPTION";
    public static final String MTOM_ENABLED = "mtom-enabled";
    public static final String MTOM_THRESHOLD = "mtom-threshold";
    public static final String SCHEMA_VALIDATION_ENABLED = "schema-validation-enabled";
    public static final String SCHEMA_VALIDATION_TYPE = "schema-validation-type";
    public static final String FAULT_STACKTRACE_ENABLED = "faultStackTraceEnabled";
    public static final String EXCEPTION_MESSAGE_CAUSE_ENABLED = "exceptionMessageCauseEnabled";
    public static final String EXCEPTION_CAUSE_SUFFIX = "#*#";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT_CONTENT_TYPE = "Accept";
    public static final String BASE_PATH = Message.class.getName() + ".BASE_PATH";
    public static final String ENCODING = Message.class.getName() + ".ENCODING";
    public static final String FIXED_PARAMETER_ORDER = Message.class.getName() + ".FIXED_PARAMETER_ORDER";
    public static final String MAINTAIN_SESSION = Message.class.getName() + ".MAINTAIN_SESSION";
    public static final String ATTACHMENTS = Message.class.getName() + ".ATTACHMENTS";
    public static final String WSDL_DESCRIPTION = "javax.xml.ws.wsdl.description";
    public static final String WSDL_SERVICE = "javax.xml.ws.wsdl.service";
    public static final String WSDL_PORT = "javax.xml.ws.wsdl.port";
    public static final String WSDL_INTERFACE = "javax.xml.ws.wsdl.interface";
    public static final String WSDL_OPERATION = "javax.xml.ws.wsdl.operation";
    public static final String IN_INTERCEPTORS = Message.class.getName() + ".IN_INTERCEPTORS";
    public static final String OUT_INTERCEPTORS = Message.class.getName() + ".OUT_INTERCEPTORS";
    public static final String FAULT_IN_INTERCEPTORS = Message.class.getName() + ".FAULT_IN_INTERCEPTORS";
    public static final String FAULT_OUT_INTERCEPTORS = Message.class.getName() + ".FAULT_OUT_INTERCEPTORS";
    public static final String INTERCEPTOR_PROVIDERS = Message.class.getName() + ".INTERCEPTOR_PROVIDER";
    public static final String CONTENT_TRANSFER_ENCODING = Message.class.getName() + ".CONTENT_TRANSFER_ENCODING";
    public static final String CONNECTION_TIMEOUT = "javax.xml.ws.client.connectionTimeout";
    public static final String RECEIVE_TIMEOUT = "javax.xml.ws.client.receiveTimeout";
    public static final String THREAD_SAFE_STAX_FACTORIES = Message.class.getName() + ".THREAD_SAFE_STAX_FACTORIES";

    public String getId();

    public void setId(String var1);

    public InterceptorChain getInterceptorChain();

    public void setInterceptorChain(InterceptorChain var1);

    public Destination getDestination();

    public Exchange getExchange();

    public void setExchange(Exchange var1);

    public Collection<Attachment> getAttachments();

    public void setAttachments(Collection<Attachment> var1);

    public <T> T getContent(Class<T> var1);

    public <T> void setContent(Class<T> var1, Object var2);

    public Set<Class<?>> getContentFormats();

    public <T> void removeContent(Class<T> var1);

    public Object getContextualProperty(String var1);

    public void resetContextCache();

    public Set<String> getContextualPropertyKeys();
}

