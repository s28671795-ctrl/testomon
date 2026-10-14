/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.model.DigitalSignature;
import net.lingala.zip4j.model.FileHeader;

public class CentralDirectory {
    private List<FileHeader> fileHeaders = new ArrayList<FileHeader>();
    private DigitalSignature digitalSignature = new DigitalSignature();

    public List<FileHeader> getFileHeaders() {
        return this.fileHeaders;
    }

    public void setFileHeaders(List<FileHeader> fileHeaders) {
        this.fileHeaders = fileHeaders;
    }

    public DigitalSignature getDigitalSignature() {
        return this.digitalSignature;
    }

    public void setDigitalSignature(DigitalSignature digitalSignature) {
        this.digitalSignature = digitalSignature;
    }
}

