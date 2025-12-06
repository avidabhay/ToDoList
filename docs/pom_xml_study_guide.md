# Maven POM.xml Study Guide

## What is POM.xml?

**POM** = **Project Object Model**

- XML file that contains project configuration and metadata for Maven
- The heart of a Maven project - Maven reads this file to build your project
- Located at the root of every Maven project

---

## Core Structure

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Project Coordinates (GAV) -->
    <groupId>com.example</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <!-- Parent POM -->
    <parent>...</parent>
    
    <!-- Properties -->
    <properties>...</properties>
    
    <!-- Dependencies -->
    <dependencies>...</dependencies>
    
    <!-- Build Configuration -->
    <build>...</build>
</project>
```

---

## Project Coordinates (GAV)

Every Maven project is uniquely identified by:

| Element | Description | Example |
|---------|-------------|---------|
| **groupId** | Organization/company identifier (reverse domain) | `com.example` or `org.springframework.boot` |
| **artifactId** | Project name | `my-app` or `spring-boot-starter-web` |
| **version** | Project version | `1.0.0` or `1.0-SNAPSHOT` |

**Together = Unique Identifier**: `com.example:my-app:1.0.0`

---

## Parent POM

Inherit configuration from another POM (reduces duplication):

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.1</version>
</parent>
```

**Benefits:**
- Inherits dependency versions (dependency management)
- Inherits plugin configurations
- Inherits properties and build settings
- **Best Practice**: Don't specify versions for dependencies managed by parent

---

## Dependencies

### Basic Dependency

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <!-- version inherited from parent -->
    </dependency>
</dependencies>
```

### Dependency Scopes

| Scope | When Available | Included in JAR? | Use Case |
|-------|---------------|------------------|----------|
| **compile** (default) | Compile + Runtime + Test | ✅ Yes | Main application dependencies |
| **runtime** | Runtime + Test only | ✅ Yes | JDBC drivers, logging implementations |
| **test** | Test only | ❌ No | JUnit, Mockito, test frameworks |
| **provided** | Compile + Test only | ❌ No | Servlet API (provided by server) |
| **system** | Like provided, but from local path | ❌ No | Rarely used (anti-pattern) |

### Optional Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

**`optional=true`**: This dependency won't be transitively included in projects that depend on yours.

---

## Properties

Define reusable values:

```xml
<properties>
    <java.version>21</java.version>
    <spring.version>6.1.0</spring.version>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
</properties>
```

**Usage**: `${property.name}` anywhere in POM

---

## Build Configuration

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

**Plugins** extend Maven's capabilities:
- `maven-compiler-plugin` - Compiles Java code
- `maven-surefire-plugin` - Runs unit tests
- `spring-boot-maven-plugin` - Packages Spring Boot apps

---

## Dependency Management vs Dependencies

### Dependency Management (in parent POM)
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>6.1.0</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```
- **Defines** versions but **doesn't add** dependencies
- Child projects can use these dependencies without specifying versions

### Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <!-- version comes from dependencyManagement -->
    </dependency>
</dependencies>
```
- **Actually adds** dependencies to the project

---

## Common Interview Questions

### Q: What is the difference between `<dependencies>` and `<dependencyManagement>`?

**Answer:**
- `<dependencies>`: Actually adds dependencies to your project
- `<dependencyManagement>`: Only declares versions, doesn't add dependencies. Used in parent POMs to centralize version management.

### Q: What are Maven coordinates?

**Answer:** The GAV (GroupId, ArtifactId, Version) that uniquely identifies a project or dependency in Maven Central.

### Q: What is a SNAPSHOT version?

**Answer:** 
- Version ending with `-SNAPSHOT` (e.g., `1.0-SNAPSHOT`)
- Indicates work in progress, not a release
- Maven always downloads the latest SNAPSHOT from repository
- Used during development

### Q: What is transitive dependency?

**Answer:**
- Dependencies of your dependencies
- Example: You depend on `spring-boot-starter-web` → it depends on `spring-core` → `spring-core` is a transitive dependency
- Maven automatically resolves and includes transitive dependencies

### Q: How do you exclude a transitive dependency?

**Answer:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

### Q: What is the Maven lifecycle?

**Answer:** Three built-in lifecycles:
1. **clean**: Cleans the project (deletes `target/` directory)
2. **default**: Builds the project
   - `validate` → `compile` → `test` → `package` → `verify` → `install` → `deploy`
3. **site**: Generates project documentation

Common commands:
- `mvn clean` - Clean the project
- `mvn compile` - Compile source code
- `mvn test` - Run tests
- `mvn package` - Create JAR/WAR
- `mvn install` - Install to local repository (~/.m2)
- `mvn clean install` - Clean + build + install

---

## Best Practices

✅ **DO:**
- Use parent POM for version management (e.g., `spring-boot-starter-parent`)
- Let parent manage dependency versions (don't specify versions for managed dependencies)
- Use properties for version numbers you need to reference multiple times
- Use appropriate scopes (`test` for test dependencies, `runtime` for runtime-only)
- Use `<dependencyManagement>` in multi-module projects

❌ **DON'T:**
- Hardcode versions for dependencies managed by parent
- Use `scope=compile` for test dependencies
- Include test dependencies in production JAR
- Use `<scope>system</scope>` (anti-pattern)

---

## Spring Boot Specific

### Typical Spring Boot POM Structure

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.1</version>
</parent>

<dependencies>
    <!-- Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- DevTools (development only) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    
    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

### Common Spring Boot Starters

| Starter | Purpose |
|---------|---------|
| `spring-boot-starter-web` | Web applications (includes Tomcat, Spring MVC) |
| `spring-boot-starter-data-jpa` | JPA with Hibernate |
| `spring-boot-starter-security` | Spring Security |
| `spring-boot-starter-test` | Testing (JUnit, Mockito, etc.) |
| `spring-boot-devtools` | Development tools (hot reload) |
| `spring-boot-starter-actuator` | Production monitoring |

---

## Quick Reference

**Maven Commands:**
```bash
mvn clean              # Clean target directory
mvn compile            # Compile code
mvn test               # Run tests
mvn package            # Create JAR/WAR
mvn install            # Install to local repo
mvn dependency:tree    # Show dependency tree
mvn dependency:resolve # Download all dependencies
```

**File Location:** `~/.m2/repository/` (local Maven repository)

**Maven Central:** https://repo.maven.apache.org/maven2/
