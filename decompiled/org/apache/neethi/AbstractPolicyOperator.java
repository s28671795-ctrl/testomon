/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyOperator;
import org.apache.neethi.PolicyReference;
import org.apache.neethi.PolicyRegistry;
import org.apache.neethi.util.PolicyComparator;

public abstract class AbstractPolicyOperator
implements PolicyOperator {
    protected List<PolicyComponent> policyComponents = new ArrayList<PolicyComponent>();

    public AbstractPolicyOperator() {
    }

    public AbstractPolicyOperator(PolicyOperator parent) {
        parent.addPolicyComponent(this);
    }

    @Override
    public void addPolicyComponent(PolicyComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("Component must not be null");
        }
        this.policyComponents.add(component);
    }

    public void addPolicyComponents(List<? extends PolicyComponent> components) {
        this.policyComponents.addAll(components);
    }

    @Override
    public List<PolicyComponent> getPolicyComponents() {
        return this.policyComponents;
    }

    public PolicyComponent getFirstPolicyComponent() {
        if (this.policyComponents.isEmpty()) {
            return null;
        }
        return this.policyComponents.get(0);
    }

    @Override
    public boolean isEmpty() {
        return this.policyComponents.isEmpty();
    }

    @Override
    public boolean equal(PolicyComponent policyComponent) {
        return PolicyComparator.compare(this, policyComponent);
    }

    protected static Policy normalize(Policy policy, PolicyRegistry reg, boolean deep) {
        String id;
        Policy result = new Policy(reg, policy.getNamespace());
        String policyName = policy.getName();
        if (policyName != null) {
            result.setName(policyName);
        }
        if ((id = policy.getId()) != null) {
            result.setId(id);
        }
        result.addPolicyComponent(AbstractPolicyOperator.normalizeOperator(policy, policy, reg, deep));
        return result;
    }

    private static PolicyComponent normalizeOperator(Policy policy, PolicyOperator operator, PolicyRegistry reg, boolean deep) {
        short type = operator.getType();
        if (operator.isEmpty()) {
            ExactlyOne exactlyOne = new ExactlyOne();
            if (2 != type) {
                exactlyOne.addPolicyComponent(new All());
            }
            return exactlyOne;
        }
        ArrayList<PolicyComponent> childComponentsList = new ArrayList<PolicyComponent>();
        for (PolicyComponent policyComponent : operator.getPolicyComponents()) {
            All all;
            if (policyComponent.getType() == 5) {
                if (deep) {
                    policyComponent = ((Assertion)policyComponent).normalize();
                }
                if (policyComponent.getType() == 1) {
                    childComponentsList.add(((Policy)policyComponent).getFirstPolicyComponent());
                    continue;
                }
                ExactlyOne exactlyOne = new ExactlyOne();
                all = new All();
                all.addPolicyComponent(policyComponent);
                exactlyOne.addPolicyComponent(all);
                childComponentsList.add(exactlyOne);
                continue;
            }
            if (policyComponent.getType() == 4) {
                String uri = ((PolicyReference)policyComponent).getURI();
                PolicyComponent policyComponent2 = policyComponent = reg == null ? null : reg.lookup(uri);
                if (policyComponent == null && uri.charAt(0) == '#') {
                    String id = uri.substring(1);
                    PolicyComponent policyComponent3 = policyComponent = reg == null ? null : reg.lookup(id);
                    if (policyComponent == null) {
                        for (PolicyComponent p : policy.getPolicyComponents()) {
                            if (!(p instanceof Policy) || !id.equals(((Policy)p).getId())) continue;
                            policyComponent = p;
                        }
                    }
                }
                if (policyComponent == null) {
                    throw new RuntimeException(uri + " can't be resolved");
                }
                all = new All();
                all.addPolicyComponents(((Policy)policyComponent).getPolicyComponents());
                childComponentsList.add(AbstractPolicyOperator.normalizeOperator(policy, all, reg, deep));
                continue;
            }
            if (policyComponent.getType() == 1) {
                All all2 = new All();
                all2.addPolicyComponents(((Policy)policyComponent).getPolicyComponents());
                childComponentsList.add(AbstractPolicyOperator.normalizeOperator(policy, all2, reg, deep));
                continue;
            }
            childComponentsList.add(AbstractPolicyOperator.normalizeOperator(policy, (PolicyOperator)policyComponent, reg, deep));
        }
        return AbstractPolicyOperator.computeResultantComponent(childComponentsList, type);
    }

    private static PolicyComponent computeResultantComponent(List<PolicyComponent> normalizedInnerComponets, short componentType) {
        ExactlyOne exactlyOne = new ExactlyOne();
        if (componentType == 2) {
            for (PolicyComponent comp : normalizedInnerComponets) {
                ExactlyOne innerExactlyOne = (ExactlyOne)comp;
                exactlyOne.addPolicyComponents(innerExactlyOne.getPolicyComponents());
            }
        } else if (componentType == 1 || componentType == 3) {
            if (normalizedInnerComponets.size() > 1) {
                Iterator<PolicyComponent> iter = normalizedInnerComponets.iterator();
                exactlyOne = (ExactlyOne)iter.next();
                if (!exactlyOne.isEmpty()) {
                    while (iter.hasNext()) {
                        ExactlyOne currentExactlyOne = (ExactlyOne)iter.next();
                        if (currentExactlyOne.isEmpty()) {
                            exactlyOne = currentExactlyOne;
                            break;
                        }
                        exactlyOne = AbstractPolicyOperator.getCrossProduct(exactlyOne, currentExactlyOne);
                    }
                }
            } else {
                exactlyOne = (ExactlyOne)normalizedInnerComponets.get(0);
            }
        }
        return exactlyOne;
    }

    private static ExactlyOne getCrossProduct(ExactlyOne exactlyOne1, ExactlyOne exactlyOne2) {
        ExactlyOne crossProduct = new ExactlyOne();
        for (PolicyComponent pc : exactlyOne1.getPolicyComponents()) {
            All currentAll1 = (All)pc;
            for (PolicyComponent pc2 : exactlyOne2.getPolicyComponents()) {
                All currentAll2 = (All)pc2;
                All crossProductAll = new All();
                crossProductAll.addPolicyComponents(currentAll1.getPolicyComponents());
                crossProductAll.addPolicyComponents(currentAll2.getPolicyComponents());
                crossProduct.addPolicyComponent(crossProductAll);
            }
        }
        return crossProduct;
    }
}

