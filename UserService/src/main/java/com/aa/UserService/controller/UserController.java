package com.aa.UserService.controller;

import com.aa.UserService.dto.request.LoginRequestDto;
import com.aa.UserService.dto.response.ResponseDto;
import com.aa.UserService.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    private UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto dto) throws MessagingException {
        return ResponseEntity.ok(userService.login(dto));
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
