package curso.microservicios.items.models;

import com.example.libreria_commons.models.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Producto producto;
    private Integer cantidad;

    public Double getTotal(){
        return this.producto.getPrecio() * this.cantidad.doubleValue();
    }
}
