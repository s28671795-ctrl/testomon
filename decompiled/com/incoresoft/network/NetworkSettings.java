/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.network;

import java.net.URI;

public record NetworkSettings(int id, String name, String localMask, URI publicAddress) {
}

