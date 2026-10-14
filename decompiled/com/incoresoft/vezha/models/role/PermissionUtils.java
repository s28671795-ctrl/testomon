/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.role;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.vezha.models.role.MissedPermissionsValidationError;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PermissionUtils {
    public static Optional<MissedPermissionsValidationError> validate(Set<Permission> permissions) {
        Objects.requireNonNull(permissions);
        Map permissionMap = permissions.stream().collect(Collectors.toMap(Permission::getId, Function.identity()));
        for (Permission permission : permissions) {
            HashSet<Permission> missed = new HashSet<Permission>();
            for (String required : permission.getRequired()) {
                Optional.ofNullable((Permission)permissionMap.get(required)).filter(it -> !permissions.contains(it)).ifPresent(missed::add);
            }
            if (missed.isEmpty()) continue;
            return Optional.of(new MissedPermissionsValidationError(permission, missed));
        }
        return Optional.empty();
    }
}

