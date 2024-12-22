package com.aa.userservice.controller;


import com.aa.userservice.dto.request.*;
import com.aa.userservice.dto.response.ResponseDto;
import com.aa.userservice.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody ChangePasswordRequestDto dto) {
        return ResponseEntity.ok(userService.changePassword(dto));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ResponseDto> forgotPassword(@RequestBody ForgotPasswordRequestDto dto) throws MessagingException {
        return ResponseEntity.ok(userService.forgotPassword(dto));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDto> refreshAccessToken(@RequestBody RefreshTokenRequestDto dto){
        return ResponseEntity.ok(userService.refreshAccessToken(dto));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<ResponseDto> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(userService.validateToken(token));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto> logout(@RequestParam String email) {
        return ResponseEntity.ok(userService.logout(email));
    }

}

