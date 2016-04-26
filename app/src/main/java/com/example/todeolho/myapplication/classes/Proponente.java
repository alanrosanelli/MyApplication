package com.example.todeolho.myapplication.classes;

import java.io.Serializable;

/**
 * Created by Alan on 25/04/2016.
 */
public class Proponente implements Serializable  {

    private String id;
    private String cnpj;
    private String nome;
    private String esferaadministrativa;
    private String municipio;
    private String endereco;
    private String cep;
    private String nomeresponsavel;
    private String cpfresponsavel;
    private String telefone;
    private String naturezajuridica;
    private String inscricaoestadual;
    private String inscricaomunicipal;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEsferaadministrativa() {
        return esferaadministrativa;
    }

    public void setEsferaadministrativa(String esferaadministrativa) {
        this.esferaadministrativa = esferaadministrativa;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNomeresponsavel() {
        return nomeresponsavel;
    }

    public void setNomeresponsavel(String nomeresponsavel) {
        this.nomeresponsavel = nomeresponsavel;
    }

    public String getCpfresponsavel() {
        return cpfresponsavel;
    }

    public void setCpfresponsavel(String cpfresponsavel) {
        this.cpfresponsavel = cpfresponsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNaturezajuridica() {
        return naturezajuridica;
    }

    public void setNaturezajuridica(String naturezajuridica) {
        this.naturezajuridica = naturezajuridica;
    }

    public String getInscricaoestadual() {
        return inscricaoestadual;
    }

    public void setInscricaoestadual(String inscricaoestadual) {
        this.inscricaoestadual = inscricaoestadual;
    }

    public String getInscricaomunicipal() {
        return inscricaomunicipal;
    }

    public void setInscricaomunicipal(String inscricaomunicipal) {
        this.inscricaomunicipal = inscricaomunicipal;
    }
}
