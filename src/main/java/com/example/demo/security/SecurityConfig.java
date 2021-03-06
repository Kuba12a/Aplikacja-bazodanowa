package com.example.demo.security;


import com.example.demo.dao.AddressesDAO;
import com.example.demo.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.List;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource datasource;

    @Autowired
    private UsersDao usersDao;

/*
    @Bean
    public UserDetailsService userDetailsService(){

        List<com.example.demo.model.User> listUser=usersDao.list();

    UserDetails user1 = User.withDefaultPasswordEncoder()
            .username(listUser.get(0).getUSERNAME())
            .password(listUser.get(0).getPassword())
            .roles("USER")
            .build();
        System.out.println(listUser.get(0).getUSERNAME());
        System.out.println(listUser.get(0).getPassword());

        UserDetails user17 = User.withDefaultPasswordEncoder()
                .username("user17")
                .password("user17")
                .roles("USER")
                .build();


        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();


    return new InMemoryUserDetailsManager(user1,admin,user17);
}

*/
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(datasource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/addAddress").hasRole("ADMIN")
                .antMatchers("/editAddress/{N}").hasRole("ADMIN")
                .antMatchers("/addAnimal").hasRole("ADMIN")
                .antMatchers("/editAnimal/{N}").hasRole("ADMIN")
                .antMatchers("/addPerson").hasRole("ADMIN")
                .antMatchers("/editPerson/{N}").hasRole("ADMIN")
                .antMatchers("/deletePerson").hasRole("ADMIN")
                .antMatchers("/deleteAnimal").hasRole("ADMIN")
                .antMatchers("/deleteAddress").hasRole("ADMIN")
                .antMatchers("/saveEditAnimal").hasRole("ADMIN")
                .antMatchers("/saveEditAddress").hasRole("ADMIN")
                .antMatchers("/saveEditPerson").hasRole("ADMIN")
                .antMatchers("/saveDeleteAddress").hasRole("ADMIN")
                .antMatchers("/saveDeleteAnimal").hasRole("ADMIN")
                .antMatchers("/saveDeletePerson").hasRole("ADMIN")
                .antMatchers("/saveDeleteAddress/{N}").hasRole("ADMIN")
                .antMatchers("/saveDeleteAnimal/{N}").hasRole("ADMIN")
                .antMatchers("/saveDeletePerson/{N}").hasRole("ADMIN")
                .and()
                .formLogin().permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/HomePage");





    }
}
