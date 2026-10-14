/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.AntClassLoader
 */
package liquibase.integration.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import liquibase.Scope;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.resource.ZipResourceAccessor;
import liquibase.util.StringUtil;
import org.apache.tools.ant.AntClassLoader;

public class AntResourceAccessor
extends CompositeResourceAccessor {
    public AntResourceAccessor(AntClassLoader classLoader, String changeLogDirectory) {
        super(new ResourceAccessor[0]);
        if (changeLogDirectory != null) {
            changeLogDirectory = changeLogDirectory.replace("\\", "/");
        }
        if (changeLogDirectory == null) {
            try {
                this.addResourceAccessor(new DirectoryResourceAccessor(Paths.get(".", new String[0]).toAbsolutePath()));
            }
            catch (FileNotFoundException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine(e.getMessage(), e);
            }
            try {
                this.addResourceAccessor(new DirectoryResourceAccessor(Paths.get("/", new String[0]).toAbsolutePath()));
            }
            catch (FileNotFoundException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine(e.getMessage(), e);
            }
        } else {
            try {
                this.addResourceAccessor(new DirectoryResourceAccessor(new File(changeLogDirectory).toPath().toAbsolutePath()));
            }
            catch (FileNotFoundException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine(e.getMessage(), e);
            }
        }
        String classpath = StringUtil.trimToNull(classLoader.getClasspath());
        if (classpath != null) {
            for (String path : classpath.split(System.getProperty("path.separator"))) {
                try {
                    String lowercasePath = path.toLowerCase();
                    Path path1 = Paths.get(path, new String[0]);
                    if (lowercasePath.endsWith(".jar") || lowercasePath.endsWith(".zip")) {
                        this.addResourceAccessor(new ZipResourceAccessor(path1.toAbsolutePath()));
                        continue;
                    }
                    this.addResourceAccessor(new DirectoryResourceAccessor(path1.toAbsolutePath()));
                }
                catch (FileNotFoundException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine(e.getMessage(), e);
                }
            }
        }
    }
}

