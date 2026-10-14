/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import org.ldaptive.Credential;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.auth.User;
import org.ldaptive.control.RequestControl;

public class AuthenticationRequest {
    private User user;
    private Credential credential;
    private String[] returnAttributes = ReturnAttributes.NONE.value();
    private RequestControl[] controls;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String id, Credential c) {
        this.setUser(new User(id));
        this.setCredential(c);
    }

    public AuthenticationRequest(String id, Credential c, String ... attrs) {
        this.setUser(new User(id));
        this.setCredential(c);
        this.setReturnAttributes(attrs);
    }

    public AuthenticationRequest(User u, Credential c) {
        this.setUser(u);
        this.setCredential(c);
    }

    public AuthenticationRequest(User u, Credential c, String ... attrs) {
        this.setUser(u);
        this.setCredential(c);
        this.setReturnAttributes(attrs);
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public Credential getCredential() {
        return this.credential;
    }

    public void setCredential(Credential c) {
        this.credential = c;
    }

    public String[] getReturnAttributes() {
        return this.returnAttributes;
    }

    public void setReturnAttributes(String ... attrs) {
        this.returnAttributes = ReturnAttributes.parse(attrs);
    }

    public RequestControl[] getControls() {
        return this.controls;
    }

    public void setControls(RequestControl ... cntrls) {
        this.controls = cntrls;
    }

    public static AuthenticationRequest copy(AuthenticationRequest request) {
        AuthenticationRequest r = new AuthenticationRequest();
        r.setUser(request.getUser());
        r.setCredential(request.getCredential());
        r.setReturnAttributes(request.getReturnAttributes());
        r.setControls(request.getControls());
        return r;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "user=" + this.user + ", " + "returnAttributes=" + Arrays.toString(this.returnAttributes) + ", " + "controls=" + Arrays.toString(this.controls) + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final AuthenticationRequest object = new AuthenticationRequest();

        protected Builder() {
        }

        public Builder id(String id) {
            this.object.setUser(new User(id));
            return this;
        }

        public Builder credential(Credential credential) {
            this.object.setCredential(credential);
            return this;
        }

        public Builder credential(String credential) {
            this.object.setCredential(new Credential(credential));
            return this;
        }

        public Builder credential(char[] credential) {
            this.object.setCredential(new Credential(credential));
            return this;
        }

        public Builder credential(byte[] credential) {
            this.object.setCredential(new Credential(credential));
            return this;
        }

        public Builder user(User user) {
            this.object.setUser(user);
            return this;
        }

        public Builder returnAttributes(String ... attributes) {
            this.object.setReturnAttributes(attributes);
            return this;
        }

        public Builder controls(RequestControl ... controls) {
            this.object.setControls(controls);
            return this;
        }

        public AuthenticationRequest build() {
            return this.object;
        }
    }
}

