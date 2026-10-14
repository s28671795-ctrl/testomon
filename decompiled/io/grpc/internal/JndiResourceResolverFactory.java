/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import android.annotation.SuppressLint;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Verify;
import io.grpc.internal.DnsNameResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

final class JndiResourceResolverFactory
implements DnsNameResolver.ResourceResolverFactory {
    @Nullable
    private static final Throwable JNDI_UNAVAILABILITY_CAUSE = JndiResourceResolverFactory.initJndi();

    @Nullable
    private static Throwable initJndi() {
        try {
            Class.forName("javax.naming.directory.InitialDirContext");
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
        }
        catch (ClassNotFoundException e) {
            return e;
        }
        catch (RuntimeException e) {
            return e;
        }
        catch (Error e) {
            return e;
        }
        return null;
    }

    @Override
    @Nullable
    public DnsNameResolver.ResourceResolver newResourceResolver() {
        if (this.unavailabilityCause() != null) {
            return null;
        }
        return new JndiResourceResolver(new JndiRecordFetcher());
    }

    @Override
    @Nullable
    public Throwable unavailabilityCause() {
        return JNDI_UNAVAILABILITY_CAUSE;
    }

    @VisibleForTesting
    @IgnoreJRERequirement
    @SuppressLint(value={"all"})
    static final class JndiRecordFetcher
    implements RecordFetcher {
        JndiRecordFetcher() {
        }

        @Override
        public List<String> getAllRecords(String recordType, String name) throws NamingException {
            JndiRecordFetcher.checkAvailable();
            String[] rrType = new String[]{recordType};
            ArrayList<String> records = new ArrayList<String>();
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put("com.sun.jndi.ldap.connect.timeout", "5000");
            env.put("com.sun.jndi.ldap.read.timeout", "5000");
            InitialDirContext dirContext = new InitialDirContext(env);
            try {
                Attributes attrs = dirContext.getAttributes(name, rrType);
                NamingEnumeration<? extends Attribute> rrGroups = attrs.getAll();
                try {
                    while (rrGroups.hasMore()) {
                        Attribute rrEntry = rrGroups.next();
                        assert (Arrays.asList(rrType).contains(rrEntry.getID()));
                        NamingEnumeration<?> rrValues = rrEntry.getAll();
                        try {
                            while (rrValues.hasMore()) {
                                records.add(String.valueOf(rrValues.next()));
                            }
                        }
                        catch (NamingException ne) {
                            JndiRecordFetcher.closeThenThrow(rrValues, ne);
                        }
                        rrValues.close();
                    }
                }
                catch (NamingException ne) {
                    JndiRecordFetcher.closeThenThrow(rrGroups, ne);
                }
                rrGroups.close();
            }
            catch (NamingException ne) {
                JndiRecordFetcher.closeThenThrow(dirContext, ne);
            }
            dirContext.close();
            return records;
        }

        private static void closeThenThrow(NamingEnumeration<?> namingEnumeration, NamingException e) throws NamingException {
            try {
                namingEnumeration.close();
            }
            catch (NamingException namingException) {
                // empty catch block
            }
            throw e;
        }

        private static void closeThenThrow(DirContext ctx, NamingException e) throws NamingException {
            try {
                ctx.close();
            }
            catch (NamingException namingException) {
                // empty catch block
            }
            throw e;
        }

        private static void checkAvailable() {
            if (JNDI_UNAVAILABILITY_CAUSE != null) {
                throw new UnsupportedOperationException("JNDI is not currently available", JNDI_UNAVAILABILITY_CAUSE);
            }
        }
    }

    @VisibleForTesting
    static final class JndiResourceResolver
    implements DnsNameResolver.ResourceResolver {
        private static final Logger logger = Logger.getLogger(JndiResourceResolver.class.getName());
        private static final Pattern whitespace = Pattern.compile("\\s+");
        private final RecordFetcher recordFetcher;

        public JndiResourceResolver(RecordFetcher recordFetcher) {
            this.recordFetcher = recordFetcher;
        }

        @Override
        public List<String> resolveTxt(String serviceConfigHostname) throws NamingException {
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "About to query TXT records for {0}", new Object[]{serviceConfigHostname});
            }
            List<String> serviceConfigRawTxtRecords = this.recordFetcher.getAllRecords("TXT", "dns:///" + serviceConfigHostname);
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "Found {0} TXT records", new Object[]{serviceConfigRawTxtRecords.size()});
            }
            ArrayList<String> serviceConfigTxtRecords = new ArrayList<String>(serviceConfigRawTxtRecords.size());
            for (String serviceConfigRawTxtRecord : serviceConfigRawTxtRecords) {
                serviceConfigTxtRecords.add(JndiResourceResolver.unquote(serviceConfigRawTxtRecord));
            }
            return Collections.unmodifiableList(serviceConfigTxtRecords);
        }

        @Override
        public List<DnsNameResolver.SrvRecord> resolveSrv(String host2) throws Exception {
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "About to query SRV records for {0}", new Object[]{host2});
            }
            List<String> rawSrvRecords = this.recordFetcher.getAllRecords("SRV", "dns:///" + host2);
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "Found {0} SRV records", new Object[]{rawSrvRecords.size()});
            }
            ArrayList<DnsNameResolver.SrvRecord> srvRecords = new ArrayList<DnsNameResolver.SrvRecord>(rawSrvRecords.size());
            RuntimeException first = null;
            Level level = Level.WARNING;
            for (String rawSrv : rawSrvRecords) {
                try {
                    String[] parts = whitespace.split(rawSrv, 5);
                    Verify.verify(parts.length == 4, "Bad SRV Record: %s", (Object)rawSrv);
                    if (!parts[3].endsWith(".")) {
                        throw new RuntimeException("Returned SRV host does not end in period: " + parts[3]);
                    }
                    srvRecords.add(new DnsNameResolver.SrvRecord(parts[3], Integer.parseInt(parts[2])));
                }
                catch (RuntimeException e) {
                    logger.log(level, "Failed to construct SRV record " + rawSrv, e);
                    if (first != null) continue;
                    first = e;
                    level = Level.FINE;
                }
            }
            if (srvRecords.isEmpty() && first != null) {
                throw first;
            }
            return Collections.unmodifiableList(srvRecords);
        }

        @VisibleForTesting
        static String unquote(String txtRecord) {
            StringBuilder sb = new StringBuilder(txtRecord.length());
            boolean inquote = false;
            for (int i = 0; i < txtRecord.length(); ++i) {
                char c = txtRecord.charAt(i);
                if (!inquote) {
                    if (c == ' ') continue;
                    if (c == '\"') {
                        inquote = true;
                        continue;
                    }
                } else {
                    if (c == '\"') {
                        inquote = false;
                        continue;
                    }
                    if (c == '\\') {
                        c = txtRecord.charAt(++i);
                        assert (c == '\"' || c == '\\');
                    }
                }
                sb.append(c);
            }
            return sb.toString();
        }
    }

    @VisibleForTesting
    static interface RecordFetcher {
        public List<String> getAllRecords(String var1, String var2) throws NamingException;
    }
}

