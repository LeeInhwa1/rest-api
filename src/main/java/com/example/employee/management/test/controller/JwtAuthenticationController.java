package com.example.employee.management.test.controller;

import java.util.Objects;

import com.example.employee.management.test.config.JwtTokenUtil;
import com.example.employee.management.test.model.JwtRequest;
import com.example.employee.management.test.model.JwtResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "4. JWT 토큰 연동 테스트")
@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @ApiOperation(value = "토큰 가져오기")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        String token = null;
        try {

            log.debug("getUserName : " + authenticationRequest.getUsername());
            log.debug("getUserPassword : " + authenticationRequest.getPassword());

            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());

            token = jwtTokenUtil.generateToken(userDetails);
            log.debug("TOKEN :: " + token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED : " + e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS : " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
