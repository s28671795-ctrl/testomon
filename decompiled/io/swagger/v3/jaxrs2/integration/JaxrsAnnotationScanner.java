/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2.integration;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.swagger.v3.jaxrs2.integration.api.JaxrsOpenApiScanner;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Webhooks;
import io.swagger.v3.oas.integration.IgnoredPackages;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxrsAnnotationScanner<T extends JaxrsAnnotationScanner<T>>
implements JaxrsOpenApiScanner {
    static final Set<String> ignored = new HashSet<String>();
    protected OpenAPIConfiguration openApiConfiguration;
    protected Application application;
    protected static final Logger LOGGER;
    protected boolean onlyConsiderResourcePackages = false;

    public JaxrsAnnotationScanner application(Application application) {
        this.application = application;
        return this;
    }

    @Override
    public void setApplication(Application application) {
        this.application = application;
    }

    public T openApiConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.openApiConfiguration = openApiConfiguration;
        return (T)this;
    }

    @Override
    public void setConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.openApiConfiguration = openApiConfiguration;
    }

    @Override
    public Set<Class<?>> classes() {
        HashSet classes;
        if (this.openApiConfiguration == null) {
            this.openApiConfiguration = new SwaggerConfiguration();
        }
        ClassGraph graph = new ClassGraph().enableAllInfo();
        HashSet<String> acceptablePackages = new HashSet<String>();
        HashSet output = new HashSet();
        if (this.openApiConfiguration.getResourceClasses() != null && !this.openApiConfiguration.getResourceClasses().isEmpty()) {
            for (String className : this.openApiConfiguration.getResourceClasses()) {
                if (this.isIgnored(className)) continue;
                try {
                    output.add(Class.forName(className));
                }
                catch (ClassNotFoundException e) {
                    LOGGER.warn("error loading class from resourceClasses: " + e.getMessage(), e);
                }
            }
            return output;
        }
        boolean allowAllPackages = false;
        if (this.openApiConfiguration.getResourcePackages() != null && !this.openApiConfiguration.getResourcePackages().isEmpty()) {
            for (String pkg : this.openApiConfiguration.getResourcePackages()) {
                if (this.isIgnored(pkg)) continue;
                acceptablePackages.add(pkg);
                graph.whitelistPackages(pkg);
            }
        } else if (!this.onlyConsiderResourcePackages) {
            allowAllPackages = true;
        }
        try (ScanResult scanResult = graph.scan();){
            classes = new HashSet(scanResult.getClassesWithAnnotation(Path.class.getName()).loadClasses());
            classes.addAll(new HashSet(scanResult.getClassesWithAnnotation(OpenAPIDefinition.class.getName()).loadClasses()));
            classes.addAll(new HashSet(scanResult.getClassesWithAnnotation(Webhooks.class.getName()).loadClasses()));
            if (Boolean.TRUE.equals(this.openApiConfiguration.isAlwaysResolveAppPath())) {
                classes.addAll(new HashSet(scanResult.getClassesWithAnnotation(ApplicationPath.class.getName()).loadClasses()));
            }
        }
        for (Class clazz : classes) {
            if (allowAllPackages) {
                output.add(clazz);
                continue;
            }
            for (String pkg : acceptablePackages) {
                if (!clazz.getPackage().getName().startsWith(pkg)) continue;
                output.add(clazz);
            }
        }
        LOGGER.trace("classes() - output size {}", (Object)output.size());
        return output;
    }

    protected boolean isIgnored(String classOrPackageName) {
        if (StringUtils.isBlank(classOrPackageName)) {
            return true;
        }
        return ignored.stream().anyMatch(classOrPackageName::startsWith);
    }

    @Override
    public Map<String, Object> resources() {
        return new HashMap<String, Object>();
    }

    static {
        ignored.addAll(IgnoredPackages.ignored);
        LOGGER = LoggerFactory.getLogger(JaxrsAnnotationScanner.class);
    }
}

