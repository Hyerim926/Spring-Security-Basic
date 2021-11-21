package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //View 리턴
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //localhost:8080/
    //localhost:8080
    @GetMapping({"", "/"})
    public String index() {
        //머스테치 : 기본폴더 src/main/resources/
        //뷰리졸버 설정 : template(prefix), .mustache(suffix) - 생략가능
        return "index"; //src/main/resource/templates/index.mustache
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
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

    //스프링 시큐리티가 해당 주소를 낚아챔 - SecurityConfig 파일로 인해 작동안함
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword); //인코딩된 암호가 저장될 수 있도록

        userRepository.save(user); //회원가입 잘됨 그러나, 비밀번호가 1234 => 시큐리티 로그인 불가. 이유는 패스워드 암호화가 안되었기 때문
        return "redirect:/loginForm";
    }

}
