/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.merge;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.CiManagement;
import org.apache.maven.model.Contributor;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DeploymentRepository;
import org.apache.maven.model.Developer;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Extension;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.IssueManagement;
import org.apache.maven.model.License;
import org.apache.maven.model.MailingList;
import org.apache.maven.model.Model;
import org.apache.maven.model.ModelBase;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.ReportSet;
import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryBase;
import org.apache.maven.model.Scm;
import org.apache.maven.model.Site;
import org.apache.maven.model.merge.ModelMerger;
import org.codehaus.plexus.util.StringUtils;

public class MavenModelMerger
extends ModelMerger {
    public static final String CHILD_PATH_ADJUSTMENT = "child-path-adjustment";
    public static final String ARTIFACT_ID = "artifact-id";

    @Override
    protected void mergeModel(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        context.put(ARTIFACT_ID, target.getArtifactId());
        super.mergeModel(target, source2, sourceDominant, context);
    }

    @Override
    protected void mergeModel_Name(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getName();
        if (src != null && sourceDominant) {
            target.setName(src);
            target.setLocation("name", source2.getLocation("name"));
        }
    }

    @Override
    protected void mergeModel_Url(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null) {
            if (sourceDominant) {
                target.setUrl(src);
                target.setLocation("url", source2.getLocation("url"));
            } else if (target.getUrl() == null) {
                target.setUrl(this.extrapolateChildUrl(src, source2.isChildProjectUrlInheritAppendPath(), context));
                target.setLocation("url", source2.getLocation("url"));
            }
        }
    }

    @Override
    protected void mergeModel_Organization(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        Organization tgt;
        Organization src = source2.getOrganization();
        if (src != null && (tgt = target.getOrganization()) == null) {
            tgt = new Organization();
            tgt.setLocation("", src.getLocation(""));
            target.setOrganization(tgt);
            this.mergeOrganization(tgt, src, sourceDominant, context);
        }
    }

    @Override
    protected void mergeModel_IssueManagement(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        IssueManagement tgt;
        IssueManagement src = source2.getIssueManagement();
        if (src != null && (tgt = target.getIssueManagement()) == null) {
            tgt = new IssueManagement();
            tgt.setLocation("", src.getLocation(""));
            target.setIssueManagement(tgt);
            this.mergeIssueManagement(tgt, src, sourceDominant, context);
        }
    }

    @Override
    protected void mergeModel_CiManagement(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        CiManagement tgt;
        CiManagement src = source2.getCiManagement();
        if (src != null && (tgt = target.getCiManagement()) == null) {
            tgt = new CiManagement();
            tgt.setLocation("", src.getLocation(""));
            target.setCiManagement(tgt);
            this.mergeCiManagement(tgt, src, sourceDominant, context);
        }
    }

    @Override
    protected void mergeModel_ModelVersion(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
    }

    @Override
    protected void mergeModel_ArtifactId(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
    }

    @Override
    protected void mergeModel_Profiles(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
    }

    @Override
    protected void mergeModel_Prerequisites(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
    }

    @Override
    protected void mergeModel_Licenses(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        if (target.getLicenses().isEmpty()) {
            target.setLicenses(new ArrayList<License>(source2.getLicenses()));
        }
    }

    @Override
    protected void mergeModel_Developers(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        if (target.getDevelopers().isEmpty()) {
            target.setDevelopers(new ArrayList<Developer>(source2.getDevelopers()));
        }
    }

    @Override
    protected void mergeModel_Contributors(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        if (target.getContributors().isEmpty()) {
            target.setContributors(new ArrayList<Contributor>(source2.getContributors()));
        }
    }

    @Override
    protected void mergeModel_MailingLists(Model target, Model source2, boolean sourceDominant, Map<Object, Object> context) {
        if (target.getMailingLists().isEmpty()) {
            target.setMailingLists(new ArrayList<MailingList>(source2.getMailingLists()));
        }
    }

    @Override
    protected void mergeModelBase_Modules(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getModules();
        if (!src.isEmpty() && sourceDominant) {
            int i;
            ArrayList<Integer> indices = new ArrayList<Integer>();
            List<String> tgt = target.getModules();
            LinkedHashSet<String> excludes = new LinkedHashSet<String>(tgt);
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            int n = tgt.size();
            for (i = 0; i < n; ++i) {
                indices.add(i);
            }
            n = src.size();
            for (i = 0; i < n; ++i) {
                String s2 = src.get(i);
                if (excludes.contains(s2)) continue;
                merged.add(s2);
                indices.add(~i);
            }
            target.setModules(merged);
            target.setLocation("modules", InputLocation.merge(target.getLocation("modules"), source2.getLocation("modules"), indices));
        }
    }

    @Override
    protected void mergeModelBase_Repositories(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        List<Repository> src = source2.getRepositories();
        if (!src.isEmpty()) {
            Object key;
            List<Repository> recessive;
            List<Repository> dominant;
            List<Repository> tgt = target.getRepositories();
            LinkedHashMap<Object, Repository> merged = new LinkedHashMap<Object, Repository>((src.size() + tgt.size()) * 2);
            if (sourceDominant) {
                dominant = src;
                recessive = tgt;
            } else {
                dominant = tgt;
                recessive = src;
            }
            for (Repository element : dominant) {
                key = this.getRepositoryKey(element);
                merged.put(key, element);
            }
            for (Repository element : recessive) {
                key = this.getRepositoryKey(element);
                if (merged.containsKey(key)) continue;
                merged.put(key, element);
            }
            target.setRepositories(new ArrayList<Repository>(merged.values()));
        }
    }

    @Override
    protected void mergeModelBase_PluginRepositories(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
        List<Repository> src = source2.getPluginRepositories();
        if (!src.isEmpty()) {
            Object key;
            List<Repository> recessive;
            List<Repository> dominant;
            List<Repository> tgt = target.getPluginRepositories();
            LinkedHashMap<Object, Repository> merged = new LinkedHashMap<Object, Repository>((src.size() + tgt.size()) * 2);
            if (sourceDominant) {
                dominant = src;
                recessive = tgt;
            } else {
                dominant = tgt;
                recessive = src;
            }
            for (Repository element : dominant) {
                key = this.getRepositoryKey(element);
                merged.put(key, element);
            }
            for (Repository element : recessive) {
                key = this.getRepositoryKey(element);
                if (merged.containsKey(key)) continue;
                merged.put(key, element);
            }
            target.setPluginRepositories(new ArrayList<Repository>(merged.values()));
        }
    }

    @Override
    protected void mergeBuildBase_Filters(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getFilters();
        if (!src.isEmpty()) {
            List<String> tgt = target.getFilters();
            LinkedHashSet<String> excludes = new LinkedHashSet<String>(tgt);
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            for (String s2 : src) {
                if (excludes.contains(s2)) continue;
                merged.add(s2);
            }
            target.setFilters(merged);
        }
    }

    @Override
    protected void mergeBuildBase_Resources(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        if (sourceDominant || target.getResources().isEmpty()) {
            super.mergeBuildBase_Resources(target, source2, sourceDominant, context);
        }
    }

    @Override
    protected void mergeBuildBase_TestResources(BuildBase target, BuildBase source2, boolean sourceDominant, Map<Object, Object> context) {
        if (sourceDominant || target.getTestResources().isEmpty()) {
            super.mergeBuildBase_TestResources(target, source2, sourceDominant, context);
        }
    }

    @Override
    protected void mergeDistributionManagement_Repository(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        DeploymentRepository src = source2.getRepository();
        if (src != null) {
            DeploymentRepository tgt = target.getRepository();
            if (sourceDominant || tgt == null) {
                tgt = new DeploymentRepository();
                tgt.setLocation("", src.getLocation(""));
                target.setRepository(tgt);
                this.mergeDeploymentRepository(tgt, src, sourceDominant, context);
            }
        }
    }

    @Override
    protected void mergeDistributionManagement_SnapshotRepository(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        DeploymentRepository src = source2.getSnapshotRepository();
        if (src != null) {
            DeploymentRepository tgt = target.getSnapshotRepository();
            if (sourceDominant || tgt == null) {
                tgt = new DeploymentRepository();
                tgt.setLocation("", src.getLocation(""));
                target.setSnapshotRepository(tgt);
                this.mergeDeploymentRepository(tgt, src, sourceDominant, context);
            }
        }
    }

    @Override
    protected void mergeDistributionManagement_Site(DistributionManagement target, DistributionManagement source2, boolean sourceDominant, Map<Object, Object> context) {
        Site src = source2.getSite();
        if (src != null) {
            Site tgt = target.getSite();
            if (sourceDominant || tgt == null || this.isSiteEmpty(tgt)) {
                if (tgt == null) {
                    tgt = new Site();
                }
                tgt.setLocation("", src.getLocation(""));
                target.setSite(tgt);
                this.mergeSite(tgt, src, sourceDominant, context);
            }
            this.mergeSite_ChildSiteUrlInheritAppendPath(tgt, src, sourceDominant, context);
        }
    }

    @Override
    protected void mergeSite(Site target, Site source2, boolean sourceDominant, Map<Object, Object> context) {
        this.mergeSite_Id(target, source2, sourceDominant, context);
        this.mergeSite_Name(target, source2, sourceDominant, context);
        this.mergeSite_Url(target, source2, sourceDominant, context);
    }

    protected boolean isSiteEmpty(Site site) {
        return StringUtils.isEmpty(site.getId()) && StringUtils.isEmpty(site.getName()) && StringUtils.isEmpty(site.getUrl());
    }

    @Override
    protected void mergeSite_Url(Site target, Site source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null) {
            if (sourceDominant) {
                target.setUrl(src);
                target.setLocation("url", source2.getLocation("url"));
            } else if (target.getUrl() == null) {
                target.setUrl(this.extrapolateChildUrl(src, source2.isChildSiteUrlInheritAppendPath(), context));
                target.setLocation("url", source2.getLocation("url"));
            }
        }
    }

    @Override
    protected void mergeScm_Url(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getUrl();
        if (src != null) {
            if (sourceDominant) {
                target.setUrl(src);
                target.setLocation("url", source2.getLocation("url"));
            } else if (target.getUrl() == null) {
                target.setUrl(this.extrapolateChildUrl(src, source2.isChildScmUrlInheritAppendPath(), context));
                target.setLocation("url", source2.getLocation("url"));
            }
        }
    }

    @Override
    protected void mergeScm_Connection(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getConnection();
        if (src != null) {
            if (sourceDominant) {
                target.setConnection(src);
                target.setLocation("connection", source2.getLocation("connection"));
            } else if (target.getConnection() == null) {
                target.setConnection(this.extrapolateChildUrl(src, source2.isChildScmConnectionInheritAppendPath(), context));
                target.setLocation("connection", source2.getLocation("connection"));
            }
        }
    }

    @Override
    protected void mergeScm_DeveloperConnection(Scm target, Scm source2, boolean sourceDominant, Map<Object, Object> context) {
        String src = source2.getDeveloperConnection();
        if (src != null) {
            if (sourceDominant) {
                target.setDeveloperConnection(src);
                target.setLocation("developerConnection", source2.getLocation("developerConnection"));
            } else if (target.getDeveloperConnection() == null) {
                String e = this.extrapolateChildUrl(src, source2.isChildScmDeveloperConnectionInheritAppendPath(), context);
                target.setDeveloperConnection(e);
                target.setLocation("developerConnection", source2.getLocation("developerConnection"));
            }
        }
    }

    @Override
    protected void mergePlugin_Executions(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
        List<PluginExecution> src = source2.getExecutions();
        if (!src.isEmpty()) {
            Object key;
            List<PluginExecution> tgt = target.getExecutions();
            LinkedHashMap<Object, PluginExecution> merged = new LinkedHashMap<Object, PluginExecution>((src.size() + tgt.size()) * 2);
            for (PluginExecution element : src) {
                if (!sourceDominant && !(element.getInherited() != null ? element.isInherited() : source2.isInherited())) continue;
                key = this.getPluginExecutionKey(element);
                merged.put(key, element);
            }
            for (PluginExecution element : tgt) {
                key = this.getPluginExecutionKey(element);
                PluginExecution existing = (PluginExecution)merged.get(key);
                if (existing != null) {
                    this.mergePluginExecution(element, existing, sourceDominant, context);
                }
                merged.put(key, element);
            }
            target.setExecutions(new ArrayList<PluginExecution>(merged.values()));
        }
    }

    @Override
    protected void mergePluginExecution_Goals(PluginExecution target, PluginExecution source2, boolean sourceDominant, Map<Object, Object> context) {
        List<String> src = source2.getGoals();
        if (!src.isEmpty()) {
            List<String> tgt = target.getGoals();
            LinkedHashSet<String> excludes = new LinkedHashSet<String>(tgt);
            ArrayList<String> merged = new ArrayList<String>(tgt.size() + src.size());
            merged.addAll(tgt);
            for (String s2 : src) {
                if (excludes.contains(s2)) continue;
                merged.add(s2);
            }
            target.setGoals(merged);
        }
    }

    @Override
    protected void mergeReportPlugin_ReportSets(ReportPlugin target, ReportPlugin source2, boolean sourceDominant, Map<Object, Object> context) {
        List<ReportSet> src = source2.getReportSets();
        if (!src.isEmpty()) {
            Object key;
            List<ReportSet> tgt = target.getReportSets();
            LinkedHashMap<Object, ReportSet> merged = new LinkedHashMap<Object, ReportSet>((src.size() + tgt.size()) * 2);
            for (ReportSet rset : src) {
                if (!sourceDominant && !(rset.getInherited() != null ? rset.isInherited() : source2.isInherited())) continue;
                key = this.getReportSetKey(rset);
                merged.put(key, rset);
            }
            for (ReportSet element : tgt) {
                key = this.getReportSetKey(element);
                ReportSet existing = (ReportSet)merged.get(key);
                if (existing != null) {
                    this.mergeReportSet(element, existing, sourceDominant, context);
                }
                merged.put(key, element);
            }
            target.setReportSets(new ArrayList<ReportSet>(merged.values()));
        }
    }

    @Override
    protected Object getDependencyKey(Dependency dependency) {
        return dependency.getManagementKey();
    }

    @Override
    protected Object getPluginKey(Plugin plugin) {
        return plugin.getKey();
    }

    @Override
    protected Object getPluginExecutionKey(PluginExecution pluginExecution) {
        return pluginExecution.getId();
    }

    @Override
    protected Object getReportPluginKey(ReportPlugin reportPlugin) {
        return reportPlugin.getKey();
    }

    @Override
    protected Object getReportSetKey(ReportSet reportSet) {
        return reportSet.getId();
    }

    @Override
    protected Object getRepositoryBaseKey(RepositoryBase repositoryBase) {
        return repositoryBase.getId();
    }

    @Override
    protected Object getExtensionKey(Extension extension) {
        return extension.getGroupId() + ':' + extension.getArtifactId();
    }

    @Override
    protected Object getExclusionKey(Exclusion exclusion) {
        return exclusion.getGroupId() + ':' + exclusion.getArtifactId();
    }

    protected String extrapolateChildUrl(String parentUrl, boolean appendPath, Map<Object, Object> context) {
        return parentUrl;
    }
}

