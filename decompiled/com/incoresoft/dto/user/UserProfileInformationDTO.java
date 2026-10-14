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

@Schema(name="UserProfileInformationDTO", description="User profile information structure")
public record UserProfileInformationDTO(@Schema(name="fullname", description="User fullname", implementation=String.class, example="User Name", requiredMode=Schema.RequiredMode.REQUIRED) @NotBlank @JsonProperty(value="fullname") @JSONField(name="fullname") String fullname, @Schema(name="lang", description="Language", implementation=String.class, example="en") @JsonProperty(value="lang") @JSONField(name="lang") @NotBlank String lang, @Schema(name="date_format", description="<div><p>Selected date format.</p><p>Valid values are:</p><ul><li>DD-MM-YYYY - 07-03-2022<li>D-M-YY - 7-3-22<li>MM-DD-YYYY - 03-07-2022<li>M-D-YY - 3-7-22<li>YYYY-MM-DD - 2022-03-07</ul></div>", implementation=String.class, example="DD-MM-YYYY", allowableValues={"DD-MM-YYYY", "D-M-YY", "MM-DD-YYYY", "M-D-YY", "YYYY-MM-DD"}) @JsonProperty(value="date_format") @JSONField(name="date_format") @NotNull UserDateFormat dateFormat, @Schema(name="time_format", description="<div><p>Selected time format.</p><p>Valid values are:</p><ul><li>HH:mm:ss - 23:15:49<li>h:mm:ss a - 11:15:49 PM</ul></div>", implementation=UserTimeFormat.class) @JsonProperty(value="time_format") @JSONField(name="time_format") @NotNull UserTimeFormat timeFormat, @Schema(name="temperature_units", description="Selected temperature units", implementation=String.class, example="celsius", allowableValues={"celsius", "fahrenheit"}) @JsonProperty(value="temperature_units") @JSONField(name="temperature_units") @NotNull UserTemperatureUnit temperatureUnits, @Schema(name="distance_units", description="<div><p>Selected distance units.</p><p>Valid values are:</p><ul><li>m - miles<li>km - kilometers</ul></div>", implementation=String.class, example="km", allowableValues={"m", "km"}) @JsonProperty(value="distance_units") @JSONField(name="distance_units") @NotNull UserDistanceUnit distanceUnits, @Schema(name="first_day_of_week", description="Selected first day of week", implementation=String.class, example="monday", allowableValues={"sunday", "monday"}) @JsonProperty(value="first_day_of_week") @JSONField(name="first_day_of_week") @NotNull UserFirstDayOfWeek firstDayOfWeek) {
    public UserProfileInformationDTO(String fullName, UserSettings settings) {
        this(fullName, settings.getLang(), settings.getDateFormat(), settings.getTimeFormat(), settings.getTemperatureUnits(), settings.getDistanceUnits(), settings.getFirstDayOfWeek());
    }
}

