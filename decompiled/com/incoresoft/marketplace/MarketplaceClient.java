/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.marketplace;

import com.github.zafarkhaja.semver.Version;
import com.google.inject.Inject;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.marketplace.dto.ProductVersionDTO;
import com.incoresoft.marketplace.dto.ProductVersionsDTO;
import com.incoresoft.marketplace.exceptions.MarketplaceServerUnavailableException;
import com.incoresoft.marketplace.models.MarketplaceProductType;
import com.incoresoft.marketplace.models.ProductVersion;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.exceptions.http.HttpServerUnavailableException;
import com.incoresoft.plugin.commons.http.HttpClientUtils;
import com.incoresoft.version.models.MinorVersion;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarketplaceClient {
    private static final Logger log = LoggerFactory.getLogger(MarketplaceClient.class);
    private final HttpClient httpClient;
    private final String marketplaceUrl;

    @Inject
    public MarketplaceClient(HttpClient httpClient, ConfigManager<?> settings) {
        this.httpClient = httpClient;
        this.marketplaceUrl = settings.getMarketplace();
    }

    public List<ProductVersion> getProductVersions(MarketplaceProductType productType, MinorVersion version) {
        Map<String, String> parameters = Map.of("type", productType.name(), "minor_version", version.toString());
        String url2 = HttpClientUtils.withQueryParameters(this.marketplaceUrl + "/latest_product_versions", parameters);
        HttpGet request = new HttpGet(url2);
        return this.send(request);
    }

    public List<ProductVersion> getProductVersions(String productId, MinorVersion version) {
        Map<String, String> parameters = Map.of("minor_version", version.toString());
        String url2 = HttpClientUtils.withQueryParameters(String.format("%s/products/%s/versions", this.marketplaceUrl, productId), parameters);
        HttpGet request = new HttpGet(url2);
        return this.send(request);
    }

    public List<ProductVersion> getProductVersions(String productId, MinorVersion version, String platform) {
        Map<String, String> parameters = Map.of("minor_version", version.toString(), "platform", platform);
        String url2 = HttpClientUtils.withQueryParameters(String.format("%s/products/%s/versions", this.marketplaceUrl, productId), parameters);
        HttpGet request = new HttpGet(url2);
        return this.send(request);
    }

    public ProductVersion getProductVersion(String productId, Version version) {
        String url2 = String.format("%s/products/%s/versions/%s", this.marketplaceUrl, productId, version.toString());
        HttpGet request = new HttpGet(url2);
        try {
            return HttpClientUtils.send(this.httpClient, request, content -> new ProductVersion(Serialization.readValue(content, ProductVersionDTO.class)), new HttpClientUtils.HttpErrorResponseHandler[0]);
        }
        catch (HttpServerUnavailableException e) {
            throw new MarketplaceServerUnavailableException(e);
        }
    }

    public ProductVersion getProductVersion(String productId, Version version, String platform) {
        String withoutParams = String.format("%s/products/%s/versions/%s", this.marketplaceUrl, productId, version.toString());
        Map<String, String> parameters = Map.of("platform", platform);
        String url2 = HttpClientUtils.withQueryParameters(withoutParams, parameters);
        HttpGet request = new HttpGet(url2);
        try {
            return HttpClientUtils.send(this.httpClient, request, content -> new ProductVersion(Serialization.readValue(content, ProductVersionDTO.class)), new HttpClientUtils.HttpErrorResponseHandler[0]);
        }
        catch (HttpServerUnavailableException e) {
            throw new MarketplaceServerUnavailableException(e);
        }
    }

    private List<ProductVersion> send(ClassicHttpRequest request) {
        Function<String, List> mappingFunction = content -> Serialization.readValue(content, ProductVersionsDTO.class).versions().stream().map(ProductVersion::new).toList();
        try {
            return HttpClientUtils.send(this.httpClient, request, mappingFunction, new HttpClientUtils.HttpErrorResponseHandler[0]);
        }
        catch (HttpServerUnavailableException e) {
            throw new MarketplaceServerUnavailableException(e);
        }
    }
}

