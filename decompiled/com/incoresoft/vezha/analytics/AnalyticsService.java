/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated(forRemoval=true)
public enum AnalyticsService {
    processRunner("process_runner", "Process-Runner"),
    alpr("alpr", "ALPR"),
    face("face", "Face recognition"),
    gates("gates", "Gates control"),
    crowd("crowd", "Crowds detection"),
    objectInZone("object_in_zone", "Object in zone detection"),
    traffic("traffic", "Traffic"),
    hardhat("hardhat", "Hardhats"),
    motion("motion", "Motion detection"),
    genderAge("gender_age", "Gender/Age"),
    heatmap("heatmap", "Heatmap"),
    peopleQueue("people_queue", "People queues detection"),
    personReid("person_reid", "Person reidentification"),
    smoking("smoking", "Smoke detection"),
    smokeFire("smoke_fire", "Smoke and fire detection");

    private static final Logger log;
    private static final Map<String, AnalyticsService> servicesMap;
    private final String name;
    private final String localizedName;

    @Nullable
    public static AnalyticsService getByName(String name) {
        return servicesMap.get(Objects.requireNonNull(name, "name"));
    }

    private AnalyticsService(String name, String localizedName) {
        this.name = Objects.requireNonNull(name, "name");
        this.localizedName = Objects.requireNonNull(localizedName, "localizedName");
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public String getLocalizedName() {
        return this.localizedName;
    }

    static {
        log = LoggerFactory.getLogger(AnalyticsService.class);
        servicesMap = new HashMap<String, AnalyticsService>();
        for (AnalyticsService service2 : AnalyticsService.values()) {
            if (servicesMap.get(service2.getName()) != null) {
                throw new RuntimeException("duplicate name: " + service2.getName());
            }
            servicesMap.put(service2.getName(), service2);
        }
    }
}

