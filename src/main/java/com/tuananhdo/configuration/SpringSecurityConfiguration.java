package com.tuananhdo.configuration;

import com.tuananhdo.security.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/users/home","/admin/home","/users/account").hasAnyAuthority("Admin", "Manage","Staff")

                .antMatchers("/users","/users/new","/users/edit/**","/users/delete/**").hasAnyAuthority("Admin")

                .antMatchers("/roles/home","/roles/new","/roles/edit/**","/roles/delete/**").hasAnyAuthority("Admin")

                .antMatchers("/projects/home","/projects/new","/projects/edit/**","/projects/delete/**").hasAnyAuthority("Admin","Manage")

                .antMatchers("/task/overview","/task/new","/task/edit/**","/task/delete/**").hasAnyAuthority("Admin","Manage")

                .anyRequest().permitAll()
                .and().formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password").defaultSuccessUrl("/admin/home").failureUrl("/login?error")
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");
    }
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


}
