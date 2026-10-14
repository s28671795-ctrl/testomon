/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.vue.VueHandlerKt;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VueDependencyResolver {
    private final Map<String, String> componentIdToOwnContent;
    private final Map<String, String> componentIdToDependencyContent;
    private final Pattern tagRegex = Pattern.compile("<\\s*([a-z0-9|-]*).*?>", 32);
    private final Pattern componentRegex;
    private final String appName;

    public VueDependencyResolver(Set<Path> paths, String appVarName) {
        this.appName = appVarName != null ? appVarName : "Vue";
        this.componentIdToOwnContent = new HashMap<String, String>();
        this.componentIdToDependencyContent = new HashMap<String, String>();
        this.componentRegex = Pattern.compile(this.appName + ".component\\s*\\(\\s*[\"|'](.*)[\"|']\\s*,.*");
        paths.stream().filter(VueHandlerKt::isVueFile).forEach(path -> {
            String fileContent = VueHandlerKt.readText(path);
            Matcher matcher = this.componentRegex.matcher(fileContent);
            while (matcher.find()) {
                this.componentIdToOwnContent.put(matcher.group(1), fileContent);
            }
        });
    }

    public String resolve(String componentId) {
        if (!this.componentIdToOwnContent.containsKey(componentId)) {
            throw new IllegalArgumentException(String.format("Component %s not found in app %s", componentId, this.appName));
        }
        if (this.componentIdToDependencyContent.containsKey(componentId)) {
            return this.componentIdToDependencyContent.get(componentId);
        }
        Set<String> dependencies = this.resolveTransitiveDependencies(componentId);
        StringBuilder builder = new StringBuilder();
        dependencies.forEach(dependency -> {
            builder.append("<!-- ").append((String)dependency).append(" -->\n");
            builder.append(this.componentIdToOwnContent.get(dependency));
            builder.append("\n");
        });
        String allDependencies = builder.toString();
        this.componentIdToDependencyContent.put(componentId, allDependencies);
        return allDependencies;
    }

    private Set<String> resolveTransitiveDependencies(String componentId) {
        HashSet<String> requiredComponents = new HashSet<String>();
        requiredComponents.add(componentId);
        Set<String> directDependencies = this.resolveDirectDependencies(componentId);
        requiredComponents.addAll(directDependencies);
        directDependencies.forEach(dependency -> requiredComponents.addAll(this.resolveTransitiveDependencies((String)dependency)));
        return requiredComponents;
    }

    private Set<String> resolveDirectDependencies(String componentId) {
        HashSet<String> dependencies = new HashSet<String>();
        String componentContent = this.componentIdToOwnContent.get(componentId);
        Matcher matcher = this.tagRegex.matcher(componentContent);
        while (matcher.find()) {
            String match = matcher.group(1);
            if (match.equals(componentId) || !this.componentIdToOwnContent.containsKey(match)) continue;
            dependencies.add(match);
        }
        return dependencies;
    }
}

