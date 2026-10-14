/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https.httpclient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.transport.https.httpclient.DomainType;
import org.apache.cxf.transport.https.httpclient.InetAddressUtils;
import org.apache.cxf.transport.https.httpclient.PublicSuffixMatcher;
import org.apache.cxf.transport.https.httpclient.SubjectName;

public final class DefaultHostnameVerifier
implements HostnameVerifier {
    private static final Logger LOG = LogUtils.getL7dLogger(DefaultHostnameVerifier.class);
    private final PublicSuffixMatcher publicSuffixMatcher;

    public DefaultHostnameVerifier(PublicSuffixMatcher publicSuffixMatcher) {
        this.publicSuffixMatcher = publicSuffixMatcher;
    }

    public DefaultHostnameVerifier() {
        this(null);
    }

    @Override
    public boolean verify(String host2, SSLSession session) {
        try {
            Certificate[] certs = session.getPeerCertificates();
            X509Certificate x509 = (X509Certificate)certs[0];
            this.verify(host2, x509);
            return true;
        }
        catch (SSLException ex) {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.log(Level.FINE, ex.getMessage(), ex);
            }
            throw new RuntimeException("HostnameVerifier, socket reset for TTL");
        }
    }

    public void verify(String host2, X509Certificate cert) throws SSLException {
        HostNameType hostType = DefaultHostnameVerifier.determineHostFormat(host2);
        List<SubjectName> subjectAlts = DefaultHostnameVerifier.getSubjectAltNames(cert);
        if (subjectAlts != null && !subjectAlts.isEmpty()) {
            switch (hostType) {
                case IPv4: {
                    DefaultHostnameVerifier.matchIPAddress(host2, subjectAlts);
                    break;
                }
                case IPv6: {
                    DefaultHostnameVerifier.matchIPv6Address(host2, subjectAlts);
                    break;
                }
                default: {
                    DefaultHostnameVerifier.matchDNSName(host2, subjectAlts, this.publicSuffixMatcher);
                    break;
                }
            }
        } else {
            X500Principal subjectPrincipal = cert.getSubjectX500Principal();
            String cn = DefaultHostnameVerifier.extractCN(subjectPrincipal.getName("RFC2253"));
            if (cn == null) {
                throw new SSLException("Certificate subject for <" + host2 + "> doesn't contain a common name and does not have alternative names");
            }
            DefaultHostnameVerifier.matchCN(host2, cn, this.publicSuffixMatcher);
        }
    }

    static void matchIPAddress(String host2, List<SubjectName> subjectAlts) throws SSLException {
        for (int i = 0; i < subjectAlts.size(); ++i) {
            SubjectName subjectAlt = subjectAlts.get(i);
            if (subjectAlt.getType() != 7 || !host2.equals(subjectAlt.getValue())) continue;
            return;
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + host2 + "> doesn't match any of the subject alternative names: " + subjectAlts);
    }

    static void matchIPv6Address(String host2, List<SubjectName> subjectAlts) throws SSLException {
        String normalisedHost = DefaultHostnameVerifier.normaliseAddress(host2);
        for (int i = 0; i < subjectAlts.size(); ++i) {
            String normalizedSubjectAlt;
            SubjectName subjectAlt = subjectAlts.get(i);
            if (subjectAlt.getType() != 7 || !normalisedHost.equals(normalizedSubjectAlt = DefaultHostnameVerifier.normaliseAddress(subjectAlt.getValue()))) continue;
            return;
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + host2 + "> doesn't match any of the subject alternative names: " + subjectAlts);
    }

    static void matchDNSName(String host2, List<SubjectName> subjectAlts, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
        String normalizedHost = host2.toLowerCase(Locale.ROOT);
        for (int i = 0; i < subjectAlts.size(); ++i) {
            String normalizedSubjectAlt;
            SubjectName subjectAlt = subjectAlts.get(i);
            if (subjectAlt.getType() != 2 || !DefaultHostnameVerifier.matchIdentityStrict(normalizedHost, normalizedSubjectAlt = subjectAlt.getValue().toLowerCase(Locale.ROOT), publicSuffixMatcher)) continue;
            return;
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + host2 + "> doesn't match any of the subject alternative names: " + subjectAlts);
    }

    static void matchCN(String host2, String cn, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
        String normalizedCn;
        String normalizedHost = host2.toLowerCase(Locale.ROOT);
        if (!DefaultHostnameVerifier.matchIdentityStrict(normalizedHost, normalizedCn = cn.toLowerCase(Locale.ROOT), publicSuffixMatcher)) {
            throw new SSLPeerUnverifiedException("Certificate for <" + host2 + "> doesn't match common name of the certificate subject: " + cn);
        }
    }

    static boolean matchDomainRoot(String host2, String domainRoot) {
        if (domainRoot == null) {
            return false;
        }
        return host2.endsWith(domainRoot) && (host2.length() == domainRoot.length() || host2.charAt(host2.length() - domainRoot.length() - 1) == '.');
    }

    private static boolean matchIdentity(String host2, String identity, PublicSuffixMatcher publicSuffixMatcher, boolean strict) {
        if (publicSuffixMatcher != null && host2.contains(".") && !DefaultHostnameVerifier.matchDomainRoot(host2, publicSuffixMatcher.getDomainRoot(identity, DomainType.ICANN))) {
            return false;
        }
        int asteriskIdx = identity.indexOf(42);
        if (asteriskIdx != -1) {
            String remainder;
            String prefix = identity.substring(0, asteriskIdx);
            String suffix = identity.substring(asteriskIdx + 1);
            if (!prefix.isEmpty() && !host2.startsWith(prefix)) {
                return false;
            }
            if (!suffix.isEmpty() && !host2.endsWith(suffix)) {
                return false;
            }
            return !strict || !(remainder = host2.substring(prefix.length(), host2.length() - suffix.length())).contains(".");
        }
        return host2.equalsIgnoreCase(identity);
    }

    static boolean matchIdentity(String host2, String identity, PublicSuffixMatcher publicSuffixMatcher) {
        return DefaultHostnameVerifier.matchIdentity(host2, identity, publicSuffixMatcher, false);
    }

    static boolean matchIdentity(String host2, String identity) {
        return DefaultHostnameVerifier.matchIdentity(host2, identity, null, false);
    }

    static boolean matchIdentityStrict(String host2, String identity, PublicSuffixMatcher publicSuffixMatcher) {
        return DefaultHostnameVerifier.matchIdentity(host2, identity, publicSuffixMatcher, true);
    }

    static boolean matchIdentityStrict(String host2, String identity) {
        return DefaultHostnameVerifier.matchIdentity(host2, identity, null, true);
    }

    static String extractCN(String subjectPrincipal) throws SSLException {
        if (subjectPrincipal == null) {
            return null;
        }
        try {
            LdapName subjectDN = new LdapName(subjectPrincipal);
            List<Rdn> rdns = subjectDN.getRdns();
            for (int i = rdns.size() - 1; i >= 0; --i) {
                Rdn rds = rdns.get(i);
                Attributes attributes = rds.toAttributes();
                Attribute cn = attributes.get("cn");
                if (cn == null) continue;
                try {
                    Object value = cn.get();
                    if (value == null) continue;
                    return value.toString();
                }
                catch (NoSuchElementException | NamingException exception) {
                    // empty catch block
                }
            }
            return null;
        }
        catch (InvalidNameException e) {
            throw new SSLException(subjectPrincipal + " is not a valid X500 distinguished name");
        }
    }

    static HostNameType determineHostFormat(String host2) {
        if (InetAddressUtils.isIPv4Address(host2)) {
            return HostNameType.IPv4;
        }
        String s2 = host2;
        if (s2.startsWith("[") && s2.endsWith("]")) {
            s2 = host2.substring(1, host2.length() - 1);
        }
        if (InetAddressUtils.isIPv6Address(s2)) {
            return HostNameType.IPv6;
        }
        return HostNameType.DNS;
    }

    static List<SubjectName> getSubjectAltNames(X509Certificate cert) {
        try {
            Collection<List<?>> entries = cert.getSubjectAlternativeNames();
            if (entries == null) {
                return Collections.emptyList();
            }
            ArrayList<SubjectName> result = new ArrayList<SubjectName>();
            for (List<?> entry : entries) {
                Integer type;
                Integer n = type = entry.size() >= 2 ? (Integer)entry.get(0) : null;
                if (type != 2 && type != 7) continue;
                Object o = entry.get(1);
                if (o instanceof String) {
                    result.add(new SubjectName((String)o, type));
                    continue;
                }
                if (!(o instanceof byte[])) continue;
            }
            return result;
        }
        catch (CertificateParsingException ignore) {
            return Collections.emptyList();
        }
    }

    static String normaliseAddress(String hostname) {
        if (hostname == null) {
            return hostname;
        }
        try {
            InetAddress inetAddress = InetAddress.getByName(hostname);
            return inetAddress.getHostAddress();
        }
        catch (UnknownHostException unexpected) {
            return hostname;
        }
    }

    static enum HostNameType {
        IPv4(7),
        IPv6(7),
        DNS(2);

        final int subjectType;

        private HostNameType(int subjectType) {
            this.subjectType = subjectType;
        }
    }
}

