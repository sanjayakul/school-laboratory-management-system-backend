package com.laboratory.management.system.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.xml.bind.DatatypeConverter;
import java.util.Optional;

@Component
public class AuditorAwareConfig implements AuditorAware<String> {

    public static String xAuthToken() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-Auth-Token");
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        var username = DatatypeConverter.parseBase64Binary(xAuthToken());
        return Optional.of(new String(username).toUpperCase());
    }
}