/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.authentication;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.ChoiceCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.RealmChoiceCallback;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;

public class SASLAuthenticator
implements ClientAuthenticator,
CallbackHandler {
    protected String realm;
    protected String username;
    protected String password;
    protected SaslClient authenticator;
    protected boolean complete = false;

    public SASLAuthenticator(String[] mechanisms, Properties properties, String protocol, String host2, String realm, String authorizationID, String username, String password) throws MessagingException {
        this.realm = realm;
        this.username = username;
        this.password = password;
        try {
            this.authenticator = Sasl.createSaslClient(mechanisms, authorizationID, protocol, host2, properties, this);
        }
        catch (SaslException e) {
            // empty catch block
        }
    }

    public boolean hasInitialResponse() {
        return this.authenticator.hasInitialResponse();
    }

    public boolean isComplete() {
        return this.authenticator.hasInitialResponse();
    }

    public String getMechanismName() {
        return this.authenticator.getMechanismName();
    }

    public byte[] evaluateChallenge(byte[] challenge) throws MessagingException {
        if (challenge == null) {
            challenge = new byte[]{};
        }
        try {
            return this.authenticator.evaluateChallenge(challenge);
        }
        catch (SaslException e) {
            throw new MessagingException("Error performing SASL validation", e);
        }
    }

    public void handle(Callback[] callBacks) {
        block0: for (int i = 0; i < callBacks.length; ++i) {
            Callback realmCallback;
            Callback callBack = callBacks[i];
            if (callBack instanceof NameCallback) {
                ((NameCallback)callBack).setName(this.username);
                continue;
            }
            if (callBack instanceof PasswordCallback) {
                ((PasswordCallback)callBack).setPassword(this.password.toCharArray());
                continue;
            }
            if (callBack instanceof RealmCallback) {
                realmCallback = (RealmCallback)callBack;
                if (this.realm == null) {
                    ((TextInputCallback)realmCallback).setText(((TextInputCallback)realmCallback).getDefaultText());
                    continue;
                }
                ((TextInputCallback)realmCallback).setText(this.realm);
                continue;
            }
            if (!(callBack instanceof RealmChoiceCallback)) continue;
            realmCallback = (RealmChoiceCallback)callBack;
            if (this.realm == null) {
                ((ChoiceCallback)realmCallback).setSelectedIndex(((ChoiceCallback)realmCallback).getDefaultChoice());
                continue;
            }
            String[] choices = ((ChoiceCallback)realmCallback).getChoices();
            for (int j = 0; j < choices.length; ++j) {
                if (!choices[j].equals(this.realm)) continue;
                ((ChoiceCallback)realmCallback).setSelectedIndex(j);
                continue block0;
            }
        }
    }
}

