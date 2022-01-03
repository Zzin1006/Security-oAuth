package com.cos.Security.auth;


// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어줍니다. (Security ContextHolder)
// 시큐리티에 들어갈 수 있는 object가 정해져있는데 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 함
// User오브젝트의 타입은 => UserDetails 타입 객체로 정해져있음

// Security Session => Authentication => UserDetails --> UserDetails를 꺼내면 UserObject 접근가능 , implements UserDetails

// Security Session => Authentication => UserDetails(PrincipalDetails)


import com.cos.Security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 나중에 new해서 띄울 것
public class PrincipalDetails implements UserDetails {


    private User user; // 콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
    }
    

    //해당 User의 권한을 리턴하는 곳! user.getRole();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    // password리턴
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 기간
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정활성화 
    @Override
    public boolean isEnabled() {
        // false하는경우, 사이트 1년동안 회원이 로그인안하면 휴면계정으로하기 -> Usermodel에 loginDate가 있어야함
        // user.getLoginDate(); 들고와서 현재시간 - 로그인시간 => 1년 초과하면 return false;

        return true;
    }
    
}
