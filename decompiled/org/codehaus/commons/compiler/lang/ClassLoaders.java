/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.lang;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.codehaus.commons.compiler.java8.java.util.function.Consumer;
import org.codehaus.commons.compiler.java9.java.lang.module.ModuleFinder;
import org.codehaus.commons.compiler.java9.java.lang.module.ModuleReference;
import org.codehaus.commons.compiler.util.resource.LocatableResource;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.NotNullByDefault;
import org.codehaus.commons.nullanalysis.Nullable;

public final class ClassLoaders {
    public static final ClassLoader CLASSPATH_CLASS_LOADER = ClassLoader.getSystemClassLoader();
    public static final ClassLoader BOOTCLASSPATH_CLASS_LOADER = ClassLoader.getSystemClassLoader().getParent();
    private static final SubresourceGetter BOOTCLASSPATH_SUBRESOURCES_OF;

    private ClassLoaders() {
    }

    public static ClassLoader getsResourceAsStream(final ResourceFinder finder, @Nullable ClassLoader parent) {
        return new ClassLoader(parent){

            @Override
            @NotNullByDefault(value=false)
            public URL getResource(String resourceName) {
                URL result = super.getResource(resourceName);
                if (result != null) {
                    return result;
                }
                Resource r = finder.findResource(resourceName);
                if (r == null) {
                    return null;
                }
                if (r instanceof LocatableResource) {
                    try {
                        return ((LocatableResource)r).getLocation();
                    }
                    catch (IOException ioe) {
                        return null;
                    }
                }
                return null;
            }

            @Override
            @NotNullByDefault(value=false)
            public InputStream getResourceAsStream(String resourceName) {
                InputStream result = super.getResourceAsStream(resourceName);
                if (result != null) {
                    return result;
                }
                try {
                    return finder.findResourceAsStream(resourceName);
                }
                catch (IOException ioe) {
                    return null;
                }
            }
        };
    }

    public static Map<String, URL> getSubresources(@Nullable ClassLoader classLoader, String name, boolean includeDirectories, boolean recurse) throws IOException {
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        assert (classLoader != null);
        HashMap<String, URL> result = new HashMap<String, URL>();
        for (URL r : Collections.list(classLoader.getResources(name))) {
            result.putAll(ClassLoaders.getSubresourcesOf(r, name, includeDirectories, recurse));
        }
        if (!result.isEmpty()) {
            return result;
        }
        result.putAll(ClassLoaders.getBootclasspathSubresourcesOf(name, includeDirectories, recurse));
        return result;
    }

    private static Map<? extends String, ? extends URL> getBootclasspathSubresourcesOf(String name, boolean includeDirectories, boolean recurse) throws IOException {
        return BOOTCLASSPATH_SUBRESOURCES_OF.get(name, includeDirectories, recurse);
    }

    public static Map<String, URL> getSubresourcesOf(URL root, String rootName, boolean includeDirectories, boolean recurse) throws IOException {
        String protocol = root.getProtocol();
        if ("jar".equalsIgnoreCase(protocol)) {
            JarURLConnection juc = (JarURLConnection)root.openConnection();
            juc.setUseCaches(false);
            if (!juc.getJarEntry().isDirectory()) {
                return Collections.singletonMap(rootName, root);
            }
            URL jarFileUrl = juc.getJarFileURL();
            JarFile jarFile = juc.getJarFile();
            Map<String, URL> result = ClassLoaders.getSubresources(jarFileUrl, jarFile, rootName, includeDirectories, recurse);
            if (includeDirectories) {
                result.put(rootName, root);
            }
            return result;
        }
        if ("file".equalsIgnoreCase(protocol)) {
            return ClassLoaders.getFileResources(root, rootName, includeDirectories, recurse);
        }
        return Collections.singletonMap(rootName, root);
    }

    private static Map<String, URL> getSubresources(URL jarFileUrl, JarFile jarFile, String namePrefix, boolean includeDirectories, boolean recurse) {
        HashMap<String, URL> result = new HashMap<String, URL>();
        Enumeration<JarEntry> en = jarFile.entries();
        while (en.hasMoreElements()) {
            URL url2;
            JarEntry je = en.nextElement();
            if (je.isDirectory() && !includeDirectories || !je.getName().startsWith(namePrefix) || !recurse && je.getName().indexOf(47, namePrefix.length()) != -1) continue;
            try {
                url2 = new URL("jar", null, jarFileUrl.toString() + "!/" + je.getName());
            }
            catch (MalformedURLException mue) {
                throw new AssertionError((Object)mue);
            }
            result.put(je.getName(), url2);
        }
        return result;
    }

    private static Map<String, URL> getFileResources(URL fileUrl, String namePrefix, boolean includeDirectories, boolean recurse) {
        File file = new File(fileUrl.getFile());
        if (file.isFile()) {
            return Collections.singletonMap(namePrefix, fileUrl);
        }
        if (file.isDirectory()) {
            if (!namePrefix.isEmpty() && !namePrefix.endsWith("/")) {
                namePrefix = namePrefix + '/';
            }
            HashMap<String, URL> result = new HashMap<String, URL>();
            if (includeDirectories) {
                result.put(namePrefix, fileUrl);
            }
            for (File member : file.listFiles()) {
                String memberName = namePrefix + member.getName();
                URL memberUrl = ClassLoaders.fileUrl(member);
                if (recurse) {
                    result.putAll(ClassLoaders.getFileResources(memberUrl, memberName, includeDirectories, recurse));
                    continue;
                }
                if (!member.isFile()) continue;
                result.put(memberName, memberUrl);
            }
            return result;
        }
        return Collections.emptyMap();
    }

    private static URL fileUrl(File file) {
        try {
            return file.toURI().toURL();
        }
        catch (MalformedURLException mue) {
            throw new AssertionError((Object)mue);
        }
    }

    static {
        URL r = ClassLoader.getSystemClassLoader().getResource("java/lang/Object.class");
        assert (r != null);
        String protocol = r.getProtocol();
        if ("jar".equalsIgnoreCase(protocol)) {
            JarFile jarFile;
            URL jarFileURL;
            try {
                JarURLConnection juc = (JarURLConnection)r.openConnection();
                juc.setUseCaches(false);
                jarFileURL = juc.getJarFileURL();
                jarFile = juc.getJarFile();
            }
            catch (IOException ioe) {
                throw new AssertionError((Object)ioe);
            }
            BOOTCLASSPATH_SUBRESOURCES_OF = new SubresourceGetter(){

                @Override
                public Map<? extends String, ? extends URL> get(String name, boolean includeDirectories, boolean recurse) {
                    return ClassLoaders.getSubresources(jarFileURL, jarFile, name, includeDirectories, recurse);
                }
            };
        } else if ("jrt".equalsIgnoreCase(protocol)) {
            final Set<ModuleReference> mrs = ModuleFinder.ofSystem().findAll();
            BOOTCLASSPATH_SUBRESOURCES_OF = new SubresourceGetter(){

                @Override
                public Map<? extends String, ? extends URL> get(final String name, boolean includeDirectories, final boolean recurse) throws IOException {
                    final HashMap result = new HashMap();
                    for (ModuleReference mr : mrs) {
                        final URI moduleContentLocation = mr.location().get();
                        mr.open().list().forEach((Consumer<String>)new Consumer<Object>(){

                            @Override
                            public void accept(Object resourceNameObject) {
                                String resourceName = (String)resourceNameObject;
                                try {
                                    this.accept2(resourceName);
                                }
                                catch (MalformedURLException mue) {
                                    throw new AssertionError((Object)mue);
                                }
                            }

                            public void accept2(String resourceName) throws MalformedURLException {
                                if ("module-info.class".equals(resourceName)) {
                                    return;
                                }
                                if ("_imported.marker".equals(resourceName)) {
                                    return;
                                }
                                if (resourceName.startsWith(name) && (recurse || resourceName.lastIndexOf(47) == name.length() - 1)) {
                                    URL classFileUrl = new URL(moduleContentLocation + "/" + resourceName);
                                    URL prev = result.put(resourceName, classFileUrl);
                                    assert (prev == null) : "prev=" + prev + ", resourceName=" + resourceName + ", classFileUrl=" + classFileUrl;
                                }
                            }
                        });
                    }
                    return result;
                }
            };
        } else {
            throw new AssertionError((Object)"\"java/lang/Object.class\" is not in a \"jar:\" location nor in a \"jrt:\" location");
        }
    }

    static interface SubresourceGetter {
        public Map<? extends String, ? extends URL> get(String var1, boolean var2, boolean var3) throws IOException;
    }
}

