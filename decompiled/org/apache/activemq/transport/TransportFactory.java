/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import org.apache.activemq.transport.MutexTransport;
import org.apache.activemq.transport.ResponseCorrelator;
import org.apache.activemq.transport.ThreadNameFilter;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.WriteTimeoutFilter;
import org.apache.activemq.util.FactoryFinder;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;
import org.apache.activemq.wireformat.WireFormatFactory;

public abstract class TransportFactory {
    private static final FactoryFinder TRANSPORT_FACTORY_FINDER = new FactoryFinder("META-INF/services/org/apache/activemq/transport/");
    private static final FactoryFinder WIREFORMAT_FACTORY_FINDER = new FactoryFinder("META-INF/services/org/apache/activemq/wireformat/");
    private static final ConcurrentMap<String, TransportFactory> TRANSPORT_FACTORYS = new ConcurrentHashMap<String, TransportFactory>();
    private static final String WRITE_TIMEOUT_FILTER = "soWriteTimeout";
    private static final String THREAD_NAME_FILTER = "threadName";

    public abstract TransportServer doBind(URI var1) throws IOException;

    public Transport doConnect(URI location, Executor ex) throws Exception {
        return this.doConnect(location);
    }

    public Transport doCompositeConnect(URI location, Executor ex) throws Exception {
        return this.doCompositeConnect(location);
    }

    public static Transport connect(URI location) throws Exception {
        TransportFactory tf = TransportFactory.findTransportFactory(location);
        return tf.doConnect(location);
    }

    public static Transport connect(URI location, Executor ex) throws Exception {
        TransportFactory tf = TransportFactory.findTransportFactory(location);
        return tf.doConnect(location, ex);
    }

    public static Transport compositeConnect(URI location) throws Exception {
        TransportFactory tf = TransportFactory.findTransportFactory(location);
        return tf.doCompositeConnect(location);
    }

    public static Transport compositeConnect(URI location, Executor ex) throws Exception {
        TransportFactory tf = TransportFactory.findTransportFactory(location);
        return tf.doCompositeConnect(location, ex);
    }

    public static TransportServer bind(URI location) throws IOException {
        TransportFactory tf = TransportFactory.findTransportFactory(location);
        return tf.doBind(location);
    }

    public Transport doConnect(URI location) throws Exception {
        try {
            HashMap<String, String> options = new HashMap<String, String>(URISupport.parseParameters(location));
            if (!options.containsKey("wireFormat.host")) {
                options.put("wireFormat.host", location.getHost());
            }
            WireFormat wf = this.createWireFormat(options);
            Transport transport = this.createTransport(location, wf);
            Transport rc = this.configure(transport, wf, options);
            IntrospectionSupport.extractProperties(options, "auto.");
            if (!options.isEmpty()) {
                throw new IllegalArgumentException("Invalid connect parameters: " + options);
            }
            return rc;
        }
        catch (URISyntaxException e) {
            throw IOExceptionSupport.create(e);
        }
    }

    public Transport doCompositeConnect(URI location) throws Exception {
        try {
            HashMap<String, String> options = new HashMap<String, String>(URISupport.parseParameters(location));
            WireFormat wf = this.createWireFormat(options);
            Transport transport = this.createTransport(location, wf);
            Transport rc = this.compositeConfigure(transport, wf, options);
            if (!options.isEmpty()) {
                throw new IllegalArgumentException("Invalid connect parameters: " + options);
            }
            return rc;
        }
        catch (URISyntaxException e) {
            throw IOExceptionSupport.create(e);
        }
    }

    public static void registerTransportFactory(String scheme2, TransportFactory tf) {
        TRANSPORT_FACTORYS.put(scheme2, tf);
    }

    protected Transport createTransport(URI location, WireFormat wf) throws MalformedURLException, UnknownHostException, IOException {
        throw new IOException("createTransport() method not implemented!");
    }

    public static TransportFactory findTransportFactory(URI location) throws IOException {
        String scheme2 = location.getScheme();
        if (scheme2 == null) {
            throw new IOException("Transport not scheme specified: [" + location + "]");
        }
        TransportFactory tf = (TransportFactory)TRANSPORT_FACTORYS.get(scheme2);
        if (tf == null) {
            try {
                tf = (TransportFactory)TRANSPORT_FACTORY_FINDER.newInstance(scheme2);
                TRANSPORT_FACTORYS.put(scheme2, tf);
            }
            catch (Throwable e) {
                throw IOExceptionSupport.create("Transport scheme NOT recognized: [" + scheme2 + "]", e);
            }
        }
        return tf;
    }

    protected WireFormat createWireFormat(Map<String, String> options) throws IOException {
        WireFormatFactory factory2 = this.createWireFormatFactory(options);
        WireFormat format = factory2.createWireFormat();
        return format;
    }

    protected WireFormatFactory createWireFormatFactory(Map<String, String> options) throws IOException {
        String wireFormat = options.remove("wireFormat");
        if (wireFormat == null) {
            wireFormat = this.getDefaultWireFormatType();
        }
        try {
            WireFormatFactory wff = (WireFormatFactory)WIREFORMAT_FACTORY_FINDER.newInstance(wireFormat);
            IntrospectionSupport.setProperties((Object)wff, options, "wireFormat.");
            return wff;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not create wire format factory for: " + wireFormat + ", reason: " + e, e);
        }
    }

    protected String getDefaultWireFormatType() {
        return "default";
    }

    public Transport configure(Transport transport, WireFormat wf, Map options) throws Exception {
        transport = this.compositeConfigure(transport, wf, options);
        transport = new MutexTransport(transport);
        transport = new ResponseCorrelator(transport);
        return transport;
    }

    public Transport serverConfigure(Transport transport, WireFormat format, HashMap options) throws Exception {
        if (options.containsKey(THREAD_NAME_FILTER)) {
            transport = new ThreadNameFilter(transport);
        }
        transport = this.compositeConfigure(transport, format, options);
        transport = new MutexTransport(transport);
        return transport;
    }

    public Transport compositeConfigure(Transport transport, WireFormat format, Map options) {
        if (options.containsKey(WRITE_TIMEOUT_FILTER)) {
            transport = new WriteTimeoutFilter(transport);
            String soWriteTimeout = (String)options.remove(WRITE_TIMEOUT_FILTER);
            if (soWriteTimeout != null) {
                ((WriteTimeoutFilter)transport).setWriteTimeout(Long.parseLong(soWriteTimeout));
            }
        }
        IntrospectionSupport.setProperties(transport, options);
        return transport;
    }

    protected String getOption(Map options, String key, String def) {
        String rc = (String)options.remove(key);
        if (rc == null) {
            rc = def;
        }
        return rc;
    }
}

