package com.minorProject.libraryManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity     //----Since we dont wanna create new table User
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @OneToOne(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Student student;

    @OneToOne(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Admin admin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Value("{$authorities.delimiter}")
//    private String delimiter;

    private static final String delimiter = ":";

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        String[] authority_list = this.authorities.split(delimiter);

        return Arrays.stream(authority_list)
                .map(x -> new SimpleGrantedAuthority(x))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
