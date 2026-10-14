/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sdk.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import liquibase.resource.AbstractResourceAccessor;
import liquibase.resource.Resource;
import liquibase.sdk.resource.MockResource;

public class MockResourceAccessor
extends AbstractResourceAccessor {
    private final SortedMap<String, String> contentByFileName;

    public MockResourceAccessor() {
        this((Map<String, String>)new HashMap<String, String>());
    }

    public MockResourceAccessor(Map<String, String> contentByFileName) {
        this.contentByFileName = new TreeMap<String, String>(contentByFileName);
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public List<Resource> getAll(String path) throws IOException {
        path = path.replace("\\", "/");
        String content = (String)this.contentByFileName.get(path);
        ArrayList<Resource> returnSet = new ArrayList<Resource>();
        if (content != null) {
            returnSet.add(new MockResource(path, content));
        }
        if (returnSet.isEmpty()) {
            return null;
        }
        return returnSet;
    }

    @Override
    public List<Resource> search(String path, boolean recursive) throws IOException {
        path = path.replace("\\", "/");
        ArrayList<Resource> returnList = new ArrayList<Resource>();
        for (String file : this.contentByFileName.keySet()) {
            if (!file.startsWith(path) || !recursive && file.split("/").length - path.split("/").length > 2) continue;
            returnList.add(new MockResource(file, (String)this.contentByFileName.get(file)));
        }
        return returnList;
    }

    @Override
    public List<String> describeLocations() {
        return Collections.singletonList("MockResouceAccessor.java");
    }

    public void setContent(String fileName, String content) {
        this.contentByFileName.put(fileName, content);
    }
}

