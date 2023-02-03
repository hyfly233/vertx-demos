package com.hyfly.testmysql.factory;

import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.core.json.jackson.JacksonCodec;
import io.vertx.core.spi.json.JsonCodec;

public class JacksonFactory implements io.vertx.core.spi.JsonFactory {

    public static final JacksonFactory INSTANCE = new JacksonFactory();

    public static final JacksonCodec CODEC;

    static {
        JacksonCodec codec;
        try {
            codec = new DatabindCodec();
        } catch (Throwable ignore) {
            // No databind
            codec = new JacksonCodec();
        }
        CODEC = codec;
    }

    @Override
    public JsonCodec codec() {
        return CODEC;
    }
}
