package br.edu.atitus.api_java_springboot.services;

import br.edu.atitus.api_java_springboot.entities.PointEntity;
import br.edu.atitus.api_java_springboot.entities.UserEntity;
import br.edu.atitus.api_java_springboot.repositories.PointRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

public class PointService {

    private final PointRepository repository;

    public PointService(PointRepository repository) {
        super();
        this.repository = repository;
    }

    public PointEntity save (PointEntity point) throws Exception {

        if (point == null) {
            throw new Exception("Objeto não pode ser nulo");
        }

        if(point.getDescription() == null || point.getDescription().isEmpty())
            throw new Exception("Descrição Inválida");
        point.setDescription(point.getDescription().trim());

        if(point.getLatitude() < -90 || point.getLatitude() > 90)
            throw new Exception("Latitude Inválida");

        if(point.getLongitude() < -180 || point.getLongitude() > 180)
            throw new Exception("Longitude Inválida");

        UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        point.setUser(userAuth);

        return repository.save(point);
    }

    public void deleteByID (UUID id) throws Exception{

        var point = repository.findById(id)
                .orElseThrow(() -> new Exception("Não existe usuário cadastrado com esse ID"));

        UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(point.getUser().getId().equals(userAuth.getId()))
            throw new Exception("Você não possui permissão para apagar este registro");

        repository.deleteById(id);
    }

    public List<PointEntity> findAll () {
        UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return repository.findByUser(userAuth);
    }

}
