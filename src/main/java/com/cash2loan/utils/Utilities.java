package com.cash2loan.utils;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Utilities {
    public static final Algorithm Util_algorithm = Algorithm.HMAC256("secret".getBytes());

    public static final URI Util_uri(String endpoint) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(endpoint).toUriString());
    }
//    public static final URI Util_uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
}
