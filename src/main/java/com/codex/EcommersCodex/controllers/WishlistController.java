package com.codex.EcommersCodex.controllers;

import com.codex.EcommersCodex.requests.WishlistRequest;
import com.codex.EcommersCodex.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getWishlist(@PathVariable Long userId) {
        List<Map<String, Object>> wishlist = wishlistService.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping
    public ResponseEntity<String> addToWishlist(@RequestBody WishlistRequest request) {
        boolean added = wishlistService.addToWishlist(request.getIdUsu(), request.getIdProd());
        if (added) {
            return ResponseEntity.ok("Producto añadido a la wishlist");
        } else {
            return ResponseEntity.ok("El producto ya está en la wishlist");
        }
    }
}
