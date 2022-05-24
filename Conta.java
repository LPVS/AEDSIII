<<<<<<< HEAD
import java.util.*;
import java.io.*;

class Conta {
=======
import java.io.*;

public class Conta {
>>>>>>> 1ef792e8fc90b31f2c57cdafb1860dd06d62c359
    // Variaveis para cada conta
    protected int idConta;
    protected String nomePessoa;
    protected String cpf;
<<<<<<< HEAD
    protected String cidade;
    protected short transferenciasRealizadas;
    protected float saldoConta;
=======
    protected String estado;
    protected int transferenciasRealizadas;
    protected int saldoConta;
>>>>>>> 1ef792e8fc90b31f2c57cdafb1860dd06d62c359

    // Métodos Construtores
    public Conta() {
        idConta = 0;
        nomePessoa = "";
        cpf = "";
<<<<<<< HEAD
        cidade = "";
=======
        estado = "";
>>>>>>> 1ef792e8fc90b31f2c57cdafb1860dd06d62c359
        transferenciasRealizadas = 0;
        saldoConta = 0;
    }

<<<<<<< HEAD
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
=======
    public Conta(int id, String nome, String cpf, String estado, int saldo) {
        this.idConta = id;
        this.nomePessoa = nome;
        this.cpf = cpf;
        this.estado = estado;
        this.transferenciasRealizadas = 0;
        this.saldoConta = saldo;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(idConta);
        dos.writeUTF(nomePessoa);
        dos.writeUTF(cpf);
        dos.writeUTF(estado);
        dos.writeInt(transferenciasRealizadas);
        dos.writeInt(saldoConta);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        idConta = dis.readInt();
        nomePessoa = dis.readUTF();
        cpf = dis.readUTF();
        estado = dis.readUTF();
        transferenciasRealizadas = dis.readInt();
        saldoConta = dis.readInt();
    }

    public int getId() {
        return this.idConta;
    }

    public void transferDone() {
        this.transferenciasRealizadas++;
    }

    public void balance(int valor) {
        this.saldoConta += valor;
    }

    public String toString() {
        return "\nID: " + idConta + "\nNome: " + nomePessoa + "\nCPF: " + cpf + "\nEstado: " + estado
                + "\nTransferencias: " + transferenciasRealizadas + "\nSaldo: " + saldoConta;
    }
}
>>>>>>> 1ef792e8fc90b31f2c57cdafb1860dd06d62c359
