package ing.hubs.spring.advanced.training.security.controller;

import ing.hubs.spring.advanced.training.security.config.HasManagerRole;
import ing.hubs.spring.advanced.training.security.config.Roles;
import ing.hubs.spring.advanced.training.security.dto.MessageDTO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import ing.hubs.spring.advanced.training.security.model.Product;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static ing.hubs.spring.advanced.training.security.config.Roles.*;
import static ing.hubs.spring.advanced.training.security.controller.ProductController.API_PREFIX;

@RestController
@RequestMapping(API_PREFIX)
@PreAuthorize("isAuthenticated()") // it's just an example :)
@SuppressWarnings("unused")
public class ProductController {

    public static final String API_PREFIX = "/v1/api";

    @PreAuthorize(
            "hasRole('" + ADMIN_ROLE + "') " +
            "AND authentication.details.userId == 25 " + // an equality check
            "AND hasAuthority('WRITE')"
    )
    @GetMapping("/product")
    public void addProduct(final Authentication authentication, @RequestBody Product product) {
        UserDetails userDetails  = (UserDetails) authentication.getPrincipal();
        System.out.println("The user details: " + userDetails);
        // further use the Authentication object, if needed
    }

    @PreFilter("filterObject.id == authentication.details.userId")
    public void filterProducts(final List<Product> products) {
        // using the filtered products, afterwards
    }
    
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable final int id, final @AuthenticationPrincipal UserDetails userDetails) {
        final String username = userDetails.getUsername();
        System.out.println("The current user is '" + username + "', has the authorities '" + Collections.singletonList(userDetails.getAuthorities()) + "'");
        return new Product(20, "Tablet");
    }

    @PostMapping("/product")
    public MessageDTO createProduct(@RequestBody Product product) {
        //TODO implement the product creation logic + the PreAuthorize for the ADMIN role
        return new MessageDTO("The product '" + product.getName() + "' has been created");
    }

    // dynamically retrieving the authenticated user details
    public void passAuthenticatedUser(final @AuthenticationPrincipal UserDetails userDetails) {
        /* the same details can be obtained using:
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final UserDetails details = (UserDetails) securityContext.getAuthentication().getPrincipal();
        */

        final String username = userDetails.getUsername();
        // the user details can be further passed to the services
    }

    @Secured({Roles.ADMIN_ROLE})
    public void processRequestOrResponseParameters(final HttpServletRequest request, final HttpServletResponse response) {
        // get parameters from the HTTP request, set details in the response
    }

    // recommended to be used when the principal details need to be consumed by an external tool / API
    @GetMapping("/currentUser")
    @HasManagerRole // DRY
    public Principal principal(final Principal principal) {
        return principal;
    }
}
