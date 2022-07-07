package curso.microservicios.items.services.restTemplate;

import com.example.libreria_commons.models.Producto;
import curso.microservicios.items.models.Item;
import curso.microservicios.items.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
@Service("resttemplateservice")
@RequiredArgsConstructor
public class RestTemplateItemServiceImpl implements ItemService {

    private final RestTemplate restTemplate;

    @Override
    public List<Item> listaItems(String timeOut) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("timeout", timeOut);
        Optional<List<Producto>> productos = Optional.ofNullable(Arrays.asList(restTemplate.getForObject("http://microservicio-productos/producto/all/{timeout}", Producto[].class, uriVariables)));
        return productos.orElseThrow(() -> new IllegalStateException("No Hay Productos")).stream().map(producto -> new Item(producto, 1)).collect(Collectors.toList());
    }

    @Override
    public Item getItem(Long id, Integer cantidad) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", id.toString());
        Producto producto = restTemplate.getForObject("http://microservicio-productos/producto/getproducto/{id}", Producto.class, uriVariables);
        return new Item(producto, cantidad);
    }

    @Override
    public Item addItem(Producto producto, Integer cantidad) {
        restTemplate.exchange("http://microservicio-productos/producto/addproducto", HttpMethod.POST, new HttpEntity<Producto>(producto), Producto.class);
        return new Item(producto, cantidad);
    }

    @Override
    public Item updateItem(Long id, Producto producto, Integer cantidad) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", id.toString());
        restTemplate.exchange("http://microservicio-productos/producto/updateproducto/{id}", HttpMethod.PUT, new HttpEntity<Producto>(producto), Producto.class, uriVariables);
        return new Item(producto, cantidad);
    }

    @Override
    public void deleteItem(Long id) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", id.toString());
        //restTemplate.delete("http://microservicio-productos/producto/deleteproducto/{id}", uriVariables);
        restTemplate.exchange("http://microservicio-productos/producto/deleteproducto/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, uriVariables);
    }
}
