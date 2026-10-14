/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.database.LicenseDatabaseRepository;
import com.incoresoft.dto.license.SavedLicenseDTO;
import com.incoresoft.license.License;
import com.incoresoft.license.LicenseImpl;
import com.incoresoft.license.LicenseProperties;
import com.incoresoft.license.model.LicenseData;
import com.incoresoft.license.model.LicenseStatus;
import com.incoresoft.license.utils.LicenseValidationManager;
import com.incoresoft.license.utils.PropertyFileLicenseReader;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.utils.Constants;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseRepository {
    private static final Logger log = LoggerFactory.getLogger(LicenseRepository.class);
    private final Map<String, License> licenses = new ConcurrentHashMap<String, License>();
    private final LicenseValidationManager licenseValidationManager;
    private final LicenseProperties licenseProperties;
    private final LicenseDatabaseRepository licenseDatabaseRepository;

    @Inject
    public synchronized void init() {
        List<License> licensesFromFile = this.loadLicensesFromFile();
        List<License> licensesFromDb = this.licenseDatabaseRepository.getAll().entrySet().stream().map(it -> new SavedLicenseDTO((String)it.getKey(), (String)it.getValue())).map(this::readLicense).toList();
        if (!licensesFromDb.isEmpty()) {
            for (License license : licensesFromDb) {
                this.licenses.put(license.getLicenseKey(), license);
            }
        } else if (!licensesFromFile.isEmpty()) {
            for (License license : licensesFromFile) {
                this.add(license);
            }
        } else {
            try {
                LicenseImpl trialLicense = this.licenseValidationManager.fromKey(this.licenseProperties.getTrialKey());
                if (trialLicense.getStatus() == LicenseStatus.LICENSE_EXPIRED) {
                    log.info("Trial license is expired");
                }
                this.add(trialLicense);
            }
            catch (Exception e) {
                log.info("Trial license activation error!!!");
            }
        }
    }

    public Optional<License> get(int id) {
        return this.licenses.values().stream().filter(it -> it.getLicenseData().getId() == (long)id).findAny();
    }

    @NotNull
    public List<License> getAll() {
        return List.copyOf(this.licenses.values());
    }

    public synchronized void add(@NotNull License license) {
        if (this.licenses.size() == 1 && this.licenses.containsKey(this.licenseProperties.getTrialKey())) {
            this.remove(this.licenseProperties.getTrialKey());
        }
        String licenseKey = Optional.of(license).map(License::getLicenseData).map(LicenseData::getKey).orElse(license.getLicenseKey());
        this.licenses.put(Objects.requireNonNull(licenseKey), Objects.requireNonNull(license));
        this.licenseDatabaseRepository.store(licenseKey, license.getLicenseText());
    }

    public synchronized void reload() {
        this.licenses.clear();
        this.init();
    }

    public synchronized void remove(@NotNull String key) {
        if (this.licenses.remove(key) != null) {
            this.licenseDatabaseRepository.delete(key);
        }
    }

    private List<License> loadLicensesFromFile() {
        if (Files.exists(Constants.LICENSE_JSON_FILE, new LinkOption[0])) {
            return this.loadLicensesFromJsonFile();
        }
        if (Files.exists(Constants.LICENSE_PROPERTIES_FILE, new LinkOption[0])) {
            return Collections.singletonList(this.loadLicenseFromPropertiesFile(Constants.LICENSE_PROPERTIES_FILE));
        }
        return Collections.emptyList();
    }

    @Nullable
    private License loadLicenseFromPropertiesFile(@NotNull Path propertiesFile) {
        Map<String, String> map = PropertyFileLicenseReader.read(propertiesFile);
        if (map.get("activated_license_text") != null && map.get("license_key") != null) {
            return new LicenseImpl(map.get("license_key"), map.get("activated_license_text"), this.licenseValidationManager);
        }
        if (map.get("activated_license_text") != null) {
            return this.licenseValidationManager.fromText(map.get("activated_license_text"));
        }
        if (map.get("license_key") != null) {
            return this.licenseValidationManager.fromKey(map.get("license_key"));
        }
        return null;
    }

    @NotNull
    private List<License> loadLicensesFromJsonFile() {
        try {
            return Serialization.readAsList(Files.readString(Constants.LICENSE_JSON_FILE), SavedLicenseDTO.class).stream().map(this::readLicense).filter(Objects::nonNull).toList();
        }
        catch (Exception e) {
            log.error("Error while reading licenses from json file", e);
            return Collections.emptyList();
        }
    }

    @Nullable
    private License readLicense(@NotNull SavedLicenseDTO json) {
        if (json.getLicenseKey() != null && json.getActivatedLicenseText() != null) {
            return new LicenseImpl(json.getLicenseKey(), json.getActivatedLicenseText(), this.licenseValidationManager);
        }
        if (json.getActivatedLicenseText() != null) {
            return this.licenseValidationManager.fromText(json.getActivatedLicenseText());
        }
        if (json.getLicenseKey() != null) {
            return this.licenseValidationManager.fromKey(json.getLicenseKey());
        }
        return null;
    }

    @Inject
    public LicenseRepository(LicenseValidationManager licenseValidationManager, LicenseProperties licenseProperties, LicenseDatabaseRepository licenseDatabaseRepository) {
        this.licenseValidationManager = licenseValidationManager;
        this.licenseProperties = licenseProperties;
        this.licenseDatabaseRepository = licenseDatabaseRepository;
    }
}

