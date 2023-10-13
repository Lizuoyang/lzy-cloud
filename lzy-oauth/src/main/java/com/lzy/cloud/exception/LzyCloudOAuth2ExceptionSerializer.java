package com.lzy.cloud.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * 异常序列化器
 *
 * @author 2428
 * @date 2022/06/21
 */
public class LzyCloudOAuth2ExceptionSerializer extends StdSerializer<LzyCloudOAuth2Exception> {

    protected LzyCloudOAuth2ExceptionSerializer() {
        super(LzyCloudOAuth2Exception.class);
    }

    @Override
    public void serialize(LzyCloudOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("code", e.getHttpErrorCode());
        jsonGenerator.writeStringField("msg", e.getOAuth2ErrorCode());
        jsonGenerator.writeEndObject();
    }
}
