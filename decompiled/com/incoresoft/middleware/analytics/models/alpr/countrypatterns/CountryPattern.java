/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.alpr.countrypatterns;

import com.incoresoft.middleware.analytics.models.alpr.countrypatterns.Country;

public final class CountryPattern {
    private final Country country;
    private final String state;
    private final String pattern;

    public CountryPattern(Country country, String state, String pattern) {
        this.country = country;
        this.state = state;
        this.pattern = pattern;
    }

    public Country getCountry() {
        return this.country;
    }

    public String getState() {
        return this.state;
    }

    public String getPattern() {
        return this.pattern;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CountryPattern)) {
            return false;
        }
        CountryPattern other = (CountryPattern)o;
        Country this$country = this.getCountry();
        Country other$country = other.getCountry();
        if (this$country == null ? other$country != null : !((Object)this$country).equals(other$country)) {
            return false;
        }
        String this$state = this.getState();
        String other$state = other.getState();
        if (this$state == null ? other$state != null : !this$state.equals(other$state)) {
            return false;
        }
        String this$pattern = this.getPattern();
        String other$pattern = other.getPattern();
        return !(this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Country $country = this.getCountry();
        result = result * 59 + ($country == null ? 43 : ((Object)$country).hashCode());
        String $state = this.getState();
        result = result * 59 + ($state == null ? 43 : $state.hashCode());
        String $pattern = this.getPattern();
        result = result * 59 + ($pattern == null ? 43 : $pattern.hashCode());
        return result;
    }

    public String toString() {
        return "CountryPattern(country=" + String.valueOf(this.getCountry()) + ", state=" + this.getState() + ", pattern=" + this.getPattern() + ")";
    }
}

