package mk.ukim.finki.performance_review.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("session")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/assets/**", "/register", "/api/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=Bad+Credentials")
                .defaultSuccessUrl("/tasks", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("performance_review")
                .logoutSuccessUrl("/login");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user1")
                .password(passwordEncoder.encode("user1"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user2")
                .password(passwordEncoder.encode("user2"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user3")
                .password(passwordEncoder.encode("user3"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user4")
                .password(passwordEncoder.encode("user4"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user5")
                .password(passwordEncoder.encode("user5"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user6")
                .password(passwordEncoder.encode("user6"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user7")
                .password(passwordEncoder.encode("user7"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user8")
                .password(passwordEncoder.encode("user8"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user9")
                .password(passwordEncoder.encode("user9"))
                .authorities("ROLE_USER")
                .and()
                .withUser("user10")
                .password(passwordEncoder.encode("user10"))
                .authorities("ROLE_USER")
                .and()
                .withUser("admin1")
                .password(passwordEncoder.encode("admin1"))
                .authorities("ROLE_ADMIN")
                .and()
                .withUser("admin2")
                .password(passwordEncoder.encode("admin2"))
                .authorities("ROLE_ADMIN")
                .and()
                .withUser("admin3")
                .password(passwordEncoder.encode("admin3"))
                .authorities("ROLE_ADMIN");
    }

}
