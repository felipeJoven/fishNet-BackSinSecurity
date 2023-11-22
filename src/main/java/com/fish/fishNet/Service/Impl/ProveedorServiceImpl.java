package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Dtos.ProveedorDTO;
import com.fish.fishNet.Model.TipoIdentificacion;
import com.fish.fishNet.Model.TipoProveedor;
import com.fish.fishNet.Service.TipoIdentificacionService;
import com.fish.fishNet.Service.TipoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.Proveedor;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.ProveedorRepository;
import com.fish.fishNet.Service.ProveedorService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProveedorServiceImpl extends BaseServiceImpl<Proveedor, Integer> implements ProveedorService {

    private static final String MENSAJE_ERROR_PROVEEDOR = "El proveedor ya existe.";
    private static final String MENSAJE_ERROR_ID = "No se encontró el proveedor con ese ID: .";

    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private TipoIdentificacionService tipoIdentificacionService;
    @Autowired
    TipoProveedorService tipoProveedorService;

    public ProveedorServiceImpl(BaseRespository<Proveedor, Integer> baseRespository){
        super(baseRespository);
    }

    public List<Proveedor> obtenerProveedoresParaLote() {
        return proveedorRepository.findProveedoresForLote("Peces");
    }

    public List<Proveedor> obtenerProveedoresParaEntrada() {
        return proveedorRepository.findProveedoresForEntrada("Alimento");
    }

    @Override
    public boolean proveedorYaExiste(ProveedorDTO proveedorDto) throws Exception {
        Proveedor proveedor = new Proveedor();
        String nombre = proveedorDto.getNombre();
        String apellido = proveedorDto.getApellido();
        int telefono = proveedorDto.getTelefono();
        String correo = proveedorDto.getCorreo();
        String direccion = proveedorDto.getDireccion();
        String razonSocial = proveedorDto.getRazonSocial();
        int numeroIdentificacion = proveedorDto.getNumeroIdentificacion();
        int tipoIdentificacionId = proveedorDto.getTipoIdentificacionId();
        int tipoProveedorId = proveedorDto.getTipoProveedorId();

        // Realiza la verificación en el repositorio
        boolean existeProveedor = proveedorRepository.existByProveedor(
                razonSocial, tipoProveedorId
        );

        if (existeProveedor) {
            // Si existe el proveedor hay excepción
            throw new IllegalArgumentException(MENSAJE_ERROR_PROVEEDOR);
        } else {
            // Si no existe, sigue la creación del proveedor
            Proveedor nuevoProveedor = new Proveedor();
            nuevoProveedor.setNombre(nombre);
            nuevoProveedor.setApellido(apellido);
            nuevoProveedor.setTelefono(telefono);
            nuevoProveedor.setCorreo(correo);
            nuevoProveedor.setDireccion(direccion);
            nuevoProveedor.setRazonSocial(razonSocial);
            nuevoProveedor.setNumeroIdentificacion(numeroIdentificacion);

            TipoProveedor tipoProveedor = tipoProveedorService.findById((tipoProveedorId));
            if(tipoProveedor != null ) {
                nuevoProveedor.setTipoProveedor(tipoProveedor);
            }

            TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.findById(tipoIdentificacionId);
            if(tipoIdentificacion != null ) {
                nuevoProveedor.setTipoIdentificacion(tipoIdentificacion);
            }

            // Se realiza set para que muestre la fecha de creación en la base de datos
            nuevoProveedor.setFechaCreacion(LocalDate.now());
            proveedorRepository.save(nuevoProveedor);
        }
        return true;
    }

    public Proveedor updateOverride(Integer id, Proveedor proveedor) throws Exception {
        Optional<Proveedor> optionalProveedor = proveedorRepository.findById(id);

        if (optionalProveedor.isPresent()) {
            Proveedor proveedorSaved = optionalProveedor.get();

            // Verificar si los nuevos valores generaran duplicados excluyendo la entidad actual
            if (
                    !Objects.equals(proveedorSaved.getRazonSocial(), proveedor.getRazonSocial()) ||
                            (proveedor.getTipoProveedor() != null &&
                                    !Objects.equals(proveedorSaved.getTipoProveedor().getId(), proveedor.getTipoProveedor().getId()))
            ) {
                boolean exist = proveedorRepository.existByProveedor(
                        proveedor.getRazonSocial(),
                        (proveedor.getTipoProveedor() != null) ? proveedor.getTipoProveedor().getId() : null,
                        id
                );
                if (!exist) {
                    return updateProveedor(proveedorSaved, proveedor);
                } else {
                    throw new IllegalArgumentException(MENSAJE_ERROR_PROVEEDOR);
                }
            } else {
                return updateProveedor(proveedorSaved, proveedor);
            }
        } else {
            throw new Exception(MENSAJE_ERROR_ID + id);
        }
    }


    private Proveedor updateProveedor(Proveedor ProveedorSaved, Proveedor proveedor) {
        // Actualizar la entidad con los nuevos valores
        ProveedorSaved.setNombre(proveedor.getNombre());
        ProveedorSaved.setApellido(proveedor.getApellido());
        ProveedorSaved.setTelefono(proveedor.getTelefono());
        ProveedorSaved.setCorreo(proveedor.getCorreo());
        ProveedorSaved.setDireccion(proveedor.getDireccion());
        ProveedorSaved.setNumeroIdentificacion(proveedor.getNumeroIdentificacion());
        ProveedorSaved.setRazonSocial(proveedor.getRazonSocial());
        ProveedorSaved.setTipoIdentificacion(proveedor.getTipoIdentificacion());
        ProveedorSaved.setTipoProveedor(proveedor.getTipoProveedor());

        proveedorRepository.save(ProveedorSaved);
        return ProveedorSaved;
    }

}
