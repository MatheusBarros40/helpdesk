package com.matheus.helpdesk.service;

import com.matheus.helpdesk.domain.Chamado;
import com.matheus.helpdesk.repositories.ChamadoRepository;
import com.matheus.helpdesk.resources.ChamadoResource;
import com.matheus.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    public Chamado findById(Integer id){
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" + id));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }
}
