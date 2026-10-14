/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.credentials;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class MinioClientConfigProvider
extends EnvironmentProvider {
    private final String filename;
    private final String alias;
    private final ObjectMapper mapper;

    public MinioClientConfigProvider(@Nullable String filename, @Nullable String alias) {
        if (filename != null && filename.isEmpty()) {
            throw new IllegalArgumentException("Filename must not be empty");
        }
        if (alias != null && alias.isEmpty()) {
            throw new IllegalArgumentException("Alias must not be empty");
        }
        this.filename = filename;
        this.alias = alias;
        this.mapper = ((JsonMapper.Builder)((JsonMapper.Builder)JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)).build();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Credentials fetch() {
        String alias;
        String filename = this.filename;
        if (filename == null) {
            filename = this.getProperty("MINIO_SHARED_CREDENTIALS_FILE");
        }
        if (filename == null) {
            String mcDir = ".mc";
            if (System.getProperty("os.name").toLowerCase(Locale.US).contains("windows")) {
                mcDir = "mc";
            }
            filename = Paths.get(System.getProperty("user.home"), mcDir, "config.json").toString();
        }
        if ((alias = this.alias) == null) {
            alias = this.getProperty("MINIO_ALIAS");
        }
        if (alias == null) {
            alias = "s3";
        }
        try (FileInputStream is = new FileInputStream(filename);){
            McConfig config = this.mapper.readValue((Reader)new InputStreamReader((InputStream)is, StandardCharsets.UTF_8), McConfig.class);
            Map<String, String> values2 = config.get(alias);
            if (values2 == null) {
                throw new ProviderException("Alias " + alias + " does not exist in MinioClient configuration file");
            }
            String accessKey = values2.get("accessKey");
            String secretKey = values2.get("secretKey");
            if (accessKey == null) {
                throw new ProviderException("Access key does not exist in alias " + alias + " in MinioClient configuration file");
            }
            if (secretKey == null) {
                throw new ProviderException("Secret key does not exist in alias " + alias + " in MinioClient configuration file");
            }
            Credentials credentials = new Credentials(accessKey, secretKey, null, null);
            return credentials;
        }
        catch (IOException e) {
            throw new ProviderException("Unable to read MinioClient configuration file", e);
        }
    }

    @SuppressFBWarnings(value={"UwF", "UuF"}, justification="All the fields are written at the time of JSON unmarshalling.")
    public static class McConfig {
        private Map<String, Map<String, String>> hosts;

        public Map<String, String> get(String alias) {
            return this.hosts.get(alias);
        }
    }
}

