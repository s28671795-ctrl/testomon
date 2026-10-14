/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;

public class SAMLArtifactContext
extends BaseContext {
    @Nullable
    private byte[] artifactType;
    @Nullable
    @NotEmpty
    private String sourceEntityId;
    @Nullable
    @NotEmpty
    private String sourceArtifactResolutionServiceEndpointUrl;
    @Nullable
    private Integer sourceArtifactResolutionServiceEndpointIndex;

    @Nullable
    public byte[] getArtifactType() {
        return this.artifactType;
    }

    public void setArtifactType(@Nullable byte[] type) {
        this.artifactType = type;
    }

    @Nullable
    @NotEmpty
    public String getSourceEntityId() {
        SAMLSelfEntityContext self;
        if (this.sourceEntityId == null && this.getParent() != null && (self = (SAMLSelfEntityContext)this.getParent().getSubcontext(SAMLSelfEntityContext.class)) != null) {
            this.sourceEntityId = self.getEntityId();
        }
        return this.sourceEntityId;
    }

    public void setSourceEntityId(@Nullable String entityId) {
        this.sourceEntityId = StringSupport.trimOrNull(entityId);
    }

    @Nullable
    @NotEmpty
    public String getSourceArtifactResolutionServiceEndpointURL() {
        return this.sourceArtifactResolutionServiceEndpointUrl;
    }

    public void setSourceArtifactResolutionServiceEndpointURL(@Nullable String url2) {
        this.sourceArtifactResolutionServiceEndpointUrl = StringSupport.trimOrNull(url2);
    }

    @Nullable
    public Integer getSourceArtifactResolutionServiceEndpointIndex() {
        return this.sourceArtifactResolutionServiceEndpointIndex;
    }

    public void setSourceArtifactResolutionServiceEndpointIndex(@Nullable Integer index) {
        this.sourceArtifactResolutionServiceEndpointIndex = index;
    }
}

