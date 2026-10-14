/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.user.UserDateFormat;
import com.incoresoft.user.UserDistanceUnit;
import com.incoresoft.user.UserFirstDayOfWeek;
import com.incoresoft.user.UserSettings;
import com.incoresoft.user.UserTemperatureUnit;
import com.incoresoft.user.UserTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(name="DbUserSettingsDTO", description="User settings class structure")
public record DbUserSettingsDTO(@Schema(name="ldap_server", description="Ldap server", implementation=String.class, example="ldap://localhost:389") @JsonProperty(value="ldap_server") @JSONField(name="ldap_server") String ldapServer, @Schema(name="ldap_sid", description="Ldap sid", implementation=String.class, example="S-1-5-21-2127521184-1604012920-1887927527") @JsonProperty(value="ldap_sid") @JSONField(name="ldap_sid") String ldapSid, @Schema(name="ldap_base_dn", description="Ldap base dn", implementation=String.class, example="dc=example,dc=com") @JsonProperty(value="ldap_base_dn") @JSONField(name="ldap_base_dn") String ldapBaseDN, @Schema(name="lang", description="Language", implementation=String.class, example="en") @JsonProperty(value="lang") @JSONField(name="lang") String lang, @Schema(name="sounds_enabled", description="Sounds enabled", implementation=Boolean.class, example="true") @JsonProperty(value="sounds_enabled") @JSONField(name="sounds_enabled") boolean soundsEnabled, @Schema(name="date_format", description="Date format", implementation=String.class, example="dd/MM/yyyy") @JsonProperty(value="date_format") @JSONField(name="date_format") @NotBlank UserDateFormat dateFormat, @Schema(name="time_format", description="Time format", implementation=UserTimeFormat.class, example="24") @JsonProperty(value="time_format") @JSONField(name="time_format") @NotNull UserTimeFormat timeFormat, @Schema(name="temperature_units", description="Temperature units", implementation=String.class, example="C") @JsonProperty(value="temperature_units") @JSONField(name="temperature_units") @NotBlank UserTemperatureUnit temperatureUnits, @Schema(name="distance_units", description="Distance units", implementation=String.class, example="km") @JsonProperty(value="distance_units") @JSONField(name="distance_units") @NotBlank UserDistanceUnit distanceUnits, @Schema(name="first_day_of_week", description="First day of week", implementation=String.class, example="Monday") @JsonProperty(value="first_day_of_week") @JSONField(name="first_day_of_week") @NotBlank UserFirstDayOfWeek firstDayOfWeek) {
    public DbUserSettingsDTO(UserSettings settings) {
        this(settings.getLdapServer(), settings.getLdapSid(), settings.getLdapBaseDN(), settings.getLang(), settings.isSoundsEnabled(), settings.getDateFormat(), settings.getTimeFormat(), settings.getTemperatureUnits(), settings.getDistanceUnits(), settings.getFirstDayOfWeek());
    }
}

