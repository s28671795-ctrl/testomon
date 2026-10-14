/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.model.Build;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.Model;
import org.apache.maven.model.ModelBase;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginContainer;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.Profile;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.ReportSet;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.merge.MavenModelMerger;
import org.apache.maven.model.profile.ProfileInjector;
import org.codehaus.plexus.component.annotations.Component;

@Component(role=ProfileInjector.class)
public class DefaultProfileInjector
implements ProfileInjector {
    private ProfileModelMerger merger = new ProfileModelMerger();

    @Override
    public void injectProfile(Model model, Profile profile, ModelBuildingRequest request, ModelProblemCollector problems) {
        if (profile != null) {
            this.merger.mergeModelBase(model, profile);
            if (profile.getBuild() != null) {
                if (model.getBuild() == null) {
                    model.setBuild(new Build());
                }
                this.merger.mergeBuildBase(model.getBuild(), profile.getBuild());
            }
        }
    }

    protected static class ProfileModelMerger
    extends MavenModelMerger {
        protected ProfileModelMerger() {
        }

        public void mergeModelBase(ModelBase target, ModelBase source2) {
            this.mergeModelBase(target, source2, true, Collections.emptyMap());
        }

        public void mergeBuildBase(BuildBase target, BuildBase source2) {
            this.mergeBuildBase(target, source2, true, Collections.emptyMap());
        }

        @Override
        protected void mergePluginContainer_Plugins(PluginContainer target, PluginContainer source2, boolean sourceDominant, Map<Object, Object> context) {
            List<Plugin> src = source2.getPlugins();
            if (!src.isEmpty()) {
                List<Plugin> tgt = target.getPlugins();
                LinkedHashMap<Iterator<Plugin>, Plugin> master = new LinkedHashMap<Iterator<Plugin>, Plugin>(tgt.size() * 2);
                for (Plugin element : tgt) {
                    Iterator<Plugin> key = this.getPluginKey(element);
                    master.put(key, element);
                }
                LinkedHashMap predecessors = new LinkedHashMap();
                ArrayList<Plugin> pending = new ArrayList<Plugin>();
                for (Plugin element : src) {
                    Object key = this.getPluginKey(element);
                    Plugin existing = (Plugin)master.get(key);
                    if (existing != null) {
                        this.mergePlugin(existing, element, sourceDominant, context);
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
        protected void mergePlugin_Executions(Plugin target, Plugin source2, boolean sourceDominant, Map<Object, Object> context) {
            List<PluginExecution> src = source2.getExecutions();
            if (!src.isEmpty()) {
                Object key;
                List<PluginExecution> tgt = target.getExecutions();
                LinkedHashMap<Object, PluginExecution> merged = new LinkedHashMap<Object, PluginExecution>((src.size() + tgt.size()) * 2);
                for (PluginExecution element : tgt) {
                    key = this.getPluginExecutionKey(element);
                    merged.put(key, element);
                }
                for (PluginExecution element : src) {
                    key = this.getPluginExecutionKey(element);
                    PluginExecution existing = (PluginExecution)merged.get(key);
                    if (existing != null) {
                        this.mergePluginExecution(existing, element, sourceDominant, context);
                        continue;
                    }
                    merged.put(key, element);
                }
                target.setExecutions(new ArrayList<PluginExecution>(merged.values()));
            }
        }

        @Override
        protected void mergeReporting_Plugins(Reporting target, Reporting source2, boolean sourceDominant, Map<Object, Object> context) {
            List<ReportPlugin> src = source2.getPlugins();
            if (!src.isEmpty()) {
                Object key;
                List<ReportPlugin> tgt = target.getPlugins();
                LinkedHashMap<Object, ReportPlugin> merged = new LinkedHashMap<Object, ReportPlugin>((src.size() + tgt.size()) * 2);
                for (ReportPlugin element : tgt) {
                    key = this.getReportPluginKey(element);
                    merged.put(key, element);
                }
                for (ReportPlugin element : src) {
                    key = this.getReportPluginKey(element);
                    ReportPlugin existing = (ReportPlugin)merged.get(key);
                    if (existing == null) {
                        merged.put(key, element);
                        continue;
                    }
                    this.mergeReportPlugin(existing, element, sourceDominant, context);
                }
                target.setPlugins(new ArrayList<ReportPlugin>(merged.values()));
            }
        }

        @Override
        protected void mergeReportPlugin_ReportSets(ReportPlugin target, ReportPlugin source2, boolean sourceDominant, Map<Object, Object> context) {
            List<ReportSet> src = source2.getReportSets();
            if (!src.isEmpty()) {
                Object key;
                List<ReportSet> tgt = target.getReportSets();
                LinkedHashMap<Object, ReportSet> merged = new LinkedHashMap<Object, ReportSet>((src.size() + tgt.size()) * 2);
                for (ReportSet element : tgt) {
                    key = this.getReportSetKey(element);
                    merged.put(key, element);
                }
                for (ReportSet element : src) {
                    key = this.getReportSetKey(element);
                    ReportSet existing = (ReportSet)merged.get(key);
                    if (existing != null) {
                        this.mergeReportSet(existing, element, sourceDominant, context);
                        continue;
                    }
                    merged.put(key, element);
                }
                target.setReportSets(new ArrayList<ReportSet>(merged.values()));
            }
        }
    }
}

