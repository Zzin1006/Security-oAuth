package com.cos.Security.auth;

import com.cos.Security.model.User;
import com.cos.Security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 PrincipalDetailsService찾아서 loadUserByUsername 함수가 실행

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session내부(Authentication(내부UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username); // 기본적인 CRUD만 들고있어서 findByUsername는 userRepository에 만들어야함
        if(userEntity != null) {
            return new PrincipalDetails(userEntity); // PrincipalDetails(UserDetails) 리턴될 때 session안에 Authentication만들어지면서 자동으로 들어감
        }
        return null;
    }
}
