package curso.microservicios.items.services;

import com.example.libreria_commons.models.Producto;
import curso.microservicios.items.models.Item;

import java.util.List;

public interface ItemService {
    public List<Item> listaItems(String timeOut);
    public Item getItem(Long id, Integer cantidad);

    public Item addItem(Producto producto, Integer cantidad);
    public Item updateItem(Long id, Producto producto, Integer cantidad);

    public void deleteItem(Long id);


}
