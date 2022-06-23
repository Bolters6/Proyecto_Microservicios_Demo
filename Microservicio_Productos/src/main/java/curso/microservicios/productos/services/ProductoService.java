package curso.microservicios.productos.services;

import curso.microservicios.productos.models.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> allProducts();
    Producto getProduct(Long id);
}
