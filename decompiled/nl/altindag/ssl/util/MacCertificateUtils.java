/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import nl.altindag.ssl.exception.GenericIOException;
import nl.altindag.ssl.util.CertificateUtils;
import nl.altindag.ssl.util.OperatingSystem;
import nl.altindag.ssl.util.internal.IOUtils;

final class MacCertificateUtils {
    private static final Path HOME_DIRECTORY = Paths.get(System.getProperty("user.home"), new String[0]);
    private static final String SYSTEM_ROOT_KEYCHAIN_FILE = "/System/Library/Keychains/SystemRootCertificates.keychain";
    private static final List<String> KEYCHAIN_LOOKUP_COMMANDS = Arrays.asList("list-keychains", "default-keychain");

    private MacCertificateUtils() {
    }

    static List<Certificate> getCertificates() {
        if (OperatingSystem.get() != OperatingSystem.MAC) {
            return Collections.emptyList();
        }
        String certificateContent = MacCertificateUtils.getKeychainFiles().stream().distinct().map(MacCertificateUtils::createProcessForGettingCertificates).map(Process::getInputStream).map(IOUtils::getContent).collect(Collectors.joining(System.lineSeparator()));
        return CertificateUtils.parsePemCertificate(certificateContent);
    }

    private static List<String> getKeychainFiles() {
        ArrayList<String> keychainFiles = new ArrayList<String>();
        keychainFiles.add(SYSTEM_ROOT_KEYCHAIN_FILE);
        KEYCHAIN_LOOKUP_COMMANDS.stream().map(MacCertificateUtils::createProcessForGettingKeychainFile).map(Process::getInputStream).map(IOUtils::getContent).flatMap(content -> Stream.of(content.split(System.lineSeparator())).map(line -> line.replace("\"", "")).map(String::trim)).forEach(keychainFiles::add);
        return keychainFiles;
    }

    private static Process createProcessForGettingKeychainFile(String command) {
        return MacCertificateUtils.createProcess("security " + command);
    }

    private static Process createProcessForGettingCertificates(String keychainFilePath) {
        return MacCertificateUtils.createProcess("security find-certificate -a -p " + keychainFilePath);
    }

    private static Process createProcess(String command) {
        try {
            return MacCertificateUtils.createProcess().command("sh", "-c", command).directory(HOME_DIRECTORY.toFile()).start();
        }
        catch (IOException e) {
            throw new GenericIOException(e);
        }
    }

    static ProcessBuilder createProcess() {
        return new ProcessBuilder(new String[0]);
    }
}

