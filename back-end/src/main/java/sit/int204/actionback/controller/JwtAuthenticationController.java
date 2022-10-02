package sit.int204.actionback.controller;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import sit.int204.actionback.dtos.UserMatchingDTO;
import sit.int204.actionback.entities.User;


import sit.int204.actionback.config.JwtTokenUtil;
import sit.int204.actionback.model.JwtResponse;
import sit.int204.actionback.repo.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/jwt")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserMatchingDTO authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UserDetails user = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        String name = userRepository.findByEmail(authenticationRequest.getEmail()).getName();

        Argon2 argon2 = Argon2Factory.create();
        if (argon2.verify(user.getPassword(), authenticationRequest.getPassword())) {
//            final UserDetails userDetails = userDetailsService
//                    .loadUserByUsername(authenticationRequest.getUsername());
//            UserDetails userDetails = new org.springframework.security.core.userdetails.User(authenticationRequest.getUsername(), authenticationRequest.getPassword(), new ArrayList<>());
            final String token = jwtTokenUtil.generateToken(user, name);
            final String token2 = jwtTokenUtil.generateRefreshToken(user, name);
            HashMap<String, String> objectToResponse = new HashMap<String, String>();
            objectToResponse.put("token", token);
            objectToResponse.put("refreshtoken", token2);
            objectToResponse.put("name", name);
            return ResponseEntity.ok(objectToResponse);
        }
      else  return ResponseEntity.status(404).body("Password Invaild");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        HashMap<String, String> objectToResponse = new HashMap<String, String>();
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        if(claims == null){
            return ResponseEntity.status(403).body("Claims == null, Can't Refresh");
        }

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        objectToResponse.put("token", token);
        return ResponseEntity.ok(objectToResponse);
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}