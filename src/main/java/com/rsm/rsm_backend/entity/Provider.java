package com.rsm.rsm_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@Builder
@Entity
@Table(name = "provider")
public class Provider implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Builder.Default
    @Column(name = "first_name")
    private String firstName = "first_name";

    @Builder.Default
    @Column(name = "last_name")
    private String lastName = "last_name";

    @Builder.Default
    @Column(name = "address")
    private String address = "address";

    @Builder.Default
    @Column(name = "login")
    private String login = "login";

    @Builder.Default
    @Column(name = "password")
    private String password = "password";

    @Builder.Default
    @Column(name = "e_mail", unique = true)
    private String email = "email@gmail.com";

    @Builder.Default
    @Column(name = "phone_number")
    private String phoneNumber = "phone_number";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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
