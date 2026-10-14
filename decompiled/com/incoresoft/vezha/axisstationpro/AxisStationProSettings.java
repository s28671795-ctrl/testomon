/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.axisstationpro;

import com.incoresoft.vezha.dto.axisstationpro.AxisStationProSettingsDTO;

public record AxisStationProSettings(String host, Integer port, String username, String password) {
    public AxisStationProSettings(AxisStationProSettingsDTO dto) {
        this(dto.host(), dto.port(), dto.username(), dto.password());
    }
}

