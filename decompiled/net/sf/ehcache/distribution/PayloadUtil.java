/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.sf.ehcache.distribution.CachePeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class PayloadUtil {
    public static final int MTU = 1500;
    public static final String URL_DELIMITER = "|";
    static final String URL_DELIMITER_REGEXP = "\\|";
    private static final Logger LOG = LoggerFactory.getLogger(PayloadUtil.class.getName());

    private PayloadUtil() {
    }

    public static List<byte[]> createCompressedPayloadList(List<CachePeer> localCachePeers, int maximumPeersPerSend) {
        ArrayList<byte[]> rv = new ArrayList<byte[]>();
        int iters = (int)Math.ceil((double)localCachePeers.size() / (double)maximumPeersPerSend);
        for (int i = 0; i < iters; ++i) {
            int fromIndex = maximumPeersPerSend * i;
            int toIndex = Math.min(maximumPeersPerSend * (i + 1), localCachePeers.size());
            List<CachePeer> subList = localCachePeers.subList(fromIndex, toIndex);
            rv.addAll(PayloadUtil.createCompressedPayload(subList, 1500));
        }
        return rv;
    }

    private static List<byte[]> createCompressedPayload(List<CachePeer> list, int maxSizePerPayload) {
        ArrayList<byte[]> rv = new ArrayList<byte[]>();
        byte[] compressed = PayloadUtil.gzip(PayloadUtil.assembleUrlList(list));
        if (compressed.length <= maxSizePerPayload) {
            rv.add(compressed);
        } else {
            if (list.size() == 1) {
                String url2 = null;
                try {
                    url2 = list.get(0).getUrl();
                }
                catch (RemoteException e) {
                    LOG.error("This should never be thrown as it is called locally");
                }
                LOG.error("The replicated cache url is too long. Unless configured with a smaller name, heartbeat won't work for this cache. Compressed url size: " + compressed.length + " MTU: " + maxSizePerPayload + " URL: " + url2);
                return Collections.emptyList();
            }
            List<CachePeer> list1 = list.subList(0, list.size() / 2);
            List<CachePeer> list2 = list.subList(list.size() / 2, list.size());
            rv.addAll(PayloadUtil.createCompressedPayload(list1, maxSizePerPayload));
            rv.addAll(PayloadUtil.createCompressedPayload(list2, maxSizePerPayload));
        }
        return rv;
    }

    public static byte[] assembleUrlList(List localCachePeers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < localCachePeers.size(); ++i) {
            CachePeer cachePeer = (CachePeer)localCachePeers.get(i);
            String rmiUrl = null;
            try {
                rmiUrl = cachePeer.getUrl();
            }
            catch (RemoteException e) {
                LOG.error("This should never be thrown as it is called locally");
            }
            if (i != localCachePeers.size() - 1) {
                sb.append(rmiUrl).append(URL_DELIMITER);
                continue;
            }
            sb.append(rmiUrl);
        }
        LOG.debug("Cache peers for this CacheManager to be advertised: {}", (Object)sb);
        return sb.toString().getBytes();
    }

    public static byte[] gzip(byte[] ungzipped) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bytes);
            gzipOutputStream.write(ungzipped);
            gzipOutputStream.close();
        }
        catch (IOException e) {
            LOG.error("Could not gzip " + Arrays.toString(ungzipped));
        }
        return bytes.toByteArray();
    }

    public static byte[] ungzip(byte[] gzipped) {
        byte[] ungzipped = new byte[]{};
        try {
            GZIPInputStream inputStream2 = new GZIPInputStream(new ByteArrayInputStream(gzipped));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(gzipped.length);
            byte[] buffer = new byte[1500];
            int bytesRead = 0;
            while (bytesRead != -1) {
                bytesRead = inputStream2.read(buffer, 0, 1500);
                if (bytesRead == -1) continue;
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            ungzipped = byteArrayOutputStream.toByteArray();
            inputStream2.close();
            byteArrayOutputStream.close();
        }
        catch (IOException e) {
            LOG.error("Could not ungzip. Heartbeat will not be working. " + e.getMessage());
        }
        return ungzipped;
    }
}

