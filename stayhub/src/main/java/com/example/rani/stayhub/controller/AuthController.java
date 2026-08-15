package com.example.rani.stayhub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rani.stayhub.dto.LoginDto;
import com.example.rani.stayhub.dto.LoginResponseDto;
import com.example.rani.stayhub.dto.SignUpRequestDto;
import com.example.rani.stayhub.dto.UserDto;
import com.example.rani.stayhub.security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        return new ResponseEntity<>(authService.signUp(signUpRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       String[] tokens = authService.login(loginDto);

       Cookie cookie = new Cookie("refreshToken", tokens[1]);
       cookie.setHttpOnly(true);

       httpServletResponse.addCookie(cookie);
       return ResponseEntity.ok(new LoginResponseDto(tokens[0]));

    }
    

}
