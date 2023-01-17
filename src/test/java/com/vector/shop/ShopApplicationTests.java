package com.vector.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.vector.shop.bill.Bill;
import com.vector.shop.bill.Item;
import com.vector.shop.product.Product;
import com.vector.shop.user.CartEmptyException;
import com.vector.shop.user.User;

import com.vector.shop.user.UserServiceImpl;

@SpringBootTest

class ShopApplicationTests {
	@MockBean
	UserServiceImpl userService;

	Product product;
	User user;
	Bill bill;

	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private PrintStream out = System.out;
	@BeforeEach
	public void setup() {
		product = new Product();
		product.setName("hello");
		product.setPrice(10000);
		user = new User();

		bill = new Bill();
		Item item = new Item("something",2l,100l);
		Item item2 = new Item("something",2l,200l);
		List<Item> products = new ArrayList<>();
		products.add(item);
		products.add(item2);
		bill.setProducts(products);
		System.setOut(new PrintStream(outContent));
	}

	@AfterAll 
	public void close() {
		System.setOut(out);
	}
	@Test
	public void testAddToCart() {
		Mockito.when(userService.addToCart(product)).thenReturn(product);
	}

	@Test
	public void testBuyItemInCart() {
		Mockito.when(userService.buyItemInCart(user)).thenThrow(CartEmptyException.class);
	}

	@Test
	public void testCalculate() {
		bill.calculateTotalprice();
		System.out.println(300);
		assertEquals(300,System.out.toString());
	}
}
