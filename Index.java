
import java.io.RandomAccessFile;
import java.io.*;

//CRUD do arquivo de indices
public class Index  {
    protected RandomAccessFile arq;
    protected final String indexFileName = "contas_index.db";

    protected int id;
    protected long end;

    /**
     * Método contrutor padrão para abrir o arquivo
     */
    public Index() {
        try {
            boolean exists = (new File(indexFileName)).exists();

            if (!exists) {
                try {
                    arq = new RandomAccessFile(indexFileName, "rw"); // Cria o arquivo caso não exista
                    arq.close();
                } catch (Exception e) {
                    System.out.println("ERRO!: Index não foi possível de criar.");
                }
            } 
        } catch (Exception e) {
            System.out.println("Erro! " + e.getMessage());
        }
    }
    /**
     * Método para construir um objeto index
     * @param id : ID da conta a ser salva
     * @param end : Endereco da conta no arquivo original
     */
    public Index(int id, long end) {
        this.id = id;
        this.end = end;
    }

    //Getters e Setters
    public int getId() {
        return id;
    }

    public long getEnd() {
        return end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    /**
     * Adiciona o id e o endereco da conta criada
     * @param index : objeto com o id e endereco original
     */
    public void create(Index index) {
        try {
            arq = new RandomAccessFile(indexFileName, "rw"); // abre o arquivo ou cria se ele não existir

            if(arq.length() == 0)  { //Arquivo está vazio
                arq.seek(0); // Escreve no inicio do arquivo
                arq.writeInt(index.getId());
                arq.writeLong(index.getEnd());

            } else {
                long i = (index.getId()) * 12; // Multiplica o valor dos bytes
                arq.seek(i);  // Vai para a posição indicada pelo Id
                arq.writeInt(index.getId());
                arq.writeLong(index.getEnd());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Realiza uma busca binaria no arquivo de indices
     * @param idSearch : id a ser pesquisado no index
     * @return : posicao do objeto no original, '-1' se não existir
     */
    public long binSearch(int idSearch) {
        try {
            arq = new RandomAccessFile(indexFileName, "rw");

            long esq = 0, dir = arq.length() / 12, meio; // Define os pontos para a pesquisa
            int idArq;

            // Confere se o primeiro item é o procurado
            arq.seek(0);
            idArq = arq.readInt();
            if (idSearch == idArq) {
                return arq.readLong();
            }

            while (esq <= dir) {
                meio = (int) ((esq + dir) / 2); // Define o meio baseado na direita e esquerda atual

                arq.seek(meio * 12); // Transformar o valor em bytes
                idArq = arq.readInt();

                if (idSearch < idArq) { //Se o id a ser pesquisado for menor que o id do meio
                    dir = meio - 1; // O meio se torna a 'nova direita'
                } else if (idSearch > idArq) { // Caso seja maior que o id do meio
                    esq = meio + 1; // O meio se torna a 'nova esquerda'
                } else { // Caso for igual
                    long pos = arq.getFilePointer();
                    arq.seek(pos); 
                    return arq.readLong(); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Atualiza o endereco do indice
     * @param index : objeto com id e endereco novos do dado original
     * @return true se a alteracao foi possivel
     */
    public boolean update(Index index) {
        try {
            arq = new RandomAccessFile(indexFileName, "rw");

            while(arq.getFilePointer() < arq.length()) {
                if(arq.readInt() == index.getId()) { // Confere se o id do index e o mesmo do arquivo
                    arq.writeLong(index.getEnd());  // Atualiza o valor do endereco
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Apaga o objeto excluido no original
     * @param id : id que deve ser deletado
     */
    public void delete(int id) {
        try {
            arq = new RandomAccessFile(indexFileName, "rw");
            long pos;

            while(arq.getFilePointer() < arq.length()) {
                pos = arq.getFilePointer(); // Guarda a posicao do ponteiro
                if(arq.readInt() == id) { // Se o id igual
                    arq.seek(pos);  // Volta para a posicao inicial do registro
                    arq.writeInt(-1); // "Apaga" o registro
                    arq.writeLong(-1);

                } else { // Caso nao seja igual, pula o endereco
                    arq.readLong();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "ID: " + this.id + " | ENDEREÇO: " + this.end;
    }
}