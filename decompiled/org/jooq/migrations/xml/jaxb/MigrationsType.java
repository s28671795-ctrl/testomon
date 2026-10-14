/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlElementWrapper
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.migrations.xml.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jooq.migrations.xml.jaxb.CommitType;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MigrationsType", propOrder={})
public class MigrationsType
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31500L;
    @XmlElementWrapper(name="commits")
    @XmlElement(name="commit")
    protected List<CommitType> commits;

    public List<CommitType> getCommits() {
        if (this.commits == null) {
            this.commits = new ArrayList<CommitType>();
        }
        return this.commits;
    }

    public void setCommits(List<CommitType> commits) {
        this.commits = commits;
    }

    public MigrationsType withCommits(CommitType ... values2) {
        if (values2 != null) {
            for (CommitType value : values2) {
                this.getCommits().add(value);
            }
        }
        return this;
    }

    public MigrationsType withCommits(Collection<CommitType> values2) {
        if (values2 != null) {
            this.getCommits().addAll(values2);
        }
        return this;
    }

    public MigrationsType withCommits(List<CommitType> commits) {
        this.setCommits(commits);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("commits", "commit", this.commits);
    }

    public String toString() {
        XMLBuilder builder = XMLBuilder.nonFormatting();
        this.appendTo(builder);
        return builder.toString();
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        MigrationsType other = (MigrationsType)that;
        return !(this.commits == null ? other.commits != null : !this.commits.equals(other.commits));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.commits == null ? 0 : this.commits.hashCode());
        return result;
    }
}

