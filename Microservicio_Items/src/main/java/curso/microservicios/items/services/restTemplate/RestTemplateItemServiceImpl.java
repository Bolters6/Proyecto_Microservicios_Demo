package curso.microservicios.items.services.restTemplate;

import curso.microservicios.items.models.Item;
import curso.microservicios.items.models.Producto;
import curso.microservicios.items.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Service("resttemplateservice")
@RequiredArgsConstructor
public class RestTemplateItemServiceImpl implements ItemService {

    private final RestTemplate restTemplate;

    @Override
    public List<Item> listaItems() {
        List<Producto> productos = Arrays.asList(restTemplate.getForObject("http://microservicio-productos/producto/all", Producto[].class));
        return productos.stream().map(producto -> new Item(producto, 1)).collect(Collectors.toList());
    }

    @Override
    public Item getItem(Long id, Integer cantidad) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", id.toString());
        Producto producto = restTemplate.getForObject("http://microservicio-productos/producto/getproducto/{id}", Producto.class, uriVariables);
        return new Item(producto, cantidad);
    }
}
