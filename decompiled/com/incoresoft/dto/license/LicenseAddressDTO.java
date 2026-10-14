/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.license;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.license.model.LicenseData;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="LicenseAddressDTO", description="License address class structure")
public class LicenseAddressDTO {
    @JsonProperty(value="country")
    @JSONField(name="country")
    @Schema(name="country", description="Country", example="Ukraine", implementation=String.class)
    private String country;
    @JsonProperty(value="city")
    @JSONField(name="city")
    @Schema(name="city", description="City", example="Kyiv", implementation=String.class)
    private String city;
    @JsonProperty(value="street")
    @JSONField(name="street")
    @Schema(name="street", description="Street", example="Shevchenka", implementation=String.class)
    private String street;
    @JsonProperty(value="zip_code")
    @JSONField(name="zip_code")
    @Schema(name="zip_code", description="Zip code", example="01001", implementation=String.class)
    private String zipCode;

    public LicenseAddressDTO(LicenseData.Address address) {
        this.country = address.getCountry();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipCode = address.getZipCode();
    }

    public String getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    @JsonProperty(value="country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty(value="city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty(value="street")
    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty(value="zip_code")
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LicenseAddressDTO)) {
            return false;
        }
        LicenseAddressDTO other = (LicenseAddressDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$country = this.getCountry();
        String other$country = other.getCountry();
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) {
            return false;
        }
        String this$city = this.getCity();
        String other$city = other.getCity();
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) {
            return false;
        }
        String this$street = this.getStreet();
        String other$street = other.getStreet();
        if (this$street == null ? other$street != null : !this$street.equals(other$street)) {
            return false;
        }
        String this$zipCode = this.getZipCode();
        String other$zipCode = other.getZipCode();
        return !(this$zipCode == null ? other$zipCode != null : !this$zipCode.equals(other$zipCode));
    }

    protected boolean canEqual(Object other) {
        return other instanceof LicenseAddressDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $country = this.getCountry();
        result = result * 59 + ($country == null ? 43 : $country.hashCode());
        String $city = this.getCity();
        result = result * 59 + ($city == null ? 43 : $city.hashCode());
        String $street = this.getStreet();
        result = result * 59 + ($street == null ? 43 : $street.hashCode());
        String $zipCode = this.getZipCode();
        result = result * 59 + ($zipCode == null ? 43 : $zipCode.hashCode());
        return result;
    }

    public String toString() {
        return "LicenseAddressDTO(country=" + this.getCountry() + ", city=" + this.getCity() + ", street=" + this.getStreet() + ", zipCode=" + this.getZipCode() + ")";
    }

    public LicenseAddressDTO() {
    }

    public LicenseAddressDTO(String country, String city, String street, String zipCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}

