import java.io.*;

public class CRUD {
    private RandomAccessFile arq;
    private final String fileName = "Contas.db";

    public CRUD() {
        try {

            boolean check = (new File(fileName)).exists();
            if (!check) {
                try {

                    int id = 0;
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

    /**
     * Registra uma nova conta no arquivo
     * 
     * @param conta : informações da conta a ser salva
     * @param id    : ID da conta
     */
    public void create(Conta conta, int id) {
        byte[] array;
        Index index;

        try {
            arq = new RandomAccessFile(fileName, "rw");

            array = conta.toByteArray();
            arq.seek(0); // Move o ponteiro para o inicio do arquivo
            arq.writeInt(id); // Atualiza o ID do cabecalho
            arq.seek(arq.length()); // Move o ponteiro para o fim do arquivo
            long end = arq.getFilePointer(); // Salva o endereco do registro

            arq.writeChar(' '); // Escreve a Lapide do registro
            arq.writeInt(array.length); // Escreve o tamanho do registro
            arq.write(array); // Escreve o registro em si

            // Adiciona no index o id do objeto e seu endereco no arquivo principal
            index = new Index(id, end);
            index.create(index);

            arq.close();
        } catch (IOException e) {
            System.out.println("ERRO!: Falha ao inserir no arquivo.");
        }
    }

    /**
     * IMPORTANTE: a sequencia de comandos desse método será bastante utilizada
     * pelos outros métodos, com pequenas variações
     * 
     * @param conta : objeto com os dados atualizados para registrar no arquivo
     * @return
     */
    public boolean update(Conta conta) {
        try {

            char lapide;
            byte[] array;
            int size;
            Conta tmpConta;

            // Abre o arquivo e pula o cabecalho
            arq = new RandomAccessFile(fileName, "rw");
            arq.seek(4);

            // Abre o arquivo index e procura o id
            Index index = new Index();
            long end = index.binSearch(conta.idConta);

            // Confere se o ID não existe
            if (end == -1) {
                return false;
            }
            arq.seek(end);

            lapide = arq.readChar(); // Guarda o estado da lapide
            size = arq.readInt(); // guarda o tamanho do registro
            array = new byte[size]; // Cria array com tamanho do registro

            // Confere se o registro nao esta "apagado"
            if (lapide == '*') {
                return false;
            }

            // Cria uma conta temporaria que recebe o registro lido
            tmpConta = new Conta();
            tmpConta.fromByteArray(array);
            array = conta.toByteArray();

            // Confere se a conta atualizada eh menor que a original
            if (array.length <= size) {
                arq.seek(end + 6); // Pula a lapide e o tamanho do registro para sobrescrever
                arq.write(array); // Escreve por cima o registro
                arq.close();
            } else {
                arq.seek(arq.length()); // Vai para o final do arquivo
                arq.writeChar(' '); // Escreve a lapide
                arq.writeInt(array.length); // escreve o tamanho do registro
                arq.write(array); // Escreve o registro no final do arquivo
                delete(tmpConta.getId()); // Apaga o registro na posicao original
                arq.close();
            }
            return true;
        } catch (Exception e) {
            System.out.println("\nERRO!: Conta inválida (UPDATE).");
            return false;
        }

    }

    // Recebe o ID de uma conta e "deleta" o registro do arquivo
    /**
     * "Deleta o registro", alterando sua lapide
     * @param id : ID da conta a ser deletada
     * @return TRUE caso consiga apagar
     */
    public boolean delete(int id) {
        try {
            char lapide;
            
            arq = new RandomAccessFile(fileName, "rw");
            arq.seek(4); // Pula o cabecalho

            // Abre o arquivo index e procura o id
            Index index = new Index();
            long end = index.binSearch(id);

            // Confere se o ID não existe
            if (end == -1) {
                return false;
            }
            arq.seek(end);
            lapide = arq.readChar(); // Guarda o estado da lapide

            // Confere se o registro já nao esta "apagado"
            if (lapide == '*') {
                return false;
            }

            arq.seek(end); // vai ate a lapide
            arq.writeChar('*'); // "deleta" o arquivo
            arq.close();
            return true;

        } catch (IOException e) {
            System.out.println("ERRO!: Falha ao atualizar lápide de conta (DELETE)");
            return false;
        }

    }


/**
 * Confere se o registro esta salvo no arquivo de indice
 * @param id : id do registro a ser persquisado
 * @return : TRUE se existir, FALSE caso não exista
 */
    public boolean isSaved(int id) {
        Index index = new Index();
        if (index.binSearch(id) > 0) {
            return true;
        }
        return false;
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

            // Abre o arquivo index e procura o id
            Index index = new Index();
            long end = index.binSearch(id);

            // Confere se o ID não existe
            if (end == -1) {
                return null;
            }
            arq.seek(end);

            lapide = arq.readChar(); // Guarda o estado da lapide
            size = arq.readInt(); // guarda o tamanho do registro
            array = new byte[size]; // Cria array com tamanho do registro
            arq.read(array);

            // Confere se o registro nao esta "apagado"
            if (lapide == '*') {
                return null;
            }

            // Cria uma conta temporaria que recebe o registro lido
            tmp = new Conta();
            tmp.fromByteArray(array);

            return tmp;

        } catch (IOException e) {
            System.out.println("ERRO!: Falha ao encontrar a conta desejada (READ_ID)");
            return null;
        }
    }

    /**
     * Recebe o ID de duas contas e o valor a ser transferido, atualiza o registro de ambas
     * @param id1 : ID da primeira conta (Debitar)
     * @param id2 : ID da segunda conta (Creditar)
     * @param valor : Valor da transacao (Dinheiro)
     */
    public void bankTransfer(int id1, int id2, int valor) {
        Conta debitar = readId(id1);
        Conta creditar = readId(id2);

        if (debitar != null && creditar != null && valor > 0) {
            debitar.transferDone();
            creditar.transferDone();
            debitar.balance(-1 * valor);
            creditar.balance(valor);

            if (update(debitar) && update(creditar)) {
                System.out.println("\nAVISO: Transfêrencia concluida e contas atualizadas.");
            } else {
                System.out.println("\nERRO!: Não foi possível concluir a transferência (BANK_TRANSFER).");
            }
        } else {
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
