package curso.microservicios.productos.services;

import curso.microservicios.productos.models.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> allProducts(String timeOut) throws InterruptedException;
    Producto getProduct(Long id);
}
