package com.github.practice.service;

import com.github.practice.domain.Member;
import com.github.practice.dto.LoginRequest;
import com.github.practice.dto.SignUpRequest;
import com.github.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public boolean signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        // 이미 등록된 이메일인지 확인
        if(userRepository.existsByEmail(email)){
            return false; // 이미 등록된 이메일이라면 가입 실패
        };

//        Member userFound = userRepository.findByEmail(email)
//                .orElseGet(() -> userRepository.save(Member.builder()
//                        .email(email)
//                        .build()));

        // 패스워드 암호화
        String encryptedPassword = passwordEncoder.encode(password);

        // 회원 생성 및 저장
        Member newUser = Member.builder()
                .email(email)
                .password(encryptedPassword)
                .build();

        userRepository.save(newUser);
        return true; // 가입 성공


    }

    public String login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(password, member.getPassword())){
            // 비밀번호가 일치하는 경우, JWT 토큰 생성
            String token = jwtService.encode(member.getId());
            return token;
        } else {
            throw new RuntimeException("Invalid password");
        }


    }
}
