/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ClasspathResolver
implements EntityResolver,
LSResourceResolver {
    public static final String CLASSPATH_URI_SCHEME = "classpath:";
    private final Logger log = LoggerFactory.getLogger(ClasspathResolver.class);

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        InputStream resourceStream = this.resolver(publicId, systemId);
        if (resourceStream != null) {
            InputSource is = new InputSource(resourceStream);
            is.setSystemId(systemId);
            is.setPublicId(publicId);
            return is;
        }
        return null;
    }

    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        return new LSInputImpl(publicId, systemId, this.resolver(publicId, systemId));
    }

    protected InputStream resolver(String publicId, String systemId) {
        String resource = null;
        InputStream resourceIns = null;
        if (systemId.startsWith(CLASSPATH_URI_SCHEME)) {
            this.log.trace("Attempting to resolve, within the classpath, the entity with the following system id: {}", (Object)systemId);
            resource = systemId.replaceFirst(CLASSPATH_URI_SCHEME, "");
            resourceIns = this.getClass().getResourceAsStream(resource);
        }
        if (resourceIns == null && publicId != null && publicId.startsWith(CLASSPATH_URI_SCHEME)) {
            this.log.trace("Attempting to resolve, within the classpath, the entity with the following public id: {}", (Object)resource);
            resource = publicId.replaceFirst(CLASSPATH_URI_SCHEME, "");
            resourceIns = this.getClass().getResourceAsStream(resource);
        }
        if (resourceIns == null) {
            this.log.trace("Entity was not resolved from classpath, public id ({}), system id({})", (Object)publicId, (Object)systemId);
            return null;
        }
        this.log.trace("Entity resolved from classpath");
        return resourceIns;
    }

    protected class LSInputImpl
    implements LSInput {
        private String publicId;
        private String systemId;
        private BufferedInputStream buffInput;

        public LSInputImpl(String pubId, String sysId, InputStream input) {
            this.publicId = pubId;
            this.systemId = sysId;
            this.buffInput = new BufferedInputStream(input);
        }

        @Override
        public String getBaseURI() {
            return null;
        }

        @Override
        public InputStream getByteStream() {
            return this.buffInput;
        }

        @Override
        public boolean getCertifiedText() {
            return false;
        }

        @Override
        public Reader getCharacterStream() {
            return new InputStreamReader(this.buffInput);
        }

        @Override
        public String getEncoding() {
            return null;
        }

        @Override
        public String getPublicId() {
            return this.publicId;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String getStringData() {
            BufferedInputStream bufferedInputStream = this.buffInput;
            synchronized (bufferedInputStream) {
                try {
                    this.buffInput.reset();
                    byte[] input = new byte[this.buffInput.available()];
                    this.buffInput.read(input);
                    return new String(input);
                }
                catch (IOException e) {
                    return null;
                }
            }
        }

        @Override
        public String getSystemId() {
            return this.systemId;
        }

        @Override
        public void setBaseURI(String uri) {
        }

        @Override
        public void setByteStream(InputStream byteStream) {
        }

        @Override
        public void setCertifiedText(boolean isCertifiedText) {
        }

        @Override
        public void setCharacterStream(Reader characterStream) {
        }

        @Override
        public void setEncoding(String encoding) {
        }

        @Override
        public void setPublicId(String id) {
        }

        @Override
        public void setStringData(String stringData) {
        }

        @Override
        public void setSystemId(String id) {
        }
    }
}

