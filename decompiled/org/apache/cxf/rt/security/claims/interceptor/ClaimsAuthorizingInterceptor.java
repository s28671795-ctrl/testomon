/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.claims.interceptor;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.rt.security.claims.Claim;
import org.apache.cxf.rt.security.claims.ClaimBean;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.claims.ClaimsSecurityContext;
import org.apache.cxf.rt.security.claims.SAMLClaim;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.security.claims.authorization.ClaimMode;
import org.apache.cxf.security.claims.authorization.Claims;

public class ClaimsAuthorizingInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(ClaimsAuthorizingInterceptor.class);
    private static final Set<String> SKIP_METHODS = new HashSet<String>(Arrays.asList("wait", "notify", "notifyAll", "equals", "toString", "hashCode"));
    private Map<String, List<ClaimBean>> claims = new HashMap<String, List<ClaimBean>>();
    private Map<String, String> nameAliases = Collections.emptyMap();
    private Map<String, String> formatAliases = Collections.emptyMap();

    public ClaimsAuthorizingInterceptor() {
        super("pre-invoke");
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        SecurityContext sc = message.get(SecurityContext.class);
        if (!(sc instanceof ClaimsSecurityContext)) {
            throw new AccessDeniedException("Security Context is unavailable or unrecognized");
        }
        Method method2 = this.getTargetMethod(message);
        if (this.authorize((ClaimsSecurityContext)sc, method2)) {
            return;
        }
        throw new AccessDeniedException("Unauthorized");
    }

    public void setClaims(Map<String, List<ClaimBean>> claimsMap) {
        this.claims.putAll(claimsMap);
    }

    protected Method getTargetMethod(Message m4) {
        return MessageUtils.getTargetMethod(m4).orElseThrow(() -> new AccessDeniedException("Method is not available : Unauthorized"));
    }

    protected boolean authorize(ClaimsSecurityContext sc, Method method2) {
        List<ClaimBean> list = this.claims.get(method2.getName());
        ClaimCollection actualClaims = sc.getClaims();
        for (ClaimBean claimBean : list) {
            Claim matchingClaim = null;
            for (Claim cl : actualClaims) {
                if (cl instanceof SAMLClaim) {
                    if (!((SAMLClaim)cl).getName().equals(claimBean.getClaim().getClaimType()) || !((SAMLClaim)cl).getNameFormat().equals(claimBean.getClaimFormat())) continue;
                    matchingClaim = cl;
                    break;
                }
                if (!cl.getClaimType().equals(claimBean.getClaim().getClaimType())) continue;
                matchingClaim = cl;
                break;
            }
            if (matchingClaim == null) {
                if (claimBean.getClaimMode() != ClaimMode.STRICT) continue;
                return false;
            }
            List<Object> claimValues = claimBean.getClaim().getValues();
            List<Object> matchingClaimValues = matchingClaim.getValues();
            if (claimBean.isMatchAll() && !matchingClaimValues.containsAll(claimValues)) {
                return false;
            }
            boolean matched = false;
            for (Object value : matchingClaimValues) {
                if (!claimValues.contains(value)) continue;
                matched = true;
                break;
            }
            if (matched) continue;
            return false;
        }
        return true;
    }

    public void setSecuredObject(Object object) {
        Class<?> cls = ClassHelper.getRealClass(object);
        this.findClaims(cls);
        if (this.claims.isEmpty()) {
            LOG.warning("The claims list is empty, the service object is not protected");
        }
    }

    protected void findClaims(Class<?> cls) {
        if (cls == null || cls == Object.class) {
            return;
        }
        List<ClaimBean> clsClaims = this.getClaims(cls.getAnnotation(Claims.class), cls.getAnnotation(org.apache.cxf.security.claims.authorization.Claim.class));
        for (Method method2 : cls.getMethods()) {
            if (SKIP_METHODS.contains(method2.getName())) continue;
            List<ClaimBean> methodClaims = this.getClaims(method2.getAnnotation(Claims.class), method2.getAnnotation(org.apache.cxf.security.claims.authorization.Claim.class));
            ArrayList<ClaimBean> allClaims = new ArrayList<ClaimBean>(methodClaims);
            for (ClaimBean bean : clsClaims) {
                if (ClaimsAuthorizingInterceptor.isClaimOverridden(bean, methodClaims)) continue;
                allClaims.add(bean);
            }
            this.claims.put(method2.getName(), allClaims);
        }
        if (!this.claims.isEmpty()) {
            return;
        }
        this.findClaims(cls.getSuperclass());
        if (!this.claims.isEmpty()) {
            return;
        }
        for (GenericDeclaration genericDeclaration : cls.getInterfaces()) {
            this.findClaims((Class<?>)genericDeclaration);
        }
    }

    private static boolean isClaimOverridden(ClaimBean bean, List<ClaimBean> mClaims) {
        for (ClaimBean methodBean : mClaims) {
            if (!bean.getClaim().getClaimType().equals(methodBean.getClaim().getClaimType()) || !bean.getClaimFormat().equals(methodBean.getClaimFormat())) continue;
            return true;
        }
        return false;
    }

    private List<ClaimBean> getClaims(Claims claimsAnn, org.apache.cxf.security.claims.authorization.Claim claimAnn) {
        ArrayList<ClaimBean> claimsList = new ArrayList<ClaimBean>();
        List<org.apache.cxf.security.claims.authorization.Claim> annClaims = claimsAnn != null ? Arrays.asList(claimsAnn.value()) : (claimAnn != null ? Collections.singletonList(claimAnn) : Collections.emptyList());
        for (org.apache.cxf.security.claims.authorization.Claim ann : annClaims) {
            String claimFormat;
            Claim claim = new Claim();
            String claimName = ann.name();
            if (this.nameAliases.containsKey(claimName)) {
                claimName = this.nameAliases.get(claimName);
            }
            if (this.formatAliases.containsKey(claimFormat = ann.format())) {
                claimFormat = this.formatAliases.get(claimFormat);
            }
            claim.setClaimType(claimName);
            for (String value : ann.value()) {
                claim.addValue(value);
            }
            claimsList.add(new ClaimBean(claim, claimFormat, ann.mode(), ann.matchAll()));
        }
        return claimsList;
    }

    public void setNameAliases(Map<String, String> nameAliases) {
        this.nameAliases = nameAliases;
    }

    public void setFormatAliases(Map<String, String> formatAliases) {
        this.formatAliases = formatAliases;
    }
}

