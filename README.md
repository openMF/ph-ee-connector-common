# payment-hub-ee
Payment Hub Enterprise Edition middleware for integration to real-time payment systems. 

# How to enable JWSWebInterceptor
Step1: Add `@EnableJsonWebSignature` annotation to the main application class of your microservice.
Step2: Use below mention configuration in application.yaml to disable or enable the JsonWebSignatureInterceptor.

```yaml
security:
  jws:
    enable: true
```
