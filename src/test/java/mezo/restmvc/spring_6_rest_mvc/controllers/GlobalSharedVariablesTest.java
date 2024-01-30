package mezo.restmvc.spring_6_rest_mvc.controllers;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.time.Instant;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

final public class GlobalSharedVariablesTest {

    // Basic Authentication
    static public final String USERNAME = "user";
    static public final String PASSWORD = "pass";


    static private final RequestPostProcessor httpRequestPostProcessor = SecurityMockMvcRequestPostProcessors.httpBasic(
            GlobalSharedVariablesTest.USERNAME,
            GlobalSharedVariablesTest.PASSWORD);


    // OAuth2 Authentication
    static private final RequestPostProcessor jwtRequestPostProcessor = jwt().jwt(
            jwt -> {
                jwt.claims(claims -> {
                       claims.put("socpe", "message.read");
                       claims.put("socpe", "message.writ");
                   })
                   .subject("messaging-client")
                   .notBefore(Instant.now()
                                     .minusSeconds(5));

            });

    static public final RequestPostProcessor requestPostProcessor = jwtRequestPostProcessor;

}


