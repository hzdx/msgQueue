package com.mycom.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Codec{

    private Logger logger = LoggerFactory.getLogger(Codec.class);

    private static ObjectMapper objectMapper = null;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.AUTO_DETECT_CREATORS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_FIELDS, false);
       // objectMapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * 编码HttpResponse
     */
    public HttpResponse encodeResponse(Object response) throws Exception {
        logger.debug("From {}", response);
        HttpResponse httpResponse = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null);
        String json = toJson(response);
        httpResponse.setEntity(new StringEntity(json, "UTF-8"));
        logger.debug("To {} {}", httpResponse, json);
        return httpResponse;
    }

    /**
     * 解码HttpResponse
     */
    public <T> T decodeResponse(HttpResponse httpResponse, Class<T> valueType) throws Exception {
        String json = EntityUtils.toString(httpResponse.getEntity(), "UTF8");
        logger.debug("From {} {}", httpResponse, json);
        T Response = fromJson(json, valueType);
        logger.debug("To {}", Response);
        return Response;
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(json, valueType);
    }

    public static String toJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

}