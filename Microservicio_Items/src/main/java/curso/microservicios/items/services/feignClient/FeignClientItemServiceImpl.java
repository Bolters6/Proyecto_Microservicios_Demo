package curso.microservicios.items.services.feignClient;

import com.example.libreria_commons.models.Producto;
import curso.microservicios.items.feignclientproducto.FeignClientProducto;
import curso.microservicios.items.models.Item;
import curso.microservicios.items.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("feignclientservice")
@RequiredArgsConstructor
//@Primary ESTA NOTACION ME PERMITE DECIRLE A SPRING QUE ESTA ES LA IMPLEMENTACION QUE QUIERO QUE INYECTE CUANDO UTILIZO LA INTERFAZ
//ESTO CUANDO NO UTILIZO @QUALIFIER
public class FeignClientItemServiceImpl implements ItemService {

    private final FeignClientProducto feignClientProducto;
    @Override
    public List<Item> listaItems(String timeOut) {
        return feignClientProducto.listaProductos()
                .stream()
                .map(producto -> new Item(producto,1))
                .collect(Collectors.toList());
    }
    @Override
    public Item getItem(Long id, Integer cantidad) {
        return new Item(feignClientProducto.getProducto(id), cantidad);
    }

    @Override
    public Item addItem(Producto producto, Integer cantidad) {
        return null;
    }

    @Override
    public Item updateItem(Long id, Producto producto, Integer cantidad) {
        return null;
    }

    @Override
    public void deleteItem(Long id) {

    }
}
