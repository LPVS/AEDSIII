import java.util.*;
import java.io.*;

public class InvertedList {
    private RandomAccessFile file;
    private final String invertedNameListFile = "invertedNameList.db";
    private final String invertedCityListFile = "invertedCityList.db";

    public InvertedList() {
        try {
            boolean nameExists = (new File(invertedNameListFile)).exists();
            boolean cityExists = (new File(invertedCityListFile)).exists();

            if (nameExists == false && cityExists == false) {
                try {
                    file = new RandomAccessFile(invertedNameListFile, "rw");
                    file = new RandomAccessFile(invertedCityListFile, "rw");
                    file.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /**
     * Deleta indices do arquivo e atualiza
     * 
     * @param word     palavra atualizada
     * @param id       id deletado
     * @param fileName nome do arquivo que será lido
     * @param isDelete qual operacao deve ser feita
     */
    public void updateList(String word, byte id, String fileName, boolean isDelete) {
        try {
            deleteAllWordsRelatedToId(id, fileName);

            if (isDelete == false) {
                createListFile(word, id, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checa se a lista invertida tem a palavra
     * 
     * @param word     o que será pesquisado
     * @param fileName onde será pesquisado
     * @return se achou a palavra
     */
    public boolean hasWord(String word, String fileName) {
        try {
            file = new RandomAccessFile(fileName, "rw");

            String wordFile;

            file.seek(0);
            while (file.getFilePointer() < file.length()) {
                wordFile = file.readUTF();
                for (int i = 0; i < 5; i++)
                    file.readByte();
                file.readLong();
                if (word.compareTo(wordFile) == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Procura o indice livre
     * 
     * @param word     palavra repetida
     * @param fileName nome do arquivo que será lido
     * @return indice livre
     */
    public long freeIndexPosition(String word, String fileName) {
        try {
            file = new RandomAccessFile(fileName, "rw");

            file.seek(0);
            long index = file.getFilePointer();
            String wordFile;

            while (file.getFilePointer() < file.length()) {
                wordFile = file.readUTF();

                if (word.compareTo(wordFile) == 0) {

                    for (int i = 0; i < 5; i++) {
                        index = file.getFilePointer();
                        if (file.readByte() == -1) {
                            return index;
                        }
                    }

                    index = file.getFilePointer();
                    if (file.readLong() == -1) {
                        file.seek(index);
                        file.writeLong(file.length());

                        file.seek(file.length());
                        return file.getFilePointer();
                    }
                } else {
                    for (int i = 0; i < 5; i++)
                        file.readByte();
                    file.readLong();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * lista invertida com nome ou cidade do cliente
     * 
     * @param name nome ou cidade do cliente para ser utilizado na lista
     * @param id  id do cliente a ser inserido na lista
     */
    public void createListFile(String name, byte id, String fileName) {
        String words[] = new String[checkWordCount(name)];
        words = name.split(" ");

        try {
            file = new RandomAccessFile(fileName, "rw");

            for (int i = 0; i < words.length; i++) {
                if (hasWord(words[i], fileName) == true) {
                    long freeIndex = freeIndexPosition(words[i], fileName);

                    if (freeIndex != file.length()) {
                        file.seek(freeIndex);
                        file.writeByte(id);
                    } else {
                        file.seek(freeIndex);
                        file.writeUTF(words[i]);
                        file.writeByte(id);
                        for (int j = 0; j < 4; j++)
                            file.writeByte(-1);
                        file.writeLong(-1);
                    }
                } else {
                    file.seek(file.length());
                    file.writeUTF(words[i]);
                    file.writeByte(id);
                    for (int j = 0; j < 4; j++)
                        file.writeByte(-1);
                    file.writeLong(-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showInvertedList() {
        try {
            file = new RandomAccessFile(invertedNameListFile, "rw");

            while (file.getFilePointer() < file.length()) {
                System.out.println(file.readUTF());
                for (int i = 0; i < 5; i++)
                    System.out.println(file.readByte());
                System.out.println(file.readLong());
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * busca e mostra ids para a palavra inserida
     * 
     * @param word     palavras que serao buscadas
     * @param fileName arquivo de busca
     */
    public void searchList(String word, String fileName) {
        String words[] = new String[checkWordCount(word)];
        words = word.split(" ");
        try {
            file = new RandomAccessFile(fileName, "rw");
            String wordFile;
            byte id;
            long index;
            ArrayList<Byte> ids = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                file.seek(0);
                while (file.getFilePointer() < file.length()) {
                    wordFile = file.readUTF();
                    if (words[i].compareTo(wordFile) == 0) {
                        for (int j = 0; j < 5; j++) {
                            index = file.getFilePointer();
                            if (file.readByte() != -1) {
                                file.seek(index);
                                id = file.readByte();
                                if (idExistsInArray(ids, id) == false) {
                                    ids.add(id);
                                }
                            }
                        }
                        index = file.getFilePointer();
                        if (file.readLong() != -1) {
                            file.seek(index);
                        }
                    } else {
                        for (int j = 0; j < 5; j++)
                            file.readByte();
                        file.readLong();
                    }
                }
            }

            System.out.println("Os ids relacionados a " + word + " foram: ");
            System.out.println(ids);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove palavras relacionadas ao id
     * 
     * @param id -> id que será deletado
     */
    public void deleteAllWordsRelatedToId(byte id, String fileName) {
        try {
            file = new RandomAccessFile(fileName, "rw");

            long index;

            while (file.getFilePointer() < file.length()) {
                file.readUTF();

                for (int i = 0; i < 5; i++) {
                    index = file.getFilePointer();
                    if (file.readByte() == id) {
                        file.seek(index);
                        file.writeByte(-1);
                    }
                }
                file.readLong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * checa se o id está na lista
     * 
     * @param ids lista de ids
     * @param id  id que será procurado
     * @return se achou o id
     */
    public boolean idExistsInArray(ArrayList<Byte> ids, byte id) {
        for (Byte idList : ids) {
            if (idList == id) {
                return true;
            } else {
                break;
            }
        }
        return false;
    }

     /**
     * Método para contar a quantidade de palavras que tem em um nome
     * 
     * @param name nome a ser pesquisado
     * @return quantidade de palavras no nome
     */
    public int checkWordCount(String name) {
        int wordCount = 0;

        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                wordCount++;
            }
        }

        return wordCount;
    }
}