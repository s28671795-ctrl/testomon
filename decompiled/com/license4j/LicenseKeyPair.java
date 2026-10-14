/*
 * Decompiled with CFR 0.152.
 */
package com.license4j;

import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.util.Crypto;
import com.license4j.util.SignatureParams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseKeyPair {
    private static final Logger log = LoggerFactory.getLogger(LicenseKeyPair.class);
    public static final String KEYPAIR_ALGORITHM_DSA = "DSA";
    public static final String KEYPAIR_ALGORITHM_RSA = "RSA";
    public static final String SIGNATURE_ALGORITHM_SHA1withDSA = "SHA1withDSA";
    public static final String SIGNATURE_ALGORITHM_SHA256withRSA = "SHA256withRSA";
    public static final String SIGNATURE_ALGORITHM_SHA512withRSA = "SHA512withRSA";
    public static final int KEY_SIZE_512 = 512;
    public static final int KEY_SIZE_768 = 768;
    public static final int KEY_SIZE_1024 = 1024;
    public static final int KEY_SIZE_2048 = 2048;
    private final Properties properties;
    private final String licenseTextKeyPairAlgorithm;
    private final String licenseTextSignatureAlgorithm;
    private final int licenseTextKeySize;

    protected LicenseKeyPair(String id, String name, String description, String publicKey, String privateKey, SignatureParams signatureParams) {
        this(id, name, description, publicKey, privateKey, signatureParams.getKeypairAlgorithm(), signatureParams.getSignatureAlgorithm(), signatureParams.getKeySize());
    }

    protected LicenseKeyPair(String id, String name, String description, String publicKey, String privateKey, String keypairAlgorithm, String signatureAlgorithm, int keySize) {
        this.licenseTextKeyPairAlgorithm = keypairAlgorithm;
        this.licenseTextSignatureAlgorithm = signatureAlgorithm;
        this.licenseTextKeySize = keySize;
        this.properties = new Properties();
        this.properties.setProperty("id", id);
        this.properties.setProperty("timecreated", String.valueOf(System.currentTimeMillis()));
        if (name != null) {
            this.properties.setProperty("name", name);
        }
        if (description != null) {
            this.properties.setProperty("description", description);
        }
        this.properties.setProperty("publickey", publicKey);
        this.properties.setProperty("privatekey", privateKey);
    }

    public static LicenseKeyPair loadFromFile(String filename) throws LicenseSecurityException {
        Properties properties = new Properties();
        if (filename != null && filename.length() >= 100) {
            try {
                filename = Crypto.decrypt(filename);
            }
            catch (Exception e) {
                log.debug("EX4103", e);
                throw new LicenseSecurityException(e.getLocalizedMessage());
            }
            try {
                properties.load(new ByteArrayInputStream(filename.getBytes(StandardCharsets.UTF_8)));
            }
            catch (IOException e) {
                log.debug("EX4111", e);
            }
            if (properties.containsKey("id") && properties.containsKey("publickey")) {
                SignatureParams signatureParams = new SignatureParams(properties.getProperty("publickey"));
                String id = properties.getProperty("id");
                String publicKey = properties.getProperty("publickey");
                String privateKey = properties.getProperty("privatekey");
                String name = properties.getProperty("name");
                String description = properties.getProperty("description");
                return new LicenseKeyPair(id, name, description, publicKey, privateKey, signatureParams);
            }
            throw new IllegalArgumentException("Given key pair file content is invalid.");
        }
        throw new IllegalArgumentException("Keypair string error");
    }

    public String getPublicKey() {
        return this.properties.getProperty("publickey");
    }

    public String getPrivateKey() {
        return this.properties.getProperty("privatekey");
    }

    public void setName(String value) {
        if (value != null && !value.isBlank()) {
            this.properties.setProperty("name", value.trim());
        } else {
            this.properties.remove("name");
        }
    }

    public void setDescription(String value) {
        if (value != null && !value.isBlank()) {
            this.properties.setProperty("description", value.trim());
        } else {
            this.properties.remove("description");
        }
    }

    public String getID() {
        return this.properties.getProperty("id");
    }

    public String getName() {
        return this.properties.getProperty("name");
    }

    public String getDescription() {
        return this.properties.getProperty("description");
    }

    public Long getTimeCreated() {
        return Optional.ofNullable(this.properties.getProperty("timecreated")).map(Long::parseLong).orElse(null);
    }

    public String getLicenseTextKeyPairAlgorithm() {
        return this.licenseTextKeyPairAlgorithm;
    }

    public String getLicenseKeyKeyPairAlgorithm() {
        return "EC";
    }

    public String getLicenseKeySignatureAlgorithm() {
        return "SHA512withECDSA";
    }

    public String getContent() {
        String propertiesContent = this.buildPropertiesContent();
        String headerContent = this.buildHeaderContent();
        String encryptedContent = this.encryptContent(propertiesContent);
        return headerContent + encryptedContent;
    }

    private String buildPropertiesContent() {
        StringBuilder result = new StringBuilder();
        for (String property : this.getPropertiesList()) {
            result.append(property).append("=").append(this.properties.getProperty(property)).append("\n");
        }
        return result.toString();
    }

    private String buildHeaderContent() {
        StringBuilder header = new StringBuilder("# ");
        if (this.getName() != null) {
            header.append(this.getName()).append(" ");
        }
        header.append("KeyPair (id: ").append(this.getID()).append(")\n");
        return header.toString();
    }

    private String encryptContent(String content) {
        try {
            return Crypto.encrypt(content);
        }
        catch (Exception e) {
            log.debug("EX3101", e);
            return "";
        }
    }

    private List<String> getPropertiesList() {
        Enumeration<?> enumeration = this.properties.propertyNames();
        ArrayList<String> propertiesList = new ArrayList<String>();
        while (enumeration.hasMoreElements()) {
            propertiesList.add((String)enumeration.nextElement());
        }
        Collections.sort(propertiesList);
        return propertiesList;
    }

    public String toString() {
        return this.properties.getProperty("name") + "   (" + this.licenseTextKeyPairAlgorithm + " " + this.licenseTextKeySize + "bit)";
    }

    public Properties getProperties() {
        return this.properties;
    }

    public String getLicenseTextSignatureAlgorithm() {
        return this.licenseTextSignatureAlgorithm;
    }

    public int getLicenseTextKeySize() {
        return this.licenseTextKeySize;
    }
}

