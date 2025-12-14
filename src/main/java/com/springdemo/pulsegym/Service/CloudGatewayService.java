package com.springdemo.pulsegym.Service;

import org.springframework.stereotype.Component;

@Component
public class CloudGatewayService {

    private static final String API_KEY = "PULSE-CLOUD-123";

    public void logRequest(String path) {
        System.out.println("🌩 [CloudGateway] Incoming request → " + path);
    }

    public void logResponse(String path) {
        System.out.println("🌩 [CloudGateway] Response sent for → " + path);
    }

    public void validateApiKey(String key) {
        if (key == null || !key.equals(API_KEY)) {
            throw new IllegalArgumentException("Invalid API Key. Access denied by Cloud Gateway.");
        }
    }

    public long startProcessing() {
        return System.currentTimeMillis();
    }

    public void endProcessing(long startTime, String path) {
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("⏱ [CloudGateway] Processing time for " + path + ": " + duration + " ms");
    }

    public Object wrapResponse(Object data, String message) {
        return new CloudResponse(message, data);
    }

    public static class CloudResponse {
        private String message;
        private Object data;

        public CloudResponse(String message, Object data) {
            this.message = message;
            this.data = data;
        }
        public String getMessage() { return message; }
        public Object getData() { return data; }
    }
}
