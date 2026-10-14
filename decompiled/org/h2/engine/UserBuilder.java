/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.engine.Database;
import org.h2.engine.User;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.util.MathUtils;

public class UserBuilder {
    public static User buildUser(AuthenticationInfo authenticationInfo, Database database, boolean bl) {
        User user = new User(database, bl ? database.allocateObjectId() : -1, authenticationInfo.getFullyQualifiedName(), false);
        user.setUserPasswordHash(authenticationInfo.getRealm() == null ? authenticationInfo.getConnectionInfo().getUserPasswordHash() : MathUtils.secureRandomBytes(64));
        user.setTemporary(!bl);
        return user;
    }
}

