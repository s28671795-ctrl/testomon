/*
 * Decompiled with CFR 0.152.
 */
package io.minio.credentials;

import io.minio.credentials.Credentials;
import io.minio.credentials.EnvironmentProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.ProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Nullable;

public class AwsConfigProvider
extends EnvironmentProvider {
    private final String filename;
    private final String profile;

    public AwsConfigProvider(@Nullable String filename, @Nullable String profile) {
        if (filename != null && filename.isEmpty()) {
            throw new IllegalArgumentException("Filename must not be empty");
        }
        if (profile != null && profile.isEmpty()) {
            throw new IllegalArgumentException("Profile must not be empty");
        }
        this.filename = filename;
        this.profile = profile;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Credentials fetch() {
        String profile;
        String filename = this.filename;
        if (filename == null) {
            filename = this.getProperty("AWS_SHARED_CREDENTIALS_FILE");
        }
        if (filename == null) {
            filename = Paths.get(System.getProperty("user.home"), ".aws", "credentials").toString();
        }
        if ((profile = this.profile) == null) {
            profile = this.getProperty("AWS_PROFILE");
        }
        if (profile == null) {
            profile = "default";
        }
        try (FileInputStream is = new FileInputStream(filename);){
            Map<String, Properties> result = this.unmarshal(new InputStreamReader((InputStream)is, StandardCharsets.UTF_8));
            Properties values2 = result.get(profile);
            if (values2 == null) {
                throw new ProviderException("Profile " + profile + " does not exist in AWS credential file");
            }
            String accessKey = values2.getProperty("aws_access_key_id");
            String secretKey = values2.getProperty("aws_secret_access_key");
            String sessionToken = values2.getProperty("aws_session_token");
            if (accessKey == null) {
                throw new ProviderException("Access key does not exist in profile " + profile + " in AWS credential file");
            }
            if (secretKey == null) {
                throw new ProviderException("Secret key does not exist in profile " + profile + " in AWS credential file");
            }
            Credentials credentials = new Credentials(accessKey, secretKey, sessionToken, null);
            return credentials;
        }
        catch (IOException e) {
            throw new ProviderException("Unable to read AWS credential file", e);
        }
    }

    private Map<String, Properties> unmarshal(Reader reader) throws IOException {
        return new Ini().unmarshal(reader);
    }

    private static class Ini {
        private Map<String, Properties> result = new HashMap<String, Properties>();

        private Ini() {
        }

        public Map<String, Properties> unmarshal(Reader reader) throws IOException {
            new Properties(){
                private Properties section;

                @Override
                public Object put(Object key, Object value) {
                    String header = ((String)key + " " + value).trim();
                    if (header.startsWith("[") && header.endsWith("]")) {
                        this.section = new Properties();
                        return result.put(header.substring(1, header.length() - 1), this.section);
                    }
                    return this.section.put(key, value);
                }

                @Override
                public boolean equals(Object o) {
                    return super.equals(o);
                }

                @Override
                public int hashCode() {
                    return super.hashCode();
                }
            }.load(reader);
            return this.result;
        }
    }
}

