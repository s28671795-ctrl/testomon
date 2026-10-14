/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.merge;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import org.apache.maven.model.Activation;
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

public class ModelMerger {
    public void merge(Model target, Model source2, boolean sourceDominant, Map<?, ?> hints) {
        Objects.requireNonNull(target, "target cannot be null");
        if (source2 == null) {
            return;
        }
        HashMap<Object, Object> context = new HashMap<Object, Object>();
        if (hints != null) {
            context.putAll(hints);
        }
        this.mergeModel(target, source2, sourceDominant, context);
    }

    protected void mergeModel(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeModelBase(target, source2, sourceDominant, context);
        this.mergeModel_ChildProjectUrlInheritAppendPath(target, source2, sourceDominant, context);
        this.mergeModel_ModelVersion(target, source2, sourceDominant, context);
        this.mergeModel_Parent(target, source2, sourceDominant, context);
        this.mergeModel_GroupId(target, source2, sourceDominant, context);
        this.mergeModel_ArtifactId(target, source2, sourceDominant, context);
        this.mergeModel_Version(target, source2, sourceDominant, context);
        this.mergeModel_Packaging(target, source2, sourceDominant, context);
        this.mergeModel_Name(target, source2, sourceDominant, context);
        this.mergeModel_Description(target, source2, sourceDominant, context);
        this.mergeModel_Url(target, source2, sourceDominant, context);
        this.mergeModel_InceptionYear(target, source2, sourceDominant, context);
        this.mergeModel_Organization(target, source2, sourceDominant, context);
        this.mergeModel_Licenses(target, source2, sourceDominant, context);
        this.mergeModel_MailingLists(target, source2, sourceDominant, context);
        this.mergeModel_Developers(target, source2, sourceDominant, context);
        this.mergeModel_Contributors(target, source2, sourceDominant, context);
        this.mergeModel_IssueManagement(target, source2, sourceDominant, context);
        this.mergeModel_Scm(target, source2, sourceDominant, context);
        this.mergeModel_CiManagement(target, source2, sourceDominant, context);
        this.mergeModel_Prerequisites(target, source2, sourceDominant, context);
        this.mergeModel_Build(target, source2, sourceDominant, context);
        this.mergeModel_Profiles(target, source2, sourceDominant, context);
    }

    protected void mergeModel_ModelVersion(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getModelVersion();
        if (src != null && (sourceDominant || target.getModelVersion() == null)) {
            target.setModelVersion(src);
            target.setLocation("modelVersion", source2.getLocation("modelVersion"));
        }
    }

    protected void mergeModel_Parent(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        Parent src = source2.getParent();
        if (src != null) {
            Parent tgt = target.getParent();
            if (tgt == null) {
                tgt = new Parent();
                tgt.setRelativePath(null);
                target.setParent(tgt);
            }
            this.mergeParent(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModel_GroupId(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getGroupId();
        if (src != null && (sourceDominant || target.getGroupId() == null)) {
            target.setGroupId(src);
            target.setLocation("groupId", source2.getLocation("groupId"));
        }
    }

    protected void mergeModel_ArtifactId(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArtifactId();
        if (src != null && (sourceDominant || target.getArtifactId() == null)) {
            target.setArtifactId(src);
            target.setLocation("artifactId", source2.getLocation("artifactId"));
        }
    }

    protected void mergeModel_ChildProjectUrlInheritAppendPath(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getChildProjectUrlInheritAppendPath();
        if (src != null && (sourceDominant || target.getChildProjectUrlInheritAppendPath() == null)) {
            target.setChildProjectUrlInheritAppendPath(src);
            target.setLocation("child.project.url.inherit.append.path", source2.getLocation("child.project.url.inherit.append.path"));
        }
    }

    protected void mergeModel_Version(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getVersion();
        if (src != null && (sourceDominant || target.getVersion() == null)) {
            target.setVersion(src);
            target.setLocation("version", source2.getLocation("version"));
        }
    }

    protected void mergeModel_Packaging(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getPackaging();
        if (src != null && (sourceDominant || target.getPackaging() == null)) {
            target.setPackaging(src);
            target.setLocation("packaging", source2.getLocation("packaging"));
        }
    }

    protected void mergeModel_Name(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getName();
        if (src != null && (sourceDominant || target.getName() == null)) {
            target.setName(src);
            target.setLocation("name", source2.getLocation("name"));
        }
    }

    protected void mergeModel_Description(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getDescription();
        if (src != null && (sourceDominant || target.getDescription() == null)) {
            target.setDescription(src);
            target.setLocation("description", source2.getLocation("description"));
        }
    }

    protected void mergeModel_Url(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeModel_InceptionYear(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getInceptionYear();
        if (src != null && (sourceDominant || target.getInceptionYear() == null)) {
            target.setInceptionYear(src);
            target.setLocation("inceptionYear", source2.getLocation("inceptionYear"));
        }
    }

    protected void mergeModel_Organization(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        Organization src = source2.getOrganization();
        if (src != null) {
            Organization tgt = target.getOrganization();
            if (tgt == null) {
                tgt = new Organization();
                target.setOrganization(tgt);
            }
            this.mergeOrganization(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModel_Licenses(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setLicenses(ModelMerger.merge(target.getLicenses(), source2.getLicenses(), sourceDominant, new LicenseKeyComputer()));
    }

    protected void mergeModel_MailingLists(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setMailingLists(ModelMerger.merge(target.getMailingLists(), source2.getMailingLists(), sourceDominant, new MailingListKeyComputer()));
    }

    protected void mergeModel_Developers(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setDevelopers(ModelMerger.merge(target.getDevelopers(), source2.getDevelopers(), sourceDominant, new DeveloperKeyComputer()));
    }

    protected void mergeModel_Contributors(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setContributors(ModelMerger.merge(target.getContributors(), source2.getContributors(), sourceDominant, new ContributorKeyComputer()));
    }

    protected void mergeModel_IssueManagement(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        IssueManagement src = source2.getIssueManagement();
        if (src != null) {
            IssueManagement tgt = target.getIssueManagement();
            if (tgt == null) {
                tgt = new IssueManagement();
                target.setIssueManagement(tgt);
            }
            this.mergeIssueManagement(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModel_Scm(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        Scm src = source2.getScm();
        if (src != null) {
            Scm tgt = target.getScm();
            if (tgt == null) {
                tgt = new Scm();
                tgt.setTag(null);
                target.setScm(tgt);
            }
            this.mergeScm(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModel_CiManagement(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        CiManagement src = source2.getCiManagement();
        if (src != null) {
            CiManagement tgt = target.getCiManagement();
            if (tgt == null) {
                tgt = new CiManagement();
                target.setCiManagement(tgt);
            }
            this.mergeCiManagement(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModel_Prerequisites(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        Prerequisites src = source2.getPrerequisites();
        if (src != null) {
            Prerequisites tgt = target.getPrerequisites();
            if (tgt == null) {
                tgt = new Prerequisites();
                tgt.setMaven(null);
                target.setPrerequisites(tgt);
            }
            this.mergePrerequisites(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModel_Build(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        Build src = source2.getBuild();
        if (src != null) {
            Build tgt = target.getBuild();
            if (tgt == null) {
                tgt = new Build();
                target.setBuild(tgt);
            }
            this.mergeBuild(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModel_Profiles(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setProfiles(ModelMerger.merge(target.getProfiles(), source2.getProfiles(), sourceDominant, new ProfileKeyComputer()));
    }

    protected void mergeModelBase(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeModelBase_DistributionManagement(target, source2, sourceDominant, context);
        this.mergeModelBase_Modules(target, source2, sourceDominant, context);
        this.mergeModelBase_Repositories(target, source2, sourceDominant, context);
        this.mergeModelBase_PluginRepositories(target, source2, sourceDominant, context);
        this.mergeModelBase_Dependencies(target, source2, sourceDominant, context);
        this.mergeModelBase_Reporting(target, source2, sourceDominant, context);
        this.mergeModelBase_DependencyManagement(target, source2, sourceDominant, context);
        this.mergeModelBase_Properties(target, source2, sourceDominant, context);
    }

    protected void mergeModelBase_Modules(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getModules();
        if (!src.isEmpty()) {
            List<String> tgt = target.getModules();
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            merged.addAll(src);
            target.setModules(merged);
        }
    }

    protected void mergeModelBase_Dependencies(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setDependencies(ModelMerger.merge(target.getDependencies(), source2.getDependencies(), sourceDominant, new DependencyKeyComputer()));
    }

    protected void mergeModelBase_Repositories(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setRepositories(ModelMerger.merge(target.getRepositories(), source2.getRepositories(), sourceDominant, new RepositoryKeyComputer()));
    }

    protected void mergeModelBase_PluginRepositories(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setPluginRepositories(ModelMerger.merge(target.getPluginRepositories(), source2.getPluginRepositories(), sourceDominant, new RepositoryKeyComputer()));
    }

    protected void mergeModelBase_DistributionManagement(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        DistributionManagement src = source2.getDistributionManagement();
        if (src != null) {
            DistributionManagement tgt = target.getDistributionManagement();
            if (tgt == null) {
                tgt = new DistributionManagement();
                target.setDistributionManagement(tgt);
            }
            this.mergeDistributionManagement(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModelBase_Reporting(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        Reporting src = source2.getReporting();
        if (src != null) {
            Reporting tgt = target.getReporting();
            if (tgt == null) {
                tgt = new Reporting();
                target.setReporting(tgt);
            }
            this.mergeReporting(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModelBase_DependencyManagement(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        DependencyManagement src = source2.getDependencyManagement();
        if (src != null) {
            DependencyManagement tgt = target.getDependencyManagement();
            if (tgt == null) {
                tgt = new DependencyManagement();
                target.setDependencyManagement(tgt);
            }
            this.mergeDependencyManagement(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeModelBase_Properties(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        Properties merged = new Properties();
        if (sourceDominant) {
            merged.putAll((Map<?, ?>)target.getProperties());
            merged.putAll((Map<?, ?>)source2.getProperties());
        } else {
            merged.putAll((Map<?, ?>)source2.getProperties());
            merged.putAll((Map<?, ?>)target.getProperties());
        }
        target.setProperties(merged);
        target.setLocation("properties", InputLocation.merge(target.getLocation("properties"), source2.getLocation("properties"), sourceDominant));
    }

    protected void mergeDistributionManagement(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeDistributionManagement_Repository(target, source2, sourceDominant, context);
        this.mergeDistributionManagement_SnapshotRepository(target, source2, sourceDominant, context);
        this.mergeDistributionManagement_Site(target, source2, sourceDominant, context);
        this.mergeDistributionManagement_Status(target, source2, sourceDominant, context);
        this.mergeDistributionManagement_DownloadUrl(target, source2, sourceDominant, context);
    }

    protected void mergeDistributionManagement_Repository(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        DeploymentRepository src = source2.getRepository();
        if (src != null) {
            DeploymentRepository tgt = target.getRepository();
            if (tgt == null) {
                tgt = new DeploymentRepository();
                target.setRepository(tgt);
            }
            this.mergeDeploymentRepository(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeDistributionManagement_SnapshotRepository(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        DeploymentRepository src = source2.getSnapshotRepository();
        if (src != null) {
            DeploymentRepository tgt = target.getSnapshotRepository();
            if (tgt == null) {
                tgt = new DeploymentRepository();
                target.setSnapshotRepository(tgt);
            }
            this.mergeDeploymentRepository(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeDistributionManagement_Site(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        Site src = source2.getSite();
        if (src != null) {
            Site tgt = target.getSite();
            if (tgt == null) {
                tgt = new Site();
                target.setSite(tgt);
            }
            this.mergeSite(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeDistributionManagement_Status(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getStatus();
        if (src != null && (sourceDominant || target.getStatus() == null)) {
            target.setStatus(src);
            target.setLocation("status", source2.getLocation("status"));
        }
    }

    protected void mergeDistributionManagement_DownloadUrl(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getDownloadUrl();
        if (src != null && (sourceDominant || target.getDownloadUrl() == null)) {
            target.setDownloadUrl(src);
            target.setLocation("downloadUrl", source2.getLocation("downloadUrl"));
        }
    }

    protected void mergeRelocation(Relocation target, Relocation source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeRelocation_GroupId(target, source2, sourceDominant, context);
        this.mergeRelocation_ArtifactId(target, source2, sourceDominant, context);
        this.mergeRelocation_Version(target, source2, sourceDominant, context);
        this.mergeRelocation_Message(target, source2, sourceDominant, context);
    }

    protected void mergeRelocation_GroupId(Relocation target, Relocation source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getGroupId();
        if (src != null && (sourceDominant || target.getGroupId() == null)) {
            target.setGroupId(src);
            target.setLocation("groupId", source2.getLocation("groupId"));
        }
    }

    protected void mergeRelocation_ArtifactId(Relocation target, Relocation source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArtifactId();
        if (src != null && (sourceDominant || target.getArtifactId() == null)) {
            target.setArtifactId(src);
            target.setLocation("artifactId", source2.getLocation("artifactId"));
        }
    }

    protected void mergeRelocation_Version(Relocation target, Relocation source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getVersion();
        if (src != null && (sourceDominant || target.getVersion() == null)) {
            target.setVersion(src);
            target.setLocation("version", source2.getLocation("version"));
        }
    }

    protected void mergeRelocation_Message(Relocation target, Relocation source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getMessage();
        if (src != null && (sourceDominant || target.getMessage() == null)) {
            target.setMessage(src);
            target.setLocation("message", source2.getLocation("message"));
        }
    }

    protected void mergeDeploymentRepository(DeploymentRepository target, DeploymentRepository source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeRepository(target, source2, sourceDominant, context);
        this.mergeDeploymentRepository_UniqueVersion(target, source2, sourceDominant, context);
    }

    protected void mergeDeploymentRepository_UniqueVersion(DeploymentRepository target, DeploymentRepository source2, boolean sourceDominant, Map<Object, Object> context) {
        if (sourceDominant) {
            target.setUniqueVersion(source2.isUniqueVersion());
            target.setLocation("uniqueVersion", source2.getLocation("uniqueVersion"));
        }
    }

    protected void mergeSite(Site target, Site source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeSite_ChildSiteUrlInheritAppendPath(target, source2, sourceDominant, context);
        this.mergeSite_Id(target, source2, sourceDominant, context);
        this.mergeSite_Name(target, source2, sourceDominant, context);
        this.mergeSite_Url(target, source2, sourceDominant, context);
    }

    protected void mergeSite_ChildSiteUrlInheritAppendPath(Site target, Site source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getChildSiteUrlInheritAppendPath();
        if (src != null && (sourceDominant || target.getChildSiteUrlInheritAppendPath() == null)) {
            target.setChildSiteUrlInheritAppendPath(src);
            target.setLocation("child.site.url.inherit.append.path", source2.getLocation("child.site.url.inherit.append.path"));
        }
    }

    protected void mergeSite_Id(Site target, Site source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getId();
        if (src != null && (sourceDominant || target.getId() == null)) {
            target.setId(src);
            target.setLocation("id", source2.getLocation("id"));
        }
    }

    protected void mergeSite_Name(Site target, Site source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getName();
        if (src != null && (sourceDominant || target.getName() == null)) {
            target.setName(src);
            target.setLocation("name", source2.getLocation("name"));
        }
    }

    protected void mergeSite_Url(Site target, Site source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeRepository(Repository target, Repository source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeRepositoryBase(target, source2, sourceDominant, context);
        this.mergeRepository_Releases(target, source2, sourceDominant, context);
        this.mergeRepository_Snapshots(target, source2, sourceDominant, context);
    }

    protected void mergeRepository_Releases(Repository target, Repository source2, boolean sourceDominant, Map<Object, Object> context) {
        RepositoryPolicy src = source2.getReleases();
        if (src != null) {
            RepositoryPolicy tgt = target.getReleases();
            if (tgt == null) {
                tgt = new RepositoryPolicy();
                target.setReleases(tgt);
            }
            this.mergeRepositoryPolicy(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeRepository_Snapshots(Repository target, Repository source2, boolean sourceDominant, Map<Object, Object> context) {
        RepositoryPolicy src = source2.getSnapshots();
        if (src != null) {
            RepositoryPolicy tgt = target.getSnapshots();
            if (tgt == null) {
                tgt = new RepositoryPolicy();
                target.setSnapshots(tgt);
            }
            this.mergeRepositoryPolicy(tgt, src, sourceDominant, context);
        }
    }

    protected void mergeRepositoryBase(RepositoryBase target, RepositoryBase source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeRepositoryBase_Id(target, source2, sourceDominant, context);
        this.mergeRepositoryBase_Name(target, source2, sourceDominant, context);
        this.mergeRepositoryBase_Url(target, source2, sourceDominant, context);
        this.mergeRepositoryBase_Layout(target, source2, sourceDominant, context);
    }

    protected void mergeRepositoryBase_Id(RepositoryBase target, RepositoryBase source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getId();
        if (src != null && (sourceDominant || target.getId() == null)) {
            target.setId(src);
            target.setLocation("id", source2.getLocation("id"));
        }
    }

    protected void mergeRepositoryBase_Url(RepositoryBase target, RepositoryBase source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeRepositoryBase_Name(RepositoryBase target, RepositoryBase source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getName();
        if (src != null && (sourceDominant || target.getName() == null)) {
            target.setName(src);
            target.setLocation("name", source2.getLocation("name"));
        }
    }

    protected void mergeRepositoryBase_Layout(RepositoryBase target, RepositoryBase source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getLayout();
        if (src != null && (sourceDominant || target.getLayout() == null)) {
            target.setLayout(src);
            target.setLocation("layout", source2.getLocation("layout"));
        }
    }

    protected void mergeRepositoryPolicy(RepositoryPolicy target, RepositoryPolicy source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeRepositoryPolicy_Enabled(target, source2, sourceDominant, context);
        this.mergeRepositoryPolicy_UpdatePolicy(target, source2, sourceDominant, context);
        this.mergeRepositoryPolicy_ChecksumPolicy(target, source2, sourceDominant, context);
    }

    protected void mergeRepositoryPolicy_Enabled(RepositoryPolicy target, RepositoryPolicy source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getEnabled();
        if (src != null && (sourceDominant || target.getEnabled() == null)) {
            target.setEnabled(src);
            target.setLocation("enabled", source2.getLocation("enabled"));
        }
    }

    protected void mergeRepositoryPolicy_UpdatePolicy(RepositoryPolicy target, RepositoryPolicy source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUpdatePolicy();
        if (src != null && (sourceDominant || target.getUpdatePolicy() == null)) {
            target.setUpdatePolicy(src);
            target.setLocation("updatePolicy", source2.getLocation("updatePolicy"));
        }
    }

    protected void mergeRepositoryPolicy_ChecksumPolicy(RepositoryPolicy target, RepositoryPolicy source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getChecksumPolicy();
        if (src != null && (sourceDominant || target.getChecksumPolicy() == null)) {
            target.setChecksumPolicy(src);
            target.setLocation("checksumPolicy", source2.getLocation("checksumPolicy"));
        }
    }

    protected void mergeDependency(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeDependency_GroupId(target, source2, sourceDominant, context);
        this.mergeDependency_ArtifactId(target, source2, sourceDominant, context);
        this.mergeDependency_Version(target, source2, sourceDominant, context);
        this.mergeDependency_Type(target, source2, sourceDominant, context);
        this.mergeDependency_Classifier(target, source2, sourceDominant, context);
        this.mergeDependency_Scope(target, source2, sourceDominant, context);
        this.mergeDependency_SystemPath(target, source2, sourceDominant, context);
        this.mergeDependency_Optional(target, source2, sourceDominant, context);
        this.mergeDependency_Exclusions(target, source2, sourceDominant, context);
    }

    protected void mergeDependency_GroupId(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getGroupId();
        if (src != null && (sourceDominant || target.getGroupId() == null)) {
            target.setGroupId(src);
            target.setLocation("groupId", source2.getLocation("groupId"));
        }
    }

    protected void mergeDependency_ArtifactId(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArtifactId();
        if (src != null && (sourceDominant || target.getArtifactId() == null)) {
            target.setArtifactId(src);
            target.setLocation("artifactId", source2.getLocation("artifactId"));
        }
    }

    protected void mergeDependency_Version(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getVersion();
        if (src != null && (sourceDominant || target.getVersion() == null)) {
            target.setVersion(src);
            target.setLocation("version", source2.getLocation("version"));
        }
    }

    protected void mergeDependency_Type(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getType();
        if (src != null && (sourceDominant || target.getType() == null)) {
            target.setType(src);
            target.setLocation("type", source2.getLocation("type"));
        }
    }

    protected void mergeDependency_Classifier(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getClassifier();
        if (src != null && (sourceDominant || target.getClassifier() == null)) {
            target.setClassifier(src);
            target.setLocation("classifier", source2.getLocation("classifier"));
        }
    }

    protected void mergeDependency_Scope(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getScope();
        if (src != null && (sourceDominant || target.getScope() == null)) {
            target.setScope(src);
            target.setLocation("scope", source2.getLocation("scope"));
        }
    }

    protected void mergeDependency_SystemPath(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getSystemPath();
        if (src != null && (sourceDominant || target.getSystemPath() == null)) {
            target.setSystemPath(src);
            target.setLocation("systemPath", source2.getLocation("systemPath"));
        }
    }

    protected void mergeDependency_Optional(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getOptional();
        if (src != null && (sourceDominant || target.getOptional() == null)) {
            target.setOptional(src);
            target.setLocation("optional", source2.getLocation("optional"));
        }
    }

    protected void mergeDependency_Exclusions(Dependency target, Dependency source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setExclusions(ModelMerger.merge(target.getExclusions(), source2.getExclusions(), sourceDominant, new ExclusionKeyComputer()));
    }

    protected void mergeExclusion(Exclusion target, Exclusion source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeExclusion_GroupId(target, source2, sourceDominant, context);
        this.mergeExclusion_ArtifactId(target, source2, sourceDominant, context);
    }

    protected void mergeExclusion_GroupId(Exclusion target, Exclusion source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getGroupId();
        if (src != null && (sourceDominant || target.getGroupId() == null)) {
            target.setGroupId(src);
            target.setLocation("groupId", source2.getLocation("groupId"));
        }
    }

    protected void mergeExclusion_ArtifactId(Exclusion target, Exclusion source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArtifactId();
        if (src != null && (sourceDominant || target.getArtifactId() == null)) {
            target.setArtifactId(src);
            target.setLocation("artifactId", source2.getLocation("artifactId"));
        }
    }

    protected void mergeReporting(Reporting target, Reporting source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeReporting_OutputDirectory(target, source2, sourceDominant, context);
        this.mergeReporting_ExcludeDefaults(target, source2, sourceDominant, context);
        this.mergeReporting_Plugins(target, source2, sourceDominant, context);
    }

    protected void mergeReporting_OutputDirectory(Reporting target, Reporting source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getOutputDirectory();
        if (src != null && (sourceDominant || target.getOutputDirectory() == null)) {
            target.setOutputDirectory(src);
            target.setLocation("outputDirectory", source2.getLocation("outputDirectory"));
        }
    }

    protected void mergeReporting_ExcludeDefaults(Reporting target, Reporting source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getExcludeDefaults();
        if (src != null && (sourceDominant || target.getExcludeDefaults() == null)) {
            target.setExcludeDefaults(src);
            target.setLocation("excludeDefaults", source2.getLocation("excludeDefaults"));
        }
    }

    protected void mergeReporting_Plugins(Reporting target, Reporting source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setPlugins(ModelMerger.merge(target.getPlugins(), source2.getPlugins(), sourceDominant, new ReportPluginKeyComputer()));
    }

    protected void mergeReportPlugin(ReportPlugin target, ReportPlugin source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeConfigurationContainer(target, source2, sourceDominant, context);
        this.mergeReportPlugin_GroupId(target, source2, sourceDominant, context);
        this.mergeReportPlugin_ArtifactId(target, source2, sourceDominant, context);
        this.mergeReportPlugin_Version(target, source2, sourceDominant, context);
        this.mergeReportPlugin_ReportSets(target, source2, sourceDominant, context);
    }

    protected void mergeReportPlugin_GroupId(ReportPlugin target, ReportPlugin source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getGroupId();
        if (src != null && (sourceDominant || target.getGroupId() == null)) {
            target.setGroupId(src);
            target.setLocation("groupId", source2.getLocation("groupId"));
        }
    }

    protected void mergeReportPlugin_ArtifactId(ReportPlugin target, ReportPlugin source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArtifactId();
        if (src != null && (sourceDominant || target.getArtifactId() == null)) {
            target.setArtifactId(src);
            target.setLocation("artifactId", source2.getLocation("artifactId"));
        }
    }

    protected void mergeReportPlugin_Version(ReportPlugin target, ReportPlugin source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getVersion();
        if (src != null && (sourceDominant || target.getVersion() == null)) {
            target.setVersion(src);
            target.setLocation("version", source2.getLocation("version"));
        }
    }

    protected void mergeReportPlugin_ReportSets(ReportPlugin target, ReportPlugin source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setReportSets(ModelMerger.merge(target.getReportSets(), source2.getReportSets(), sourceDominant, new ReportSetKeyComputer()));
    }

    protected void mergeReportSet(ReportSet target, ReportSet source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeConfigurationContainer(target, source2, sourceDominant, context);
        this.mergeReportSet_Id(target, source2, sourceDominant, context);
        this.mergeReportSet_Reports(target, source2, sourceDominant, context);
    }

    protected void mergeReportSet_Id(ReportSet target, ReportSet source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getId();
        if (src != null && (sourceDominant || target.getId() == null)) {
            target.setId(src);
            target.setLocation("id", source2.getLocation("id"));
        }
    }

    protected void mergeReportSet_Reports(ReportSet target, ReportSet source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getReports();
        if (!src.isEmpty()) {
            List<String> tgt = target.getReports();
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            merged.addAll(src);
            target.setReports(merged);
            InputLocation sourceLocation = source2.getLocation("reports");
            if (sourceLocation != null) {
                InputLocation targetLocation = target.getLocation("reports");
                if (targetLocation == null) {
                    target.setLocation("reports", sourceLocation);
                } else {
                    for (int i = 0; i < src.size(); ++i) {
                        targetLocation.setLocation(tgt.size() + i, sourceLocation.getLocation(i));
                    }
                }
            }
        }
    }

    protected void mergeDependencyManagement(DependencyManagement target, DependencyManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeDependencyManagement_Dependencies(target, source2, sourceDominant, context);
    }

    protected void mergeDependencyManagement_Dependencies(DependencyManagement target, DependencyManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setDependencies(ModelMerger.merge(target.getDependencies(), source2.getDependencies(), sourceDominant, new DependencyKeyComputer()));
    }

    protected void mergeParent(Parent target, Parent source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeParent_GroupId(target, source2, sourceDominant, context);
        this.mergeParent_ArtifactId(target, source2, sourceDominant, context);
        this.mergeParent_Version(target, source2, sourceDominant, context);
        this.mergeParent_RelativePath(target, source2, sourceDominant, context);
    }

    protected void mergeParent_GroupId(Parent target, Parent source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getGroupId();
        if (src != null && (sourceDominant || target.getGroupId() == null)) {
            target.setGroupId(src);
            target.setLocation("groupId", source2.getLocation("groupId"));
        }
    }

    protected void mergeParent_ArtifactId(Parent target, Parent source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArtifactId();
        if (src != null && (sourceDominant || target.getArtifactId() == null)) {
            target.setArtifactId(src);
            target.setLocation("artifactId", source2.getLocation("artifactId"));
        }
    }

    protected void mergeParent_Version(Parent target, Parent source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getVersion();
        if (src != null && (sourceDominant || target.getVersion() == null)) {
            target.setVersion(src);
            target.setLocation("version", source2.getLocation("version"));
        }
    }

    protected void mergeParent_RelativePath(Parent target, Parent source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getRelativePath();
        if (src != null && (sourceDominant || target.getRelativePath() == null)) {
            target.setRelativePath(src);
            target.setLocation("relativePath", source2.getLocation("relativePath"));
        }
    }

    protected void mergeOrganization(Organization target, Organization source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeOrganization_Name(target, source2, sourceDominant, context);
        this.mergeOrganization_Url(target, source2, sourceDominant, context);
    }

    protected void mergeOrganization_Name(Organization target, Organization source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getName();
        if (src != null && (sourceDominant || target.getName() == null)) {
            target.setName(src);
            target.setLocation("name", source2.getLocation("name"));
        }
    }

    protected void mergeOrganization_Url(Organization target, Organization source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeLicense(License target, License source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeLicense_Name(target, source2, sourceDominant, context);
        this.mergeLicense_Url(target, source2, sourceDominant, context);
        this.mergeLicense_Distribution(target, source2, sourceDominant, context);
        this.mergeLicense_Comments(target, source2, sourceDominant, context);
    }

    protected void mergeLicense_Name(License target, License source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getName();
        if (src != null && (sourceDominant || target.getName() == null)) {
            target.setName(src);
            target.setLocation("name", source2.getLocation("name"));
        }
    }

    protected void mergeLicense_Url(License target, License source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeLicense_Distribution(License target, License source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getDistribution();
        if (src != null && (sourceDominant || target.getDistribution() == null)) {
            target.setDistribution(src);
            target.setLocation("distribution", source2.getLocation("distribution"));
        }
    }

    protected void mergeLicense_Comments(License target, License source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getComments();
        if (src != null && (sourceDominant || target.getComments() == null)) {
            target.setComments(src);
            target.setLocation("comments", source2.getLocation("comments"));
        }
    }

    protected void mergeMailingList(MailingList target, MailingList source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeMailingList_Name(target, source2, sourceDominant, context);
        this.mergeMailingList_Subscribe(target, source2, sourceDominant, context);
        this.mergeMailingList_Unsubscribe(target, source2, sourceDominant, context);
        this.mergeMailingList_Post(target, source2, sourceDominant, context);
        this.mergeMailingList_OtherArchives(target, source2, sourceDominant, context);
    }

    protected void mergeMailingList_Name(MailingList target, MailingList source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getName();
        if (src != null && (sourceDominant || target.getName() == null)) {
            target.setName(src);
            target.setLocation("name", source2.getLocation("name"));
        }
    }

    protected void mergeMailingList_Subscribe(MailingList target, MailingList source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getSubscribe();
        if (src != null && (sourceDominant || target.getSubscribe() == null)) {
            target.setSubscribe(src);
            target.setLocation("subscribe", source2.getLocation("subscribe"));
        }
    }

    protected void mergeMailingList_Unsubscribe(MailingList target, MailingList source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUnsubscribe();
        if (src != null && (sourceDominant || target.getUnsubscribe() == null)) {
            target.setUnsubscribe(src);
            target.setLocation("unsubscribe", source2.getLocation("unsubscribe"));
        }
    }

    protected void mergeMailingList_Post(MailingList target, MailingList source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getPost();
        if (src != null && (sourceDominant || target.getPost() == null)) {
            target.setPost(src);
            target.setLocation("post", source2.getLocation("post"));
        }
    }

    protected void mergeMailingList_Archive(MailingList target, MailingList source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArchive();
        if (src != null && (sourceDominant || target.getArchive() == null)) {
            target.setArchive(src);
            target.setLocation("archive", source2.getLocation("archive"));
        }
    }

    protected void mergeMailingList_OtherArchives(MailingList target, MailingList source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getOtherArchives();
        if (!src.isEmpty()) {
            List<String> tgt = target.getOtherArchives();
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            merged.addAll(src);
            target.setOtherArchives(merged);
        }
    }

    protected void mergeDeveloper(Developer target, Developer source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeContributor(target, source2, sourceDominant, context);
        this.mergeDeveloper_Id(target, source2, sourceDominant, context);
    }

    protected void mergeDeveloper_Id(Developer target, Developer source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getId();
        if (src != null && (sourceDominant || target.getId() == null)) {
            target.setId(src);
            target.setLocation("id", source2.getLocation("id"));
        }
    }

    protected void mergeContributor(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeContributor_Name(target, source2, sourceDominant, context);
        this.mergeContributor_Email(target, source2, sourceDominant, context);
        this.mergeContributor_Url(target, source2, sourceDominant, context);
        this.mergeContributor_Organization(target, source2, sourceDominant, context);
        this.mergeContributor_OrganizationUrl(target, source2, sourceDominant, context);
        this.mergeContributor_Timezone(target, source2, sourceDominant, context);
        this.mergeContributor_Roles(target, source2, sourceDominant, context);
        this.mergeContributor_Properties(target, source2, sourceDominant, context);
    }

    protected void mergeContributor_Name(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getName();
        if (src != null && (sourceDominant || target.getName() == null)) {
            target.setName(src);
            target.setLocation("name", source2.getLocation("name"));
        }
    }

    protected void mergeContributor_Email(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getEmail();
        if (src != null && (sourceDominant || target.getEmail() == null)) {
            target.setEmail(src);
            target.setLocation("email", source2.getLocation("email"));
        }
    }

    protected void mergeContributor_Url(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeContributor_Organization(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getOrganization();
        if (src != null && (sourceDominant || target.getOrganization() == null)) {
            target.setOrganization(src);
            target.setLocation("organization", source2.getLocation("organization"));
        }
    }

    protected void mergeContributor_OrganizationUrl(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getOrganizationUrl();
        if (src != null && (sourceDominant || target.getOrganizationUrl() == null)) {
            target.setOrganizationUrl(src);
            target.setLocation("organizationUrl", source2.getLocation("organizationUrl"));
        }
    }

    protected void mergeContributor_Timezone(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getTimezone();
        if (src != null && (sourceDominant || target.getTimezone() == null)) {
            target.setTimezone(src);
            target.setLocation("timezone", source2.getLocation("timezone"));
        }
    }

    protected void mergeContributor_Roles(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getRoles();
        if (!src.isEmpty()) {
            List<String> tgt = target.getRoles();
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            merged.addAll(src);
            target.setRoles(merged);
        }
    }

    protected void mergeContributor_Properties(Contributor target, Contributor source2, boolean sourceDominant, Map<Object, Object> context) {
        Properties merged = new Properties();
        if (sourceDominant) {
            merged.putAll((Map<?, ?>)target.getProperties());
            merged.putAll((Map<?, ?>)source2.getProperties());
        } else {
            merged.putAll((Map<?, ?>)source2.getProperties());
            merged.putAll((Map<?, ?>)target.getProperties());
        }
        target.setProperties(merged);
        target.setLocation("properties", InputLocation.merge(target.getLocation("properties"), source2.getLocation("properties"), sourceDominant));
    }

    protected void mergeIssueManagement(IssueManagement target, IssueManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeIssueManagement_Url(target, source2, sourceDominant, context);
        this.mergeIssueManagement_System(target, source2, sourceDominant, context);
    }

    protected void mergeIssueManagement_System(IssueManagement target, IssueManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getSystem();
        if (src != null && (sourceDominant || target.getSystem() == null)) {
            target.setSystem(src);
            target.setLocation("system", source2.getLocation("system"));
        }
    }

    protected void mergeIssueManagement_Url(IssueManagement target, IssueManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeScm(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeScm_ChildScmConnectionInheritAppendPath(target, source2, sourceDominant, context);
        this.mergeScm_ChildScmDeveloperConnectionInheritAppendPath(target, source2, sourceDominant, context);
        this.mergeScm_ChildScmUrlInheritAppendPath(target, source2, sourceDominant, context);
        this.mergeScm_Url(target, source2, sourceDominant, context);
        this.mergeScm_Connection(target, source2, sourceDominant, context);
        this.mergeScm_DeveloperConnection(target, source2, sourceDominant, context);
        this.mergeScm_Tag(target, source2, sourceDominant, context);
    }

    protected void mergeScm_ChildScmConnectionInheritAppendPath(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getChildScmConnectionInheritAppendPath();
        if (src != null && (sourceDominant || target.getChildScmConnectionInheritAppendPath() == null)) {
            target.setChildScmConnectionInheritAppendPath(src);
            target.setLocation("child.scm.connection.inherit.append.path", source2.getLocation("child.scm.connection.inherit.append.path"));
        }
    }

    protected void mergeScm_ChildScmDeveloperConnectionInheritAppendPath(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getChildScmDeveloperConnectionInheritAppendPath();
        if (src != null && (sourceDominant || target.getChildScmDeveloperConnectionInheritAppendPath() == null)) {
            target.setChildScmDeveloperConnectionInheritAppendPath(src);
            target.setLocation("child.scm.developerConnection.inherit.append.path", source2.getLocation("child.scm.developerConnection.inherit.append.path"));
        }
    }

    protected void mergeScm_ChildScmUrlInheritAppendPath(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getChildScmUrlInheritAppendPath();
        if (src != null && (sourceDominant || target.getChildScmUrlInheritAppendPath() == null)) {
            target.setChildScmUrlInheritAppendPath(src);
            target.setLocation("child.scm.url.inherit.append.path", source2.getLocation("child.scm.url.inherit.append.path"));
        }
    }

    protected void mergeScm_Url(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeScm_Connection(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getConnection();
        if (src != null && (sourceDominant || target.getConnection() == null)) {
            target.setConnection(src);
            target.setLocation("connection", source2.getLocation("connection"));
        }
    }

    protected void mergeScm_DeveloperConnection(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getDeveloperConnection();
        if (src != null && (sourceDominant || target.getDeveloperConnection() == null)) {
            target.setDeveloperConnection(src);
            target.setLocation("developerConnection", source2.getLocation("developerConnection"));
        }
    }

    protected void mergeScm_Tag(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getTag();
        if (src != null && (sourceDominant || target.getTag() == null)) {
            target.setTag(src);
            target.setLocation("tag", source2.getLocation("tag"));
        }
    }

    protected void mergeCiManagement(CiManagement target, CiManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeCiManagement_System(target, source2, sourceDominant, context);
        this.mergeCiManagement_Url(target, source2, sourceDominant, context);
        this.mergeCiManagement_Notifiers(target, source2, sourceDominant, context);
    }

    protected void mergeCiManagement_System(CiManagement target, CiManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getSystem();
        if (src != null && (sourceDominant || target.getSystem() == null)) {
            target.setSystem(src);
            target.setLocation("system", source2.getLocation("system"));
        }
    }

    protected void mergeCiManagement_Url(CiManagement target, CiManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null && (sourceDominant || target.getUrl() == null)) {
            target.setUrl(src);
            target.setLocation("url", source2.getLocation("url"));
        }
    }

    protected void mergeCiManagement_Notifiers(CiManagement target, CiManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setNotifiers(ModelMerger.merge(target.getNotifiers(), source2.getNotifiers(), sourceDominant, new NotifierKeyComputer()));
    }

    protected void mergeNotifier(Notifier target, Notifier source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeNotifier_Type(target, source2, sourceDominant, context);
        this.mergeNotifier_Address(target, source2, sourceDominant, context);
        this.mergeNotifier_Configuration(target, source2, sourceDominant, context);
        this.mergeNotifier_SendOnError(target, source2, sourceDominant, context);
        this.mergeNotifier_SendOnFailure(target, source2, sourceDominant, context);
        this.mergeNotifier_SendOnSuccess(target, source2, sourceDominant, context);
        this.mergeNotifier_SendOnWarning(target, source2, sourceDominant, context);
    }

    protected void mergeNotifier_Type(Notifier target, Notifier source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getType();
        if (src != null && (sourceDominant || target.getType() == null)) {
            target.setType(src);
        }
    }

    protected void mergeNotifier_Address(Notifier target, Notifier source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getAddress();
        if (src != null && (sourceDominant || target.getAddress() == null)) {
            target.setAddress(src);
        }
    }

    protected void mergeNotifier_Configuration(Notifier target, Notifier source2, boolean sourceDominant, Map<Object, Object> context) {
        Properties merged = new Properties();
        if (sourceDominant) {
            merged.putAll((Map<?, ?>)target.getConfiguration());
            merged.putAll((Map<?, ?>)source2.getConfiguration());
        } else {
            merged.putAll((Map<?, ?>)source2.getConfiguration());
            merged.putAll((Map<?, ?>)target.getConfiguration());
        }
        target.setConfiguration(merged);
    }

    protected void mergeNotifier_SendOnError(Notifier target, Notifier source2, boolean sourceDominant, Map<Object, Object> context) {
        if (sourceDominant) {
            target.setSendOnError(source2.isSendOnError());
        }
    }

    protected void mergeNotifier_SendOnFailure(Notifier target, Notifier source2, boolean sourceDominant, Map<Object, Object> context) {
        if (sourceDominant) {
            target.setSendOnFailure(source2.isSendOnFailure());
        }
    }

    protected void mergeNotifier_SendOnSuccess(Notifier target, Notifier source2, boolean sourceDominant, Map<Object, Object> context) {
        if (sourceDominant) {
            target.setSendOnSuccess(source2.isSendOnSuccess());
        }
    }

    protected void mergeNotifier_SendOnWarning(Notifier target, Notifier source2, boolean sourceDominant, Map<Object, Object> context) {
        if (sourceDominant) {
            target.setSendOnWarning(source2.isSendOnWarning());
        }
    }

    protected void mergePrerequisites(Prerequisites target, Prerequisites source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergePrerequisites_Maven(target, source2, sourceDominant, context);
    }

    protected void mergePrerequisites_Maven(Prerequisites target, Prerequisites source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getMaven();
        if (src != null && (sourceDominant || target.getMaven() == null)) {
            target.setMaven(src);
            target.setLocation("maven", source2.getLocation("maven"));
        }
    }

    protected void mergeBuild(Build target, Build source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeBuildBase(target, source2, sourceDominant, context);
        this.mergeBuild_SourceDirectory(target, source2, sourceDominant, context);
        this.mergeBuild_ScriptSourceDirectory(target, source2, sourceDominant, context);
        this.mergeBuild_TestSourceDirectory(target, source2, sourceDominant, context);
        this.mergeBuild_OutputDirectory(target, source2, sourceDominant, context);
        this.mergeBuild_TestOutputDirectory(target, source2, sourceDominant, context);
        this.mergeBuild_Extensions(target, source2, sourceDominant, context);
    }

    protected void mergeBuild_SourceDirectory(Build target, Build source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getSourceDirectory();
        if (src != null && (sourceDominant || target.getSourceDirectory() == null)) {
            target.setSourceDirectory(src);
            target.setLocation("sourceDirectory", source2.getLocation("sourceDirectory"));
        }
    }

    protected void mergeBuild_ScriptSourceDirectory(Build target, Build source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getScriptSourceDirectory();
        if (src != null && (sourceDominant || target.getScriptSourceDirectory() == null)) {
            target.setScriptSourceDirectory(src);
            target.setLocation("scriptSourceDirectory", source2.getLocation("scriptSourceDirectory"));
        }
    }

    protected void mergeBuild_TestSourceDirectory(Build target, Build source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getTestSourceDirectory();
        if (src != null && (sourceDominant || target.getTestSourceDirectory() == null)) {
            target.setTestSourceDirectory(src);
            target.setLocation("testSourceDirectory", source2.getLocation("testSourceDirectory"));
        }
    }

    protected void mergeBuild_OutputDirectory(Build target, Build source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getOutputDirectory();
        if (src != null && (sourceDominant || target.getOutputDirectory() == null)) {
            target.setOutputDirectory(src);
            target.setLocation("outputDirectory", source2.getLocation("outputDirectory"));
        }
    }

    protected void mergeBuild_TestOutputDirectory(Build target, Build source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getTestOutputDirectory();
        if (src != null && (sourceDominant || target.getTestOutputDirectory() == null)) {
            target.setTestOutputDirectory(src);
            target.setLocation("testOutputDirectory", source2.getLocation("testOutputDirectory"));
        }
    }

    protected void mergeBuild_Extensions(Build target, Build source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setExtensions(ModelMerger.merge(target.getExtensions(), source2.getExtensions(), sourceDominant, new ExtensionKeyComputer()));
    }

    protected void mergeExtension(Extension target, Extension source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeExtension_GroupId(target, source2, sourceDominant, context);
        this.mergeExtension_ArtifactId(target, source2, sourceDominant, context);
        this.mergeExtension_Version(target, source2, sourceDominant, context);
    }

    protected void mergeExtension_GroupId(Extension target, Extension source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getGroupId();
        if (src != null && (sourceDominant || target.getGroupId() == null)) {
            target.setGroupId(src);
            target.setLocation("groupId", source2.getLocation("groupId"));
        }
    }

    protected void mergeExtension_ArtifactId(Extension target, Extension source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArtifactId();
        if (src != null && (sourceDominant || target.getArtifactId() == null)) {
            target.setArtifactId(src);
            target.setLocation("artifactId", source2.getLocation("artifactId"));
        }
    }

    protected void mergeExtension_Version(Extension target, Extension source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getVersion();
        if (src != null && (sourceDominant || target.getVersion() == null)) {
            target.setVersion(src);
            target.setLocation("version", source2.getLocation("version"));
        }
    }

    protected void mergeBuildBase(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergePluginConfiguration(target, source2, sourceDominant, context);
        this.mergeBuildBase_DefaultGoal(target, source2, sourceDominant, context);
        this.mergeBuildBase_FinalName(target, source2, sourceDominant, context);
        this.mergeBuildBase_Directory(target, source2, sourceDominant, context);
        this.mergeBuildBase_Resources(target, source2, sourceDominant, context);
        this.mergeBuildBase_TestResources(target, source2, sourceDominant, context);
        this.mergeBuildBase_Filters(target, source2, sourceDominant, context);
    }

    protected void mergeBuildBase_DefaultGoal(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getDefaultGoal();
        if (src != null && (sourceDominant || target.getDefaultGoal() == null)) {
            target.setDefaultGoal(src);
            target.setLocation("defaultGoal", source2.getLocation("defaultGoal"));
        }
    }

    protected void mergeBuildBase_Directory(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getDirectory();
        if (src != null && (sourceDominant || target.getDirectory() == null)) {
            target.setDirectory(src);
            target.setLocation("directory", source2.getLocation("directory"));
        }
    }

    protected void mergeBuildBase_FinalName(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getFinalName();
        if (src != null && (sourceDominant || target.getFinalName() == null)) {
            target.setFinalName(src);
            target.setLocation("finalName", source2.getLocation("finalName"));
        }
    }

    protected void mergeBuildBase_Filters(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getFilters();
        if (!src.isEmpty()) {
            List<String> tgt = target.getFilters();
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            merged.addAll(src);
            target.setFilters(merged);
        }
    }

    protected void mergeBuildBase_Resources(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setResources(ModelMerger.merge(target.getResources(), source2.getResources(), sourceDominant, new ResourceKeyComputer()));
    }

    protected void mergeBuildBase_TestResources(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setTestResources(ModelMerger.merge(target.getTestResources(), source2.getTestResources(), sourceDominant, new ResourceKeyComputer()));
    }

    protected void mergePluginConfiguration(PluginConfiguration target, PluginConfiguration source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergePluginContainer(target, source2, sourceDominant, context);
        this.mergePluginConfiguration_PluginManagement(target, source2, sourceDominant, context);
    }

    protected void mergePluginConfiguration_PluginManagement(PluginConfiguration target, PluginConfiguration source2, boolean sourceDominant, Map<Object, Object> context) {
        PluginManagement src = source2.getPluginManagement();
        if (src != null) {
            PluginManagement tgt = target.getPluginManagement();
            if (tgt == null) {
                tgt = new PluginManagement();
                target.setPluginManagement(tgt);
            }
            this.mergePluginManagement(tgt, src, sourceDominant, context);
        }
    }

    protected void mergePluginContainer(PluginContainer target, PluginContainer source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergePluginContainer_Plugins(target, source2, sourceDominant, context);
    }

    protected void mergePluginContainer_Plugins(PluginContainer target, PluginContainer source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setPlugins(ModelMerger.merge(target.getPlugins(), source2.getPlugins(), sourceDominant, new PluginKeyComputer()));
    }

    protected void mergePluginManagement(PluginManagement target, PluginManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergePluginContainer(target, source2, sourceDominant, context);
    }

    protected void mergePlugin(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeConfigurationContainer(target, source2, sourceDominant, context);
        this.mergePlugin_GroupId(target, source2, sourceDominant, context);
        this.mergePlugin_ArtifactId(target, source2, sourceDominant, context);
        this.mergePlugin_Version(target, source2, sourceDominant, context);
        this.mergePlugin_Extensions(target, source2, sourceDominant, context);
        this.mergePlugin_Dependencies(target, source2, sourceDominant, context);
        this.mergePlugin_Executions(target, source2, sourceDominant, context);
    }

    protected void mergePlugin_GroupId(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getGroupId();
        if (src != null && (sourceDominant || target.getGroupId() == null)) {
            target.setGroupId(src);
            target.setLocation("groupId", source2.getLocation("groupId"));
        }
    }

    protected void mergePlugin_ArtifactId(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getArtifactId();
        if (src != null && (sourceDominant || target.getArtifactId() == null)) {
            target.setArtifactId(src);
            target.setLocation("artifactId", source2.getLocation("artifactId"));
        }
    }

    protected void mergePlugin_Version(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getVersion();
        if (src != null && (sourceDominant || target.getVersion() == null)) {
            target.setVersion(src);
            target.setLocation("version", source2.getLocation("version"));
        }
    }

    protected void mergePlugin_Extensions(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getExtensions();
        if (src != null && (sourceDominant || target.getExtensions() == null)) {
            target.setExtensions(src);
            target.setLocation("extensions", source2.getLocation("extensions"));
        }
    }

    protected void mergePlugin_Dependencies(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setDependencies(ModelMerger.merge(target.getDependencies(), source2.getDependencies(), sourceDominant, new DependencyKeyComputer()));
    }

    protected void mergePlugin_Executions(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
        target.setExecutions(ModelMerger.merge(target.getExecutions(), source2.getExecutions(), sourceDominant, new ExecutionKeyComputer()));
    }

    protected void mergeConfigurationContainer(ConfigurationContainer target, ConfigurationContainer source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeConfigurationContainer_Inherited(target, source2, sourceDominant, context);
        this.mergeConfigurationContainer_Configuration(target, source2, sourceDominant, context);
    }

    protected void mergeConfigurationContainer_Inherited(ConfigurationContainer target, ConfigurationContainer source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getInherited();
        if (src != null && (sourceDominant || target.getInherited() == null)) {
            target.setInherited(src);
            target.setLocation("inherited", source2.getLocation("inherited"));
        }
    }

    protected void mergeConfigurationContainer_Configuration(ConfigurationContainer target, ConfigurationContainer source2, boolean sourceDominant, Map<Object, Object> context) {
        Xpp3Dom src = (Xpp3Dom)source2.getConfiguration();
        if (src != null) {
            Xpp3Dom tgt = (Xpp3Dom)target.getConfiguration();
            tgt = sourceDominant || tgt == null ? Xpp3Dom.mergeXpp3Dom(new Xpp3Dom(src), tgt) : Xpp3Dom.mergeXpp3Dom(tgt, src);
            target.setConfiguration(tgt);
        }
    }

    protected void mergePluginExecution(PluginExecution target, PluginExecution source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeConfigurationContainer(target, source2, sourceDominant, context);
        this.mergePluginExecution_Id(target, source2, sourceDominant, context);
        this.mergePluginExecution_Phase(target, source2, sourceDominant, context);
        this.mergePluginExecution_Goals(target, source2, sourceDominant, context);
    }

    protected void mergePluginExecution_Id(PluginExecution target, PluginExecution source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getId();
        if (src != null && (sourceDominant || target.getId() == null)) {
            target.setId(src);
            target.setLocation("id", source2.getLocation("id"));
        }
    }

    protected void mergePluginExecution_Phase(PluginExecution target, PluginExecution source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getPhase();
        if (src != null && (sourceDominant || target.getPhase() == null)) {
            target.setPhase(src);
            target.setLocation("phase", source2.getLocation("phase"));
        }
    }

    protected void mergePluginExecution_Goals(PluginExecution target, PluginExecution source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getGoals();
        if (!src.isEmpty()) {
            List<String> tgt = target.getGoals();
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            merged.addAll(src);
            target.setGoals(merged);
        }
    }

    protected void mergeResource(Resource target, Resource source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeFileSet(target, source2, sourceDominant, context);
        this.mergeResource_TargetPath(target, source2, sourceDominant, context);
        this.mergeResource_Filtering(target, source2, sourceDominant, context);
        this.mergeResource_MergeId(target, source2, sourceDominant, context);
    }

    protected void mergeResource_TargetPath(Resource target, Resource source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getTargetPath();
        if (src != null && (sourceDominant || target.getTargetPath() == null)) {
            target.setTargetPath(src);
            target.setLocation("targetPath", source2.getLocation("targetPath"));
        }
    }

    protected void mergeResource_Filtering(Resource target, Resource source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getFiltering();
        if (src != null && (sourceDominant || target.getFiltering() == null)) {
            target.setFiltering(src);
            target.setLocation("filtering", source2.getLocation("filtering"));
        }
    }

    protected void mergeResource_MergeId(Resource target, Resource source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getMergeId();
        if (src != null && (sourceDominant || target.getMergeId() == null)) {
            target.setMergeId(src);
        }
    }

    protected void mergeFileSet(FileSet target, FileSet source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergePatternSet(target, source2, sourceDominant, context);
        this.mergeFileSet_Directory(target, source2, sourceDominant, context);
    }

    protected void mergeFileSet_Directory(FileSet target, FileSet source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getDirectory();
        if (src != null && (sourceDominant || target.getDirectory() == null)) {
            target.setDirectory(src);
            target.setLocation("directory", source2.getLocation("directory"));
        }
    }

    protected void mergePatternSet(PatternSet target, PatternSet source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergePatternSet_Includes(target, source2, sourceDominant, context);
        this.mergePatternSet_Excludes(target, source2, sourceDominant, context);
    }

    protected void mergePatternSet_Includes(PatternSet target, PatternSet source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getIncludes();
        if (!src.isEmpty()) {
            List<String> tgt = target.getIncludes();
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            merged.addAll(src);
            target.setIncludes(merged);
        }
    }

    protected void mergePatternSet_Excludes(PatternSet target, PatternSet source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getExcludes();
        if (!src.isEmpty()) {
            List<String> tgt = target.getExcludes();
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            merged.addAll(src);
            target.setExcludes(merged);
        }
    }

    protected void mergeProfile(Profile target, Profile source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeModelBase(target, source2, sourceDominant, context);
    }

    protected void mergeActivation(Activation target, Activation source2, boolean sourceDominant, Map<Object, Object> context) {
    }

    protected Object getDependencyKey(Dependency dependency) {
        return dependency;
    }

    protected Object getPluginKey(Plugin plugin) {
        return plugin;
    }

    protected Object getPluginExecutionKey(PluginExecution pluginExecution) {
        return pluginExecution;
    }

    protected Object getReportPluginKey(ReportPlugin reportPlugin) {
        return reportPlugin;
    }

    protected Object getReportSetKey(ReportSet reportSet) {
        return reportSet;
    }

    protected Object getLicenseKey(License license) {
        return license;
    }

    protected Object getMailingListKey(MailingList mailingList) {
        return mailingList;
    }

    protected Object getDeveloperKey(Developer developer) {
        return developer;
    }

    protected Object getContributorKey(Contributor contributor) {
        return contributor;
    }

    protected Object getProfileKey(Profile profile) {
        return profile;
    }

    protected Object getRepositoryKey(Repository repository) {
        return this.getRepositoryBaseKey(repository);
    }

    protected Object getRepositoryBaseKey(RepositoryBase repositoryBase) {
        return repositoryBase;
    }

    protected Object getNotifierKey(Notifier notifier) {
        return notifier;
    }

    protected Object getResourceKey(Resource resource) {
        return resource;
    }

    protected Object getExtensionKey(Extension extension) {
        return extension;
    }

    protected Object getExclusionKey(Exclusion exclusion) {
        return exclusion;
    }

    private static <T> List<T> merge(List<T> tgt, List<T> src, boolean sourceDominant, KeyComputer<T> computer) {
        return ModelMerger.merge(tgt, src, computer, new SourceDominant(sourceDominant));
    }

    private static <T> List<T> merge(List<T> tgt, List<T> src, KeyComputer<T> computer, Remapping<T> remapping) {
        MergingList<T> list;
        if (src.isEmpty()) {
            return tgt;
        }
        if (tgt instanceof MergingList) {
            list = (MergingList<T>)tgt;
        } else {
            list = new MergingList<T>(computer, src.size() + tgt.size());
            list.mergeAll(tgt, new SourceDominant(true));
        }
        list.mergeAll(src, remapping);
        return list;
    }

    private class LicenseKeyComputer
    implements KeyComputer<License> {
        private LicenseKeyComputer() {
        }

        @Override
        public Object key(License license) {
            return ModelMerger.this.getLicenseKey(license);
        }
    }

    private static interface KeyComputer<T> {
        public Object key(T var1);
    }

    private class MailingListKeyComputer
    implements KeyComputer<MailingList> {
        private MailingListKeyComputer() {
        }

        @Override
        public Object key(MailingList mailingList) {
            return ModelMerger.this.getMailingListKey(mailingList);
        }
    }

    private class DeveloperKeyComputer
    implements KeyComputer<Developer> {
        private DeveloperKeyComputer() {
        }

        @Override
        public Object key(Developer developer) {
            return ModelMerger.this.getDeveloperKey(developer);
        }
    }

    private class ContributorKeyComputer
    implements KeyComputer<Contributor> {
        private ContributorKeyComputer() {
        }

        @Override
        public Object key(Contributor contributor) {
            return ModelMerger.this.getContributorKey(contributor);
        }
    }

    private class ProfileKeyComputer
    implements KeyComputer<Profile> {
        private ProfileKeyComputer() {
        }

        @Override
        public Object key(Profile profile) {
            return ModelMerger.this.getProfileKey(profile);
        }
    }

    private final class DependencyKeyComputer
    implements KeyComputer<Dependency> {
        private DependencyKeyComputer() {
        }

        @Override
        public Object key(Dependency dependency) {
            return ModelMerger.this.getDependencyKey(dependency);
        }
    }

    private class RepositoryKeyComputer
    implements KeyComputer<Repository> {
        private RepositoryKeyComputer() {
        }

        @Override
        public Object key(Repository repository) {
            return ModelMerger.this.getRepositoryKey(repository);
        }
    }

    private class ExclusionKeyComputer
    implements KeyComputer<Exclusion> {
        private ExclusionKeyComputer() {
        }

        @Override
        public Object key(Exclusion exclusion) {
            return ModelMerger.this.getExclusionKey(exclusion);
        }
    }

    private class ReportPluginKeyComputer
    implements KeyComputer<ReportPlugin> {
        private ReportPluginKeyComputer() {
        }

        @Override
        public Object key(ReportPlugin plugin) {
            return ModelMerger.this.getReportPluginKey(plugin);
        }
    }

    private class ReportSetKeyComputer
    implements KeyComputer<ReportSet> {
        private ReportSetKeyComputer() {
        }

        @Override
        public Object key(ReportSet reportSet) {
            return ModelMerger.this.getReportSetKey(reportSet);
        }
    }

    private class NotifierKeyComputer
    implements KeyComputer<Notifier> {
        private NotifierKeyComputer() {
        }

        @Override
        public Object key(Notifier notifier) {
            return ModelMerger.this.getNotifierKey(notifier);
        }
    }

    private class ExtensionKeyComputer
    implements KeyComputer<Extension> {
        private ExtensionKeyComputer() {
        }

        @Override
        public Object key(Extension extension) {
            return ModelMerger.this.getExtensionKey(extension);
        }
    }

    private class ResourceKeyComputer
    implements KeyComputer<Resource> {
        private ResourceKeyComputer() {
        }

        @Override
        public Object key(Resource resource) {
            return ModelMerger.this.getResourceKey(resource);
        }
    }

    private class PluginKeyComputer
    implements KeyComputer<Plugin> {
        private PluginKeyComputer() {
        }

        @Override
        public Object key(Plugin plugin) {
            return ModelMerger.this.getPluginKey(plugin);
        }
    }

    private class ExecutionKeyComputer
    implements KeyComputer<PluginExecution> {
        private ExecutionKeyComputer() {
        }

        @Override
        public Object key(PluginExecution pluginExecution) {
            return ModelMerger.this.getPluginExecutionKey(pluginExecution);
        }
    }

    private static class SourceDominant<T>
    implements Remapping<T> {
        private final boolean sourceDominant;

        SourceDominant(boolean sourceDominant) {
            this.sourceDominant = sourceDominant;
        }

        @Override
        public T merge(T u, T v) {
            return this.sourceDominant ? v : u;
        }
    }

    private static interface Remapping<T> {
        public T merge(T var1, T var2);
    }

    private static class MergingList<V>
    extends AbstractList<V>
    implements Serializable {
        private final KeyComputer<V> keyComputer;
        private Map<Object, V> map;
        private List<V> list;

        MergingList(KeyComputer<V> keyComputer, int initialCapacity) {
            this.map = new LinkedHashMap<Object, V>(initialCapacity);
            this.keyComputer = keyComputer;
        }

        Object writeReplace() throws ObjectStreamException {
            return new ArrayList(this);
        }

        @Override
        public Iterator<V> iterator() {
            if (this.map != null) {
                return this.map.values().iterator();
            }
            return this.list.iterator();
        }

        void mergeAll(Collection<V> vs, Remapping<V> remapping) {
            if (this.map == null) {
                this.map = new LinkedHashMap<Object, V>(this.list.size() + vs.size());
                for (Object object : this.list) {
                    this.map.put(this.keyComputer.key(object), object);
                }
                this.list = null;
            }
            if (vs instanceof MergingList && ((MergingList)vs).map != null) {
                for (Map.Entry entry : ((MergingList)vs).map.entrySet()) {
                    Object newValue;
                    Object key = entry.getKey();
                    V oldValue = this.map.get(key);
                    Object v = newValue = oldValue == null ? entry.getValue() : remapping.merge(oldValue, entry.getValue());
                    if (newValue == null) {
                        this.remove(key);
                        continue;
                    }
                    if (newValue == oldValue) continue;
                    this.map.put(key, newValue);
                }
            } else {
                for (V v : vs) {
                    V newValue;
                    Object key = this.keyComputer.key(v);
                    V oldValue = this.map.get(key);
                    V v2 = newValue = oldValue == null ? v : remapping.merge(oldValue, v);
                    if (newValue == null) {
                        this.remove(key);
                        continue;
                    }
                    this.map.put(key, newValue);
                }
            }
        }

        @Override
        public boolean contains(Object o) {
            if (this.map != null) {
                return this.map.containsValue(o);
            }
            return this.list.contains(o);
        }

        private List<V> asList() {
            if (this.list == null) {
                this.list = new ArrayList<V>(this.map.values());
                this.map = null;
            }
            return this.list;
        }

        @Override
        public void add(int index, V element) {
            this.asList().add(index, element);
        }

        @Override
        public V remove(int index) {
            return this.asList().remove(index);
        }

        @Override
        public V get(int index) {
            return this.asList().get(index);
        }

        @Override
        public int size() {
            if (this.map != null) {
                return this.map.size();
            }
            return this.list.size();
        }
    }
}

