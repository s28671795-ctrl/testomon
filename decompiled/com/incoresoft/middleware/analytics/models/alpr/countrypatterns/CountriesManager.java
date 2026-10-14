/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.alpr.countrypatterns;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.models.alpr.countrypatterns.Country;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CountriesManager {
    private static final String COUNTRIES_FILE = "countries.csv";
    private final Map<String, Country> countries = new ConcurrentHashMap<String, Country>();

    @Inject
    public CountriesManager() {
        try (InputStream inputStream2 = this.getClass().getClassLoader().getResourceAsStream(COUNTRIES_FILE);){
            try (CSVParser csvParser = new CSVParser(new BufferedReader(new InputStreamReader(inputStream2, StandardCharsets.UTF_8)), CSVFormat.Builder.create(CSVFormat.EXCEL).setDelimiter(";").build());){
                LinkedHashMap<String, Country> result = new LinkedHashMap<String, Country>();
                for (CSVRecord record : csvParser) {
                    Country country = new Country(record.get(0), record.get(1));
                    result.put(country.getCode(), country);
                }
                this.countries.putAll(Collections.unmodifiableMap(result));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Country get(String code) {
        return this.countries.get(code);
    }

    public List<Country> getAll() {
        return List.copyOf(this.countries.values());
    }
}

