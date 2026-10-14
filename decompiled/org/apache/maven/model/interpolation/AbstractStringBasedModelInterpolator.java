/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.interpolation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.building.ModelProblemCollectorRequest;
import org.apache.maven.model.interpolation.BuildTimestampValueSource;
import org.apache.maven.model.interpolation.ModelInterpolator;
import org.apache.maven.model.interpolation.PathTranslatingPostProcessor;
import org.apache.maven.model.interpolation.ProblemDetectingValueSource;
import org.apache.maven.model.interpolation.UrlNormalizingPostProcessor;
import org.apache.maven.model.path.PathTranslator;
import org.apache.maven.model.path.UrlNormalizer;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.interpolation.AbstractValueSource;
import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;
import org.codehaus.plexus.interpolation.Interpolator;
import org.codehaus.plexus.interpolation.MapBasedValueSource;
import org.codehaus.plexus.interpolation.ObjectBasedValueSource;
import org.codehaus.plexus.interpolation.PrefixAwareRecursionInterceptor;
import org.codehaus.plexus.interpolation.PrefixedObjectValueSource;
import org.codehaus.plexus.interpolation.PrefixedValueSourceWrapper;
import org.codehaus.plexus.interpolation.RecursionInterceptor;
import org.codehaus.plexus.interpolation.ValueSource;

public abstract class AbstractStringBasedModelInterpolator
implements ModelInterpolator {
    public static final String SHA1_PROPERTY = "sha1";
    public static final String CHANGELIST_PROPERTY = "changelist";
    public static final String REVISION_PROPERTY = "revision";
    private static final List<String> PROJECT_PREFIXES = Arrays.asList("pom.", "project.");
    private static final Collection<String> TRANSLATED_PATH_EXPRESSIONS;
    @Requirement
    private PathTranslator pathTranslator;
    @Requirement
    private UrlNormalizer urlNormalizer;
    private Interpolator interpolator = this.createInterpolator();
    private RecursionInterceptor recursionInterceptor = new PrefixAwareRecursionInterceptor(PROJECT_PREFIXES);

    public AbstractStringBasedModelInterpolator setPathTranslator(PathTranslator pathTranslator) {
        this.pathTranslator = pathTranslator;
        return this;
    }

    public AbstractStringBasedModelInterpolator setUrlNormalizer(UrlNormalizer urlNormalizer) {
        this.urlNormalizer = urlNormalizer;
        return this;
    }

    protected List<ValueSource> createValueSources(Model model, final File projectDir, final ModelBuildingRequest config, ModelProblemCollector problems) {
        Properties modelProperties = model.getProperties();
        ValueSource modelValueSource1 = new PrefixedObjectValueSource(PROJECT_PREFIXES, model, false);
        if (config.getValidationLevel() >= 20) {
            modelValueSource1 = new ProblemDetectingValueSource(modelValueSource1, "pom.", "project.", problems);
        }
        ValueSource modelValueSource2 = new ObjectBasedValueSource(model);
        if (config.getValidationLevel() >= 20) {
            modelValueSource2 = new ProblemDetectingValueSource(modelValueSource2, "", "project.", problems);
        }
        ArrayList<ValueSource> valueSources = new ArrayList<ValueSource>(9);
        if (projectDir != null) {
            PrefixedValueSourceWrapper basedirValueSource = new PrefixedValueSourceWrapper((ValueSource)new AbstractValueSource(false){

                @Override
                public Object getValue(String expression) {
                    if ("basedir".equals(expression)) {
                        return projectDir.getAbsolutePath();
                    }
                    return null;
                }
            }, PROJECT_PREFIXES, true);
            valueSources.add(basedirValueSource);
            PrefixedValueSourceWrapper baseUriValueSource = new PrefixedValueSourceWrapper((ValueSource)new AbstractValueSource(false){

                @Override
                public Object getValue(String expression) {
                    if ("baseUri".equals(expression)) {
                        return projectDir.getAbsoluteFile().toPath().toUri().toASCIIString();
                    }
                    return null;
                }
            }, PROJECT_PREFIXES, false);
            valueSources.add(baseUriValueSource);
            valueSources.add(new BuildTimestampValueSource(config.getBuildStartTime(), modelProperties));
        }
        valueSources.add(modelValueSource1);
        valueSources.add(new MapBasedValueSource(config.getUserProperties()));
        if (config.getSystemProperties().containsKey(REVISION_PROPERTY)) {
            modelProperties.put(REVISION_PROPERTY, config.getSystemProperties().get(REVISION_PROPERTY));
        }
        if (config.getSystemProperties().containsKey(CHANGELIST_PROPERTY)) {
            modelProperties.put(CHANGELIST_PROPERTY, config.getSystemProperties().get(CHANGELIST_PROPERTY));
        }
        if (config.getSystemProperties().containsKey(SHA1_PROPERTY)) {
            modelProperties.put(SHA1_PROPERTY, config.getSystemProperties().get(SHA1_PROPERTY));
        }
        valueSources.add(new MapBasedValueSource(modelProperties));
        valueSources.add(new MapBasedValueSource(config.getSystemProperties()));
        valueSources.add(new AbstractValueSource(false){

            @Override
            public Object getValue(String expression) {
                return config.getSystemProperties().getProperty("env." + expression);
            }
        });
        valueSources.add(modelValueSource2);
        return valueSources;
    }

    protected List<? extends InterpolationPostProcessor> createPostProcessors(Model model, File projectDir, ModelBuildingRequest config) {
        ArrayList<InterpolationPostProcessor> processors = new ArrayList<InterpolationPostProcessor>(2);
        if (projectDir != null) {
            processors.add(new PathTranslatingPostProcessor(PROJECT_PREFIXES, TRANSLATED_PATH_EXPRESSIONS, projectDir, this.pathTranslator));
        }
        processors.add(new UrlNormalizingPostProcessor(this.urlNormalizer));
        return processors;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String interpolateInternal(String src, List<? extends ValueSource> valueSources, List<? extends InterpolationPostProcessor> postProcessors, ModelProblemCollector problems) {
        if (!src.contains("${")) {
            return src;
        }
        String result = src;
        AbstractStringBasedModelInterpolator abstractStringBasedModelInterpolator = this;
        synchronized (abstractStringBasedModelInterpolator) {
            for (ValueSource valueSource : valueSources) {
                this.interpolator.addValueSource(valueSource);
            }
            for (InterpolationPostProcessor interpolationPostProcessor : postProcessors) {
                this.interpolator.addPostProcessor(interpolationPostProcessor);
            }
            try {
                try {
                    result = this.interpolator.interpolate(result, this.recursionInterceptor);
                }
                catch (InterpolationException e) {
                    problems.add(new ModelProblemCollectorRequest(ModelProblem.Severity.ERROR, ModelProblem.Version.BASE).setMessage(e.getMessage()).setException(e));
                }
                this.interpolator.clearFeedback();
            }
            catch (Throwable throwable) {
                for (ValueSource valueSource : valueSources) {
                    this.interpolator.removeValuesSource(valueSource);
                }
                for (InterpolationPostProcessor interpolationPostProcessor : postProcessors) {
                    this.interpolator.removePostProcessor(interpolationPostProcessor);
                }
                throw throwable;
            }
            for (ValueSource valueSource : valueSources) {
                this.interpolator.removeValuesSource(valueSource);
            }
            for (InterpolationPostProcessor interpolationPostProcessor : postProcessors) {
                this.interpolator.removePostProcessor(interpolationPostProcessor);
            }
        }
        return result;
    }

    protected RecursionInterceptor getRecursionInterceptor() {
        return this.recursionInterceptor;
    }

    protected void setRecursionInterceptor(RecursionInterceptor recursionInterceptor) {
        this.recursionInterceptor = recursionInterceptor;
    }

    protected abstract Interpolator createInterpolator();

    protected final Interpolator getInterpolator() {
        return this.interpolator;
    }

    static {
        HashSet<String> translatedPrefixes = new HashSet<String>();
        translatedPrefixes.add("build.directory");
        translatedPrefixes.add("build.outputDirectory");
        translatedPrefixes.add("build.testOutputDirectory");
        translatedPrefixes.add("build.sourceDirectory");
        translatedPrefixes.add("build.testSourceDirectory");
        translatedPrefixes.add("build.scriptSourceDirectory");
        translatedPrefixes.add("reporting.outputDirectory");
        TRANSLATED_PATH_EXPRESSIONS = translatedPrefixes;
    }
}

