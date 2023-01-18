package com.vector.shop;



// @SpringBootTest

// class ShopApplicationTests {
// 	@MockBean
// 	UserServiceImpl userService;

// 	Product product;
// 	User user;
// 	Bill bill;

// 	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
// 	private PrintStream out = System.out;
// 	@BeforeEach
// 	public void setup() {
// 		product = new Product();
// 		product.setName("hello");
// 		product.setPrice(10000);
// 		user = new User();

// 		bill = new Bill();
// 		Item item = new Item("something",2l,100l);
// 		Item item2 = new Item("something",2l,200l);
// 		List<Item> products = new ArrayList<>();
// 		products.add(item);
// 		products.add(item2);
// 		bill.setProducts(products);
// 		System.setOut(new PrintStream(outContent));
// 	}

// 	@AfterAll 
// 	public void close() {
// 		System.setOut(out);
// 	}
// 	@Test
// 	public void testAddToCart() {
// 		Mockito.when(userService.addToCart(product)).thenReturn(product);
// 	}

// 	@Test
// 	public void testBuyItemInCart() {
// 		Mockito.when(userService.buyItemInCart(user)).thenThrow(CartEmptyException.class);
// 	}

// 	@Test
// 	public void testCalculate() {
// 		bill.calculateTotalprice();
// 		System.out.println(300);
// 		assertEquals(300,System.out.toString());
// 	}
// }
