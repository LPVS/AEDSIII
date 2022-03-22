import java.util.*;
import java.io.*;

class Conta {
    // Variaveis para cada conta
    protected int idConta;
    protected String nomePessoa;
    protected String cpf;
    protected String cidade;
    protected short transferenciasRealizadas;
    protected float saldoConta;

    // Métodos Construtores
    public Conta() {
        idConta = 0;
        nomePessoa = "";
        cpf = "";
        cidade = "";
        transferenciasRealizadas = 0;
        saldoConta = 0;
    }

    public Conta(String nomePessoa, String cpf, String cidade, float saldoConta) {
        this.idConta = findlastID() + 1;
        this.nomePessoa = nomePessoa;
        this.cpf = cpf;
        this.cidade = cidade;
        this.transferenciasRealizadas = 0;
        this.saldoConta = saldoConta;
    }

    // Setters e Getters
    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public short getTransferenciasRealizadas() {
        return transferenciasRealizadas;
    }

    public void setTransferenciasRealizadas(short transferenciasRealizadas) {
        this.transferenciasRealizadas = transferenciasRealizadas;
    }

    public float getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(float saldoConta) {
        this.saldoConta = saldoConta;
    }

    //
    int findlastID() {
        // Criar um método com o File Stream pra achar o último id
        int x = 0;
        return x;
    }

    void transferencia(Conta debit, Conta credit, float value) {
        // Criar um método com File Stream pra pegar um "valor" de uma conta e adicionar
        // em outra

    }

    void find(int id) {
        // Criar um método com o File Stream pra achar o id
    }

    void update(int id, String nomePessoa, String cpf, String cidade, float saldoConta) {
        // Criar um método com o File Stream pra atualizar os registros do id
    }

    void delete(int id) {
        // Criar um método com o File Stream pra deletar os registros do id
    }

}