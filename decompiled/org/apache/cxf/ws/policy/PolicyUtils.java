/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.Assertor;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.neethi.PolicyOperator;

public final class PolicyUtils {
    private static final String INDENT = "  ";

    private PolicyUtils() {
    }

    public static boolean isEmptyPolicy(Policy p) {
        return PolicyUtils.isEmptyPolicyOperator(p);
    }

    public static boolean isEmptyPolicyOperator(PolicyOperator p) {
        if (p.isEmpty()) {
            return true;
        }
        for (PolicyComponent component : p.getPolicyComponents()) {
            if (component instanceof PolicyOperator && PolicyUtils.isEmptyPolicyOperator((PolicyOperator)component)) continue;
            return false;
        }
        return true;
    }

    public static boolean contains(Collection<Assertion> assertions, Assertion candidate) {
        for (Assertion a : assertions) {
            if (!a.equal(candidate)) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(Collection<Assertion> assertions, Collection<Assertion> candidates) {
        if (null == candidates || candidates.isEmpty()) {
            return true;
        }
        for (Assertion c : candidates) {
            if (PolicyUtils.contains(assertions, c)) continue;
            return false;
        }
        return true;
    }

    public static void logPolicy(Logger log, Level level, String msg, PolicyComponent pc) {
        if (!log.isLoggable(level)) {
            return;
        }
        if (null == pc) {
            log.log(level, msg);
            return;
        }
        StringBuilder buf = new StringBuilder();
        buf.append(msg);
        PolicyUtils.nl(buf);
        PolicyUtils.printPolicyComponent(pc, buf, 0);
        log.log(level, buf.toString());
    }

    public static void printPolicyComponent(PolicyComponent pc) {
        StringBuilder buf = new StringBuilder();
        PolicyUtils.printPolicyComponent(pc, buf, 0);
        System.out.println(buf.toString());
    }

    public static void printPolicyComponent(PolicyComponent pc, StringBuilder buf, int level) {
        PolicyUtils.indent(buf, level);
        buf.append("type: ");
        buf.append(PolicyUtils.typeToString(pc.getType()));
        if (5 == pc.getType()) {
            buf.append(' ');
            buf.append(((Assertion)pc).getName());
            if (((Assertion)pc).isOptional()) {
                buf.append(" (optional)");
            }
            buf.append(" (");
            buf.append(pc);
            buf.append(')');
            PolicyUtils.nl(buf);
            if (pc instanceof PolicyContainingAssertion) {
                Policy nested = ((PolicyContainingAssertion)pc).getPolicy();
                PolicyUtils.printPolicyComponent(nested, buf, ++level);
                --level;
            }
        } else {
            ++level;
            List<PolicyComponent> children = CastUtils.cast(((PolicyOperator)pc).getPolicyComponents(), PolicyComponent.class);
            PolicyUtils.nl(buf);
            for (PolicyComponent child : children) {
                PolicyUtils.printPolicyComponent(child, buf, level);
            }
            --level;
        }
    }

    private static void indent(StringBuilder buf, int level) {
        for (int i = 0; i < level; ++i) {
            buf.append(INDENT);
        }
    }

    private static void nl(StringBuilder buf) {
        buf.append(SystemPropertyAction.getProperty("line.separator"));
    }

    private static String typeToString(short type) {
        switch (type) {
            case 5: {
                return "Assertion";
            }
            case 3: {
                return "All";
            }
            case 2: {
                return "ExactlyOne";
            }
            case 1: {
                return "Policy";
            }
            case 4: {
                return "PolicyReference";
            }
        }
        return "";
    }

    public static Assertor createAsserter(Object o) {
        if (o instanceof Assertor) {
            return (Assertor)o;
        }
        if (o instanceof org.apache.cxf.transport.Assertor) {
            return new WrappedAssertor((org.apache.cxf.transport.Assertor)o);
        }
        return null;
    }

    public static class WrappedAssertor
    implements Assertor {
        org.apache.cxf.transport.Assertor obj;

        public WrappedAssertor(org.apache.cxf.transport.Assertor o) {
            this.obj = o;
        }

        @Override
        public void assertMessage(Message message) {
            this.obj.assertMessage(message);
        }

        @Override
        public boolean canAssert(QName type) {
            return this.obj.canAssert(type);
        }

        public org.apache.cxf.transport.Assertor getWrappedAssertor() {
            return this.obj;
        }
    }
}

