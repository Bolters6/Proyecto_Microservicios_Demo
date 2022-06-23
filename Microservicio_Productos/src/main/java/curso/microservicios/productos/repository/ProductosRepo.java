package curso.microservicios.productos.repository;

import curso.microservicios.productos.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepo extends JpaRepository<Producto, Long> {

}

