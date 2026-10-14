/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.model;

import javax.validation.constraints.NotNull;

public record CameraContainer(int id, boolean enabled, @NotNull String state) {
}

