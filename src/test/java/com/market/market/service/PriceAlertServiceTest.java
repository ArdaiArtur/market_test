package com.market.market.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.market.market.dto.PriceAlertCreateDto;
import com.market.market.dto.PriceAlertResponseDto;
import com.market.market.entity.PriceAlert;
import com.market.market.entity.Product;
import com.market.market.entity.User;
import com.market.market.repository.PriceAlertRepository;
import com.market.market.repository.PriceSnapshotRepository;
import com.market.market.repository.ProductRepository;
import com.market.market.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class PriceAlertServiceTest {

    @Mock
    private PriceAlertRepository alertRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private ProductRepository productRepo;

    @Mock
    private PriceSnapshotRepository snapshotRepo;

    @InjectMocks
    private PriceAlertService priceAlertService;

    private User mockUser;
    private Product mockProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setUserId(1);

        mockProduct = new Product();
        mockProduct.setProductId("P002");
    }

    @Test
    void testCreateAlert_success() {
        // Arrange
        BigDecimal targetPrice = new BigDecimal("99.99");
        PriceAlertCreateDto dto = new PriceAlertCreateDto(mockUser.getUserId(), mockProduct.getProductId(), targetPrice);

        when(userRepo.findById(mockUser.getUserId())).thenReturn(Optional.of(mockUser));
        when(productRepo.findById(mockProduct.getProductId())).thenReturn(Optional.of(mockProduct));

        ArgumentCaptor<PriceAlert> alertCaptor = ArgumentCaptor.forClass(PriceAlert.class);
        when(alertRepo.save(any(PriceAlert.class))).thenAnswer(invocation -> {
            PriceAlert a = invocation.getArgument(0);
            a.setAlertId(100);
            a.setCreatedAt(LocalDate.now());
            return a;
        });

        // Act
        PriceAlertResponseDto result = priceAlertService.createAlert(dto);

        // Assert
        assertNotNull(result);
        assertEquals(100, result.alertId());
        assertEquals(mockUser.getUserId(), result.userId());
        assertEquals(mockProduct.getProductId(), result.productId());
        assertEquals(targetPrice, result.targetPrice());

        verify(alertRepo).save(alertCaptor.capture());
        PriceAlert saved = alertCaptor.getValue();
        assertEquals(mockUser, saved.getUser());
        assertEquals(mockProduct, saved.getProduct());
        assertEquals(targetPrice, saved.getTargetPrice());
    }

    @Test
    void testCreateAlert_userNotFound() {
        // Arrange
        PriceAlertCreateDto dto = new PriceAlertCreateDto(999, "P002", new BigDecimal("99.99"));
        when(userRepo.findById(dto.userId())).thenReturn(Optional.empty());

        // Act + Assert
        Exception ex = assertThrows(IllegalArgumentException.class, () -> priceAlertService.createAlert(dto));
        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    void testCreateAlert_productNotFound() {
        // Arrange
        when(userRepo.findById(mockUser.getUserId())).thenReturn(Optional.of(mockUser));
        when(productRepo.findById(mockProduct.getProductId())).thenReturn(Optional.empty());

        PriceAlertCreateDto dto = new PriceAlertCreateDto(mockUser.getUserId(), mockProduct.getProductId(), new BigDecimal("50"));

        // Act + Assert
        Exception ex = assertThrows(IllegalArgumentException.class, () -> priceAlertService.createAlert(dto));
        assertTrue(ex.getMessage().contains("Product not found"));
    }
}
