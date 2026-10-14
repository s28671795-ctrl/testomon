/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.xmlschema;

import java.io.InputStream;
import java.io.Reader;
import org.w3c.dom.ls.LSInput;

public class LSInputImpl
implements LSInput {
    protected String fPublicId;
    protected String fSystemId;
    protected String fBaseSystemId;
    protected InputStream fByteStream;
    protected Reader fCharStream;
    protected String fData;
    protected String fEncoding;
    protected boolean fCertifiedText;

    public LSInputImpl() {
    }

    public LSInputImpl(String publicId, String systemId, InputStream byteStream) {
        this.fPublicId = publicId;
        this.fSystemId = systemId;
        this.fByteStream = byteStream;
    }

    @Override
    public InputStream getByteStream() {
        return this.fByteStream;
    }

    @Override
    public void setByteStream(InputStream byteStream) {
        this.fByteStream = byteStream;
    }

    @Override
    public Reader getCharacterStream() {
        return this.fCharStream;
    }

    @Override
    public void setCharacterStream(Reader characterStream) {
        this.fCharStream = characterStream;
    }

    @Override
    public String getStringData() {
        return this.fData;
    }

    @Override
    public void setStringData(String stringData) {
        this.fData = stringData;
    }

    @Override
    public String getEncoding() {
        return this.fEncoding;
    }

    @Override
    public void setEncoding(String encoding) {
        this.fEncoding = encoding;
    }

    @Override
    public String getPublicId() {
        return this.fPublicId;
    }

    @Override
    public void setPublicId(String publicId) {
        this.fPublicId = publicId;
    }

    @Override
    public String getSystemId() {
        return this.fSystemId;
    }

    @Override
    public void setSystemId(String systemId) {
        this.fSystemId = systemId;
    }

    @Override
    public String getBaseURI() {
        return this.fBaseSystemId;
    }

    @Override
    public void setBaseURI(String baseURI) {
        this.fBaseSystemId = baseURI;
    }

    @Override
    public boolean getCertifiedText() {
        return this.fCertifiedText;
    }

    @Override
    public void setCertifiedText(boolean certifiedText) {
        this.fCertifiedText = certifiedText;
    }
}

