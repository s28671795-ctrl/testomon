/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.models;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.marketplace.models.ProductVersion;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class MarketplacePluginInfo {
    private final String id;
    private final Version version;
    private final long byteSize;
    private final Instant updateDate;
    private final String icon;
    private final String name;
    private final String type;
    private final String description;
    private final String downloadUrl;
    private final List<String> platforms;
    private final String hash;
    private final boolean isInstalled;
    private final Map<String, String> attributes;

    public MarketplacePluginInfo(ProductVersion productVersion, boolean isInstalled) {
        this.id = productVersion.getId();
        this.version = productVersion.getVersion();
        this.byteSize = productVersion.getByteSize();
        this.updateDate = productVersion.getUpdateDate();
        this.icon = productVersion.getIcon();
        this.name = productVersion.getName();
        this.description = productVersion.getDescription();
        this.downloadUrl = productVersion.getDownloadUrl();
        this.platforms = productVersion.getPlatforms();
        this.hash = productVersion.getHash();
        this.isInstalled = isInstalled;
        this.type = productVersion.getProductType().getValue();
        this.attributes = productVersion.getAttributes();
    }

    public String getId() {
        return this.id;
    }

    public Version getVersion() {
        return this.version;
    }

    public long getByteSize() {
        return this.byteSize;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public List<String> getPlatforms() {
        return this.platforms;
    }

    public String getHash() {
        return this.hash;
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public MarketplacePluginInfo(String id, Version version, long byteSize, Instant updateDate, String icon, String name, String type, String description, String downloadUrl, List<String> platforms, String hash, boolean isInstalled, Map<String, String> attributes) {
        this.id = id;
        this.version = version;
        this.byteSize = byteSize;
        this.updateDate = updateDate;
        this.icon = icon;
        this.name = name;
        this.type = type;
        this.description = description;
        this.downloadUrl = downloadUrl;
        this.platforms = platforms;
        this.hash = hash;
        this.isInstalled = isInstalled;
        this.attributes = attributes;
    }
}

