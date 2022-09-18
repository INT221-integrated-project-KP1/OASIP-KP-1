package sit.int204.actionback.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int204.actionback.repo.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<sit.int204.actionback.entities.User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            System.out.println("user is Empty");
            throw new UsernameNotFoundException("User not found with Email: " + email);
        }
       else {
            System.out.println("user not null");
            UserDetails userDetails = new User(user.get().getEmail(),user.get().getPassword(),new ArrayList<>());

            return userDetails;
        }
    }

}