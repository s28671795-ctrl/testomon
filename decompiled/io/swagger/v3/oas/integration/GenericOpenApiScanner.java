/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Webhooks;
import io.swagger.v3.oas.integration.IgnoredPackages;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiScanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericOpenApiScanner
implements OpenApiScanner {
    static final Set<String> ignored = new HashSet<String>();
    private static Logger LOGGER;
    OpenAPIConfiguration openApiConfiguration;

    @Override
    public void setConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.openApiConfiguration = openApiConfiguration;
    }

    @Override
    public Set<Class<?>> classes() {
        HashSet classes;
        ClassGraph graph = new ClassGraph().enableAllInfo();
        HashSet<String> acceptablePackages = new HashSet<String>();
        HashSet output = new HashSet();
        boolean allowAllPackages = false;
        if (this.openApiConfiguration.getResourceClasses() != null && !this.openApiConfiguration.getResourceClasses().isEmpty()) {
            for (String className : this.openApiConfiguration.getResourceClasses()) {
                if (this.isIgnored(className)) continue;
                try {
                    output.add(Class.forName(className));
                }
                catch (ClassNotFoundException classNotFoundException) {
                    LOGGER.warn("error loading class from resourceClasses: " + classNotFoundException.getMessage(), classNotFoundException);
                }
            }
            return output;
        }
        if (this.openApiConfiguration.getResourcePackages() != null && !this.openApiConfiguration.getResourcePackages().isEmpty()) {
            for (String pkg : this.openApiConfiguration.getResourcePackages()) {
                if (this.isIgnored(pkg)) continue;
                acceptablePackages.add(pkg);
                graph.whitelistPackages(pkg);
            }
        } else {
            allowAllPackages = true;
        }
        try (ScanResult scanResult = graph.scan();){
            classes = new HashSet(scanResult.getClassesWithAnnotation(OpenAPIDefinition.class.getName()).loadClasses());
            classes.addAll(new HashSet(scanResult.getClassesWithAnnotation(Webhooks.class.getName()).loadClasses()));
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
        return output;
    }

    @Override
    public Map<String, Object> resources() {
        return new HashMap<String, Object>();
    }

    protected boolean isIgnored(String classOrPackageName) {
        if (StringUtils.isBlank(classOrPackageName)) {
            return true;
        }
        return ignored.stream().anyMatch(classOrPackageName::startsWith);
    }

    static {
        ignored.addAll(IgnoredPackages.ignored);
        LOGGER = LoggerFactory.getLogger(GenericOpenApiScanner.class);
    }
}

