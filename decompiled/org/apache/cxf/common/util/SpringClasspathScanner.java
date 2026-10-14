/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.PathMatchingResourcePatternResolver
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.classreading.CachingMetadataReaderFactory
 *  org.springframework.core.type.classreading.MetadataReader
 *  org.springframework.util.ClassUtils
 */
package org.apache.cxf.common.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.util.ClasspathScanner;
import org.apache.cxf.common.util.SpringOsgiUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

class SpringClasspathScanner
extends ClasspathScanner {
    private static final Boolean IN_OSGI = SpringClasspathScanner.isSpringInOsgi();

    SpringClasspathScanner() throws Exception {
        Class.forName("org.springframework.core.io.support.PathMatchingResourcePatternResolver");
        Class.forName("org.springframework.core.type.classreading.CachingMetadataReaderFactory");
    }

    private static boolean isSpringInOsgi() {
        try {
            Class.forName("org.springframework.osgi.io.OsgiBundleResourcePatternResolver");
            Class.forName("org.springframework.osgi.util.BundleDelegatingClassLoader");
            return true;
        }
        catch (Throwable ex) {
            return false;
        }
    }

    @Override
    protected Map<Class<? extends Annotation>, Collection<Class<?>>> findClassesInternal(Collection<String> basePackages, List<Class<? extends Annotation>> annotations, ClassLoader loader) throws IOException, ClassNotFoundException {
        ResourcePatternResolver resolver2 = this.getResolver(loader);
        CachingMetadataReaderFactory factory2 = new CachingMetadataReaderFactory((ResourceLoader)resolver2);
        HashMap classes = new HashMap();
        HashMap matchingInterfaces = new HashMap();
        HashMap<String, String[]> nonMatchingClasses = new HashMap<String, String[]>();
        for (Class<? extends Annotation> clazz : annotations) {
            classes.put(clazz, new HashSet());
            matchingInterfaces.put(clazz, new HashSet());
        }
        if (basePackages == null || basePackages.isEmpty()) {
            return classes;
        }
        for (String string : basePackages) {
            Resource[] resources;
            boolean scanAllPackages = string.equals("*");
            String packageSearchPath = "classpath*:" + (scanAllPackages ? "" : ClassUtils.convertClassNameToResourcePath((String)string)) + "/**/*.class";
            Resource[] resourceArray = resources = resolver2.getResources(packageSearchPath);
            int n = resourceArray.length;
            for (int i = 0; i < n; ++i) {
                Resource resource = resourceArray[i];
                MetadataReader reader = factory2.getMetadataReader(resource);
                AnnotationMetadata metadata = reader.getAnnotationMetadata();
                if (scanAllPackages && this.shouldSkip(metadata.getClassName())) continue;
                for (Class<? extends Annotation> annotation : annotations) {
                    boolean concreteClass;
                    boolean bl = concreteClass = !metadata.isInterface() && !metadata.isAbstract();
                    if (metadata.isAnnotated(annotation.getName())) {
                        if (concreteClass) {
                            ((Collection)classes.get(annotation)).add(this.loadClass(metadata.getClassName(), loader));
                            continue;
                        }
                        ((Collection)matchingInterfaces.get(annotation)).add(metadata.getClassName());
                        continue;
                    }
                    if (!concreteClass || metadata.getInterfaceNames().length <= 0) continue;
                    nonMatchingClasses.put(metadata.getClassName(), metadata.getInterfaceNames());
                }
            }
        }
        if (!nonMatchingClasses.isEmpty()) {
            for (Map.Entry entry : matchingInterfaces.entrySet()) {
                block5: for (Map.Entry e2 : nonMatchingClasses.entrySet()) {
                    for (String intName : (String[])e2.getValue()) {
                        if (!((Collection)entry.getValue()).contains(intName)) continue;
                        ((Collection)classes.get(entry.getKey())).add(this.loadClass((String)e2.getKey(), loader));
                        continue block5;
                    }
                }
            }
        }
        for (Map.Entry entry : matchingInterfaces.entrySet()) {
            if (!((Collection)classes.get(entry.getKey())).isEmpty()) continue;
            for (String intName : (Collection)entry.getValue()) {
                ((Collection)classes.get(entry.getKey())).add(this.loadClass(intName, loader));
            }
        }
        return classes;
    }

    @Override
    protected List<URL> findResourcesInternal(Collection<String> basePackages, String extension, ClassLoader loader) throws IOException {
        ArrayList<URL> resourceURLs = new ArrayList<URL>();
        if (basePackages == null || basePackages.isEmpty()) {
            return resourceURLs;
        }
        ResourcePatternResolver resolver2 = this.getResolver(loader);
        for (String basePackage : basePackages) {
            Resource[] resources;
            boolean scanAllPackages = basePackage.equals("*");
            String theBasePackage = basePackage;
            if (theBasePackage.startsWith("classpath:")) {
                theBasePackage = theBasePackage.substring("classpath:".length());
            }
            String packageSearchPath = "classpath*:" + (scanAllPackages ? "" : (basePackage.contains("*") ? basePackage : ClassUtils.convertClassNameToResourcePath((String)theBasePackage))) + "/**/*" + "." + extension;
            for (Resource resource : resources = resolver2.getResources(packageSearchPath)) {
                resourceURLs.add(resource.getURL());
            }
        }
        return resourceURLs;
    }

    private ResourcePatternResolver getResolver(ClassLoader loader) {
        ResourcePatternResolver resolver2 = null;
        if (IN_OSGI.booleanValue()) {
            resolver2 = SpringOsgiUtil.getResolver(loader);
        }
        if (resolver2 == null) {
            resolver2 = loader != null ? new PathMatchingResourcePatternResolver(loader) : new PathMatchingResourcePatternResolver();
        }
        return resolver2;
    }

    private boolean shouldSkip(String classname) {
        for (String packageToSkip : PACKAGES_TO_SKIP) {
            if (!classname.startsWith(packageToSkip)) continue;
            return true;
        }
        return false;
    }

    private Class<?> loadClass(String className, ClassLoader loader) throws ClassNotFoundException {
        if (loader == null) {
            return ClassLoaderUtils.loadClass(className, this.getClass());
        }
        return loader.loadClass(className);
    }
}

