/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license;

import com.google.inject.Inject;
import com.incoresoft.database.LicenseRepository;
import com.incoresoft.license.License;
import com.incoresoft.license.LicenseImpl;
import com.incoresoft.license.OnLicenseListener;
import com.incoresoft.license.exceptions.LicenseActivationException;
import com.incoresoft.license.exceptions.LicenseDeactivationException;
import com.incoresoft.license.model.LicenseData;
import com.incoresoft.license.model.LicenseStatus;
import com.incoresoft.license.utils.LicenseValidationManager;
import com.incoresoft.middleware.http.exceptions.LicenseException;
import com.incoresoft.middleware.utils.TextUtils;
import com.license4j.ValidationStatus;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseManager {
    private static final Logger log = LoggerFactory.getLogger(LicenseManager.class);
    private final Set<OnLicenseListener> licenseListeners = Collections.newSetFromMap(new ConcurrentHashMap());
    private final LicenseRepository licenseRepository;
    private final LicenseValidationManager licenseValidationManager;

    private void triggerListeners() {
        for (OnLicenseListener licenseListener : this.licenseListeners) {
            try {
                licenseListener.onLicenseChanged();
            }
            catch (Exception e) {
                log.error("Error while running license change listener", e);
            }
        }
    }

    public void addOnLicenseChangeListener(OnLicenseListener listener) {
        this.licenseListeners.add(listener);
    }

    public void removeListener(OnLicenseListener listener) {
        this.licenseListeners.remove(listener);
    }

    @NotNull
    public List<License> getLicenses() {
        return this.licenseRepository.getAll();
    }

    public Optional<License> get(int id) {
        return this.licenseRepository.get(id);
    }

    public String getLicenseKey(License license) {
        return Optional.ofNullable(license.getLicenseData()).map(LicenseData::getKey).orElse(license.getLicenseKey());
    }

    public void reactivateLicenses() {
        if (!this.checkLicenseServer()) {
            return;
        }
        List<License> licenses = this.getLicenses().stream().filter(it -> it.getLicenseKey() != null).toList();
        ArrayList<CompletableFuture<Void>> futures = new ArrayList<CompletableFuture<Void>>();
        for (License license : licenses) {
            futures.add(CompletableFuture.runAsync(() -> {
                try {
                    this.activateWithKey(license.getLicenseKey());
                    log.info("License {} reactivated", (Object)license.getLicenseKey());
                }
                catch (Exception e) {
                    log.error("Error while reactivating license: {}", (Object)license.getLicenseKey());
                }
            }));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        this.triggerListeners();
    }

    public void activateWithKey(@NotNull String key) throws LicenseException, IllegalArgumentException {
        if (TextUtils.isBlank(key)) {
            throw new LicenseException("License key must not be empty");
        }
        LicenseStatus validationStatus = this.licenseValidationManager.checkLicenseKey(key);
        if (validationStatus != LicenseStatus.LICENSE_VALID) {
            throw new LicenseActivationException(validationStatus, ValidationStatus.LICENSE_INVALID);
        }
        LicenseImpl license = this.licenseValidationManager.fromKey(key);
        LicenseStatus status = license.getStatus();
        if (status == LicenseStatus.LICENSE_NOT_FOUND_ON_ACTIVATION_SERVER) {
            this.licenseRepository.remove(key);
            throw new LicenseActivationException(LicenseStatus.LICENSE_NOT_FOUND_ON_ACTIVATION_SERVER, ValidationStatus.LICENSE_INVALID);
        }
        if (status == LicenseStatus.LICENSE_EXPIRED && !this.licenseKeyAlreadyExists(key)) {
            throw new LicenseActivationException(LicenseStatus.LICENSE_EXPIRED, ValidationStatus.LICENSE_EXPIRED);
        }
        this.licenseRepository.add(license);
        this.triggerListeners();
    }

    public void activateWithFile(@NotNull String text) throws LicenseException {
        String licenseText = text;
        if (TextUtils.isBlank(licenseText)) {
            throw new LicenseException("license.file");
        }
        LicenseImpl license = new LicenseImpl(null, licenseText = licenseText.replaceAll("(\r|\n|\r\n| )", ""), this.licenseValidationManager);
        LicenseStatus licenseStatus = license.getStatus();
        if (licenseStatus != LicenseStatus.LICENSE_VALID && licenseStatus != LicenseStatus.ACTIVATION_COMPLETED) {
            throw new LicenseActivationException(licenseStatus, ValidationStatus.LICENSE_INVALID);
        }
        this.licenseRepository.add(license);
        this.triggerListeners();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deactivateLicense(int id) throws LicenseException {
        License license = this.get(id).orElseThrow(() -> new LicenseException(String.format("License with id %d does not exist.", id)));
        String licenseKey = this.getLicenseKey(license);
        try {
            LicenseStatus status = license.deactivateLicense();
            if (license.getStatus() != LicenseStatus.LICENSE_EXPIRED && status != LicenseStatus.DEACTIVATION_COMPLETED && status != LicenseStatus.ACTIVATION_NOT_FOUND_ON_SERVER) {
                throw new LicenseDeactivationException(status);
            }
        }
        finally {
            this.licenseRepository.remove(licenseKey);
            this.triggerListeners();
        }
    }

    public void updateLicense() {
        this.licenseRepository.getAll().forEach(License::updateLicense);
        this.triggerListeners();
    }

    @NotNull
    public String getActivationString(String key) throws LicenseException, IllegalArgumentException {
        if (TextUtils.isBlank(key)) {
            throw new LicenseException("License key must not be empty");
        }
        LicenseStatus status = this.licenseValidationManager.checkLicenseKey(key);
        if (status != LicenseStatus.LICENSE_VALID) {
            throw new LicenseActivationException(status, ValidationStatus.LICENSE_INVALID);
        }
        String manualActivationString = this.licenseValidationManager.getManualActivationString(key);
        if (TextUtils.isBlank(manualActivationString)) {
            throw new LicenseException("Unknown error occurred");
        }
        return manualActivationString.replaceAll("\n", "");
    }

    public boolean checkLicenseServer() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://activate.incoresoft.com/api/v1/activations/ping")).GET().build();
        try {
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode() == 204;
        }
        catch (IOException | InterruptedException e) {
            log.error("Error while checking license server", e);
            return false;
        }
    }

    public boolean licenseKeyAlreadyExists(String key) {
        return this.licenseRepository.getAll().stream().anyMatch(license -> Objects.equals(license.getLicenseKey(), key));
    }

    @Inject
    public LicenseManager(LicenseRepository licenseRepository, LicenseValidationManager licenseValidationManager) {
        this.licenseRepository = licenseRepository;
        this.licenseValidationManager = licenseValidationManager;
    }
}

