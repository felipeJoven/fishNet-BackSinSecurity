package com.fish.fishNet.Controller;

import com.fish.fishNet.Dtos.UnidadProductivaDTO;
import com.fish.fishNet.Service.UnidadProductivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.UnidadProductiva;
import com.fish.fishNet.Service.Impl.UnidadProductivaServiceImpl;

@RestController
@RequestMapping("api/V1/unidad-productiva")
@CrossOrigin(origins="*")
public class UnidadProductivaController extends BaseControllerImpl<UnidadProductiva, UnidadProductivaServiceImpl> {

    @Autowired
    private UnidadProductivaService unidadProductivaService;

    @PostMapping("/registrar")
    public boolean registrarUnidadProductiva(@RequestBody UnidadProductivaDTO unidadProductivaDTO) throws Exception {
        return unidadProductivaService.existByUnidadProductiva(unidadProductivaDTO);
    }

    @Override
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UnidadProductiva entity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(unidadProductivaService.updateOverride(id, entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\"}" + e.getMessage());
        }
    }
}