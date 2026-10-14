/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.msv.reader.GrammarReaderController2
 *  com.sun.msv.reader.xmlschema.DOMLSInputImpl
 */
package org.apache.cxf.staxutils.validation;

import com.sun.msv.reader.GrammarReaderController2;
import com.sun.msv.reader.xmlschema.DOMLSInputImpl;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.staxutils.validation.EmbeddedSchema;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ResolvingGrammarReaderController
implements GrammarReaderController2,
LSResourceResolver {
    private static final Logger LOG = LogUtils.getL7dLogger(ResolvingGrammarReaderController.class);
    private Map<String, EmbeddedSchema> sources;
    private String baseURI;

    public ResolvingGrammarReaderController(String baseURI, Map<String, EmbeddedSchema> sources) {
        this.baseURI = baseURI;
        this.sources = sources;
    }

    public void error(Locator[] locs, String msg, Exception nestedException) {
        LOG.log(Level.SEVERE, msg, nestedException);
        for (Locator loc : locs) {
            LOG.severe("in " + loc.getSystemId() + " " + loc.getLineNumber() + ":" + loc.getColumnNumber());
        }
    }

    public void warning(Locator[] locs, String errorMessage) {
        LOG.log(Level.WARNING, errorMessage);
        for (Locator loc : locs) {
            LOG.warning("in " + loc.getSystemId() + " " + loc.getLineNumber() + ":" + loc.getColumnNumber());
        }
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        return null;
    }

    public LSResourceResolver getLSResourceResolver() {
        return this;
    }

    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String resolveBaseURI) {
        EmbeddedSchema embeddedSchema = this.sources.get(namespaceURI);
        if (embeddedSchema != null) {
            return new DOMLSInputImpl(this.baseURI, embeddedSchema.getSystemId(), embeddedSchema.getSchemaElement());
        }
        return null;
    }
}

