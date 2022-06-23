package curso.microservicios.items.controllers;

import curso.microservicios.items.models.Item;
import curso.microservicios.items.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//SI QUIERO USAR QUALIFIER NO PUEDO USAR LOMBOK, NI INYECTOR POR CONSTRUCTOR COMO INJECTOR PERO USAR AUTOWIRED, QUALIFIER ME PERMITE
//ELEGIR QUE IMPLEMENTACION INYECTAR DE UNA INTERFAZ COMUN, COMO TAMBIEN PUEDO USAR @PRIMARY EN LA IMPLEMENTACION
//QUE QUIERO QUE ELIJA

//@RequiredArgsConstructor
@RestController
@RequestMapping(path="/item")
public class ItemController {
    //@Qualifier("feignclientservice")
    @Qualifier("resttemplateservice")
    @Autowired
    private ItemService itemService;

    @GetMapping(path = "/listaitems")
    public ResponseEntity<List<Item>> getListaItems(){
        return ResponseEntity.ok().body(itemService.listaItems());
    }

    @GetMapping(path = "/get/{id}/cantidad/{cantidad}")
    public ResponseEntity<Item> getItem(@PathVariable Long id, @PathVariable Integer cantidad){
        return ResponseEntity.ok().body(itemService.getItem(id, cantidad));
    }
}
