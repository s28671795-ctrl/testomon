/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.media.grubbing.jni;

import com.incoresoft.media.grubbing.jni.FrameCodec;
import com.incoresoft.media.grubbing.jni.FrameData;
import com.incoresoft.media.grubbing.jni.JniLoader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameGrabber {
    private static final Logger log = LoggerFactory.getLogger(FrameGrabber.class);

    public native FrameData process(String var1, String var2, String var3, boolean var4);

    public native int codec_count();

    public native FrameCodec codec(int var1);

    static {
        try {
            JniLoader.loadFrameGrabberLib();
        }
        catch (IOException e) {
            log.error("Frame grabber lib initialization error", e);
        }
    }
}

