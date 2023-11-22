# payment-hub-ee
Payment Hub Enterprise Edition middleware for integration to real-time payment systems.

# How to enable JWSWebInterceptor
Step1: Add `@EnableJsonWebSignature` annotation to the main application class of your microservice.
Step2: Use below mention configuration in application.yaml to disable or enable the JsonWebSignatureInterceptor.

```yaml
security:
  jws:
    enable: true
    response:
      enable: true
```

# How to generate signature

```java
JsonWebSignature jwsSignature = new JsonWebSignature.JsonWebSignatureBuilder()
.setClientCorrelationId(UUID.randomUUID().toString())
.setTenantId("rhino").setData("/src/main/resources/demo.csv").build();

String signature = jwsSignature.getSignature("<private-key>");
```


# Checkstyle
Use below command to execute the checkstyle test.
```shell
./gradlew checkstyleMain
```

## Spotless
Use below command to execute the spotless apply.
```shell
./gradlew spotlessApply
```
