/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Liberacao;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Leticia-PC
 */

@Stateful
public class LiberacaoDAO<TIPO> extends DAOGenerico<Liberacao> implements Serializable{
    
    public LiberacaoDAO(){
        super();
        classePersistente = Liberacao.class;
        ordem = "identificacao";
        maximoObjetos = 3;
    }
}
