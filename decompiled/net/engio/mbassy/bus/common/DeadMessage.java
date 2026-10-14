/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.common;

import net.engio.mbassy.bus.common.PublicationEvent;

public final class DeadMessage
extends PublicationEvent {
    public DeadMessage(Object message) {
        super(message);
    }
}

