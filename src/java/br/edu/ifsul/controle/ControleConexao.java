/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.FuncionarioDAO;
import br.edu.ifsul.modelo.Funcionario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Leticia-PC
 */
@Named(value = "controleConexao")
@SessionScoped
public class ControleConexao implements Serializable {

    private Funcionario funcionarioAutenticado;
    @EJB
    private FuncionarioDAO<Funcionario> dao;
    private String funcionario;
    private String password;

    public ControleConexao() {

    }

    public String paginaLogin() {
        return "/login?faces-redirect=true";
    }

    public String efetuarConexao() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.login(this.funcionario, this.password);
            if (request.getUserPrincipal() != null) {
                funcionarioAutenticado = dao.localizaPorNomeFuncionario(request.getUserPrincipal().getName());
                Util.mensagemInformacao("Conexão efetuada com sucesso!");
                funcionario = "";
                password = "";
            }
            return "/index";
        } catch (Exception e) {
            e.printStackTrace();
            Util.mensagemErro("Funcionario ou password inválidos!" + Util.getMensagemErro(e));
            return "/login";
        }
    }

    public String efetuarDesconexao() {
        try {
            funcionarioAutenticado = null;
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.logout();
            return "/index";
        } catch (Exception e) {
            Util.mensagemErro("Erro: " + Util.getMensagemErro(e));
        }
        return "/index";
    }

   public Boolean liberacao(){
       if(funcionarioAutenticado != null){
        for (int i = 0; i < funcionarioAutenticado.getLiberacoes().size(); i++) {
            if (funcionarioAutenticado.getLiberacoes().get(i).getIdentificacao().equals("SUPER")) {
                return true;
            }     
            
        }
       }
        return false;

    }

    public Funcionario getFuncionarioAutenticado() {
        return funcionarioAutenticado;
    }

    public void setFuncionarioAutenticado(Funcionario funcionarioAutenticado) {
        this.funcionarioAutenticado = funcionarioAutenticado;
    }

    public FuncionarioDAO<Funcionario> getDao() {
        return dao;
    }

    public void setDao(FuncionarioDAO<Funcionario> dao) {
        this.dao = dao;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
