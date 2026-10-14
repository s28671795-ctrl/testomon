/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.path;

import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Scm;
import org.apache.maven.model.Site;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.path.ModelUrlNormalizer;
import org.apache.maven.model.path.UrlNormalizer;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component(role=ModelUrlNormalizer.class)
public class DefaultModelUrlNormalizer
implements ModelUrlNormalizer {
    @Requirement
    private UrlNormalizer urlNormalizer;

    public DefaultModelUrlNormalizer setUrlNormalizer(UrlNormalizer urlNormalizer) {
        this.urlNormalizer = urlNormalizer;
        return this;
    }

    @Override
    public void normalize(Model model, ModelBuildingRequest request) {
        Site site;
        DistributionManagement dist;
        if (model == null) {
            return;
        }
        model.setUrl(this.normalize(model.getUrl()));
        Scm scm = model.getScm();
        if (scm != null) {
            scm.setUrl(this.normalize(scm.getUrl()));
            scm.setConnection(this.normalize(scm.getConnection()));
            scm.setDeveloperConnection(this.normalize(scm.getDeveloperConnection()));
        }
        if ((dist = model.getDistributionManagement()) != null && (site = dist.getSite()) != null) {
            site.setUrl(this.normalize(site.getUrl()));
        }
    }

    private String normalize(String url2) {
        return this.urlNormalizer.normalize(url2);
    }
}

