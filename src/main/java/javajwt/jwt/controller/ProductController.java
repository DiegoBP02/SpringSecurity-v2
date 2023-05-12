package javajwt.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    public void saveProduct(@RequestBody Product product){
        this.productRepository.save(product);
    }

    @GetMapping("/product")
    public List<Product> getProducts(){
        return (List<Product>) productRepository.findAll();
    }

    @DeleteMapping("/product/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathVariable Long id){
        this.productRepository.deleteById(id);
    }
}
