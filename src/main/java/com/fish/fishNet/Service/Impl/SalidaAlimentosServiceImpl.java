package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.*;
import com.fish.fishNet.Repository.*;
import com.fish.fishNet.Dtos.SalidaAlimentosDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fish.fishNet.Service.SalidaAlimentosService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SalidaAlimentosServiceImpl extends BaseServiceImpl<SalidaAlimentos, Integer> implements SalidaAlimentosService {

    private static final String MENSAJE_ERROR_SALIDA = "No es posible realizar la salida, porque solo existen %s kilos disponibles";
    private static final String MENSAJE_ERROR_LOTE_TIPO_ALIMENTO = "El tipo alimento o el lote seleccionado no es valido.";
    private static final String MENSAJE_ERROR_NUMERO_FACTURA = "El número de factura %s no existe.";

    @Autowired
    private SalidaAlimentosRepository salidaAlimentosRepository;
    @Autowired
    private EntradaAlimentosRepository entradaAlimentosRepository;
    @Autowired
    private LoteRepository loteRepository;
    @Autowired
    private TipoAlimentoRepository tipoAlimentoRepository;

    public SalidaAlimentosServiceImpl(BaseRespository<SalidaAlimentos, Integer> baseRespository){
        super(baseRespository);
    }

    @Override
    public SalidaAlimentos salidaAlimento(SalidaAlimentosDto salidaAlimentosDto) throws Exception {
        EntradaAlimentos facturaEntrada = entradaAlimentosRepository.findByNumeroFactura(salidaAlimentosDto.getNumeroFactura());
        if (facturaEntrada == null) {
            throw new IllegalArgumentException(String.format(MENSAJE_ERROR_NUMERO_FACTURA, salidaAlimentosDto.getNumeroFactura()));
        }
        int kilosEntrada = entradaAlimentosRepository.totalEntradas(salidaAlimentosDto.getNumeroFactura(), salidaAlimentosDto.getTipoAlimentoId());
        int kilosSalida = salidaAlimentosRepository.totalSalidas(salidaAlimentosDto.getNumeroFactura(), salidaAlimentosDto.getTipoAlimentoId());
        int kilosDisponibles = kilosEntrada - kilosSalida;
        if (salidaAlimentosDto.getNumeroKilos() > kilosDisponibles) {
            throw new IllegalArgumentException(String.format(MENSAJE_ERROR_SALIDA, kilosDisponibles));
        } else {
            // Restar la cantidad de kilos a EntradaAlimentos
            facturaEntrada.setNumeroKilos(facturaEntrada.getNumeroKilos() - salidaAlimentosDto.getNumeroKilos());
            entradaAlimentosRepository.save(facturaEntrada);
        }
        Optional<Lote> lote = loteRepository.findById(salidaAlimentosDto.getLoteId());
        Optional<TipoAlimento> tipoAlimento = tipoAlimentoRepository.findById(salidaAlimentosDto.getTipoAlimentoId());
        if (lote.isPresent() && tipoAlimento.isPresent()) {
            SalidaAlimentos salidaAlimentos = new SalidaAlimentos(salidaAlimentosDto.getNumeroFactura(),
                    salidaAlimentosDto.getNumeroKilos(), lote.get(), tipoAlimento.get());
            // Se realiza set para que muestre la fecha de creación en la base de datos
            salidaAlimentos.setFechaCreacion(LocalDate.now());
            salidaAlimentosRepository.save(salidaAlimentos);
            return salidaAlimentos;
        }
        throw new IllegalArgumentException(MENSAJE_ERROR_LOTE_TIPO_ALIMENTO);
    }

    @Override
    public SalidaAlimentos update(Integer id, SalidaAlimentos salidaAlimentos) {
        try {
            // Obtener la salida de kilos existente por su ID
            List<SalidaAlimentos> salidaAlimentosList = salidaAlimentosRepository.findByNumeroFacturaAndTipoAlimento(salidaAlimentos.getNumeroFactura(), salidaAlimentos.getTipoAlimento());
            SalidaAlimentos salidaAlimentosSaved = null;
            if(!salidaAlimentosList.isEmpty()) {
                int totaSalida = 0;
                Optional<EntradaAlimentos> entradaAlimentosOptional = entradaAlimentosRepository.findByNumeroFacturaAndTipoAlimento(salidaAlimentos.getNumeroFactura(), salidaAlimentos.getTipoAlimento());
                if(entradaAlimentosOptional.isPresent()) {
                    for (SalidaAlimentos sa : salidaAlimentosList) {
                        if (!Objects.equals(sa.getId(), id)) {
                            totaSalida += sa.getNumeroKilos();
                        } else {
                            salidaAlimentosSaved = sa;
                        }
                    }
                    totaSalida += salidaAlimentos.getNumeroKilos();
                    if(entradaAlimentosOptional.get().getKilosInicial() > totaSalida) {
                        int restaKilos = (int) (entradaAlimentosOptional.get().getKilosInicial() - totaSalida);
                        salidaAlimentosSaved.setNumeroKilos(salidaAlimentos.getNumeroKilos());
                        entradaAlimentosOptional.get().setNumeroKilos(restaKilos);
                        entradaAlimentosRepository.save(entradaAlimentosOptional.get());
                        salidaAlimentosRepository.save(salidaAlimentosSaved);
                        return salidaAlimentos;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la Entrada.", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) throws Exception {
        try {
            Optional<SalidaAlimentos> salidaAlimentosOptional = baseRepository.findById(id);
            if (salidaAlimentosOptional.isPresent()) {
                SalidaAlimentos salidaAlimentos = salidaAlimentosOptional.get();
                // Buscar la EntradaAlimentos correspondiente
                EntradaAlimentos entradaAlimentos = entradaAlimentosRepository.findByNumeroFactura(salidaAlimentos.getNumeroFactura());
                if (entradaAlimentos != null && salidaAlimentos.getTipoAlimento().equals(entradaAlimentos.getTipoAlimento())) {
                    // Sumar la cantidad de kilos a la entidad EntradaAlimentos
                    entradaAlimentos.setNumeroKilos(entradaAlimentos.getNumeroKilos() + salidaAlimentos.getNumeroKilos());
                    entradaAlimentosRepository.save(entradaAlimentos);
                }
                baseRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("Error al eliminar SalidaAlimentos", e);
        }
    }
}