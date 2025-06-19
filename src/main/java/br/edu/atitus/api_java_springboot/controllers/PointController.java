package br.edu.atitus.api_java_springboot.controllers;


import br.edu.atitus.api_java_springboot.dtos.PointDTO;
import br.edu.atitus.api_java_springboot.entities.PointEntity;
import br.edu.atitus.api_java_springboot.repositories.PointRepository;
import br.edu.atitus.api_java_springboot.services.PointService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ws/point")
public class PointController {

    private final PointService pointService;

    public PointController(PointService pointService, PointRepository pointRepository) {
        super();
        this.pointService = pointService;
    }

    @GetMapping
    public ResponseEntity<List<PointEntity>> getPoints() {

        var lista = pointService.findAll();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<PointEntity> postPoint (@RequestBody PointDTO pointDTO) throws Exception{

        PointEntity point = new PointEntity();
        BeanUtils.copyProperties(pointDTO, point);

        pointService.save(point);

        return ResponseEntity.status(201).body(point);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable UUID id) throws Exception {

        pointService.deleteByID(id);

        return ResponseEntity.ok("Localização Excluída com Sucesso");
    }

}
