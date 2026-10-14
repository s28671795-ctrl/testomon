/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.license;

import com.google.inject.Inject;
import com.incoresoft.core.ProjectPropertiesReader;
import com.incoresoft.database.LicenseRepository;
import com.incoresoft.license.License;
import com.incoresoft.license.OnLicenseListener;
import com.incoresoft.license.licenseparams.ALPRLicenseParams;
import com.incoresoft.license.licenseparams.LicenseParamsContainer;
import com.incoresoft.license.model.LPRLicenseType;
import com.incoresoft.license.model.LicenseGrabStatus;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsType;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.state.AnalyticsStateListener;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.vezha.license.AnalyticUsageManager;
import com.incoresoft.vezha.license.FeatureKey;
import com.incoresoft.vezha.license.LicenseUsage;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class LicenseUsageManager
implements AnalyticsStateListener,
OnLicenseListener {
    public static final String LPR_LICENSE_TYPE_KEY = "ALPRAnalytics.type";
    private static final AnalyticsType universalType = () -> "universal";
    public static final Map<String, AnalyticsType> featureToType = Map.ofEntries(Map.entry("ALPRAnalytics", AnalyticsTypes.alpr), Map.entry("faceAnalytics", AnalyticsTypes.face), Map.entry("trafficAnalytics", AnalyticsTypes.traffic), Map.entry("trafficLightsAnalytics", AnalyticsTypes.traffic_lights), Map.entry("hardhatAnalytics", AnalyticsTypes.hardhat), Map.entry("crowdAnalytics", AnalyticsTypes.crowd), Map.entry("objectInZoneAnalytics", AnalyticsTypes.object_in_zone), Map.entry("gatesAnalytics", AnalyticsTypes.gates), Map.entry("motionAnalytics", AnalyticsTypes.motion), Map.entry("genderAgeAnalytics", AnalyticsTypes.gender_age), Map.entry("heatmapAnalytics", AnalyticsTypes.heatmap), Map.entry("queueAnalytics", AnalyticsTypes.people_queue), Map.entry("personReidAnalytics", AnalyticsTypes.person_reid), Map.entry("smokingAnalytics", AnalyticsTypes.smoking), Map.entry("smokeFireAnalytics", AnalyticsTypes.smoke_fire), Map.entry("railroadAnalytics", AnalyticsTypes.railroad), Map.entry("fallenPeopleAnalytics", AnalyticsTypes.fallen_people), Map.entry("parkingLotsAnalytics", AnalyticsTypes.parking_lots), Map.entry("smartVAAnalytics", AnalyticsTypes.smart_va), Map.entry("gunDetectorAnalytics", AnalyticsTypes.gun_detection), Map.entry("militaryAnalytics", AnalyticsTypes.military), Map.entry("operatorAIAnalytics", AnalyticsTypes.operator_ai), Map.entry("universal", universalType));
    private final Map<FeatureKey, AnalyticUsageManager> typeToManagers = new ConcurrentHashMap<FeatureKey, AnalyticUsageManager>();
    private final LicenseRepository repository;
    private final ProjectPropertiesReader projectPropertiesReader;
    private final StreamsManager streamsManager;

    @Inject
    public void init() {
        this.onLicenseChanged();
    }

    public LicenseGrabStatus checkGrabLicense(Analytics analytic) {
        return this.checkOrGetLicense(analytic, manager -> manager.checkGrabLicense(analytic.getId()));
    }

    public LicenseGrabStatus grabLicense(Analytics analytic) {
        return this.checkOrGetLicense(analytic, manager -> manager.grabLicense(analytic.getId()));
    }

    private LicenseGrabStatus checkOrGetLicense(Analytics analytic, Function<AnalyticUsageManager, LicenseGrabStatus> function) {
        LocalDate releaseDate = this.projectPropertiesReader.getReleaseDate();
        if (this.repository.getAll().stream().noneMatch(it -> releaseDate.isBefore(it.maintenanceExpireDate()))) {
            return LicenseGrabStatus.MAINTENANCE_BLOCKED;
        }
        if (this.repository.getAll().stream().noneMatch(it -> it.isValid() && it.isActivated())) {
            return LicenseGrabStatus.UNAVAILABLE;
        }
        boolean embedded = this.isEmbedded(analytic);
        LicenseGrabStatus status = analytic.getModule().getAnalyticsTypes().stream().filter(it -> this.typeToManagers.containsKey(new FeatureKey((AnalyticsType)it, embedded))).map(it -> this.typeToManagers.get(new FeatureKey((AnalyticsType)it, embedded))).map(function).filter(it -> it == LicenseGrabStatus.LICENSED).findFirst().orElse(LicenseGrabStatus.NOT_LICENSED);
        if (status != LicenseGrabStatus.LICENSED && this.typeToManagers.containsKey(new FeatureKey(universalType, embedded))) {
            status = function.apply(this.typeToManagers.get(new FeatureKey(universalType, embedded)));
        }
        return status;
    }

    private boolean isEmbedded(Analytics analytic) {
        return this.streamsManager.get(analytic.getStreamUUID()).getType() == StreamType.embedded;
    }

    public void releaseLicense(Analytics analytic) {
        boolean embedded = this.isEmbedded(analytic);
        AnalyticUsageManager universalManager = this.typeToManagers.get(new FeatureKey(universalType, embedded));
        if (universalManager != null && universalManager.licensed(analytic.getId())) {
            universalManager.releaseLicense(analytic.getId());
            return;
        }
        analytic.getModule().getAnalyticsTypes().stream().map(it -> this.typeToManagers.get(new FeatureKey((AnalyticsType)it, embedded))).filter(Objects::nonNull).forEach(m4 -> m4.releaseLicense(analytic.getId()));
    }

    public LicenseParamsContainer getLicenseParamsForAnalytics(Analytics analytics) {
        EnumSet<AnalyticsTypes> analyticsTypes = EnumSet.copyOf(analytics.getModule().getAnalyticsTypes());
        List<License> allLicenses = this.repository.getAll();
        LicenseParamsContainer result = new LicenseParamsContainer();
        if (analyticsTypes.contains(AnalyticsTypes.alpr)) {
            result.setAlpr(this.getAlprLicenseParams(allLicenses));
        }
        if (analyticsTypes.contains(AnalyticsTypes.traffic_lights)) {
            result.setAlpr(new ALPRLicenseParams(LPRLicenseType.FULL));
        }
        return result;
    }

    private ALPRLicenseParams getAlprLicenseParams(List<License> allLicenses) {
        LPRLicenseType licenseType = allLicenses.stream().map(it -> it.getFeatures() == null ? this.getEmptyFeatures() : it.getFeatures()).filter(it -> it.containsKey(LPR_LICENSE_TYPE_KEY)).map(it -> ((String)it.get(LPR_LICENSE_TYPE_KEY)).toUpperCase()).map(name -> {
            try {
                return LPRLicenseType.valueOf(name);
            }
            catch (IllegalArgumentException e) {
                return null;
            }
        }).filter(Objects::nonNull).min(Comparator.comparingInt(LPRLicenseType::getLevelValue)).orElse(LPRLicenseType.BASIC);
        return new ALPRLicenseParams(licenseType);
    }

    @Override
    public void onAnalyticsStarted(Analytics analytics) {
        this.grabLicense(analytics);
    }

    @Override
    public void onAnalyticsStopped(Analytics analytics) {
        this.releaseLicense(analytics);
    }

    @Override
    public void onAnalyticsPaused(Analytics analytics) {
        this.releaseLicense(analytics);
    }

    @Override
    public void onLicenseChanged() {
        LocalDate releaseDate = this.projectPropertiesReader.getReleaseDate();
        List<License> allWorkingLicenses = this.repository.getAll().stream().filter(it -> it.expireDate().isAfter(LocalDate.now())).filter(it -> it.maintenanceExpireDate().isAfter(releaseDate)).toList();
        HashMap<FeatureKey, Integer> totals = new HashMap<FeatureKey, Integer>();
        for (License license : allWorkingLicenses) {
            Map<String, String> customFeatures = license.getFeatures() == null ? this.getEmptyFeatures() : license.getFeatures();
            boolean embedded = Boolean.parseBoolean(customFeatures.getOrDefault("embedded", "false"));
            for (Map.Entry<String, AnalyticsType> entry : featureToType.entrySet()) {
                try {
                    int maxAnalyticsPerLicense = Integer.parseInt(customFeatures.get(entry.getKey()));
                    totals.merge(new FeatureKey(entry.getValue(), embedded), maxAnalyticsPerLicense, Integer::sum);
                }
                catch (NumberFormatException numberFormatException) {}
            }
        }
        for (Map.Entry entry : totals.entrySet()) {
            FeatureKey featureKey = (FeatureKey)entry.getKey();
            Integer total = (Integer)entry.getValue();
            this.typeToManagers.compute(featureKey, (k, existing) -> existing == null ? new AnalyticUsageManager(total) : existing.resize(total));
        }
        this.typeToManagers.keySet().retainAll(totals.keySet());
    }

    public List<LicenseUsage> getLicenseUsage() {
        return featureToType.entrySet().stream().flatMap(entry -> Stream.of(this.createLicenseUsage((String)entry.getKey(), (AnalyticsType)entry.getValue(), false), this.createLicenseUsage((String)entry.getKey(), (AnalyticsType)entry.getValue(), true))).filter(Objects::nonNull).toList();
    }

    private LicenseUsage createLicenseUsage(String key, AnalyticsType type, boolean isEmbedded) {
        AnalyticUsageManager manager = this.typeToManagers.get(new FeatureKey(type, isEmbedded));
        return manager != null ? new LicenseUsage(key, isEmbedded, manager.licensedAnalyticsCount()) : null;
    }

    private Map<String, String> getEmptyFeatures() {
        HashMap<String, String> features = new HashMap<String, String>();
        features.put("TRIAL", "TRUE");
        featureToType.keySet().forEach(key -> features.put((String)key, "0"));
        return features;
    }

    @Inject
    public LicenseUsageManager(LicenseRepository repository, ProjectPropertiesReader projectPropertiesReader, StreamsManager streamsManager) {
        this.repository = repository;
        this.projectPropertiesReader = projectPropertiesReader;
        this.streamsManager = streamsManager;
    }
}

