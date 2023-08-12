package com.github.practice.controller;

import com.github.practice.dto.LoginRequest;
import com.github.practice.dto.SignUpRequest;
import com.github.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest){
        boolean isSuccess = userService.signUp(signUpRequest);
        return isSuccess ? ResponseEntity.ok("회원가입이 완료되었습니다.") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 등록된 이메일입니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws Exception {
        String token = userService.login(loginRequest);

        if (token != null) {
            httpServletResponse.setHeader("Authorization", "Bearer " + token);
            return ResponseEntity.ok("로그인이 성공적으로 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }


    }


}
