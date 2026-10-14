/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.face.race;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Schema(name="Race", description="Person race", example="white")
public enum Race {
    white("White"),
    hispanic("Hispanic"),
    black("Black"),
    asian("Asian"),
    eastIndian("East Indian"),
    middleEastern("Middle Eastern");

    private static final Map<String, Race> raceMap;
    private final String name;

    public static Race getByName(String name) {
        return raceMap.get(name);
    }

    private Race(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return this.name;
    }

    static {
        raceMap = new HashMap<String, Race>();
        for (Race race : Race.values()) {
            if (raceMap.containsKey(race.name)) {
                throw new RuntimeException("duplicate key: " + race.name);
            }
            raceMap.put(race.name, race);
        }
    }
}

