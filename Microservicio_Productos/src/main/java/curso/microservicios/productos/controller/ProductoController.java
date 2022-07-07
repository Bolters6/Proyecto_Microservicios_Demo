package curso.microservicios.productos.controller;

import com.example.libreria_commons.models.Producto;
import curso.microservicios.productos.services.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/producto")
public class ProductoController {

    private final Logger log = LoggerFactory.getLogger(ProductoController.class);
    @Autowired
    private ProductoService productoService;

    @GetMapping(path = "/all/{timeout}")
    public ResponseEntity<List<Producto>> getAllProducts(@PathVariable(name = "timeout") String timeOut) throws InterruptedException {
        return ResponseEntity.ok(productoService.allProducts(timeOut));
    }

    @GetMapping(path = "/getproducto/{id}")
    public ResponseEntity<Producto> getAProduct(@PathVariable Long id){
        return ResponseEntity.ok(productoService.getProduct(id));
    }

    @PostMapping(path = "/addproducto")
    public ResponseEntity<Producto> addProduct(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.addProduct(producto));
    }

    @PutMapping(path = "/updateproducto/{id}")
    public ResponseEntity<Producto> updateProduct(@RequestBody Producto producto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.updateProduct(id, producto));
    }

    @DeleteMapping(path = "/deleteproducto/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productoService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
