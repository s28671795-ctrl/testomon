/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.EventNode;

public interface EventReader {
    public EventNode next() throws Exception;

    public EventNode peek() throws Exception;
}

