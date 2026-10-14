/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.timezones;

import com.incoresoft.vezha.dto.timezones.TimezonesDTO;
import com.incoresoft.vezha.models.timezones.TimezoneInfo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class TimeZoneManager {
    private static final List<ZoneId> timezones;
    private static final Pattern TIMEZONE_WITH_REGION_AND_CITY;
    private static final Set<String> EXCLUDED_REGIONS;
    private static final Set<String> EXCLUDED_ZONE_IDS;

    public List<TimezonesDTO> getTimezones() {
        LocalDateTime now = LocalDateTime.now();
        HashMap<String, List> timezoneInfo = new HashMap<String, List>();
        for (ZoneId timezone : timezones) {
            String[] split;
            String timezoneId;
            if (EXCLUDED_ZONE_IDS.contains(timezone.getId()) || !TIMEZONE_WITH_REGION_AND_CITY.matcher(timezoneId = timezone.getId()).matches() || EXCLUDED_REGIONS.contains((split = timezoneId.split("/"))[0])) continue;
            ZoneOffset offset = now.atZone(timezone).getOffset();
            String city = split[1];
            timezoneInfo.computeIfAbsent(split[0], k -> new LinkedList()).add(new TimezoneInfo(timezoneId, city, offset));
        }
        LinkedHashMap<String, List<TimezoneInfo>> sortedTimezoneInfo = new LinkedHashMap<String, List<TimezoneInfo>>();
        for (String region : timezoneInfo.keySet().stream().sorted().toList()) {
            sortedTimezoneInfo.put(region, ((List)timezoneInfo.get(region)).stream().sorted(Comparator.comparing(TimezoneInfo::getZoneOffset).reversed()).toList());
        }
        return sortedTimezoneInfo.entrySet().stream().map(it -> new TimezonesDTO((String)it.getKey(), (Collection)it.getValue())).toList();
    }

    static {
        TIMEZONE_WITH_REGION_AND_CITY = Pattern.compile("[A-Za-z_]+/[A-Za-z_]+");
        EXCLUDED_REGIONS = Set.of("Canada", "Brazil", "Mexico", "Chile", "US", "Etc");
        EXCLUDED_ZONE_IDS = Set.of("Europe/Kiev");
        timezones = ZoneId.getAvailableZoneIds().stream().map(ZoneId::of).toList();
    }
}

