package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.Especies;
import com.fish.fishNet.Model.Lote;
import com.fish.fishNet.Model.Proveedor;
import com.fish.fishNet.Model.UnidadProductiva;
import com.fish.fishNet.Service.EspeciesService;
import com.fish.fishNet.Service.ProveedorService;
import com.fish.fishNet.Service.UnidadProductivaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.LoteRepository;
import com.fish.fishNet.Service.LoteService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class LoteServiceImpl extends BaseServiceImpl<Lote, Integer> implements LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private UnidadProductivaService unidadProductivaService;

    @Autowired
    private EspeciesService especiesService;


    public LoteServiceImpl(BaseRespository<Lote, Integer> baseRespository){

        super(baseRespository);
    }

    @Override
    public Lote save(Lote lote) {
        // animalesInicial toma el valor de numeroAnimales
        try {
            if (lote.getAnimalesInicial() == null) {
                lote.setAnimalesInicial(lote.getNumeroAnimales());
                return super.save(lote);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el lote", e);
        }
        return lote;
    }


    @Override
    public List<Lote> findAll() {
        try {
            List<Lote> lote = super.findAll();
            lote.forEach(this::calcularDias);
            return lote;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al calcular dias.", e);
        }

    }

    @Override
    public Lote findById(Integer id) {
        try {
            Lote lote = super.findById(id);
            if (lote != null) {
                calcularDias(lote);
            }
            return lote;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al calcular dias.", e);
        }
    }

    private void calcularDias(Lote lote) {
        if (lote.getFechaSiembra() != null) {
            LocalDate fechaActual = LocalDate.now();
            lote.setDiasDeSiembra(ChronoUnit.DAYS.between(lote.getFechaSiembra(), fechaActual));
        }
    }

    @Override
    @Transactional
    public Lote update(Integer id, Lote lote) throws Exception {
        try {
            // Obtener el lote existente por su ID
            Lote loteExistente = findById(id);

            System.out.print(loteExistente);

            if (loteExistente == null) {
                System.out.print(" Antes de validar lote" );
                throw new Exception("El lote no existe.");
            }

            // Verificar si hay salidas de animales relacionadas antes de permitir la actualización
            if (loteRepository.salenAnimales(id)) {
                System.out.print(" Durante ver salida");
                throw new Exception("No se puede actualizar el lote porque tiene salidas de animales relacionadas.");
            }

            // Actualizar los campos del lote existente con los valores del lote recibido
            loteExistente.setNombreLote(lote.getNombreLote());
            loteExistente.setNumeroAnimales(lote.getNumeroAnimales());
            loteExistente.setFechaSiembra(lote.getFechaSiembra());

            // Corrige las llamadas a los servicios para obtener las entidades relacionadas
            Proveedor proveedor = proveedorService.findById(lote.getProveedor().getId());
            UnidadProductiva unidadProductiva = unidadProductivaService.findById(lote.getUnidadProductiva().getId());
            Especies especies = especiesService.findById(lote.getEspecies().getId());

            // Verifica si las entidades se encontraron en la base de datos
            if (Objects.isNull(proveedor) || Objects.isNull(unidadProductiva) || Objects.isNull(especies)) {
                throw new Exception("Proveedor, Unidad Productiva o Especies no encontrados.");
            }

            // Establece las relaciones en el lote existente
            loteExistente.setProveedor(proveedor);
            loteExistente.setUnidadProductiva(unidadProductiva);
            loteExistente.setEspecies(especies);

            // Luego, guarda el lote actualizado en la base de datos
            return loteRepository.save(loteExistente);

        } catch (Exception e) {
        // Manejo de excepciones...
        throw new Exception("Error al actualizar el lote." + e.getMessage());
    }

    }
}
