import java.util.Scanner;
import java.io.*;

public class TP1 {

    // Método simples para menu
    public static void menu() {
        System.out.println("[Terminal]\n    > Seja bem vindo, por favor escolha o número que deseja:");
        System.out.println("[1]Criar uma conta");
        System.out.println(
                "[2]Realizar uma tranferência (Informar duas contas, a primeira para debitar e outra para creditar, e o valor da transferência.)");
        System.out.println("[3]Encontrar um registro (Informar ID)");
        System.out.println("[4]Atualizar um registro (Informar ID, Nome, CPF, Estado e Saldo)");
        System.out.println("[5]Deletar um registro (Informar ID)");
        System.out.print("[6]Finalizar programa\n> ");
    }

    // Método para ler o cabecalho do arquivo
    public static int getId() {
        RandomAccessFile file;
        int newId;
        try {
            file = new RandomAccessFile("Contas.db", "rw");
            newId = file.readInt();
            file.close();
            return newId;
        } catch (IOException e) {
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
        int id = getId();
        do {
            crud.readAll();
            menu();

            op = scan.nextByte();
            scan.nextLine();
            switch (op) {
                case 1:
                    id++;
                    System.out.print("Favor informar Nome:\n> ");
                    nome = scan.nextLine();
                    System.out.print("Favor informar CPF:\n> ");
                    cpf = scan.nextLine();
                    System.out.print("Favor informar Estado:\n> ");
                    estado = scan.nextLine();
                    System.out.print("Favor informar Saldo:\n> ");
                    saldo = scan.nextFloat();
                    conta = new Conta(id, nome, cpf, estado, saldo);
                    crud.create(conta, id);
                    break;
                case 2:
                    System.out.print("Favor informar ID da conta que REALIZARÁ o depósito:\n> ");
                    int id1 = scan.nextInt();
                    System.out.print("Favor informar ID da conta que RECEBERÁ o depósito:\n> ");
                    int id2 = scan.nextInt();
                    System.out.print("Favor informar o valor da transferência:\n> ");
                    float value = scan.nextFloat();
                    crud.bankTransfer(id1, id2, value);
                    break;
                case 3:
                    System.out.print("Favor informar ID da conta que procura:\n> ");
                    int searchID = scan.nextInt();
                    Conta searchedAccount = crud.readId(searchID);
                    if (searchedAccount != null) {
                        System.out.println(searchedAccount);
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 4:
                    System.out.print("Favor informar ID da conta que deseja atualizar:\n> ");
                    int updateID = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Favor informar Nome:\n> ");
                    nome = scan.nextLine();
                    System.out.print("Favor informar CPF:\n> ");
                    cpf = scan.nextLine();
                    System.out.print("Favor informar Estado:\n> ");
                    estado = scan.nextLine();
                    System.out.print("Favor informar Saldo:\n> ");
                    saldo = scan.nextFloat();
                    conta = new Conta(updateID, nome, cpf, estado, saldo);
                    if (crud.update(conta)) {
                        System.out.println("Conta atualizada com sucesso.");
                    } else {
                        System.out.println("ERRO: Não foi possível atualizar a conta.");
                    }
                    break;
                case 5:
                    System.out.print("Favor informar ID da conta que deseja deletar:\n> ");
                    int deleteID = scan.nextInt();
                    if (crud.delete(deleteID)) {
                        System.out.println("Conta deletada com sucesso.");
                    } else {
                        System.out.println("ERRO: Não foi possível deletar a conta.");
                    }
                    break;
                default:

                    break;
            }

        } while (op < 6);
        scan.close();
    }
}