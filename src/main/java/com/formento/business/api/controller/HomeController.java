package com.formento.business.api.controller;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    @ResponseBody
    public final Map<String, Object> home() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.debug(username);

        return ImmutableMap.<String, Object>builder().
            put("username", username).
            put("field", "blah").
            build();
    }

}
