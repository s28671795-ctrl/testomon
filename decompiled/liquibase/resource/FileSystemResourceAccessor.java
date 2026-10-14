/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.resource.ZipResourceAccessor;
import liquibase.util.CollectionUtil;

@Deprecated
public class FileSystemResourceAccessor
extends CompositeResourceAccessor {
    public FileSystemResourceAccessor(File ... baseDirsAndFiles) {
        super(new ResourceAccessor[0]);
        for (File base : CollectionUtil.createIfNull(baseDirsAndFiles)) {
            this.addRootPath(base);
        }
    }

    public FileSystemResourceAccessor(String file) {
        this(new File(file));
    }

    protected void addRootPath(Path path) {
        if (path == null) {
            return;
        }
        this.addRootPath(path.toFile());
    }

    protected void addRootPath(File base) {
        block6: {
            if (base == null) {
                return;
            }
            try {
                if (!base.exists()) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning("Non-existent path: " + base.getAbsolutePath());
                    break block6;
                }
                if (base.isDirectory()) {
                    this.addResourceAccessor(new DirectoryResourceAccessor(base));
                    break block6;
                }
                if (base.getName().endsWith(".jar") || base.getName().toLowerCase().endsWith("zip")) {
                    this.addResourceAccessor(new ZipResourceAccessor(base));
                    break block6;
                }
                throw new IllegalArgumentException(base.getAbsolutePath() + " must be a directory, jar or zip");
            }
            catch (FileNotFoundException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
    }
}

