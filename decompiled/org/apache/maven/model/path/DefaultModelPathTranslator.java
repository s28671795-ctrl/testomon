/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.path;

import java.io.File;
import java.util.ArrayList;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Resource;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.path.ModelPathTranslator;
import org.apache.maven.model.path.PathTranslator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component(role=ModelPathTranslator.class)
public class DefaultModelPathTranslator
implements ModelPathTranslator {
    @Requirement
    private PathTranslator pathTranslator;

    public DefaultModelPathTranslator setPathTranslator(PathTranslator pathTranslator) {
        this.pathTranslator = pathTranslator;
        return this;
    }

    @Override
    public void alignToBaseDirectory(Model model, File basedir, ModelBuildingRequest request) {
        Reporting reporting;
        if (model == null || basedir == null) {
            return;
        }
        Build build = model.getBuild();
        if (build != null) {
            build.setDirectory(this.alignToBaseDirectory(build.getDirectory(), basedir));
            build.setSourceDirectory(this.alignToBaseDirectory(build.getSourceDirectory(), basedir));
            build.setTestSourceDirectory(this.alignToBaseDirectory(build.getTestSourceDirectory(), basedir));
            build.setScriptSourceDirectory(this.alignToBaseDirectory(build.getScriptSourceDirectory(), basedir));
            for (Resource resource : build.getResources()) {
                resource.setDirectory(this.alignToBaseDirectory(resource.getDirectory(), basedir));
            }
            for (Resource resource : build.getTestResources()) {
                resource.setDirectory(this.alignToBaseDirectory(resource.getDirectory(), basedir));
            }
            if (build.getFilters() != null) {
                ArrayList<String> filters = new ArrayList<String>(build.getFilters().size());
                for (String filter2 : build.getFilters()) {
                    filters.add(this.alignToBaseDirectory(filter2, basedir));
                }
                build.setFilters(filters);
            }
            build.setOutputDirectory(this.alignToBaseDirectory(build.getOutputDirectory(), basedir));
            build.setTestOutputDirectory(this.alignToBaseDirectory(build.getTestOutputDirectory(), basedir));
        }
        if ((reporting = model.getReporting()) != null) {
            reporting.setOutputDirectory(this.alignToBaseDirectory(reporting.getOutputDirectory(), basedir));
        }
    }

    private String alignToBaseDirectory(String path, File basedir) {
        return this.pathTranslator.alignToBaseDirectory(path, basedir);
    }
}

