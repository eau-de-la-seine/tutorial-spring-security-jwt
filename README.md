# Spring Security with JWT

Launch the project with `mvn spring-boot:run` OR `mvn tomcat7:run`


## Project's content

#### The JWT Service

The `IJwtService` interface contains two methods:

* `verify()` : Used for verifying the sended JWT Token in `AuthenticationFilter#doFilter()`.
* `sign()` : Used for creating a JWT Token in `ILoginService#login()`.

There's two implementation for `IJwtService`:

* `JwtHmacService`, based on **HMAC**, activated with the ***jwtHmac*** Spring profile.
* `JwtRsaService`, based on **RSA**, activated with the ***jwtRsa*** Spring profile.



#### The Authentication Filter

The `AuthenticationFilter` is a class which extends Spring's `GenericFilterBean` class.

This filter:

* verifies the JWT Token.
* is used by our `WebSecurityConfig` class.
* is called for all routes, but is ignored for ***/login***.



#### The Authentication object

The `AuthenticationImpl` is a class which implements Spring's `Authentication` interface:

* `principal` : Normally, contains user's identity. But we are using Stateless JWT tokens in this project, so it contains user's session + profile.
* `authenticated` : True if user is authenticated, false otherwise. This attribute is set during the JWT verify step.
* `authorities` : Contains user's roles.

For more details about the Spring's `Authentication` interface, you can read its [JavaDoc](https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/core/Authentication.html).

The `Authentication` object is internally used by Spring Security's `SecurityContextHolder` class:

* For setting authentication object: `SecurityContextHolder.getContext().setAuthentication(authentication);`
* For getting authentication object: `SecurityContextHolder.getContext().getAuthentication();`



#### The Session object

The `Authentication` object is internally used by Spring and contains some information we do not prefer to expose, so we prefer to use and expose our `Session` object which contains a `Profile` object instead.



#### The Web Security Config class

The `WebSecurityConfig` is a configuration class which:

* is annotated with `@Configuration` and `@EnableWebSecurity` in order to activate Spring Security.
* extends Spring's `WebSecurityConfigurerAdapter` class, and overrides `configure()` method for setting authorized/unauthorized routes and the authentication filter.



#### The Session Service

The `ISessionService` interface can be injected in your controller or service in order to get user's `Session` object.



#### The Authentication Controller

The `AuthenticationController` contains :

* A ***/login*** route and injects `ILoginService` for authentication.
* A ***/me*** route and injects `ISessionService` for obtaining user's `Session` object.


#### The Other Controllers

The other controllers may be annotated with `@Secured` annotation. The role names must start with the `ROLE_` prefix.


#### Spring Security classes summary

* [org.springframework.security.core.Authentication](https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/core/Authentication.html)
* [org.springframework.security.core.context.SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/core/context/SecurityContextHolder.html)
* [org.springframework.security.core.context.SecurityContext](https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/core/context/SecurityContext.html)
* [org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter](https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/config/annotation/web/configuration/WebSecurityConfigurerAdapter.html)
* [org.springframework.security.config.annotation.web.builders.WebSecurity](https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/config/annotation/web/builders/WebSecurity.html)
* [org.springframework.security.config.annotation.web.builders.HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/config/annotation/web/builders/HttpSecurity.html)
* [org.springframework.security.core.GrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/core/GrantedAuthority.html)
* [org.springframework.security.access.annotation.Secured](https://docs.spring.io/autorepo/docs/spring-security/current/apidocs/org/springframework/security/access/annotation/Secured.html)


## Spring Zuul (Proxy / Reverse-Proxy)

We are now going to use ***Spring Zuul*** for redirecting authentified users to microservices.

#### Installation

Add this dependency to **pom.xml**:

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zuul</artifactId>
    </dependency>

Activate Zuul with this annotation on your main class: `@EnableZuulProxy`


#### Configuration

Add proxy pass routes to your *application.properties* file. Example:

    zuul.routes.foobar.stripPrefix=false
    zuul.routes.foobar.path=/foobar/**
    zuul.routes.foobar.url=http://localhost:25002

This will will redirect from http://localhost:25001/foobar to http://localhost:25002/foobar


#### Conditional routing

The `setSendZuulResponse()` method allows you to redirect requests.

Default value of `sendZuulResponse` is `true`.

If `sendZuulResponse` is set to `false`, then routing is not done by Zuul.

I used the `setSendZuulResponse()` method in `ZuulFilterImplementation#shouldFilter()` :

    RequestContext.getCurrentContext()
        .setSendZuulResponse(booleanValue)


Sources:
* https://spring.io/guides/gs/routing-and-filtering/
* https://github.com/Netflix/zuul/wiki/How-it-Works
* https://github.com/Netflix/zuul/wiki/Writing-Filters
* https://github.com/spring-guides/gs-routing-and-filtering
* https://stackoverflow.com/questions/29159462/spring-boot-cloud-zuul-proxy-404-error
* https://github.com/spring-cloud-samples/sample-zuul-filters
* https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html
