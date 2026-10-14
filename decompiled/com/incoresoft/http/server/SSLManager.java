/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http.server;

import com.google.inject.Inject;
import com.incoresoft.database.CertificateInfoRepository;
import com.incoresoft.http.SSLCertificateType;
import com.incoresoft.http.server.SSLCertificatePaths;
import com.incoresoft.network.NetworkSettings;
import com.incoresoft.network.NetworkSettingsManager;
import com.incoresoft.ssl.CertificateAndKey;
import com.incoresoft.ssl.SSLCertificateGenerator;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLManager {
    private static final Logger log = LoggerFactory.getLogger(SSLManager.class);
    private static final Path INTERNAL_CERTIFICATE_FOLDER = Path.of("crt", "internal");
    private static final Path INTERNAL_CERTIFICATE_PATH = INTERNAL_CERTIFICATE_FOLDER.resolve("vms-core.crt");
    private static final Path INTERNAL_PRIVATE_KEY_PATH = INTERNAL_CERTIFICATE_FOLDER.resolve("vms-core.key");
    private static final Path EXTERNAL_CERTIFICATE_FOLDER = Path.of("crt", "external");
    private static final Path EXTERNAL_CERTIFICATE_PATH = EXTERNAL_CERTIFICATE_FOLDER.resolve("vms-core.crt");
    private static final Path EXTERNAL_PRIVATE_KEY_PATH = EXTERNAL_CERTIFICATE_FOLDER.resolve("vms-core.key");
    private static final Pattern IP_V4_PATTERN = Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$");
    private static final String LOOPBACK_ADDRESS = "127.0.0.1";
    private static final String LOCALHOST = "localhost";
    private final NetworkSettingsManager networkSettingsManager;
    private final CertificateInfoRepository certificateInfoRepository;

    public Optional<String> getCertificatePassword() {
        return this.certificateInfoRepository.getCertificatePassword();
    }

    public SSLCertificateType getCurrentCertificateType() {
        if (Files.exists(EXTERNAL_CERTIFICATE_PATH, new LinkOption[0]) && Files.exists(EXTERNAL_PRIVATE_KEY_PATH, new LinkOption[0])) {
            return SSLCertificateType.EXTERNAL;
        }
        return SSLCertificateType.INTERNAL;
    }

    public SSLCertificatePaths getCurrentCertificatePaths() {
        if (Files.exists(EXTERNAL_CERTIFICATE_PATH, new LinkOption[0]) && Files.exists(EXTERNAL_PRIVATE_KEY_PATH, new LinkOption[0])) {
            return new SSLCertificatePaths(EXTERNAL_PRIVATE_KEY_PATH, EXTERNAL_CERTIFICATE_PATH);
        }
        if (Files.exists(INTERNAL_CERTIFICATE_PATH, new LinkOption[0]) && Files.exists(INTERNAL_PRIVATE_KEY_PATH, new LinkOption[0])) {
            return new SSLCertificatePaths(INTERNAL_PRIVATE_KEY_PATH, INTERNAL_CERTIFICATE_PATH);
        }
        log.warn("No SSL certificate found, generating a new one");
        this.setupCertificateForCurrentService();
        return new SSLCertificatePaths(INTERNAL_PRIVATE_KEY_PATH, INTERNAL_CERTIFICATE_PATH);
    }

    public void installExternalCertificate(@NotNull InputStream privateKey, @NotNull InputStream certificate, @Nullable String password) {
        if (!Files.exists(EXTERNAL_CERTIFICATE_FOLDER, new LinkOption[0])) {
            try {
                Files.createDirectories(EXTERNAL_CERTIFICATE_FOLDER, new FileAttribute[0]);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Files.copy(privateKey, EXTERNAL_PRIVATE_KEY_PATH, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(certificate, EXTERNAL_CERTIFICATE_PATH, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.certificateInfoRepository.setCertificatePassword(password);
    }

    public void setupCertificateForCurrentService() {
        CertificateAndKey generatedCertificate;
        ArrayList<String> ips = new ArrayList<String>(this.getLocalAddresses());
        ArrayList<String> hosts = new ArrayList<String>();
        NetworkSettings networkSettings = this.networkSettingsManager.get();
        if (networkSettings.publicAddress() != null) {
            String access = networkSettings.publicAddress().getHost();
            if (this.isIpV4Address(access)) {
                ips.add(access);
            } else {
                hosts.add(access);
            }
        }
        if (!ips.contains(LOOPBACK_ADDRESS)) {
            ips.add(LOOPBACK_ADDRESS);
        }
        if (!hosts.contains(LOCALHOST)) {
            hosts.add(LOCALHOST);
        }
        try {
            generatedCertificate = SSLCertificateGenerator.createIncoresoftIssuedCertificate(ips, hosts, Duration.ofDays(36500L));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!Files.exists(INTERNAL_CERTIFICATE_FOLDER, new LinkOption[0])) {
            try {
                Files.createDirectories(INTERNAL_CERTIFICATE_FOLDER, new FileAttribute[0]);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Files.writeString(INTERNAL_CERTIFICATE_PATH, (CharSequence)generatedCertificate.certificateToString(), new OpenOption[0]);
            Files.writeString(INTERNAL_PRIVATE_KEY_PATH, (CharSequence)generatedCertificate.privateKeyToString(), new OpenOption[0]);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getLocalAddresses() {
        try {
            ArrayList<String> addresses = new ArrayList<String>();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!(inetAddress instanceof Inet4Address)) continue;
                    addresses.add(inetAddress.getHostAddress());
                }
            }
            return Collections.unmodifiableList(addresses);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isIpV4Address(String address) {
        return IP_V4_PATTERN.matcher(address).matches();
    }

    public void deleteExternalCertificate() {
        try {
            Files.deleteIfExists(EXTERNAL_CERTIFICATE_PATH);
            Files.deleteIfExists(EXTERNAL_PRIVATE_KEY_PATH);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.certificateInfoRepository.resetCertificatePassword();
    }

    @Inject
    public SSLManager(NetworkSettingsManager networkSettingsManager, CertificateInfoRepository certificateInfoRepository) {
        this.networkSettingsManager = networkSettingsManager;
        this.certificateInfoRepository = certificateInfoRepository;
    }
}

