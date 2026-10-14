/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.inheritance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.Model;
import org.apache.maven.model.ModelBase;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginContainer;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.inheritance.InheritanceAssembler;
import org.apache.maven.model.merge.MavenModelMerger;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.StringUtils;

@Component(role=InheritanceAssembler.class)
public class DefaultInheritanceAssembler
implements InheritanceAssembler {
    private InheritanceModelMerger merger = new InheritanceModelMerger();
    private static final String CHILD_DIRECTORY = "child-directory";
    private static final String CHILD_DIRECTORY_PROPERTY = "project.directory";

    @Override
    public void assembleModelInheritance(Model child, Model parent, ModelBuildingRequest request, ModelProblemCollector problems) {
        HashMap<String, String> hints = new HashMap<String, String>();
        String childPath = child.getProperties().getProperty(CHILD_DIRECTORY_PROPERTY, child.getArtifactId());
        hints.put(CHILD_DIRECTORY, childPath);
        hints.put("child-path-adjustment", this.getChildPathAdjustment(child, parent, childPath));
        this.merger.merge(child, parent, false, hints);
    }

    private String getChildPathAdjustment(Model child, Model parent, String childDirectory) {
        String adjustment = "";
        if (parent != null) {
            String childName = child.getArtifactId();
            if (child.getProjectDirectory() != null) {
                childName = child.getProjectDirectory().getName();
            }
            for (String module : parent.getModules()) {
                int lastSlash;
                String moduleName;
                if ((module = module.replace('\\', '/')).regionMatches(true, module.length() - 4, ".xml", 0, 4)) {
                    module = module.substring(0, module.lastIndexOf(47) + 1);
                }
                if ((moduleName = module).endsWith("/")) {
                    moduleName = moduleName.substring(0, moduleName.length() - 1);
                }
                if (!(moduleName = moduleName.substring((lastSlash = moduleName.lastIndexOf(47)) + 1)).equals(childName) && !moduleName.equals(childDirectory) || lastSlash < 0) continue;
                adjustment = module.substring(0, lastSlash);
                break;
            }
        }
        return adjustment;
    }

    protected static class InheritanceModelMerger
    extends MavenModelMerger {
        protected InheritanceModelMerger() {
        }

        @Override
        protected String extrapolateChildUrl(String parentUrl, boolean appendPath, Map<Object, Object> context) {
            Object childDirectory = context.get(DefaultInheritanceAssembler.CHILD_DIRECTORY);
            Object childPathAdjustment = context.get("child-path-adjustment");
            if (StringUtils.isBlank(parentUrl) || childDirectory == null || childPathAdjustment == null || !appendPath) {
                return parentUrl;
            }
            return this.appendPath(parentUrl, childDirectory.toString(), childPathAdjustment.toString());
        }

        private String appendPath(String parentUrl, String childPath, String pathAdjustment) {
            StringBuilder url2 = new StringBuilder(parentUrl.length() + pathAdjustment.length() + childPath.length() + (pathAdjustment.length() == 0 ? 1 : 2));
            url2.append(parentUrl);
            this.concatPath(url2, pathAdjustment);
            this.concatPath(url2, childPath);
            return url2.toString();
        }

        private void concatPath(StringBuilder url2, String path) {
            if (path.length() > 0) {
                boolean pathStartsWithSlash;
                boolean initialUrlEndsWithSlash = url2.charAt(url2.length() - 1) == '/';
                boolean bl = pathStartsWithSlash = path.charAt(0) == '/';
                if (pathStartsWithSlash) {
                    if (initialUrlEndsWithSlash) {
                        url2.setLength(url2.length() - 1);
                    }
                } else if (!initialUrlEndsWithSlash) {
                    url2.append('/');
                }
                url2.append(path);
                if (initialUrlEndsWithSlash && !path.endsWith("/")) {
                    url2.append('/');
                }
            }
        }

        @Override
        protected void mergeModelBase_Properties(ModelBase target, ModelBase source2, boolean sourceDominant, Map<Object, Object> context) {
            Properties merged = new Properties();
            if (sourceDominant) {
                merged.putAll((Map<?, ?>)target.getProperties());
                this.putAll(merged, source2.getProperties(), DefaultInheritanceAssembler.CHILD_DIRECTORY_PROPERTY);
            } else {
                this.putAll(merged, source2.getProperties(), DefaultInheritanceAssembler.CHILD_DIRECTORY_PROPERTY);
                merged.putAll((Map<?, ?>)target.getProperties());
            }
            target.setProperties(merged);
            target.setLocation("properties", InputLocation.merge(target.getLocation("properties"), source2.getLocation("properties"), sourceDominant));
        }

        private void putAll(Map<Object, Object> s2, Map<Object, Object> t2, Object excludeKey) {
            for (Map.Entry<Object, Object> e : t2.entrySet()) {
                if (e.getKey().equals(excludeKey)) continue;
                s2.put(e.getKey(), e.getValue());
            }
        }

        @Override
        protected void mergePluginContainer_Plugins(PluginContainer target, PluginContainer source2, boolean sourceDominant, Map<Object, Object> context) {
            List<Plugin> src = source2.getPlugins();
            if (!src.isEmpty()) {
                List<Plugin> tgt = target.getPlugins();
                LinkedHashMap<Object, Object> master = new LinkedHashMap<Object, Object>(src.size() * 2);
                for (Plugin element : src) {
                    if (!element.isInherited() && element.getExecutions().isEmpty()) continue;
                    Plugin plugin = new Plugin();
                    plugin.setLocation("", element.getLocation(""));
                    plugin.setGroupId(null);
                    this.mergePlugin(plugin, element, sourceDominant, context);
                    Object key = this.getPluginKey(element);
                    master.put(key, plugin);
                }
                LinkedHashMap predecessors = new LinkedHashMap();
                ArrayList<Plugin> pending = new ArrayList<Plugin>();
                for (Plugin element : tgt) {
                    Object key = this.getPluginKey(element);
                    Plugin existing = (Plugin)master.get(key);
                    if (existing != null) {
                        this.mergePlugin(element, existing, sourceDominant, context);
                        master.put(key, element);
                        if (pending.isEmpty()) continue;
                        predecessors.put(key, pending);
                        pending = new ArrayList();
                        continue;
                    }
                    pending.add(element);
                }
                ArrayList<Plugin> result = new ArrayList<Plugin>(src.size() + tgt.size());
                for (Map.Entry entry : master.entrySet()) {
                    List pre = (List)predecessors.get(entry.getKey());
                    if (pre != null) {
                        result.addAll(pre);
                    }
                    result.add((Plugin)entry.getValue());
                }
                result.addAll(pending);
                target.setPlugins(result);
            }
        }

        @Override
        protected void mergePlugin(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
            if (source2.isInherited()) {
                this.mergeConfigurationContainer(target, source2, sourceDominant, context);
            }
            this.mergePlugin_GroupId(target, source2, sourceDominant, context);
            this.mergePlugin_ArtifactId(target, source2, sourceDominant, context);
            this.mergePlugin_Version(target, source2, sourceDominant, context);
            this.mergePlugin_Extensions(target, source2, sourceDominant, context);
            this.mergePlugin_Dependencies(target, source2, sourceDominant, context);
            this.mergePlugin_Executions(target, source2, sourceDominant, context);
        }

        @Override
        protected void mergeReporting_Plugins(Reporting target, Reporting source2, boolean sourceDominant, Map<Object, Object> context) {
            List<ReportPlugin> src = source2.getPlugins();
            if (!src.isEmpty()) {
                Object key;
                List<ReportPlugin> tgt = target.getPlugins();
                LinkedHashMap<Object, ReportPlugin> merged = new LinkedHashMap<Object, ReportPlugin>((src.size() + tgt.size()) * 2);
                for (ReportPlugin element : src) {
                    key = this.getReportPluginKey(element);
                    if (!element.isInherited()) continue;
                    ReportPlugin plugin = new ReportPlugin();
                    plugin.setLocation("", element.getLocation(""));
                    plugin.setGroupId(null);
                    this.mergeReportPlugin(plugin, element, sourceDominant, context);
                    merged.put(key, plugin);
                }
                for (ReportPlugin element : tgt) {
                    key = this.getReportPluginKey(element);
                    ReportPlugin existing = (ReportPlugin)merged.get(key);
                    if (existing != null) {
                        this.mergeReportPlugin(element, existing, sourceDominant, context);
                    }
                    merged.put(key, element);
                }
                target.setPlugins(new ArrayList<ReportPlugin>(merged.values()));
            }
        }
    }
}

