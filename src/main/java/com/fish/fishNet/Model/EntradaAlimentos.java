package com.fish.fishNet.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "entrada_alimentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntradaAlimentos extends Base {
   
    @Column(name = "numero_factura", nullable = false)
    private String numeroFactura;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;
    
    @Column(name = "registro_ica", nullable = false)
    private String registroIca;
    
    @Column(name = "numero_kilos", nullable = false)
    private Double numeroKilos;

    @Column(name = "kilos_inicial")
    private Double kilosInicial;


    // Relaciones
    
    // TipoAlimento
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_alimento_id", nullable = false, foreignKey = @ForeignKey(name = "fk_entrada_alimentos_tipo_alimento"))
    private TipoAlimento tipoAlimento;

    // Proveedor
    @ManyToOne(optional = false)
    @JoinColumn(name = "proveedor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_entrada_alimentos_proveedor"))
    private Proveedor proveedor;
}
