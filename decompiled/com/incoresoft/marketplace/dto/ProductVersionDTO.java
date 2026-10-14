/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.marketplace.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.marketplace.dto.ClientVisibility;
import com.incoresoft.marketplace.models.MarketplaceProductType;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ProductVersionDTO(@JsonProperty(value="id") Integer id, @JsonProperty(value="version") String version, @JsonProperty(value="product_id") String productId, @JsonProperty(value="url") String url, @JsonProperty(value="size") Integer size, @JsonProperty(value="sha1") String sha1, @JsonProperty(value="release_date") Instant releaseDate, @JsonProperty(value="platform") List<String> platform, @JsonProperty(value="product_name") String productName, @JsonProperty(value="product_type") MarketplaceProductType productType, @JsonProperty(value="ico") String ico, @JsonProperty(value="desc") String desc, @JsonProperty(value="attributes") Map<String, String> attributes, @JsonProperty(value="client_visibility") ClientVisibility clientVisibility) {
}

