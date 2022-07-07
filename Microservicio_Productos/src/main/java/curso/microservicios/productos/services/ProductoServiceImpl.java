package curso.microservicios.productos.services;

import com.example.libreria_commons.models.Producto;
import curso.microservicios.productos.repository.ProductosRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    ProductosRepo productosRepo;

    @Value("${server.port}")
    @Autowired
    private String puerto;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> allProducts(String timeOut) throws InterruptedException {
        if (timeOut.equals("y"))
            TimeUnit.SECONDS.sleep(5L); //para una slowcall, funcionara pero lo vera como un problema porque no acepta mas del 50% de slowcalls
        if (timeOut.equals("s")) TimeUnit.SECONDS.sleep(3L); //si para lanzar el timeout
        return productosRepo.findAll().stream().map(producto -> {
            producto.setPuerto(puerto);
            return producto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProduct(Long id) throws IllegalStateException{
        Producto producto = productosRepo.findById(id).orElseThrow(() -> new IllegalStateException("Product Not Found"));
        producto.setPuerto(puerto);
        return producto;
    }

    @Override
    public Producto addProduct(Producto producto) throws IllegalArgumentException {
        if (productosRepo.findByNombre(producto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Producto ya Existente");
        }
        return productosRepo.save(producto);
    }


    @Override
    public Producto updateProduct(Long id, Producto producto) throws IllegalArgumentException{
        return productosRepo.findById(id).map(productoExistente -> {
            productoExistente.setCreatedAt(producto.getCreatedAt());
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setPrecio(producto.getPrecio());
            return productoExistente;
        }).orElseThrow(() -> new IllegalArgumentException("Producto no Existe"));
    }

    @Override
    public void deleteProduct(Long id) throws IllegalArgumentException {
        if (productosRepo.findById(id).isPresent()) {
            productosRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Producto no Existente para la Eliminacion");
        }
    }
}
