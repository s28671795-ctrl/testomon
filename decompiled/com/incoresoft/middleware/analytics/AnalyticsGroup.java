/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.middleware.model.folders.Folder;

public class AnalyticsGroup
implements Folder,
Model {
    private Integer id;
    private String name;
    private String pluginName;
    private Integer parentId;
    private int clientId;

    @Override
    public Folder getParent() {
        return new NullFolder(this.parentId);
    }

    @Override
    public int getId() {
        return this.id == null ? 0 : this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsGroup)) {
            return false;
        }
        AnalyticsGroup other = (AnalyticsGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        Integer this$parentId = this.getParentId();
        Integer other$parentId = other.getParentId();
        if (this$parentId == null ? other$parentId != null : !((Object)this$parentId).equals(other$parentId)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$pluginName = this.getPluginName();
        String other$pluginName = other.getPluginName();
        return !(this$pluginName == null ? other$pluginName != null : !this$pluginName.equals(other$pluginName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsGroup;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getClientId();
        result = result * 59 + this.getId();
        Integer $parentId = this.getParentId();
        result = result * 59 + ($parentId == null ? 43 : ((Object)$parentId).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $pluginName = this.getPluginName();
        result = result * 59 + ($pluginName == null ? 43 : $pluginName.hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsGroup(id=" + this.getId() + ", name=" + this.getName() + ", pluginName=" + this.getPluginName() + ", parentId=" + this.getParentId() + ", clientId=" + this.getClientId() + ")";
    }

    public AnalyticsGroup(Integer id, String name, String pluginName, Integer parentId, int clientId) {
        this.id = id;
        this.name = name;
        this.pluginName = pluginName;
        this.parentId = parentId;
        this.clientId = clientId;
    }

    public AnalyticsGroup() {
    }

    private static class NullFolder
    implements Folder {
        private final int id;

        public NullFolder(int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return this.id;
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public Folder getParent() {
            return null;
        }

        @Override
        public int getClientId() {
            return 0;
        }
    }
}

