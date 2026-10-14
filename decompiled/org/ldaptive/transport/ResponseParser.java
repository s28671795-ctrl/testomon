/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.util.Optional;
import org.ldaptive.AddResponse;
import org.ldaptive.BindResponse;
import org.ldaptive.CompareResponse;
import org.ldaptive.DeleteResponse;
import org.ldaptive.LdapEntry;
import org.ldaptive.Message;
import org.ldaptive.ModifyDnResponse;
import org.ldaptive.ModifyResponse;
import org.ldaptive.SearchResponse;
import org.ldaptive.SearchResultReference;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.extended.ExtendedResponse;
import org.ldaptive.extended.IntermediateResponse;
import org.ldaptive.extended.NoticeOfDisconnection;
import org.ldaptive.extended.SyncInfoMessage;

public class ResponseParser {
    private static final DERPath BIND_PATH = new DERPath("/SEQ/APP(1)");
    private static final DERPath ENTRY_PATH = new DERPath("/SEQ/APP(4)");
    private static final DERPath SEARCH_PATH = new DERPath("/SEQ/APP(5)");
    private static final DERPath MODIFY_PATH = new DERPath("/SEQ/APP(7)");
    private static final DERPath ADD_PATH = new DERPath("/SEQ/APP(9)");
    private static final DERPath DELETE_PATH = new DERPath("/SEQ/APP(11)");
    private static final DERPath MODIFY_DN_PATH = new DERPath("/SEQ/APP(13)");
    private static final DERPath COMPARE_PATH = new DERPath("/SEQ/APP(15)");
    private static final DERPath SEARCH_REFERENCE_PATH = new DERPath("/SEQ/APP(19)");
    private static final DERPath EXTENDED_PATH = new DERPath("/SEQ/APP(24)");
    private static final DERPath INTERMEDIATE_PATH = new DERPath("/SEQ/APP(25)");
    private final DERParser parser = new DERParser();
    private Message message;

    public ResponseParser() {
        this.parser.registerHandler(BIND_PATH, (p, e) -> {
            e.clear();
            this.message = new BindResponse(e);
        });
        this.parser.registerHandler(ENTRY_PATH, (p, e) -> {
            e.clear();
            this.message = new LdapEntry(e);
        });
        this.parser.registerHandler(SEARCH_PATH, (p, e) -> {
            e.clear();
            this.message = new SearchResponse(e);
        });
        this.parser.registerHandler(MODIFY_PATH, (p, e) -> {
            e.clear();
            this.message = new ModifyResponse(e);
        });
        this.parser.registerHandler(ADD_PATH, (p, e) -> {
            e.clear();
            this.message = new AddResponse(e);
        });
        this.parser.registerHandler(DELETE_PATH, (p, e) -> {
            e.clear();
            this.message = new DeleteResponse(e);
        });
        this.parser.registerHandler(MODIFY_DN_PATH, (p, e) -> {
            e.clear();
            this.message = new ModifyDnResponse(e);
        });
        this.parser.registerHandler(COMPARE_PATH, (p, e) -> {
            e.clear();
            this.message = new CompareResponse(e);
        });
        this.parser.registerHandler(SEARCH_REFERENCE_PATH, (p, e) -> {
            e.clear();
            this.message = new SearchResultReference(e);
        });
        this.parser.registerHandler(EXTENDED_PATH, (p, e) -> {
            e.clear();
            ExtendedResponse extRes = new ExtendedResponse(e);
            this.message = "1.3.6.1.4.1.1466.20036".equals(extRes.getResponseName()) ? new NoticeOfDisconnection() : extRes;
        });
        this.parser.registerHandler(INTERMEDIATE_PATH, (p, e) -> {
            e.clear();
            IntermediateResponse intRes = new IntermediateResponse(e);
            if ("1.3.6.1.4.1.4203.1.9.1.4".equals(intRes.getResponseName())) {
                e.clear();
                this.message = new SyncInfoMessage(e);
            } else {
                this.message = intRes;
            }
        });
    }

    public Optional<Message> parse(DERBuffer buffer) {
        this.parser.parse(buffer);
        return Optional.ofNullable(this.message);
    }
}

