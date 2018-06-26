/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Funcionario;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Leticia-PC
 */

@Stateful
public class FuncionarioDAO<TIPO> extends DAOGenerico<Funcionario> implements Serializable{
    
    public FuncionarioDAO(){
        super();
        classePersistente = Funcionario.class;
        ordem = "nome";
        maximoObjetos = 3;
    }
    
    public Funcionario getObjectById(Object id) throws Exception {
        Funcionario obj = em.find(Funcionario.class, id);
        /**
         *A linha obj.getPermissoes.size(); é necessaria para inicializar a coleção
         * para quando ela for exibida na tela não gerar um erro de 
         * lazyInicializationException
         */
        
        obj.getLiberacoes().size();
        return obj;
    }
    
    public Funcionario localizaPorNomeFuncionario(String nomeFuncionario){
        Query query = em.createQuery("from Funcionario where upper(login) = :nomeFuncionario");
        query.setParameter("nomeFuncionario", nomeFuncionario.toUpperCase());
        Funcionario obj = (Funcionario) query.getSingleResult();
        obj.getLiberacoes().size();
        return obj;
    }
}
