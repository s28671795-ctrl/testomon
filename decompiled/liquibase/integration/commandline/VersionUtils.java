/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.commandline;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import liquibase.Scope;
import liquibase.integration.commandline.LiquibaseLauncherSettings;
import liquibase.logging.mdc.customobjects.Version;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public class VersionUtils {
    public static Path getLiquibaseHomePath(Path workingDirectory) throws IOException {
        return new File(ObjectUtil.defaultIfNull(LiquibaseLauncherSettings.getSetting(LiquibaseLauncherSettings.LiquibaseLauncherSetting.LIQUIBASE_HOME), workingDirectory.toAbsolutePath().toString())).getAbsoluteFile().getCanonicalFile().toPath();
    }

    public static List<String> listLibraries(Map<String, LibraryInfo> libraryInfo, Path liquibaseHomePath, Path workingDirectory, Version mdcVersion) throws IOException {
        ArrayList<Version.Library> mdcLibraries = new ArrayList<Version.Library>(libraryInfo.size());
        ArrayList<String> libraries = new ArrayList<String>(libraryInfo.size());
        for (LibraryInfo info : new TreeSet<LibraryInfo>(libraryInfo.values())) {
            String filePath = info.file.getCanonicalPath();
            if (liquibaseHomePath != null && info.file.toPath().startsWith(liquibaseHomePath)) {
                filePath = liquibaseHomePath.relativize(info.file.toPath()).toString();
            }
            if (info.file.toPath().startsWith(workingDirectory)) {
                filePath = workingDirectory.relativize(info.file.toPath()).toString();
            }
            String libraryDescription = filePath + ": " + info.name + " " + (info.version == null ? "UNKNOWN" : info.version) + (info.vendor == null ? "" : " By " + info.vendor);
            libraries.add(libraryDescription);
            mdcLibraries.add(new Version.Library(info.name, filePath));
        }
        if (mdcVersion != null) {
            mdcVersion.setLiquibaseLibraries(new Version.LiquibaseLibraries(libraryInfo.size(), mdcLibraries));
        }
        return libraries;
    }

    public static Map<String, LibraryInfo> getLibraryInfoMap() throws URISyntaxException, IOException {
        HashMap<String, LibraryInfo> libraryInfo = new HashMap<String, LibraryInfo>();
        ClassLoader classLoader = VersionUtils.class.getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            for (URL url2 : ((URLClassLoader)classLoader).getURLs()) {
                File file;
                if (!url2.toExternalForm().startsWith("file:") || (file = new File(url2.toURI())).getName().equals("liquibase-core.jar") || !file.exists() || !file.getName().toLowerCase().endsWith(".jar")) continue;
                LibraryInfo thisInfo = VersionUtils.getLibraryInfo(file);
                libraryInfo.putIfAbsent(thisInfo.name, thisInfo);
            }
        }
        return libraryInfo;
    }

    private static LibraryInfo getLibraryInfo(File pathEntryFile) throws IOException {
        try (JarFile jarFile = new JarFile(pathEntryFile);){
            LibraryInfo libraryInfo = new LibraryInfo();
            libraryInfo.file = pathEntryFile;
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                libraryInfo.name = VersionUtils.getValue(manifest, "Bundle-Name", "Implementation-Title", "Specification-Title");
                libraryInfo.version = VersionUtils.getValue(manifest, "Bundle-Version", "Implementation-Version", "Specification-Version");
                libraryInfo.vendor = VersionUtils.getValue(manifest, "Bundle-Vendor", "Implementation-Vendor", "Specification-Vendor");
            }
            VersionUtils.handleCompilerJarEdgeCase(pathEntryFile, jarFile, libraryInfo);
            if (libraryInfo.name == null) {
                libraryInfo.name = pathEntryFile.getName().replace(".jar", "");
            }
            LibraryInfo libraryInfo2 = libraryInfo;
            return libraryInfo2;
        }
    }

    private static void handleCompilerJarEdgeCase(File pathEntryFile, JarFile jarFile, LibraryInfo libraryInfo) {
        try {
            if (pathEntryFile.toString().endsWith("compiler.jar") && StringUtil.isEmpty(libraryInfo.version)) {
                ZipEntry entry = jarFile.getEntry("META-INF/maven/com.github.spullara.mustache.java/compiler/pom.properties");
                InputStream inputStream2 = jarFile.getInputStream(entry);
                Properties jarProperties = new Properties();
                jarProperties.load(inputStream2);
                libraryInfo.version = jarProperties.getProperty("version");
            }
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(VersionUtils.class).fine("Failed to load the version of compiler.jar from its pom.properties, this is relatively harmless, but could mean that the version of compiler.jar will not appear in the liquibase --version console output.", e);
        }
    }

    private static String getValue(Manifest manifest, String ... keys2) {
        for (String key : keys2) {
            String value = manifest.getMainAttributes().getValue(key);
            if (value == null) continue;
            return value;
        }
        return null;
    }

    public static class LibraryInfo
    implements Comparable<LibraryInfo> {
        public String vendor;
        public String name;
        public File file;
        public String version;

        @Override
        public int compareTo(LibraryInfo o) {
            return this.file.compareTo(o.file);
        }
    }
}

