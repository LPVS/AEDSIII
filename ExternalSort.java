import java.util.*;
import java.io.*;

public class ExternalSort {
    private RandomAccessFile file;
    private final String nomeArquivoIndex = "contas_index.db";
    private final String tempFile1 = "tempFile1";
    private final String tempFile2 = "tempFile2";

    public ExternalSort() {
        try {
            boolean temp1Exists = (new File(tempFile1)).exists();
            boolean temp2Exists = (new File(tempFile2)).exists();

            if (!(temp1Exists && temp2Exists)) {
                try {
                    file = new RandomAccessFile(tempFile1, "rw");
                    file = new RandomAccessFile(tempFile2, "rw");
                    file.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void balancedIntercalation(int blockSize, int pathNum) {
        ArrayList<String> keys = new ArrayList<>();
        keys = getFileKeys();
    }

    public ArrayList<String> getFileKeys() {
        ArrayList<String> indexedFile = new ArrayList<>();

        try {
            file = new RandomAccessFile(nomeArquivoIndex, "rw");

            long index;
            byte id;
            long address;

            while (file.getFilePointer() < file.length()) {
                index = file.getFilePointer();
                String fileBuilder = "";
                if (file.readByte() != -1) {
                    file.seek(index);
                    id = file.readByte();
                    address = file.readLong();
                    fileBuilder = id + " " + address;
                    indexedFile.add(fileBuilder);
                } else {
                    file.readLong();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indexedFile;
    }

}