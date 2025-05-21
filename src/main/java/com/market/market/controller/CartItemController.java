package com.market.market.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.market.market.dto.MagazineProductDto;
import com.market.market.service.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    /**
     * GET /api/cart-items/grouped/{cartId}
     * 
     * Returns the cart items grouped by magazine name with pricing and promotions
     */
    @GetMapping("/grouped/{cartId}")
    public ResponseEntity<List<MagazineProductDto>> getGroupedItems(@PathVariable Integer cartId) {
        List<MagazineProductDto> groupedItems = cartItemService.getGroupedCartItemsByMagazine(cartId);
        return ResponseEntity.ok(groupedItems);
    }
}
