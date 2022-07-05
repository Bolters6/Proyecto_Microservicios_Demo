package curso.microservicios.productos.services;

import ch.qos.logback.core.util.TimeUtil;
import curso.microservicios.productos.models.Producto;
import curso.microservicios.productos.repository.ProductosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    ProductosRepo productosRepo;

    @Value("${server.port}")
    @Autowired
    private String puerto;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> allProducts(String timeOut) throws InterruptedException {
        if(timeOut.equals("y")) TimeUnit.SECONDS.sleep(5L); //para una slowcall, funcionara pero lo vera como un problema porque no acepta mas del 50% de slowcalls
        if(timeOut.equals("s")) TimeUnit.SECONDS.sleep(3L); //si para lanzar el timeout
        return productosRepo.findAll().stream().map(producto -> {
            producto.setPuerto(puerto);
            return producto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProduct(Long id) {
        Producto producto =  productosRepo.findById(id).orElseThrow(() -> new IllegalStateException("Product Not Found"));
        producto.setPuerto(puerto);
        return producto;
    }
}
