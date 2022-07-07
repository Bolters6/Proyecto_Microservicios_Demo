package curso.microservicios.productos.services;


import com.example.libreria_commons.models.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> allProducts(String timeOut) throws InterruptedException;
    Producto getProduct(Long id) throws IllegalStateException;
    Producto addProduct(Producto producto) throws IllegalArgumentException;
    Producto updateProduct(Long id, Producto producto) throws IllegalArgumentException;
    void deleteProduct(Long id) throws IllegalArgumentException;
}
