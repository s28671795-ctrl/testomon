/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.composition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.composition.DependencyManagementImporter;
import org.codehaus.plexus.component.annotations.Component;

@Component(role=DependencyManagementImporter.class)
public class DefaultDependencyManagementImporter
implements DependencyManagementImporter {
    @Override
    public void importManagement(Model target, List<? extends DependencyManagement> sources, ModelBuildingRequest request, ModelProblemCollector problems) {
        if (sources != null && !sources.isEmpty()) {
            LinkedHashMap<String, Dependency> dependencies = new LinkedHashMap<String, Dependency>();
            DependencyManagement depMgmt = target.getDependencyManagement();
            if (depMgmt != null) {
                for (Dependency dependency : depMgmt.getDependencies()) {
                    dependencies.put(dependency.getManagementKey(), dependency);
                }
            } else {
                depMgmt = new DependencyManagement();
                target.setDependencyManagement(depMgmt);
            }
            for (DependencyManagement dependencyManagement : sources) {
                for (Dependency dependency : dependencyManagement.getDependencies()) {
                    String key = dependency.getManagementKey();
                    if (dependencies.containsKey(key)) continue;
                    dependencies.put(key, dependency);
                }
            }
            depMgmt.setDependencies(new ArrayList<Dependency>(dependencies.values()));
        }
    }
}

