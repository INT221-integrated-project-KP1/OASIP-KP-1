package sit.int204.actionback.service;

import java.util.ArrayList;
import java.util.Optional;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int204.actionback.repo.MatchingRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private MatchingRepository matchingRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<sit.int204.actionback.entities.User> user = matchingRepository.findByEmail(email);
        System.out.println(user.isEmpty());

        if(user.isEmpty()){
            System.out.println("user is Empty");
            throw new UsernameNotFoundException("User not found with Email: " + email);
        }
       else {
            System.out.println(user.get().getEmail());
            System.out.println(user.get().getPassword());
            System.out.println("user not null");
            UserDetails userDetails = new User(user.get().getEmail(),user.get().getPassword(),new ArrayList<>());

            return userDetails;

        }
//        Argon2 argon2 = Argon2Factory.create();
//        if (argon2.verify(user.getPassword(), userToMatch.getPassword())) {
//            return ResponseEntity.status(200).body("Matched");
//        }
//
//        return ResponseEntity.status(401).body("Invalid Password");


    }

//    public UserDetails loadUserByEmailAndPassword(String email, String password) throws UsernameNotFoundException {
//        Optional<sit.int204.actionback.entities.User> user = matchingRepository.findByEmail(email);
//        System.out.println(user.isEmpty());
//
//        if(user.isEmpty()){
//            System.out.println("user is Empty");
//            throw new UsernameNotFoundException("User not found with Email: " + email);
//        }
//        else {
//            System.out.println(user.get().getEmail());
//            System.out.println(user.get().getPassword());
//            System.out.println("user not null");
//            return new User(user.get().getEmail(), user.get().getPassword(),
//                    new ArrayList<>());
//
//        }
//    }

}