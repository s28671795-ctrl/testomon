/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.io.xpp3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
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
import org.apache.maven.model.InputLocationTracker;
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
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.MXSerializer;
import org.codehaus.plexus.util.xml.pull.XmlSerializer;

public class MavenXpp3WriterEx {
    private static final String NAMESPACE = null;
    private String fileComment = null;
    protected InputLocation.StringFormatter stringFormatter;

    public void setFileComment(String fileComment) {
        this.fileComment = fileComment;
    }

    public void setStringFormatter(InputLocation.StringFormatter stringFormatter) {
        this.stringFormatter = stringFormatter;
    }

    protected String toString(InputLocation location) {
        if (this.stringFormatter != null) {
            return this.stringFormatter.toString(location);
        }
        return ' ' + location.getSource().toString() + ':' + location.getLineNumber() + ' ';
    }

    public void write(Writer writer, Model model) throws IOException {
        MXSerializer serializer = new MXSerializer();
        serializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-indentation", "  ");
        serializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-line-separator", "\n");
        serializer.setOutput(writer);
        serializer.startDocument(model.getModelEncoding(), null);
        this.writeModel(model, "project", serializer);
        serializer.endDocument();
    }

    public void write(OutputStream stream, Model model) throws IOException {
        MXSerializer serializer = new MXSerializer();
        serializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-indentation", "  ");
        serializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-line-separator", "\n");
        serializer.setOutput(stream, model.getModelEncoding());
        serializer.startDocument(model.getModelEncoding(), null);
        this.writeModel(model, "project", serializer);
        serializer.endDocument();
    }

    protected void writeXpp3DomToSerializer(Xpp3Dom dom, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, dom.getName());
        String[] attributeNames = dom.getAttributeNames();
        for (String attributeName : attributeNames) {
            serializer.attribute(NAMESPACE, attributeName, dom.getAttribute(attributeName));
        }
        for (Xpp3Dom aChild : dom.getChildren()) {
            this.writeXpp3DomToSerializer(aChild, serializer);
        }
        String value = dom.getValue();
        if (value != null) {
            serializer.text(value);
        }
        serializer.endTag(NAMESPACE, dom.getName());
        if (dom.getInputLocation() != null && dom.getChildCount() == 0) {
            serializer.comment(this.toString((InputLocation)dom.getInputLocation()));
        }
    }

    private void writeActivation(Activation activation, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (activation.isActiveByDefault()) {
            serializer.startTag(NAMESPACE, "activeByDefault").text(String.valueOf(activation.isActiveByDefault())).endTag(NAMESPACE, "activeByDefault");
            this.writeLocationTracking(activation, "activeByDefault", serializer);
        }
        if (activation.getJdk() != null) {
            serializer.startTag(NAMESPACE, "jdk").text(activation.getJdk()).endTag(NAMESPACE, "jdk");
            this.writeLocationTracking(activation, "jdk", serializer);
        }
        if (activation.getOs() != null) {
            this.writeActivationOS(activation.getOs(), "os", serializer);
        }
        if (activation.getProperty() != null) {
            this.writeActivationProperty(activation.getProperty(), "property", serializer);
        }
        if (activation.getFile() != null) {
            this.writeActivationFile(activation.getFile(), "file", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeActivationFile(ActivationFile activationFile, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (activationFile.getMissing() != null) {
            serializer.startTag(NAMESPACE, "missing").text(activationFile.getMissing()).endTag(NAMESPACE, "missing");
            this.writeLocationTracking(activationFile, "missing", serializer);
        }
        if (activationFile.getExists() != null) {
            serializer.startTag(NAMESPACE, "exists").text(activationFile.getExists()).endTag(NAMESPACE, "exists");
            this.writeLocationTracking(activationFile, "exists", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeActivationOS(ActivationOS activationOS, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (activationOS.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(activationOS.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(activationOS, "name", serializer);
        }
        if (activationOS.getFamily() != null) {
            serializer.startTag(NAMESPACE, "family").text(activationOS.getFamily()).endTag(NAMESPACE, "family");
            this.writeLocationTracking(activationOS, "family", serializer);
        }
        if (activationOS.getArch() != null) {
            serializer.startTag(NAMESPACE, "arch").text(activationOS.getArch()).endTag(NAMESPACE, "arch");
            this.writeLocationTracking(activationOS, "arch", serializer);
        }
        if (activationOS.getVersion() != null) {
            serializer.startTag(NAMESPACE, "version").text(activationOS.getVersion()).endTag(NAMESPACE, "version");
            this.writeLocationTracking(activationOS, "version", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeActivationProperty(ActivationProperty activationProperty, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (activationProperty.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(activationProperty.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(activationProperty, "name", serializer);
        }
        if (activationProperty.getValue() != null) {
            serializer.startTag(NAMESPACE, "value").text(activationProperty.getValue()).endTag(NAMESPACE, "value");
            this.writeLocationTracking(activationProperty, "value", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    /*
     * WARNING - void declaration
     */
    private void writeBuild(Build build, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (build.getSourceDirectory() != null) {
            serializer.startTag(NAMESPACE, "sourceDirectory").text(build.getSourceDirectory()).endTag(NAMESPACE, "sourceDirectory");
            this.writeLocationTracking(build, "sourceDirectory", serializer);
        }
        if (build.getScriptSourceDirectory() != null) {
            serializer.startTag(NAMESPACE, "scriptSourceDirectory").text(build.getScriptSourceDirectory()).endTag(NAMESPACE, "scriptSourceDirectory");
            this.writeLocationTracking(build, "scriptSourceDirectory", serializer);
        }
        if (build.getTestSourceDirectory() != null) {
            serializer.startTag(NAMESPACE, "testSourceDirectory").text(build.getTestSourceDirectory()).endTag(NAMESPACE, "testSourceDirectory");
            this.writeLocationTracking(build, "testSourceDirectory", serializer);
        }
        if (build.getOutputDirectory() != null) {
            serializer.startTag(NAMESPACE, "outputDirectory").text(build.getOutputDirectory()).endTag(NAMESPACE, "outputDirectory");
            this.writeLocationTracking(build, "outputDirectory", serializer);
        }
        if (build.getTestOutputDirectory() != null) {
            serializer.startTag(NAMESPACE, "testOutputDirectory").text(build.getTestOutputDirectory()).endTag(NAMESPACE, "testOutputDirectory");
            this.writeLocationTracking(build, "testOutputDirectory", serializer);
        }
        if (build.getExtensions() != null && build.getExtensions().size() > 0) {
            serializer.startTag(NAMESPACE, "extensions");
            for (Extension extension : build.getExtensions()) {
                this.writeExtension(extension, "extension", serializer);
            }
            serializer.endTag(NAMESPACE, "extensions");
        }
        if (build.getDefaultGoal() != null) {
            serializer.startTag(NAMESPACE, "defaultGoal").text(build.getDefaultGoal()).endTag(NAMESPACE, "defaultGoal");
            this.writeLocationTracking(build, "defaultGoal", serializer);
        }
        if (build.getResources() != null && build.getResources().size() > 0) {
            serializer.startTag(NAMESPACE, "resources");
            for (Resource resource : build.getResources()) {
                this.writeResource(resource, "resource", serializer);
            }
            serializer.endTag(NAMESPACE, "resources");
        }
        if (build.getTestResources() != null && build.getTestResources().size() > 0) {
            serializer.startTag(NAMESPACE, "testResources");
            for (Resource resource : build.getTestResources()) {
                this.writeResource(resource, "testResource", serializer);
            }
            serializer.endTag(NAMESPACE, "testResources");
        }
        if (build.getDirectory() != null) {
            serializer.startTag(NAMESPACE, "directory").text(build.getDirectory()).endTag(NAMESPACE, "directory");
            this.writeLocationTracking(build, "directory", serializer);
        }
        if (build.getFinalName() != null) {
            serializer.startTag(NAMESPACE, "finalName").text(build.getFinalName()).endTag(NAMESPACE, "finalName");
            this.writeLocationTracking(build, "finalName", serializer);
        }
        if (build.getFilters() != null && build.getFilters().size() > 0) {
            serializer.startTag(NAMESPACE, "filters");
            InputLocation location = build.getLocation("filters");
            boolean bl = false;
            for (String filter2 : build.getFilters()) {
                void var5_11;
                serializer.startTag(NAMESPACE, "filter").text(filter2).endTag(NAMESPACE, "filter");
                this.writeLocationTracking(location, (int)(++var5_11), serializer);
            }
            serializer.endTag(NAMESPACE, "filters");
        }
        if (build.getPluginManagement() != null) {
            this.writePluginManagement(build.getPluginManagement(), "pluginManagement", serializer);
        }
        if (build.getPlugins() != null && build.getPlugins().size() > 0) {
            serializer.startTag(NAMESPACE, "plugins");
            for (Plugin plugin : build.getPlugins()) {
                this.writePlugin(plugin, "plugin", serializer);
            }
            serializer.endTag(NAMESPACE, "plugins");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeBuildBase(BuildBase buildBase, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (buildBase.getDefaultGoal() != null) {
            serializer.startTag(NAMESPACE, "defaultGoal").text(buildBase.getDefaultGoal()).endTag(NAMESPACE, "defaultGoal");
            this.writeLocationTracking(buildBase, "defaultGoal", serializer);
        }
        if (buildBase.getResources() != null && buildBase.getResources().size() > 0) {
            serializer.startTag(NAMESPACE, "resources");
            for (Resource o : buildBase.getResources()) {
                this.writeResource(o, "resource", serializer);
            }
            serializer.endTag(NAMESPACE, "resources");
        }
        if (buildBase.getTestResources() != null && buildBase.getTestResources().size() > 0) {
            serializer.startTag(NAMESPACE, "testResources");
            for (Resource o : buildBase.getTestResources()) {
                this.writeResource(o, "testResource", serializer);
            }
            serializer.endTag(NAMESPACE, "testResources");
        }
        if (buildBase.getDirectory() != null) {
            serializer.startTag(NAMESPACE, "directory").text(buildBase.getDirectory()).endTag(NAMESPACE, "directory");
            this.writeLocationTracking(buildBase, "directory", serializer);
        }
        if (buildBase.getFinalName() != null) {
            serializer.startTag(NAMESPACE, "finalName").text(buildBase.getFinalName()).endTag(NAMESPACE, "finalName");
            this.writeLocationTracking(buildBase, "finalName", serializer);
        }
        if (buildBase.getFilters() != null && buildBase.getFilters().size() > 0) {
            serializer.startTag(NAMESPACE, "filters");
            InputLocation location = buildBase.getLocation("filters");
            int n = 0;
            for (String filter2 : buildBase.getFilters()) {
                serializer.startTag(NAMESPACE, "filter").text(filter2).endTag(NAMESPACE, "filter");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "filters");
        }
        if (buildBase.getPluginManagement() != null) {
            this.writePluginManagement(buildBase.getPluginManagement(), "pluginManagement", serializer);
        }
        if (buildBase.getPlugins() != null && buildBase.getPlugins().size() > 0) {
            serializer.startTag(NAMESPACE, "plugins");
            for (Plugin o : buildBase.getPlugins()) {
                this.writePlugin(o, "plugin", serializer);
            }
            serializer.endTag(NAMESPACE, "plugins");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeCiManagement(CiManagement ciManagement, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (ciManagement.getSystem() != null) {
            serializer.startTag(NAMESPACE, "system").text(ciManagement.getSystem()).endTag(NAMESPACE, "system");
            this.writeLocationTracking(ciManagement, "system", serializer);
        }
        if (ciManagement.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(ciManagement.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(ciManagement, "url", serializer);
        }
        if (ciManagement.getNotifiers() != null && ciManagement.getNotifiers().size() > 0) {
            serializer.startTag(NAMESPACE, "notifiers");
            for (Notifier o : ciManagement.getNotifiers()) {
                this.writeNotifier(o, "notifier", serializer);
            }
            serializer.endTag(NAMESPACE, "notifiers");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeConfigurationContainer(ConfigurationContainer configurationContainer, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (configurationContainer.getInherited() != null) {
            serializer.startTag(NAMESPACE, "inherited").text(configurationContainer.getInherited()).endTag(NAMESPACE, "inherited");
            this.writeLocationTracking(configurationContainer, "inherited", serializer);
        }
        if (configurationContainer.getConfiguration() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)configurationContainer.getConfiguration(), serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeContributor(Contributor contributor, String tagName, XmlSerializer serializer) throws IOException {
        InputLocation location;
        serializer.startTag(NAMESPACE, tagName);
        if (contributor.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(contributor.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(contributor, "name", serializer);
        }
        if (contributor.getEmail() != null) {
            serializer.startTag(NAMESPACE, "email").text(contributor.getEmail()).endTag(NAMESPACE, "email");
            this.writeLocationTracking(contributor, "email", serializer);
        }
        if (contributor.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(contributor.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(contributor, "url", serializer);
        }
        if (contributor.getOrganization() != null) {
            serializer.startTag(NAMESPACE, "organization").text(contributor.getOrganization()).endTag(NAMESPACE, "organization");
            this.writeLocationTracking(contributor, "organization", serializer);
        }
        if (contributor.getOrganizationUrl() != null) {
            serializer.startTag(NAMESPACE, "organizationUrl").text(contributor.getOrganizationUrl()).endTag(NAMESPACE, "organizationUrl");
            this.writeLocationTracking(contributor, "organizationUrl", serializer);
        }
        if (contributor.getRoles() != null && contributor.getRoles().size() > 0) {
            serializer.startTag(NAMESPACE, "roles");
            location = contributor.getLocation("roles");
            int n = 0;
            for (String role : contributor.getRoles()) {
                serializer.startTag(NAMESPACE, "role").text(role).endTag(NAMESPACE, "role");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "roles");
        }
        if (contributor.getTimezone() != null) {
            serializer.startTag(NAMESPACE, "timezone").text(contributor.getTimezone()).endTag(NAMESPACE, "timezone");
            this.writeLocationTracking(contributor, "timezone", serializer);
        }
        if (contributor.getProperties() != null && contributor.getProperties().size() > 0) {
            serializer.startTag(NAMESPACE, "properties");
            location = contributor.getLocation("properties");
            for (String string : contributor.getProperties().keySet()) {
                String value = (String)contributor.getProperties().get(string);
                serializer.startTag(NAMESPACE, string).text(value).endTag(NAMESPACE, string);
                this.writeLocationTracking(location, string, serializer);
            }
            serializer.endTag(NAMESPACE, "properties");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeDependency(Dependency dependency, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (dependency.getGroupId() != null) {
            serializer.startTag(NAMESPACE, "groupId").text(dependency.getGroupId()).endTag(NAMESPACE, "groupId");
            this.writeLocationTracking(dependency, "groupId", serializer);
        }
        if (dependency.getArtifactId() != null) {
            serializer.startTag(NAMESPACE, "artifactId").text(dependency.getArtifactId()).endTag(NAMESPACE, "artifactId");
            this.writeLocationTracking(dependency, "artifactId", serializer);
        }
        if (dependency.getVersion() != null) {
            serializer.startTag(NAMESPACE, "version").text(dependency.getVersion()).endTag(NAMESPACE, "version");
            this.writeLocationTracking(dependency, "version", serializer);
        }
        if (dependency.getType() != null && !dependency.getType().equals("jar")) {
            serializer.startTag(NAMESPACE, "type").text(dependency.getType()).endTag(NAMESPACE, "type");
            this.writeLocationTracking(dependency, "type", serializer);
        }
        if (dependency.getClassifier() != null) {
            serializer.startTag(NAMESPACE, "classifier").text(dependency.getClassifier()).endTag(NAMESPACE, "classifier");
            this.writeLocationTracking(dependency, "classifier", serializer);
        }
        if (dependency.getScope() != null) {
            serializer.startTag(NAMESPACE, "scope").text(dependency.getScope()).endTag(NAMESPACE, "scope");
            this.writeLocationTracking(dependency, "scope", serializer);
        }
        if (dependency.getSystemPath() != null) {
            serializer.startTag(NAMESPACE, "systemPath").text(dependency.getSystemPath()).endTag(NAMESPACE, "systemPath");
            this.writeLocationTracking(dependency, "systemPath", serializer);
        }
        if (dependency.getExclusions() != null && dependency.getExclusions().size() > 0) {
            serializer.startTag(NAMESPACE, "exclusions");
            for (Exclusion o : dependency.getExclusions()) {
                this.writeExclusion(o, "exclusion", serializer);
            }
            serializer.endTag(NAMESPACE, "exclusions");
        }
        if (dependency.getOptional() != null) {
            serializer.startTag(NAMESPACE, "optional").text(dependency.getOptional()).endTag(NAMESPACE, "optional");
            this.writeLocationTracking(dependency, "optional", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeDependencyManagement(DependencyManagement dependencyManagement, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (dependencyManagement.getDependencies() != null && dependencyManagement.getDependencies().size() > 0) {
            serializer.startTag(NAMESPACE, "dependencies");
            for (Dependency o : dependencyManagement.getDependencies()) {
                this.writeDependency(o, "dependency", serializer);
            }
            serializer.endTag(NAMESPACE, "dependencies");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeDeploymentRepository(DeploymentRepository deploymentRepository, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (!deploymentRepository.isUniqueVersion()) {
            serializer.startTag(NAMESPACE, "uniqueVersion").text(String.valueOf(deploymentRepository.isUniqueVersion())).endTag(NAMESPACE, "uniqueVersion");
            this.writeLocationTracking(deploymentRepository, "uniqueVersion", serializer);
        }
        if (deploymentRepository.getReleases() != null) {
            this.writeRepositoryPolicy(deploymentRepository.getReleases(), "releases", serializer);
        }
        if (deploymentRepository.getSnapshots() != null) {
            this.writeRepositoryPolicy(deploymentRepository.getSnapshots(), "snapshots", serializer);
        }
        if (deploymentRepository.getId() != null) {
            serializer.startTag(NAMESPACE, "id").text(deploymentRepository.getId()).endTag(NAMESPACE, "id");
            this.writeLocationTracking(deploymentRepository, "id", serializer);
        }
        if (deploymentRepository.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(deploymentRepository.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(deploymentRepository, "name", serializer);
        }
        if (deploymentRepository.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(deploymentRepository.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(deploymentRepository, "url", serializer);
        }
        if (deploymentRepository.getLayout() != null && !deploymentRepository.getLayout().equals("default")) {
            serializer.startTag(NAMESPACE, "layout").text(deploymentRepository.getLayout()).endTag(NAMESPACE, "layout");
            this.writeLocationTracking(deploymentRepository, "layout", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeDeveloper(Developer developer, String tagName, XmlSerializer serializer) throws IOException {
        InputLocation location;
        serializer.startTag(NAMESPACE, tagName);
        if (developer.getId() != null) {
            serializer.startTag(NAMESPACE, "id").text(developer.getId()).endTag(NAMESPACE, "id");
            this.writeLocationTracking(developer, "id", serializer);
        }
        if (developer.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(developer.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(developer, "name", serializer);
        }
        if (developer.getEmail() != null) {
            serializer.startTag(NAMESPACE, "email").text(developer.getEmail()).endTag(NAMESPACE, "email");
            this.writeLocationTracking(developer, "email", serializer);
        }
        if (developer.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(developer.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(developer, "url", serializer);
        }
        if (developer.getOrganization() != null) {
            serializer.startTag(NAMESPACE, "organization").text(developer.getOrganization()).endTag(NAMESPACE, "organization");
            this.writeLocationTracking(developer, "organization", serializer);
        }
        if (developer.getOrganizationUrl() != null) {
            serializer.startTag(NAMESPACE, "organizationUrl").text(developer.getOrganizationUrl()).endTag(NAMESPACE, "organizationUrl");
            this.writeLocationTracking(developer, "organizationUrl", serializer);
        }
        if (developer.getRoles() != null && developer.getRoles().size() > 0) {
            serializer.startTag(NAMESPACE, "roles");
            location = developer.getLocation("roles");
            int n = 0;
            for (String role : developer.getRoles()) {
                serializer.startTag(NAMESPACE, "role").text(role).endTag(NAMESPACE, "role");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "roles");
        }
        if (developer.getTimezone() != null) {
            serializer.startTag(NAMESPACE, "timezone").text(developer.getTimezone()).endTag(NAMESPACE, "timezone");
            this.writeLocationTracking(developer, "timezone", serializer);
        }
        if (developer.getProperties() != null && developer.getProperties().size() > 0) {
            serializer.startTag(NAMESPACE, "properties");
            location = developer.getLocation("properties");
            for (String string : developer.getProperties().keySet()) {
                String value = (String)developer.getProperties().get(string);
                serializer.startTag(NAMESPACE, string).text(value).endTag(NAMESPACE, string);
                this.writeLocationTracking(location, string, serializer);
            }
            serializer.endTag(NAMESPACE, "properties");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeDistributionManagement(DistributionManagement distributionManagement, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (distributionManagement.getRepository() != null) {
            this.writeDeploymentRepository(distributionManagement.getRepository(), "repository", serializer);
        }
        if (distributionManagement.getSnapshotRepository() != null) {
            this.writeDeploymentRepository(distributionManagement.getSnapshotRepository(), "snapshotRepository", serializer);
        }
        if (distributionManagement.getSite() != null) {
            this.writeSite(distributionManagement.getSite(), "site", serializer);
        }
        if (distributionManagement.getDownloadUrl() != null) {
            serializer.startTag(NAMESPACE, "downloadUrl").text(distributionManagement.getDownloadUrl()).endTag(NAMESPACE, "downloadUrl");
            this.writeLocationTracking(distributionManagement, "downloadUrl", serializer);
        }
        if (distributionManagement.getRelocation() != null) {
            this.writeRelocation(distributionManagement.getRelocation(), "relocation", serializer);
        }
        if (distributionManagement.getStatus() != null) {
            serializer.startTag(NAMESPACE, "status").text(distributionManagement.getStatus()).endTag(NAMESPACE, "status");
            this.writeLocationTracking(distributionManagement, "status", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeExclusion(Exclusion exclusion, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (exclusion.getGroupId() != null) {
            serializer.startTag(NAMESPACE, "groupId").text(exclusion.getGroupId()).endTag(NAMESPACE, "groupId");
            this.writeLocationTracking(exclusion, "groupId", serializer);
        }
        if (exclusion.getArtifactId() != null) {
            serializer.startTag(NAMESPACE, "artifactId").text(exclusion.getArtifactId()).endTag(NAMESPACE, "artifactId");
            this.writeLocationTracking(exclusion, "artifactId", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeExtension(Extension extension, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (extension.getGroupId() != null) {
            serializer.startTag(NAMESPACE, "groupId").text(extension.getGroupId()).endTag(NAMESPACE, "groupId");
            this.writeLocationTracking(extension, "groupId", serializer);
        }
        if (extension.getArtifactId() != null) {
            serializer.startTag(NAMESPACE, "artifactId").text(extension.getArtifactId()).endTag(NAMESPACE, "artifactId");
            this.writeLocationTracking(extension, "artifactId", serializer);
        }
        if (extension.getVersion() != null) {
            serializer.startTag(NAMESPACE, "version").text(extension.getVersion()).endTag(NAMESPACE, "version");
            this.writeLocationTracking(extension, "version", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeFileSet(FileSet fileSet, String tagName, XmlSerializer serializer) throws IOException {
        int n;
        InputLocation location;
        serializer.startTag(NAMESPACE, tagName);
        if (fileSet.getDirectory() != null) {
            serializer.startTag(NAMESPACE, "directory").text(fileSet.getDirectory()).endTag(NAMESPACE, "directory");
            this.writeLocationTracking(fileSet, "directory", serializer);
        }
        if (fileSet.getIncludes() != null && fileSet.getIncludes().size() > 0) {
            serializer.startTag(NAMESPACE, "includes");
            location = fileSet.getLocation("includes");
            n = 0;
            for (String include : fileSet.getIncludes()) {
                serializer.startTag(NAMESPACE, "include").text(include).endTag(NAMESPACE, "include");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "includes");
        }
        if (fileSet.getExcludes() != null && fileSet.getExcludes().size() > 0) {
            serializer.startTag(NAMESPACE, "excludes");
            location = fileSet.getLocation("excludes");
            n = 0;
            for (String exclude : fileSet.getExcludes()) {
                serializer.startTag(NAMESPACE, "exclude").text(exclude).endTag(NAMESPACE, "exclude");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "excludes");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeIssueManagement(IssueManagement issueManagement, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (issueManagement.getSystem() != null) {
            serializer.startTag(NAMESPACE, "system").text(issueManagement.getSystem()).endTag(NAMESPACE, "system");
            this.writeLocationTracking(issueManagement, "system", serializer);
        }
        if (issueManagement.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(issueManagement.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(issueManagement, "url", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeLicense(License license, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (license.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(license.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(license, "name", serializer);
        }
        if (license.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(license.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(license, "url", serializer);
        }
        if (license.getDistribution() != null) {
            serializer.startTag(NAMESPACE, "distribution").text(license.getDistribution()).endTag(NAMESPACE, "distribution");
            this.writeLocationTracking(license, "distribution", serializer);
        }
        if (license.getComments() != null) {
            serializer.startTag(NAMESPACE, "comments").text(license.getComments()).endTag(NAMESPACE, "comments");
            this.writeLocationTracking(license, "comments", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeLocationTracking(InputLocationTracker locationTracker, Object key, XmlSerializer serializer) throws IOException {
        InputLocation location;
        InputLocation inputLocation = location = locationTracker == null ? null : locationTracker.getLocation(key);
        if (location != null) {
            serializer.comment(this.toString(location));
        }
    }

    private void writeMailingList(MailingList mailingList, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (mailingList.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(mailingList.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(mailingList, "name", serializer);
        }
        if (mailingList.getSubscribe() != null) {
            serializer.startTag(NAMESPACE, "subscribe").text(mailingList.getSubscribe()).endTag(NAMESPACE, "subscribe");
            this.writeLocationTracking(mailingList, "subscribe", serializer);
        }
        if (mailingList.getUnsubscribe() != null) {
            serializer.startTag(NAMESPACE, "unsubscribe").text(mailingList.getUnsubscribe()).endTag(NAMESPACE, "unsubscribe");
            this.writeLocationTracking(mailingList, "unsubscribe", serializer);
        }
        if (mailingList.getPost() != null) {
            serializer.startTag(NAMESPACE, "post").text(mailingList.getPost()).endTag(NAMESPACE, "post");
            this.writeLocationTracking(mailingList, "post", serializer);
        }
        if (mailingList.getArchive() != null) {
            serializer.startTag(NAMESPACE, "archive").text(mailingList.getArchive()).endTag(NAMESPACE, "archive");
            this.writeLocationTracking(mailingList, "archive", serializer);
        }
        if (mailingList.getOtherArchives() != null && mailingList.getOtherArchives().size() > 0) {
            serializer.startTag(NAMESPACE, "otherArchives");
            InputLocation location = mailingList.getLocation("otherArchives");
            int n = 0;
            for (String otherArchive : mailingList.getOtherArchives()) {
                serializer.startTag(NAMESPACE, "otherArchive").text(otherArchive).endTag(NAMESPACE, "otherArchive");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "otherArchives");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    /*
     * WARNING - void declaration
     */
    private void writeModel(Model model, String tagName, XmlSerializer serializer) throws IOException {
        InputLocation location;
        if (this.fileComment != null) {
            serializer.comment(this.fileComment);
        }
        serializer.setPrefix("", "http://maven.apache.org/POM/4.0.0");
        serializer.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        serializer.startTag(NAMESPACE, tagName);
        serializer.attribute("", "xsi:schemaLocation", "http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd");
        if (model.getChildProjectUrlInheritAppendPath() != null) {
            serializer.attribute(NAMESPACE, "child.project.url.inherit.append.path", model.getChildProjectUrlInheritAppendPath());
        }
        if (model.getModelVersion() != null) {
            serializer.startTag(NAMESPACE, "modelVersion").text(model.getModelVersion()).endTag(NAMESPACE, "modelVersion");
            this.writeLocationTracking(model, "modelVersion", serializer);
        }
        if (model.getParent() != null) {
            this.writeParent(model.getParent(), "parent", serializer);
        }
        if (model.getGroupId() != null) {
            serializer.startTag(NAMESPACE, "groupId").text(model.getGroupId()).endTag(NAMESPACE, "groupId");
            this.writeLocationTracking(model, "groupId", serializer);
        }
        if (model.getArtifactId() != null) {
            serializer.startTag(NAMESPACE, "artifactId").text(model.getArtifactId()).endTag(NAMESPACE, "artifactId");
            this.writeLocationTracking(model, "artifactId", serializer);
        }
        if (model.getVersion() != null) {
            serializer.startTag(NAMESPACE, "version").text(model.getVersion()).endTag(NAMESPACE, "version");
            this.writeLocationTracking(model, "version", serializer);
        }
        if (model.getPackaging() != null && !model.getPackaging().equals("jar")) {
            serializer.startTag(NAMESPACE, "packaging").text(model.getPackaging()).endTag(NAMESPACE, "packaging");
            this.writeLocationTracking(model, "packaging", serializer);
        }
        if (model.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(model.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(model, "name", serializer);
        }
        if (model.getDescription() != null) {
            serializer.startTag(NAMESPACE, "description").text(model.getDescription()).endTag(NAMESPACE, "description");
            this.writeLocationTracking(model, "description", serializer);
        }
        if (model.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(model.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(model, "url", serializer);
        }
        if (model.getInceptionYear() != null) {
            serializer.startTag(NAMESPACE, "inceptionYear").text(model.getInceptionYear()).endTag(NAMESPACE, "inceptionYear");
            this.writeLocationTracking(model, "inceptionYear", serializer);
        }
        if (model.getOrganization() != null) {
            this.writeOrganization(model.getOrganization(), "organization", serializer);
        }
        if (model.getLicenses() != null && model.getLicenses().size() > 0) {
            serializer.startTag(NAMESPACE, "licenses");
            for (License license : model.getLicenses()) {
                this.writeLicense(license, "license", serializer);
            }
            serializer.endTag(NAMESPACE, "licenses");
        }
        if (model.getDevelopers() != null && model.getDevelopers().size() > 0) {
            serializer.startTag(NAMESPACE, "developers");
            for (Developer developer : model.getDevelopers()) {
                this.writeDeveloper(developer, "developer", serializer);
            }
            serializer.endTag(NAMESPACE, "developers");
        }
        if (model.getContributors() != null && model.getContributors().size() > 0) {
            serializer.startTag(NAMESPACE, "contributors");
            for (Contributor contributor : model.getContributors()) {
                this.writeContributor(contributor, "contributor", serializer);
            }
            serializer.endTag(NAMESPACE, "contributors");
        }
        if (model.getMailingLists() != null && model.getMailingLists().size() > 0) {
            serializer.startTag(NAMESPACE, "mailingLists");
            for (MailingList mailingList : model.getMailingLists()) {
                this.writeMailingList(mailingList, "mailingList", serializer);
            }
            serializer.endTag(NAMESPACE, "mailingLists");
        }
        if (model.getPrerequisites() != null) {
            this.writePrerequisites(model.getPrerequisites(), "prerequisites", serializer);
        }
        if (model.getModules() != null && model.getModules().size() > 0) {
            serializer.startTag(NAMESPACE, "modules");
            location = model.getLocation("modules");
            boolean bl = false;
            for (String module : model.getModules()) {
                void var5_13;
                serializer.startTag(NAMESPACE, "module").text(module).endTag(NAMESPACE, "module");
                this.writeLocationTracking(location, (int)(++var5_13), serializer);
            }
            serializer.endTag(NAMESPACE, "modules");
        }
        if (model.getScm() != null) {
            this.writeScm(model.getScm(), "scm", serializer);
        }
        if (model.getIssueManagement() != null) {
            this.writeIssueManagement(model.getIssueManagement(), "issueManagement", serializer);
        }
        if (model.getCiManagement() != null) {
            this.writeCiManagement(model.getCiManagement(), "ciManagement", serializer);
        }
        if (model.getDistributionManagement() != null) {
            this.writeDistributionManagement(model.getDistributionManagement(), "distributionManagement", serializer);
        }
        if (model.getProperties() != null && model.getProperties().size() > 0) {
            serializer.startTag(NAMESPACE, "properties");
            location = model.getLocation("properties");
            for (String string : model.getProperties().keySet()) {
                String value = (String)model.getProperties().get(string);
                serializer.startTag(NAMESPACE, string).text(value).endTag(NAMESPACE, string);
                this.writeLocationTracking(location, string, serializer);
            }
            serializer.endTag(NAMESPACE, "properties");
        }
        if (model.getDependencyManagement() != null) {
            this.writeDependencyManagement(model.getDependencyManagement(), "dependencyManagement", serializer);
        }
        if (model.getDependencies() != null && model.getDependencies().size() > 0) {
            serializer.startTag(NAMESPACE, "dependencies");
            for (Dependency dependency : model.getDependencies()) {
                this.writeDependency(dependency, "dependency", serializer);
            }
            serializer.endTag(NAMESPACE, "dependencies");
        }
        if (model.getRepositories() != null && model.getRepositories().size() > 0) {
            serializer.startTag(NAMESPACE, "repositories");
            for (Repository repository : model.getRepositories()) {
                this.writeRepository(repository, "repository", serializer);
            }
            serializer.endTag(NAMESPACE, "repositories");
        }
        if (model.getPluginRepositories() != null && model.getPluginRepositories().size() > 0) {
            serializer.startTag(NAMESPACE, "pluginRepositories");
            for (Repository repository : model.getPluginRepositories()) {
                this.writeRepository(repository, "pluginRepository", serializer);
            }
            serializer.endTag(NAMESPACE, "pluginRepositories");
        }
        if (model.getBuild() != null) {
            this.writeBuild(model.getBuild(), "build", serializer);
        }
        if (model.getReports() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)model.getReports(), serializer);
        }
        if (model.getReporting() != null) {
            this.writeReporting(model.getReporting(), "reporting", serializer);
        }
        if (model.getProfiles() != null && model.getProfiles().size() > 0) {
            serializer.startTag(NAMESPACE, "profiles");
            for (Profile profile : model.getProfiles()) {
                this.writeProfile(profile, "profile", serializer);
            }
            serializer.endTag(NAMESPACE, "profiles");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeModelBase(ModelBase modelBase, String tagName, XmlSerializer serializer) throws IOException {
        InputLocation location;
        serializer.startTag(NAMESPACE, tagName);
        if (modelBase.getModules() != null && modelBase.getModules().size() > 0) {
            serializer.startTag(NAMESPACE, "modules");
            location = modelBase.getLocation("modules");
            int n = 0;
            for (String module : modelBase.getModules()) {
                serializer.startTag(NAMESPACE, "module").text(module).endTag(NAMESPACE, "module");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "modules");
        }
        if (modelBase.getDistributionManagement() != null) {
            this.writeDistributionManagement(modelBase.getDistributionManagement(), "distributionManagement", serializer);
        }
        if (modelBase.getProperties() != null && modelBase.getProperties().size() > 0) {
            serializer.startTag(NAMESPACE, "properties");
            location = modelBase.getLocation("properties");
            for (String string : modelBase.getProperties().keySet()) {
                String value = (String)modelBase.getProperties().get(string);
                serializer.startTag(NAMESPACE, string).text(value).endTag(NAMESPACE, string);
                this.writeLocationTracking(location, string, serializer);
            }
            serializer.endTag(NAMESPACE, "properties");
        }
        if (modelBase.getDependencyManagement() != null) {
            this.writeDependencyManagement(modelBase.getDependencyManagement(), "dependencyManagement", serializer);
        }
        if (modelBase.getDependencies() != null && modelBase.getDependencies().size() > 0) {
            serializer.startTag(NAMESPACE, "dependencies");
            for (Dependency o : modelBase.getDependencies()) {
                this.writeDependency(o, "dependency", serializer);
            }
            serializer.endTag(NAMESPACE, "dependencies");
        }
        if (modelBase.getRepositories() != null && modelBase.getRepositories().size() > 0) {
            serializer.startTag(NAMESPACE, "repositories");
            for (Repository o : modelBase.getRepositories()) {
                this.writeRepository(o, "repository", serializer);
            }
            serializer.endTag(NAMESPACE, "repositories");
        }
        if (modelBase.getPluginRepositories() != null && modelBase.getPluginRepositories().size() > 0) {
            serializer.startTag(NAMESPACE, "pluginRepositories");
            for (Repository o : modelBase.getPluginRepositories()) {
                this.writeRepository(o, "pluginRepository", serializer);
            }
            serializer.endTag(NAMESPACE, "pluginRepositories");
        }
        if (modelBase.getReports() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)modelBase.getReports(), serializer);
        }
        if (modelBase.getReporting() != null) {
            this.writeReporting(modelBase.getReporting(), "reporting", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeNotifier(Notifier notifier, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (notifier.getType() != null && !notifier.getType().equals("mail")) {
            serializer.startTag(NAMESPACE, "type").text(notifier.getType()).endTag(NAMESPACE, "type");
            this.writeLocationTracking(notifier, "type", serializer);
        }
        if (!notifier.isSendOnError()) {
            serializer.startTag(NAMESPACE, "sendOnError").text(String.valueOf(notifier.isSendOnError())).endTag(NAMESPACE, "sendOnError");
            this.writeLocationTracking(notifier, "sendOnError", serializer);
        }
        if (!notifier.isSendOnFailure()) {
            serializer.startTag(NAMESPACE, "sendOnFailure").text(String.valueOf(notifier.isSendOnFailure())).endTag(NAMESPACE, "sendOnFailure");
            this.writeLocationTracking(notifier, "sendOnFailure", serializer);
        }
        if (!notifier.isSendOnSuccess()) {
            serializer.startTag(NAMESPACE, "sendOnSuccess").text(String.valueOf(notifier.isSendOnSuccess())).endTag(NAMESPACE, "sendOnSuccess");
            this.writeLocationTracking(notifier, "sendOnSuccess", serializer);
        }
        if (!notifier.isSendOnWarning()) {
            serializer.startTag(NAMESPACE, "sendOnWarning").text(String.valueOf(notifier.isSendOnWarning())).endTag(NAMESPACE, "sendOnWarning");
            this.writeLocationTracking(notifier, "sendOnWarning", serializer);
        }
        if (notifier.getAddress() != null) {
            serializer.startTag(NAMESPACE, "address").text(notifier.getAddress()).endTag(NAMESPACE, "address");
            this.writeLocationTracking(notifier, "address", serializer);
        }
        if (notifier.getConfiguration() != null && notifier.getConfiguration().size() > 0) {
            serializer.startTag(NAMESPACE, "configuration");
            InputLocation location = notifier.getLocation("configuration");
            for (String string : notifier.getConfiguration().keySet()) {
                String value = (String)notifier.getConfiguration().get(string);
                serializer.startTag(NAMESPACE, string).text(value).endTag(NAMESPACE, string);
                this.writeLocationTracking(location, string, serializer);
            }
            serializer.endTag(NAMESPACE, "configuration");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeOrganization(Organization organization, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (organization.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(organization.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(organization, "name", serializer);
        }
        if (organization.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(organization.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(organization, "url", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeParent(Parent parent, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (parent.getGroupId() != null) {
            serializer.startTag(NAMESPACE, "groupId").text(parent.getGroupId()).endTag(NAMESPACE, "groupId");
            this.writeLocationTracking(parent, "groupId", serializer);
        }
        if (parent.getArtifactId() != null) {
            serializer.startTag(NAMESPACE, "artifactId").text(parent.getArtifactId()).endTag(NAMESPACE, "artifactId");
            this.writeLocationTracking(parent, "artifactId", serializer);
        }
        if (parent.getVersion() != null) {
            serializer.startTag(NAMESPACE, "version").text(parent.getVersion()).endTag(NAMESPACE, "version");
            this.writeLocationTracking(parent, "version", serializer);
        }
        if (parent.getRelativePath() != null && !parent.getRelativePath().equals("../pom.xml")) {
            serializer.startTag(NAMESPACE, "relativePath").text(parent.getRelativePath()).endTag(NAMESPACE, "relativePath");
            this.writeLocationTracking(parent, "relativePath", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writePatternSet(PatternSet patternSet, String tagName, XmlSerializer serializer) throws IOException {
        int n;
        InputLocation location;
        serializer.startTag(NAMESPACE, tagName);
        if (patternSet.getIncludes() != null && patternSet.getIncludes().size() > 0) {
            serializer.startTag(NAMESPACE, "includes");
            location = patternSet.getLocation("includes");
            n = 0;
            for (String include : patternSet.getIncludes()) {
                serializer.startTag(NAMESPACE, "include").text(include).endTag(NAMESPACE, "include");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "includes");
        }
        if (patternSet.getExcludes() != null && patternSet.getExcludes().size() > 0) {
            serializer.startTag(NAMESPACE, "excludes");
            location = patternSet.getLocation("excludes");
            n = 0;
            for (String exclude : patternSet.getExcludes()) {
                serializer.startTag(NAMESPACE, "exclude").text(exclude).endTag(NAMESPACE, "exclude");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "excludes");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writePlugin(Plugin plugin, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (plugin.getGroupId() != null && !plugin.getGroupId().equals("org.apache.maven.plugins")) {
            serializer.startTag(NAMESPACE, "groupId").text(plugin.getGroupId()).endTag(NAMESPACE, "groupId");
            this.writeLocationTracking(plugin, "groupId", serializer);
        }
        if (plugin.getArtifactId() != null) {
            serializer.startTag(NAMESPACE, "artifactId").text(plugin.getArtifactId()).endTag(NAMESPACE, "artifactId");
            this.writeLocationTracking(plugin, "artifactId", serializer);
        }
        if (plugin.getVersion() != null) {
            serializer.startTag(NAMESPACE, "version").text(plugin.getVersion()).endTag(NAMESPACE, "version");
            this.writeLocationTracking(plugin, "version", serializer);
        }
        if (plugin.getExtensions() != null) {
            serializer.startTag(NAMESPACE, "extensions").text(plugin.getExtensions()).endTag(NAMESPACE, "extensions");
            this.writeLocationTracking(plugin, "extensions", serializer);
        }
        if (plugin.getExecutions() != null && plugin.getExecutions().size() > 0) {
            serializer.startTag(NAMESPACE, "executions");
            for (PluginExecution pluginExecution : plugin.getExecutions()) {
                this.writePluginExecution(pluginExecution, "execution", serializer);
            }
            serializer.endTag(NAMESPACE, "executions");
        }
        if (plugin.getDependencies() != null && plugin.getDependencies().size() > 0) {
            serializer.startTag(NAMESPACE, "dependencies");
            for (Dependency dependency : plugin.getDependencies()) {
                this.writeDependency(dependency, "dependency", serializer);
            }
            serializer.endTag(NAMESPACE, "dependencies");
        }
        if (plugin.getGoals() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)plugin.getGoals(), serializer);
        }
        if (plugin.getInherited() != null) {
            serializer.startTag(NAMESPACE, "inherited").text(plugin.getInherited()).endTag(NAMESPACE, "inherited");
            this.writeLocationTracking(plugin, "inherited", serializer);
        }
        if (plugin.getConfiguration() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)plugin.getConfiguration(), serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writePluginConfiguration(PluginConfiguration pluginConfiguration, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (pluginConfiguration.getPluginManagement() != null) {
            this.writePluginManagement(pluginConfiguration.getPluginManagement(), "pluginManagement", serializer);
        }
        if (pluginConfiguration.getPlugins() != null && pluginConfiguration.getPlugins().size() > 0) {
            serializer.startTag(NAMESPACE, "plugins");
            for (Plugin o : pluginConfiguration.getPlugins()) {
                this.writePlugin(o, "plugin", serializer);
            }
            serializer.endTag(NAMESPACE, "plugins");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writePluginContainer(PluginContainer pluginContainer, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (pluginContainer.getPlugins() != null && pluginContainer.getPlugins().size() > 0) {
            serializer.startTag(NAMESPACE, "plugins");
            for (Plugin o : pluginContainer.getPlugins()) {
                this.writePlugin(o, "plugin", serializer);
            }
            serializer.endTag(NAMESPACE, "plugins");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writePluginExecution(PluginExecution pluginExecution, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (pluginExecution.getId() != null && !pluginExecution.getId().equals("default")) {
            serializer.startTag(NAMESPACE, "id").text(pluginExecution.getId()).endTag(NAMESPACE, "id");
            this.writeLocationTracking(pluginExecution, "id", serializer);
        }
        if (pluginExecution.getPhase() != null) {
            serializer.startTag(NAMESPACE, "phase").text(pluginExecution.getPhase()).endTag(NAMESPACE, "phase");
            this.writeLocationTracking(pluginExecution, "phase", serializer);
        }
        if (pluginExecution.getGoals() != null && pluginExecution.getGoals().size() > 0) {
            serializer.startTag(NAMESPACE, "goals");
            InputLocation location = pluginExecution.getLocation("goals");
            int n = 0;
            for (String goal : pluginExecution.getGoals()) {
                serializer.startTag(NAMESPACE, "goal").text(goal).endTag(NAMESPACE, "goal");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "goals");
        }
        if (pluginExecution.getInherited() != null) {
            serializer.startTag(NAMESPACE, "inherited").text(pluginExecution.getInherited()).endTag(NAMESPACE, "inherited");
            this.writeLocationTracking(pluginExecution, "inherited", serializer);
        }
        if (pluginExecution.getConfiguration() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)pluginExecution.getConfiguration(), serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writePluginManagement(PluginManagement pluginManagement, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (pluginManagement.getPlugins() != null && pluginManagement.getPlugins().size() > 0) {
            serializer.startTag(NAMESPACE, "plugins");
            for (Plugin o : pluginManagement.getPlugins()) {
                this.writePlugin(o, "plugin", serializer);
            }
            serializer.endTag(NAMESPACE, "plugins");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writePrerequisites(Prerequisites prerequisites, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (prerequisites.getMaven() != null && !prerequisites.getMaven().equals("2.0")) {
            serializer.startTag(NAMESPACE, "maven").text(prerequisites.getMaven()).endTag(NAMESPACE, "maven");
            this.writeLocationTracking(prerequisites, "maven", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeProfile(Profile profile, String tagName, XmlSerializer serializer) throws IOException {
        InputLocation location;
        serializer.startTag(NAMESPACE, tagName);
        if (profile.getId() != null && !profile.getId().equals("default")) {
            serializer.startTag(NAMESPACE, "id").text(profile.getId()).endTag(NAMESPACE, "id");
            this.writeLocationTracking(profile, "id", serializer);
        }
        if (profile.getActivation() != null) {
            this.writeActivation(profile.getActivation(), "activation", serializer);
        }
        if (profile.getBuild() != null) {
            this.writeBuildBase(profile.getBuild(), "build", serializer);
        }
        if (profile.getModules() != null && profile.getModules().size() > 0) {
            serializer.startTag(NAMESPACE, "modules");
            location = profile.getLocation("modules");
            int n = 0;
            for (String module : profile.getModules()) {
                serializer.startTag(NAMESPACE, "module").text(module).endTag(NAMESPACE, "module");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "modules");
        }
        if (profile.getDistributionManagement() != null) {
            this.writeDistributionManagement(profile.getDistributionManagement(), "distributionManagement", serializer);
        }
        if (profile.getProperties() != null && profile.getProperties().size() > 0) {
            serializer.startTag(NAMESPACE, "properties");
            location = profile.getLocation("properties");
            for (String string : profile.getProperties().keySet()) {
                String value = (String)profile.getProperties().get(string);
                serializer.startTag(NAMESPACE, string).text(value).endTag(NAMESPACE, string);
                this.writeLocationTracking(location, string, serializer);
            }
            serializer.endTag(NAMESPACE, "properties");
        }
        if (profile.getDependencyManagement() != null) {
            this.writeDependencyManagement(profile.getDependencyManagement(), "dependencyManagement", serializer);
        }
        if (profile.getDependencies() != null && profile.getDependencies().size() > 0) {
            serializer.startTag(NAMESPACE, "dependencies");
            for (Dependency o : profile.getDependencies()) {
                this.writeDependency(o, "dependency", serializer);
            }
            serializer.endTag(NAMESPACE, "dependencies");
        }
        if (profile.getRepositories() != null && profile.getRepositories().size() > 0) {
            serializer.startTag(NAMESPACE, "repositories");
            for (Repository o : profile.getRepositories()) {
                this.writeRepository(o, "repository", serializer);
            }
            serializer.endTag(NAMESPACE, "repositories");
        }
        if (profile.getPluginRepositories() != null && profile.getPluginRepositories().size() > 0) {
            serializer.startTag(NAMESPACE, "pluginRepositories");
            for (Repository o : profile.getPluginRepositories()) {
                this.writeRepository(o, "pluginRepository", serializer);
            }
            serializer.endTag(NAMESPACE, "pluginRepositories");
        }
        if (profile.getReports() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)profile.getReports(), serializer);
        }
        if (profile.getReporting() != null) {
            this.writeReporting(profile.getReporting(), "reporting", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeRelocation(Relocation relocation, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (relocation.getGroupId() != null) {
            serializer.startTag(NAMESPACE, "groupId").text(relocation.getGroupId()).endTag(NAMESPACE, "groupId");
            this.writeLocationTracking(relocation, "groupId", serializer);
        }
        if (relocation.getArtifactId() != null) {
            serializer.startTag(NAMESPACE, "artifactId").text(relocation.getArtifactId()).endTag(NAMESPACE, "artifactId");
            this.writeLocationTracking(relocation, "artifactId", serializer);
        }
        if (relocation.getVersion() != null) {
            serializer.startTag(NAMESPACE, "version").text(relocation.getVersion()).endTag(NAMESPACE, "version");
            this.writeLocationTracking(relocation, "version", serializer);
        }
        if (relocation.getMessage() != null) {
            serializer.startTag(NAMESPACE, "message").text(relocation.getMessage()).endTag(NAMESPACE, "message");
            this.writeLocationTracking(relocation, "message", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeReportPlugin(ReportPlugin reportPlugin, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (reportPlugin.getGroupId() != null && !reportPlugin.getGroupId().equals("org.apache.maven.plugins")) {
            serializer.startTag(NAMESPACE, "groupId").text(reportPlugin.getGroupId()).endTag(NAMESPACE, "groupId");
            this.writeLocationTracking(reportPlugin, "groupId", serializer);
        }
        if (reportPlugin.getArtifactId() != null) {
            serializer.startTag(NAMESPACE, "artifactId").text(reportPlugin.getArtifactId()).endTag(NAMESPACE, "artifactId");
            this.writeLocationTracking(reportPlugin, "artifactId", serializer);
        }
        if (reportPlugin.getVersion() != null) {
            serializer.startTag(NAMESPACE, "version").text(reportPlugin.getVersion()).endTag(NAMESPACE, "version");
            this.writeLocationTracking(reportPlugin, "version", serializer);
        }
        if (reportPlugin.getReportSets() != null && reportPlugin.getReportSets().size() > 0) {
            serializer.startTag(NAMESPACE, "reportSets");
            for (ReportSet o : reportPlugin.getReportSets()) {
                this.writeReportSet(o, "reportSet", serializer);
            }
            serializer.endTag(NAMESPACE, "reportSets");
        }
        if (reportPlugin.getInherited() != null) {
            serializer.startTag(NAMESPACE, "inherited").text(reportPlugin.getInherited()).endTag(NAMESPACE, "inherited");
            this.writeLocationTracking(reportPlugin, "inherited", serializer);
        }
        if (reportPlugin.getConfiguration() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)reportPlugin.getConfiguration(), serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeReportSet(ReportSet reportSet, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (reportSet.getId() != null && !reportSet.getId().equals("default")) {
            serializer.startTag(NAMESPACE, "id").text(reportSet.getId()).endTag(NAMESPACE, "id");
            this.writeLocationTracking(reportSet, "id", serializer);
        }
        if (reportSet.getReports() != null && reportSet.getReports().size() > 0) {
            serializer.startTag(NAMESPACE, "reports");
            InputLocation location = reportSet.getLocation("reports");
            int n = 0;
            for (String report : reportSet.getReports()) {
                serializer.startTag(NAMESPACE, "report").text(report).endTag(NAMESPACE, "report");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "reports");
        }
        if (reportSet.getInherited() != null) {
            serializer.startTag(NAMESPACE, "inherited").text(reportSet.getInherited()).endTag(NAMESPACE, "inherited");
            this.writeLocationTracking(reportSet, "inherited", serializer);
        }
        if (reportSet.getConfiguration() != null) {
            this.writeXpp3DomToSerializer((Xpp3Dom)reportSet.getConfiguration(), serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeReporting(Reporting reporting, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (reporting.getExcludeDefaults() != null) {
            serializer.startTag(NAMESPACE, "excludeDefaults").text(reporting.getExcludeDefaults()).endTag(NAMESPACE, "excludeDefaults");
            this.writeLocationTracking(reporting, "excludeDefaults", serializer);
        }
        if (reporting.getOutputDirectory() != null) {
            serializer.startTag(NAMESPACE, "outputDirectory").text(reporting.getOutputDirectory()).endTag(NAMESPACE, "outputDirectory");
            this.writeLocationTracking(reporting, "outputDirectory", serializer);
        }
        if (reporting.getPlugins() != null && reporting.getPlugins().size() > 0) {
            serializer.startTag(NAMESPACE, "plugins");
            for (ReportPlugin o : reporting.getPlugins()) {
                this.writeReportPlugin(o, "plugin", serializer);
            }
            serializer.endTag(NAMESPACE, "plugins");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeRepository(Repository repository, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (repository.getReleases() != null) {
            this.writeRepositoryPolicy(repository.getReleases(), "releases", serializer);
        }
        if (repository.getSnapshots() != null) {
            this.writeRepositoryPolicy(repository.getSnapshots(), "snapshots", serializer);
        }
        if (repository.getId() != null) {
            serializer.startTag(NAMESPACE, "id").text(repository.getId()).endTag(NAMESPACE, "id");
            this.writeLocationTracking(repository, "id", serializer);
        }
        if (repository.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(repository.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(repository, "name", serializer);
        }
        if (repository.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(repository.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(repository, "url", serializer);
        }
        if (repository.getLayout() != null && !repository.getLayout().equals("default")) {
            serializer.startTag(NAMESPACE, "layout").text(repository.getLayout()).endTag(NAMESPACE, "layout");
            this.writeLocationTracking(repository, "layout", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeRepositoryBase(RepositoryBase repositoryBase, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (repositoryBase.getId() != null) {
            serializer.startTag(NAMESPACE, "id").text(repositoryBase.getId()).endTag(NAMESPACE, "id");
            this.writeLocationTracking(repositoryBase, "id", serializer);
        }
        if (repositoryBase.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(repositoryBase.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(repositoryBase, "name", serializer);
        }
        if (repositoryBase.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(repositoryBase.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(repositoryBase, "url", serializer);
        }
        if (repositoryBase.getLayout() != null && !repositoryBase.getLayout().equals("default")) {
            serializer.startTag(NAMESPACE, "layout").text(repositoryBase.getLayout()).endTag(NAMESPACE, "layout");
            this.writeLocationTracking(repositoryBase, "layout", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeRepositoryPolicy(RepositoryPolicy repositoryPolicy, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (repositoryPolicy.getEnabled() != null) {
            serializer.startTag(NAMESPACE, "enabled").text(repositoryPolicy.getEnabled()).endTag(NAMESPACE, "enabled");
            this.writeLocationTracking(repositoryPolicy, "enabled", serializer);
        }
        if (repositoryPolicy.getUpdatePolicy() != null) {
            serializer.startTag(NAMESPACE, "updatePolicy").text(repositoryPolicy.getUpdatePolicy()).endTag(NAMESPACE, "updatePolicy");
            this.writeLocationTracking(repositoryPolicy, "updatePolicy", serializer);
        }
        if (repositoryPolicy.getChecksumPolicy() != null) {
            serializer.startTag(NAMESPACE, "checksumPolicy").text(repositoryPolicy.getChecksumPolicy()).endTag(NAMESPACE, "checksumPolicy");
            this.writeLocationTracking(repositoryPolicy, "checksumPolicy", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeResource(Resource resource, String tagName, XmlSerializer serializer) throws IOException {
        int n;
        InputLocation location;
        serializer.startTag(NAMESPACE, tagName);
        if (resource.getTargetPath() != null) {
            serializer.startTag(NAMESPACE, "targetPath").text(resource.getTargetPath()).endTag(NAMESPACE, "targetPath");
            this.writeLocationTracking(resource, "targetPath", serializer);
        }
        if (resource.getFiltering() != null) {
            serializer.startTag(NAMESPACE, "filtering").text(resource.getFiltering()).endTag(NAMESPACE, "filtering");
            this.writeLocationTracking(resource, "filtering", serializer);
        }
        if (resource.getDirectory() != null) {
            serializer.startTag(NAMESPACE, "directory").text(resource.getDirectory()).endTag(NAMESPACE, "directory");
            this.writeLocationTracking(resource, "directory", serializer);
        }
        if (resource.getIncludes() != null && resource.getIncludes().size() > 0) {
            serializer.startTag(NAMESPACE, "includes");
            location = resource.getLocation("includes");
            n = 0;
            for (String include : resource.getIncludes()) {
                serializer.startTag(NAMESPACE, "include").text(include).endTag(NAMESPACE, "include");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "includes");
        }
        if (resource.getExcludes() != null && resource.getExcludes().size() > 0) {
            serializer.startTag(NAMESPACE, "excludes");
            location = resource.getLocation("excludes");
            n = 0;
            for (String exclude : resource.getExcludes()) {
                serializer.startTag(NAMESPACE, "exclude").text(exclude).endTag(NAMESPACE, "exclude");
                this.writeLocationTracking(location, n++, serializer);
            }
            serializer.endTag(NAMESPACE, "excludes");
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeScm(Scm scm, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (scm.getChildScmConnectionInheritAppendPath() != null) {
            serializer.attribute(NAMESPACE, "child.scm.connection.inherit.append.path", scm.getChildScmConnectionInheritAppendPath());
        }
        if (scm.getChildScmDeveloperConnectionInheritAppendPath() != null) {
            serializer.attribute(NAMESPACE, "child.scm.developerConnection.inherit.append.path", scm.getChildScmDeveloperConnectionInheritAppendPath());
        }
        if (scm.getChildScmUrlInheritAppendPath() != null) {
            serializer.attribute(NAMESPACE, "child.scm.url.inherit.append.path", scm.getChildScmUrlInheritAppendPath());
        }
        if (scm.getConnection() != null) {
            serializer.startTag(NAMESPACE, "connection").text(scm.getConnection()).endTag(NAMESPACE, "connection");
            this.writeLocationTracking(scm, "connection", serializer);
        }
        if (scm.getDeveloperConnection() != null) {
            serializer.startTag(NAMESPACE, "developerConnection").text(scm.getDeveloperConnection()).endTag(NAMESPACE, "developerConnection");
            this.writeLocationTracking(scm, "developerConnection", serializer);
        }
        if (scm.getTag() != null && !scm.getTag().equals("HEAD")) {
            serializer.startTag(NAMESPACE, "tag").text(scm.getTag()).endTag(NAMESPACE, "tag");
            this.writeLocationTracking(scm, "tag", serializer);
        }
        if (scm.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(scm.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(scm, "url", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }

    private void writeSite(Site site, String tagName, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE, tagName);
        if (site.getChildSiteUrlInheritAppendPath() != null) {
            serializer.attribute(NAMESPACE, "child.site.url.inherit.append.path", site.getChildSiteUrlInheritAppendPath());
        }
        if (site.getId() != null) {
            serializer.startTag(NAMESPACE, "id").text(site.getId()).endTag(NAMESPACE, "id");
            this.writeLocationTracking(site, "id", serializer);
        }
        if (site.getName() != null) {
            serializer.startTag(NAMESPACE, "name").text(site.getName()).endTag(NAMESPACE, "name");
            this.writeLocationTracking(site, "name", serializer);
        }
        if (site.getUrl() != null) {
            serializer.startTag(NAMESPACE, "url").text(site.getUrl()).endTag(NAMESPACE, "url");
            this.writeLocationTracking(site, "url", serializer);
        }
        serializer.endTag(NAMESPACE, tagName);
    }
}

