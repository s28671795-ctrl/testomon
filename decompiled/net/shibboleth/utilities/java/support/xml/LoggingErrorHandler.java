/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import org.slf4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class LoggingErrorHandler
implements ErrorHandler {
    private Logger log;

    public LoggingErrorHandler(Logger logger2) {
        this.log = logger2;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        this.log.error("XML Parsing Error", exception);
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        this.log.error("XML Parsing Error", exception);
        throw exception;
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        this.log.warn("XML Parsing Error", exception);
        throw exception;
    }
}

