/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.config.models;

import com.incoresoft.vezha.dto.config.AnalyticsVramDTO;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsVramConfig {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsVramConfig.class);
    public static final double DEFAULT_GB = 0.3;
    public static final double DEFAULT_GB_PER_MEGAPIXEL = 0.085;
    public static final double DEFAULT_MIN_GB = 0.1;
    public static final double FULL_HD_MEGAPIXELS = 2.0736;
    private final Map<String, Double> perType;
    private final double defaultGb;
    private final double gbPerMegapixel;
    private final double minGb;

    public AnalyticsVramConfig() {
        this(Map.of(), 0.3);
    }

    public AnalyticsVramConfig(Map<String, Double> perType, double defaultGb) {
        this(perType, defaultGb, 0.085, 0.1);
    }

    public AnalyticsVramConfig(Map<String, Double> perType, double defaultGb, double gbPerMegapixel, double minGb) {
        this.perType = AnalyticsVramConfig.sanitizeOverrides(perType);
        this.defaultGb = AnalyticsVramConfig.sanitize(defaultGb, 0.3, "default");
        this.gbPerMegapixel = AnalyticsVramConfig.sanitize(gbPerMegapixel, 0.085, "gb_per_megapixel");
        this.minGb = AnalyticsVramConfig.sanitize(minGb, 0.1, "min_gb");
    }

    public AnalyticsVramConfig(AnalyticsVramDTO dto) {
        this(dto == null ? Map.of() : dto.getPerType(), dto == null || dto.getDefaultGb() == null ? 0.3 : dto.getDefaultGb(), dto == null || dto.getGbPerMegapixel() == null ? 0.085 : dto.getGbPerMegapixel(), dto == null || dto.getMinGb() == null ? 0.1 : dto.getMinGb());
    }

    private static double sanitize(double value, double fallback, String name) {
        if (Double.isFinite(value) && value >= 0.0) {
            return value;
        }
        log.warn("analytics_vram.{}={} is not a finite non-negative number, using {}", name, value, fallback);
        return fallback;
    }

    private static Map<String, Double> sanitizeOverrides(Map<String, Double> perType) {
        if (perType == null || perType.isEmpty()) {
            return Map.of();
        }
        HashMap clean = new HashMap();
        perType.forEach((type, gb) -> {
            if (type != null && gb != null && Double.isFinite(gb) && gb >= 0.0) {
                clean.put(type, gb);
            } else {
                log.warn("analytics_vram.per_type[{}]={} is not a finite non-negative number, ignoring it", type, gb);
            }
        });
        return Map.copyOf(clean);
    }

    public double estimateGb(String type) {
        return this.estimateGb(type, null);
    }

    public double estimateGb(String type, Double megapixels, boolean resizedToFullHd) {
        if (resizedToFullHd && megapixels != null) {
            megapixels = Math.min(megapixels, 2.0736);
        }
        return this.estimateGb(type, megapixels);
    }

    public double estimateGb(String type, Double megapixels) {
        Double override;
        if (type != null && (override = this.perType.get(type)) != null) {
            return override;
        }
        if (megapixels != null && Double.isFinite(megapixels) && megapixels > 0.0) {
            return Math.max(this.minGb, megapixels * this.gbPerMegapixel);
        }
        return this.defaultGb;
    }

    public Map<String, Double> getPerType() {
        return this.perType;
    }

    public double getDefaultGb() {
        return this.defaultGb;
    }

    public double getGbPerMegapixel() {
        return this.gbPerMegapixel;
    }

    public double getMinGb() {
        return this.minGb;
    }
}

