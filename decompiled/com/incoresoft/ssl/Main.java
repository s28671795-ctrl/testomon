/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.ssl;

import com.incoresoft.ssl.CertificateAndKey;
import com.incoresoft.ssl.SSLCertificateGenerator;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {
    public static void main(String[] args2) throws Exception {
        Duration validFor = Duration.of(36500L, ChronoUnit.DAYS);
        CertificateAndKey domain1 = SSLCertificateGenerator.createIncoresoftIssuedCertificate(List.of("127.0.0.1"), List.of("example1.com"), validFor);
        Files.writeString(Path.of("vms-core.crt", new String[0]), (CharSequence)domain1.certificateToString(), new OpenOption[0]);
        Files.writeString(Path.of("vms-core.key", new String[0]), (CharSequence)domain1.privateKeyToString(), new OpenOption[0]);
    }
}

