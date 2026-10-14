/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.io.xpp3;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.apache.maven.model.Activation;
import org.apache.maven.model.ActivationFile;
import org.apache.maven.model.ActivationOS;
import org.apache.maven.model.ActivationProperty;
import org.apache.maven.model.Build;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.CiManagement;
import org.apache.maven.model.ConfigurationContainer;
import org.apache.maven.model.Contributor;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.DeploymentRepository;
import org.apache.maven.model.Developer;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Extension;
import org.apache.maven.model.FileSet;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputSource;
import org.apache.maven.model.IssueManagement;
import org.apache.maven.model.License;
import org.apache.maven.model.MailingList;
import org.apache.maven.model.Model;
import org.apache.maven.model.ModelBase;
import org.apache.maven.model.Notifier;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Parent;
import org.apache.maven.model.PatternSet;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginConfiguration;
import org.apache.maven.model.PluginContainer;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Prerequisites;
import org.apache.maven.model.Profile;
import org.apache.maven.model.Relocation;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.ReportSet;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryBase;
import org.apache.maven.model.RepositoryPolicy;
import org.apache.maven.model.Resource;
import org.apache.maven.model.Scm;
import org.apache.maven.model.Site;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.EntityReplacementMap;
import org.codehaus.plexus.util.xml.pull.MXParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class MavenXpp3ReaderEx {
    private boolean addDefaultEntities = true;
    public final ContentTransformer contentTransformer;

    public MavenXpp3ReaderEx() {
        this(new ContentTransformer(){

            @Override
            public String transform(String source2, String fieldName) {
                return source2;
            }
        });
    }

    public MavenXpp3ReaderEx(ContentTransformer contentTransformer) {
        this.contentTransformer = contentTransformer;
    }

    private boolean checkFieldWithDuplicate(XmlPullParser parser, String tagName, String alias, Set parsed) throws XmlPullParserException {
        if (!parser.getName().equals(tagName) && !parser.getName().equals(alias)) {
            return false;
        }
        if (!parsed.add(tagName)) {
            throw new XmlPullParserException("Duplicated tag: '" + tagName + "'", parser, null);
        }
        return true;
    }

    private void checkUnknownAttribute(XmlPullParser parser, String attribute, String tagName, boolean strict) throws XmlPullParserException, IOException {
        if (strict) {
            throw new XmlPullParserException("Unknown attribute '" + attribute + "' for tag '" + tagName + "'", parser, null);
        }
    }

    private void checkUnknownElement(XmlPullParser parser, boolean strict) throws XmlPullParserException, IOException {
        if (strict) {
            throw new XmlPullParserException("Unrecognised tag: '" + parser.getName() + "'", parser, null);
        }
        int unrecognizedTagCount = 1;
        while (unrecognizedTagCount > 0) {
            int eventType = parser.next();
            if (eventType == 2) {
                ++unrecognizedTagCount;
                continue;
            }
            if (eventType != 3) continue;
            --unrecognizedTagCount;
        }
    }

    public boolean getAddDefaultEntities() {
        return this.addDefaultEntities;
    }

    private boolean getBooleanValue(String s2, String attribute, XmlPullParser parser) throws XmlPullParserException {
        return this.getBooleanValue(s2, attribute, parser, null);
    }

    private boolean getBooleanValue(String s2, String attribute, XmlPullParser parser, String defaultValue) throws XmlPullParserException {
        if (s2 != null && s2.length() != 0) {
            return Boolean.valueOf(s2);
        }
        if (defaultValue != null) {
            return Boolean.valueOf(defaultValue);
        }
        return false;
    }

    private byte getByteValue(String s2, String attribute, XmlPullParser parser, boolean strict) throws XmlPullParserException {
        block3: {
            if (s2 != null) {
                try {
                    return Byte.valueOf(s2);
                }
                catch (NumberFormatException nfe) {
                    if (!strict) break block3;
                    throw new XmlPullParserException("Unable to parse element '" + attribute + "', must be a byte", parser, nfe);
                }
            }
        }
        return 0;
    }

    private char getCharacterValue(String s2, String attribute, XmlPullParser parser) throws XmlPullParserException {
        if (s2 != null) {
            return s2.charAt(0);
        }
        return '\u0000';
    }

    private Date getDateValue(String s2, String attribute, XmlPullParser parser) throws XmlPullParserException {
        return this.getDateValue(s2, attribute, null, parser);
    }

    private Date getDateValue(String s2, String attribute, String dateFormat, XmlPullParser parser) throws XmlPullParserException {
        if (s2 != null) {
            String effectiveDateFormat = dateFormat;
            if (dateFormat == null) {
                effectiveDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
            }
            if ("long".equals(effectiveDateFormat)) {
                try {
                    return new Date(Long.parseLong(s2));
                }
                catch (NumberFormatException e) {
                    throw new XmlPullParserException(e.getMessage(), parser, e);
                }
            }
            try {
                SimpleDateFormat dateParser = new SimpleDateFormat(effectiveDateFormat, Locale.US);
                return dateParser.parse(s2);
            }
            catch (ParseException e) {
                throw new XmlPullParserException(e.getMessage(), parser, e);
            }
        }
        return null;
    }

    private double getDoubleValue(String s2, String attribute, XmlPullParser parser, boolean strict) throws XmlPullParserException {
        block3: {
            if (s2 != null) {
                try {
                    return Double.valueOf(s2);
                }
                catch (NumberFormatException nfe) {
                    if (!strict) break block3;
                    throw new XmlPullParserException("Unable to parse element '" + attribute + "', must be a floating point number", parser, nfe);
                }
            }
        }
        return 0.0;
    }

    private float getFloatValue(String s2, String attribute, XmlPullParser parser, boolean strict) throws XmlPullParserException {
        block3: {
            if (s2 != null) {
                try {
                    return Float.valueOf(s2).floatValue();
                }
                catch (NumberFormatException nfe) {
                    if (!strict) break block3;
                    throw new XmlPullParserException("Unable to parse element '" + attribute + "', must be a floating point number", parser, nfe);
                }
            }
        }
        return 0.0f;
    }

    private int getIntegerValue(String s2, String attribute, XmlPullParser parser, boolean strict) throws XmlPullParserException {
        block3: {
            if (s2 != null) {
                try {
                    return Integer.valueOf(s2);
                }
                catch (NumberFormatException nfe) {
                    if (!strict) break block3;
                    throw new XmlPullParserException("Unable to parse element '" + attribute + "', must be an integer", parser, nfe);
                }
            }
        }
        return 0;
    }

    private long getLongValue(String s2, String attribute, XmlPullParser parser, boolean strict) throws XmlPullParserException {
        block3: {
            if (s2 != null) {
                try {
                    return Long.valueOf(s2);
                }
                catch (NumberFormatException nfe) {
                    if (!strict) break block3;
                    throw new XmlPullParserException("Unable to parse element '" + attribute + "', must be a long integer", parser, nfe);
                }
            }
        }
        return 0L;
    }

    private String getRequiredAttributeValue(String s2, String attribute, XmlPullParser parser, boolean strict) throws XmlPullParserException {
        if (s2 == null && strict) {
            throw new XmlPullParserException("Missing required value for attribute '" + attribute + "'", parser, null);
        }
        return s2;
    }

    private short getShortValue(String s2, String attribute, XmlPullParser parser, boolean strict) throws XmlPullParserException {
        block3: {
            if (s2 != null) {
                try {
                    return Short.valueOf(s2);
                }
                catch (NumberFormatException nfe) {
                    if (!strict) break block3;
                    throw new XmlPullParserException("Unable to parse element '" + attribute + "', must be a short integer", parser, nfe);
                }
            }
        }
        return 0;
    }

    private String getTrimmedValue(String s2) {
        if (s2 != null) {
            s2 = s2.trim();
        }
        return s2;
    }

    private String interpolatedTrimmed(String value, String context) {
        return this.getTrimmedValue(this.contentTransformer.transform(value, context));
    }

    private int nextTag(XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.next();
        if (eventType == 4) {
            eventType = parser.next();
        }
        if (eventType != 2 && eventType != 3) {
            throw new XmlPullParserException("expected START_TAG or END_TAG not " + XmlPullParser.TYPES[eventType], parser, null);
        }
        return eventType;
    }

    public Model read(Reader reader, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        MXParser parser = this.addDefaultEntities ? new MXParser(EntityReplacementMap.defaultEntityReplacementMap) : new MXParser();
        parser.setInput(reader);
        return this.read(parser, strict, source2);
    }

    public Model read(InputStream in, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        return this.read(ReaderFactory.newXmlReader(in), strict, source2);
    }

    private Activation parseActivation(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Activation activation = new Activation();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        activation.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "activeByDefault", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activation.setLocation("activeByDefault", _location);
                activation.setActiveByDefault(this.getBooleanValue(this.interpolatedTrimmed(parser.nextText(), "activeByDefault"), "activeByDefault", parser, "false"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "jdk", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activation.setLocation("jdk", _location);
                activation.setJdk(this.interpolatedTrimmed(parser.nextText(), "jdk"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "os", null, parsed)) {
                activation.setOs(this.parseActivationOS(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "property", null, parsed)) {
                activation.setProperty(this.parseActivationProperty(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "file", null, parsed)) {
                activation.setFile(this.parseActivationFile(parser, strict, source2));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return activation;
    }

    private ActivationFile parseActivationFile(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        ActivationFile activationFile = new ActivationFile();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        activationFile.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "missing", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activationFile.setLocation("missing", _location);
                activationFile.setMissing(this.interpolatedTrimmed(parser.nextText(), "missing"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "exists", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activationFile.setLocation("exists", _location);
                activationFile.setExists(this.interpolatedTrimmed(parser.nextText(), "exists"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return activationFile;
    }

    private ActivationOS parseActivationOS(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        ActivationOS activationOS = new ActivationOS();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        activationOS.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activationOS.setLocation("name", _location);
                activationOS.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "family", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activationOS.setLocation("family", _location);
                activationOS.setFamily(this.interpolatedTrimmed(parser.nextText(), "family"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "arch", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activationOS.setLocation("arch", _location);
                activationOS.setArch(this.interpolatedTrimmed(parser.nextText(), "arch"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "version", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activationOS.setLocation("version", _location);
                activationOS.setVersion(this.interpolatedTrimmed(parser.nextText(), "version"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return activationOS;
    }

    private ActivationProperty parseActivationProperty(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        ActivationProperty activationProperty = new ActivationProperty();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        activationProperty.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activationProperty.setLocation("name", _location);
                activationProperty.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "value", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                activationProperty.setLocation("value", _location);
                activationProperty.setValue(this.interpolatedTrimmed(parser.nextText(), "value"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return activationProperty;
    }

    private Build parseBuild(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Build build = new Build();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        build.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "sourceDirectory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("sourceDirectory", _location);
                build.setSourceDirectory(this.interpolatedTrimmed(parser.nextText(), "sourceDirectory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "scriptSourceDirectory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("scriptSourceDirectory", _location);
                build.setScriptSourceDirectory(this.interpolatedTrimmed(parser.nextText(), "scriptSourceDirectory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "testSourceDirectory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("testSourceDirectory", _location);
                build.setTestSourceDirectory(this.interpolatedTrimmed(parser.nextText(), "testSourceDirectory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "outputDirectory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("outputDirectory", _location);
                build.setOutputDirectory(this.interpolatedTrimmed(parser.nextText(), "outputDirectory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "testOutputDirectory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("testOutputDirectory", _location);
                build.setTestOutputDirectory(this.interpolatedTrimmed(parser.nextText(), "testOutputDirectory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "extensions", null, parsed)) {
                ArrayList<Extension> extensions = new ArrayList<Extension>();
                build.setExtensions(extensions);
                while (parser.nextTag() == 2) {
                    if ("extension".equals(parser.getName())) {
                        extensions.add(this.parseExtension(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "defaultGoal", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("defaultGoal", _location);
                build.setDefaultGoal(this.interpolatedTrimmed(parser.nextText(), "defaultGoal"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "resources", null, parsed)) {
                ArrayList<Resource> resources = new ArrayList<Resource>();
                build.setResources(resources);
                while (parser.nextTag() == 2) {
                    if ("resource".equals(parser.getName())) {
                        resources.add(this.parseResource(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "testResources", null, parsed)) {
                ArrayList<Resource> testResources = new ArrayList<Resource>();
                build.setTestResources(testResources);
                while (parser.nextTag() == 2) {
                    if ("testResource".equals(parser.getName())) {
                        testResources.add(this.parseResource(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "directory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("directory", _location);
                build.setDirectory(this.interpolatedTrimmed(parser.nextText(), "directory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "finalName", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("finalName", _location);
                build.setFinalName(this.interpolatedTrimmed(parser.nextText(), "finalName"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "filters", null, parsed)) {
                ArrayList<String> filters = new ArrayList<String>();
                build.setFilters(filters);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                build.setLocation("filters", _locations);
                while (parser.nextTag() == 2) {
                    if ("filter".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(filters.size(), _location);
                        filters.add(this.interpolatedTrimmed(parser.nextText(), "filters"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "pluginManagement", null, parsed)) {
                build.setPluginManagement(this.parsePluginManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "plugins", null, parsed)) {
                ArrayList<Plugin> plugins = new ArrayList<Plugin>();
                build.setPlugins(plugins);
                while (parser.nextTag() == 2) {
                    if ("plugin".equals(parser.getName())) {
                        plugins.add(this.parsePlugin(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return build;
    }

    private BuildBase parseBuildBase(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        BuildBase buildBase = new BuildBase();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        buildBase.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "defaultGoal", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                buildBase.setLocation("defaultGoal", _location);
                buildBase.setDefaultGoal(this.interpolatedTrimmed(parser.nextText(), "defaultGoal"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "resources", null, parsed)) {
                ArrayList<Resource> resources = new ArrayList<Resource>();
                buildBase.setResources(resources);
                while (parser.nextTag() == 2) {
                    if ("resource".equals(parser.getName())) {
                        resources.add(this.parseResource(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "testResources", null, parsed)) {
                ArrayList<Resource> testResources = new ArrayList<Resource>();
                buildBase.setTestResources(testResources);
                while (parser.nextTag() == 2) {
                    if ("testResource".equals(parser.getName())) {
                        testResources.add(this.parseResource(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "directory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                buildBase.setLocation("directory", _location);
                buildBase.setDirectory(this.interpolatedTrimmed(parser.nextText(), "directory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "finalName", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                buildBase.setLocation("finalName", _location);
                buildBase.setFinalName(this.interpolatedTrimmed(parser.nextText(), "finalName"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "filters", null, parsed)) {
                ArrayList<String> filters = new ArrayList<String>();
                buildBase.setFilters(filters);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                buildBase.setLocation("filters", _locations);
                while (parser.nextTag() == 2) {
                    if ("filter".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(filters.size(), _location);
                        filters.add(this.interpolatedTrimmed(parser.nextText(), "filters"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "pluginManagement", null, parsed)) {
                buildBase.setPluginManagement(this.parsePluginManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "plugins", null, parsed)) {
                ArrayList<Plugin> plugins = new ArrayList<Plugin>();
                buildBase.setPlugins(plugins);
                while (parser.nextTag() == 2) {
                    if ("plugin".equals(parser.getName())) {
                        plugins.add(this.parsePlugin(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return buildBase;
    }

    private CiManagement parseCiManagement(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        CiManagement ciManagement = new CiManagement();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        ciManagement.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "system", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                ciManagement.setLocation("system", _location);
                ciManagement.setSystem(this.interpolatedTrimmed(parser.nextText(), "system"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                ciManagement.setLocation("url", _location);
                ciManagement.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "notifiers", null, parsed)) {
                ArrayList<Notifier> notifiers = new ArrayList<Notifier>();
                ciManagement.setNotifiers(notifiers);
                while (parser.nextTag() == 2) {
                    if ("notifier".equals(parser.getName())) {
                        notifiers.add(this.parseNotifier(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return ciManagement;
    }

    private ConfigurationContainer parseConfigurationContainer(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        ConfigurationContainer configurationContainer = new ConfigurationContainer();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        configurationContainer.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "inherited", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                configurationContainer.setLocation("inherited", _location);
                configurationContainer.setInherited(this.interpolatedTrimmed(parser.nextText(), "inherited"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "configuration", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                configurationContainer.setLocation("configuration", _location);
                configurationContainer.setConfiguration(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return configurationContainer;
    }

    private Contributor parseContributor(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Contributor contributor = new Contributor();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        contributor.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                contributor.setLocation("name", _location);
                contributor.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "email", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                contributor.setLocation("email", _location);
                contributor.setEmail(this.interpolatedTrimmed(parser.nextText(), "email"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                contributor.setLocation("url", _location);
                contributor.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "organization", "organisation", parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                contributor.setLocation("organization", _location);
                contributor.setOrganization(this.interpolatedTrimmed(parser.nextText(), "organization"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "organizationUrl", "organisationUrl", parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                contributor.setLocation("organizationUrl", _location);
                contributor.setOrganizationUrl(this.interpolatedTrimmed(parser.nextText(), "organizationUrl"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "roles", null, parsed)) {
                ArrayList<String> roles = new ArrayList<String>();
                contributor.setRoles(roles);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                contributor.setLocation("roles", _locations);
                while (parser.nextTag() == 2) {
                    if ("role".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(roles.size(), _location);
                        roles.add(this.interpolatedTrimmed(parser.nextText(), "roles"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "timezone", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                contributor.setLocation("timezone", _location);
                contributor.setTimezone(this.interpolatedTrimmed(parser.nextText(), "timezone"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "properties", null, parsed)) {
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                contributor.setLocation("properties", _locations);
                while (parser.nextTag() == 2) {
                    String key = parser.getName();
                    _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                    _locations.setLocation(key, _location);
                    String value = parser.nextText().trim();
                    contributor.addProperty(key, value);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return contributor;
    }

    private Dependency parseDependency(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Dependency dependency = new Dependency();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        dependency.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "groupId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                dependency.setLocation("groupId", _location);
                dependency.setGroupId(this.interpolatedTrimmed(parser.nextText(), "groupId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "artifactId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                dependency.setLocation("artifactId", _location);
                dependency.setArtifactId(this.interpolatedTrimmed(parser.nextText(), "artifactId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "version", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                dependency.setLocation("version", _location);
                dependency.setVersion(this.interpolatedTrimmed(parser.nextText(), "version"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "type", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                dependency.setLocation("type", _location);
                dependency.setType(this.interpolatedTrimmed(parser.nextText(), "type"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "classifier", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                dependency.setLocation("classifier", _location);
                dependency.setClassifier(this.interpolatedTrimmed(parser.nextText(), "classifier"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "scope", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                dependency.setLocation("scope", _location);
                dependency.setScope(this.interpolatedTrimmed(parser.nextText(), "scope"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "systemPath", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                dependency.setLocation("systemPath", _location);
                dependency.setSystemPath(this.interpolatedTrimmed(parser.nextText(), "systemPath"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "exclusions", null, parsed)) {
                ArrayList<Exclusion> exclusions = new ArrayList<Exclusion>();
                dependency.setExclusions(exclusions);
                while (parser.nextTag() == 2) {
                    if ("exclusion".equals(parser.getName())) {
                        exclusions.add(this.parseExclusion(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "optional", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                dependency.setLocation("optional", _location);
                dependency.setOptional(this.interpolatedTrimmed(parser.nextText(), "optional"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return dependency;
    }

    private DependencyManagement parseDependencyManagement(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        DependencyManagement dependencyManagement = new DependencyManagement();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        dependencyManagement.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "dependencies", null, parsed)) {
                ArrayList<Dependency> dependencies = new ArrayList<Dependency>();
                dependencyManagement.setDependencies(dependencies);
                while (parser.nextTag() == 2) {
                    if ("dependency".equals(parser.getName())) {
                        dependencies.add(this.parseDependency(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return dependencyManagement;
    }

    private DeploymentRepository parseDeploymentRepository(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        DeploymentRepository deploymentRepository = new DeploymentRepository();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        deploymentRepository.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "uniqueVersion", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                deploymentRepository.setLocation("uniqueVersion", _location);
                deploymentRepository.setUniqueVersion(this.getBooleanValue(this.interpolatedTrimmed(parser.nextText(), "uniqueVersion"), "uniqueVersion", parser, "true"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "releases", null, parsed)) {
                deploymentRepository.setReleases(this.parseRepositoryPolicy(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "snapshots", null, parsed)) {
                deploymentRepository.setSnapshots(this.parseRepositoryPolicy(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "id", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                deploymentRepository.setLocation("id", _location);
                deploymentRepository.setId(this.interpolatedTrimmed(parser.nextText(), "id"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                deploymentRepository.setLocation("name", _location);
                deploymentRepository.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                deploymentRepository.setLocation("url", _location);
                deploymentRepository.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "layout", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                deploymentRepository.setLocation("layout", _location);
                deploymentRepository.setLayout(this.interpolatedTrimmed(parser.nextText(), "layout"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return deploymentRepository;
    }

    private Developer parseDeveloper(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Developer developer = new Developer();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        developer.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "id", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("id", _location);
                developer.setId(this.interpolatedTrimmed(parser.nextText(), "id"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("name", _location);
                developer.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "email", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("email", _location);
                developer.setEmail(this.interpolatedTrimmed(parser.nextText(), "email"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("url", _location);
                developer.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "organization", "organisation", parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("organization", _location);
                developer.setOrganization(this.interpolatedTrimmed(parser.nextText(), "organization"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "organizationUrl", "organisationUrl", parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("organizationUrl", _location);
                developer.setOrganizationUrl(this.interpolatedTrimmed(parser.nextText(), "organizationUrl"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "roles", null, parsed)) {
                ArrayList<String> roles = new ArrayList<String>();
                developer.setRoles(roles);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("roles", _locations);
                while (parser.nextTag() == 2) {
                    if ("role".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(roles.size(), _location);
                        roles.add(this.interpolatedTrimmed(parser.nextText(), "roles"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "timezone", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("timezone", _location);
                developer.setTimezone(this.interpolatedTrimmed(parser.nextText(), "timezone"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "properties", null, parsed)) {
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                developer.setLocation("properties", _locations);
                while (parser.nextTag() == 2) {
                    String key = parser.getName();
                    _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                    _locations.setLocation(key, _location);
                    String value = parser.nextText().trim();
                    developer.addProperty(key, value);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return developer;
    }

    private DistributionManagement parseDistributionManagement(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        DistributionManagement distributionManagement = new DistributionManagement();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        distributionManagement.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "repository", null, parsed)) {
                distributionManagement.setRepository(this.parseDeploymentRepository(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "snapshotRepository", null, parsed)) {
                distributionManagement.setSnapshotRepository(this.parseDeploymentRepository(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "site", null, parsed)) {
                distributionManagement.setSite(this.parseSite(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "downloadUrl", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                distributionManagement.setLocation("downloadUrl", _location);
                distributionManagement.setDownloadUrl(this.interpolatedTrimmed(parser.nextText(), "downloadUrl"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "relocation", null, parsed)) {
                distributionManagement.setRelocation(this.parseRelocation(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "status", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                distributionManagement.setLocation("status", _location);
                distributionManagement.setStatus(this.interpolatedTrimmed(parser.nextText(), "status"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return distributionManagement;
    }

    private Exclusion parseExclusion(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Exclusion exclusion = new Exclusion();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        exclusion.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "groupId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                exclusion.setLocation("groupId", _location);
                exclusion.setGroupId(this.interpolatedTrimmed(parser.nextText(), "groupId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "artifactId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                exclusion.setLocation("artifactId", _location);
                exclusion.setArtifactId(this.interpolatedTrimmed(parser.nextText(), "artifactId"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return exclusion;
    }

    private Extension parseExtension(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Extension extension = new Extension();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        extension.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "groupId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                extension.setLocation("groupId", _location);
                extension.setGroupId(this.interpolatedTrimmed(parser.nextText(), "groupId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "artifactId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                extension.setLocation("artifactId", _location);
                extension.setArtifactId(this.interpolatedTrimmed(parser.nextText(), "artifactId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "version", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                extension.setLocation("version", _location);
                extension.setVersion(this.interpolatedTrimmed(parser.nextText(), "version"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return extension;
    }

    private FileSet parseFileSet(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        FileSet fileSet = new FileSet();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        fileSet.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            InputLocation _locations;
            if (this.checkFieldWithDuplicate(parser, "directory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                fileSet.setLocation("directory", _location);
                fileSet.setDirectory(this.interpolatedTrimmed(parser.nextText(), "directory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "includes", null, parsed)) {
                ArrayList<String> includes = new ArrayList<String>();
                fileSet.setIncludes(includes);
                _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                fileSet.setLocation("includes", _locations);
                while (parser.nextTag() == 2) {
                    if ("include".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(includes.size(), _location);
                        includes.add(this.interpolatedTrimmed(parser.nextText(), "includes"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "excludes", null, parsed)) {
                ArrayList<String> excludes = new ArrayList<String>();
                fileSet.setExcludes(excludes);
                _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                fileSet.setLocation("excludes", _locations);
                while (parser.nextTag() == 2) {
                    if ("exclude".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(excludes.size(), _location);
                        excludes.add(this.interpolatedTrimmed(parser.nextText(), "excludes"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return fileSet;
    }

    private IssueManagement parseIssueManagement(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        IssueManagement issueManagement = new IssueManagement();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        issueManagement.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "system", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                issueManagement.setLocation("system", _location);
                issueManagement.setSystem(this.interpolatedTrimmed(parser.nextText(), "system"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                issueManagement.setLocation("url", _location);
                issueManagement.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return issueManagement;
    }

    private License parseLicense(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        License license = new License();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        license.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                license.setLocation("name", _location);
                license.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                license.setLocation("url", _location);
                license.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "distribution", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                license.setLocation("distribution", _location);
                license.setDistribution(this.interpolatedTrimmed(parser.nextText(), "distribution"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "comments", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                license.setLocation("comments", _location);
                license.setComments(this.interpolatedTrimmed(parser.nextText(), "comments"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return license;
    }

    private MailingList parseMailingList(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        MailingList mailingList = new MailingList();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        mailingList.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                mailingList.setLocation("name", _location);
                mailingList.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "subscribe", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                mailingList.setLocation("subscribe", _location);
                mailingList.setSubscribe(this.interpolatedTrimmed(parser.nextText(), "subscribe"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "unsubscribe", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                mailingList.setLocation("unsubscribe", _location);
                mailingList.setUnsubscribe(this.interpolatedTrimmed(parser.nextText(), "unsubscribe"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "post", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                mailingList.setLocation("post", _location);
                mailingList.setPost(this.interpolatedTrimmed(parser.nextText(), "post"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "archive", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                mailingList.setLocation("archive", _location);
                mailingList.setArchive(this.interpolatedTrimmed(parser.nextText(), "archive"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "otherArchives", null, parsed)) {
                ArrayList<String> otherArchives = new ArrayList<String>();
                mailingList.setOtherArchives(otherArchives);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                mailingList.setLocation("otherArchives", _locations);
                while (parser.nextTag() == 2) {
                    if ("otherArchive".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(otherArchives.size(), _location);
                        otherArchives.add(this.interpolatedTrimmed(parser.nextText(), "otherArchives"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return mailingList;
    }

    private Model parseModel(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Model model = new Model();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        model.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0 || "xmlns".equals(name)) continue;
            if ("child.project.url.inherit.append.path".equals(name)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("childProjectUrlInheritAppendPath", _location);
                model.setChildProjectUrlInheritAppendPath(this.interpolatedTrimmed(value, "child.project.url.inherit.append.path"));
                continue;
            }
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "modelVersion", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("modelVersion", _location);
                model.setModelVersion(this.interpolatedTrimmed(parser.nextText(), "modelVersion"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "parent", null, parsed)) {
                model.setParent(this.parseParent(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "groupId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("groupId", _location);
                model.setGroupId(this.interpolatedTrimmed(parser.nextText(), "groupId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "artifactId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("artifactId", _location);
                model.setArtifactId(this.interpolatedTrimmed(parser.nextText(), "artifactId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "version", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("version", _location);
                model.setVersion(this.interpolatedTrimmed(parser.nextText(), "version"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "packaging", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("packaging", _location);
                model.setPackaging(this.interpolatedTrimmed(parser.nextText(), "packaging"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("name", _location);
                model.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "description", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("description", _location);
                model.setDescription(this.interpolatedTrimmed(parser.nextText(), "description"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("url", _location);
                model.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "inceptionYear", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("inceptionYear", _location);
                model.setInceptionYear(this.interpolatedTrimmed(parser.nextText(), "inceptionYear"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "organization", "organisation", parsed)) {
                model.setOrganization(this.parseOrganization(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "licenses", null, parsed)) {
                ArrayList<License> licenses = new ArrayList<License>();
                model.setLicenses(licenses);
                while (parser.nextTag() == 2) {
                    if ("license".equals(parser.getName())) {
                        licenses.add(this.parseLicense(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "developers", null, parsed)) {
                ArrayList<Developer> developers = new ArrayList<Developer>();
                model.setDevelopers(developers);
                while (parser.nextTag() == 2) {
                    if ("developer".equals(parser.getName())) {
                        developers.add(this.parseDeveloper(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "contributors", null, parsed)) {
                ArrayList<Contributor> contributors = new ArrayList<Contributor>();
                model.setContributors(contributors);
                while (parser.nextTag() == 2) {
                    if ("contributor".equals(parser.getName())) {
                        contributors.add(this.parseContributor(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "mailingLists", null, parsed)) {
                ArrayList<MailingList> mailingLists = new ArrayList<MailingList>();
                model.setMailingLists(mailingLists);
                while (parser.nextTag() == 2) {
                    if ("mailingList".equals(parser.getName())) {
                        mailingLists.add(this.parseMailingList(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "prerequisites", null, parsed)) {
                model.setPrerequisites(this.parsePrerequisites(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "modules", null, parsed)) {
                ArrayList<String> modules = new ArrayList<String>();
                model.setModules(modules);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("modules", _locations);
                while (parser.nextTag() == 2) {
                    if ("module".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(modules.size(), _location);
                        modules.add(this.interpolatedTrimmed(parser.nextText(), "modules"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "scm", null, parsed)) {
                model.setScm(this.parseScm(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "issueManagement", null, parsed)) {
                model.setIssueManagement(this.parseIssueManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "ciManagement", null, parsed)) {
                model.setCiManagement(this.parseCiManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "distributionManagement", null, parsed)) {
                model.setDistributionManagement(this.parseDistributionManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "properties", null, parsed)) {
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("properties", _locations);
                while (parser.nextTag() == 2) {
                    String key = parser.getName();
                    _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                    _locations.setLocation(key, _location);
                    String value = parser.nextText().trim();
                    model.addProperty(key, value);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "dependencyManagement", null, parsed)) {
                model.setDependencyManagement(this.parseDependencyManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "dependencies", null, parsed)) {
                ArrayList<Dependency> dependencies = new ArrayList<Dependency>();
                model.setDependencies(dependencies);
                while (parser.nextTag() == 2) {
                    if ("dependency".equals(parser.getName())) {
                        dependencies.add(this.parseDependency(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "repositories", null, parsed)) {
                ArrayList<Repository> repositories = new ArrayList<Repository>();
                model.setRepositories(repositories);
                while (parser.nextTag() == 2) {
                    if ("repository".equals(parser.getName())) {
                        repositories.add(this.parseRepository(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "pluginRepositories", null, parsed)) {
                ArrayList<Repository> pluginRepositories = new ArrayList<Repository>();
                model.setPluginRepositories(pluginRepositories);
                while (parser.nextTag() == 2) {
                    if ("pluginRepository".equals(parser.getName())) {
                        pluginRepositories.add(this.parseRepository(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "build", null, parsed)) {
                model.setBuild(this.parseBuild(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "reports", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                model.setLocation("reports", _location);
                model.setReports(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "reporting", null, parsed)) {
                model.setReporting(this.parseReporting(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "profiles", null, parsed)) {
                ArrayList<Profile> profiles = new ArrayList<Profile>();
                model.setProfiles(profiles);
                while (parser.nextTag() == 2) {
                    if ("profile".equals(parser.getName())) {
                        profiles.add(this.parseProfile(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return model;
    }

    private ModelBase parseModelBase(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        ModelBase modelBase = new ModelBase();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        modelBase.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "modules", null, parsed)) {
                ArrayList<String> modules = new ArrayList<String>();
                modelBase.setModules(modules);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                modelBase.setLocation("modules", _locations);
                while (parser.nextTag() == 2) {
                    if ("module".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(modules.size(), _location);
                        modules.add(this.interpolatedTrimmed(parser.nextText(), "modules"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "distributionManagement", null, parsed)) {
                modelBase.setDistributionManagement(this.parseDistributionManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "properties", null, parsed)) {
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                modelBase.setLocation("properties", _locations);
                while (parser.nextTag() == 2) {
                    String key = parser.getName();
                    _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                    _locations.setLocation(key, _location);
                    String value = parser.nextText().trim();
                    modelBase.addProperty(key, value);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "dependencyManagement", null, parsed)) {
                modelBase.setDependencyManagement(this.parseDependencyManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "dependencies", null, parsed)) {
                ArrayList<Dependency> dependencies = new ArrayList<Dependency>();
                modelBase.setDependencies(dependencies);
                while (parser.nextTag() == 2) {
                    if ("dependency".equals(parser.getName())) {
                        dependencies.add(this.parseDependency(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "repositories", null, parsed)) {
                ArrayList<Repository> repositories = new ArrayList<Repository>();
                modelBase.setRepositories(repositories);
                while (parser.nextTag() == 2) {
                    if ("repository".equals(parser.getName())) {
                        repositories.add(this.parseRepository(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "pluginRepositories", null, parsed)) {
                ArrayList<Repository> pluginRepositories = new ArrayList<Repository>();
                modelBase.setPluginRepositories(pluginRepositories);
                while (parser.nextTag() == 2) {
                    if ("pluginRepository".equals(parser.getName())) {
                        pluginRepositories.add(this.parseRepository(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "reports", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                modelBase.setLocation("reports", _location);
                modelBase.setReports(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "reporting", null, parsed)) {
                modelBase.setReporting(this.parseReporting(parser, strict, source2));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return modelBase;
    }

    private Notifier parseNotifier(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Notifier notifier = new Notifier();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        notifier.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "type", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                notifier.setLocation("type", _location);
                notifier.setType(this.interpolatedTrimmed(parser.nextText(), "type"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "sendOnError", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                notifier.setLocation("sendOnError", _location);
                notifier.setSendOnError(this.getBooleanValue(this.interpolatedTrimmed(parser.nextText(), "sendOnError"), "sendOnError", parser, "true"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "sendOnFailure", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                notifier.setLocation("sendOnFailure", _location);
                notifier.setSendOnFailure(this.getBooleanValue(this.interpolatedTrimmed(parser.nextText(), "sendOnFailure"), "sendOnFailure", parser, "true"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "sendOnSuccess", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                notifier.setLocation("sendOnSuccess", _location);
                notifier.setSendOnSuccess(this.getBooleanValue(this.interpolatedTrimmed(parser.nextText(), "sendOnSuccess"), "sendOnSuccess", parser, "true"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "sendOnWarning", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                notifier.setLocation("sendOnWarning", _location);
                notifier.setSendOnWarning(this.getBooleanValue(this.interpolatedTrimmed(parser.nextText(), "sendOnWarning"), "sendOnWarning", parser, "true"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "address", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                notifier.setLocation("address", _location);
                notifier.setAddress(this.interpolatedTrimmed(parser.nextText(), "address"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "configuration", null, parsed)) {
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                notifier.setLocation("configuration", _locations);
                while (parser.nextTag() == 2) {
                    String key = parser.getName();
                    _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                    _locations.setLocation(key, _location);
                    String value = parser.nextText().trim();
                    notifier.addConfiguration(key, value);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return notifier;
    }

    private Organization parseOrganization(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Organization organization = new Organization();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        organization.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                organization.setLocation("name", _location);
                organization.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                organization.setLocation("url", _location);
                organization.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return organization;
    }

    private Parent parseParent(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Parent parent = new Parent();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        parent.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "groupId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                parent.setLocation("groupId", _location);
                parent.setGroupId(this.interpolatedTrimmed(parser.nextText(), "groupId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "artifactId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                parent.setLocation("artifactId", _location);
                parent.setArtifactId(this.interpolatedTrimmed(parser.nextText(), "artifactId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "version", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                parent.setLocation("version", _location);
                parent.setVersion(this.interpolatedTrimmed(parser.nextText(), "version"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "relativePath", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                parent.setLocation("relativePath", _location);
                parent.setRelativePath(this.interpolatedTrimmed(parser.nextText(), "relativePath"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return parent;
    }

    private PatternSet parsePatternSet(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        PatternSet patternSet = new PatternSet();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        patternSet.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            InputLocation _locations;
            if (this.checkFieldWithDuplicate(parser, "includes", null, parsed)) {
                ArrayList<String> includes = new ArrayList<String>();
                patternSet.setIncludes(includes);
                _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                patternSet.setLocation("includes", _locations);
                while (parser.nextTag() == 2) {
                    if ("include".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(includes.size(), _location);
                        includes.add(this.interpolatedTrimmed(parser.nextText(), "includes"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "excludes", null, parsed)) {
                ArrayList<String> excludes = new ArrayList<String>();
                patternSet.setExcludes(excludes);
                _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                patternSet.setLocation("excludes", _locations);
                while (parser.nextTag() == 2) {
                    if ("exclude".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(excludes.size(), _location);
                        excludes.add(this.interpolatedTrimmed(parser.nextText(), "excludes"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return patternSet;
    }

    private Plugin parsePlugin(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Plugin plugin = new Plugin();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        plugin.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "groupId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                plugin.setLocation("groupId", _location);
                plugin.setGroupId(this.interpolatedTrimmed(parser.nextText(), "groupId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "artifactId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                plugin.setLocation("artifactId", _location);
                plugin.setArtifactId(this.interpolatedTrimmed(parser.nextText(), "artifactId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "version", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                plugin.setLocation("version", _location);
                plugin.setVersion(this.interpolatedTrimmed(parser.nextText(), "version"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "extensions", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                plugin.setLocation("extensions", _location);
                plugin.setExtensions(this.interpolatedTrimmed(parser.nextText(), "extensions"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "executions", null, parsed)) {
                ArrayList<PluginExecution> executions = new ArrayList<PluginExecution>();
                plugin.setExecutions(executions);
                while (parser.nextTag() == 2) {
                    if ("execution".equals(parser.getName())) {
                        executions.add(this.parsePluginExecution(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "dependencies", null, parsed)) {
                ArrayList<Dependency> dependencies = new ArrayList<Dependency>();
                plugin.setDependencies(dependencies);
                while (parser.nextTag() == 2) {
                    if ("dependency".equals(parser.getName())) {
                        dependencies.add(this.parseDependency(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "goals", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                plugin.setLocation("goals", _location);
                plugin.setGoals(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "inherited", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                plugin.setLocation("inherited", _location);
                plugin.setInherited(this.interpolatedTrimmed(parser.nextText(), "inherited"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "configuration", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                plugin.setLocation("configuration", _location);
                plugin.setConfiguration(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return plugin;
    }

    private PluginConfiguration parsePluginConfiguration(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        pluginConfiguration.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "pluginManagement", null, parsed)) {
                pluginConfiguration.setPluginManagement(this.parsePluginManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "plugins", null, parsed)) {
                ArrayList<Plugin> plugins = new ArrayList<Plugin>();
                pluginConfiguration.setPlugins(plugins);
                while (parser.nextTag() == 2) {
                    if ("plugin".equals(parser.getName())) {
                        plugins.add(this.parsePlugin(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return pluginConfiguration;
    }

    private PluginContainer parsePluginContainer(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        PluginContainer pluginContainer = new PluginContainer();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        pluginContainer.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "plugins", null, parsed)) {
                ArrayList<Plugin> plugins = new ArrayList<Plugin>();
                pluginContainer.setPlugins(plugins);
                while (parser.nextTag() == 2) {
                    if ("plugin".equals(parser.getName())) {
                        plugins.add(this.parsePlugin(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return pluginContainer;
    }

    private PluginExecution parsePluginExecution(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        PluginExecution pluginExecution = new PluginExecution();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        pluginExecution.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "id", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                pluginExecution.setLocation("id", _location);
                pluginExecution.setId(this.interpolatedTrimmed(parser.nextText(), "id"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "phase", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                pluginExecution.setLocation("phase", _location);
                pluginExecution.setPhase(this.interpolatedTrimmed(parser.nextText(), "phase"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "goals", null, parsed)) {
                ArrayList<String> goals = new ArrayList<String>();
                pluginExecution.setGoals(goals);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                pluginExecution.setLocation("goals", _locations);
                while (parser.nextTag() == 2) {
                    if ("goal".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(goals.size(), _location);
                        goals.add(this.interpolatedTrimmed(parser.nextText(), "goals"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "inherited", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                pluginExecution.setLocation("inherited", _location);
                pluginExecution.setInherited(this.interpolatedTrimmed(parser.nextText(), "inherited"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "configuration", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                pluginExecution.setLocation("configuration", _location);
                pluginExecution.setConfiguration(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return pluginExecution;
    }

    private PluginManagement parsePluginManagement(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        PluginManagement pluginManagement = new PluginManagement();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        pluginManagement.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "plugins", null, parsed)) {
                ArrayList<Plugin> plugins = new ArrayList<Plugin>();
                pluginManagement.setPlugins(plugins);
                while (parser.nextTag() == 2) {
                    if ("plugin".equals(parser.getName())) {
                        plugins.add(this.parsePlugin(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return pluginManagement;
    }

    private Prerequisites parsePrerequisites(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Prerequisites prerequisites = new Prerequisites();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        prerequisites.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "maven", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                prerequisites.setLocation("maven", _location);
                prerequisites.setMaven(this.interpolatedTrimmed(parser.nextText(), "maven"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return prerequisites;
    }

    private Profile parseProfile(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Profile profile = new Profile();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        profile.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "id", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                profile.setLocation("id", _location);
                profile.setId(this.interpolatedTrimmed(parser.nextText(), "id"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "activation", null, parsed)) {
                profile.setActivation(this.parseActivation(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "build", null, parsed)) {
                profile.setBuild(this.parseBuildBase(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "modules", null, parsed)) {
                ArrayList<String> modules = new ArrayList<String>();
                profile.setModules(modules);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                profile.setLocation("modules", _locations);
                while (parser.nextTag() == 2) {
                    if ("module".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(modules.size(), _location);
                        modules.add(this.interpolatedTrimmed(parser.nextText(), "modules"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "distributionManagement", null, parsed)) {
                profile.setDistributionManagement(this.parseDistributionManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "properties", null, parsed)) {
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                profile.setLocation("properties", _locations);
                while (parser.nextTag() == 2) {
                    String key = parser.getName();
                    _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                    _locations.setLocation(key, _location);
                    String value = parser.nextText().trim();
                    profile.addProperty(key, value);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "dependencyManagement", null, parsed)) {
                profile.setDependencyManagement(this.parseDependencyManagement(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "dependencies", null, parsed)) {
                ArrayList<Dependency> dependencies = new ArrayList<Dependency>();
                profile.setDependencies(dependencies);
                while (parser.nextTag() == 2) {
                    if ("dependency".equals(parser.getName())) {
                        dependencies.add(this.parseDependency(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "repositories", null, parsed)) {
                ArrayList<Repository> repositories = new ArrayList<Repository>();
                profile.setRepositories(repositories);
                while (parser.nextTag() == 2) {
                    if ("repository".equals(parser.getName())) {
                        repositories.add(this.parseRepository(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "pluginRepositories", null, parsed)) {
                ArrayList<Repository> pluginRepositories = new ArrayList<Repository>();
                profile.setPluginRepositories(pluginRepositories);
                while (parser.nextTag() == 2) {
                    if ("pluginRepository".equals(parser.getName())) {
                        pluginRepositories.add(this.parseRepository(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "reports", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                profile.setLocation("reports", _location);
                profile.setReports(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "reporting", null, parsed)) {
                profile.setReporting(this.parseReporting(parser, strict, source2));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return profile;
    }

    private Relocation parseRelocation(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Relocation relocation = new Relocation();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        relocation.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "groupId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                relocation.setLocation("groupId", _location);
                relocation.setGroupId(this.interpolatedTrimmed(parser.nextText(), "groupId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "artifactId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                relocation.setLocation("artifactId", _location);
                relocation.setArtifactId(this.interpolatedTrimmed(parser.nextText(), "artifactId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "version", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                relocation.setLocation("version", _location);
                relocation.setVersion(this.interpolatedTrimmed(parser.nextText(), "version"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "message", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                relocation.setLocation("message", _location);
                relocation.setMessage(this.interpolatedTrimmed(parser.nextText(), "message"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return relocation;
    }

    private ReportPlugin parseReportPlugin(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        ReportPlugin reportPlugin = new ReportPlugin();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        reportPlugin.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "groupId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportPlugin.setLocation("groupId", _location);
                reportPlugin.setGroupId(this.interpolatedTrimmed(parser.nextText(), "groupId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "artifactId", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportPlugin.setLocation("artifactId", _location);
                reportPlugin.setArtifactId(this.interpolatedTrimmed(parser.nextText(), "artifactId"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "version", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportPlugin.setLocation("version", _location);
                reportPlugin.setVersion(this.interpolatedTrimmed(parser.nextText(), "version"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "reportSets", null, parsed)) {
                ArrayList<ReportSet> reportSets = new ArrayList<ReportSet>();
                reportPlugin.setReportSets(reportSets);
                while (parser.nextTag() == 2) {
                    if ("reportSet".equals(parser.getName())) {
                        reportSets.add(this.parseReportSet(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "inherited", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportPlugin.setLocation("inherited", _location);
                reportPlugin.setInherited(this.interpolatedTrimmed(parser.nextText(), "inherited"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "configuration", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportPlugin.setLocation("configuration", _location);
                reportPlugin.setConfiguration(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return reportPlugin;
    }

    private ReportSet parseReportSet(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        ReportSet reportSet = new ReportSet();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        reportSet.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "id", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportSet.setLocation("id", _location);
                reportSet.setId(this.interpolatedTrimmed(parser.nextText(), "id"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "reports", null, parsed)) {
                ArrayList<String> reports = new ArrayList<String>();
                reportSet.setReports(reports);
                InputLocation _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportSet.setLocation("reports", _locations);
                while (parser.nextTag() == 2) {
                    if ("report".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(reports.size(), _location);
                        reports.add(this.interpolatedTrimmed(parser.nextText(), "reports"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "inherited", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportSet.setLocation("inherited", _location);
                reportSet.setInherited(this.interpolatedTrimmed(parser.nextText(), "inherited"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "configuration", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reportSet.setLocation("configuration", _location);
                reportSet.setConfiguration(Xpp3DomBuilder.build(parser, true, (Xpp3DomBuilder.InputLocationBuilder)new Xpp3DomBuilderInputLocationBuilder(_location)));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return reportSet;
    }

    private Reporting parseReporting(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Reporting reporting = new Reporting();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        reporting.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "excludeDefaults", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reporting.setLocation("excludeDefaults", _location);
                reporting.setExcludeDefaults(this.interpolatedTrimmed(parser.nextText(), "excludeDefaults"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "outputDirectory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                reporting.setLocation("outputDirectory", _location);
                reporting.setOutputDirectory(this.interpolatedTrimmed(parser.nextText(), "outputDirectory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "plugins", null, parsed)) {
                ArrayList<ReportPlugin> plugins = new ArrayList<ReportPlugin>();
                reporting.setPlugins(plugins);
                while (parser.nextTag() == 2) {
                    if ("plugin".equals(parser.getName())) {
                        plugins.add(this.parseReportPlugin(parser, strict, source2));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return reporting;
    }

    private Repository parseRepository(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Repository repository = new Repository();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        repository.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "releases", null, parsed)) {
                repository.setReleases(this.parseRepositoryPolicy(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "snapshots", null, parsed)) {
                repository.setSnapshots(this.parseRepositoryPolicy(parser, strict, source2));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "id", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repository.setLocation("id", _location);
                repository.setId(this.interpolatedTrimmed(parser.nextText(), "id"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repository.setLocation("name", _location);
                repository.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repository.setLocation("url", _location);
                repository.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "layout", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repository.setLocation("layout", _location);
                repository.setLayout(this.interpolatedTrimmed(parser.nextText(), "layout"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return repository;
    }

    private RepositoryBase parseRepositoryBase(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        RepositoryBase repositoryBase = new RepositoryBase();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        repositoryBase.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "id", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repositoryBase.setLocation("id", _location);
                repositoryBase.setId(this.interpolatedTrimmed(parser.nextText(), "id"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repositoryBase.setLocation("name", _location);
                repositoryBase.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repositoryBase.setLocation("url", _location);
                repositoryBase.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "layout", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repositoryBase.setLocation("layout", _location);
                repositoryBase.setLayout(this.interpolatedTrimmed(parser.nextText(), "layout"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return repositoryBase;
    }

    private RepositoryPolicy parseRepositoryPolicy(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        RepositoryPolicy repositoryPolicy = new RepositoryPolicy();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        repositoryPolicy.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "enabled", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repositoryPolicy.setLocation("enabled", _location);
                repositoryPolicy.setEnabled(this.interpolatedTrimmed(parser.nextText(), "enabled"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "updatePolicy", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repositoryPolicy.setLocation("updatePolicy", _location);
                repositoryPolicy.setUpdatePolicy(this.interpolatedTrimmed(parser.nextText(), "updatePolicy"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "checksumPolicy", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                repositoryPolicy.setLocation("checksumPolicy", _location);
                repositoryPolicy.setChecksumPolicy(this.interpolatedTrimmed(parser.nextText(), "checksumPolicy"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return repositoryPolicy;
    }

    private Resource parseResource(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Resource resource = new Resource();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        resource.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            InputLocation _locations;
            if (this.checkFieldWithDuplicate(parser, "targetPath", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                resource.setLocation("targetPath", _location);
                resource.setTargetPath(this.interpolatedTrimmed(parser.nextText(), "targetPath"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "filtering", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                resource.setLocation("filtering", _location);
                resource.setFiltering(this.interpolatedTrimmed(parser.nextText(), "filtering"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "directory", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                resource.setLocation("directory", _location);
                resource.setDirectory(this.interpolatedTrimmed(parser.nextText(), "directory"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "includes", null, parsed)) {
                ArrayList<String> includes = new ArrayList<String>();
                resource.setIncludes(includes);
                _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                resource.setLocation("includes", _locations);
                while (parser.nextTag() == 2) {
                    if ("include".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(includes.size(), _location);
                        includes.add(this.interpolatedTrimmed(parser.nextText(), "includes"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "excludes", null, parsed)) {
                ArrayList<String> excludes = new ArrayList<String>();
                resource.setExcludes(excludes);
                _locations = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                resource.setLocation("excludes", _locations);
                while (parser.nextTag() == 2) {
                    if ("exclude".equals(parser.getName())) {
                        _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                        _locations.setLocation(excludes.size(), _location);
                        excludes.add(this.interpolatedTrimmed(parser.nextText(), "excludes"));
                        continue;
                    }
                    this.checkUnknownElement(parser, strict);
                }
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return resource;
    }

    private Scm parseScm(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Scm scm = new Scm();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        scm.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            if ("child.scm.connection.inherit.append.path".equals(name)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                scm.setLocation("childScmConnectionInheritAppendPath", _location);
                scm.setChildScmConnectionInheritAppendPath(this.interpolatedTrimmed(value, "child.scm.connection.inherit.append.path"));
                continue;
            }
            if ("child.scm.developerConnection.inherit.append.path".equals(name)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                scm.setLocation("childScmDeveloperConnectionInheritAppendPath", _location);
                scm.setChildScmDeveloperConnectionInheritAppendPath(this.interpolatedTrimmed(value, "child.scm.developerConnection.inherit.append.path"));
                continue;
            }
            if ("child.scm.url.inherit.append.path".equals(name)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                scm.setLocation("childScmUrlInheritAppendPath", _location);
                scm.setChildScmUrlInheritAppendPath(this.interpolatedTrimmed(value, "child.scm.url.inherit.append.path"));
                continue;
            }
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "connection", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                scm.setLocation("connection", _location);
                scm.setConnection(this.interpolatedTrimmed(parser.nextText(), "connection"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "developerConnection", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                scm.setLocation("developerConnection", _location);
                scm.setDeveloperConnection(this.interpolatedTrimmed(parser.nextText(), "developerConnection"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "tag", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                scm.setLocation("tag", _location);
                scm.setTag(this.interpolatedTrimmed(parser.nextText(), "tag"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                scm.setLocation("url", _location);
                scm.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return scm;
    }

    private Site parseSite(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        String tagName = parser.getName();
        Site site = new Site();
        InputLocation _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
        site.setLocation("", _location);
        for (int i = parser.getAttributeCount() - 1; i >= 0; --i) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (name.indexOf(58) >= 0) continue;
            if ("child.site.url.inherit.append.path".equals(name)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                site.setLocation("childSiteUrlInheritAppendPath", _location);
                site.setChildSiteUrlInheritAppendPath(this.interpolatedTrimmed(value, "child.site.url.inherit.append.path"));
                continue;
            }
            this.checkUnknownAttribute(parser, name, tagName, strict);
        }
        HashSet parsed = new HashSet();
        while ((strict ? parser.nextTag() : this.nextTag(parser)) == 2) {
            if (this.checkFieldWithDuplicate(parser, "id", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                site.setLocation("id", _location);
                site.setId(this.interpolatedTrimmed(parser.nextText(), "id"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "name", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                site.setLocation("name", _location);
                site.setName(this.interpolatedTrimmed(parser.nextText(), "name"));
                continue;
            }
            if (this.checkFieldWithDuplicate(parser, "url", null, parsed)) {
                _location = new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), source2);
                site.setLocation("url", _location);
                site.setUrl(this.interpolatedTrimmed(parser.nextText(), "url"));
                continue;
            }
            this.checkUnknownElement(parser, strict);
        }
        return site;
    }

    private Model read(XmlPullParser parser, boolean strict, InputSource source2) throws IOException, XmlPullParserException {
        Model model = null;
        int eventType = parser.getEventType();
        boolean parsed = false;
        while (eventType != 1) {
            if (eventType == 2) {
                if (strict && !"project".equals(parser.getName())) {
                    throw new XmlPullParserException("Expected root element 'project' but found '" + parser.getName() + "'", parser, null);
                }
                if (parsed) {
                    throw new XmlPullParserException("Duplicated tag: 'project'", parser, null);
                }
                model = this.parseModel(parser, strict, source2);
                model.setModelEncoding(parser.getInputEncoding());
                parsed = true;
            }
            eventType = parser.next();
        }
        if (parsed) {
            return model;
        }
        throw new XmlPullParserException("Expected root element 'project' but found no element at all: invalid XML document", parser, null);
    }

    public void setAddDefaultEntities(boolean addDefaultEntities) {
        this.addDefaultEntities = addDefaultEntities;
    }

    public static interface ContentTransformer {
        public String transform(String var1, String var2);
    }

    private static class Xpp3DomBuilderInputLocationBuilder
    implements Xpp3DomBuilder.InputLocationBuilder {
        private final InputLocation rootLocation;

        public Xpp3DomBuilderInputLocationBuilder(InputLocation rootLocation) {
            this.rootLocation = rootLocation;
        }

        @Override
        public Object toInputLocation(XmlPullParser parser) {
            return new InputLocation(parser.getLineNumber(), parser.getColumnNumber(), this.rootLocation.getSource());
        }
    }
}

