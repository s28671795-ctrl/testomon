/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DefaultDERBuffer;
import org.ldaptive.dn.Dn;
import org.ldaptive.dn.NameValue;
import org.ldaptive.dn.RDn;
import org.ldaptive.ssl.CertificateHostnameVerifier;
import org.ldaptive.ssl.HostnameVerifierAdapter;
import org.ldaptive.ssl.X509DnDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHostnameVerifier
implements HostnameVerifier,
CertificateHostnameVerifier {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HostnameVerifier verifier = new HostnameVerifierAdapter(this);

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return this.verifier.verify(hostname, session);
    }

    @Override
    public boolean verify(String hostname, X509Certificate cert) {
        boolean b = LdapUtils.isIPAddress(hostname) ? this.verifyIP(hostname, cert) : this.verifyDNS(hostname, cert);
        this.logger.debug("Verify is {} with hostname={} against cert={}", b, hostname, cert.getSubjectX500Principal());
        return b;
    }

    protected boolean verifyIP(String ip2, X509Certificate cert) {
        Object[] subjAltNames = this.getSubjectAltNames(cert, SubjectAltNameType.IP_ADDRESS);
        this.logger.trace("verifyIP using subjectAltNames={}", (Object)Arrays.toString(subjAltNames));
        for (Object name : subjAltNames) {
            if (!ip2.equalsIgnoreCase((String)name)) continue;
            this.logger.trace("verifyIP found hostname match: {}", name);
            return true;
        }
        return false;
    }

    protected boolean verifyDNS(String hostname, X509Certificate cert) {
        boolean verified = false;
        Object[] subjAltNames = this.getSubjectAltNames(cert, SubjectAltNameType.DNS_NAME);
        this.logger.trace("verifyDNS using subjectAltNames={}", (Object)Arrays.toString(subjAltNames));
        if (subjAltNames.length > 0) {
            for (Object name : subjAltNames) {
                if (!this.isMatch(hostname, (String)name)) continue;
                this.logger.trace("verifyDNS found hostname match: {}", name);
                verified = true;
                break;
            }
        } else {
            Object[] cns = this.getCNs(cert);
            this.logger.trace("verifyDNS using CN={}", (Object)Arrays.toString(cns));
            if (cns.length > 0 && this.isMatch(hostname, (String)cns[cns.length - 1])) {
                this.logger.trace("verifyDNS found hostname match: {}", cns[cns.length - 1]);
                verified = true;
            }
        }
        return verified;
    }

    private String[] getSubjectAltNames(X509Certificate cert, SubjectAltNameType type) {
        ArrayList<String> names = new ArrayList<String>();
        try {
            Collection<List<?>> subjAltNames = cert.getSubjectAlternativeNames();
            if (subjAltNames != null) {
                for (List<?> generalName : subjAltNames) {
                    Integer nameType = (Integer)generalName.get(0);
                    if (nameType.intValue() != type.ordinal()) continue;
                    names.add((String)generalName.get(1));
                }
            }
        }
        catch (CertificateParsingException e) {
            this.logger.warn("Error reading subject alt names from certificate", e);
        }
        return names.toArray(new String[0]);
    }

    private String[] getCNs(X509Certificate cert) {
        ArrayList<String> names = new ArrayList<String>();
        byte[] encodedDn = cert.getSubjectX500Principal().getEncoded();
        if (encodedDn != null && encodedDn.length > 0) {
            X509DnDecoder decoder = new X509DnDecoder();
            Dn dn = decoder.apply(new DefaultDERBuffer(encodedDn));
            for (RDn rdn : dn.getRDns()) {
                NameValue nameValue = rdn.getNameValue("2.5.4.3");
                if (nameValue == null) continue;
                names.add(nameValue.getStringValue());
            }
        }
        return names.toArray(new String[0]);
    }

    private boolean isMatch(String hostname, String certName) {
        boolean match;
        boolean isWildcard = certName.startsWith("*.") && certName.indexOf(46) < certName.lastIndexOf(46);
        this.logger.trace("matching for hostname={}, certName={}, isWildcard={}", hostname, certName, isWildcard);
        if (isWildcard) {
            String certNameDomain = certName.substring(certName.indexOf("."));
            int hostnameIdx = hostname.contains(".") ? hostname.indexOf(".") : hostname.length();
            String hostnameDomain = hostname.substring(hostnameIdx);
            match = certNameDomain.equalsIgnoreCase(hostnameDomain);
            this.logger.trace("match={} for {} == {}", match, certNameDomain, hostnameDomain);
        } else {
            match = certName.equalsIgnoreCase(hostname);
            this.logger.trace("match={} for {} == {}", match, certName, hostname);
        }
        return match;
    }

    private static enum SubjectAltNameType {
        OTHER_NAME,
        RFC822_NAME,
        DNS_NAME,
        X400_ADDRESS,
        DIRECTORY_NAME,
        EDI_PARTY_NAME,
        UNIFORM_RESOURCE_IDENTIFIER,
        IP_ADDRESS,
        REGISTERED_ID;

    }
}

