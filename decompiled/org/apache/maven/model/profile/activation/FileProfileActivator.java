/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile.activation;

import java.io.File;
import org.apache.maven.model.Activation;
import org.apache.maven.model.ActivationFile;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.building.ModelProblemCollectorRequest;
import org.apache.maven.model.path.PathTranslator;
import org.apache.maven.model.profile.ProfileActivationContext;
import org.apache.maven.model.profile.activation.ProfileActivator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.codehaus.plexus.interpolation.MapBasedValueSource;
import org.codehaus.plexus.interpolation.RegexBasedInterpolator;
import org.codehaus.plexus.util.StringUtils;

@Component(role=ProfileActivator.class, hint="file")
public class FileProfileActivator
implements ProfileActivator {
    @Requirement
    private PathTranslator pathTranslator;

    public FileProfileActivator setPathTranslator(PathTranslator pathTranslator) {
        this.pathTranslator = pathTranslator;
        return this;
    }

    @Override
    public boolean isActive(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        boolean missing;
        String path;
        Activation activation = profile.getActivation();
        if (activation == null) {
            return false;
        }
        ActivationFile file = activation.getFile();
        if (file == null) {
            return false;
        }
        if (StringUtils.isNotEmpty(file.getExists())) {
            path = file.getExists();
            missing = false;
        } else if (StringUtils.isNotEmpty(file.getMissing())) {
            path = file.getMissing();
            missing = true;
        } else {
            return false;
        }
        RegexBasedInterpolator interpolator = new RegexBasedInterpolator();
        final File basedir = context.getProjectDirectory();
        if (basedir != null) {
            interpolator.addValueSource(new AbstractValueSource(false){

                @Override
                public Object getValue(String expression) {
                    if ("basedir".equals(expression)) {
                        return basedir.getAbsolutePath();
                    }
                    return null;
                }
            });
        } else if (path.contains("${basedir}")) {
            return false;
        }
        interpolator.addValueSource(new MapBasedValueSource(context.getProjectProperties()));
        interpolator.addValueSource(new MapBasedValueSource(context.getUserProperties()));
        interpolator.addValueSource(new MapBasedValueSource(context.getSystemProperties()));
        try {
            path = interpolator.interpolate(path, "");
        }
        catch (Exception e) {
            problems.add(new ModelProblemCollectorRequest(ModelProblem.Severity.ERROR, ModelProblem.Version.BASE).setMessage("Failed to interpolate file location " + path + " for profile " + profile.getId() + ": " + e.getMessage()).setLocation(file.getLocation(missing ? "missing" : "exists")).setException(e));
            return false;
        }
        path = this.pathTranslator.alignToBaseDirectory(path, basedir);
        if (missing) {
            file.setMissing(path);
        } else {
            file.setExists(path);
        }
        File f = new File(path);
        if (!f.isAbsolute()) {
            return false;
        }
        boolean fileExists = f.exists();
        return missing ? !fileExists : fileExists;
    }

    @Override
    public boolean presentInConfig(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        Activation activation = profile.getActivation();
        if (activation == null) {
            return false;
        }
        ActivationFile file = activation.getFile();
        return file != null;
    }
}

