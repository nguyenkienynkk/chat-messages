//package com.example.chatmessages.configuration;
//
//import com.nimbusds.jwt.SignedJWT;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtException;
//import org.springframework.stereotype.Component;
//
//import java.text.ParseException;
//
//@Component
//public class CustomJwtDecoder implements JwtDecoder {
//    @Override
//    public Jwt decode(String token) throws JwtException {
//        // can not introspect and verify signature you can delete and decode it now
//        try {
//            SignedJWT signedJWT = SignedJWT.parse(token);
//            return new Jwt(
//                    token,
//                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
//                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
//                    signedJWT.getHeader().toJSONObject(),
//                    signedJWT.getJWTClaimsSet().getClaims());
//        } catch (ParseException e) {
//            throw new JwtException("Invalid token");
//        }
//    }
//}
