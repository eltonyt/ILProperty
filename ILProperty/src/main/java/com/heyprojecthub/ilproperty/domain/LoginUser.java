package com.heyprojecthub.ilproperty.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = 123456L;

    private User user;

    private List<String> permission;

    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> grantedAuthorities;

    public LoginUser(User user, List<String> permission) {
        this.user = user;
        this.permission = permission;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (grantedAuthorities != null)
            return grantedAuthorities;
        grantedAuthorities = permission
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
