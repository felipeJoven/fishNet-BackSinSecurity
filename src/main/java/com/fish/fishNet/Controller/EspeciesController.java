package com.fish.fishNet.Controller;

import com.fish.fishNet.Dtos.EspeciesDTO;
import com.fish.fishNet.Model.UnidadProductiva;
import com.fish.fishNet.Service.EspeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.Especies;
import com.fish.fishNet.Service.Impl.EspeciesServiceImpl;

@RestController
@RequestMapping("api/V1/especies")
@CrossOrigin(origins="*")
public class EspeciesController extends BaseControllerImpl<Especies, EspeciesServiceImpl> {

    @Autowired
    private EspeciesService especiesService;

    @PostMapping("/registrar")
    public boolean registrarEspecie(@RequestBody EspeciesDTO especiesDTO) throws Exception {
        return especiesService.especieYaExiste(especiesDTO);
    }

    @Override
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Especies entity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(especiesService.updateOverride(id, entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\"}" + e.getMessage());
        }
    }
}
