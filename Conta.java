import java.io.*;

public class Conta {
    // Variaveis para cada conta
    protected int idConta;
    protected String nomePessoa;
    protected String cpf;
    protected String estado;
    protected int transferenciasRealizadas;
    protected int saldoConta;

    // Métodos Construtores
    public Conta() {
        idConta = 0;
        nomePessoa = "";
        cpf = "";
        estado = "";
        transferenciasRealizadas = 0;
        saldoConta = 0;
    }

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
