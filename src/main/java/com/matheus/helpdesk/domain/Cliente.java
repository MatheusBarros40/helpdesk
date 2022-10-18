package com.matheus.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matheus.helpdesk.domain.enums.Perfil;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Cliente extends Pessoa{
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente(){
        super();
        addPerfil(Perfil.CLIENTE);
    }
    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}
