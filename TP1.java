import java.io.*;
import java.util.*;

public class TP1 {

    public static void main(String[] args) {
        int op;
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("[Terminal]\n    > Seja bem vindo, por favor escolha o que deseja:");
            System.out.println("[1]Criar uma conta (Informe nome, CPF, Estado e Saldo)");
            System.out.println(
                    "[2]Realizar uma tranferência (Informe duas contas, a primeira para debitar e outra para creditar, e o valor da transferência.)");
            System.out.println("[3]Encontrar um registro (Informe ID)");
            System.out.println("[4]Atualizar um registro (Informe ID, Nome, CPF, Estado e Saldo)");
            System.out.println("[5]Deletar um registro (Informe ID)");
            System.out.println("[6]Finalizar programa");
            op = scan.nextInt();

            switch (op) {
                case 1:

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

        } while (op != 6);
        
        scan.close();
    }

}