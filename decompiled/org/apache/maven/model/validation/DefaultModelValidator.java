/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.validation;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.maven.model.Activation;
import org.apache.maven.model.ActivationFile;
import org.apache.maven.model.Build;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Profile;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Repository;
import org.apache.maven.model.Resource;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.building.ModelProblemCollectorRequest;
import org.apache.maven.model.validation.ModelValidator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.StringUtils;

@Component(role=ModelValidator.class)
public class DefaultModelValidator
implements ModelValidator {
    private static final Pattern CI_FRIENDLY_EXPRESSION = Pattern.compile("\\$\\{(.+?)\\}");
    private static final List<String> CI_FRIENDLY_POSSIBLE_PROPERTY_NAMES = Arrays.asList("revision", "changelist", "sha1");
    private static final Pattern ID_REGEX = Pattern.compile("[A-Za-z0-9_\\-.]+");
    private static final Pattern ID_WITH_WILDCARDS_REGEX = Pattern.compile("[A-Za-z0-9_\\-.?*]+");
    private static final String ILLEGAL_FS_CHARS = "\\/:\"<>|?*";
    private static final String ILLEGAL_VERSION_CHARS = "\\/:\"<>|?*";
    private static final String ILLEGAL_REPO_ID_CHARS = "\\/:\"<>|?*";

    @Override
    public void validateRawModel(Model m4, ModelBuildingRequest request, ModelProblemCollector problems) {
        Parent parent = m4.getParent();
        if (parent != null) {
            this.validateStringNotEmpty("parent.groupId", problems, ModelProblem.Severity.FATAL, ModelProblem.Version.BASE, parent.getGroupId(), parent);
            this.validateStringNotEmpty("parent.artifactId", problems, ModelProblem.Severity.FATAL, ModelProblem.Version.BASE, parent.getArtifactId(), parent);
            this.validateStringNotEmpty("parent.version", problems, ModelProblem.Severity.FATAL, ModelProblem.Version.BASE, parent.getVersion(), parent);
            if (DefaultModelValidator.equals(parent.getGroupId(), m4.getGroupId()) && DefaultModelValidator.equals(parent.getArtifactId(), m4.getArtifactId())) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.FATAL, ModelProblem.Version.BASE, "parent.artifactId", null, "must be changed, the parent element cannot have the same groupId:artifactId as the project.", parent);
            }
            if (DefaultModelValidator.equals("LATEST", parent.getVersion()) || DefaultModelValidator.equals("RELEASE", parent.getVersion())) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.BASE, "parent.version", null, "is either LATEST or RELEASE (both of them are being deprecated)", parent);
            }
        }
        if (request.getValidationLevel() >= 20) {
            ModelProblem.Severity errOn30 = DefaultModelValidator.getSeverity(request, 30);
            this.validateStringNotEmpty("modelVersion", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, m4.getModelVersion(), m4);
            this.validateEnum("modelVersion", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, m4.getModelVersion(), null, m4, "4.0.0");
            this.validateStringNoExpression("groupId", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, m4.getGroupId(), m4);
            if (parent == null) {
                this.validateStringNotEmpty("groupId", problems, ModelProblem.Severity.FATAL, ModelProblem.Version.V20, m4.getGroupId(), m4);
            }
            this.validateStringNoExpression("artifactId", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, m4.getArtifactId(), m4);
            this.validateStringNotEmpty("artifactId", problems, ModelProblem.Severity.FATAL, ModelProblem.Version.V20, m4.getArtifactId(), m4);
            this.validateVersionNoExpression("version", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, m4.getVersion(), m4);
            if (parent == null) {
                this.validateStringNotEmpty("version", problems, ModelProblem.Severity.FATAL, ModelProblem.Version.V20, m4.getVersion(), m4);
            }
            this.validate20RawDependencies(problems, m4.getDependencies(), "dependencies.dependency", request);
            this.validate20RawDependenciesSelfReferencing(problems, m4, m4.getDependencies(), "dependencies.dependency", request);
            if (m4.getDependencyManagement() != null) {
                this.validate20RawDependencies(problems, m4.getDependencyManagement().getDependencies(), "dependencyManagement.dependencies.dependency", request);
            }
            this.validateRawRepositories(problems, m4.getRepositories(), "repositories.repository", request);
            this.validateRawRepositories(problems, m4.getPluginRepositories(), "pluginRepositories.pluginRepository", request);
            Build build = m4.getBuild();
            if (build != null) {
                this.validate20RawPlugins(problems, build.getPlugins(), "build.plugins.plugin", request);
                PluginManagement mgmt = build.getPluginManagement();
                if (mgmt != null) {
                    this.validate20RawPlugins(problems, mgmt.getPlugins(), "build.pluginManagement.plugins.plugin", request);
                }
            }
            HashSet<String> profileIds = new HashSet<String>();
            for (Profile profile : m4.getProfiles()) {
                String prefix = "profiles.profile[" + profile.getId() + "]";
                if (!profileIds.add(profile.getId())) {
                    DefaultModelValidator.addViolation(problems, errOn30, ModelProblem.Version.V20, "profiles.profile.id", null, "must be unique but found duplicate profile with id " + profile.getId(), profile);
                }
                this.validate30RawProfileActivation(problems, profile.getActivation(), profile.getId(), prefix + ".activation", request);
                this.validate20RawDependencies(problems, profile.getDependencies(), prefix + ".dependencies.dependency", request);
                if (profile.getDependencyManagement() != null) {
                    this.validate20RawDependencies(problems, profile.getDependencyManagement().getDependencies(), prefix + ".dependencyManagement.dependencies.dependency", request);
                }
                this.validateRawRepositories(problems, profile.getRepositories(), prefix + ".repositories.repository", request);
                this.validateRawRepositories(problems, profile.getPluginRepositories(), prefix + ".pluginRepositories.pluginRepository", request);
                BuildBase buildBase = profile.getBuild();
                if (buildBase == null) continue;
                this.validate20RawPlugins(problems, buildBase.getPlugins(), prefix + ".plugins.plugin", request);
                PluginManagement mgmt = buildBase.getPluginManagement();
                if (mgmt == null) continue;
                this.validate20RawPlugins(problems, mgmt.getPlugins(), prefix + ".pluginManagement.plugins.plugin", request);
            }
        }
    }

    private void validate30RawProfileActivation(ModelProblemCollector problems, Activation activation, String sourceHint, String prefix, ModelBuildingRequest request) {
        if (activation == null) {
            return;
        }
        ActivationFile file = activation.getFile();
        if (file != null) {
            boolean missing;
            String path;
            if (StringUtils.isNotEmpty(file.getExists())) {
                path = file.getExists();
                missing = false;
            } else if (StringUtils.isNotEmpty(file.getMissing())) {
                path = file.getMissing();
                missing = true;
            } else {
                return;
            }
            if (path.contains("${project.basedir}")) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V30, prefix + (missing ? ".file.missing" : ".file.exists"), null, "Failed to interpolate file location " + path + " for profile " + sourceHint + ": ${project.basedir} expression not supported during profile activation, use ${basedir} instead", file.getLocation(missing ? "missing" : "exists"));
            } else if (this.hasProjectExpression(path)) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V30, prefix + (missing ? ".file.missing" : ".file.exists"), null, "Failed to interpolate file location " + path + " for profile " + sourceHint + ": ${project.*} expressions are not supported during profile activation", file.getLocation(missing ? "missing" : "exists"));
            }
        }
    }

    private void validate20RawPlugins(ModelProblemCollector problems, List<Plugin> plugins, String prefix, ModelBuildingRequest request) {
        ModelProblem.Severity errOn31 = DefaultModelValidator.getSeverity(request, 31);
        HashMap<String, Plugin> index = new HashMap<String, Plugin>();
        for (Plugin plugin : plugins) {
            String key;
            Plugin existing;
            if (plugin.getGroupId() == null || plugin.getGroupId() != null && plugin.getGroupId().trim().isEmpty()) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.FATAL, ModelProblem.Version.V20, prefix + ".(groupId:artifactId)", null, "groupId of a plugin must be defined. ", plugin);
            }
            if (plugin.getArtifactId() == null || plugin.getArtifactId() != null && plugin.getArtifactId().trim().isEmpty()) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.FATAL, ModelProblem.Version.V20, prefix + ".(groupId:artifactId)", null, "artifactId of a plugin must be defined. ", plugin);
            }
            if (plugin.getVersion() != null && plugin.getVersion().trim().isEmpty()) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.FATAL, ModelProblem.Version.V20, prefix + ".(groupId:artifactId)", null, "version of a plugin must be defined. ", plugin);
            }
            if ((existing = (Plugin)index.get(key = plugin.getKey())) != null) {
                DefaultModelValidator.addViolation(problems, errOn31, ModelProblem.Version.V20, prefix + ".(groupId:artifactId)", null, "must be unique but found duplicate declaration of plugin " + key, plugin);
            } else {
                index.put(key, plugin);
            }
            HashSet<String> executionIds = new HashSet<String>();
            for (PluginExecution exec : plugin.getExecutions()) {
                if (executionIds.add(exec.getId())) continue;
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, prefix + "[" + plugin.getKey() + "].executions.execution.id", null, "must be unique but found duplicate execution with id " + exec.getId(), exec);
            }
        }
    }

    @Override
    public void validateEffectiveModel(Model m4, ModelBuildingRequest request, ModelProblemCollector problems) {
        this.validateStringNotEmpty("modelVersion", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, m4.getModelVersion(), m4);
        this.validateId("groupId", problems, m4.getGroupId(), m4);
        this.validateId("artifactId", problems, m4.getArtifactId(), m4);
        this.validateStringNotEmpty("packaging", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, m4.getPackaging(), m4);
        if (!m4.getModules().isEmpty()) {
            if (!"pom".equals(m4.getPackaging())) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, "packaging", null, "with value '" + m4.getPackaging() + "' is invalid. Aggregator projects require 'pom' as packaging.", m4);
            }
            int n = m4.getModules().size();
            for (int i = 0; i < n; ++i) {
                String module = m4.getModules().get(i);
                if (!StringUtils.isBlank(module)) continue;
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, "modules.module[" + i + "]", null, "has been specified without a path to the project directory.", m4.getLocation("modules"));
            }
        }
        this.validateStringNotEmpty("version", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, m4.getVersion(), m4);
        ModelProblem.Severity errOn30 = DefaultModelValidator.getSeverity(request, 30);
        this.validateEffectiveDependencies(problems, m4, m4.getDependencies(), false, request);
        DependencyManagement mgmt = m4.getDependencyManagement();
        if (mgmt != null) {
            this.validateEffectiveDependencies(problems, m4, mgmt.getDependencies(), true, request);
        }
        if (request.getValidationLevel() >= 20) {
            Reporting reporting;
            HashSet<String> modules = new HashSet<String>();
            int n = m4.getModules().size();
            for (int i = 0; i < n; ++i) {
                String module = m4.getModules().get(i);
                if (modules.add(module)) continue;
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, "modules.module[" + i + "]", null, "specifies duplicate child module " + (String)module, m4.getLocation("modules"));
            }
            ModelProblem.Severity errOn31 = DefaultModelValidator.getSeverity(request, 31);
            this.validateBannedCharacters("version", problems, errOn31, ModelProblem.Version.V20, m4.getVersion(), null, m4, "\\/:\"<>|?*");
            this.validate20ProperSnapshotVersion("version", problems, errOn31, ModelProblem.Version.V20, m4.getVersion(), null, m4);
            Build build = m4.getBuild();
            if (build != null) {
                for (Plugin plugin : build.getPlugins()) {
                    this.validateStringNotEmpty("build.plugins.plugin.artifactId", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, plugin.getArtifactId(), plugin);
                    this.validateStringNotEmpty("build.plugins.plugin.groupId", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, plugin.getGroupId(), plugin);
                    this.validate20PluginVersion("build.plugins.plugin.version", problems, plugin.getVersion(), plugin.getKey(), plugin, request);
                    this.validateBoolean("build.plugins.plugin.inherited", problems, errOn30, ModelProblem.Version.V20, plugin.getInherited(), plugin.getKey(), plugin);
                    this.validateBoolean("build.plugins.plugin.extensions", problems, errOn30, ModelProblem.Version.V20, plugin.getExtensions(), plugin.getKey(), plugin);
                    this.validate20EffectivePluginDependencies(problems, plugin, request);
                }
                this.validate20RawResources(problems, build.getResources(), "build.resources.resource", request);
                this.validate20RawResources(problems, build.getTestResources(), "build.testResources.testResource", request);
            }
            if ((reporting = m4.getReporting()) != null) {
                for (ReportPlugin p : reporting.getPlugins()) {
                    this.validateStringNotEmpty("reporting.plugins.plugin.artifactId", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, p.getArtifactId(), p);
                    this.validateStringNotEmpty("reporting.plugins.plugin.groupId", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, p.getGroupId(), p);
                }
            }
            for (Repository repository : m4.getRepositories()) {
                this.validate20EffectiveRepository(problems, repository, "repositories.repository", request);
            }
            for (Repository repository : m4.getPluginRepositories()) {
                this.validate20EffectiveRepository(problems, repository, "pluginRepositories.pluginRepository", request);
            }
            DistributionManagement distributionManagement = m4.getDistributionManagement();
            if (distributionManagement != null) {
                if (distributionManagement.getStatus() != null) {
                    DefaultModelValidator.addViolation(problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, "distributionManagement.status", null, "must not be specified.", distributionManagement);
                }
                this.validate20EffectiveRepository(problems, distributionManagement.getRepository(), "distributionManagement.repository", request);
                this.validate20EffectiveRepository(problems, distributionManagement.getSnapshotRepository(), "distributionManagement.snapshotRepository", request);
            }
        }
    }

    private void validate20RawDependencies(ModelProblemCollector problems, List<Dependency> dependencies, String prefix, ModelBuildingRequest request) {
        ModelProblem.Severity errOn30 = DefaultModelValidator.getSeverity(request, 30);
        ModelProblem.Severity errOn31 = DefaultModelValidator.getSeverity(request, 31);
        HashMap<String, Dependency> index = new HashMap<String, Dependency>();
        for (Dependency dependency : dependencies) {
            Dependency existing;
            String key = dependency.getManagementKey();
            if ("import".equals(dependency.getScope())) {
                if (!"pom".equals(dependency.getType())) {
                    DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, prefix + ".type", key, "must be 'pom' to import the managed dependencies.", dependency);
                } else if (StringUtils.isNotEmpty(dependency.getClassifier())) {
                    DefaultModelValidator.addViolation(problems, errOn30, ModelProblem.Version.V20, prefix + ".classifier", key, "must be empty, imported POM cannot have a classifier.", dependency);
                }
            } else if ("system".equals(dependency.getScope())) {
                String sysPath;
                if (request.getValidationLevel() >= 31) {
                    DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V31, prefix + ".scope", key, "declares usage of deprecated 'system' scope ", dependency);
                }
                if (StringUtils.isNotEmpty(sysPath = dependency.getSystemPath())) {
                    if (!this.hasExpression(sysPath)) {
                        DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, prefix + ".systemPath", key, "should use a variable instead of a hard-coded path " + sysPath, dependency);
                    } else if (sysPath.contains("${basedir}") || sysPath.contains("${project.basedir}")) {
                        DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, prefix + ".systemPath", key, "should not point at files within the project directory, " + sysPath + " will be unresolvable by dependent projects", dependency);
                    }
                }
            }
            if (DefaultModelValidator.equals("LATEST", dependency.getVersion()) || DefaultModelValidator.equals("RELEASE", dependency.getVersion())) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.BASE, prefix + ".version", key, "is either LATEST or RELEASE (both of them are being deprecated)", dependency);
            }
            if ((existing = (Dependency)index.get(key)) != null) {
                String msg = DefaultModelValidator.equals(existing.getVersion(), dependency.getVersion()) ? "duplicate declaration of version " + StringUtils.defaultString(dependency.getVersion(), "(?)") : "version " + StringUtils.defaultString(existing.getVersion(), "(?)") + " vs " + StringUtils.defaultString(dependency.getVersion(), "(?)");
                DefaultModelValidator.addViolation(problems, errOn31, ModelProblem.Version.V20, prefix + ".(groupId:artifactId:type:classifier)", null, "must be unique: " + key + " -> " + msg, dependency);
                continue;
            }
            index.put(key, dependency);
        }
    }

    private void validate20RawDependenciesSelfReferencing(ModelProblemCollector problems, Model m4, List<Dependency> dependencies, String prefix, ModelBuildingRequest request) {
        for (Dependency dependency : dependencies) {
            String mKey;
            String key = dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + dependency.getVersion() + (dependency.getClassifier() != null ? ":" + dependency.getClassifier() : "");
            if (!key.equals(mKey = m4.getGroupId() + ":" + m4.getArtifactId() + ":" + m4.getVersion())) continue;
            DefaultModelValidator.addViolation(problems, ModelProblem.Severity.FATAL, ModelProblem.Version.V31, prefix + " " + key, key, "is referencing itself.", dependency);
        }
    }

    private void validateEffectiveDependencies(ModelProblemCollector problems, Model m4, List<Dependency> dependencies, boolean management, ModelBuildingRequest request) {
        ModelProblem.Severity errOn30 = DefaultModelValidator.getSeverity(request, 30);
        String prefix = management ? "dependencyManagement.dependencies.dependency." : "dependencies.dependency.";
        for (Dependency d : dependencies) {
            this.validateEffectiveDependency(problems, d, management, prefix, request);
            if (request.getValidationLevel() < 20) continue;
            this.validateBoolean(prefix + "optional", problems, errOn30, ModelProblem.Version.V20, d.getOptional(), d.getManagementKey(), d);
            if (!management) {
                this.validateVersion(prefix + "version", problems, errOn30, ModelProblem.Version.V20, d.getVersion(), d.getManagementKey(), d);
                this.validateEnum(prefix + "scope", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, d.getScope(), d.getManagementKey(), d, "provided", "compile", "runtime", "test", "system");
                this.validateEffectiveModelAgainstDependency(prefix, problems, m4, d, request);
                continue;
            }
            this.validateEnum(prefix + "scope", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, d.getScope(), d.getManagementKey(), d, "provided", "compile", "runtime", "test", "system", "import");
        }
    }

    private void validateEffectiveModelAgainstDependency(String prefix, ModelProblemCollector problems, Model m4, Dependency d, ModelBuildingRequest request) {
        String mKey;
        String key = d.getGroupId() + ":" + d.getArtifactId() + ":" + d.getVersion() + (d.getClassifier() != null ? ":" + d.getClassifier() : "");
        if (key.equals(mKey = m4.getGroupId() + ":" + m4.getArtifactId() + ":" + m4.getVersion())) {
            DefaultModelValidator.addViolation(problems, ModelProblem.Severity.FATAL, ModelProblem.Version.V31, prefix + " " + key, key, "is referencing itself.", d);
        }
    }

    private void validate20EffectivePluginDependencies(ModelProblemCollector problems, Plugin plugin, ModelBuildingRequest request) {
        List<Dependency> dependencies = plugin.getDependencies();
        if (!dependencies.isEmpty()) {
            String prefix = "build.plugins.plugin[" + plugin.getKey() + "].dependencies.dependency.";
            ModelProblem.Severity errOn30 = DefaultModelValidator.getSeverity(request, 30);
            for (Dependency d : dependencies) {
                this.validateEffectiveDependency(problems, d, false, prefix, request);
                this.validateVersion(prefix + "version", problems, errOn30, ModelProblem.Version.BASE, d.getVersion(), d.getManagementKey(), d);
                this.validateEnum(prefix + "scope", problems, errOn30, ModelProblem.Version.BASE, d.getScope(), d.getManagementKey(), d, "compile", "runtime", "system");
            }
        }
    }

    private void validateEffectiveDependency(ModelProblemCollector problems, Dependency d, boolean management, String prefix, ModelBuildingRequest request) {
        this.validateId(prefix + "artifactId", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, d.getArtifactId(), d.getManagementKey(), d);
        this.validateId(prefix + "groupId", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, d.getGroupId(), d.getManagementKey(), d);
        if (!management) {
            this.validateStringNotEmpty(prefix + "type", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, d.getType(), d.getManagementKey(), d);
            this.validateDependencyVersion(problems, d, prefix);
        }
        if ("system".equals(d.getScope())) {
            String systemPath = d.getSystemPath();
            if (StringUtils.isEmpty(systemPath)) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, prefix + "systemPath", d.getManagementKey(), "is missing.", d);
            } else {
                File sysFile = new File(systemPath);
                if (!sysFile.isAbsolute()) {
                    DefaultModelValidator.addViolation(problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, prefix + "systemPath", d.getManagementKey(), "must specify an absolute path but is " + systemPath, d);
                } else if (!sysFile.isFile()) {
                    String jdkHome;
                    String msg = "refers to a non-existing file " + sysFile.getAbsolutePath();
                    if ((systemPath = systemPath.replace('/', File.separatorChar).replace('\\', File.separatorChar)).startsWith(jdkHome = request.getSystemProperties().getProperty("java.home", "") + File.separator + "..")) {
                        msg = msg + ". Please verify that you run Maven using a JDK and not just a JRE.";
                    }
                    DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.BASE, prefix + "systemPath", d.getManagementKey(), msg, d);
                }
            }
        } else if (StringUtils.isNotEmpty(d.getSystemPath())) {
            DefaultModelValidator.addViolation(problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, prefix + "systemPath", d.getManagementKey(), "must be omitted. This field may only be specified for a dependency with system scope.", d);
        }
        if (request.getValidationLevel() >= 20) {
            for (Exclusion exclusion : d.getExclusions()) {
                if (request.getValidationLevel() < 30) {
                    this.validateId(prefix + "exclusions.exclusion.groupId", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, exclusion.getGroupId(), d.getManagementKey(), exclusion);
                    this.validateId(prefix + "exclusions.exclusion.artifactId", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, exclusion.getArtifactId(), d.getManagementKey(), exclusion);
                    continue;
                }
                this.validateIdWithWildcards(prefix + "exclusions.exclusion.groupId", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V30, exclusion.getGroupId(), d.getManagementKey(), exclusion);
                this.validateIdWithWildcards(prefix + "exclusions.exclusion.artifactId", problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V30, exclusion.getArtifactId(), d.getManagementKey(), exclusion);
            }
        }
    }

    protected void validateDependencyVersion(ModelProblemCollector problems, Dependency d, String prefix) {
        this.validateStringNotEmpty(prefix + "version", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, d.getVersion(), d.getManagementKey(), d);
    }

    private void validateRawRepositories(ModelProblemCollector problems, List<Repository> repositories, String prefix, ModelBuildingRequest request) {
        HashMap<String, Repository> index = new HashMap<String, Repository>();
        for (Repository repository : repositories) {
            this.validateStringNotEmpty(prefix + ".id", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, repository.getId(), repository);
            this.validateStringNotEmpty(prefix + "[" + repository.getId() + "].url", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, repository.getUrl(), repository);
            String key = repository.getId();
            Repository existing = (Repository)index.get(key);
            if (existing != null) {
                ModelProblem.Severity errOn30 = DefaultModelValidator.getSeverity(request, 30);
                DefaultModelValidator.addViolation(problems, errOn30, ModelProblem.Version.V20, prefix + ".id", null, "must be unique: " + repository.getId() + " -> " + existing.getUrl() + " vs " + repository.getUrl(), repository);
                continue;
            }
            index.put(key, repository);
        }
    }

    private void validate20EffectiveRepository(ModelProblemCollector problems, Repository repository, String prefix, ModelBuildingRequest request) {
        if (repository != null) {
            ModelProblem.Severity errOn31 = DefaultModelValidator.getSeverity(request, 31);
            this.validateBannedCharacters(prefix + ".id", problems, errOn31, ModelProblem.Version.V20, repository.getId(), null, repository, "\\/:\"<>|?*");
            if ("local".equals(repository.getId())) {
                DefaultModelValidator.addViolation(problems, errOn31, ModelProblem.Version.V20, prefix + ".id", null, "must not be 'local', this identifier is reserved for the local repository, using it for other repositories will corrupt your repository metadata.", repository);
            }
            if ("legacy".equals(repository.getLayout())) {
                DefaultModelValidator.addViolation(problems, ModelProblem.Severity.WARNING, ModelProblem.Version.V20, prefix + ".layout", repository.getId(), "uses the unsupported value 'legacy', artifact resolution might fail.", repository);
            }
        }
    }

    private void validate20RawResources(ModelProblemCollector problems, List<Resource> resources, String prefix, ModelBuildingRequest request) {
        ModelProblem.Severity errOn30 = DefaultModelValidator.getSeverity(request, 30);
        for (Resource resource : resources) {
            this.validateStringNotEmpty(prefix + ".directory", problems, ModelProblem.Severity.ERROR, ModelProblem.Version.V20, resource.getDirectory(), resource);
            this.validateBoolean(prefix + ".filtering", problems, errOn30, ModelProblem.Version.V20, resource.getFiltering(), resource.getDirectory(), resource);
        }
    }

    private boolean validateId(String fieldName, ModelProblemCollector problems, String id, InputLocationTracker tracker) {
        return this.validateId(fieldName, problems, ModelProblem.Severity.ERROR, ModelProblem.Version.BASE, id, null, tracker);
    }

    private boolean validateId(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String id, String sourceHint, InputLocationTracker tracker) {
        if (!this.validateStringNotEmpty(fieldName, problems, severity, version, id, sourceHint, tracker)) {
            return false;
        }
        boolean match = ID_REGEX.matcher(id).matches();
        if (!match) {
            DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "with value '" + id + "' does not match a valid id pattern.", tracker);
        }
        return match;
    }

    private boolean validateIdWithWildcards(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String id, String sourceHint, InputLocationTracker tracker) {
        if (!this.validateStringNotEmpty(fieldName, problems, severity, version, id, sourceHint, tracker)) {
            return false;
        }
        boolean match = ID_WITH_WILDCARDS_REGEX.matcher(id).matches();
        if (!match) {
            DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "with value '" + id + "' does not match a valid id pattern.", tracker);
        }
        return match;
    }

    private boolean validateStringNoExpression(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, InputLocationTracker tracker) {
        if (!this.hasExpression(string)) {
            return true;
        }
        DefaultModelValidator.addViolation(problems, severity, version, fieldName, null, "contains an expression but should be a constant.", tracker);
        return false;
    }

    private boolean validateVersionNoExpression(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, InputLocationTracker tracker) {
        if (!this.hasExpression(string)) {
            return true;
        }
        Matcher m4 = CI_FRIENDLY_EXPRESSION.matcher(string.trim());
        while (m4.find()) {
            if (CI_FRIENDLY_POSSIBLE_PROPERTY_NAMES.contains(m4.group(1))) continue;
            DefaultModelValidator.addViolation(problems, severity, version, fieldName, null, "contains an expression but should be a constant.", tracker);
            return false;
        }
        return true;
    }

    private boolean hasExpression(String value) {
        return value != null && value.contains("${");
    }

    private boolean hasProjectExpression(String value) {
        return value != null && value.contains("${project.");
    }

    private boolean validateStringNotEmpty(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, InputLocationTracker tracker) {
        return this.validateStringNotEmpty(fieldName, problems, severity, version, string, null, tracker);
    }

    private boolean validateStringNotEmpty(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, String sourceHint, InputLocationTracker tracker) {
        if (!this.validateNotNull(fieldName, problems, severity, version, string, sourceHint, tracker)) {
            return false;
        }
        if (string.length() > 0) {
            return true;
        }
        DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "is missing.", tracker);
        return false;
    }

    private boolean validateNotNull(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, Object object, String sourceHint, InputLocationTracker tracker) {
        if (object != null) {
            return true;
        }
        DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "is missing.", tracker);
        return false;
    }

    private boolean validateBoolean(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, String sourceHint, InputLocationTracker tracker) {
        if (string == null || string.length() <= 0) {
            return true;
        }
        if ("true".equalsIgnoreCase(string) || "false".equalsIgnoreCase(string)) {
            return true;
        }
        DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "must be 'true' or 'false' but is '" + string + "'.", tracker);
        return false;
    }

    private boolean validateEnum(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, String sourceHint, InputLocationTracker tracker, String ... validValues) {
        if (string == null || string.length() <= 0) {
            return true;
        }
        List<String> values2 = Arrays.asList(validValues);
        if (values2.contains(string)) {
            return true;
        }
        DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "must be one of " + values2 + " but is '" + string + "'.", tracker);
        return false;
    }

    private boolean validateBannedCharacters(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, String sourceHint, InputLocationTracker tracker, String banned) {
        if (string != null) {
            for (int i = string.length() - 1; i >= 0; --i) {
                if (banned.indexOf(string.charAt(i)) < 0) continue;
                DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "must not contain any of these characters " + banned + " but found " + string.charAt(i), tracker);
                return false;
            }
        }
        return true;
    }

    private boolean validateVersion(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, String sourceHint, InputLocationTracker tracker) {
        if (string == null || string.length() <= 0) {
            return true;
        }
        if (this.hasExpression(string)) {
            DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "must be a valid version but is '" + string + "'.", tracker);
            return false;
        }
        return this.validateBannedCharacters(fieldName, problems, severity, version, string, sourceHint, tracker, "\\/:\"<>|?*");
    }

    private boolean validate20ProperSnapshotVersion(String fieldName, ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String string, String sourceHint, InputLocationTracker tracker) {
        if (string == null || string.length() <= 0) {
            return true;
        }
        if (string.endsWith("SNAPSHOT") && !string.endsWith("-SNAPSHOT")) {
            DefaultModelValidator.addViolation(problems, severity, version, fieldName, sourceHint, "uses an unsupported snapshot version format, should be '*-SNAPSHOT' instead.", tracker);
            return false;
        }
        return true;
    }

    private boolean validate20PluginVersion(String fieldName, ModelProblemCollector problems, String string, String sourceHint, InputLocationTracker tracker, ModelBuildingRequest request) {
        if (string == null) {
            return true;
        }
        ModelProblem.Severity errOn30 = DefaultModelValidator.getSeverity(request, 30);
        if (!this.validateVersion(fieldName, problems, errOn30, ModelProblem.Version.V20, string, sourceHint, tracker)) {
            return false;
        }
        if (string.length() <= 0 || "RELEASE".equals(string) || "LATEST".equals(string)) {
            DefaultModelValidator.addViolation(problems, errOn30, ModelProblem.Version.V20, fieldName, sourceHint, "must be a valid version but is '" + string + "'.", tracker);
            return false;
        }
        return true;
    }

    private static void addViolation(ModelProblemCollector problems, ModelProblem.Severity severity, ModelProblem.Version version, String fieldName, String sourceHint, String message, InputLocationTracker tracker) {
        StringBuilder buffer = new StringBuilder(256);
        buffer.append('\'').append(fieldName).append('\'');
        if (sourceHint != null) {
            buffer.append(" for ").append(sourceHint);
        }
        buffer.append(' ').append(message);
        problems.add(new ModelProblemCollectorRequest(severity, version).setMessage(buffer.toString()).setLocation(DefaultModelValidator.getLocation(fieldName, tracker)));
    }

    private static InputLocation getLocation(String fieldName, InputLocationTracker tracker) {
        InputLocation location = null;
        if (tracker != null) {
            if (fieldName != null) {
                Object key = fieldName;
                int idx = fieldName.lastIndexOf(46);
                if (idx >= 0) {
                    fieldName = fieldName.substring(idx + 1);
                    key = fieldName;
                }
                if (fieldName.endsWith("]")) {
                    key = fieldName.substring(fieldName.lastIndexOf(91) + 1, fieldName.length() - 1);
                    try {
                        key = Integer.valueOf(key.toString());
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
                location = tracker.getLocation(key);
            }
            if (location == null) {
                location = tracker.getLocation("");
            }
        }
        return location;
    }

    private static boolean equals(String s1, String s2) {
        return StringUtils.clean(s1).equals(StringUtils.clean(s2));
    }

    private static ModelProblem.Severity getSeverity(ModelBuildingRequest request, int errorThreshold) {
        return DefaultModelValidator.getSeverity(request.getValidationLevel(), errorThreshold);
    }

    private static ModelProblem.Severity getSeverity(int validationLevel, int errorThreshold) {
        if (validationLevel < errorThreshold) {
            return ModelProblem.Severity.WARNING;
        }
        return ModelProblem.Severity.ERROR;
    }
}

