/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security.callback;

import java.security.cert.Certificate;

public interface CertificateToNameMapper {
    public String getUserName(Certificate var1);
}

