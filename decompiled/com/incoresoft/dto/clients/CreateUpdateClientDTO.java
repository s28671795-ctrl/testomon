/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.clients;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.clients.validations.ClientWithThisNameAlreadyExists;
import com.incoresoft.clients.validations.LanguageCodeInvalid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

@ClientWithThisNameAlreadyExists
@LanguageCodeInvalid
@Schema(name="CreateClientDTO", description="Client creation/update structure")
@JsonDeserialize(builder=CreateUpdateClientDTOBuilder.class)
public final class CreateUpdateClientDTO {
    @Schema(hidden=true)
    private Integer id;
    @JsonProperty(value="name")
    @JSONField(name="name")
    @Schema(name="name", description="Client name", implementation=String.class, example="client")
    @NotBlank
    private final String name;
    @JsonProperty(value="address")
    @JSONField(name="address")
    @Schema(name="address", description="Client address", implementation=String.class, example="21. Khreshchatyk st.")
    private final String address;
    @JsonProperty(value="city")
    @JSONField(name="city")
    @Schema(name="city", description="Client's city", implementation=String.class, example="Kyiv")
    private final String city;
    @JsonProperty(value="comment")
    @JSONField(name="comment")
    @Schema(name="comment", description="Short notes about client", implementation=String.class, example="Short notes about client")
    private final String comment;
    @JsonProperty(value="country")
    @JSONField(name="country")
    @Schema(name="country", description="Client's country", implementation=String.class, example="UA")
    private final String country;
    @JsonProperty(value="email")
    @JSONField(name="email")
    @Schema(name="email", description="Client's email", implementation=String.class, example="client.mail@test.com")
    private final String email;
    @JsonProperty(value="phone")
    @JSONField(name="phone")
    @Schema(name="phone", description="Client's phone", implementation=String.class, example="+380123456789")
    private final String phone;
    @JsonProperty(value="zip_code")
    @JSONField(name="zip_code")
    @Schema(name="zip_code", description="Client's zip code", implementation=String.class, example="01000")
    private final String zipCode;

    CreateUpdateClientDTO(Integer id, String name, String address, String city, String comment, String country, String email, String phone, String zipCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.comment = comment;
        this.country = country;
        this.email = email;
        this.phone = phone;
        this.zipCode = zipCode;
    }

    public static CreateUpdateClientDTOBuilder builder() {
        return new CreateUpdateClientDTOBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getComment() {
        return this.comment;
    }

    public String getCountry() {
        return this.country;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CreateUpdateClientDTO)) {
            return false;
        }
        CreateUpdateClientDTO other = (CreateUpdateClientDTO)o;
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$address = this.getAddress();
        String other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) {
            return false;
        }
        String this$city = this.getCity();
        String other$city = other.getCity();
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) {
            return false;
        }
        String this$comment = this.getComment();
        String other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) {
            return false;
        }
        String this$country = this.getCountry();
        String other$country = other.getCountry();
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) {
            return false;
        }
        String this$email = this.getEmail();
        String other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
            return false;
        }
        String this$phone = this.getPhone();
        String other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) {
            return false;
        }
        String this$zipCode = this.getZipCode();
        String other$zipCode = other.getZipCode();
        return !(this$zipCode == null ? other$zipCode != null : !this$zipCode.equals(other$zipCode));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $address = this.getAddress();
        result = result * 59 + ($address == null ? 43 : $address.hashCode());
        String $city = this.getCity();
        result = result * 59 + ($city == null ? 43 : $city.hashCode());
        String $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        String $country = this.getCountry();
        result = result * 59 + ($country == null ? 43 : $country.hashCode());
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $phone = this.getPhone();
        result = result * 59 + ($phone == null ? 43 : $phone.hashCode());
        String $zipCode = this.getZipCode();
        result = result * 59 + ($zipCode == null ? 43 : $zipCode.hashCode());
        return result;
    }

    public String toString() {
        return "CreateUpdateClientDTO(id=" + this.getId() + ", name=" + this.getName() + ", address=" + this.getAddress() + ", city=" + this.getCity() + ", comment=" + this.getComment() + ", country=" + this.getCountry() + ", email=" + this.getEmail() + ", phone=" + this.getPhone() + ", zipCode=" + this.getZipCode() + ")";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class CreateUpdateClientDTOBuilder {
        private Integer id;
        private String name;
        private String address;
        private String city;
        private String comment;
        private String country;
        private String email;
        private String phone;
        private String zipCode;

        CreateUpdateClientDTOBuilder() {
        }

        public CreateUpdateClientDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        @JsonProperty(value="name")
        public CreateUpdateClientDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty(value="address")
        public CreateUpdateClientDTOBuilder address(String address) {
            this.address = address;
            return this;
        }

        @JsonProperty(value="city")
        public CreateUpdateClientDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        @JsonProperty(value="comment")
        public CreateUpdateClientDTOBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        @JsonProperty(value="country")
        public CreateUpdateClientDTOBuilder country(String country) {
            this.country = country;
            return this;
        }

        @JsonProperty(value="email")
        public CreateUpdateClientDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        @JsonProperty(value="phone")
        public CreateUpdateClientDTOBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        @JsonProperty(value="zip_code")
        public CreateUpdateClientDTOBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public CreateUpdateClientDTO build() {
            return new CreateUpdateClientDTO(this.id, this.name, this.address, this.city, this.comment, this.country, this.email, this.phone, this.zipCode);
        }

        public String toString() {
            return "CreateUpdateClientDTO.CreateUpdateClientDTOBuilder(id=" + this.id + ", name=" + this.name + ", address=" + this.address + ", city=" + this.city + ", comment=" + this.comment + ", country=" + this.country + ", email=" + this.email + ", phone=" + this.phone + ", zipCode=" + this.zipCode + ")";
        }
    }

    public static final class Fields {
        public static final String id = "id";
        public static final String name = "name";
        public static final String address = "address";
        public static final String city = "city";
        public static final String comment = "comment";
        public static final String country = "country";
        public static final String email = "email";
        public static final String phone = "phone";
        public static final String zipCode = "zipCode";
    }
}

