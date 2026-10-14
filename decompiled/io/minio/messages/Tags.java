/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name="Tagging", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class Tags {
    private static final int MAX_KEY_LENGTH = 128;
    private static final int MAX_VALUE_LENGTH = 256;
    private static final int MAX_OBJECT_TAG_COUNT = 10;
    private static final int MAX_TAG_COUNT = 50;
    @Path(value="TagSet")
    @ElementMap(attribute=false, entry="Tag", inline=true, key="Key", value="Value", required=false)
    Map<String, String> tags;

    public Tags() {
    }

    private Tags(Map<String, String> tags, boolean isObject) throws IllegalArgumentException {
        int limit;
        if (tags == null) {
            return;
        }
        int n = limit = isObject ? 10 : 50;
        if (tags.size() > limit) {
            throw new IllegalArgumentException("too many " + (isObject ? "object" : "bucket") + " tags; allowed = " + limit + ", found = " + tags.size());
        }
        for (Map.Entry<String, String> entry : tags.entrySet()) {
            String key = entry.getKey();
            if (key.length() == 0 || key.length() > 128 || key.contains("&")) {
                throw new IllegalArgumentException("invalid tag key '" + key + "'");
            }
            String value = entry.getValue();
            if (value.length() <= 256 && !value.contains("&")) continue;
            throw new IllegalArgumentException("invalid tag value '" + value + "'");
        }
        this.tags = Collections.unmodifiableMap(tags);
    }

    public static Tags newBucketTags(Map<String, String> tags) throws IllegalArgumentException {
        return new Tags(tags, false);
    }

    public static Tags newObjectTags(Map<String, String> tags) throws IllegalArgumentException {
        return new Tags(tags, true);
    }

    public Map<String, String> get() {
        return Collections.unmodifiableMap(this.tags == null ? new HashMap() : this.tags);
    }
}

