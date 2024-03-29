package sit.int204.actionback.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // configure AuthenticationManager so that it knows from where to load
//        // user for matching credentials
//        // Use BCryptPasswordEncoder
//        System.out.println("test");
//        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//        httpSecurity.cors().disable();
        System.out.println("sss");
        //on local ใช้อันนี้แก้ cor
        //บนคอมใช้อันนี้
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "IsRefreshToken"));
        httpSecurity.csrf().disable().cors().configurationSource(request -> corsConfiguration.applyPermitDefaultValues()).and()
        //ถึงอันนี้ //
        // We don't need CSRF for this example
                //onserver ใช้อันเก่า
//        httpSecurity.csrf().disable() //ถ้าขึ้น server ใช้อันนี้
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/api/jwt/login").permitAll()
                .and().authorizeRequests().antMatchers("/api/jwt/loginms").permitAll()
                .and().authorizeRequests().antMatchers("/api/jwt/refresh").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/user").hasAnyAuthority("ADMIN")
                .and().authorizeRequests().antMatchers("/api/eventcategoryowner").hasAnyAuthority("ADMIN")

                //delete put post
                .and().authorizeRequests().antMatchers("/api/event/all").hasAnyAuthority("ADMIN","STUDENT","LECTURER")
                .and().authorizeRequests().antMatchers("/api/event/adding").hasAnyAuthority("ADMIN", "STUDENT")
                .and().authorizeRequests().antMatchers("/api/event/overlapping").hasAnyAuthority("ADMIN", "STUDENT")
                .and().authorizeRequests().antMatchers("/api/event/filtration").permitAll()
                .and().authorizeRequests().antMatchers("/api/event/blinded").permitAll()
//blinded

                .and().authorizeRequests().antMatchers("/api/eventcategory").permitAll()
                .and().authorizeRequests().antMatchers("/api/user").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated().and().

                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}