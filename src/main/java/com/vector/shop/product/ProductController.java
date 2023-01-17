package com.vector.shop.product;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vector.shop.product.comment.Comment;
import com.vector.shop.storage.StorageService;
import com.vector.shop.user.User;



@Controller
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;
    private StorageService storageService;

    public ProductController(ProductService productService,StorageService storageService) {
        this.productService = productService;
        this.storageService = storageService;
    }

    @GetMapping("/search")
    public String home(Model model,@RequestParam("searchInput") String input,@AuthenticationPrincipal User user) {
        List<Product> products = productService.search(input);
        model.addAttribute("user",user);
        model.addAttribute("products",products);
        return "home";
    }

    @PostMapping("save")
    public String saveProduct(Model model,@AuthenticationPrincipal User user,
    @RequestParam("name") String name,
    @RequestParam("quantity") long quantity,
    @RequestParam("price") long price,
    @RequestParam("image") MultipartFile image) {
        storageService.store(image);
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setImage(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getImage(image.getOriginalFilename())).toString());
        System.out.println(product.getImage());
        productService.save(product);
        model.addAttribute("user", user);
        return "redirect:/cpanel";
    }

    @GetMapping("/images/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable("name") String imageName) {
        Resource resource = storageService.loadAsResource(imageName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
    }

    @GetMapping("/{id}")
    public String productDetail(@AuthenticationPrincipal User user,@PathVariable("id") long id,Model model) {
        Product p = productService.findById(id);
        model.addAttribute("user",user);
        model.addAttribute("product",p);
        model.addAttribute("comments",productService.loadComments(p).isEmpty()?null:productService.loadComments(p));
        return "product";
    }

    @PostMapping("/comment")
    public String commentOnPost(Model model,@AuthenticationPrincipal User user,
    @RequestParam("commentText") String commentText,
    @RequestParam("productId") String id) {
        log.info("Hello");
        Comment comment = new Comment();
        comment.setCommentText(commentText);
    
        productService.saveComment(comment,user,productService.findById(Long.valueOf(id)));
        return "redirect:/"+productDetail(user, Long.valueOf(id), model)+"/"+id;
    }
}
