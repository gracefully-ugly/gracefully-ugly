package com.gracefullyugly.domain.cart_item.controller.api;

import com.gracefullyugly.domain.cart_item.dto.AddCartItemRequest;
import com.gracefullyugly.domain.cart_item.dto.CartItemResponse;
import com.gracefullyugly.domain.cart_item.service.CartItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/cart/{itemId}")
    public ResponseEntity<CartItemResponse> addCartItem(
            @Valid @NotNull @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable(name = "itemId") Long itemId, @Valid @RequestBody AddCartItemRequest request) {
        return ResponseEntity.ok(cartItemService.addCartItem(userId, itemId, request));
    }

    @DeleteMapping("/cart/{itemId}")
    public ResponseEntity<CartItemResponse> deleteCartItem(
            @Valid @NotNull @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable(name = "itemId") Long itemId) {
        return ResponseEntity.ok(cartItemService.deleteCartItem(userId, itemId));
    }
}
