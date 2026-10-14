/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.ad.control.DirSyncControl;
import org.ldaptive.ad.control.GetStatsControl;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.AuthorizationIdentityResponseControl;
import org.ldaptive.control.EntryChangeNotificationControl;
import org.ldaptive.control.GenericControl;
import org.ldaptive.control.PagedResultsControl;
import org.ldaptive.control.PasswordExpiredControl;
import org.ldaptive.control.PasswordExpiringControl;
import org.ldaptive.control.PasswordPolicyControl;
import org.ldaptive.control.ResponseControl;
import org.ldaptive.control.SessionTrackingControl;
import org.ldaptive.control.SortResponseControl;
import org.ldaptive.control.SyncDoneControl;
import org.ldaptive.control.SyncStateControl;
import org.ldaptive.control.VirtualListViewResponseControl;

public final class ControlFactory {
    private ControlFactory() {
    }

    public static ResponseControl createResponseControl(String oid, boolean critical, DERBuffer encoded) {
        AbstractControl ctl;
        switch (oid) {
            case "1.2.840.113556.1.4.474": {
                ctl = new SortResponseControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "1.2.840.113556.1.4.319": {
                ctl = new PagedResultsControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "2.16.840.1.113730.3.4.10": {
                ctl = new VirtualListViewResponseControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "1.3.6.1.4.1.42.2.27.8.5.1": {
                ctl = new PasswordPolicyControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "1.3.6.1.4.1.4203.1.9.1.2": {
                ctl = new SyncStateControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "1.3.6.1.4.1.4203.1.9.1.3": {
                ctl = new SyncDoneControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "1.2.840.113556.1.4.841": {
                ctl = new DirSyncControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "2.16.840.1.113730.3.4.7": {
                ctl = new EntryChangeNotificationControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "1.2.840.113556.1.4.970": {
                ctl = new GetStatsControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "2.16.840.1.113730.3.4.4": {
                ctl = new PasswordExpiredControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "2.16.840.1.113730.3.4.5": {
                ctl = new PasswordExpiringControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "2.16.840.1.113730.3.4.15": {
                ctl = new AuthorizationIdentityResponseControl(critical);
                ctl.decode(encoded);
                break;
            }
            case "1.3.6.1.4.1.21008.108.63.1": {
                ctl = new SessionTrackingControl(critical);
                ctl.decode(encoded);
                break;
            }
            default: {
                ctl = new GenericControl(oid, critical, encoded);
            }
        }
        return ctl;
    }
}

