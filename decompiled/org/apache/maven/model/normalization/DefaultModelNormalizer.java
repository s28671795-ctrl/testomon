/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.normalization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.InputLocationTracker;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.merge.MavenModelMerger;
import org.apache.maven.model.normalization.ModelNormalizer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.StringUtils;

@Component(role=ModelNormalizer.class)
public class DefaultModelNormalizer
implements ModelNormalizer {
    private DuplicateMerger merger = new DuplicateMerger();

    @Override
    public void mergeDuplicates(Model model, ModelBuildingRequest request, ModelProblemCollector problems) {
        LinkedHashMap<String, InputLocationTracker> normalized;
        Build build = model.getBuild();
        if (build != null) {
            List<Plugin> plugins = build.getPlugins();
            normalized = new LinkedHashMap<String, InputLocationTracker>(plugins.size() * 2);
            for (Plugin plugin : plugins) {
                String key = plugin.getKey();
                Plugin first = (Plugin)normalized.get(key);
                if (first != null) {
                    this.merger.mergePlugin(plugin, first);
                }
                normalized.put(key, plugin);
            }
            if (plugins.size() != normalized.size()) {
                build.setPlugins(new ArrayList<Plugin>(normalized.values()));
            }
        }
        List<Dependency> dependencies = model.getDependencies();
        normalized = new LinkedHashMap(dependencies.size() * 2);
        for (Dependency dependency : dependencies) {
            normalized.put(dependency.getManagementKey(), dependency);
        }
        if (dependencies.size() != normalized.size()) {
            model.setDependencies(new ArrayList<Dependency>(normalized.values()));
        }
    }

    @Override
    public void injectDefaultValues(Model model, ModelBuildingRequest request, ModelProblemCollector problems) {
        this.injectDependencyDefaults(model.getDependencies());
        Build build = model.getBuild();
        if (build != null) {
            for (Plugin plugin : build.getPlugins()) {
                this.injectDependencyDefaults(plugin.getDependencies());
            }
        }
    }

    private void injectDependencyDefaults(List<Dependency> dependencies) {
        for (Dependency dependency : dependencies) {
            if (!StringUtils.isEmpty(dependency.getScope())) continue;
            dependency.setScope("compile");
        }
    }

    protected static class DuplicateMerger
    extends MavenModelMerger {
        protected DuplicateMerger() {
        }

        public void mergePlugin(Plugin target, Plugin source2) {
            super.mergePlugin(target, source2, false, Collections.emptyMap());
        }
    }
}

