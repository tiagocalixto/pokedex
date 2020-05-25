package br.com.tiagocalixto.pokedex.infra.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(UserConfiguration.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private UserConfiguration configuration;

    @Autowired
    public WebSecurityConfig(UserConfiguration configuration) {

        this.configuration = configuration;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/pokemonController/**").authenticated()
                .anyRequest().authenticated().and().httpBasic();
        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        for (User user : configuration.getUsers()) {
            auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                    .withUser(user.getUserName()).password(user.getPassword()).roles(user.getRoles());
        }
    }
}
