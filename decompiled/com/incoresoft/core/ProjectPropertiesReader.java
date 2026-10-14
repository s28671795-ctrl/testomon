/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.core.exceptions.ReleaseDateIsNotSpecifiedException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jetbrains.annotations.NotNull;

public class ProjectPropertiesReader {
    private static final String POM_XML = "pom.xml";

    public Version getVersion() {
        if (Files.exists(Path.of(POM_XML, new String[0]), new LinkOption[0])) {
            Version version;
            MavenXpp3Reader reader = new MavenXpp3Reader();
            FileReader pomFileReader = new FileReader(POM_XML);
            try {
                Model model = reader.read(pomFileReader);
                version = Version.valueOf(model.getVersion());
            }
            catch (Throwable throwable) {
                try {
                    try {
                        pomFileReader.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                catch (IOException | XmlPullParserException e) {
                    throw new RuntimeException(e);
                }
            }
            pomFileReader.close();
            return version;
        }
        return Version.valueOf(this.getClass().getPackage().getSpecificationVersion());
    }

    public LocalDate getReleaseDate() {
        String releaseDateString = Files.exists(Path.of(POM_XML, new String[0]), new LinkOption[0]) ? ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) : ProjectPropertiesReader.readManifest().getMainAttributes().getValue("Release-Date");
        if (releaseDateString == null) {
            throw new ReleaseDateIsNotSpecifiedException();
        }
        return LocalDate.parse(releaseDateString);
    }

    private static Manifest readManifest() {
        Manifest manifest;
        JarFile currentJar = new JarFile(ProjectPropertiesReader.getCurrentJarFileName());
        try {
            manifest = currentJar.getManifest();
        }
        catch (Throwable throwable) {
            try {
                try {
                    currentJar.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        currentJar.close();
        return manifest;
    }

    @NotNull
    private static String getCurrentJarFileName() {
        return new File(ProjectPropertiesReader.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
    }
}

