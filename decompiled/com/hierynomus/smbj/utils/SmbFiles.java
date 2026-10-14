/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.utils;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.smbj.common.SmbPath;
import com.hierynomus.smbj.io.InputStreamByteChunkProvider;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumSet;

public class SmbFiles {
    public static int copy(java.io.File source2, DiskShare share, String destPath, boolean overwrite) throws IOException {
        int r;
        block12: {
            r = 0;
            if (source2 != null && source2.exists() && source2.canRead() && source2.isFile()) {
                try (FileInputStream is = new FileInputStream(source2);){
                    if (destPath == null || is == null) break block12;
                    try (File f = share.openFile(destPath, EnumSet.of(AccessMask.GENERIC_WRITE), EnumSet.of(FileAttributes.FILE_ATTRIBUTE_NORMAL), EnumSet.of(SMB2ShareAccess.FILE_SHARE_WRITE), overwrite ? SMB2CreateDisposition.FILE_OVERWRITE_IF : SMB2CreateDisposition.FILE_CREATE, EnumSet.noneOf(SMB2CreateOptions.class));){
                        r = f.write(new InputStreamByteChunkProvider(is));
                    }
                }
            }
        }
        return r;
    }

    public void mkdirs(DiskShare diskShare, String path) throws SMBApiException {
        SmbPath smbPath = new SmbPath(diskShare.getSmbPath(), path);
        this.mkdirs(diskShare, smbPath);
    }

    public void mkdirs(DiskShare diskShare, SmbPath path) throws SMBApiException {
        if (!diskShare.folderExists(path.getPath())) {
            this.mkdirs(diskShare, path.getParent());
            diskShare.mkdir(path.getPath());
        }
    }
}

