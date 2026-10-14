/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.util.Collections;
import java.util.List;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.CombinatorType;
import org.apache.cxf.configuration.security.DNConstraintsType;
import org.apache.cxf.transport.https.CertConstraints;

public final class CertConstraintsJaxBUtils {
    private CertConstraintsJaxBUtils() {
    }

    public static CertConstraints createCertConstraints(CertificateConstraintsType certConstraints) {
        List<String> subjectRegexps = CertConstraintsJaxBUtils.getSubjectConstraints(certConstraints);
        CertConstraints.Combinator subjectCombinator = CertConstraintsJaxBUtils.getSubjectConstraintsCombinator(certConstraints);
        List<String> issuerRegexps = CertConstraintsJaxBUtils.getIssuerConstraints(certConstraints);
        CertConstraints.Combinator issuerCombinator = CertConstraintsJaxBUtils.getIssuerConstraintsCombinator(certConstraints);
        return new CertConstraints(subjectRegexps, subjectCombinator, issuerRegexps, issuerCombinator);
    }

    public static List<String> getSubjectConstraints(CertificateConstraintsType certConstraints) {
        if (certConstraints != null && certConstraints.isSetSubjectDNConstraints()) {
            DNConstraintsType constraints = certConstraints.getSubjectDNConstraints();
            return constraints.getRegularExpression();
        }
        return Collections.emptyList();
    }

    public static List<String> getIssuerConstraints(CertificateConstraintsType certConstraints) {
        if (certConstraints != null && certConstraints.isSetIssuerDNConstraints()) {
            DNConstraintsType constraints = certConstraints.getIssuerDNConstraints();
            return constraints.getRegularExpression();
        }
        return Collections.emptyList();
    }

    public static CertConstraints.Combinator getSubjectConstraintsCombinator(CertificateConstraintsType certConstraints) {
        CombinatorType combinator;
        DNConstraintsType constraints;
        if (certConstraints != null && certConstraints.isSetSubjectDNConstraints() && (constraints = certConstraints.getSubjectDNConstraints()) != null && constraints.isSetCombinator() && (combinator = constraints.getCombinator()) == CombinatorType.ANY) {
            return CertConstraints.Combinator.ANY;
        }
        return CertConstraints.Combinator.ALL;
    }

    public static CertConstraints.Combinator getIssuerConstraintsCombinator(CertificateConstraintsType certConstraints) {
        CombinatorType combinator;
        DNConstraintsType constraints;
        if (certConstraints != null && certConstraints.isSetIssuerDNConstraints() && (constraints = certConstraints.getIssuerDNConstraints()) != null && constraints.isSetCombinator() && (combinator = constraints.getCombinator()) == CombinatorType.ANY) {
            return CertConstraints.Combinator.ANY;
        }
        return CertConstraints.Combinator.ALL;
    }
}

