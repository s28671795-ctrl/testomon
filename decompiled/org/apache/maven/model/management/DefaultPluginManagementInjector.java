/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginContainer;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.management.PluginManagementInjector;
import org.apache.maven.model.merge.MavenModelMerger;
import org.codehaus.plexus.component.annotations.Component;

@Component(role=PluginManagementInjector.class)
public class DefaultPluginManagementInjector
implements PluginManagementInjector {
    private ManagementModelMerger merger = new ManagementModelMerger();

    @Override
    public void injectManagement(Model model, ModelBuildingRequest request, ModelProblemCollector problems) {
        this.merger.mergeManagedBuildPlugins(model);
    }

    protected static class ManagementModelMerger
    extends MavenModelMerger {
        protected ManagementModelMerger() {
        }

        public void mergeManagedBuildPlugins(Model model) {
            PluginManagement pluginManagement;
            Build build = model.getBuild();
            if (build != null && (pluginManagement = build.getPluginManagement()) != null) {
                this.mergePluginContainerPlugins(build, pluginManagement);
            }
        }

        private void mergePluginContainerPlugins(PluginContainer target, PluginContainer source2) {
            List<Plugin> src = source2.getPlugins();
            if (!src.isEmpty()) {
                Object key;
                List<Plugin> tgt = target.getPlugins();
                LinkedHashMap<Object, Plugin> managedPlugins = new LinkedHashMap<Object, Plugin>(src.size() * 2);
                Map<Object, Object> context = Collections.emptyMap();
                for (Plugin element : src) {
                    key = this.getPluginKey(element);
                    managedPlugins.put(key, element);
                }
                for (Plugin element : tgt) {
                    key = this.getPluginKey(element);
                    Plugin managedPlugin = (Plugin)managedPlugins.get(key);
                    if (managedPlugin == null) continue;
                    this.mergePlugin(element, managedPlugin, false, context);
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
                    key = this.getPluginExecutionKey(element);
                    merged.put(key, element.clone());
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
    }
}

