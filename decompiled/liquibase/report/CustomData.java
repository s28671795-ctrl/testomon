/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.Map;

public class CustomData {
    private String customDataFile;
    private Map<String, Object> fileContents;

    public String getCustomDataFile() {
        return this.customDataFile;
    }

    public Map<String, Object> getFileContents() {
        return this.fileContents;
    }

    public void setCustomDataFile(String customDataFile) {
        this.customDataFile = customDataFile;
    }

    public void setFileContents(Map<String, Object> fileContents) {
        this.fileContents = fileContents;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CustomData)) {
            return false;
        }
        CustomData other = (CustomData)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$customDataFile = this.getCustomDataFile();
        String other$customDataFile = other.getCustomDataFile();
        if (this$customDataFile == null ? other$customDataFile != null : !this$customDataFile.equals(other$customDataFile)) {
            return false;
        }
        Map<String, Object> this$fileContents = this.getFileContents();
        Map<String, Object> other$fileContents = other.getFileContents();
        return !(this$fileContents == null ? other$fileContents != null : !((Object)this$fileContents).equals(other$fileContents));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CustomData;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $customDataFile = this.getCustomDataFile();
        result = result * 59 + ($customDataFile == null ? 43 : $customDataFile.hashCode());
        Map<String, Object> $fileContents = this.getFileContents();
        result = result * 59 + ($fileContents == null ? 43 : ((Object)$fileContents).hashCode());
        return result;
    }

    public String toString() {
        return "CustomData(customDataFile=" + this.getCustomDataFile() + ", fileContents=" + this.getFileContents() + ")";
    }
}

