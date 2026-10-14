/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.apps;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.xml.resolver.helpers.FileURL;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class XParseError
implements ErrorHandler {
    private boolean showErrors = true;
    private boolean showWarnings = false;
    private int maxMessages = 10;
    private int fatalCount = 0;
    private int errorCount = 0;
    private int warningCount = 0;
    private String baseURI = "";

    public XParseError(boolean errors2, boolean warnings) {
        this.showErrors = errors2;
        this.showWarnings = warnings;
        try {
            URL url2 = FileURL.makeURL("basename");
            this.baseURI = url2.toString();
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public int getFatalCount() {
        return this.fatalCount;
    }

    public int getWarningCount() {
        return this.warningCount;
    }

    public int getMaxMessages() {
        return this.maxMessages;
    }

    public void setMaxMessages(int max) {
        this.maxMessages = max;
    }

    public void error(SAXParseException exception) {
        ++this.errorCount;
        if (this.showErrors && this.errorCount + this.warningCount < this.maxMessages) {
            this.message("Error", exception);
        }
    }

    public void fatalError(SAXParseException exception) {
        ++this.errorCount;
        ++this.fatalCount;
        if (this.showErrors && this.errorCount + this.warningCount < this.maxMessages) {
            this.message("Fatal error", exception);
        }
    }

    public void warning(SAXParseException exception) {
        ++this.warningCount;
        if (this.showWarnings && this.errorCount + this.warningCount < this.maxMessages) {
            this.message("Warning", exception);
        }
    }

    private void message(String type, SAXParseException exception) {
        String filename = exception.getSystemId();
        if (filename.startsWith(this.baseURI)) {
            filename = filename.substring(this.baseURI.length());
        }
        System.out.print(type + ":" + filename + ":" + exception.getLineNumber());
        if (exception.getColumnNumber() > 0) {
            System.out.print(":" + exception.getColumnNumber());
        }
        System.out.println(":" + exception.getMessage());
    }
}

