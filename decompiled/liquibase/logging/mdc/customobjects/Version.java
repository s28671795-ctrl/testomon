/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import java.util.List;
import liquibase.integration.commandline.Banner;
import liquibase.logging.mdc.CustomMdcObject;

public class Version
implements CustomMdcObject {
    private Banner liquibaseVersion;
    private JavaHome javaHome;
    private LiquibaseLibraries liquibaseLibraries;

    public Version() {
    }

    public Version(Banner liquibaseVersion, JavaHome javaHome, LiquibaseLibraries liquibaseLibraries) {
        this.liquibaseVersion = liquibaseVersion;
        this.javaHome = javaHome;
        this.liquibaseLibraries = liquibaseLibraries;
    }

    public Banner getLiquibaseVersion() {
        return this.liquibaseVersion;
    }

    public void setLiquibaseVersion(Banner liquibaseVersion) {
        this.liquibaseVersion = liquibaseVersion;
    }

    public JavaHome getJavaHome() {
        return this.javaHome;
    }

    public void setJavaHome(JavaHome javaHome) {
        this.javaHome = javaHome;
    }

    public LiquibaseLibraries getLiquibaseLibraries() {
        return this.liquibaseLibraries;
    }

    public void setLiquibaseLibraries(LiquibaseLibraries liquibaseLibraries) {
        this.liquibaseLibraries = liquibaseLibraries;
    }

    public static class JavaHome {
        private String path;
        private String version;

        public JavaHome() {
        }

        public JavaHome(String path, String version) {
            this.path = path;
            this.version = version;
        }

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public static class LiquibaseLibraries {
        private int libraryCount;
        private List<Library> libraries;

        public LiquibaseLibraries() {
        }

        public LiquibaseLibraries(int libraryCount, List<Library> libraries) {
            this.libraryCount = libraryCount;
            this.libraries = libraries;
        }

        public int getLibraryCount() {
            return this.libraryCount;
        }

        public void setLibraryCount(int libraryCount) {
            this.libraryCount = libraryCount;
        }

        public List<Library> getLibraries() {
            return this.libraries;
        }

        public void setLibraries(List<Library> libraries) {
            this.libraries = libraries;
        }
    }

    public static class Library {
        private String name;
        private String path;

        public Library() {
        }

        public Library(String name, String path) {
            this.name = name;
            this.path = path;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}

