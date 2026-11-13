import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinaryTree arbol = new BinaryTree();
        int opcion = 0;

        do {
            System.out.println("\n========== MENÚ ==========");
            System.out.println("1. Construir árbol");
            System.out.println("2. Mostrar árbol");
            System.out.println("3. Recorrido Preorden");
            System.out.println("4. Recorrido Inorden");
            System.out.println("5. Recorrido Posorden");
            System.out.println("6. Mostrar factores de equilibrio");
            System.out.println("7. Balancear árbol (AVL)");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
            } else {
                scanner.nextLine();
                System.out.println("Por favor ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    arbol.buildTree(scanner);
                    break;
                case 2:
                    arbol.printTree();
                    break;
                case 3:
                    System.out.println("\nPreorden: " + arbol.preorder());
                    break;
                case 4:
                    System.out.println("\nInorden: " + arbol.inorder());
                    break;
                case 5:
                    System.out.println("\nPosorden: " + arbol.postorder());
                    break;
                case 6:
                    arbol.printBalanceFactors();
                    break;
                case 7:
                    arbol.balance();
                    break;
                case 8:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 8);

        scanner.close();
    }
}
