package javajwt.jwt.controller;

import jakarta.annotation.security.RolesAllowed;
import javajwt.jwt.dto.ProductDto;
import javajwt.jwt.entity.Product;
import javajwt.jwt.entity.Usuario;
import javajwt.jwt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    public void saveProduct(@RequestBody ProductDto productDto){

        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Product product = new Product(productDto, usuario.getId());

        this.productRepository.save(product);
    }

    @GetMapping("/product")
    public List<Product> getProducts(){

        Usuario usuario = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Long id = usuario.getId();

        var role = usuario.getAuthorities().stream().toList().get(0).getAuthority();

        if("ADMIN".equals(role)){
            return (List<Product>) productRepository.findAll();
        }
        return productRepository.findByUsuarioId(id);
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasAuthority(\"ADMIN\")") // @RolesAllowed only works for roles with 'ROLE_' prefix
    public void delete(@PathVariable Long id){
        this.productRepository.deleteById(id);
    }
}
