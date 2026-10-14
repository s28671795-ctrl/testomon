/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config.models;

import com.incoresoft.dto.config.LoadBalancerDTO;

public class LoadBalancerConfig {
    private boolean checkCpuUsage;
    private boolean checkGpuUsage;
    private boolean checkNvDec;
    private boolean checkServerOverloaded;
    private boolean checkMemory;

    public LoadBalancerConfig() {
        this.checkCpuUsage = true;
        this.checkGpuUsage = true;
        this.checkNvDec = true;
        this.checkServerOverloaded = true;
        this.checkMemory = true;
    }

    public LoadBalancerConfig(LoadBalancerDTO dto) {
        this.checkCpuUsage = dto.getCheckCpuUsage() == null || dto.getCheckCpuUsage() != false;
        this.checkGpuUsage = dto.getCheckGpuUsage() == null || dto.getCheckGpuUsage() != false;
        this.checkNvDec = dto.getCheckNvDec() == null || dto.getCheckNvDec() != false;
        this.checkServerOverloaded = dto.getCheckServerOverloaded() == null || dto.getCheckServerOverloaded() != false;
        this.checkMemory = dto.getCheckMemory() == null || dto.getCheckMemory() != false;
    }

    public boolean isCheckCpuUsage() {
        return this.checkCpuUsage;
    }

    public boolean isCheckGpuUsage() {
        return this.checkGpuUsage;
    }

    public boolean isCheckNvDec() {
        return this.checkNvDec;
    }

    public boolean isCheckServerOverloaded() {
        return this.checkServerOverloaded;
    }

    public boolean isCheckMemory() {
        return this.checkMemory;
    }

    public void setCheckCpuUsage(boolean checkCpuUsage) {
        this.checkCpuUsage = checkCpuUsage;
    }

    public void setCheckGpuUsage(boolean checkGpuUsage) {
        this.checkGpuUsage = checkGpuUsage;
    }

    public void setCheckNvDec(boolean checkNvDec) {
        this.checkNvDec = checkNvDec;
    }

    public void setCheckServerOverloaded(boolean checkServerOverloaded) {
        this.checkServerOverloaded = checkServerOverloaded;
    }

    public void setCheckMemory(boolean checkMemory) {
        this.checkMemory = checkMemory;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LoadBalancerConfig)) {
            return false;
        }
        LoadBalancerConfig other = (LoadBalancerConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isCheckCpuUsage() != other.isCheckCpuUsage()) {
            return false;
        }
        if (this.isCheckGpuUsage() != other.isCheckGpuUsage()) {
            return false;
        }
        if (this.isCheckNvDec() != other.isCheckNvDec()) {
            return false;
        }
        if (this.isCheckServerOverloaded() != other.isCheckServerOverloaded()) {
            return false;
        }
        return this.isCheckMemory() == other.isCheckMemory();
    }

    protected boolean canEqual(Object other) {
        return other instanceof LoadBalancerConfig;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isCheckCpuUsage() ? 79 : 97);
        result = result * 59 + (this.isCheckGpuUsage() ? 79 : 97);
        result = result * 59 + (this.isCheckNvDec() ? 79 : 97);
        result = result * 59 + (this.isCheckServerOverloaded() ? 79 : 97);
        result = result * 59 + (this.isCheckMemory() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "LoadBalancerConfig(checkCpuUsage=" + this.isCheckCpuUsage() + ", checkGpuUsage=" + this.isCheckGpuUsage() + ", checkNvDec=" + this.isCheckNvDec() + ", checkServerOverloaded=" + this.isCheckServerOverloaded() + ", checkMemory=" + this.isCheckMemory() + ")";
    }
}

