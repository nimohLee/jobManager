package com.nimoh.jobManager.data.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 사용자 및 시큐리티 관련 엔티티
 *
 * @author nimoh
 */
@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uid;

    private String name;

    private String password;

    private String email;

    public Long getId() {
        return id;
    }

    private String roles;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> result;
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (this.roles.length() > 0) {
            result = Arrays.asList(this.roles.split(","));
        } else result = new ArrayList<>();
        result.forEach(r->{
            authorities.add(()->r);
        });
        return authorities;
    }

    @Override
    public String getUsername() {
        return uid;
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
