import java.util.Scanner;
import java.io.*;

public class TP2 {

    // Método simples para menu
    public static void menu() {
        System.out.println("\n[Terminal]\n");
        System.out.println("[1] Criar uma conta");
        System.out.println(
                "[2] Realizar uma tranferência (Informar duas contas - a primeira para debitar e outra para creditar - e o valor da transferência.)");
        System.out.println("[3] Encontrar um registro (Informar ID)");
        System.out.println("[4] Atualizar um registro (Informar ID, Nome, CPF, Cidade e Saldo)");
        System.out.println("[5] Deletar um registro (Informar ID)");
        System.out.println("[6]Finalizar programa \n>Seja bem vindo, por favor escolha o número que deseja:");
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
        String nome, cpf, cidade;
        byte op;
        int saldo;
        int id = getId();
        do {
            menu();

            op = scan.nextByte();
            scan.nextLine();
            switch (op) {
                // Criar conta
                case 1:
                    id++;
                    System.out.print("Favor informar Nome:\n> ");
                    nome = scan.nextLine();
                    System.out.print("Favor informar CPF:\n> ");
                    cpf = scan.nextLine();
                    System.out.print("Favor informar Cidade:\n> ");
                    cidade = scan.nextLine();
                    System.out.print("Favor informar Saldo:\n> ");
                    saldo = scan.nextInt();
                    conta = new Conta(id, nome, cpf, cidade, saldo);
                    crud.create(conta, id);
                    break;
                // Tranferencia
                case 2:
                    System.out.print("Favor informar ID da conta que REALIZARA o depósito:\n> ");
                    int id1 = scan.nextInt();
                    System.out.print("Favor informar ID da conta que RECEBERA o depósito:\n> ");
                    int id2 = scan.nextInt();
                    System.out.print("Favor informar o valor da transferência:\n> ");
                    int value = scan.nextInt();
                    crud.bankTransfer(id1, id2, value);
                    break;
                // Encontrar contra
                case 3:
                    System.out.print("Favor informar ID da conta que procura:\n> ");
                    int searchID = scan.nextInt();
                    Conta searchedAccount = crud.readId(searchID);
                    if(searchedAccount != null){
                        System.out.println(searchedAccount);
                    } else{
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                // Atualizar conta
                case 4:
                    System.out.print("Favor informar ID da conta que deseja atualizar:\n> ");
                    int updateID = scan.nextInt();

                    if(!crud.isSaved(updateID)){
                        System.out.println("ERRO: Conta não existe");
                        break;
                    }

                    scan.nextLine();
                    System.out.print("Favor informar Nome:\n> ");
                    nome = scan.nextLine();
                    System.out.print("Favor informar CPF:\n> ");
                    cpf = scan.nextLine();
                    System.out.print("Favor informar Cidade:\n> ");
                    cidade = scan.nextLine();
                    System.out.print("Favor informar Saldo:\n> ");
                    saldo = scan.nextInt();
                    conta = new Conta(updateID, nome, cpf, cidade, saldo);
                    if (crud.update(conta)) {
                        System.out.println("Conta atualizada com sucesso.");
                    } else {
                        System.out.println("ERRO: Não foi possível atualizar a conta.");
                    }
                    break;
                // Deletar
                case 5:
                    System.out.print("Favor informar ID da conta que deseja deletar:\n> ");
                    int deleteID = scan.nextInt();
                    if (crud.delete(deleteID)) {
                        System.out.println("Conta deletada com sucesso.");
                    } else {
                        System.out.println("ERRO: Não foi possível deletar a conta.");
                    }
                    break;
                // Finalizar
                case 6:
                    System.out.println("\nEncerrando programa");
                    break;
                default:
                    System.out.println("ERRO: Opção inválida");
                    op = 0;
                    break;
            }
        } while (op < 6);
        scan.close();
    }
}