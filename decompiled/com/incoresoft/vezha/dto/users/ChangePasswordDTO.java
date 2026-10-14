/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.users;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incoresoft.vezha.models.users.validations.password.CurrentPasswordIsWrong;
import com.incoresoft.vezha.models.users.validations.password.PasswordTooShort;
import com.incoresoft.vezha.models.users.validations.password.PasswordValidatable;
import com.incoresoft.vezha.models.users.validations.password.PasswordsDoNotMatch;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

@CurrentPasswordIsWrong
@PasswordTooShort
@PasswordsDoNotMatch
public record ChangePasswordDTO(@Schema(hidden=true) @JsonIgnore Integer id, @JSONField(name="current_password") @Schema(name="current_password", description="Current user password", implementation=String.class, example="password", requiredMode=Schema.RequiredMode.REQUIRED) String currentPassword, @JSONField(name="new_password") @Schema(name="new_password", description="New user password", implementation=String.class, example="password", requiredMode=Schema.RequiredMode.REQUIRED) @NotBlank String password, @JSONField(name="confirmation_password") @Schema(name="confirmation_password", description="User confirmation password", implementation=String.class, example="password", requiredMode=Schema.RequiredMode.REQUIRED) @NotBlank String confirmationPassword) implements PasswordValidatable
{
    @Override
    public Integer getId() {
        return this.id();
    }

    @Override
    public String getPassword() {
        return this.password();
    }

    @Override
    public String getConfirmationPassword() {
        return this.confirmationPassword();
    }

    public ChangePasswordDTO withId(Integer id) {
        return this.id == id ? this : new ChangePasswordDTO(id, this.currentPassword, this.password, this.confirmationPassword);
    }

    public static final class Fields {
        public static final String id = "id";
        public static final String currentPassword = "currentPassword";
        public static final String password = "password";
        public static final String confirmationPassword = "confirmationPassword";
    }
}

