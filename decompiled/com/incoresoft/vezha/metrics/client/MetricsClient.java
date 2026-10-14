/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.client;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.plugin.commons.http.HttpClientUtils;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.dto.metrics.MetricsDTO;
import com.incoresoft.vezha.metrics.aggregated.AggregatedMetrics;
import com.incoresoft.vezha.metrics.snapshot.SnapshotMetrics;
import java.io.File;
import java.util.function.Function;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricsClient {
    private static final Logger log = LoggerFactory.getLogger(MetricsClient.class);
    private final HttpClient httpClient;
    private final String metricsUrl;

    @Inject
    public MetricsClient(HttpClient httpClient, VezhaConfigManager vezhaConfigManager) {
        this.httpClient = httpClient;
        this.metricsUrl = null;
    }

    public void send(AggregatedMetrics aggregatedMetrics, SnapshotMetrics snapshotMetrics, File logFile) {
        if (this.metricsUrl == null) {
            log.info("Metrics server is not defined, skipping metadata sending");
        }
        log.info("Metrics are not sent now, server for metrics is being implemented...");
        HttpPut request = new HttpPut(this.metricsUrl);
        MetricsDTO metricsDto = new MetricsDTO(aggregatedMetrics, snapshotMetrics);
        request.setEntity(MultipartEntityBuilder.create().addTextBody("metrics", JSON.toJSONString(metricsDto)).addBinaryBody("log_file", logFile).build());
        HttpClientUtils.send(this.httpClient, request, Function.identity(), new HttpClientUtils.HttpErrorResponseHandler[0]);
    }
}

