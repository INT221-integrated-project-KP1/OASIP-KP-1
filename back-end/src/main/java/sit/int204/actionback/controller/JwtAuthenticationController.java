package sit.int204.actionback.controller;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
import sit.int204.actionback.dtos.UserMatchingDTO;
import sit.int204.actionback.entities.User;


import sit.int204.actionback.config.JwtTokenUtil;
import sit.int204.actionback.model.JwtResponse;
import sit.int204.actionback.repo.UserRepository;

import javax.validation.Valid;
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
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
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