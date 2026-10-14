/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;

public final class SerializeSupport {
    private static Map<String, Object> prettyPrintParams = new LazyMap<String, Object>();

    private SerializeSupport() {
    }

    @Nonnull
    public static String nodeToString(@Nonnull Node node) {
        return SerializeSupport.nodeToString(node, null);
    }

    @Nonnull
    public static String nodeToString(@Nonnull Node node, @Nullable Map<String, Object> serializerParams) {
        Constraint.isNotNull(node, "Node may not be null");
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        SerializeSupport.writeNode(node, baout, serializerParams);
        try {
            return new String(baout.toByteArray(), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public static String prettyPrintXML(@Nonnull Node node) {
        Constraint.isNotNull(node, "Node may not be null");
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        SerializeSupport.writeNode(node, baout, prettyPrintParams);
        try {
            return new String(baout.toByteArray(), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeNode(@Nonnull Node node, @Nonnull OutputStream output) {
        SerializeSupport.writeNode(node, output, null);
    }

    public static void writeNode(@Nonnull Node node, @Nonnull OutputStream output, @Nullable Map<String, Object> serializerParams) {
        Constraint.isNotNull(node, "Node may not be null");
        Constraint.isNotNull(output, "Outputstream may not be null");
        DOMImplementationLS domImplLS = SerializeSupport.getDOMImplementationLS(node);
        LSSerializer serializer = SerializeSupport.getLSSerializer(domImplLS, serializerParams);
        LSOutput serializerOut = domImplLS.createLSOutput();
        serializerOut.setByteStream(output);
        serializer.write(node, serializerOut);
    }

    @Nonnull
    public static LSSerializer getLSSerializer(@Nonnull DOMImplementationLS domImplLS, @Nullable Map<String, Object> serializerParams) {
        Constraint.isNotNull(domImplLS, "DOM implementation can not be null");
        LSSerializer serializer = domImplLS.createLSSerializer();
        serializer.setFilter(new LSSerializerFilter(){

            @Override
            public short acceptNode(Node arg0) {
                return 1;
            }

            @Override
            public int getWhatToShow() {
                return -1;
            }
        });
        if (serializerParams != null) {
            DOMConfiguration serializerDOMConfig = serializer.getDomConfig();
            for (String key : serializerParams.keySet()) {
                serializerDOMConfig.setParameter(key, serializerParams.get(key));
            }
        }
        return serializer;
    }

    @Nonnull
    public static DOMImplementationLS getDOMImplementationLS(@Nonnull Node node) {
        Constraint.isNotNull(node, "DOM node can not be null");
        DOMImplementation domImpl = node instanceof Document ? ((Document)node).getImplementation() : node.getOwnerDocument().getImplementation();
        return (DOMImplementationLS)domImpl.getFeature("LS", "3.0");
    }

    static {
        prettyPrintParams.put("format-pretty-print", Boolean.TRUE);
    }
}

