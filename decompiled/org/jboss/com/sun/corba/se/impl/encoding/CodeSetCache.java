/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.WeakHashMap;

class CodeSetCache {
    private ThreadLocal<Object> converterCaches = new ThreadLocal<Object>(){

        @Override
        public Object initialValue() {
            return new Map[]{new WeakHashMap(), new WeakHashMap()};
        }
    };
    private static final int BTC_CACHE_MAP = 0;
    private static final int CTB_CACHE_MAP = 1;

    CodeSetCache() {
    }

    CharsetDecoder getByteToCharConverter(Object key) {
        Map btcMap = ((Map[])this.converterCaches.get())[0];
        return (CharsetDecoder)btcMap.get(key);
    }

    CharsetEncoder getCharToByteConverter(Object key) {
        Map ctbMap = ((Map[])this.converterCaches.get())[1];
        return (CharsetEncoder)ctbMap.get(key);
    }

    CharsetDecoder setConverter(Object key, CharsetDecoder converter) {
        Map btcMap = ((Map[])this.converterCaches.get())[0];
        btcMap.put(key, converter);
        return converter;
    }

    CharsetEncoder setConverter(Object key, CharsetEncoder converter) {
        Map ctbMap = ((Map[])this.converterCaches.get())[1];
        ctbMap.put(key, converter);
        return converter;
    }
}

