package curso.microservicios.items.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable {
    private Long id;
    private String nombre;
    private Double precio;
    private Date createdAt;
    private String puerto;
}
