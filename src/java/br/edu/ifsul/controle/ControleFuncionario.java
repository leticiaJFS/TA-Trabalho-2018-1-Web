/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.FuncionarioDAO;
import br.edu.ifsul.dao.LiberacaoDAO;
import br.edu.ifsul.modelo.Funcionario;
import br.edu.ifsul.modelo.Liberacao;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Leticia-PC
 */
@Named(value="controleFuncionario")
@ViewScoped
public class ControleFuncionario implements Serializable{
    @EJB
    private FuncionarioDAO<Funcionario> dao;
    private Funcionario objeto;
    private Boolean editando;
    @EJB
    private LiberacaoDAO<Liberacao> daoLiberacao;
    private Liberacao liberacao;
    private Boolean editandoLiberacao;            
    
    public ControleFuncionario(){
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/funcionario/listar?faces-redirect=true";
    }
    
    public String listar2(){
        editando = false;
        return "/publico/funcionario/listar?faces-redirect=true";
    }
    
    public void novo(){
        editandoLiberacao = false;
        editando = true;
        objeto = new Funcionario();
    }
    
    public void alterar(Object id){
        System.out.println("chegou");
        try {
            System.out.println("chegou2");
            objeto = dao.getObjectById(id);
            editando = true;
            editandoLiberacao = false;
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: " + 
                    Util.getMensagemErro(e));
        } 
    }
    
    public void excluir(Object id){
        try {
            objeto = dao.getObjectById(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e){
            Util.mensagemErro("Erro ao remover objeto: " + 
                    Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {
            if (objeto.getId() == null){
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
            editando = false;
        } catch(Exception e){
            Util.mensagemErro("Erro ao persistir objeto: " + 
                    Util.getMensagemErro(e));
        }
    }
    
    public void novaLiberacao(){
        editandoLiberacao = true;        
    }
    
    public void salvarLiberacao(){
        if(objeto.getLiberacoes().contains(liberacao)){
            Util.mensagemErro("Funcionario já possui esta liberação!");
        }else{
            objeto.getLiberacoes().add(liberacao);
            Util.mensagemInformacao("Liberação adicionada com sucessso!");
        }
        editandoLiberacao = false;
    }
    
    public void removerLiberacao(Liberacao obj){
        objeto.getLiberacoes().remove(obj);
        Util.mensagemInformacao("Liberação removida com sucesso!");
    }

    public FuncionarioDAO<Funcionario> getDao() {
        return dao;
    }

    public void setDao(FuncionarioDAO<Funcionario> dao) {
        this.dao = dao;
    }

    public Funcionario getObjeto() {
        return objeto;
    }

    public void setObjeto(Funcionario objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public LiberacaoDAO<Liberacao> getDaoLiberacao() {
        return daoLiberacao;
    }

    public void setDaoLiberacao(LiberacaoDAO<Liberacao> daoLiberacao) {
        this.daoLiberacao = daoLiberacao;
    }

    public Liberacao getLiberacao() {
        return liberacao;
    }

    public void setLiberacao(Liberacao liberacao) {
        this.liberacao = liberacao;
    }

    public Boolean getEditandoLiberacao() {
        return editandoLiberacao;
    }

    public void setEditandoLiberacao(Boolean editandoLiberacao) {
        this.editandoLiberacao = editandoLiberacao;
    }
    
    
}
