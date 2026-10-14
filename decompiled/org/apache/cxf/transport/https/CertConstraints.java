/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.security.auth.x500.X500Principal;

public class CertConstraints {
    private final DNConstraints subjectDNConstraints;
    private final DNConstraints issuerDNConstraints;

    public CertConstraints(List<String> subjectConstraints, Combinator subjectConstraintsCombinator, List<String> issuerConstraints, Combinator issuerConstraintsCombinator) throws PatternSyntaxException {
        this.subjectDNConstraints = new DNConstraints(subjectConstraints, subjectConstraintsCombinator);
        this.issuerDNConstraints = new DNConstraints(issuerConstraints, issuerConstraintsCombinator);
    }

    public boolean matches(X509Certificate cert) {
        return this.subjectDNConstraints.matches(cert.getSubjectX500Principal()) && this.issuerDNConstraints.matches(cert.getIssuerX500Principal());
    }

    private static class DNConstraints {
        private final Combinator combinator;
        private final Collection<Pattern> dnPatterns = new ArrayList<Pattern>();

        DNConstraints(List<String> patterns, Combinator patternCombinator) throws PatternSyntaxException {
            if (patterns == null) {
                this.combinator = Combinator.ALL;
                return;
            }
            this.combinator = patternCombinator;
            for (String expression : patterns) {
                this.dnPatterns.add(Pattern.compile(expression));
            }
        }

        final boolean matches(X500Principal dn) {
            boolean atLeastOnePatternMatches = false;
            boolean atLeastOnePatternDoesNotMatch = false;
            for (Pattern dnPattern : this.dnPatterns) {
                Matcher matcher = dnPattern.matcher(dn.getName());
                if (matcher.matches()) {
                    atLeastOnePatternMatches = true;
                    if (this.combinator != Combinator.ANY) continue;
                    break;
                }
                atLeastOnePatternDoesNotMatch = true;
                if (this.combinator != Combinator.ALL) continue;
                break;
            }
            switch (this.combinator) {
                case ALL: {
                    return !atLeastOnePatternDoesNotMatch;
                }
                case ANY: {
                    return atLeastOnePatternMatches;
                }
            }
            throw new RuntimeException("LOGIC ERROR: Unreachable code");
        }
    }

    public static enum Combinator {
        ANY,
        ALL;

    }
}

