/*
 * Decompiled with CFR 0.152.
 */
package org.xmlsoap.schemas.ws._2005._04.discovery;

import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.xmlsoap.schemas.ws._2005._04.discovery.AppSequenceType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ByeType;
import org.xmlsoap.schemas.ws._2005._04.discovery.HelloType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ProbeMatchType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ProbeMatchesType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ProbeType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ResolveMatchType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ResolveMatchesType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ResolveType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ScopesType;
import org.xmlsoap.schemas.ws._2005._04.discovery.SecurityType;
import org.xmlsoap.schemas.ws._2005._04.discovery.SigType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Hello_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "Hello");
    private static final QName _Bye_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "Bye");
    private static final QName _Probe_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "Probe");
    private static final QName _ProbeMatches_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "ProbeMatches");
    private static final QName _Resolve_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "Resolve");
    private static final QName _ResolveMatches_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "ResolveMatches");
    private static final QName _Types_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "Types");
    private static final QName _Scopes_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "Scopes");
    private static final QName _XAddrs_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "XAddrs");
    private static final QName _MetadataVersion_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "MetadataVersion");
    private static final QName _SupportedMatchingRules_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "SupportedMatchingRules");
    private static final QName _Security_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "Security");
    private static final QName _Sig_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "Sig");
    private static final QName _AppSequence_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/04/discovery", "AppSequence");

    public HelloType createHelloType() {
        return new HelloType();
    }

    public ByeType createByeType() {
        return new ByeType();
    }

    public ProbeType createProbeType() {
        return new ProbeType();
    }

    public ProbeMatchesType createProbeMatchesType() {
        return new ProbeMatchesType();
    }

    public ResolveType createResolveType() {
        return new ResolveType();
    }

    public ResolveMatchesType createResolveMatchesType() {
        return new ResolveMatchesType();
    }

    public ScopesType createScopesType() {
        return new ScopesType();
    }

    public SecurityType createSecurityType() {
        return new SecurityType();
    }

    public SigType createSigType() {
        return new SigType();
    }

    public AppSequenceType createAppSequenceType() {
        return new AppSequenceType();
    }

    public ProbeMatchType createProbeMatchType() {
        return new ProbeMatchType();
    }

    public ResolveMatchType createResolveMatchType() {
        return new ResolveMatchType();
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="Hello")
    public JAXBElement<HelloType> createHello(HelloType value) {
        return new JAXBElement<HelloType>(_Hello_QNAME, HelloType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="Bye")
    public JAXBElement<ByeType> createBye(ByeType value) {
        return new JAXBElement<ByeType>(_Bye_QNAME, ByeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="Probe")
    public JAXBElement<ProbeType> createProbe(ProbeType value) {
        return new JAXBElement<ProbeType>(_Probe_QNAME, ProbeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="ProbeMatches")
    public JAXBElement<ProbeMatchesType> createProbeMatches(ProbeMatchesType value) {
        return new JAXBElement<ProbeMatchesType>(_ProbeMatches_QNAME, ProbeMatchesType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="Resolve")
    public JAXBElement<ResolveType> createResolve(ResolveType value) {
        return new JAXBElement<ResolveType>(_Resolve_QNAME, ResolveType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="ResolveMatches")
    public JAXBElement<ResolveMatchesType> createResolveMatches(ResolveMatchesType value) {
        return new JAXBElement<ResolveMatchesType>(_ResolveMatches_QNAME, ResolveMatchesType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="Types")
    public JAXBElement<List<QName>> createTypes(List<QName> value) {
        return new JAXBElement<List<QName>>(_Types_QNAME, List.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="Scopes")
    public JAXBElement<ScopesType> createScopes(ScopesType value) {
        return new JAXBElement<ScopesType>(_Scopes_QNAME, ScopesType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="XAddrs")
    public JAXBElement<List<String>> createXAddrs(List<String> value) {
        return new JAXBElement<List<String>>(_XAddrs_QNAME, List.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="MetadataVersion")
    public JAXBElement<Long> createMetadataVersion(Long value) {
        return new JAXBElement<Long>(_MetadataVersion_QNAME, Long.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="SupportedMatchingRules")
    public JAXBElement<List<String>> createSupportedMatchingRules(List<String> value) {
        return new JAXBElement<List<String>>(_SupportedMatchingRules_QNAME, List.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="Security")
    public JAXBElement<SecurityType> createSecurity(SecurityType value) {
        return new JAXBElement<SecurityType>(_Security_QNAME, SecurityType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="Sig")
    public JAXBElement<SigType> createSig(SigType value) {
        return new JAXBElement<SigType>(_Sig_QNAME, SigType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2005/04/discovery", name="AppSequence")
    public JAXBElement<AppSequenceType> createAppSequence(AppSequenceType value) {
        return new JAXBElement<AppSequenceType>(_AppSequence_QNAME, AppSequenceType.class, null, value);
    }
}

