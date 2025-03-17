package com.coopbank.useraccessservice.controllers;

import com.coopbank.useraccessservice.dtos.BankUserDTO;
import com.coopbank.useraccessservice.exceptions.ItemNotFoundException;
import com.coopbank.useraccessservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<BankUserDTO>> getAllUsers(
            @RequestParam(required = false) String status) {

    }

    @PostMapping
    public ResponseEntity<?> createBankUser(@RequestBody BankUserDTO data) {
        try {
            String message = userService.createBankUserTemp(data);
            return ResponseEntity.ok().body(Collections
                    .singletonMap("message", message));

        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(404).body(
                    Collections.singletonMap("error", e.getMessage()));
        }
    }

}
