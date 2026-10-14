/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.wss4j.policy.model.AbstractBinding;

public final class PolicyUtils {
    private PolicyUtils() {
    }

    public static Collection<AssertionInfo> getAllAssertionsByLocalname(AssertionInfoMap aim, String localname) {
        Collection sp11Ais = (Collection)aim.get(new QName("http://schemas.xmlsoap.org/ws/2005/07/securitypolicy", localname));
        Collection sp12Ais = (Collection)aim.get(new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", localname));
        if (sp11Ais != null && !sp11Ais.isEmpty() || sp12Ais != null && !sp12Ais.isEmpty()) {
            HashSet<AssertionInfo> ais = new HashSet<AssertionInfo>();
            if (sp11Ais != null) {
                ais.addAll(sp11Ais);
            }
            if (sp12Ais != null) {
                ais.addAll(sp12Ais);
            }
            return ais;
        }
        return Collections.emptySet();
    }

    public static boolean assertPolicy(AssertionInfoMap aim, QName name) {
        Collection<AssertionInfo> ais = aim.getAssertionInfo(name);
        if (ais != null && !ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                ai.setAsserted(true);
            }
            return true;
        }
        return false;
    }

    public static boolean assertPolicy(AssertionInfoMap aim, String localname) {
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, localname);
        if (!ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                ai.setAsserted(true);
            }
            return true;
        }
        return false;
    }

    public static AssertionInfo getFirstAssertionByLocalname(AssertionInfoMap aim, String localname) {
        Collection sp11Ais = (Collection)aim.get(new QName("http://schemas.xmlsoap.org/ws/2005/07/securitypolicy", localname));
        if (sp11Ais != null && !sp11Ais.isEmpty()) {
            return (AssertionInfo)sp11Ais.iterator().next();
        }
        Collection sp12Ais = (Collection)aim.get(new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", localname));
        if (sp12Ais != null && !sp12Ais.isEmpty()) {
            return (AssertionInfo)sp12Ais.iterator().next();
        }
        return null;
    }

    public static boolean isThereAnAssertionByLocalname(AssertionInfoMap aim, String localname) {
        Collection sp11Ais = (Collection)aim.get(new QName("http://schemas.xmlsoap.org/ws/2005/07/securitypolicy", localname));
        Collection sp12Ais = (Collection)aim.get(new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", localname));
        return sp11Ais != null && !sp11Ais.isEmpty() || sp12Ais != null && !sp12Ais.isEmpty();
    }

    public static AbstractBinding getSecurityBinding(AssertionInfoMap aim) {
        AssertionInfo asymAis = PolicyUtils.getFirstAssertionByLocalname(aim, "AsymmetricBinding");
        if (asymAis != null) {
            asymAis.setAsserted(true);
            return (AbstractBinding)asymAis.getAssertion();
        }
        AssertionInfo symAis = PolicyUtils.getFirstAssertionByLocalname(aim, "SymmetricBinding");
        if (symAis != null) {
            symAis.setAsserted(true);
            return (AbstractBinding)symAis.getAssertion();
        }
        AssertionInfo transAis = PolicyUtils.getFirstAssertionByLocalname(aim, "TransportBinding");
        if (transAis != null) {
            transAis.setAsserted(true);
            return (AbstractBinding)transAis.getAssertion();
        }
        return null;
    }
}

