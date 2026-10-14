/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.onvif;

import com.incoresoft.vezha.models.streams.onvif.OnvifStream;
import com.incoresoft.vezha.models.streams.onvif.SuccessfullyAddedStream;
import java.util.List;

public record ImportResult(List<SuccessfullyAddedStream> added, List<OnvifStream> failed) {
}

