/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import nl.altindag.ssl.exception.GenericIOException;
import nl.altindag.ssl.util.CertificateUtils;
import nl.altindag.ssl.util.OperatingSystem;

final class LinuxCertificateUtils {
    private static final String HOME_DIRECTORY = System.getProperty("user.home");
    private static final List<Path> LINUX_CERTIFICATE_PATHS = Stream.of("/etc/ssl/certs", "/etc/pki/nssdb", "/usr/local/share/ca-certificates", "/usr/share/ca-certificates", "/etc/pki/tls/certs/ca-bundle.crt", "/etc/pki/ca-trust/source/anchors", "/etc/pki/ca-trust/extracted/pem/tls-ca-bundle.pem", HOME_DIRECTORY + "/.pki/nssdb").map(x$0 -> Paths.get(x$0, new String[0])).collect(Collectors.toList());

    private LinuxCertificateUtils() {
    }

    static List<Certificate> getCertificates() {
        if (OperatingSystem.get() != OperatingSystem.LINUX) {
            return Collections.emptyList();
        }
        ArrayList<Certificate> certificates = new ArrayList<Certificate>();
        try {
            for (Path path : LINUX_CERTIFICATE_PATHS) {
                if (!Files.exists(path, new LinkOption[0])) continue;
                if (Files.isRegularFile(path, new LinkOption[0])) {
                    List<Certificate> certs = LinuxCertificateUtils.loadCertificate(path);
                    certificates.addAll(certs);
                    continue;
                }
                if (!Files.isDirectory(path, new LinkOption[0])) continue;
                Stream<Path> files = Files.walk(path, 1, FileVisitOption.FOLLOW_LINKS);
                try {
                    List certs = files.filter(x$0 -> Files.isRegularFile(x$0, new LinkOption[0])).flatMap(file -> LinuxCertificateUtils.loadCertificate(file).stream()).collect(Collectors.toList());
                    certificates.addAll(certs);
                }
                finally {
                    if (files == null) continue;
                    files.close();
                }
            }
            return Collections.unmodifiableList(certificates);
        }
        catch (IOException e) {
            throw new GenericIOException(e);
        }
    }

    static List<Certificate> loadCertificate(Path path) {
        try {
            return CertificateUtils.loadCertificate(path);
        }
        catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

