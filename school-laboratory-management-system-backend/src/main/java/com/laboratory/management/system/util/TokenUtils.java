package com.laboratory.management.system.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TokenUtils {

    public static HttpHeaders getAuthToken(String value) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Auth-Token", Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8)));
        return headers;
    }
}
