package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Model.Lote;
import com.fish.fishNet.Model.TipoAlimento;
import com.fish.fishNet.Repository.*;
import com.fish.fishNet.Dtos.SalidaAlimentosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.SalidaAlimentos;
import com.fish.fishNet.Service.SalidaAlimentosService;

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
    public SalidaAlimentos salidaAlimento(SalidaAlimentosDto salidaAlimentosDto) {

        EntradaAlimentos facturaEntrada = entradaAlimentosRepository.findByNumeroFactura(salidaAlimentosDto.getNumeroFactura());

        if (facturaEntrada == null) {
            throw new IllegalArgumentException(String.format(MENSAJE_ERROR_NUMERO_FACTURA, salidaAlimentosDto.getNumeroFactura()));
        }

        int kilosEntrada = entradaAlimentosRepository.totalEntradas(salidaAlimentosDto.getNumeroFactura(), salidaAlimentosDto.getTipoAlimentoId());
        int kilosSalida = salidaAlimentosRepository.totalSalidas(salidaAlimentosDto.getNumeroFactura(), salidaAlimentosDto.getTipoAlimentoId());
        int kilosDisponibles = kilosEntrada - kilosSalida;

        if (salidaAlimentosDto.getNumeroKilos() > kilosDisponibles) {
            throw new IllegalArgumentException(String.format(MENSAJE_ERROR_SALIDA, kilosDisponibles));
        }

        Optional<Lote> lote = loteRepository.findById(salidaAlimentosDto.getLoteId());
        Optional<TipoAlimento> tipoAlimento = tipoAlimentoRepository.findById(salidaAlimentosDto.getTipoAlimentoId());

        if (lote.isPresent() && tipoAlimento.isPresent()) {
            SalidaAlimentos salidaAlimentos = new SalidaAlimentos(salidaAlimentosDto.getNumeroFactura(),
                    salidaAlimentosDto.getNumeroKilos(), lote.get(), tipoAlimento.get());

            return salidaAlimentosRepository.save(salidaAlimentos);
        }

        throw new IllegalArgumentException(MENSAJE_ERROR_LOTE_TIPO_ALIMENTO);
    }
}