package com.matheus.helpdesk.service;

import com.matheus.helpdesk.domain.Pessoa;
import com.matheus.helpdesk.domain.Cliente;
import com.matheus.helpdesk.domain.dto.ClienteDTO;
import com.matheus.helpdesk.repositories.PessoaRepository;
import com.matheus.helpdesk.repositories.ClienteRepository;
import com.matheus.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.matheus.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id){
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id :" + id));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return repository.save(newObj);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }
    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0){
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!!");
        }
        repository.deleteById(id);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
        }
    }


}
