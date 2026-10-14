/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.binding;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import org.opensaml.saml.saml2.metadata.Endpoint;

public interface EndpointResolver<EndpointType extends Endpoint>
extends Resolver<EndpointType, CriteriaSet> {
}

