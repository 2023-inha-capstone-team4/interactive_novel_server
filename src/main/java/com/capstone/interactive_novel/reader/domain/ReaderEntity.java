package com.capstone.interactive_novel.reader.domain;

import com.capstone.interactive_novel.common.type.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.capstone.interactive_novel.common.type.Role.JUNIOR;

@Entity(name = "READER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReaderEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name", columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "profile_img")
    private String profileImgUrl;

    @Column(name = "interlock")
    private String interlock;

    @Column(name = "email_auth_key")
    private String emailAuthKey;

    @Column(name = "email_auth_yn")
    private boolean emailAuthYn;

    @Column(name = "author_service_yn")
    private boolean authorServiceYn;

    public ReaderEntity update(String name) {
        this.userName = name;

        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add(this.getRole().getKey());
        if(this.isAuthorServiceYn()) {
            roles.add(JUNIOR.getKey());
        }
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
