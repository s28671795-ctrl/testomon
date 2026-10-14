/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.logging.Logger;
import liquibase.parser.core.xml.XSDLookUpException;
import liquibase.resource.Resource;
import liquibase.util.LiquibaseUtil;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

public class LiquibaseEntityResolver
implements EntityResolver2 {
    private static final String XSD_VERSION_REGEX = "(?:-pro-|-)(?<version>[\\d.]*)\\.xsd";
    private static final Pattern XSD_VERSION_PATTERN = Pattern.compile("(?:-pro-|-)(?<version>[\\d.]*)\\.xsd");
    private boolean shouldWarnOnMismatchedXsdVersion = false;
    private static boolean hasWarnedAboutMismatchedXsdVersion = false;

    @Override
    public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException, IOException {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        log.fine("Resolving XML entity name='" + name + "', publicId='" + publicId + "', baseURI='" + baseURI + "', systemId='" + systemId + "'");
        if (systemId == null) {
            log.fine("Cannot determine systemId for name=" + name + ", publicId=" + publicId + ". Will load from network.");
            return null;
        }
        String path = systemId.toLowerCase().replace("http://www.liquibase.org/xml/ns/migrator/", "http://www.liquibase.org/xml/ns/dbchangelog/").replaceFirst("https?://", "");
        if (this.shouldWarnOnMismatchedXsdVersion && !hasWarnedAboutMismatchedXsdVersion) {
            this.warnForMismatchedXsdVersion(systemId);
        }
        InputStream stream = null;
        URL resourceUri = this.getSearchClassloader().getResource(path);
        if (resourceUri == null) {
            Resource resource = Scope.getCurrentScope().getResourceAccessor().get(path);
            if (resource.exists()) {
                stream = resource.openInputStream();
            }
        } else {
            stream = resourceUri.openStream();
        }
        if (stream == null) {
            if (GlobalConfiguration.SECURE_PARSING.getCurrentValue().booleanValue()) {
                String errorMessage = "Unable to resolve xml entity " + systemId + ". " + GlobalConfiguration.SECURE_PARSING.getKey() + " is set to 'true' which does not allow remote lookups. Check for spelling or capitalization errors and missing extensions such as liquibase-commercial in your XSD definition. Or, set it to 'false' to allow remote lookups of xsd files.";
                throw new XSDLookUpException(errorMessage);
            }
            log.fine("Unable to resolve XML entity locally. Will load from network.");
            return null;
        }
        InputSource source2 = new InputSource(stream);
        source2.setPublicId(publicId);
        source2.setSystemId(systemId);
        return source2;
    }

    protected ClassLoader getSearchClassloader() {
        return new CombinedClassLoader();
    }

    private void warnForMismatchedXsdVersion(String systemId) {
        try {
            String xsdVersion;
            String buildVersion;
            Matcher versionMatcher = XSD_VERSION_PATTERN.matcher(systemId);
            boolean found = versionMatcher.find();
            if (found && !(buildVersion = LiquibaseUtil.getBuildVersion()).equals("DEV") && !buildVersion.startsWith(xsdVersion = versionMatcher.group("version"))) {
                hasWarnedAboutMismatchedXsdVersion = true;
                String msg = "INFO: An older version of the XSD is specified in one or more changelog's <databaseChangeLog> header. This can lead to unexpected outcomes. If a specific XSD is not required, please replace all XSD version references with \"-latest\". Learn more at https://docs.liquibase.com/concepts/changelogs/xml-format.html";
                Scope.getCurrentScope().getLog(this.getClass()).info(msg);
                Scope.getCurrentScope().getUI().sendMessage(msg);
            }
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Failed to compare XSD version with build version.", e);
        }
    }

    @Override
    public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
        return null;
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        Scope.getCurrentScope().getLog(this.getClass()).warning("The current XML parser does not seems to not support EntityResolver2. External entities may not be correctly loaded");
        return this.resolveEntity(null, publicId, null, systemId);
    }

    public void setShouldWarnOnMismatchedXsdVersion(boolean shouldWarnOnMismatchedXsdVersion) {
        this.shouldWarnOnMismatchedXsdVersion = shouldWarnOnMismatchedXsdVersion;
    }

    private static class CombinedClassLoader
    extends ClassLoader {
        private final List<ClassLoader> classLoaders = Arrays.asList(Thread.currentThread().getContextClassLoader(), this.getClass().getClassLoader());

        @Override
        public URL getResource(String name) {
            for (ClassLoader classLoader : this.classLoaders) {
                URL resource = classLoader.getResource(name);
                if (resource == null) continue;
                return resource;
            }
            return null;
        }
    }
}

