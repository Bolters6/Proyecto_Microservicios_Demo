package curso.microservicios.productos.controller;

import curso.microservicios.productos.models.Producto;
import curso.microservicios.productos.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Producto>> getAllProducts(){
        return ResponseEntity.ok(productoService.allProducts());
    }

    @GetMapping(path = "/getproducto/{id}")
    public ResponseEntity<Producto> getAProduct(@PathVariable Long id){
        return ResponseEntity.ok(productoService.getProduct(id));
    }

}
