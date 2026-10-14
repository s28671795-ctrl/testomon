/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.user;

import com.incoresoft.dto.user.DbUserSettingsDTO;
import com.incoresoft.dto.user.UserProfileInformationDTO;
import com.incoresoft.dto.user.UserSettingsDTO;
import com.incoresoft.user.UserDateFormat;
import com.incoresoft.user.UserDistanceUnit;
import com.incoresoft.user.UserFirstDayOfWeek;
import com.incoresoft.user.UserTemperatureUnit;
import com.incoresoft.user.UserTimeFormat;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserSettings {
    @NotNull
    @NotBlank
    private String lang = "en";
    private String ldapServer;
    private String ldapSid;
    private String ldapBaseDN;
    private boolean soundsEnabled = true;
    private UserDateFormat dateFormat = UserDateFormat.DD_MM_YYYY;
    private UserTimeFormat timeFormat = UserTimeFormat.HH_MM_SS;
    private UserTemperatureUnit temperatureUnits = UserTemperatureUnit.CELSIUS;
    private UserDistanceUnit distanceUnits = UserDistanceUnit.KILOMETER;
    private UserFirstDayOfWeek firstDayOfWeek = UserFirstDayOfWeek.MONDAY;

    public UserSettings(@Nullable UserSettings original, @NotNull UserSettingsDTO userSettingsDTO) {
        this.lang = userSettingsDTO.lang();
        this.soundsEnabled = userSettingsDTO.soundsEnabled();
        this.dateFormat = userSettingsDTO.dateFormat();
        this.timeFormat = userSettingsDTO.timeFormat();
        this.temperatureUnits = userSettingsDTO.temperatureUnits();
        this.distanceUnits = userSettingsDTO.distanceUnits();
        this.firstDayOfWeek = userSettingsDTO.firstDayOfWeek();
        this.ldapServer = original != null ? original.getLdapServer() : null;
        this.ldapSid = original != null ? original.getLdapSid() : null;
        this.ldapBaseDN = original != null ? original.getLdapBaseDN() : null;
    }

    public UserSettings(@Nullable UserSettings original, @NotNull UserProfileInformationDTO profileInformationDTO) {
        this.lang = profileInformationDTO.lang();
        this.soundsEnabled = original != null && original.isSoundsEnabled();
        this.dateFormat = profileInformationDTO.dateFormat();
        this.timeFormat = profileInformationDTO.timeFormat();
        this.temperatureUnits = profileInformationDTO.temperatureUnits();
        this.distanceUnits = profileInformationDTO.distanceUnits();
        this.firstDayOfWeek = profileInformationDTO.firstDayOfWeek();
        this.ldapServer = original != null ? original.getLdapServer() : null;
        this.ldapSid = original != null ? original.getLdapSid() : null;
        this.ldapBaseDN = original != null ? original.getLdapBaseDN() : null;
    }

    public UserSettings(DbUserSettingsDTO userSettingsDTO) {
        this.lang = userSettingsDTO.lang();
        this.soundsEnabled = userSettingsDTO.soundsEnabled();
        this.dateFormat = Optional.ofNullable(userSettingsDTO.dateFormat()).orElse(UserDateFormat.DD_MM_YYYY);
        this.timeFormat = Optional.ofNullable(userSettingsDTO.timeFormat()).orElse(UserTimeFormat.HH_MM_SS);
        this.temperatureUnits = Optional.ofNullable(userSettingsDTO.temperatureUnits()).orElse(UserTemperatureUnit.CELSIUS);
        this.distanceUnits = Optional.ofNullable(userSettingsDTO.distanceUnits()).orElse(UserDistanceUnit.KILOMETER);
        this.firstDayOfWeek = Optional.ofNullable(userSettingsDTO.firstDayOfWeek()).orElse(UserFirstDayOfWeek.MONDAY);
        this.ldapServer = userSettingsDTO.ldapServer();
        this.ldapSid = userSettingsDTO.ldapSid();
        this.ldapBaseDN = userSettingsDTO.ldapBaseDN();
    }

    public void toggleSound() {
        this.soundsEnabled = !this.soundsEnabled;
    }

    public String getLang() {
        return this.lang;
    }

    public String getLdapServer() {
        return this.ldapServer;
    }

    public String getLdapSid() {
        return this.ldapSid;
    }

    public String getLdapBaseDN() {
        return this.ldapBaseDN;
    }

    public boolean isSoundsEnabled() {
        return this.soundsEnabled;
    }

    public UserDateFormat getDateFormat() {
        return this.dateFormat;
    }

    public UserTimeFormat getTimeFormat() {
        return this.timeFormat;
    }

    public UserTemperatureUnit getTemperatureUnits() {
        return this.temperatureUnits;
    }

    public UserDistanceUnit getDistanceUnits() {
        return this.distanceUnits;
    }

    public UserFirstDayOfWeek getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setLdapServer(String ldapServer) {
        this.ldapServer = ldapServer;
    }

    public void setLdapSid(String ldapSid) {
        this.ldapSid = ldapSid;
    }

    public void setLdapBaseDN(String ldapBaseDN) {
        this.ldapBaseDN = ldapBaseDN;
    }

    public void setSoundsEnabled(boolean soundsEnabled) {
        this.soundsEnabled = soundsEnabled;
    }

    public void setDateFormat(UserDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setTimeFormat(UserTimeFormat timeFormat) {
        this.timeFormat = timeFormat;
    }

    public void setTemperatureUnits(UserTemperatureUnit temperatureUnits) {
        this.temperatureUnits = temperatureUnits;
    }

    public void setDistanceUnits(UserDistanceUnit distanceUnits) {
        this.distanceUnits = distanceUnits;
    }

    public void setFirstDayOfWeek(UserFirstDayOfWeek firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public UserSettings() {
    }
}

