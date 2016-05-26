package com.example.todeolho.myapplication.classes;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Alan on 09/04/2016.
 */
public class Convenio implements Serializable {

    private String id;
    private String modalidade;
    private String orgao_concedente;
    private String justificativa_resumida;
    private String objeto_resumido;
    private Date data_inicio_vigencia;
    private Date data_fim_vigencia;
    private double valor_global;
    private double valor_repasse;
    private double valor_contra_partida;
    private Date data_assinatura;
    private Date data_publicacao;
    private String situacao;
    private String proponente;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getOrgao_concedente() {
        return orgao_concedente;
    }

    public void setOrgao_concedente(String orgao_concedente) {
        this.orgao_concedente = orgao_concedente;
    }

    public String getJustificativa_resumida() {
        return justificativa_resumida;
    }

    public void setJustificativa_resumida(String justificativa_resumida) {
        this.justificativa_resumida = justificativa_resumida;
    }

    public String getObjeto_resumido() {
        return objeto_resumido;
    }

    public void setObjeto_resumido(String objeto_resumido) {
        this.objeto_resumido = objeto_resumido;
    }

    public Date getData_inicio_vigencia() {
        return data_inicio_vigencia;
    }

    public void setData_inicio_vigencia(Date data_inicio_vigencia) {
        this.data_inicio_vigencia = data_inicio_vigencia;
    }

    public Date getData_fim_vigencia() {
        return data_fim_vigencia;
    }

    public void setData_fim_vigencia(Date data_fim_vigencia) {
        this.data_fim_vigencia = data_fim_vigencia;
    }

    public double getValor_global() {
        return valor_global;
    }

    public void setValor_global(double valor_global) {
        this.valor_global = valor_global;
    }

    public double getValor_repasse() {
        return valor_repasse;
    }

    public void setValor_repasse(double valor_repasse) {
        this.valor_repasse = valor_repasse;
    }

    public double getValor_contra_partida() {
        return valor_contra_partida;
    }

    public void setValor_contra_partida(double valor_contra_partida) {
        this.valor_contra_partida = valor_contra_partida;
    }

    public Date getData_assinatura() {
        return data_assinatura;
    }

    public void setData_assinatura(Date data_assinatura) {
        this.data_assinatura = data_assinatura;
    }

    public Date getData_publicacao() {
        return data_publicacao;
    }

    public void setData_publicacao(Date data_publicacao) {
        this.data_publicacao = data_publicacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getProponente() {
        return proponente;
    }

    public void setProponente(String proponente) {
        this.proponente = proponente;
    }
}
