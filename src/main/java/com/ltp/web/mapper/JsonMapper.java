package com.ltp.web.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonMapper {

    public static <T> T parseToObject(String input, Class<T> cl) throws IOException {
        return new ObjectMapper().readValue(input, cl);
    }

    public static <T> String parseToString(T t) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(t);
    }

}
