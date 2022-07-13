package curso.microservicios.items.controllers;

import com.example.libreria_commons.models.Producto;
import curso.microservicios.items.models.Item;
import curso.microservicios.items.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

//SI QUIERO USAR QUALIFIER NO PUEDO USAR LOMBOK, NI INYECTOR POR CONSTRUCTOR COMO INJECTOR PERO USAR AUTOWIRED, QUALIFIER ME PERMITE
//ELEGIR QUE IMPLEMENTACION INYECTAR DE UNA INTERFAZ COMUN, COMO TAMBIEN PUEDO USAR @PRIMARY EN LA IMPLEMENTACION
//QUE QUIERO QUE ELIJA

//@RequiredArgsConstructor
@RefreshScope
@RestController
@Slf4j
@RequestMapping(path="/item")
public class ItemController {

    @Autowired
    private Environment environment;
    @Autowired
    private CircuitBreakerFactory breakerFactory;

    //@Qualifier("feignclientservice")
    @Qualifier("resttemplateservice")
    @Autowired
    private ItemService itemService;

    @Value("${configuracion.texto}")
    private String texto;
    //timeout en el pathvariable para simular una falla de timeout , si quiero simular la falla mando un "y"
    @GetMapping(path = "/listaitems/{timeout}")
    public ResponseEntity<List<Item>> getListaItems(
            @PathVariable(name = "timeout") String timeOut,
            @RequestParam(name = "greetings", required = false) String greetingsParam,
            @RequestHeader(name="greetings", required = false) String greetingsHeader){
        log.info(greetingsHeader + greetingsParam);
        return breakerFactory.create("items").run(() -> ResponseEntity.ok().body(itemService.listaItems(timeOut)), e -> alternativeGetListaItems(timeOut, greetingsParam, greetingsHeader, e));
    }
    //SE PUEDE HACER DE 2 MANERAS O INYECTANDO EL CIRCUITBREAKERFACTORY Y USANDOLO LUEGO COMO EN EL METODO DE ARRIBA
    //O UTILIZANDO ANOTACIONES COMO EL METODO DE ABAJO, EL INCONVENIENTE ES QUE USANDO ANOTACIONES ASI SE SEPARA LO QUE ES EL TIMELIMITER Y EL CIRCUITBREAKER
    //QUIERO DECIR QUE YA NO DARA PROBLEMAS NI EXCEPCION POR TIMEOUT, AMENOS QUE LO COMBINEMOS CON @TimeLimiter
    //OTRO INCONVENIENTE DEL METODO DE USAR ANOTACIONES COMO EL DE ABAJO ES QUE EL METODO A LLAMAR PARA EL FALLBACK TIENE QUE
    //SER BASICAMENTE IDENTICO AL ORIGINAL , OSEA QUE LLEVE LOS MISMOS ARGUMENTOS Y EL MISMO RETORNO
    //MIENTRAS QUE USANDO EL DE ARRIBA POR INYECCION PUEDES USAR BASICAMENTE CUALQUIER METODO QUE CLARO TENGA EL MISMO RETORNO, PERO  ARGUMENTOS NO
    //OTRA COSA ES QUE USANDO LA INYECCION DEL FACTORY TE QUITAS EL PROBLEMA DE TENER QUE ANOTAR LOS METODOS Y COMBINAR
    //LAS ANOTACIONES DE TIMELIMITER Y CIRCUITBREAKER Y DE PASO TENER QUE ENVOLVER EL METODO CON EL COMPLETABLEFUTURE
    @CircuitBreaker(name = "items", fallbackMethod = "alternativeGetListaItems")
    @GetMapping(path = "/listaitems2/{timeout}")
    public ResponseEntity<List<Item>> getListaItems2(
            @PathVariable(name = "timeout") String timeOut,
            @RequestParam(name = "greetings", required = false) String greetingsParam,
            @RequestHeader(name="greetings", required = false) String greetingsHeader){
        log.info(greetingsHeader + greetingsParam);
        return ResponseEntity.ok().body(itemService.listaItems(timeOut));
    }
//COMO EXPLIQUE ARRIBA TENGO QUE ENVOLVERLO EN EL COMPLETABLE FUTURE PARA QUE PUEDA SABER QUE ES UN TIMEOUT Y LLEVE EL CONTEO DEL TIEMPO
    // Y OBVIAMENTE TUVE QUE HACER OTRO METODO ALTERNATIVO PORQUE TIENE QUE DEVOLVER TAMBIEN UN COMPLETABLEFUTURE
    //EL TIMELIMITER TAMBIEN SE PUEDE USAR POR SEPARADO , PERO NO SE TENDRIA LA PROPIEDAD DE CIRCUITBRAKER
    //PUEDES TAMBIEN PONERLE EL FALLBACK METHOD Y SOLO SALE CUANDO HAY UN TIMEOUT
    @CircuitBreaker(name = "items", fallbackMethod = "alternativeGetListaItems2")
    @TimeLimiter(name = "items")
    @GetMapping(path = "/listaitems3/{timeout}")
    public CompletableFuture<ResponseEntity<List<Item>>> getListaItems3(
            @PathVariable(name = "timeout") String timeOut,
            @RequestParam(name = "greetings", required = false) String greetingsParam,
            @RequestHeader(name="greetings", required = false) String greetingsHeader){
        log.info(greetingsHeader + greetingsParam);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().body(itemService.listaItems(timeOut)));
    }

    private ResponseEntity<List<Item>> alternativeGetListaItems(String timeOut, String greetingsParam, String greetingsHeader, Throwable e){
        log.error(e.getMessage());
        log.info(greetingsHeader + greetingsParam);
        return ResponseEntity.ok().body(List.of(new Item(new Producto(null, "ERROR", 0.0, Date.valueOf(LocalDate.now())), 1)));
    }
    private CompletableFuture<ResponseEntity<List<Item>>> alternativeGetListaItems2(String timeOut, String greetingsParam, String greetingsHeader, Throwable e){
        log.error(e.getMessage());
        log.info(greetingsHeader + greetingsParam);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().body(List.of(new Item(new Producto(null, "ERROR", 0.0, Date.valueOf(LocalDate.now())), 1))));
    }
    @GetMapping(path = "/get/{id}/cantidad/{cantidad}")
    public ResponseEntity<Item> getItem(@PathVariable Long id, @PathVariable Integer cantidad){
        return breakerFactory.create("items").run(() -> ResponseEntity.ok().body(itemService.getItem(id, cantidad)), e -> alternativeGetItem(id, cantidad, e));
    }
    @CircuitBreaker(name = "items")
    @GetMapping(path = "/get2/{id}/cantidad/{cantidad}")
    public ResponseEntity<Item> getItem2(@PathVariable Long id, @PathVariable Integer cantidad){
        return ResponseEntity.ok().body(itemService.getItem(id, cantidad));
    }

    private ResponseEntity<Item> alternativeGetItem(Long id, Integer cantidad, Throwable e){
        log.error(e.getMessage());
        return ResponseEntity.ok().body(new Item(new Producto(id, "ERROR", 0.0, Date.valueOf(LocalDate.now())), cantidad));
    }
    @PostMapping(path = "/additem")
    public ResponseEntity<?> addItem(@RequestBody Producto producto, @RequestParam Integer cantidad){
        return breakerFactory.create("items")
                .run(() -> ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItem(producto, cantidad)), this::alternativeNewCrudMethods);
    }

    @PutMapping(path = "/updateitem/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Producto producto, @RequestParam Integer cantidad){
        return breakerFactory.create("items")
                .run(() -> ResponseEntity.status(HttpStatus.CREATED).body(itemService.updateItem(id, producto, cantidad)), this::alternativeNewCrudMethods);
    }

    @DeleteMapping(path = "/deleteitem/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        return breakerFactory.create("items").run(() -> {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        }, this::alternativeNewCrudMethods);
    }

    private ResponseEntity<?> alternativeNewCrudMethods(Throwable e){
        log.error(e.getMessage() + "\n" + environment.getProperty("server.port"));
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
    @GetMapping(path = "/getconfig")
    public ResponseEntity<Map<String, String>> getEnvPropertiesConfig(@Value("${server.port}") String puerto){
        Map<String, String> json = new HashMap<>();
        json.put("puerto", puerto);
        json.put("texto", texto);
        if(environment.getActiveProfiles()[0].equals("dev")){
            json.put("developer", environment.getProperty("configuracion.developer.name"));
        }
        return ResponseEntity.ok().body(json);
    }
}
