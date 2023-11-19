package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.Proveedor;
import com.fish.fishNet.Model.TipoAlimento;
import com.fish.fishNet.Repository.SalidaAlimentosRepository;
import com.fish.fishNet.Service.ProveedorService;
import com.fish.fishNet.Service.TipoAlimentoService;
import com.fish.fishNet.Dtos.EntradaAlimentosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.EntradaAlimentosRepository;
import com.fish.fishNet.Service.EntradaAlimentosService;

import java.time.LocalDate;

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
    public boolean entradaAlimentosYaExiste(EntradaAlimentosDto nuevaEntradaDto) throws Exception {

        EntradaAlimentos entradaAlimentos = new EntradaAlimentos();

        LocalDate fechaVencimiento = nuevaEntradaDto.getFechaVencimiento();
        String numeroFactura = nuevaEntradaDto.getNumeroFactura();
        String registroIca = nuevaEntradaDto.getRegistroIca();
        int numeroKilos = nuevaEntradaDto.getNumeroKilos();
        int tipoAlimentoId = nuevaEntradaDto.getTipoAlimentoId();
        int proveedorId = nuevaEntradaDto.getProveedorId();


        // Realiza la verificación en el repositorio
        boolean existeEntrada = entradaAlimentosRepository.existByEntradaAlimento(
                numeroFactura, tipoAlimentoId
        );

        if (existeEntrada) {
            // Excepción personalizada si la entrada ya existe
            throw new IllegalArgumentException(MENSAJE_ERROR_ENTRADA);
        } else {
            // Si no existe, puedes continuar con la creación de la entrada
            EntradaAlimentos nuevaEntrada = new EntradaAlimentos();
            nuevaEntrada.setFechaVencimiento(fechaVencimiento);
            nuevaEntrada.setNumeroFactura(numeroFactura);
            nuevaEntrada.setRegistroIca(registroIca);
            nuevaEntrada.setNumeroKilos(numeroKilos);

            // Asignar el tipo de alimento y proveedor (debes obtenerlos de la base de datos)
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
            // Obtener la entrada de alimentos existente por su ID
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

}
