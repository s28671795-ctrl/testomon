/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.util.List;
import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.PropertyUserStore;
import org.eclipse.jetty.security.RolePrincipal;
import org.eclipse.jetty.security.UserPrincipal;
import org.eclipse.jetty.security.UserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashLoginService
extends AbstractLoginService {
    private static final Logger LOG = LoggerFactory.getLogger(HashLoginService.class);
    private String _config;
    private boolean hotReload = false;
    private UserStore _userStore;
    private boolean _userStoreAutoCreate = false;

    public HashLoginService() {
    }

    public HashLoginService(String name) {
        this.setName(name);
    }

    public HashLoginService(String name, String config) {
        this.setName(name);
        this.setConfig(config);
    }

    public String getConfig() {
        return this._config;
    }

    public void setConfig(String config) {
        this._config = config;
    }

    public boolean isHotReload() {
        return this.hotReload;
    }

    public void setHotReload(boolean enable) {
        if (this.isRunning()) {
            throw new IllegalStateException("Cannot set hot reload while user store is running");
        }
        this.hotReload = enable;
    }

    public void setUserStore(UserStore userStore) {
        this.updateBean(this._userStore, userStore);
        this._userStore = userStore;
    }

    @Override
    protected List<RolePrincipal> loadRoleInfo(UserPrincipal user) {
        return this._userStore.getRolePrincipals(user.getName());
    }

    @Override
    protected UserPrincipal loadUserInfo(String userName) {
        return this._userStore.getUserPrincipal(userName);
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        if (this._userStore == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("doStart: Starting new PropertyUserStore. PropertiesFile: {} hotReload: {}", (Object)this._config, (Object)this.hotReload);
            }
            PropertyUserStore propertyUserStore = new PropertyUserStore();
            propertyUserStore.setHotReload(this.hotReload);
            propertyUserStore.setConfig(this._config);
            this.setUserStore(propertyUserStore);
            this._userStoreAutoCreate = true;
        }
    }

    UserStore getUserStore() {
        return this._userStore;
    }

    boolean isUserStoreAutoCreate() {
        return this._userStoreAutoCreate;
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        if (this._userStoreAutoCreate) {
            this.setUserStore(null);
            this._userStoreAutoCreate = false;
        }
    }
}

