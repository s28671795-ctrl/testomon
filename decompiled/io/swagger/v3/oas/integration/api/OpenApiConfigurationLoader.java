/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration.api;

import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public interface OpenApiConfigurationLoader {
    public OpenAPIConfiguration load(String var1) throws IOException;

    public boolean exists(String var1);

    default public String readInputStreamToString(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream));){
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine).append("\n");
            }
        }
        return sb.toString();
    }
}

