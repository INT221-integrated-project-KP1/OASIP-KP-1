package sit.int204.actionback.controller;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.jsonwebtoken.Jwts;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sit.int204.actionback.dtos.UserMatchingDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.MatchingRepository;
import sit.int204.actionback.service.JwtUserDetailsService;


import sit.int204.actionback.config.JwtTokenUtil;
import sit.int204.actionback.model.JwtRequest;
import sit.int204.actionback.model.JwtResponse;
import sit.int204.actionback.service.MatchingService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/login")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MatchingRepository matchingRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserMatchingDTO authenticationRequest) throws Exception {

//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Optional<User> user = matchingRepository.findByEmail(authenticationRequest.getEmail());
        if(user.isEmpty()){
            return ResponseEntity.status(404).body("Dont have this Email(");
        }

        Argon2 argon2 = Argon2Factory.create();
        if (argon2.verify(user.get().getPassword(), authenticationRequest.getPassword())) {
//            final UserDetails userDetails = userDetailsService
//                    .loadUserByUsername(authenticationRequest.getUsername());
//            UserDetails userDetails = new org.springframework.security.core.userdetails.User(authenticationRequest.getUsername(), authenticationRequest.getPassword(), new ArrayList<>());
            final String token = jwtTokenUtil.generateToken(user);
            return ResponseEntity.ok(new JwtResponse(token));
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
}