/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.transform.TransformerException;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaSerializer;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

class XercesSchemaValidationUtils {
    private DOMImplementation impl;

    XercesSchemaValidationUtils() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        this.impl = registry.getDOMImplementation("XS-Loader");
    }

    Method findMethod(Object o, String name) {
        for (Method m4 : o.getClass().getMethods()) {
            if (m4.getName() == null || !m4.getName().equals(name)) continue;
            m4.setAccessible(true);
            return m4;
        }
        return null;
    }

    void tryToParseSchemas(XmlSchemaCollection collection, DOMErrorHandler handler) throws Exception {
        ArrayList<DOMLSInput> inputs = new ArrayList<DOMLSInput>();
        final HashMap<String, DOMLSInput> resolverMap = new HashMap<String, DOMLSInput>();
        for (XmlSchema schema : collection.getXmlSchemas()) {
            if ("http://www.w3.org/2001/XMLSchema".equals(schema.getTargetNamespace())) continue;
            Document document = new XmlSchemaSerializer().serializeSchema(schema, false)[0];
            DOMLSInput input = new DOMLSInput(document, schema.getTargetNamespace());
            resolverMap.put(schema.getTargetNamespace(), input);
            inputs.add(input);
        }
        try {
            Object schemaLoader = this.findMethod(this.impl, "createXSLoader").invoke((Object)this.impl, new Object[1]);
            DOMConfiguration config = (DOMConfiguration)this.findMethod(schemaLoader, "getConfig").invoke(schemaLoader, new Object[0]);
            config.setParameter("validate", Boolean.TRUE);
            config.setParameter("error-handler", handler);
            config.setParameter("resource-resolver", new LSResourceResolver(){

                @Override
                public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
                    return (LSInput)resolverMap.get(namespaceURI);
                }
            });
            Method m4 = this.findMethod(schemaLoader, "loadInputList");
            String name = m4.getParameterTypes()[0].getName() + "Impl";
            name = name.replace("xs.LS", "impl.xs.util.LS");
            Class<?> c = Class.forName(name);
            Object inputList = c.getConstructor(LSInput[].class, Integer.TYPE).newInstance(inputs.toArray(new LSInput[0]), inputs.size());
            m4.invoke(schemaLoader, inputList);
        }
        catch (InvocationTargetException e) {
            throw (Exception)e.getTargetException();
        }
    }

    static class DOMLSInput
    implements LSInput {
        private String systemId;
        private String data;

        DOMLSInput(Document doc, String systemId) throws TransformerException {
            this.systemId = systemId;
            this.data = StaxUtils.toString(doc);
        }

        @Override
        public String getBaseURI() {
            return null;
        }

        @Override
        public InputStream getByteStream() {
            return null;
        }

        @Override
        public boolean getCertifiedText() {
            return false;
        }

        @Override
        public Reader getCharacterStream() {
            return null;
        }

        @Override
        public String getEncoding() {
            return "utf-8";
        }

        @Override
        public String getPublicId() {
            return null;
        }

        @Override
        public String getStringData() {
            return this.data;
        }

        @Override
        public String getSystemId() {
            return this.systemId;
        }

        @Override
        public void setBaseURI(String baseURI) {
        }

        @Override
        public void setByteStream(InputStream byteStream) {
        }

        @Override
        public void setCertifiedText(boolean certifiedText) {
        }

        @Override
        public void setCharacterStream(Reader characterStream) {
        }

        @Override
        public void setEncoding(String encoding) {
        }

        @Override
        public void setPublicId(String publicId) {
        }

        @Override
        public void setStringData(String stringData) {
        }

        @Override
        public void setSystemId(String systemId) {
        }
    }
}

