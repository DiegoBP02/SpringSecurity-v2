package javajwt.jwt.repository;

import javajwt.jwt.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query(value = "select * from product where usuario_id = :id", nativeQuery = true)
    List<Product> findByUsuarioId(Long id);
}
