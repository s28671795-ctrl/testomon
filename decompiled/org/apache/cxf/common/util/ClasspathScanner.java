/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.cxf.common.util.SpringClasspathScanner;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;

public class ClasspathScanner {
    public static final String ALL_FILES = "/**/*";
    public static final String ALL_CLASS_FILES = "/**/*.class";
    public static final String WILDCARD = "*";
    public static final String CLASSPATH_URL_SCHEME = "classpath:";
    static final ClasspathScanner HELPER = ClasspathScanner.getClasspathScanner();
    static final String[] PACKAGES_TO_SKIP = new String[]{"org.apache.cxf"};

    protected ClasspathScanner() {
    }

    private static ClasspathScanner getClasspathScanner() {
        boolean useSpring = true;
        String s2 = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.useSpringClassHelpers");
        if (!StringUtils.isEmpty(s2)) {
            boolean bl = useSpring = "1".equals(s2) || Boolean.parseBoolean(s2);
        }
        if (useSpring) {
            try {
                return new SpringClasspathScanner();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return new ClasspathScanner();
    }

    @SafeVarargs
    public static Map<Class<? extends Annotation>, Collection<Class<?>>> findClasses(String basePackage, Class<? extends Annotation> ... annotations) throws IOException, ClassNotFoundException {
        ArrayList<Class<? extends Annotation>> annotationList = new ArrayList<Class<? extends Annotation>>(annotations.length);
        for (Class<? extends Annotation> c : annotations) {
            annotationList.add(c);
        }
        return ClasspathScanner.findClasses(ClasspathScanner.parsePackages(basePackage), Collections.unmodifiableList(annotationList));
    }

    @SafeVarargs
    public static Map<Class<? extends Annotation>, Collection<Class<?>>> findClasses(Collection<String> basePackages, Class<? extends Annotation> ... annotations) throws IOException, ClassNotFoundException {
        ArrayList<Class<? extends Annotation>> annotationList = new ArrayList<Class<? extends Annotation>>(annotations.length);
        for (Class<? extends Annotation> c : annotations) {
            annotationList.add(c);
        }
        return ClasspathScanner.findClasses(basePackages, Collections.unmodifiableList(annotationList));
    }

    public static Map<Class<? extends Annotation>, Collection<Class<?>>> findClasses(Collection<String> basePackages, List<Class<? extends Annotation>> annotations) throws IOException, ClassNotFoundException {
        return ClasspathScanner.findClasses(basePackages, annotations, null);
    }

    public static Map<Class<? extends Annotation>, Collection<Class<?>>> findClasses(Collection<String> basePackages, List<Class<? extends Annotation>> annotations, ClassLoader loader) throws IOException, ClassNotFoundException {
        return HELPER.findClassesInternal(basePackages, annotations, loader);
    }

    protected Map<Class<? extends Annotation>, Collection<Class<?>>> findClassesInternal(Collection<String> basePackages, List<Class<? extends Annotation>> annotations, ClassLoader loader) throws IOException, ClassNotFoundException {
        return Collections.emptyMap();
    }

    public static List<URL> findResources(String basePackage, String extension) throws IOException {
        return ClasspathScanner.findResources(basePackage, extension, null);
    }

    public static List<URL> findResources(String basePackage, String extension, ClassLoader loader) throws IOException {
        return ClasspathScanner.findResources(Collections.singletonList(basePackage), extension, loader);
    }

    public static List<URL> findResources(Collection<String> basePackages, String extension) throws IOException {
        return ClasspathScanner.findResources(basePackages, extension, null);
    }

    public static List<URL> findResources(Collection<String> basePackages, String extension, ClassLoader loader) throws IOException {
        return HELPER.findResourcesInternal(basePackages, extension, loader);
    }

    public static Set<String> parsePackages(String packagesAsCsv) {
        String[] values2 = packagesAsCsv.split(",");
        HashSet<String> basePackages = new HashSet<String>(values2.length);
        for (String value : values2) {
            String trimmed = value.trim();
            if (trimmed.equals(WILDCARD)) {
                basePackages.clear();
                basePackages.add(trimmed);
                break;
            }
            if (trimmed.length() <= 0) continue;
            basePackages.add(trimmed);
        }
        return basePackages;
    }

    protected List<URL> findResourcesInternal(Collection<String> basePackages, String extension, ClassLoader loader) throws IOException {
        return Collections.emptyList();
    }
}

