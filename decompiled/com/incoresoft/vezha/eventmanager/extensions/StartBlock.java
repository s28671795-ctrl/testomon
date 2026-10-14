/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager.extensions;

import com.incoresoft.vezha.eventmanager.extensions.GoBlock;

public class StartBlock
extends GoBlock {
    public static final String START_BLOCK_EXTENSION_ID = "start";

    @Override
    public String getNameLocaleKey() {
        return "Start";
    }

    @Override
    public String getExtensionId() {
        return START_BLOCK_EXTENSION_ID;
    }
}

