package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.Proveedor;
import com.fish.fishNet.Model.TipoAlimento;
import com.fish.fishNet.Repository.SalidaAlimentosRepository;
import com.fish.fishNet.Service.ProveedorService;
import com.fish.fishNet.Service.TipoAlimentoService;
import com.fish.fishNet.Dtos.EntradaAlimentosDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.EntradaAlimentosRepository;
import com.fish.fishNet.Service.EntradaAlimentosService;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.Optional;

@Service
public class EntradaAlimentosServiceImpl extends BaseServiceImpl<EntradaAlimentos, Integer> implements EntradaAlimentosService {

    private static final String MENSAJE_ERROR_ENTRADA = "La entrada de alimentos ya existe";

    @Autowired
    private EntradaAlimentosRepository entradaAlimentosRepository;
    @Autowired
    private TipoAlimentoService tipoAlimentoService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private SalidaAlimentosRepository salidaAlimentosRepository;

    public EntradaAlimentosServiceImpl(BaseRespository<EntradaAlimentos, Integer> baseRespository){
        super(baseRespository);
    }

    @Override
    public boolean entradaAlimentosYaExiste(EntradaAlimentosDTO nuevaEntradaDto) throws Exception {
        EntradaAlimentos entradaAlimentos = new EntradaAlimentos();
        LocalDate fechaVencimiento = nuevaEntradaDto.getFechaVencimiento();
        String numeroFactura = nuevaEntradaDto.getNumeroFactura();
        String registroIca = nuevaEntradaDto.getRegistroIca();
        double numeroKilos = nuevaEntradaDto.getNumeroKilos();
        int tipoAlimentoId = nuevaEntradaDto.getTipoAlimentoId();
        int proveedorId = nuevaEntradaDto.getProveedorId();

        // Realiza la verificación en el repositorio
        boolean existeEntrada = entradaAlimentosRepository.existByEntradaAlimento(
                numeroFactura, tipoAlimentoId
        );

        if (existeEntrada) {
            // Si existe el entrada hay excepción
            throw new IllegalArgumentException(MENSAJE_ERROR_ENTRADA);
        } else {
            // Si no existe, sigue la creación de la entrada de alimento
            EntradaAlimentos nuevaEntrada = new EntradaAlimentos();
            nuevaEntrada.setFechaVencimiento(fechaVencimiento);
            nuevaEntrada.setNumeroFactura(numeroFactura);
            nuevaEntrada.setRegistroIca(registroIca);
            nuevaEntrada.setNumeroKilos(numeroKilos);

            TipoAlimento tipoAlimento = tipoAlimentoService.findById(tipoAlimentoId);
            if(tipoAlimento != null ) {
                nuevaEntrada.setTipoAlimento(tipoAlimento);
            }

            Proveedor proveedor = proveedorService.findById(proveedorId);
            if(proveedor != null) {
                nuevaEntrada.setProveedor(proveedor);
            }

            // kilosInicial toma el valor de numeroKilos
            if (nuevaEntrada.getKilosInicial() == null) {
                nuevaEntrada.setKilosInicial(nuevaEntrada.getNumeroKilos());
            }

            // Se realiza set para que muestre la fecha de creación en la base de datos
            nuevaEntrada.setFechaCreacion(LocalDate.now());
            entradaAlimentosRepository.save(nuevaEntrada);
        }
        return true;
    }

    @Override
    public EntradaAlimentos update(Integer id, EntradaAlimentos entradaAlimentos) {
        try {
            // Obtener la entrada de alimento por su Id
            EntradaAlimentos entradaExistente = findById(id);

            // Verificar si ya se ha realizado una salida para esta entrada
            if (!entradaAlimentosRepository.tieneSalida(entradaExistente.getNumeroFactura(), entradaExistente.getTipoAlimento().getId())) {
                // Si no hay salida realizada, permitir la actualización
                entradaExistente.setNumeroFactura(entradaAlimentos.getNumeroFactura());
                entradaExistente.setFechaVencimiento(entradaAlimentos.getFechaVencimiento());
                entradaExistente.setRegistroIca(entradaAlimentos.getRegistroIca());
                entradaExistente.setNumeroKilos(entradaAlimentos.getNumeroKilos());
                // Actualizar otras propiedades según sea necesario
                entradaExistente.setKilosInicial(entradaAlimentos.getNumeroKilos());
                // Guardar la entrada de alimentos actualizada

                return entradaAlimentosRepository.save(entradaExistente);
            } else {
                // Si ya se ha realizado una salida, lanzar una excepción o manejar según tus necesidades
                throw new IllegalStateException("No se puede actualizar la entrada de alimentos porque ya se ha realizado una salida.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la Entrada.", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) throws Exception {
        try {
            Optional<EntradaAlimentos> entradaAlimentosOptional = baseRepository.findById(id);
            if (entradaAlimentosOptional.isPresent()) {
                EntradaAlimentos entradaAlimentos = entradaAlimentosOptional.get();

                // Verificar si ya se ha realizado una salida para esta entrada
                if (!salidaAlimentosRepository.tieneSalida(entradaAlimentos.getNumeroFactura(), entradaAlimentos.getTipoAlimento().getId())) {
                    // Si no hay salida realizada, permitir la eliminación
                    baseRepository.deleteById(id);
                    return true;
                } else {
                    // Si ya se ha realizado una salida, lanzar una excepción o manejar según tus necesidades
                    throw new IllegalStateException("No se puede eliminar la entrada de alimentos porque ya se ha realizado una salida.");
                }
            }
            return false;
        } catch (Exception e) {
            throw new Exception("Error al eliminar EntradaAlimentos", e);
        }
    }

}
