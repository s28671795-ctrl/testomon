/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.marketplace.models;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.marketplace.dto.ClientVisibility;
import com.incoresoft.marketplace.dto.ProductVersionDTO;
import com.incoresoft.marketplace.models.MarketplaceProductType;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class ProductVersion {
    private final String id;
    private final Version version;
    private final long byteSize;
    private final Instant updateDate;
    private final String icon;
    private final String name;
    private final String description;
    private final String downloadUrl;
    private final List<String> platforms;
    private final String hash;
    private final MarketplaceProductType productType;
    private final Map<String, String> attributes;
    private final ClientVisibility clientVisibility;

    public ProductVersion(ProductVersionDTO dto) {
        this.id = dto.productId();
        this.version = Version.valueOf(dto.version());
        this.byteSize = dto.size().intValue();
        this.updateDate = dto.releaseDate();
        this.icon = dto.ico();
        this.name = dto.productName();
        this.description = dto.desc();
        this.downloadUrl = dto.url();
        this.platforms = dto.platform();
        this.hash = dto.sha1();
        this.productType = dto.productType();
        this.attributes = dto.attributes();
        this.clientVisibility = dto.clientVisibility();
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

    public MarketplaceProductType getProductType() {
        return this.productType;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public ClientVisibility getClientVisibility() {
        return this.clientVisibility;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ProductVersion)) {
            return false;
        }
        ProductVersion other = (ProductVersion)o;
        if (this.getByteSize() != other.getByteSize()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Version this$version = this.getVersion();
        Version other$version = other.getVersion();
        if (this$version == null ? other$version != null : !((Object)this$version).equals(other$version)) {
            return false;
        }
        Instant this$updateDate = this.getUpdateDate();
        Instant other$updateDate = other.getUpdateDate();
        if (this$updateDate == null ? other$updateDate != null : !((Object)this$updateDate).equals(other$updateDate)) {
            return false;
        }
        String this$icon = this.getIcon();
        String other$icon = other.getIcon();
        if (this$icon == null ? other$icon != null : !this$icon.equals(other$icon)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        String this$downloadUrl = this.getDownloadUrl();
        String other$downloadUrl = other.getDownloadUrl();
        if (this$downloadUrl == null ? other$downloadUrl != null : !this$downloadUrl.equals(other$downloadUrl)) {
            return false;
        }
        List<String> this$platforms = this.getPlatforms();
        List<String> other$platforms = other.getPlatforms();
        if (this$platforms == null ? other$platforms != null : !((Object)this$platforms).equals(other$platforms)) {
            return false;
        }
        String this$hash = this.getHash();
        String other$hash = other.getHash();
        if (this$hash == null ? other$hash != null : !this$hash.equals(other$hash)) {
            return false;
        }
        MarketplaceProductType this$productType = this.getProductType();
        MarketplaceProductType other$productType = other.getProductType();
        if (this$productType == null ? other$productType != null : !((Object)((Object)this$productType)).equals((Object)other$productType)) {
            return false;
        }
        Map<String, String> this$attributes = this.getAttributes();
        Map<String, String> other$attributes = other.getAttributes();
        if (this$attributes == null ? other$attributes != null : !((Object)this$attributes).equals(other$attributes)) {
            return false;
        }
        ClientVisibility this$clientVisibility = this.getClientVisibility();
        ClientVisibility other$clientVisibility = other.getClientVisibility();
        return !(this$clientVisibility == null ? other$clientVisibility != null : !((Object)((Object)this$clientVisibility)).equals((Object)other$clientVisibility));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $byteSize = this.getByteSize();
        result = result * 59 + (int)($byteSize >>> 32 ^ $byteSize);
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Version $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : ((Object)$version).hashCode());
        Instant $updateDate = this.getUpdateDate();
        result = result * 59 + ($updateDate == null ? 43 : ((Object)$updateDate).hashCode());
        String $icon = this.getIcon();
        result = result * 59 + ($icon == null ? 43 : $icon.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        String $downloadUrl = this.getDownloadUrl();
        result = result * 59 + ($downloadUrl == null ? 43 : $downloadUrl.hashCode());
        List<String> $platforms = this.getPlatforms();
        result = result * 59 + ($platforms == null ? 43 : ((Object)$platforms).hashCode());
        String $hash = this.getHash();
        result = result * 59 + ($hash == null ? 43 : $hash.hashCode());
        MarketplaceProductType $productType = this.getProductType();
        result = result * 59 + ($productType == null ? 43 : ((Object)((Object)$productType)).hashCode());
        Map<String, String> $attributes = this.getAttributes();
        result = result * 59 + ($attributes == null ? 43 : ((Object)$attributes).hashCode());
        ClientVisibility $clientVisibility = this.getClientVisibility();
        result = result * 59 + ($clientVisibility == null ? 43 : ((Object)((Object)$clientVisibility)).hashCode());
        return result;
    }

    public String toString() {
        return "ProductVersion(id=" + this.getId() + ", version=" + String.valueOf(this.getVersion()) + ", byteSize=" + this.getByteSize() + ", updateDate=" + String.valueOf(this.getUpdateDate()) + ", icon=" + this.getIcon() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", downloadUrl=" + this.getDownloadUrl() + ", platforms=" + String.valueOf(this.getPlatforms()) + ", hash=" + this.getHash() + ", productType=" + String.valueOf((Object)this.getProductType()) + ", attributes=" + String.valueOf(this.getAttributes()) + ", clientVisibility=" + String.valueOf((Object)this.getClientVisibility()) + ")";
    }
}

