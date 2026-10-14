/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.share;

import com.hierynomus.msdtyp.SecurityDescriptor;
import com.hierynomus.msdtyp.SecurityInformation;
import com.hierynomus.msfscc.fileinformation.FileAllInformation;
import com.hierynomus.msfscc.fileinformation.FileLinkInformation;
import com.hierynomus.msfscc.fileinformation.FileQueryableInformation;
import com.hierynomus.msfscc.fileinformation.FileRenameInformation;
import com.hierynomus.msfscc.fileinformation.FileSettableInformation;
import com.hierynomus.mssmb2.SMB2FileId;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.smbj.common.SmbPath;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.Open;
import java.util.EnumSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DiskEntry
extends Open<DiskShare> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    DiskEntry(SMB2FileId fileId, DiskShare share, SmbPath fileName) {
        super(fileId, fileName, share);
    }

    public void closeNoWait() {
        ((DiskShare)this.share).closeFileIdNoWait(this.fileId);
    }

    @Deprecated
    public String getFileName() {
        return this.name.toUncPath();
    }

    public String getUncPath() {
        return this.name.toUncPath();
    }

    public String getPath() {
        return this.name.getPath();
    }

    public DiskShare getDiskShare() {
        return (DiskShare)this.share;
    }

    public FileAllInformation getFileInformation() throws SMBApiException {
        return this.getFileInformation(FileAllInformation.class);
    }

    public <F extends FileQueryableInformation> F getFileInformation(Class<F> informationClass) throws SMBApiException {
        return ((DiskShare)this.share).getFileInformation(this.fileId, informationClass);
    }

    public <F extends FileSettableInformation> void setFileInformation(F information) {
        ((DiskShare)this.share).setFileInformation(this.fileId, information);
    }

    public SecurityDescriptor getSecurityInformation(Set<SecurityInformation> securityInfo) throws SMBApiException {
        return ((DiskShare)this.share).getSecurityInfo(this.fileId, securityInfo);
    }

    public void setSecurityInformation(SecurityDescriptor securityDescriptor) throws SMBApiException {
        EnumSet<SecurityInformation> securityInfo = EnumSet.noneOf(SecurityInformation.class);
        if (securityDescriptor.getOwnerSid() != null) {
            securityInfo.add(SecurityInformation.OWNER_SECURITY_INFORMATION);
        }
        if (securityDescriptor.getGroupSid() != null) {
            securityInfo.add(SecurityInformation.GROUP_SECURITY_INFORMATION);
        }
        if (securityDescriptor.getControl().contains(SecurityDescriptor.Control.DP)) {
            securityInfo.add(SecurityInformation.DACL_SECURITY_INFORMATION);
        }
        if (securityDescriptor.getControl().contains(SecurityDescriptor.Control.SP)) {
            securityInfo.add(SecurityInformation.SACL_SECURITY_INFORMATION);
        }
        ((DiskShare)this.share).setSecurityInfo(this.fileId, securityInfo, securityDescriptor);
    }

    public void setSecurityInformation(SecurityDescriptor securityDescriptor, Set<SecurityInformation> securityInfo) throws SMBApiException {
        ((DiskShare)this.share).setSecurityInfo(this.fileId, securityInfo, securityDescriptor);
    }

    public void rename(String newName) throws SMBApiException {
        this.rename(newName, false);
    }

    public void rename(String newName, boolean replaceIfExist) throws SMBApiException {
        this.rename(newName, replaceIfExist, 0L);
    }

    public void rename(String newName, boolean replaceIfExist, long rootDirectory) throws SMBApiException {
        FileRenameInformation renameInfo = new FileRenameInformation(replaceIfExist, rootDirectory, newName);
        this.setFileInformation(renameInfo);
    }

    public void createHardlink(String linkname) throws SMBApiException {
        this.createHardlink(linkname, false);
    }

    public void createHardlink(String linkname, boolean replaceIfExist) throws SMBApiException {
        FileLinkInformation linkInfo = new FileLinkInformation(replaceIfExist, linkname);
        this.setFileInformation(linkInfo);
    }

    public byte[] ioctl(int ctlCode, boolean isFsCtl, byte[] inData, int inOffset, int inLength) {
        return ((DiskShare)this.share).ioctl(this.fileId, ctlCode, isFsCtl, inData, inOffset, inLength);
    }

    public byte[] ioctl(int ctlCode, boolean isFsCtl, byte[] inData, int inOffset, int inLength, int maxOutputResponse) {
        return ((DiskShare)this.share).ioctl(this.fileId, ctlCode, isFsCtl, inData, inOffset, inLength, maxOutputResponse);
    }

    public int ioctl(int ctlCode, boolean isFsCtl, byte[] inData, int inOffset, int inLength, byte[] outData, int outOffset, int outLength) {
        return ((DiskShare)this.share).ioctl(this.fileId, ctlCode, isFsCtl, inData, inOffset, inLength, outData, outOffset, outLength);
    }

    public void flush() {
        ((DiskShare)this.share).flush(this.fileId);
    }

    public void deleteOnClose() {
        ((DiskShare)this.share).deleteOnClose(this.fileId);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        result = 31 * result + (this.share == null ? 0 : ((DiskShare)this.share).hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DiskEntry other = (DiskEntry)obj;
        if (this.name == null ? other.name != null : !this.name.equals(other.name)) {
            return false;
        }
        return !(this.share == null ? other.share != null : !((DiskShare)this.share).equals(other.share));
    }
}

