/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config.models;

import com.incoresoft.dto.config.QDrantConfigDTO;

public final class QDrantConfig {
    private final String server;

    public QDrantConfig(QDrantConfigDTO dto) {
        this.server = dto.getServer();
    }

    public String getServer() {
        return this.server;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof QDrantConfig)) {
            return false;
        }
        QDrantConfig other = (QDrantConfig)o;
        String this$server = this.getServer();
        String other$server = other.getServer();
        return !(this$server == null ? other$server != null : !this$server.equals(other$server));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $server = this.getServer();
        result = result * 59 + ($server == null ? 43 : $server.hashCode());
        return result;
    }

    public String toString() {
        return "QDrantConfig(server=" + this.getServer() + ")";
    }

    public QDrantConfig(String server) {
        this.server = server;
    }
}

