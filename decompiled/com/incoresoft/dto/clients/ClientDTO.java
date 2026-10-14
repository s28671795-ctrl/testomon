/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.clients;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.clients.Client;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ClientDTO", description="Client class structure")
public final class ClientDTO {
    @JsonProperty(value="id")
    @JSONField(name="id")
    @Schema(name="id", description="Client Id", implementation=Integer.class, example="1")
    private final Integer id;
    @JsonProperty(value="name")
    @JSONField(name="name")
    @Schema(name="name", description="Client name", implementation=String.class, example="client")
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

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress();
        this.city = client.getCity();
        this.comment = client.getComment();
        this.country = client.getCountry();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.zipCode = client.getZipCode();
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
        if (!(o instanceof ClientDTO)) {
            return false;
        }
        ClientDTO other = (ClientDTO)o;
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
        return "ClientDTO(id=" + this.getId() + ", name=" + this.getName() + ", address=" + this.getAddress() + ", city=" + this.getCity() + ", comment=" + this.getComment() + ", country=" + this.getCountry() + ", email=" + this.getEmail() + ", phone=" + this.getPhone() + ", zipCode=" + this.getZipCode() + ")";
    }

    public ClientDTO(Integer id, String name, String address, String city, String comment, String country, String email, String phone, String zipCode) {
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
}

