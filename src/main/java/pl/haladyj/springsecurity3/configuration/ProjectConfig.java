package pl.haladyj.springsecurity3.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    //extend niezbędne by móc skonfigurować ruch do contollera inny niż GET

    @Bean
    public JdbcUserDetailsManager userDetailsService(){
        return new JdbcUserDetailsManager(dataSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource(){
        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/hello_test3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.csrf().disable();
        //niebędne do użycia POST w controller

        http.authorizeRequests()
                .mvcMatchers("/user").permitAll()
                .anyRequest().authenticated();
        //niezbędne do przepuszczenia ruchu na /user bez autentykacji
    }
}
