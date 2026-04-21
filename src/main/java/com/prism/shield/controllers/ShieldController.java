package com.prism.shield.controllers;

import com.prism.shield.entities.PrivacyResponse;
import com.prism.shield.entities.ShieldRequest;
import com.prism.shield.services.PrivacyService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/shield")
public class ShieldController {

    private final PrivacyService privacyService;

    public ShieldController(PrivacyService privacyService) {
        this.privacyService = privacyService;
    }

    @PostMapping("/analyze")
    public PrivacyResponse analyze(@RequestBody ShieldRequest request) {
        System.out.println("Processing request on: " + Thread.currentThread());
        return privacyService.checkPrivacy(request.userInput());
    }
}