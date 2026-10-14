/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.clients;

import com.incoresoft.dto.clients.CreateUpdateClientDTO;

public class Client {
    public static final Client DEFAULT = new Client(0, "Default");
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String address;
    private String zipCode;
    private String email;
    private String phone;
    private String comment;

    public Client(CreateUpdateClientDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.country = dto.getCountry();
        this.city = dto.getCity();
        this.address = dto.getAddress();
        this.zipCode = dto.getZipCode();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.comment = dto.getComment();
    }

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
        this.country = "";
        this.city = "";
        this.address = "";
        this.zipCode = "";
        this.email = "";
        this.phone = "";
        this.comment = "";
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getAddress() {
        return this.address;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getComment() {
        return this.comment;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        Client other = (Client)o;
        if (!other.canEqual(this)) {
            return false;
        }
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
        String this$address = this.getAddress();
        String other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) {
            return false;
        }
        String this$zipCode = this.getZipCode();
        String other$zipCode = other.getZipCode();
        if (this$zipCode == null ? other$zipCode != null : !this$zipCode.equals(other$zipCode)) {
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
        String this$comment = this.getComment();
        String other$comment = other.getComment();
        return !(this$comment == null ? other$comment != null : !this$comment.equals(other$comment));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Client;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $country = this.getCountry();
        result = result * 59 + ($country == null ? 43 : $country.hashCode());
        String $city = this.getCity();
        result = result * 59 + ($city == null ? 43 : $city.hashCode());
        String $address = this.getAddress();
        result = result * 59 + ($address == null ? 43 : $address.hashCode());
        String $zipCode = this.getZipCode();
        result = result * 59 + ($zipCode == null ? 43 : $zipCode.hashCode());
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $phone = this.getPhone();
        result = result * 59 + ($phone == null ? 43 : $phone.hashCode());
        String $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        return result;
    }

    public String toString() {
        return "Client(id=" + this.getId() + ", name=" + this.getName() + ", country=" + this.getCountry() + ", city=" + this.getCity() + ", address=" + this.getAddress() + ", zipCode=" + this.getZipCode() + ", email=" + this.getEmail() + ", phone=" + this.getPhone() + ", comment=" + this.getComment() + ")";
    }

    public Client() {
    }
}

