package com.github.practice.controller;

import com.github.practice.dto.LoginRequest;
import com.github.practice.dto.LogoutRequest;
import com.github.practice.dto.SignUpRequest;
import com.github.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest){
//        boolean isSuccess = userService.signUp(signUpRequest);
//        return isSuccess ? ResponseEntity.ok("회원가입이 완료되었습니다.") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 등록된 이메일입니다.");
//    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignUpRequest signUpRequest){
        boolean isSuccess = userService.signUp(signUpRequest);
        if (isSuccess) {
            return ResponseEntity.ok(Map.of("message", "회원가입이 완료되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "이미 등록된 이메일입니다."));
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws Exception {
//        String token = userService.login(loginRequest);
//
//        if (token != null) {
//            httpServletResponse.setHeader("Authorization", "Bearer " + token);
//            return ResponseEntity.ok("로그인이 성공적으로 완료되었습니다.");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
//        }
//
//
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws Exception {
        String token = userService.login(loginRequest);
        Map<String, String> response = new HashMap<>();

        if (token != null) {
            httpServletResponse.setHeader("Authorization", "Bearer " + token);
            response.put("message", "로그인이 성공적으로 완료되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }





    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
        boolean isLoggedOut = userService.logout(logoutRequest.getEmail());

        if (isLoggedOut) {
            return ResponseEntity.ok("로그아웃되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("로그아웃 실패");
        }
    }





}
