package curso.microservicios.items.services;

import curso.microservicios.items.models.Item;

import java.util.List;

public interface ItemService {
    public List<Item> listaItems(String timeOut);
    public Item getItem(Long id, Integer cantidad);
}
