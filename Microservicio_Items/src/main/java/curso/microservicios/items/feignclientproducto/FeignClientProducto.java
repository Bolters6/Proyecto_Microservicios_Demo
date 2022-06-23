package curso.microservicios.items.feignclientproducto;

import curso.microservicios.items.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservicio-productos",path="/producto")
public interface FeignClientProducto {

    @GetMapping("/all")
    public List<Producto> listaProductos();

    @GetMapping("/getproducto/{id}")
    public Producto getProducto(@PathVariable  Long id);

}
