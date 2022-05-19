package com.minorProject.libraryManagement;

import com.minorProject.libraryManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonConfig extends WebSecurityConfigurerAdapter {
    @Value("${ADMIN_AUTHORITY}")
    private String ADMIN_AUTHORITY;

    @Value("${BOOK_INFO_AUTHORITY}")
    private String BOOK_INFO_AUTHORITY;

    @Value("${STUDENT_INFO_AUTHORITY}")
    private String STUDENT_INFO_AUTHORITY;

    @Value("{STUDENT_ONLY_AUTHORITY}")
    private String STUDENT_ONLY_AUTHORITY;

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Code to define storage of credentials in database

        auth.userDetailsService(userService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //We can define our authorization rules
        http
                .httpBasic() //Added to get the response instead of html response when trying postman
                .and()
                .authorizeHttpRequests()
                .antMatchers("/admin/**").hasAuthority(ADMIN_AUTHORITY)
                .antMatchers(HttpMethod.GET,"/book/**").hasAuthority(BOOK_INFO_AUTHORITY)
                .antMatchers(HttpMethod.POST, "/book/**").hasAuthority(ADMIN_AUTHORITY)
                .antMatchers("studentById/**").hasAuthority(STUDENT_INFO_AUTHORITY)  //Can implement GET, PUT, DELETE operations
                .antMatchers(HttpMethod.POST,"/student/**").permitAll()
                .antMatchers("/user/**").hasAuthority(STUDENT_ONLY_AUTHORITY)
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();
    }


    //    Mandatory bean required for the spring security to function(Will get "There is no PasswordEncoder mapped for the id "null"" error)
    @Bean
    public PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }


    public LettuceConnectionFactory getRedisFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                "redis-18706.c212.ap-south-1-1.ec2.cloud.redislabs.com", 18706);
        redisStandaloneConfiguration.setPassword("F1lu42k3nieD99ViIywMn51nynYLt95i");

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);

        lettuceConnectionFactory.afterPropertiesSet();

        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> getTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setConnectionFactory(getRedisFactory());

        return redisTemplate;
    }
}
