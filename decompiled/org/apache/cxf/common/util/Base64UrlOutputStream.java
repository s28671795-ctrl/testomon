/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.OutputStream;
import org.apache.cxf.common.util.Base64OutputStream;

public class Base64UrlOutputStream
extends Base64OutputStream {
    public Base64UrlOutputStream(OutputStream out) {
        super(out, true);
    }
}

