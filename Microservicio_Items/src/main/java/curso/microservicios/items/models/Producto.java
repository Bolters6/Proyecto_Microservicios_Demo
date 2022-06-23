package curso.microservicios.items.models;

import lombok.Data;

import java.util.Date;
@Data
public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    private Date createdAt;
    private String puerto;
}
