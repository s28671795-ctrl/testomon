/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import com.google.common.base.MoreObjects;
import io.minio.messages.DeleteMarker;
import io.minio.messages.Item;
import io.minio.messages.Prefix;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public abstract class ListObjectsResult {
    @Element(name="Name")
    private String name;
    @Element(name="EncodingType", required=false)
    private String encodingType;
    @Element(name="Prefix", required=false)
    private String prefix;
    @Element(name="Delimiter", required=false)
    private String delimiter;
    @Element(name="IsTruncated", required=false)
    private boolean isTruncated;
    @Element(name="MaxKeys", required=false)
    private int maxKeys;
    @ElementList(name="CommonPrefixes", inline=true, required=false)
    private List<Prefix> commonPrefixes;
    private static final List<DeleteMarker> deleteMarkers = Collections.unmodifiableList(new LinkedList());

    protected String decodeIfNeeded(String value) {
        try {
            return value != null && "url".equals(this.encodingType) ? URLDecoder.decode(value, StandardCharsets.UTF_8.name()) : value;
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String name() {
        return this.name;
    }

    public String encodingType() {
        return this.encodingType;
    }

    public String prefix() {
        return this.decodeIfNeeded(this.prefix);
    }

    public String delimiter() {
        return this.delimiter;
    }

    public boolean isTruncated() {
        return this.isTruncated;
    }

    public int maxKeys() {
        return this.maxKeys;
    }

    public List<Prefix> commonPrefixes() {
        return Collections.unmodifiableList(this.commonPrefixes == null ? new LinkedList() : this.commonPrefixes);
    }

    public List<DeleteMarker> deleteMarkers() {
        return deleteMarkers;
    }

    protected <T extends Item> List<T> emptyIfNull(List<T> lst) {
        return Collections.unmodifiableList(MoreObjects.firstNonNull(lst, new LinkedList()));
    }

    public abstract List<? extends Item> contents();
}

