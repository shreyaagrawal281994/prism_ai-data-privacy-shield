package com.prism.shield.entities;

import java.util.List;

public record PrivacyResponse(List<String> foundPII, String status, String redactedText) {}