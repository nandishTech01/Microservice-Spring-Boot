package com.aa.userservice.controller;


import com.aa.userservice.dto.request.LoginRequestDto;
import com.aa.userservice.dto.request.VerifyOtpRequestDto;
import com.aa.userservice.dto.response.ResponseDto;
import com.aa.userservice.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody LoginRequestDto dto) throws MessagingException {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto dto) throws MessagingException {
        return ResponseEntity.ok(userService.login(dto));
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<ResponseDto> verifyOtp(@RequestBody VerifyOtpRequestDto dto) throws MessagingException {
        return ResponseEntity.ok(userService.verifyOtp(dto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDto> refreshAccessToken(@RequestParam String token){
        return ResponseEntity.ok(userService.refreshAccessToken(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@RequestParam String email) {
        return ResponseEntity.ok(userService.logout(email));
    }

}

