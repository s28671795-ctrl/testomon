/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.logging.Logger;
import liquibase.resource.AbstractResourceAccessor;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;

public abstract class AbstractPathResourceAccessor
extends AbstractResourceAccessor {
    protected abstract Path getRootPath();

    @Override
    public String toString() {
        return this.getClass().getName() + " (" + this.getRootPath() + ")";
    }

    @Override
    public List<String> describeLocations() {
        return Collections.singletonList(this.getRootPath().toString());
    }

    @Override
    public List<Resource> getAll(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null");
        }
        ArrayList<Resource> returnList = new ArrayList<Resource>();
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        if ((path = this.standardizePath(path)) == null) {
            return returnList;
        }
        Path finalPath = this.getRootPath().resolve(path);
        if (Files.exists(finalPath, new LinkOption[0])) {
            returnList.add(this.createResource(finalPath, path));
        } else {
            log.fine("Path " + path + " in " + this.getRootPath() + " does not exist (" + this + ")");
        }
        return returnList;
    }

    private String standardizePath(String path) {
        try {
            String rootPath = this.getRootPath().toString();
            String result = new File(path).toPath().normalize().toString().replace("\\", "/").replaceFirst("^/", "");
            if (rootPath.equals("/") || rootPath.equals("\\")) {
                return result;
            }
            return result.replaceFirst("^\\w:/", "");
        }
        catch (InvalidPathException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to standardize path " + path, e);
            return path;
        }
    }

    @Override
    public List<Resource> search(String startPath, ResourceAccessor.SearchOptions searchOptions) throws IOException {
        final int minDepth = searchOptions.getMinDepth();
        int maxDepth = searchOptions.getMaxDepth();
        final boolean endsWithFilterIsSet = searchOptions.endsWithFilterIsSet();
        final String endsWithFilter = searchOptions.getEndsWithFilter();
        if (startPath == null) {
            throw new IllegalArgumentException("Path must not be null");
        }
        startPath = startPath.replaceFirst("^file:/+", "").replaceFirst("^/", "");
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        final Path rootPath = this.getRootPath();
        final Path basePath = rootPath.resolve(startPath);
        final ArrayList<Resource> returnSet = new ArrayList<Resource>();
        if (!Files.exists(basePath, new LinkOption[0])) {
            log.fine("Path " + startPath + " in " + rootPath + " does not exist (" + this + ")");
            return returnSet;
        }
        if (!Files.isDirectory(basePath, new LinkOption[0])) {
            throw new IOException("'" + startPath + "' is a file, not a directory");
        }
        SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (attrs.isRegularFile() && this.meetsSearchCriteria(file)) {
                    this.addToReturnList(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            private boolean meetsSearchCriteria(Path file) {
                int depth = file.getParent().getNameCount() - basePath.getNameCount() + 1;
                if (depth < minDepth) {
                    return false;
                }
                return !endsWithFilterIsSet || file.toString().toLowerCase().endsWith(endsWithFilter.toLowerCase());
            }

            private void addToReturnList(Path file) {
                String pathToAdd = rootPath.relativize(file).normalize().toString().replace("\\", "/");
                pathToAdd = pathToAdd.replaceFirst("/$", "");
                returnSet.add(AbstractPathResourceAccessor.this.createResource(file, pathToAdd));
            }
        };
        Files.walkFileTree(basePath, Collections.singleton(FileVisitOption.FOLLOW_LINKS), maxDepth, (FileVisitor<? super Path>)fileVisitor);
        return returnSet;
    }

    @Override
    public List<Resource> search(String startPath, boolean recursive) throws IOException {
        ResourceAccessor.SearchOptions searchOptions = new ResourceAccessor.SearchOptions();
        searchOptions.setRecursive(recursive);
        return this.search(startPath, searchOptions);
    }

    protected abstract Resource createResource(Path var1, String var2);
}

