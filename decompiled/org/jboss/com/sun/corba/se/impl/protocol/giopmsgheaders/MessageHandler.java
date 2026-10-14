/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_2;

public interface MessageHandler {
    public void handleInput(Message var1) throws IOException;

    public void handleInput(RequestMessage_1_0 var1) throws IOException;

    public void handleInput(RequestMessage_1_1 var1) throws IOException;

    public void handleInput(RequestMessage_1_2 var1) throws IOException;

    public void handleInput(ReplyMessage_1_0 var1) throws IOException;

    public void handleInput(ReplyMessage_1_1 var1) throws IOException;

    public void handleInput(ReplyMessage_1_2 var1) throws IOException;

    public void handleInput(LocateRequestMessage_1_0 var1) throws IOException;

    public void handleInput(LocateRequestMessage_1_1 var1) throws IOException;

    public void handleInput(LocateRequestMessage_1_2 var1) throws IOException;

    public void handleInput(LocateReplyMessage_1_0 var1) throws IOException;

    public void handleInput(LocateReplyMessage_1_1 var1) throws IOException;

    public void handleInput(LocateReplyMessage_1_2 var1) throws IOException;

    public void handleInput(FragmentMessage_1_1 var1) throws IOException;

    public void handleInput(FragmentMessage_1_2 var1) throws IOException;

    public void handleInput(CancelRequestMessage var1) throws IOException;
}

