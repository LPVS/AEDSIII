import java.util.Scanner;
import java.io.*;

public class TP1 {

    //Método simples para menu
    public static void menu() {
        System.out.println("[Terminal]\n    > Seja bem vindo, por favor escolha o número que deseja:");
        System.out.println("[1]Criar uma conta");
        System.out.println(
                "[2]Realizar uma tranferência (Informar duas contas, a primeira para debitar e outra para creditar, e o valor da transferência.)");
        System.out.println("[3]Encontrar um registro (Informar ID)");
        System.out.println("[4]Atualizar um registro (Informar ID, Nome, CPF, Estado e Saldo)");
        System.out.println("[5]Deletar um registro (Informar ID)");
        System.out.println("[6]Finalizar programa");
    }

    // Método para ler o cabecalho do arquivo
    public static int getId() {
        RandomAccessFile file;
        int newId;
        try {
            file = new RandomAccessFile("clube.db", "rw");
            newId = file.readInt();
            file.close();
            return newId;
        } catch(IOException e) {
            System.out.println("Erro ao ler o último id a ser inserido!");
            return -1;
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        CRUD crud = new CRUD();
        Conta conta;
        String nome, cpf, estado;
        byte op;
        float saldo;
        int id = -1;
        do {
            menu();

            op = scan.nextByte();
            switch (op) {
                case 1:
                    id++;
                    System.out.print("Favor informar Nome:");
                    nome = scan.nextLine();
                    System.out.print("Favor informar CPF:");
                    cpf = scan.nextLine();
                    System.out.print("Favor informar Estado:");
                    estado = scan.nextLine();
                    System.out.print("Favor informar Saldo:");
                    saldo = scan.nextFloat();
                    conta = new Conta(id, nome, cpf, estado, saldo);
                    crud.create(conta, id);
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                default:

                    break;
            }

        } while (op < 6);
        scan.close();
    }
}