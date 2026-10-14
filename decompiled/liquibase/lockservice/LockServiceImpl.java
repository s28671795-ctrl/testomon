/*
 * Decompiled with CFR 0.152.
 */
package liquibase.lockservice;

import liquibase.lockservice.StandardLockService;

public class LockServiceImpl
extends StandardLockService {
    @Override
    public int getPriority() {
        return super.getPriority() - 1;
    }
}

