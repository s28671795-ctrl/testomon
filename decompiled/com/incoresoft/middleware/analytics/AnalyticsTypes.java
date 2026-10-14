/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics;

import com.incoresoft.middleware.analytics.AnalyticsType;

public enum AnalyticsTypes implements AnalyticsType
{
    traffic,
    alpr,
    gates,
    face,
    object_in_zone,
    crowd,
    hardhat,
    motion,
    gender_age,
    heatmap,
    people_queue,
    person_reid,
    smoking,
    smoke_fire,
    fallen_people,
    parking_lots,
    smart_va,
    forensic,
    red_light,
    gun_detection,
    railroad,
    military,
    traffic_lights,
    forest_fire,
    operator_ai;


    @Override
    public String getName() {
        return this.toString();
    }
}

