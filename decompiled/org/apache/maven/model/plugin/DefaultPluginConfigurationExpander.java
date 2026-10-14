/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.plugin;

import java.util.List;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.plugin.PluginConfigurationExpander;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.xml.Xpp3Dom;

@Component(role=PluginConfigurationExpander.class)
public class DefaultPluginConfigurationExpander
implements PluginConfigurationExpander {
    @Override
    public void expandPluginConfiguration(Model model, ModelBuildingRequest request, ModelProblemCollector problems) {
        Build build = model.getBuild();
        if (build != null) {
            this.expand(build.getPlugins());
            PluginManagement pluginManagement = build.getPluginManagement();
            if (pluginManagement != null) {
                this.expand(pluginManagement.getPlugins());
            }
        }
    }

    private void expand(List<Plugin> plugins) {
        for (Plugin plugin : plugins) {
            Xpp3Dom pluginConfiguration = (Xpp3Dom)plugin.getConfiguration();
            if (pluginConfiguration == null) continue;
            for (PluginExecution execution : plugin.getExecutions()) {
                Xpp3Dom executionConfiguration = (Xpp3Dom)execution.getConfiguration();
                executionConfiguration = Xpp3Dom.mergeXpp3Dom(executionConfiguration, new Xpp3Dom(pluginConfiguration));
                execution.setConfiguration(executionConfiguration);
            }
        }
    }
}

