/*
 * Decompiled with CFR 0.152.
 */
package javax.mail.internet;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.mail.Address;
import javax.mail.internet.AddressException;

public class NewsAddress
extends Address {
    protected String newsgroup;
    protected String host;
    private static final long serialVersionUID = -4203797299824684143L;

    public NewsAddress() {
    }

    public NewsAddress(String newsgroup) {
        this(newsgroup, null);
    }

    public NewsAddress(String newsgroup, String host2) {
        this.newsgroup = newsgroup;
        this.host = host2;
    }

    public String getType() {
        return "news";
    }

    public void setNewsgroup(String newsgroup) {
        this.newsgroup = newsgroup;
    }

    public String getNewsgroup() {
        return this.newsgroup;
    }

    public void setHost(String host2) {
        this.host = host2;
    }

    public String getHost() {
        return this.host;
    }

    public String toString() {
        return this.newsgroup;
    }

    public boolean equals(Object a) {
        if (!(a instanceof NewsAddress)) {
            return false;
        }
        NewsAddress s2 = (NewsAddress)a;
        return (this.newsgroup == null && s2.newsgroup == null || this.newsgroup != null && this.newsgroup.equals(s2.newsgroup)) && (this.host == null && s2.host == null || this.host != null && s2.host != null && this.host.equalsIgnoreCase(s2.host));
    }

    public int hashCode() {
        int hash = 0;
        if (this.newsgroup != null) {
            hash += this.newsgroup.hashCode();
        }
        if (this.host != null) {
            hash += this.host.toLowerCase(Locale.ENGLISH).hashCode();
        }
        return hash;
    }

    public static String toString(Address[] addresses) {
        if (addresses == null || addresses.length == 0) {
            return null;
        }
        StringBuffer s2 = new StringBuffer(((NewsAddress)addresses[0]).toString());
        for (int i = 1; i < addresses.length; ++i) {
            s2.append(",").append(((NewsAddress)addresses[i]).toString());
        }
        return s2.toString();
    }

    public static NewsAddress[] parse(String newsgroups) throws AddressException {
        StringTokenizer st = new StringTokenizer(newsgroups, ",");
        ArrayList<NewsAddress> nglist = new ArrayList<NewsAddress>();
        while (st.hasMoreTokens()) {
            String ng = st.nextToken();
            nglist.add(new NewsAddress(ng));
        }
        return nglist.toArray(new NewsAddress[nglist.size()]);
    }
}

