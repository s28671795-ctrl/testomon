/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.logging.Logger;
import liquibase.resource.AbstractResource;
import liquibase.resource.InputStreamList;
import liquibase.resource.OpenOptions;
import liquibase.resource.Resource;
import liquibase.util.CollectionUtil;
import liquibase.util.FileUtil;

public interface ResourceAccessor
extends AutoCloseable {
    @Deprecated
    default public InputStreamList openStreams(String relativeTo, String streamPath) throws IOException {
        InputStreamList returnList = new InputStreamList();
        if (relativeTo != null) {
            Resource relativeToResource = this.get(relativeTo);
            streamPath = relativeToResource.resolveSibling(streamPath).getPath();
        }
        for (Resource resource : CollectionUtil.createIfNull(this.getAll(streamPath))) {
            returnList.add(resource.getUri(), resource.openInputStream());
        }
        return returnList;
    }

    @Deprecated
    default public InputStream openStream(String relativeTo, String streamPath) throws IOException {
        Resource resource;
        if (relativeTo != null) {
            streamPath = this.get(relativeTo).resolveSibling(streamPath).getPath();
        }
        if (!(resource = this.get(streamPath)).exists()) {
            return null;
        }
        return resource.openInputStream();
    }

    @Deprecated
    default public SortedSet<String> list(String relativeTo, String path, boolean recursive, boolean includeFiles, boolean includeDirectories) throws IOException {
        TreeSet<String> returnList = new TreeSet<String>();
        if (includeFiles) {
            if (relativeTo != null) {
                path = this.get(relativeTo).resolveSibling(path).getPath();
            }
            for (Resource resource : this.search(path, recursive)) {
                returnList.add(resource.getPath());
            }
        } else {
            throw new UnexpectedLiquibaseException("ResourceAccessor can no longer search only for directories");
        }
        return returnList;
    }

    default public List<Resource> search(String path, SearchOptions searchOptions) throws IOException {
        ArrayList<Resource> searchOptionsFilteredResourceList = new ArrayList<Resource>();
        if (searchOptions == null) {
            searchOptions = new SearchOptions();
        }
        if (searchOptions.getMaxDepth() <= 0) {
            return Collections.emptyList();
        }
        boolean searchRecursive = searchOptions.getMaxDepth() > 1;
        List<Resource> recursiveResourceList = this.search(path, searchRecursive);
        int minDepth = searchOptions.getMinDepth();
        int maxDepth = searchOptions.getMaxDepth();
        boolean endsWithFilterIsSet = searchOptions.endsWithFilterIsSet();
        String endsWithFilter = searchOptions.getEndsWithFilter();
        for (Resource res : CollectionUtil.createIfNull(recursiveResourceList)) {
            String resourcePath = res.getPath();
            int depth = (int)resourcePath.chars().filter(ch -> ch == 47).count() - (int)path.chars().filter(ch -> ch == 47).count() + 1;
            if (depth < minDepth || depth > maxDepth || endsWithFilterIsSet && !resourcePath.toLowerCase().endsWith(endsWithFilter.toLowerCase())) continue;
            searchOptionsFilteredResourceList.add(res);
        }
        return searchOptionsFilteredResourceList;
    }

    public List<Resource> search(String var1, boolean var2) throws IOException;

    public List<Resource> getAll(String var1) throws IOException;

    default public Resource getExisting(String path) throws IOException {
        Resource resource = this.get(path);
        if (!resource.exists()) {
            throw new FileNotFoundException(FileUtil.getFileNotFoundMessage(path));
        }
        return resource;
    }

    default public Resource get(String path) throws IOException {
        List<Resource> resources = this.getAll(path);
        if (resources == null || resources.size() == 0) {
            return new NotFoundResource(path, this);
        }
        if (resources.size() == 1) {
            return resources.iterator().next();
        }
        StringBuilder message = new StringBuilder("Found " + resources.size() + " files with the path '" + path + "':" + System.lineSeparator());
        for (Resource resource : resources) {
            message.append("    - ").append(resource.getUri()).append(System.lineSeparator());
        }
        message.append("  Search Path: ").append(System.lineSeparator());
        for (String location : Scope.getCurrentScope().getResourceAccessor().describeLocations()) {
            message.append("    - ").append(location).append(System.lineSeparator());
        }
        message.append("  You can limit the search path to remove duplicates with the liquibase.searchPath setting.");
        GlobalConfiguration.DuplicateFileMode mode = GlobalConfiguration.DUPLICATE_FILE_MODE.getCurrentValue();
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        if (mode == GlobalConfiguration.DuplicateFileMode.ERROR) {
            throw new IOException(message + " Or, if you KNOW these are the exact same file you can set liquibase.duplicateFileMode=WARN.");
        }
        if (mode == GlobalConfiguration.DuplicateFileMode.WARN) {
            Resource resource = resources.iterator().next();
            String warnMessage = message + System.lineSeparator() + "  To fail when duplicates are found, set liquibase.duplicateFileMode=ERROR" + System.lineSeparator() + "  Choosing: " + resource.getUri();
            Scope.getCurrentScope().getUI().sendMessage(warnMessage);
            log.warning(warnMessage);
        }
        return resources.iterator().next();
    }

    public List<String> describeLocations();

    public static class SearchOptions {
        private int minDepth = 0;
        private int maxDepth = 1;
        private String endsWithFilter = "";

        public boolean getRecursive() {
            return this.minDepth == 0 && this.maxDepth == Integer.MAX_VALUE;
        }

        public void setRecursive(boolean recursive) {
            if (recursive) {
                this.minDepth = 0;
                this.maxDepth = Integer.MAX_VALUE;
            } else {
                this.minDepth = 0;
                this.maxDepth = 1;
            }
        }

        public int getMinDepth() {
            return this.minDepth;
        }

        public void setMinDepth(int minDepth) {
            if (minDepth < 0) {
                throw new IllegalArgumentException("minDepth must be non-negative");
            }
            this.minDepth = minDepth;
        }

        public int getMaxDepth() {
            return this.maxDepth;
        }

        public void setMaxDepth(int maxDepth) {
            if (maxDepth < 0) {
                throw new IllegalArgumentException("maxDepth must be non-negative");
            }
            this.maxDepth = maxDepth;
        }

        public String getEndsWithFilter() {
            return this.endsWithFilter;
        }

        public void setTrimmedEndsWithFilter(String endsWithFilter) {
            this.endsWithFilter = endsWithFilter.trim();
        }

        public boolean endsWithFilterIsSet() {
            return this.endsWithFilter != null && this.endsWithFilter.trim().length() > 0;
        }
    }

    public static class NotFoundResource
    extends AbstractResource {
        private final ResourceAccessor resourceAccessor;

        public NotFoundResource(String path, ResourceAccessor resourceAccessor) {
            super(path, URI.create("resourceaccessor:" + path.replace(" ", "%20").replace('\\', '/')));
            this.resourceAccessor = resourceAccessor;
        }

        @Override
        public InputStream openInputStream() throws IOException {
            throw new UnexpectedLiquibaseException("Resource does not exist");
        }

        @Override
        public boolean isWritable() {
            return false;
        }

        @Override
        public boolean exists() {
            return false;
        }

        @Override
        public Resource resolve(String other) {
            try {
                return this.resourceAccessor.get(this.resolvePath(other));
            }
            catch (IOException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }

        @Override
        public Resource resolveSibling(String other) {
            try {
                return this.resourceAccessor.get(this.resolveSiblingPath(other));
            }
            catch (IOException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }

        @Override
        public OutputStream openOutputStream(OpenOptions openOptions) throws IOException {
            return this.openOutputStream(new OpenOptions());
        }
    }
}

