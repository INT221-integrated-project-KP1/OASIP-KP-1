package sit.int204.actionback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        Optional<sit.int204.actionback.entities.User> user = userRepository.findUserByEmail(email);
        if(user.isEmpty()){
            System.out.println("user is Empty");
            throw new UsernameNotFoundException("User not found with Email: " + email);
        }
       else {
            System.out.println("user not null");
            List<GrantedAuthority> role = new ArrayList<>();
            role.add(new SimpleGrantedAuthority(user.get().getRole().toUpperCase()));
            UserDetails userDetails = new User(user.get().getEmail(),user.get().getPassword(), role);

            return userDetails;
        }
    }

}