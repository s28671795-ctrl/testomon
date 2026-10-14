/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core.xml;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.changelog.ChangeLogParameters;
import liquibase.exception.ChangeLogParseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.xml.AbstractChangeLogParser;
import liquibase.parser.core.xml.LiquibaseEntityResolver;
import liquibase.parser.core.xml.XMLChangeLogSAXHandler;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.util.BomAwareInputStream;
import liquibase.util.FileUtil;
import liquibase.util.LiquibaseUtil;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

public class XMLChangeLogSAXParser
extends AbstractChangeLogParser {
    public static final String LIQUIBASE_SCHEMA_VERSION = XMLChangeLogSAXParser.computeSchemaVersion(LiquibaseUtil.getBuildVersion());
    private final SAXParserFactory saxParserFactory;
    private final LiquibaseEntityResolver resolver = new LiquibaseEntityResolver();

    public XMLChangeLogSAXParser() {
        this.saxParserFactory = SAXParserFactory.newInstance();
        this.saxParserFactory.setValidating(GlobalConfiguration.VALIDATE_XML_CHANGELOG_FILES.getCurrentValue());
        this.saxParserFactory.setNamespaceAware(true);
        if (GlobalConfiguration.SECURE_PARSING.getCurrentValue().booleanValue()) {
            try {
                this.saxParserFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            }
            catch (Throwable e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot enable FEATURE_SECURE_PROCESSING: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public static String getSchemaVersion() {
        return LIQUIBASE_SCHEMA_VERSION;
    }

    @Override
    public boolean supports(String changeLogFile, ResourceAccessor resourceAccessor) {
        return changeLogFile.toLowerCase().endsWith("xml");
    }

    protected SAXParserFactory getSaxParserFactory() {
        return this.saxParserFactory;
    }

    public void setShouldWarnOnMismatchedXsdVersion(boolean shouldWarnOnMismatchedXsdVersion) {
        this.resolver.setShouldWarnOnMismatchedXsdVersion(shouldWarnOnMismatchedXsdVersion);
    }

    @Override
    protected ParsedNode parseToNode(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        try {
            Resource resource = resourceAccessor.get(physicalChangeLogLocation);
            SAXParser parser = this.saxParserFactory.newSAXParser();
            if (GlobalConfiguration.SECURE_PARSING.getCurrentValue().booleanValue()) {
                try {
                    parser.setProperty("http://javax.xml.XMLConstants/property/accessExternalSchema", "http,https");
                }
                catch (SAXException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot enable ACCESS_EXTERNAL_SCHEMA: " + e.getMessage(), e);
                }
            }
            this.trySetSchemaLanguageProperty(parser);
            XMLReader xmlReader = parser.getXMLReader();
            xmlReader.setEntityResolver(this.resolver);
            xmlReader.setErrorHandler(new ErrorHandler(){

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    Scope.getCurrentScope().getLog(this.getClass()).warning(exception.getMessage());
                    throw exception;
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    Scope.getCurrentScope().getLog(this.getClass()).severe(exception.getMessage());
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    Scope.getCurrentScope().getLog(this.getClass()).severe(exception.getMessage());
                    throw exception;
                }
            });
            if (!resource.exists()) {
                if (physicalChangeLogLocation.startsWith("WEB-INF/classes/")) {
                    return this.parseToNode(physicalChangeLogLocation.replaceFirst("WEB-INF/classes/", ""), changeLogParameters, resourceAccessor);
                }
                throw new ChangeLogParseException(FileUtil.getFileNotFoundMessage(physicalChangeLogLocation));
            }
            XMLChangeLogSAXHandler contentHandler = new XMLChangeLogSAXHandler(physicalChangeLogLocation, resourceAccessor, changeLogParameters);
            xmlReader.setContentHandler(contentHandler);
            try (InputStream stream = resource.openInputStream();){
                xmlReader.parse(new InputSource(new BomAwareInputStream(stream)));
            }
            return contentHandler.getDatabaseChangeLogTree();
        }
        catch (ChangeLogParseException e) {
            throw e;
        }
        catch (IOException e) {
            throw new ChangeLogParseException("Error Reading Changelog File: " + e.getMessage(), e);
        }
        catch (SAXParseException e) {
            throw new ChangeLogParseException("Error parsing line " + e.getLineNumber() + " column " + e.getColumnNumber() + " of " + physicalChangeLogLocation + ": " + e.getMessage(), e);
        }
        catch (SAXException e) {
            for (Throwable parentCause = e.getException(); parentCause != null; parentCause = parentCause.getCause()) {
                if (!(parentCause instanceof ChangeLogParseException)) continue;
                throw (ChangeLogParseException)parentCause;
            }
            String reason = e.getMessage();
            String causeReason = null;
            if (e.getCause() != null) {
                causeReason = e.getCause().getMessage();
            }
            if (reason == null) {
                reason = causeReason != null ? causeReason : "Unknown Reason";
            }
            throw new ChangeLogParseException("Invalid Migration File: " + reason, e);
        }
        catch (Exception e) {
            throw new ChangeLogParseException(e);
        }
    }

    private void trySetSchemaLanguageProperty(SAXParser parser) {
        try {
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        }
        catch (SAXNotRecognizedException | SAXNotSupportedException sAXException) {
            // empty catch block
        }
    }

    static String computeSchemaVersion(String version) {
        String finalVersion = null;
        if (version != null && version.contains(".")) {
            String[] splitVersion = version.split("\\.");
            finalVersion = splitVersion[0] + "." + splitVersion[1];
        }
        if (finalVersion == null) {
            finalVersion = "latest";
        }
        return finalVersion;
    }
}

