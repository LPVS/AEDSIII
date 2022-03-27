import java.io.*;

public class Conta {
    // Variaveis para cada conta
    protected int idConta;
    protected String nomePessoa;
    protected String cpf;
    protected String estado;
    protected int transferenciasRealizadas;
    protected float saldoConta;

    // Métodos Construtores
    public Conta() {
        idConta = 0;
        nomePessoa = "";
        cpf = "";
        estado = "";
        transferenciasRealizadas = 0;
        saldoConta = 0;
    }

    public Conta(int id, String nome, String cpf, String estado, float saldo) {
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

        dos.write(idConta);
        dos.writeUTF(nomePessoa);
        dos.writeUTF(cpf);
        dos.writeUTF(estado);
        dos.write(transferenciasRealizadas);
        dos.writeFloat(saldoConta);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        idConta = dis.readByte();
        nomePessoa = dis.readUTF();
        cpf = dis.readUTF();
        estado = dis.readUTF();
        transferenciasRealizadas = dis.readByte();
        saldoConta = dis.readByte();
    }

    public int getId() {
        return this.idConta;
    }

    public void transferDone() {
        this.transferenciasRealizadas++;
    }

    public void balance(float valor) {
        this.saldoConta += valor;
    }

    public String toString() {
        return "\nID: " + idConta + "\nNome: " + nomePessoa + "\nCPF: " + cpf + "\nEstado: " + estado
                + "\nTransferencias: " + transferenciasRealizadas + "\nSaldo: " + saldoConta;
    }
}
