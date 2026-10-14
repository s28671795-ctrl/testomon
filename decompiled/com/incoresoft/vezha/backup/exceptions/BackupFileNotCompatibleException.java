/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.exceptions;

import com.incoresoft.vezha.backup.exceptions.BackupFileInvalidException;

public class BackupFileNotCompatibleException
extends BackupFileInvalidException {
    private final ResponseBody responseBody;

    public BackupFileNotCompatibleException(String currentVersion, String backupVersion) {
        this.responseBody = new ResponseBody(currentVersion, backupVersion);
    }

    public ResponseBody getResponseBody() {
        return this.responseBody;
    }

    private static class ResponseBody {
        private String currentVersion;
        private String backupVersion;

        public String getCurrentVersion() {
            return this.currentVersion;
        }

        public String getBackupVersion() {
            return this.backupVersion;
        }

        public void setCurrentVersion(String currentVersion) {
            this.currentVersion = currentVersion;
        }

        public void setBackupVersion(String backupVersion) {
            this.backupVersion = backupVersion;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ResponseBody)) {
                return false;
            }
            ResponseBody other = (ResponseBody)o;
            if (!other.canEqual(this)) {
                return false;
            }
            String this$currentVersion = this.getCurrentVersion();
            String other$currentVersion = other.getCurrentVersion();
            if (this$currentVersion == null ? other$currentVersion != null : !this$currentVersion.equals(other$currentVersion)) {
                return false;
            }
            String this$backupVersion = this.getBackupVersion();
            String other$backupVersion = other.getBackupVersion();
            return !(this$backupVersion == null ? other$backupVersion != null : !this$backupVersion.equals(other$backupVersion));
        }

        protected boolean canEqual(Object other) {
            return other instanceof ResponseBody;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            String $currentVersion = this.getCurrentVersion();
            result = result * 59 + ($currentVersion == null ? 43 : $currentVersion.hashCode());
            String $backupVersion = this.getBackupVersion();
            result = result * 59 + ($backupVersion == null ? 43 : $backupVersion.hashCode());
            return result;
        }

        public String toString() {
            return "BackupFileNotCompatibleException.ResponseBody(currentVersion=" + this.getCurrentVersion() + ", backupVersion=" + this.getBackupVersion() + ")";
        }

        public ResponseBody() {
        }

        public ResponseBody(String currentVersion, String backupVersion) {
            this.currentVersion = currentVersion;
            this.backupVersion = backupVersion;
        }
    }
}

