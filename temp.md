# URL Shortener - Comprehensive FAQ
## Java, Spring Boot & Angular - From Basics to Advanced

---

## Table of Contents
1. [Java Basics](#java-basics)
2. [Spring Boot Fundamentals](#spring-boot-fundamentals)
3. [Spring Security & JWT](#spring-security--jwt)
4. [Angular Basics](#angular-basics)
5. [Angular Advanced](#angular-advanced)
6. [URL Shortener Project Specific](#url-shortener-project-specific)
7. [Architecture & Design Patterns](#architecture--design-patterns)
8. [Database & JPA](#database--jpa)
9. [REST API Design](#rest-api-design)
10. [Performance & Optimization](#performance--optimization)
11. [Testing](#testing)
12. [Deployment & DevOps](#deployment--devops)

---

## Java Basics

### Q1: What is Java and why is it used for backend development?

**Answer:** Java is an object-oriented, platform-independent programming language. It's used for backend development because:
- **Platform Independence**: Write once, run anywhere (WORA)
- **Strong Type System**: Catches errors at compile time
- **Rich Ecosystem**: Extensive libraries and frameworks
- **Performance**: JVM optimizations and garbage collection
- **Enterprise Support**: Widely adopted in enterprise applications

```java
// Basic Java Class Example
public class UrlShortener {
    private String originalUrl;
    private String shortCode;
    
    // Constructor
    public UrlShortener(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
    }
    
    // Getter methods
    public String getOriginalUrl() {
        return originalUrl;
    }
    
    public String getShortCode() {
        return shortCode;
    }
}
```

### Q2: What are the main OOP principles in Java?

**Answer:** The four main OOP principles are:

1. **Encapsulation**: Hiding internal state and requiring interaction through methods
2. **Inheritance**: Creating new classes from existing ones
3. **Polymorphism**: Objects taking multiple forms
4. **Abstraction**: Hiding complex implementation details

```java
// Encapsulation Example
public class Url {
    private String originalUrl;  // Private field
    private int clickCount;
    
    // Public methods to access private data
    public void incrementClick() {
        this.clickCount++;
    }
    
    public int getClickCount() {
        return clickCount;
    }
}

// Inheritance Example
public class PremiumUrl extends Url {
    private boolean analyticsEnabled;
    
    public void enableAnalytics() {
        this.analyticsEnabled = true;
    }
}

// Polymorphism Example
public interface UrlValidator {
    boolean validate(String url);
}

public class HttpUrlValidator implements UrlValidator {
    @Override
    public boolean validate(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}

public class CustomUrlValidator implements UrlValidator {
    @Override
    public boolean validate(String url) {
        // Custom validation logic
        return url.matches("^[a-zA-Z0-9-_]+$");
    }
}
```

### Q3: What are Java Collections and which ones are commonly used?

**Answer:** Java Collections Framework provides data structures to store and manipulate groups of objects.

```java
import java.util.*;

public class CollectionsExample {
    
    // List - Ordered collection, allows duplicates
    public void listExample() {
        List<String> urls = new ArrayList<>();
        urls.add("https://google.com");
        urls.add("https://github.com");
        
        // Iterate
        for (String url : urls) {
            System.out.println(url);
        }
    }
    
    // Set - No duplicates allowed
    public void setExample() {
        Set<String> uniqueUrls = new HashSet<>();
        uniqueUrls.add("https://google.com");
        uniqueUrls.add("https://google.com"); // Won't be added (duplicate)
    }
    
    // Map - Key-value pairs
    public void mapExample() {
        Map<String, String> urlMappings = new HashMap<>();
        urlMappings.put("abc123", "https://google.com");
        urlMappings.put("xyz789", "https://github.com");
        
        // Get value by key
        String originalUrl = urlMappings.get("abc123");
    }
    
    // Queue - FIFO data structure
    public void queueExample() {
        Queue<String> urlQueue = new LinkedList<>();
        urlQueue.offer("https://google.com");
        urlQueue.offer("https://github.com");
        
        String first = urlQueue.poll(); // Removes and returns first element
    }
}
```

### Q4: What is the difference between == and .equals() in Java?

**Answer:**
- `==` compares **reference** (memory address)
- `.equals()` compares **content** (actual values)

```java
public class ComparisonExample {
    public static void main(String[] args) {
        // Primitive types - == compares values
        int a = 5;
        int b = 5;
        System.out.println(a == b); // true
        
        // Objects - == compares references
        String url1 = new String("https://google.com");
        String url2 = new String("https://google.com");
        System.out.println(url1 == url2);        // false (different objects)
        System.out.println(url1.equals(url2));   // true (same content)
        
        // String literals - stored in String pool
        String url3 = "https://google.com";
        String url4 = "https://google.com";
        System.out.println(url3 == url4);        // true (same reference in pool)
    }
}
```

### Q5: What are Java Streams and how do they work?

**Answer:** Streams provide a functional approach to processing collections of objects.

```java
import java.util.*;
import java.util.stream.*;

public class StreamExample {
    
    public List<String> filterAndProcessUrls(List<String> urls) {
        return urls.stream()
            .filter(url -> url.startsWith("https://"))  // Filter
            .map(String::toLowerCase)                    // Transform
            .sorted()                                    // Sort
            .distinct()                                  // Remove duplicates
            .collect(Collectors.toList());              // Collect results
    }
    
    // Example: Count URLs by domain
    public Map<String, Long> countUrlsByDomain(List<String> urls) {
        return urls.stream()
            .map(url -> url.split("/")[2])  // Extract domain
            .collect(Collectors.groupingBy(
                domain -> domain,
                Collectors.counting()
            ));
    }
    
    // Example: Find total clicks
    public int getTotalClicks(List<ShortenedUrl> urls) {
        return urls.stream()
            .mapToInt(ShortenedUrl::getClickCount)
            .sum();
    }
    
    // Example: Get top 5 most clicked URLs
    public List<ShortenedUrl> getTopUrls(List<ShortenedUrl> urls) {
        return urls.stream()
            .sorted(Comparator.comparingInt(ShortenedUrl::getClickCount).reversed())
            .limit(5)
            .collect(Collectors.toList());
    }
}
```

---

## Spring Boot Fundamentals

### Q6: What is Spring Boot and how is it different from Spring Framework?

**Answer:**
- **Spring Framework**: Comprehensive framework requiring extensive configuration
- **Spring Boot**: Opinionated, convention-over-configuration framework built on top of Spring

**Key Differences:**
1. **Auto-configuration**: Spring Boot automatically configures beans
2. **Embedded Server**: Built-in Tomcat/Jetty/Undertow
3. **Starter Dependencies**: Pre-packaged dependency bundles
4. **Production-ready**: Built-in health checks, metrics, externalized configuration

```java
// Traditional Spring - Lots of configuration
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.urlshortener")
public class WebConfig extends WebMvcConfigurerAdapter {
    // Manual bean configuration
}

// Spring Boot - Minimal configuration
@SpringBootApplication  // Combines @Configuration, @EnableAutoConfiguration, @ComponentScan
public class UrlShortenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}
```

### Q7: What are Spring Boot Annotations and their purposes?

**Answer:** Annotations provide metadata to Spring for configuration and dependency injection.

```java
// Component Annotations
@Controller         // Handles web requests, returns views
@RestController     // @Controller + @ResponseBody, returns JSON/XML
@Service            // Business logic layer
@Repository         // Data access layer
@Component          // Generic Spring-managed component

// Configuration Annotations
@Configuration      // Defines configuration class
@Bean               // Declares a bean to be managed by Spring
@Value              // Injects property values
@ConfigurationProperties  // Binds properties to POJO

// Request Mapping
@RestController
@RequestMapping("/api/urls")
public class UrlController {
    
    @GetMapping("/{shortCode}")  // GET request
    public ResponseEntity<UrlDto> getUrl(@PathVariable String shortCode) {
        // ...
    }
    
    @PostMapping  // POST request
    public ResponseEntity<UrlDto> createUrl(@RequestBody @Valid UrlRequest request) {
        // ...
    }
    
    @PutMapping("/{id}")  // PUT request
    public ResponseEntity<UrlDto> updateUrl(
        @PathVariable Long id,
        @RequestBody UrlRequest request
    ) {
        // ...
    }
    
    @DeleteMapping("/{id}")  // DELETE request
    public ResponseEntity<Void> deleteUrl(@PathVariable Long id) {
        // ...
    }
}

// Dependency Injection
@Service
public class UrlService {
    
    @Autowired  // Constructor injection (preferred)
    private final UrlRepository urlRepository;
    
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
}
```

### Q8: What is Dependency Injection and Inversion of Control?

**Answer:**
- **Dependency Injection (DI)**: Design pattern where objects receive dependencies rather than creating them
- **Inversion of Control (IoC)**: Framework controls object creation and lifecycle

```java
// Without DI - Tight coupling
public class UrlService {
    private UrlRepository repository = new UrlRepositoryImpl();  // Bad: creates own dependency
}

// With DI - Loose coupling
@Service
public class UrlService {
    private final UrlRepository repository;
    
    @Autowired  // Spring injects dependency
    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }
}

// Types of Dependency Injection

// 1. Constructor Injection (Recommended)
@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    
    public UrlService(UrlRepository urlRepository, UserRepository userRepository) {
        this.urlRepository = urlRepository;
        this.userRepository = userRepository;
    }
}

// 2. Setter Injection
@Service
public class UrlService {
    private UrlRepository urlRepository;
    
    @Autowired
    public void setUrlRepository(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
}

// 3. Field Injection (Not recommended - harder to test)
@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;
}
```

### Q9: How does Spring Boot application.properties/application.yml work?

**Answer:** Configuration files for externalizing application settings.

```properties
# application.properties

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/urlshortener
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Configuration
jwt.secret=mySecretKey123456789012345678901234567890
jwt.expiration=86400000

# Logging
logging.level.root=INFO
logging.level.com.urlshortener=DEBUG

# Custom Properties
app.url.base=http://localhost:8080
app.url.length=6
```

```yaml
# application.yml (Alternative YAML format)

server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/urlshortener
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect

jwt:
  secret: mySecretKey123456789012345678901234567890
  expiration: 86400000

app:
  url:
    base: http://localhost:8080
    length: 6

logging:
  level:
    root: INFO
    com.urlshortener: DEBUG
```

```java
// Using @Value to inject properties
@Service
public class UrlService {
    
    @Value("${app.url.base}")
    private String baseUrl;
    
    @Value("${app.url.length}")
    private int shortCodeLength;
}

// Using @ConfigurationProperties for type-safe configuration
@Configuration
@ConfigurationProperties(prefix = "app.url")
public class UrlProperties {
    private String base;
    private int length;
    
    // Getters and setters
    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }
    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }
}

@Service
public class UrlService {
    private final UrlProperties urlProperties;
    
    public UrlService(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
    }
    
    public String generateShortUrl(String code) {
        return urlProperties.getBase() + "/" + code;
    }
}
```

### Q10: What is the Spring Boot application structure and layered architecture?

**Answer:** Spring Boot follows a layered architecture for separation of concerns.

```
src/main/java/com/urlshortener/
├── UrlShortenerApplication.java          # Main application class
├── config/                                # Configuration classes
│   ├── SecurityConfig.java
│   ├── CorsConfig.java
│   └── SwaggerConfig.java
├── controller/                            # REST controllers (Presentation layer)
│   ├── AuthController.java
│   └── UrlController.java
├── service/                               # Business logic layer
│   ├── AuthService.java
│   ├── UrlService.java
│   └── impl/
│       ├── AuthServiceImpl.java
│       └── UrlServiceImpl.java
├── repository/                            # Data access layer
│   ├── UserRepository.java
│   └── UrlRepository.java
├── model/                                 # Entity classes
│   ├── User.java
│   └── ShortenedUrl.java
├── dto/                                   # Data Transfer Objects
│   ├── request/
│   │   ├── LoginRequest.java
│   │   ├── SignupRequest.java
│   │   └── UrlRequest.java
│   └── response/
│       ├── AuthResponse.java
│       └── UrlResponse.java
├── security/                              # Security components
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── UserDetailsServiceImpl.java
├── exception/                             # Custom exceptions
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── BadRequestException.java
└── util/                                  # Utility classes
    ├── ShortCodeGenerator.java
    └── UrlValidator.java
```

```java
// Layer Example: Controller -> Service -> Repository

// 1. Controller Layer (Handles HTTP requests)
@RestController
@RequestMapping("/api/urls")
public class UrlController {
    private final UrlService urlService;
    
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    
    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(@Valid @RequestBody UrlRequest request) {
        UrlResponse response = urlService.createShortUrl(request);
        return ResponseEntity.ok(response);
    }
}

// 2. Service Layer (Business logic)
@Service
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private final ShortCodeGenerator codeGenerator;
    
    public UrlServiceImpl(UrlRepository urlRepository, ShortCodeGenerator codeGenerator) {
        this.urlRepository = urlRepository;
        this.codeGenerator = codeGenerator;
    }
    
    @Override
    public UrlResponse createShortUrl(UrlRequest request) {
        String shortCode = codeGenerator.generate();
        
        ShortenedUrl url = new ShortenedUrl();
        url.setOriginalUrl(request.getOriginalUrl());
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        
        ShortenedUrl savedUrl = urlRepository.save(url);
        
        return mapToResponse(savedUrl);
    }
}

// 3. Repository Layer (Data access)
@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> {
    Optional<ShortenedUrl> findByShortCode(String shortCode);
    List<ShortenedUrl> findByUserId(Long userId);
}
```

---

## Spring Security & JWT

### Q11: What is Spring Security and how does it work?

**Answer:** Spring Security is a framework that provides authentication, authorization, and protection against common attacks.

**Core Concepts:**
1. **Authentication**: Verifying who you are
2. **Authorization**: Verifying what you can access
3. **Principal**: Currently authenticated user
4. **Granted Authority**: Permissions/roles

```java
// Security Configuration
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, 
                         AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()  // Public endpoints
                .requestMatchers("/{shortCode}").permitAll()  // Redirect endpoint
                .anyRequest().authenticated()                  // All other endpoints require auth
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No sessions
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
            throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
```

### Q12: How does JWT (JSON Web Token) authentication work?

**Answer:** JWT is a stateless authentication mechanism using digitally signed tokens.

**JWT Structure:**
- **Header**: Algorithm and token type
- **Payload**: Claims (user data)
- **Signature**: Verification signature

```java
// JWT Token Provider
@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    // Generate JWT Token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    // Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    // Extract specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    // Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    // Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

// JWT Authentication Filter
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        // Extract JWT from Authorization header
        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String jwt = authHeader.substring(7);
        final String username = jwtTokenProvider.extractUsername(jwt);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (jwtTokenProvider.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
```

### Q13: How to implement user registration and login with Spring Security?

**Answer:** Implement authentication endpoints with password encoding and token generation.

```java
// User Entity
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShortenedUrl> urls = new ArrayList<>();
    
    // Getters and setters
}

// Role Enum
public enum Role {
    USER,
    ADMIN
}

// UserDetailsService Implementation
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities(user.getRole().name())
            .build();
    }
}

// Authentication Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

// Authentication Service
@Service
public class AuthServiceImpl implements AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    
    public AuthServiceImpl(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public AuthResponse signup(SignupRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        
        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        
        User savedUser = userRepository.save(user);
        
        // Generate JWT token
        UserDetails userDetails = User.builder()
            .username(savedUser.getEmail())
            .password(savedUser.getPassword())
            .authorities(savedUser.getRole().name())
            .build();
        
        String token = jwtTokenProvider.generateToken(userDetails);
        
        return new AuthResponse(token, savedUser.getEmail(), savedUser.getName());
    }
    
    @Override
    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        
        // Get user details
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Generate JWT token
        UserDetails userDetails = User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities(user.getRole().name())
            .build();
        
        String token = jwtTokenProvider.generateToken(userDetails);
        
        return new AuthResponse(token, user.getEmail(), user.getName());
    }
}

// DTOs
public class SignupRequest {
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6)
    private String password;
    
    @NotBlank
    private String name;
    
    // Getters and setters
}

public class LoginRequest {
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;
    
    // Getters and setters
}

public class AuthResponse {
    private String token;
    private String email;
    private String name;
    
    // Constructor, getters and setters
}
```

---

## Angular Basics

### Q14: What is Angular and what are its core concepts?

**Answer:** Angular is a TypeScript-based framework for building single-page applications.

**Core Concepts:**
1. **Components**: Building blocks of UI
2. **Modules**: Organize related components
3. **Services**: Shareable business logic
4. **Dependency Injection**: Manage dependencies
5. **Directives**: Extend HTML behavior
6. **Pipes**: Transform data in templates
7. **Routing**: Navigation between views

```typescript
// 1. Component
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-url-shortener',
  templateUrl: './url-shortener.component.html',
  styleUrls: ['./url-shortener.component.css']
})
export class UrlShortenerComponent implements OnInit {
  title = 'URL Shortener';
  urls: any[] = [];
  
  constructor(private urlService: UrlService) {}
  
  ngOnInit(): void {
    this.loadUrls();
  }
  
  loadUrls(): void {
    this.urlService.getUrls().subscribe(
      data => this.urls = data
    );
  }
}

// 2. Module
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UrlShortenerComponent } from './components/url-shortener/url-shortener.component';

@NgModule({
  declarations: [
    AppComponent,
    UrlShortenerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

// 3. Service
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UrlService {
  private apiUrl = 'http://localhost:8080/api/urls';
  
  constructor(private http: HttpClient) {}
  
  getUrls(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  
  createUrl(data: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, data);
  }
}

// 4. Directive (Custom)
import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  constructor(private el: ElementRef) {}
  
  @HostListener('mouseenter') onMouseEnter() {
    this.highlight('yellow');
  }
  
  @HostListener('mouseleave') onMouseLeave() {
    this.highlight('');
  }
  
  private highlight(color: string) {
    this.el.nativeElement.style.backgroundColor = color;
  }
}

// 5. Pipe (Custom)
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'shortenUrl'
})
export class ShortenUrlPipe implements PipeTransform {
  transform(value: string, maxLength: number = 50): string {
    if (value.length <= maxLength) return value;
    return value.substring(0, maxLength) + '...';
  }
}

// Usage in template:
// <p>{{ longUrl | shortenUrl:30 }}</p>
```

### Q15: What is TypeScript and why does Angular use it?

**Answer:** TypeScript is a typed superset of JavaScript that compiles to plain JavaScript.

**Benefits:**
- Static typing catches errors at compile time
- Better IDE support (autocomplete, refactoring)
- Modern JavaScript features (ES6+)
- Object-oriented programming features

```typescript
// Basic Types
let urlCount: number = 0;
let isActive: boolean = true;
let userName: string = "John";
let tags: string[] = ["tech", "web"];
let data: any = { key: "value" };  // Avoid using 'any'

// Interfaces
interface ShortenedUrl {
  id: number;
  originalUrl: string;
  shortCode: string;
  clickCount: number;
  createdAt: Date;
}

interface UrlResponse {
  success: boolean;
  data: ShortenedUrl;
  message?: string;  // Optional property
}

// Classes
class UrlManager {
  private urls: ShortenedUrl[] = [];
  
  constructor(private baseUrl: string) {}
  
  addUrl(url: ShortenedUrl): void {
    this.urls.push(url);
  }
  
  getUrl(id: number): ShortenedUrl | undefined {
    return this.urls.find(url => url.id === id);
  }
  
  getTotalClicks(): number {
    return this.urls.reduce((total, url) => total + url.clickCount, 0);
  }
}

// Generics
interface ApiResponse<T> {
  success: boolean;
  data: T;
  error?: string;
}

function handleResponse<T>(response: ApiResponse<T>): T {
  if (response.success) {
    return response.data;
  }
  throw new Error(response.error || 'Unknown error');
}

// Usage
const urlResponse: ApiResponse<ShortenedUrl> = {
  success: true,
  data: {
    id: 1,
    originalUrl: "https://example.com",
    shortCode: "abc123",
    clickCount: 0,
    createdAt: new Date()
  }
};

const url = handleResponse(urlResponse);

// Enums
enum UserRole {
  ADMIN = 'ADMIN',
  USER = 'USER',
  GUEST = 'GUEST'
}

// Type Aliases
type UrlId = number | string;
type UrlCallback = (url: ShortenedUrl) => void;

// Union Types
function processUrl(url: string | ShortenedUrl): void {
  if (typeof url === 'string') {
    console.log('Processing URL string:', url);
  } else {
    console.log('Processing URL object:', url.originalUrl);
  }
}

// Async/Await with Promises
async function fetchUrls(): Promise<ShortenedUrl[]> {
  const response = await fetch('/api/urls');
  const data = await response.json();
  return data;
}
```

### Q16: How does Angular routing work?

**Answer:** Angular Router enables navigation between different views/components.

```typescript
// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';

import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UrlListComponent } from './components/url-list/url-list.component';
import { UrlDetailComponent } from './components/url-detail/url-detail.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    canActivate: [AuthGuard]  // Protected route
  },
  { 
    path: 'urls', 
    component: UrlListComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'urls/:id', 
    component: UrlDetailComponent,
    canActivate: [AuthGuard]
  },
  { path: '**', component: NotFoundComponent }  // 404 page
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

// Auth Guard
import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.authService.isAuthenticated()) {
      return true;
    }
    
    // Not logged in, redirect to login page
    this.router.navigate(['/login'], { 
      queryParams: { returnUrl: state.url } 
    });
    return false;
  }
}

// Component with routing
import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-url-detail',
  templateUrl: './url-detail.component.html'
})
export class UrlDetailComponent {
  urlId: string;
  
  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {
    // Get route parameter
    this.urlId = this.route.snapshot.paramMap.get('id')!;
    
    // Or subscribe to parameter changes
    this.route.paramMap.subscribe(params => {
      this.urlId = params.get('id')!;
    });
  }
  
  navigateToList(): void {
    this.router.navigate(['/urls']);
  }
  
  navigateWithQueryParams(): void {
    this.router.navigate(['/urls'], {
      queryParams: { page: 1, sort: 'date' }
    });
  }
}

// Template with router links
/*
<nav>
  <a routerLink="/">Home</a>
  <a routerLink="/urls" routerLinkActive="active">URLs</a>
  <a [routerLink]="['/urls', urlId]">URL Detail</a>
</nav>

<router-outlet></router-outlet>
*/
```

### Q17: What are Angular Forms (Template-driven vs Reactive)?

**Answer:** Angular provides two approaches to handle forms.

```typescript
// 1. TEMPLATE-DRIVEN FORMS
// Simple forms with two-way binding

// Component
import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  model = {
    email: '',
    password: ''
  };
  
  onSubmit(): void {
    console.log('Form submitted:', this.model);
  }
}

// Template (login.component.html)
/*
<form #loginForm="ngForm" (ngSubmit)="onSubmit()">
  <div>
    <label>Email:</label>
    <input 
      type="email" 
      name="email" 
      [(ngModel)]="model.email" 
      required 
      email
      #emailInput="ngModel">
    <div *ngIf="emailInput.invalid && emailInput.touched">
      <span *ngIf="emailInput.errors?.['required']">Email is required</span>
      <span *ngIf="emailInput.errors?.['email']">Invalid email format</span>
    </div>
  </div>
  
  <div>
    <label>Password:</label>
    <input 
      type="password" 
      name="password" 
      [(ngModel)]="model.password" 
      required 
      minlength="6"
      #passwordInput="ngModel">
    <div *ngIf="passwordInput.invalid && passwordInput.touched">
      <span *ngIf="passwordInput.errors?.['required']">Password is required</span>
      <span *ngIf="passwordInput.errors?.['minlength']">Password must be at least 6 characters</span>
    </div>
  </div>
  
  <button type="submit" [disabled]="loginForm.invalid">Login</button>
</form>
*/

// 2. REACTIVE FORMS
// Complex forms with better validation and testing

// Component
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UrlService } from '../../services/url.service';

@Component({
  selector: 'app-url-create',
  templateUrl: './url-create.component.html'
})
export class UrlCreateComponent implements OnInit {
  urlForm!: FormGroup;
  submitted = false;
  
  constructor(
    private fb: FormBuilder,
    private urlService: UrlService
  ) {}
  
  ngOnInit(): void {
    this.urlForm = this.fb.group({
      originalUrl: ['', [
        Validators.required,
        Validators.pattern(/^https?:\/\/.+/)
      ]],
      customAlias: ['', [
        Validators.minLength(3),
        Validators.pattern(/^[a-zA-Z0-9-_]+$/)
      ]],
      expiresIn: [30, [
        Validators.min(1),
        Validators.max(365)
      ]]
    });
  }
  
  // Getter for easy access to form fields
  get f() { return this.urlForm.controls; }
  
  onSubmit(): void {
    this.submitted = true;
    
    if (this.urlForm.invalid) {
      return;
    }
    
    this.urlService.createUrl(this.urlForm.value).subscribe(
      response => {
        console.log('URL created:', response);
        this.urlForm.reset();
        this.submitted = false;
      },
      error => {
        console.error('Error:', error);
      }
    );
  }
  
  // Custom validator example
  static urlValidator(control: any): {[key: string]: any} | null {
    if (!control.value) return null;
    
    const urlPattern = /^https?:\/\/.+/;
    const valid = urlPattern.test(control.value);
    
    return valid ? null : { invalidUrl: true };
  }
}

// Template (url-create.component.html)
/*
<form [formGroup]="urlForm" (ngSubmit)="onSubmit()">
  <div>
    <label>Original URL:</label>
    <input 
      type="text" 
      formControlName="originalUrl"
      [class.is-invalid]="submitted && f['originalUrl'].errors">
    <div *ngIf="submitted && f['originalUrl'].errors">
      <span *ngIf="f['originalUrl'].errors['required']">URL is required</span>
      <span *ngIf="f['originalUrl'].errors['pattern']">Must start with http:// or https://</span>
    </div>
  </div>
  
  <div>
    <label>Custom Alias (optional):</label>
    <input 
      type="text" 
      formControlName="customAlias"
      [class.is-invalid]="submitted && f['customAlias'].errors">
    <div *ngIf="submitted && f['customAlias'].errors">
      <span *ngIf="f['customAlias'].errors['minlength']">Minimum 3 characters</span>
      <span *ngIf="f['customAlias'].errors['pattern']">Only letters, numbers, hyphens, and underscores</span>
    </div>
  </div>
  
  <div>
    <label>Expires in (days):</label>
    <input 
      type="number" 
      formControlName="expiresIn"
      [class.is-invalid]="submitted && f['expiresIn'].errors">
  </div>
  
  <button type="submit">Create Short URL</button>
</form>
*/

// FormArray example (for dynamic form fields)
import { FormArray } from '@angular/forms';

@Component({
  selector: 'app-bulk-url-creator',
  template: `
    <form [formGroup]="bulkForm" (ngSubmit)="onSubmit()">
      <div formArrayName="urls">
        <div *ngFor="let url of urls.controls; let i = index">
          <input [formControlName]="i" placeholder="URL {{i + 1}}">
          <button type="button" (click)="removeUrl(i)">Remove</button>
        </div>
      </div>
      <button type="button" (click)="addUrl()">Add URL</button>
      <button type="submit">Submit All</button>
    </form>
  `
})
export class BulkUrlCreatorComponent implements OnInit {
  bulkForm!: FormGroup;
  
  constructor(private fb: FormBuilder) {}
  
  ngOnInit(): void {
    this.bulkForm = this.fb.group({
      urls: this.fb.array([
        this.fb.control('', Validators.required)
      ])
    });
  }
  
  get urls(): FormArray {
    return this.bulkForm.get('urls') as FormArray;
  }
  
  addUrl(): void {
    this.urls.push(this.fb.control('', Validators.required));
  }
  
  removeUrl(index: number): void {
    this.urls.removeAt(index);
  }
  
  onSubmit(): void {
    console.log(this.bulkForm.value);
  }
}
```

---

## Angular Advanced

### Q18: How does HTTP communication work in Angular with HttpClient?

**Answer:** HttpClient module provides methods for making HTTP requests.

```typescript
// Service with HttpClient
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map, tap } from 'rxjs/operators';

interface ShortenedUrl {
  id: number;
  originalUrl: string;
  shortCode: string;
  clickCount: number;
}

interface ApiResponse<T> {
  success: boolean;
  data: T;
  message?: string;
}

@Injectable({
  providedIn: 'root'
})
export class UrlService {
  private apiUrl = 'http://localhost:8080/api/urls';
  
  constructor(private http: HttpClient) {}
  
  // GET request
  getAllUrls(): Observable<ShortenedUrl[]> {
    return this.http.get<ApiResponse<ShortenedUrl[]>>(this.apiUrl)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }
  
  // GET with parameters
  getUrlsByPage(page: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get(`${this.apiUrl}`, { params });
  }
  
  // GET single item
  getUrl(id: number): Observable<ShortenedUrl> {
    return this.http.get<ApiResponse<ShortenedUrl>>(`${this.apiUrl}/${id}`)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }
  
  // POST request
  createUrl(data: any): Observable<ShortenedUrl> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    
    return this.http.post<ApiResponse<ShortenedUrl>>(this.apiUrl, data, { headers })
      .pipe(
        map(response => response.data),
        retry(3),  // Retry failed request up to 3 times
        catchError(this.handleError)
      );
  }
  
  // PUT request
  updateUrl(id: number, data: any): Observable<ShortenedUrl> {
    return this.http.put<ApiResponse<ShortenedUrl>>(`${this.apiUrl}/${id}`, data)
      .pipe(
        map(response => response.data),
        catchError(this.handleError)
      );
  }
  
  // DELETE request
  deleteUrl(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }
  
  // Error handling
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An error occurred';
    
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
  
  // Using tap for side effects (logging, etc.)
  getUrlsWithLogging(): Observable<ShortenedUrl[]> {
    return this.http.get<ApiResponse<ShortenedUrl[]>>(this.apiUrl)
      .pipe(
        tap(response => console.log('Fetched URLs:', response.data)),
        map(response => response.data),
        catchError(this.handleError)
      );
  }
}

// HTTP Interceptor for adding JWT token
import { Injectable } from '@angular/core';
import { 
  HttpRequest, 
  HttpHandler, 
  HttpEvent, 
  HttpInterceptor,
  HttpErrorResponse 
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}
  
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Get token from localStorage
    const token = localStorage.getItem('token');
    
    // Clone request and add authorization header
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Unauthorized - redirect to login
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
}

// Register interceptor in app.module.ts
import { HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
  ]
})
export class AppModule { }
```

### Q19: What are RxJS Observables and how are they used in Angular?

**Answer:** RxJS (Reactive Extensions for JavaScript) provides reactive programming with Observables for handling asynchronous data streams.

```typescript
import { 
  Observable, 
  Subject, 
  BehaviorSubject, 
  ReplaySubject,
  interval,
  of,
  from,
  fromEvent
} from 'rxjs';
import { 
  map, 
  filter, 
  debounceTime, 
  distinctUntilChanged,
  switchMap,
  mergeMap,
  concatMap,
  catchError,
  takeUntil
} from 'rxjs/operators';

// 1. Basic Observable
function basicObservable() {
  const observable = new Observable(subscriber => {
    subscriber.next(1);
    subscriber.next(2);
    subscriber.next(3);
    subscriber.complete();
  });
  
  observable.subscribe({
    next: value => console.log(value),
    complete: () => console.log('Complete')
  });
}

// 2. Subject - Multicast observable
@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private messageSubject = new Subject<string>();
  
  // Observable that components can subscribe to
  message$ = this.messageSubject.asObservable();
  
  // Method to emit new messages
  sendMessage(message: string): void {
    this.messageSubject.next(message);
  }
}

// 3. BehaviorSubject - Stores current value
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();
  
  login(user: any): void {
    this.currentUserSubject.next(user);
  }
  
  logout(): void {
    this.currentUserSubject.next(null);
  }
  
  getCurrentUser(): any {
    return this.currentUserSubject.value;  // Get current value
  }
}

// 4. Operators - Transform and combine observables
@Component({
  selector: 'app-search',
  template: `
    <input type="text" (input)="onSearch($event)" placeholder="Search URLs...">
    <div *ngFor="let result of searchResults">{{ result }}</div>
  `
})
export class SearchComponent implements OnInit {
  searchResults: string[] = [];
  private searchSubject = new Subject<string>();
  
  constructor(private urlService: UrlService) {}
  
  ngOnInit(): void {
    // Debounce search input and make API call
    this.searchSubject.pipe(
      debounceTime(300),           // Wait 300ms after user stops typing
      distinctUntilChanged(),      // Only if search term changed
      switchMap(term => 
        this.urlService.searchUrls(term)  // Cancel previous request
      )
    ).subscribe(results => {
      this.searchResults = results;
    });
  }
  
  onSearch(event: any): void {
    this.searchSubject.next(event.target.value);
  }
}

// 5. Combining multiple observables
@Component({
  selector: 'app-dashboard',
  template: `<div>Dashboard loaded</div>`
})
export class DashboardComponent implements OnInit {
  constructor(
    private urlService: UrlService,
    private userService: UserService
  ) {}
  
  ngOnInit(): void {
    // forkJoin - Wait for all observables to complete
    forkJoin({
      urls: this.urlService.getAllUrls(),
      user: this.userService.getCurrentUser(),
      stats: this.urlService.getStats()
    }).subscribe(result => {
      console.log('All data loaded:', result);
    });
    
    // combineLatest - Emit when any observable emits
    combineLatest([
      this.urlService.getAllUrls(),
      this.userService.getCurrentUser()
    ]).subscribe(([urls, user]) => {
      console.log('URLs:', urls, 'User:', user);
    });
  }
}

// 6. Error handling and retry
@Injectable({
  providedIn: 'root'
})
export class UrlServiceWithRetry {
  constructor(private http: HttpClient) {}
  
  getUrls(): Observable<any[]> {
    return this.http.get<any[]>('/api/urls').pipe(
      retry(3),  // Retry 3 times on error
      catchError(error => {
        console.error('Error after retries:', error);
        return of([]);  // Return empty array as fallback
      })
    );
  }
}

// 7. Unsubscribing to prevent memory leaks
@Component({
  selector: 'app-url-list',
  template: `<div *ngFor="let url of urls">{{ url.originalUrl }}</div>`
})
export class UrlListComponent implements OnInit, OnDestroy {
  urls: any[] = [];
  private destroy$ = new Subject<void>();
  
  constructor(private urlService: UrlService) {}
  
  ngOnInit(): void {
    // Method 1: takeUntil pattern
    this.urlService.getAllUrls()
      .pipe(takeUntil(this.destroy$))
      .subscribe(urls => this.urls = urls);
    
    // Method 2: Store subscription and unsubscribe manually
    // this.subscription = this.urlService.getAllUrls().subscribe(...);
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    
    // Method 2: Manual unsubscribe
    // if (this.subscription) this.subscription.unsubscribe();
  }
}

// 8. Advanced operators
import { forkJoin, combineLatest } from 'rxjs';

export class AdvancedRxJSExamples {
  
  // switchMap - Cancel previous inner observable
  typeaheadSearch(searchTerm$: Observable<string>): Observable<any[]> {
    return searchTerm$.pipe(
      debounceTime(300),
      switchMap(term => this.http.get<any[]>(`/api/search?q=${term}`))
    );
  }
  
  // mergeMap - Don't cancel, run in parallel
  processUrls(urls: string[]): Observable<any> {
    return from(urls).pipe(
      mergeMap(url => this.http.post('/api/shorten', { url }))
    );
  }
  
  // concatMap - Process sequentially
  processUrlsSequentially(urls: string[]): Observable<any> {
    return from(urls).pipe(
      concatMap(url => this.http.post('/api/shorten', { url }))
    );
  }
  
  // scan - Accumulate values (like reduce)
  getTotalClicks(): Observable<number> {
    return interval(1000).pipe(
      switchMap(() => this.http.get<number>('/api/clicks')),
      scan((total, current) => total + current, 0)
    );
  }
}
```

### Q20: What is Angular Dependency Injection and how does it work?

**Answer:** DI is a design pattern where dependencies are provided to a class rather than creating them internally.

```typescript
// 1. Service with providedIn (Preferred - Tree-shakeable)
@Injectable({
  providedIn: 'root'  // Single instance across entire app
})
export class UrlService {
  private apiUrl = 'http://localhost:8080/api/urls';
  
  constructor(private http: HttpClient) {}
  
  getUrls(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}

// 2. Module-level provider
@NgModule({
  providers: [UrlService]  // Instance per module
})
export class UrlModule { }

// 3. Component-level provider
@Component({
  selector: 'app-url-list',
  templateUrl: './url-list.component.html',
  providers: [UrlService]  // New instance for this component and children
})
export class UrlListComponent { }

// 4. Injection tokens for non-class dependencies
import { InjectionToken } from '@angular/core';

export const API_URL = new InjectionToken<string>('API_URL');

@NgModule({
  providers: [
    { provide: API_URL, useValue: 'http://localhost:8080/api' }
  ]
})
export class AppModule { }

// Usage
@Injectable({
  providedIn: 'root'
})
export class UrlService {
  constructor(@Inject(API_URL) private apiUrl: string) {}
}

// 5. Factory providers
export function urlServiceFactory(http: HttpClient, config: AppConfig) {
  return new UrlService(http, config.apiUrl);
}

@NgModule({
  providers: [
    {
      provide: UrlService,
      useFactory: urlServiceFactory,
      deps: [HttpClient, AppConfig]
    }
  ]
})
export class AppModule { }

// 6. Class providers with useClass
@Injectable()
export class ApiService {
  get(url: string): Observable<any> {
    return this.http.get(url);
  }
}

@Injectable()
export class MockApiService {
  get(url: string): Observable<any> {
    return of({ mock: 'data' });
  }
}

@NgModule({
  providers: [
    {
      provide: ApiService,
      useClass: MockApiService  // Replace with mock in tests
    }
  ]
})
export class AppModule { }

// 7. Optional dependencies
@Injectable({
  providedIn: 'root'
})
export class LoggingService {
  constructor(
    @Optional() private logger: Logger  // Won't throw error if not provided
  ) {}
  
  log(message: string): void {
    if (this.logger) {
      this.logger.log(message);
    } else {
      console.log(message);
    }
  }
}

// 8. Multiple providers
export const VALIDATORS = new InjectionToken<any[]>('VALIDATORS');

@NgModule({
  providers: [
    { provide: VALIDATORS, useClass: UrlValidator, multi: true },
    { provide: VALIDATORS, useClass: EmailValidator, multi: true }
  ]
})
export class AppModule { }

// Usage
@Injectable()
export class ValidationService {
  constructor(@Inject(VALIDATORS) private validators: any[]) {}
  
  validate(value: any): boolean {
    return this.validators.every(v => v.validate(value));
  }
}
```

---

## URL Shortener Project Specific

### Q21: How to implement URL shortening algorithm?

**Answer:** Generate unique short codes using various strategies.

```java
// Strategy 1: Random String Generation
@Component
public class RandomShortCodeGenerator {
    
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();
    
    @Autowired
    private UrlRepository urlRepository;
    
    public String generate() {
        String code;
        do {
            code = generateRandomCode();
        } while (urlRepository.existsByShortCode(code));
        
        return code;
    }
    
    private String generateRandomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}

// Strategy 2: Base62 Encoding of Database ID
@Component
public class Base62ShortCodeGenerator {
    
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public String encode(long id) {
        StringBuilder code = new StringBuilder();
        
        while (id > 0) {
            code.append(BASE62.charAt((int)(id % 62)));
            id /= 62;
        }
        
        return code.reverse().toString();
    }
    
    public long decode(String code) {
        long id = 0;
        for (char c : code.toCharArray()) {
            id = id * 62 + BASE62.indexOf(c);
        }
        return id;
    }
}

// Strategy 3: Hash-based (MD5/SHA)
@Component
public class HashBasedShortCodeGenerator {
    
    private static final int CODE_LENGTH = 7;
    
    @Autowired
    private UrlRepository urlRepository;
    
    public String generate(String originalUrl) {
        String code;
        int counter = 0;
        
        do {
            String input = originalUrl + counter;
            code = generateHash(input);
            counter++;
        } while (urlRepository.existsByShortCode(code));
        
        return code;
    }
    
    private String generateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            
            // Convert to Base62
            BigInteger number = new BigInteger(1, hash);
            StringBuilder result = new StringBuilder();
            
            while (number.compareTo(BigInteger.ZERO) > 0) {
                BigInteger[] divmod = number.divideAndRemainder(BigInteger.valueOf(62));
                result.insert(0, "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(divmod[1].intValue()));
                number = divmod[0];
            }
            
            return result.substring(0, Math.min(CODE_LENGTH, result.length()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}

// Strategy 4: Counter-based with Base62
@Component
public class CounterBasedGenerator {
    
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final AtomicLong counter = new AtomicLong(100000);  // Start from 100000
    
    public String generate() {
        long id = counter.incrementAndGet();
        return toBase62(id);
    }
    
    private String toBase62(long num) {
        if (num == 0) return "0";
        
        StringBuilder result = new StringBuilder();
        while (num > 0) {
            result.insert(0, BASE62.charAt((int)(num % 62)));
            num /= 62;
        }
        return result.toString();
    }
}

// Using in Service
@Service
public class UrlService {
    
    @Autowired
    private UrlRepository urlRepository;
    
    @Autowired
    private RandomShortCodeGenerator codeGenerator;
    
    public UrlResponse createShortUrl(UrlRequest request, User user) {
        String shortCode;
        
        if (request.getCustomAlias() != null && !request.getCustomAlias().isEmpty()) {
            // Use custom alias
            if (urlRepository.existsByShortCode(request.getCustomAlias())) {
                throw new BadRequestException("Alias already in use");
            }
            shortCode = request.getCustomAlias();
        } else {
            // Generate random code
            shortCode = codeGenerator.generate();
        }
        
        ShortenedUrl url = new ShortenedUrl();
        url.setOriginalUrl(request.getOriginalUrl());
        url.setShortCode(shortCode);
        url.setUser(user);
        url.setCreatedAt(LocalDateTime.now());
        url.setClickCount(0);
        
        ShortenedUrl savedUrl = urlRepository.save(url);
        
        return mapToResponse(savedUrl);
    }
}
```

### Q22: How to implement click tracking and analytics?

**Answer:** Track URL clicks and provide analytics with proper database design.

```java
// Entity for Click Tracking
@Entity
@Table(name = "url_clicks")
public class UrlClick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "url_id", nullable = false)
    private ShortenedUrl url;
    
    private String ipAddress;
    private String userAgent;
    private String referer;
    private String country;
    private String city;
    
    @Column(nullable = false)
    private LocalDateTime clickedAt;
    
    // Getters and setters
}

// Repository
@Repository
public interface UrlClickRepository extends JpaRepository<UrlClick, Long> {
    
    List<UrlClick> findByUrlIdOrderByClickedAtDesc(Long urlId);
    
    @Query("SELECT COUNT(c) FROM UrlClick c WHERE c.url.id = :urlId")
    long countByUrlId(@Param("urlId") Long urlId);
    
    @Query("SELECT new map(c.country as country, COUNT(c) as count) " +
           "FROM UrlClick c WHERE c.url.id = :urlId GROUP BY c.country")
    List<Map<String, Object>> getClicksByCountry(@Param("urlId") Long urlId);
    
    @Query("SELECT new map(DATE(c.clickedAt) as date, COUNT(c) as count) " +
           "FROM UrlClick c WHERE c.url.id = :urlId " +
           "AND c.clickedAt >= :startDate " +
           "GROUP BY DATE(c.clickedAt) ORDER BY DATE(c.clickedAt)")
    List<Map<String, Object>> getClicksByDate(
        @Param("urlId") Long urlId, 
        @Param("startDate") LocalDateTime startDate
    );
}

// Service for Analytics
@Service
public class AnalyticsService {
    
    @Autowired
    private UrlClickRepository clickRepository;
    
    @Autowired
    private GeoLocationService geoLocationService;
    
    public void trackClick(ShortenedUrl url, HttpServletRequest request) {
        UrlClick click = new UrlClick();
        click.setUrl(url);
        click.setIpAddress(getClientIpAddress(request));
        click.setUserAgent(request.getHeader("User-Agent"));
        click.setReferer(request.getHeader("Referer"));
        click.setClickedAt(LocalDateTime.now());
        
        // Get geolocation data
        String ipAddress = click.getIpAddress();
        GeoLocation location = geoLocationService.getLocation(ipAddress);
        if (location != null) {
            click.setCountry(location.getCountry());
            click.setCity(location.getCity());
        }
        
        clickRepository.save(click);
        
        // Increment click count (optimistic)
        url.setClickCount(url.getClickCount() + 1);
    }
    
    public UrlAnalytics getAnalytics(Long urlId) {
        long totalClicks = clickRepository.countByUrlId(urlId);
        List<Map<String, Object>> clicksByCountry = clickRepository.getClicksByCountry(urlId);
        List<Map<String, Object>> clicksByDate = clickRepository.getClicksByDate(
            urlId, 
            LocalDateTime.now().minusDays(30)
        );
        
        return new UrlAnalytics(totalClicks, clicksByCountry, clicksByDate);
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}

// Controller for Redirection with Tracking
@RestController
public class RedirectController {
    
    @Autowired
    private UrlService urlService;
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @GetMapping("/{shortCode}")
    public RedirectView redirect(
            @PathVariable String shortCode,
            HttpServletRequest request) {
        
        ShortenedUrl url = urlService.getByShortCode(shortCode);
        
        // Track click asynchronously
        CompletableFuture.runAsync(() -> 
            analyticsService.trackClick(url, request)
        );
        
        return new RedirectView(url.getOriginalUrl());
    }
    
    @GetMapping("/api/urls/{id}/analytics")
    public ResponseEntity<UrlAnalytics> getAnalytics(@PathVariable Long id) {
        UrlAnalytics analytics = analyticsService.getAnalytics(id);
        return ResponseEntity.ok(analytics);
    }
}

// GeoLocation Service (using external API)
@Service
public class GeoLocationService {
    
    @Value("${geolocation.api.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public GeoLocation getLocation(String ipAddress) {
        try {
            String url = String.format(
                "https://api.ipgeolocation.io/ipgeo?apiKey=%s&ip=%s",
                apiKey, ipAddress
            );
            
            return restTemplate.getForObject(url, GeoLocation.class);
        } catch (Exception e) {
            return null;
        }
    }
}

// DTO Classes
public class UrlAnalytics {
    private long totalClicks;
    private List<Map<String, Object>> clicksByCountry;
    private List<Map<String, Object>> clicksByDate;
    
    // Constructor, getters, setters
}

public class GeoLocation {
    private String country;
    private String city;
    private String latitude;
    private String longitude;
    
    // Getters and setters
}

// Angular Component to Display Analytics
@Component({
  selector: 'app-url-analytics',
  template: `
    <div class="analytics" *ngIf="analytics">
      <h2>Analytics</h2>
      
      <div class="stat-card">
        <h3>Total Clicks</h3>
        <p class="stat-value">{{ analytics.totalClicks }}</p>
      </div>
      
      <div class="chart">
        <h3>Clicks by Country</h3>
        <div *ngFor="let item of analytics.clicksByCountry">
          {{ item.country }}: {{ item.count }}
        </div>
      </div>
      
      <div class="chart">
        <h3>Clicks Over Time</h3>
        <div *ngFor="let item of analytics.clicksByDate">
          {{ item.date | date }}: {{ item.count }}
        </div>
      </div>
    </div>
  `
})
export class UrlAnalyticsComponent implements OnInit {
  analytics: any;
  
  constructor(
    private route: ActivatedRoute,
    private urlService: UrlService
  ) {}
  
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.urlService.getAnalytics(id!).subscribe(
      data => this.analytics = data
    );
  }
}
```

### Q23: How to implement URL expiration and cleanup?

**Answer:** Add expiration logic and scheduled tasks to clean up expired URLs.

```java
// Update Entity with Expiration
@Entity
@Table(name = "shortened_urls")
public class ShortenedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 2048)
    private String originalUrl;
    
    @Column(nullable = false, unique = true, length = 10)
    private String shortCode;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime expiresAt;  // Expiration date
    
    private boolean isActive = true;  // Soft delete flag
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private int clickCount = 0;
    
    // Check if URL is expired
    @Transient
    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }
    
    // Getters and setters
}

// Service with Expiration Logic
@Service
public class UrlService {
    
    @Autowired
    private UrlRepository urlRepository;
    
    public UrlResponse createShortUrl(UrlRequest request, User user) {
        // ... existing code ...
        
        // Set expiration
        if (request.getExpiresInDays() != null && request.getExpiresInDays() > 0) {
            url.setExpiresAt(LocalDateTime.now().plusDays(request.getExpiresInDays()));
        }
        
        ShortenedUrl savedUrl = urlRepository.save(url);
        return mapToResponse(savedUrl);
    }
    
    public ShortenedUrl getByShortCode(String shortCode) {
        ShortenedUrl url = urlRepository.findByShortCodeAndIsActive(shortCode, true)
            .orElseThrow(() -> new ResourceNotFoundException("URL not found"));
        
        // Check if expired
        if (url.isExpired()) {
            throw new ResourceNotFoundException("URL has expired");
        }
        
        return url;
    }
}

// Scheduled Task for Cleanup
@Component
@EnableScheduling
public class UrlCleanupScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(UrlCleanupScheduler.class);
    
    @Autowired
    private UrlRepository urlRepository;
    
    // Run every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredUrls() {
        logger.info("Starting cleanup of expired URLs");
        
        LocalDateTime now = LocalDateTime.now();
        List<ShortenedUrl> expiredUrls = urlRepository.findByExpiresAtBeforeAndIsActive(now, true);
        
        expiredUrls.forEach(url -> {
            url.setIsActive(false);  // Soft delete
            logger.info("Deactivated expired URL: {}", url.getShortCode());
        });
        
        urlRepository.saveAll(expiredUrls);
        
        logger.info("Cleanup completed. Deactivated {} URLs", expiredUrls.size());
    }
    
    // Run every hour
    @Scheduled(fixedRate = 3600000)
    public void cleanupOldClickData() {
        logger.info("Starting cleanup of old click data");
        
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(90);
        // Implement click data cleanup logic
    }
}

// Repository Methods
@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> {
    
    Optional<ShortenedUrl> findByShortCodeAndIsActive(String shortCode, boolean isActive);
    
    List<ShortenedUrl> findByExpiresAtBeforeAndIsActive(LocalDateTime date, boolean isActive);
    
    List<ShortenedUrl> findByUserIdAndIsActive(Long userId, boolean isActive);
    
    @Query("SELECT u FROM ShortenedUrl u WHERE u.user.id = :userId " +
           "AND u.isActive = true " +
           "AND (u.expiresAt IS NULL OR u.expiresAt > :now)")
    List<ShortenedUrl> findActiveUrlsByUserId(
        @Param("userId") Long userId,
        @Param("now") LocalDateTime now
    );
}

// Configuration for Scheduling
@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {
    
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }
    
    @Bean
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(5);
    }
}

// DTO with Expiration
public class UrlRequest {
    @NotBlank
    @Pattern(regexp = "^https?://.+", message = "URL must start with http:// or https://")
    private String originalUrl;
    
    @Pattern(regexp = "^[a-zA-Z0-9-_]*$", message = "Only alphanumeric characters, hyphens, and underscores allowed")
    private String customAlias;
    
    @Min(1)
    @Max(365)
    private Integer expiresInDays;  // Optional expiration in days
    
    // Getters and setters
}

// Angular Service with Expiration
export interface UrlData {
  id: number;
  originalUrl: string;
  shortCode: string;
  createdAt: Date;
  expiresAt?: Date;
  isExpired: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class UrlService {
  
  createUrl(data: { originalUrl: string, customAlias?: string, expiresInDays?: number }): Observable<UrlData> {
    return this.http.post<UrlData>('/api/urls', data);
  }
  
  // Check if URL is expired on frontend
  isUrlExpired(url: UrlData): boolean {
    if (!url.expiresAt) return false;
    return new Date() > new Date(url.expiresAt);
  }
  
  // Filter out expired URLs
  getActiveUrls(): Observable<UrlData[]> {
    return this.http.get<UrlData[]>('/api/urls').pipe(
      map(urls => urls.filter(url => !this.isUrlExpired(url)))
    );
  }
}
```

---

## Architecture & Design Patterns

### Q24: What are common design patterns used in Spring Boot applications?

**Answer:** Design patterns improve code organization, reusability, and maintainability.

```java
// 1. SINGLETON PATTERN
// Spring beans are singletons by default
@Service
public class UrlService {
    // Single instance managed by Spring
}

// 2. FACTORY PATTERN
public interface ShortCodeGenerator {
    String generate();
}

@Component("random")
public class RandomCodeGenerator implements ShortCodeGenerator {
    public String generate() {
        return RandomStringUtils.randomAlphanumeric(6);
    }
}

@Component("base62")
public class Base62CodeGenerator implements ShortCodeGenerator {
    public String generate() {
        // Base62 implementation
        return "";
    }
}

@Service
public class ShortCodeGeneratorFactory {
    
    private final Map<String, ShortCodeGenerator> generators;
    
    @Autowired
    public ShortCodeGeneratorFactory(List<ShortCodeGenerator> generatorList) {
        generators = generatorList.stream()
            .collect(Collectors.toMap(
                gen -> gen.getClass().getAnnotation(Component.class).value(),
                gen -> gen
            ));
    }
    
    public ShortCodeGenerator getGenerator(String type) {
        return generators.getOrDefault(type, generators.get("random"));
    }
}

// 3. BUILDER PATTERN
public class UrlResponse {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private int clickCount;
    
    private UrlResponse(Builder builder) {
        this.id = builder.id;
        this.originalUrl = builder.originalUrl;
        this.shortUrl = builder.shortUrl;
        this.createdAt = builder.createdAt;
        this.expiresAt = builder.expiresAt;
        this.clickCount = builder.clickCount;
    }
    
    public static class Builder {
        private Long id;
        private String originalUrl;
        private String shortUrl;
        private LocalDateTime createdAt;
        private LocalDateTime expiresAt;
        private int clickCount;
        
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        
        public Builder originalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
            return this;
        }
        
        public Builder shortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
            return this;
        }
        
        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }
        
        public Builder clickCount(int clickCount) {
            this.clickCount = clickCount;
            return this;
        }
        
        public UrlResponse build() {
            return new UrlResponse(this);
        }
    }
    
    // Getters
}

// Usage
UrlResponse response = new UrlResponse.Builder()
    .id(1L)
    .originalUrl("https://example.com")
    .shortUrl("http://short.ly/abc123")
    .createdAt(LocalDateTime.now())
    .clickCount(0)
    .build();

// 4. STRATEGY PATTERN
public interface UrlValidationStrategy {
    boolean validate(String url);
}

@Component
public class HttpUrlValidator implements UrlValidationStrategy {
    public boolean validate(String url) {
        return url.matches("^https?://.+");
    }
}

@Component
public class BlacklistValidator implements UrlValidationStrategy {
    private Set<String> blacklist = Set.of("spam.com", "malicious.com");
    
    public boolean validate(String url) {
        return blacklist.stream().noneMatch(url::contains);
    }
}

@Service
public class UrlValidationService {
    
    private final List<UrlValidationStrategy> validators;
    
    @Autowired
    public UrlValidationService(List<UrlValidationStrategy> validators) {
        this.validators = validators;
    }
    
    public boolean validateUrl(String url) {
        return validators.stream().allMatch(v -> v.validate(url));
    }
}

// 5. DECORATOR PATTERN
public interface UrlService {
    UrlResponse createUrl(UrlRequest request);
}

@Service("baseUrlService")
public class BaseUrlService implements UrlService {
    public UrlResponse createUrl(UrlRequest request) {
        // Basic implementation
        return null;
    }
}

@Service
@Primary
public class CachedUrlService implements UrlService {
    
    private final UrlService delegate;
    private final Cache<String, UrlResponse> cache;
    
    @Autowired
    public CachedUrlService(@Qualifier("baseUrlService") UrlService delegate) {
        this.delegate = delegate;
        this.cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();
    }
    
    public UrlResponse createUrl(UrlRequest request) {
        UrlResponse cached = cache.getIfPresent(request.getOriginalUrl());
        if (cached != null) {
            return cached;
        }
        
        UrlResponse response = delegate.createUrl(request);
        cache.put(request.getOriginalUrl(), response);
        return response;
    }
}

// 6. REPOSITORY PATTERN
// Already implemented with Spring Data JPA
@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> {
    Optional<ShortenedUrl> findByShortCode(String shortCode);
}

// 7. DTO PATTERN
// Separate domain models from API contracts
@Service
public class UrlMapper {
    
    public UrlResponse toResponse(ShortenedUrl entity) {
        return new UrlResponse.Builder()
            .id(entity.getId())
            .originalUrl(entity.getOriginalUrl())
            .shortUrl(buildShortUrl(entity.getShortCode()))
            .createdAt(entity.getCreatedAt())
            .expiresAt(entity.getExpiresAt())
            .clickCount(entity.getClickCount())
            .build();
    }
    
    public ShortenedUrl toEntity(UrlRequest request) {
        ShortenedUrl url = new ShortenedUrl();
        url.setOriginalUrl(request.getOriginalUrl());
        return url;
    }
    
    private String buildShortUrl(String shortCode) {
        return "http://short.ly/" + shortCode;
    }
}

// 8. OBSERVER PATTERN (Event-Driven)
// Event
public class UrlCreatedEvent extends ApplicationEvent {
    private final ShortenedUrl url;
    
    public UrlCreatedEvent(Object source, ShortenedUrl url) {
        super(source);
        this.url = url;
    }
    
    public ShortenedUrl getUrl() {
        return url;
    }
}

// Publisher
@Service
public class UrlService {
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    public UrlResponse createUrl(UrlRequest request) {
        // Create URL
        ShortenedUrl url = new ShortenedUrl();
        // ... set properties ...
        ShortenedUrl savedUrl = urlRepository.save(url);
        
        // Publish event
        eventPublisher.publishEvent(new UrlCreatedEvent(this, savedUrl));
        
        return mapToResponse(savedUrl);
    }
}

// Listener
@Component
public class UrlEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(UrlEventListener.class);
    
    @EventListener
    @Async
    public void handleUrlCreated(UrlCreatedEvent event) {
        logger.info("URL created: {}", event.getUrl().getShortCode());
        // Send notification, update analytics, etc.
    }
}
```

### Q25: How to implement caching in Spring Boot?

**Answer:** Caching improves performance by storing frequently accessed data.

```java
// 1. Enable Caching
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
            new ConcurrentMapCache("urls"),
            new ConcurrentMapCache("users")
        ));
        return cacheManager;
    }
}

// 2. Using @Cacheable, @CachePut, @CacheEvict
@Service
public class UrlService {
    
    @Autowired
    private UrlRepository urlRepository;
    
    // Cache the result
    @Cacheable(value = "urls", key = "#shortCode")
    public ShortenedUrl getByShortCode(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
            .orElseThrow(() -> new ResourceNotFoundException("URL not found"));
    }
    
    // Update cache
    @CachePut(value = "urls", key = "#result.shortCode")
    public ShortenedUrl createUrl(UrlRequest request) {
        // Create and save URL
        return savedUrl;
    }
    
    // Remove from cache
    @CacheEvict(value = "urls", key = "#shortCode")
    public void deleteUrl(String shortCode) {
        urlRepository.deleteByShortCode(shortCode);
    }
    
    // Clear entire cache
    @CacheEvict(value = "urls", allEntries = true)
    public void clearCache() {
        // Cache will be cleared
    }
    
    // Conditional caching
    @Cacheable(value = "urls", key = "#id", condition = "#id > 10")
    public ShortenedUrl getById(Long id) {
        return urlRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("URL not found"));
    }
}

// 3. Redis Cache Configuration
@Configuration
@EnableCaching
public class RedisCacheConfig {
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .disableCachingNullValues()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer()));
        
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build();
    }
}

// 4. Custom Cache Key Generator
@Component
public class CustomCacheKeyGenerator implements KeyGenerator {
    
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getSimpleName() + "_"
            + method.getName() + "_"
            + Arrays.toString(params);
    }
}

// Usage
@Cacheable(value = "urls", keyGenerator = "customCacheKeyGenerator")
public ShortenedUrl getUrl(Long id, String code) {
    // Method implementation
    return null;
}

// 5. Caffeine Cache (In-Memory, High Performance)
@Configuration
@EnableCaching
public class CaffeineCacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("urls", "users");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }
    
    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .recordStats();
    }
}

// 6. Cache Statistics and Monitoring
@Component
public class CacheMonitor {
    
    @Autowired
    private CacheManager cacheManager;
    
    @Scheduled(fixedRate = 60000)  // Every minute
    public void logCacheStats() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache instanceof CaffeineCache) {
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = 
                    (com.github.benmanes.caffeine.cache.Cache<Object, Object>) 
                    ((CaffeineCache) cache).getNativeCache();
                
                CacheStats stats = nativeCache.stats();
                System.out.printf("Cache: %s, Hits: %d, Misses: %d, Hit Rate: %.2f%%\n",
                    cacheName,
                    stats.hitCount(),
                    stats.missCount(),
                    stats.hitRate() * 100
                );
            }
        });
    }
}
```

---

## Database & JPA

### Q26: What is JPA and how does it differ from JDBC?

**Answer:**
- **JDBC**: Low-level database access using SQL queries
- **JPA**: Object-Relational Mapping (ORM) framework, works with objects

```java
// JDBC Example (Boilerplate, error-prone)
public class UrlDaoJdbc {
    
    public ShortenedUrl findByShortCode(String shortCode) {
        String sql = "SELECT * FROM shortened_urls WHERE short_code = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, shortCode);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                ShortenedUrl url = new ShortenedUrl();
                url.setId(rs.getLong("id"));
                url.setOriginalUrl(rs.getString("original_url"));
                url.setShortCode(rs.getString("short_code"));
                url.setClickCount(rs.getInt("click_count"));
                url.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return url;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return null;
    }
}

// JPA Example (Clean, simple)
@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> {
    Optional<ShortenedUrl> findByShortCode(String shortCode);
}

// Usage
ShortenedUrl url = urlRepository.findByShortCode("abc123")
    .orElseThrow(() -> new ResourceNotFoundException("Not found"));
```

### Q27: What are JPA relationships and how to map them?

**Answer:** JPA supports one-to-one, one-to-many, many-to-one, and many-to-many relationships.

```java
// ONE-TO-MANY: User has many URLs
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    private String password;
    
    // One user can have many URLs
    @OneToMany(
        mappedBy = "user",           // Field in ShortenedUrl
        cascade = CascadeType.ALL,   // Operations cascade to URLs
        orphanRemoval = true,        // Remove orphaned URLs
        fetch = FetchType.LAZY       // Load URLs only when accessed
    )
    private List<ShortenedUrl> urls = new ArrayList<>();
    
    // Helper methods
    public void addUrl(ShortenedUrl url) {
        urls.add(url);
        url.setUser(this);
    }
    
    public void removeUrl(ShortenedUrl url) {
        urls.remove(url);
        url.setUser(null);
    }
    
    // Getters and setters
}

// MANY-TO-ONE: Many URLs belong to one user
@Entity
@Table(name = "shortened_urls")
public class ShortenedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String originalUrl;
    private String shortCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // Foreign key column
    private User user;
    
    // Getters and setters
}

// ONE-TO-ONE: User has one profile
@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String bio;
    private String avatar;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    // Getters and setters
}

// MANY-TO-MANY: URLs can have many tags, tags can belong to many URLs
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToMany(mappedBy = "tags")
    private Set<ShortenedUrl> urls = new HashSet<>();
    
    // Getters and setters
}

@Entity
@Table(name = "shortened_urls")
public class ShortenedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String originalUrl;
    private String shortCode;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "url_tags",                    // Join table name
        joinColumns = @JoinColumn(name = "url_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    
    // Helper methods
    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getUrls().add(this);
    }
    
    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getUrls().remove(this);
    }
    
    // Getters and setters
}

// Fetch Strategies
@Entity
public class ShortenedUrl {
    
    // LAZY (Default for collections) - Load only when accessed
    @OneToMany(fetch = FetchType.LAZY)
    private List<UrlClick> clicks;
    
    // EAGER - Always load with parent entity
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}

// Cascade Types
@Entity
public class User {
    
    @OneToMany(cascade = {
        CascadeType.PERSIST,   // Save child when saving parent
        CascadeType.MERGE,     // Update child when updating parent
        CascadeType.REMOVE     // Delete child when deleting parent
        // CascadeType.ALL     // All operations
    })
    private List<ShortenedUrl> urls;
}
```

### Q28: How to write custom queries with JPA?

**Answer:** Use method names, @Query with JPQL, or native SQL.

```java
@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> {
    
    // 1. QUERY METHOD NAMING
    // Automatically generates query from method name
    Optional<ShortenedUrl> findByShortCode(String shortCode);
    List<ShortenedUrl> findByUserId(Long userId);
    List<ShortenedUrl> findByUserIdAndIsActive(Long userId, boolean isActive);
    List<ShortenedUrl> findByClickCountGreaterThan(int count);
    List<ShortenedUrl> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<ShortenedUrl> findByShortCodeContaining(String keyword);
    List<ShortenedUrl> findByUserEmailOrderByCreatedAtDesc(String email);
    boolean existsByShortCode(String shortCode);
    long countByUserId(Long userId);
    void deleteByShortCode(String shortCode);
    
    // 2. @QUERY with JPQL (Java Persistence Query Language)
    @Query("SELECT u FROM ShortenedUrl u WHERE u.user.id = :userId AND u.isActive = true")
    List<ShortenedUrl> findActiveUrlsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT u FROM ShortenedUrl u WHERE u.clickCount > :minClicks ORDER BY u.clickCount DESC")
    List<ShortenedUrl> findPopularUrls(@Param("minClicks") int minClicks);
    
    // Join query
    @Query("SELECT u FROM ShortenedUrl u JOIN u.user user WHERE user.email = :email")
    List<ShortenedUrl> findByUserEmail(@Param("email") String email);
    
    // Projection - Select specific fields
    @Query("SELECT new com.urlshortener.dto.UrlStats(u.shortCode, u.clickCount) " +
           "FROM ShortenedUrl u WHERE u.user.id = :userId")
    List<UrlStats> getUrlStatsByUserId(@Param("userId") Long userId);
    
    // Update query
    @Modifying
    @Query("UPDATE ShortenedUrl u SET u.clickCount = u.clickCount + 1 WHERE u.id = :id")
    void incrementClickCount(@Param("id") Long id);
    
    // Delete query
    @Modifying
    @Query("DELETE FROM ShortenedUrl u WHERE u.expiresAt < :date")
    int deleteExpiredUrls(@Param("date") LocalDateTime date);
    
    // 3. NATIVE SQL QUERY
    @Query(value = "SELECT * FROM shortened_urls WHERE short_code = :code", nativeQuery = true)
    ShortenedUrl findByShortCodeNative(@Param("code") String code);
    
    // Complex native query with pagination
    @Query(value = "SELECT u.* FROM shortened_urls u " +
                   "JOIN users usr ON u.user_id = usr.id " +
                   "WHERE usr.email = :email " +
                   "ORDER BY u.created_at DESC",
           nativeQuery = true)
    Page<ShortenedUrl> findByUserEmailWithPagination(
        @Param("email") String email, 
        Pageable pageable
    );
    
    // 4. SPECIFICATIONS (Dynamic Queries)
    // Repository
    public interface UrlRepository extends JpaRepository<ShortenedUrl, Long>, 
                                          JpaSpecificationExecutor<ShortenedUrl> {
    }
    
    // Specification class
    public class UrlSpecifications {
        
        public static Specification<ShortenedUrl> hasUserId(Long userId) {
            return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
        }
        
        public static Specification<ShortenedUrl> isActive() {
            return (root, query, cb) -> cb.equal(root.get("isActive"), true);
        }
        
        public static Specification<ShortenedUrl> createdAfter(LocalDateTime date) {
            return (root, query, cb) -> cb.greaterThan(root.get("createdAt"), date);
        }
        
        public static Specification<ShortenedUrl> hasClickCountGreaterThan(int count) {
            return (root, query, cb) -> cb.greaterThan(root.get("clickCount"), count);
        }
    }
    
    // Usage in service
    @Service
    public class UrlService {
        
        public List<ShortenedUrl> searchUrls(Long userId, LocalDateTime fromDate, Integer minClicks) {
            Specification<ShortenedUrl> spec = Specification.where(null);
            
            if (userId != null) {
                spec = spec.and(UrlSpecifications.hasUserId(userId));
            }
            if (fromDate != null) {
                spec = spec.and(UrlSpecifications.createdAfter(fromDate));
            }
            if (minClicks != null) {
                spec = spec.and(UrlSpecifications.hasClickCountGreaterThan(minClicks));
            }
            
            spec = spec.and(UrlSpecifications.isActive());
            
            return urlRepository.findAll(spec);
        }
    }
}

// DTO for Projection
public class UrlStats {
    private String shortCode;
    private int clickCount;
    
    public UrlStats(String shortCode, int clickCount) {
        this.shortCode = shortCode;
        this.clickCount = clickCount;
    }
    
    // Getters
}

// 5. PAGINATION AND SORTING
@Service
public class UrlService {
    
    public Page<ShortenedUrl> getUrlsWithPagination(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return urlRepository.findAll(pageable);
    }
    
    public Page<ShortenedUrl> getUserUrls(Long userId, Pageable pageable) {
        return urlRepository.findByUserId(userId, pageable);
    }
}

// Controller
@GetMapping
public ResponseEntity<Page<UrlResponse>> getUrls(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy) {
    
    Page<UrlResponse> urls = urlService.getUrlsWithPagination(page, size, sortBy)
        .map(this::mapToResponse);
    
    return ResponseEntity.ok(urls);
}
```

---

## REST API Design

### Q29: What are RESTful API best practices?

**Answer:** REST (Representational State Transfer) follows specific conventions for API design.

**Principles:**
1. **Stateless**: Each request contains all necessary information
2. **Resource-based**: URLs represent resources, not actions
3. **HTTP Methods**: Use appropriate verbs (GET, POST, PUT, DELETE, PATCH)
4. **Status Codes**: Return meaningful HTTP status codes
5. **Versioning**: Support API versioning
6. **Documentation**: Comprehensive API documentation

```java
// 1. PROPER URL STRUCTURE
@RestController
@RequestMapping("/api/v1/urls")  // Version in URL
public class UrlController {
    
    // GET /api/v1/urls - List all URLs
    @GetMapping
    public ResponseEntity<List<UrlResponse>> getAllUrls(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        // Implementation
        return ResponseEntity.ok(urls);
    }
    
    // GET /api/v1/urls/{id} - Get specific URL
    @GetMapping("/{id}")
    public ResponseEntity<UrlResponse> getUrl(@PathVariable Long id) {
        UrlResponse url = urlService.getById(id);
        return ResponseEntity.ok(url);
    }
    
    // POST /api/v1/urls - Create new URL
    @PostMapping
    public ResponseEntity<UrlResponse> createUrl(
            @Valid @RequestBody UrlRequest request) {
        UrlResponse created = urlService.createUrl(request);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(location).body(created);
    }
    
    // PUT /api/v1/urls/{id} - Update entire resource
    @PutMapping("/{id}")
    public ResponseEntity<UrlResponse> updateUrl(
            @PathVariable Long id,
            @Valid @RequestBody UrlRequest request) {
        UrlResponse updated = urlService.updateUrl(id, request);
        return ResponseEntity.ok(updated);
    }
    
    // PATCH /api/v1/urls/{id} - Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<UrlResponse> partialUpdateUrl(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        UrlResponse updated = urlService.partialUpdate(id, updates);
        return ResponseEntity.ok(updated);
    }
    
    // DELETE /api/v1/urls/{id} - Delete resource
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Long id) {
        urlService.deleteUrl(id);
        return ResponseEntity.noContent().build();
    }
    
    // Nested resources
    // GET /api/v1/urls/{id}/analytics
    @GetMapping("/{id}/analytics")
    public ResponseEntity<UrlAnalytics> getAnalytics(@PathVariable Long id) {
        UrlAnalytics analytics = analyticsService.getAnalytics(id);
        return ResponseEntity.ok(analytics);
    }
}

// 2. HTTP STATUS CODES
@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {
    
    @GetMapping("/{id}")
    public ResponseEntity<UrlResponse> getUrl(@PathVariable Long id) {
        try {
            UrlResponse url = urlService.getById(id);
            return ResponseEntity.ok(url);  // 200 OK
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }
    
    @PostMapping
    public ResponseEntity<UrlResponse> createUrl(@Valid @RequestBody UrlRequest request) {
        UrlResponse created = urlService.createUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);  // 201 Created
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UrlResponse> updateUrl(
            @PathVariable Long id,
            @Valid @RequestBody UrlRequest request) {
        UrlResponse updated = urlService.updateUrl(id, request);
        return ResponseEntity.ok(updated);  // 200 OK
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Long id) {
        urlService.deleteUrl(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}

// 3. ERROR HANDLING
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            errors,
            LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

public class ErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;
    
    // Constructors, getters, setters
}

// 4. REQUEST VALIDATION
public class UrlRequest {
    
    @NotBlank(message = "Original URL is required")
    @Pattern(
        regexp = "^https?://.+",
        message = "URL must start with http:// or https://"
    )
    @Size(max = 2048, message = "URL is too long")
    private String originalUrl;
    
    @Pattern(
        regexp = "^[a-zA-Z0-9-_]*$",
        message = "Custom alias can only contain alphanumeric characters, hyphens, and underscores"
    )
    @Size(min = 3, max = 20, message = "Custom alias must be between 3 and 20 characters")
    private String customAlias;
    
    @Min(value = 1, message = "Expiration must be at least 1 day")
    @Max(value = 365, message = "Expiration cannot exceed 365 days")
    private Integer expiresInDays;
    
    // Getters and setters
}

// 5. PAGINATION AND FILTERING
@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {
    
    @GetMapping
    public ResponseEntity<PagedResponse<UrlResponse>> getUrls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search) {
        
        PagedResponse<UrlResponse> response = urlService.getUrls(
            page, size, sortBy, sortDir, search
        );
        
        return ResponseEntity.ok(response);
    }
}

public class PagedResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
    // Constructors, getters, setters
}

// 6. API VERSIONING
// Option 1: URL versioning (Recommended)
@RequestMapping("/api/v1/urls")

// Option 2: Header versioning
@GetMapping(headers = "API-Version=1")

// Option 3: Request parameter versioning
@GetMapping(params = "version=1")

// 7. HATEOAS (Hypermedia)
public class UrlResponse {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private Map<String, String> links;  // Related links
    
    public void addLink(String rel, String href) {
        if (links == null) {
            links = new HashMap<>();
        }
        links.put(rel, href);
    }
}

@Service
public class UrlService {
    
    public UrlResponse getById(Long id) {
        ShortenedUrl url = urlRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("URL not found"));
        
        UrlResponse response = mapToResponse(url);
        
        // Add HATEOAS links
        response.addLink("self", "/api/v1/urls/" + id);
        response.addLink("analytics", "/api/v1/urls/" + id + "/analytics");
        response.addLink("user", "/api/v1/users/" + url.getUser().getId());
        
        return response;
    }
}
```

---

## Performance & Optimization

### Q30: How to optimize Spring Boot application performance?

**Answer:** Multiple strategies for improving application performance.

```java
// 1. DATABASE OPTIMIZATION

// Use @EntityGraph to avoid N+1 queries
@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> {
    
    @EntityGraph(attributePaths = {"user", "tags"})
    @Query("SELECT u FROM ShortenedUrl u WHERE u.id = :id")
    Optional<ShortenedUrl> findByIdWithUserAndTags(@Param("id") Long id);
}

// Batch operations
@Service
public class UrlService {
    
    @Transactional
    public void createMultipleUrls(List<UrlRequest> requests) {
        List<ShortenedUrl> urls = requests.stream()
            .map(this::mapToEntity)
            .collect(Collectors.toList());
        
        urlRepository.saveAll(urls);  // Batch insert
    }
}

// Use projections for read-only queries
public interface UrlProjection {
    Long getId();
    String getShortCode();
    int getClickCount();
}

@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> {
    List<UrlProjection> findAllProjectedBy();
}

// 2. CACHING (Covered in Q25)

// 3. ASYNCHRONOUS PROCESSING
@Configuration
@EnableAsync
public class AsyncConfig {
    
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }
}

@Service
public class AnalyticsService {
    
    @Async("taskExecutor")
    public CompletableFuture<Void> trackClickAsync(
            ShortenedUrl url, 
            HttpServletRequest request) {
        
        // Time-consuming analytics tracking
        trackClick(url, request);
        
        return CompletableFuture.completedFuture(null);
    }
}

// 4. CONNECTION POOLING
# application.properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

// 5. LAZY LOADING
@Entity
public class ShortenedUrl {
    
    @OneToMany(fetch = FetchType.LAZY)  // Don't load unless accessed
    private List<UrlClick> clicks;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

// 6. INDEXING
@Entity
@Table(name = "shortened_urls", indexes = {
    @Index(name = "idx_short_code", columnList = "short_code"),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class ShortenedUrl {
    // Entity fields
}

// 7. COMPRESSION
@Configuration
public class CompressionConfig {
    
    @Bean
    public FilterRegistrationBean<GZIPFilter> gzipFilter() {
        FilterRegistrationBean<GZIPFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new GZIPFilter());
        registration.addUrlPatterns("/api/*");
        return registration;
    }
}

// 8. QUERY OPTIMIZATION
// Bad: Loads all URLs into memory
public List<ShortenedUrl> getAllUrls() {
    return urlRepository.findAll();  // Don't do this for large datasets
}

// Good: Use pagination
public Page<ShortenedUrl> getUrls(Pageable pageable) {
    return urlRepository.findAll(pageable);
}

// 9. REDUCE JSON PAYLOAD SIZE
@JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude null fields
public class UrlResponse {
    private Long id;
    private String originalUrl;
    
    @JsonIgnore  // Don't serialize
    private String internalField;
    
    @JsonProperty("short_url")  // Rename field
    private String shortUrl;
}

// 10. USE APPROPRIATE DATA STRUCTURES
// Bad: Using List for lookup
List<String> shortCodes = new ArrayList<>();
boolean exists = shortCodes.contains(code);  // O(n)

// Good: Using Set for lookup
Set<String> shortCodes = new HashSet<>();
boolean exists = shortCodes.contains(code);  // O(1)
```

I've created a comprehensive FAQ document covering Java, Spring Boot, and Angular from basics to advanced topics, specifically related to the URL Shortener project. The file has been saved as `URL_SHORTENER_FAQ.md` in the top-most directory.

The FAQ includes:
- **Java fundamentals** (OOP, Collections, Streams)
- **Spring Boot basics** (DI, annotations, configuration)
- **Spring Security & JWT** authentication
- **Angular fundamentals** (components, routing, forms)
- **RxJS Observables** and HTTP communication
- **Project-specific topics** (URL shortening algorithms, click tracking)
- **Architecture & design patterns**
- **Database & JPA** (relationships, queries)
- **REST API best practices**
- **Performance optimization**
- **Testing strategies**
- **Deployment considerations**

Each question includes detailed explanations and practical code examples that you can use in your implementation. Would you like me to continue with more advanced topics or expand on any specific section?