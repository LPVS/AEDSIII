import java.io.*;

public class CRUD {
    private RandomAccessFile arq;
    private final String fileName = "Contas.db";

    public CRUD() {
        try {

            boolean check = (new File(fileName)).exists();
            if (!check) {
                try {

                    int id = -1;
                    arq = new RandomAccessFile(fileName, "rw");
                    arq.writeInt(id);
                    arq.close();

                } catch (Exception e) {
                    System.out.println("ERRO!: ID não inicializado corretamente.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro! " + e.getMessage());
        }
    }

    // Recebe uma conta e id e registra no arquivo
    public void create(Conta conta, int id) {
        byte[] array;

        try {
            arq = new RandomAccessFile(fileName, "rw");

            array = conta.toByteArray();
            arq.seek(0); // Move o ponteiro para o inicio do arquivo
            arq.writeInt(id); // Atualiza o ID do cabecalho
            arq.seek(arq.length()); // Move o ponteiro para o fim do arquivo
            arq.writeChar(' '); // Escreve a Lapide do registro
            arq.writeInt(array.length); // Escreve o tamanho do registro
            arq.write(array); // Escreve o registro em si
            arq.close();

        } catch (IOException e) {
            System.out.println("ERRO!: Falha ao inserir no arquivo.");
        }
    }

    // Receba uma conta com os dados atualizados para registrar no arquivo
    // IMPORTANTE: a sequencia de comandos desse método será bastante utilizada
    // pelos outros métodos, com pequenas variações
    public boolean update(Conta conta) {
        try {

            long position;
            char lapide;
            byte[] array;
            int size;
            Conta tmpConta;

            // Abre o arquivo e pula o cabecalho
            arq = new RandomAccessFile(fileName, "rw");
            arq.seek(4);

            // Inicia um loop ate encontrar o id ou chegar no fim do arquivo
            while (arq.getFilePointer() < arq.length()) {

                position = arq.getFilePointer(); // Guarda a posicao inicial do registro
                lapide = arq.readChar(); // Guarda o estado da lapide
                size = arq.readInt(); // guarda o tamanho do registro
                array = new byte[size]; // Cria array com tamanho do registro
                arq.read(array);

                // Confere se o registro nao esta "apagado"
                if (lapide != '*') {

                    // Cria uma conta temporaria que recebe o registro lido
                    tmpConta = new Conta();
                    tmpConta.fromByteArray(array);

                    if (tmpConta.getId() == conta.getId()) {
                        array = conta.toByteArray();
                        // Confere se a conta atualizada eh menor que a original
                        if (array.length <= size) {
                            arq.seek(position + 6); // Pula a lapide e o tamanho do registro para sobrescrever
                            arq.write(array);
                            arq.close();
                        } else {
                            arq.seek(arq.length());
                            arq.writeChar(' ');
                            arq.writeInt(array.length); // escreve o tamanho do registro
                            arq.write(array); // Escreve o registro no final do arquivo
                            delete(tmpConta.getId()); // Apaga o registro na posicao original
                            arq.close();
                        }
                        return true;
                    }
                }
            }
            arq.close();
            return false;

        } catch (Exception e) {
            System.out.println("\nERRO!: Conta inválida (UPDATE).");
            return false;
        }

    }

    // Recebe o ID de uma conta e "deleta" o registro do arquivo
    public boolean delete(int id) {
        try {
            long position;
            char lapide;
            byte[] array;
            int size;
            Conta tmpConta;

            arq = new RandomAccessFile(fileName, "rw");
            arq.seek(4); // Pula o cabecalho

            while (arq.getFilePointer() < arq.length()) {

                position = arq.getFilePointer();
                lapide = arq.readChar();
                size = arq.readInt();
                array = new byte[size];
                arq.read(array);

                // Confere se o registro nao esta "apagado"
                if (lapide != '*') {

                    tmpConta = new Conta();
                    tmpConta.fromByteArray(array);

                    // confere se o ID é o mesmo do parametro
                    if (tmpConta.getId() == id) {
                        arq.seek(position); // vai ate a lapide
                        arq.writeChar('*'); // "deleta" o arquivo
                        arq.close();
                        return true;
                    }
                }
            }

            arq.close();
            return false;

        } catch (IOException e) {
            System.out.println("ERRO!: Falha ao atualizar lápide de conta (DELETE)");
            return false;
        }

    }

    // Le uma determinada conta baseada no ID recebido
    public Conta readId(int id) {
        try {
            char lapide;
            byte[] array;
            int size;
            Conta tmp;

            arq = new RandomAccessFile(fileName, "rw");
            arq.seek(4);

            while (arq.getFilePointer() < arq.length()) {

                lapide = arq.readChar();
                size = arq.readInt();
                array = new byte[size];
                arq.read(array);

                if (lapide != '*') {
                    tmp = new Conta();
                    tmp.fromByteArray(array);

                    if (tmp.getId() == id) {
                        return tmp;
                    }
                }
            }
            return null;

        } catch (IOException e) {
            System.out.println("ERRO!: Falha ao encontrar a conta desejada (READ_ID)");
            return null;
        }
    }

    // Recebe um valor e duas contas, uma para debitar e outra para creditar, e
    // atualiza os registros das duas
    public void bankTransfer(int id1, int id2, float valor) {
        Conta debitar = readId(id1);
        Conta creditar = readId(id2);

        if (debitar != null && creditar != null) {
            debitar.transferDone();
            creditar.transferDone();
            debitar.balance(-1 * valor);
            creditar.balance(valor);

            if (update(debitar) && update(creditar)) {
                System.out.println("\nAVISO: Transfêrencia concluida e contas atualizadas.");
            } else {
                System.out.println("\nERRO!: Não foi possível concluir a transferência (BANK_TRANSFER).");
            }
        } else{
            System.out.println("\nERRO!: ID de conta inválido (BANK_TRANSFER).");
        }
    }

    public void readAll() {
        try {
            char lapide;
            byte[] array;
            int size;
            Conta tmp;

            arq = new RandomAccessFile(fileName, "rw");
            arq.seek(4);

            while (arq.getFilePointer() < arq.length()) {
                lapide = arq.readChar();
                size = arq.readInt();
                array = new byte[size];
                arq.read(array);

                if (lapide != '*') {
                    tmp = new Conta();
                    tmp.fromByteArray(array);
                    System.out.println(tmp.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO!: Falha ao imprimir todas as contas do arquivo (READ_ALL).");
        }
    }
}
