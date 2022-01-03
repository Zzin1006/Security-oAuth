package com.cos.Security.controller;

import com.cos.Security.model.User;
import com.cos.Security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴하겠다는 것 
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"})
    public String index() {
        // jsp 대신 머스테치 사용할 것 , spring이 권장하는 것 - 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates(prefix) .mustache(suffix) -> yml에서 생략해도됨
        // dependency에 mustache 사용하겠다고 의존성 등록하면 자동경로잡힘
        return "index"; // index는 View가 됨 -> src/main/resources/templates/index.mustache 찾음
    }

    @GetMapping("/user")
    public @ResponseBody String user() { // user가 page니까 @Responsebody , 안붙히면 page만들어야함
        return "user";
    }


    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // /login 하면 스프링 시큐리티가 해당주소를 낚아챔 -> SecurityConfig 파일 생성 후 작동안함.
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping ("/join")
    public String join(User user) { //joinFrom 안에 action적어둠,입력받은것 User user로 받기
        System.out.println(user);
        user.setRole("ROLE_USER");

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 회원가입 잘됨, 비밀번호 : 1234 => 시큐리티로 로그인을 할 수 없음, 이유는 패스워드가 암호화가 안되어있어서
        return "redirect:/loginForm"; // redirect붙히면 /loginForm함수를 호출함
    }

    @Secured("ROLE_ADMIN") // 특정 메서드에 간단하게 걸고싶을 때 , 아닌거는 global로
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    //@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN)") // 데이터라는 메서드가 실행되기 직전에 실행되는데 , 여러개 걸고 싶을 때 hasRole문법

    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }

}
