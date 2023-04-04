# payment-hub-ee
Payment Hub Enterprise Edition middleware for integration to real-time payment systems. 

# How to enable JWSWebInterceptor
Step1: Create a custom class overriding the `WebMvcConfigurationSupport`, this will help us to add the interceptor in the WebMvcController.
Step2: Annotate the above created class with `@EnableJsonWebSignature` annotation, this will inject the necessary components for interceptor to work.
Step3: At last you can directly inject the instance of `WebSignatureInterceptor` class using `@Autowire`.

PS: All the dependency injection is taken care by the custom annotation so no need to define any custom object.

```java
@EnableJsonWebSignature
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private WebSignatureInterceptor webSignatureInterceptor;
    
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webSignatureInterceptor);
        super.addInterceptors(registry);
    }
}
```
