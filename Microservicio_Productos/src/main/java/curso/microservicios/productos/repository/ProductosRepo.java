package curso.microservicios.productos.repository;

import com.example.libreria_commons.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductosRepo extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);
}

