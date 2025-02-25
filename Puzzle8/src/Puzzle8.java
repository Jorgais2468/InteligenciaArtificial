///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                                                       //
//  Programa Puzzle 8                                              Creado----------------------10/02/2025                                //
//  Inteligencia Artificial                                        Actualizado-----------------18/02/2025                                //
//  Creado por: Escalante Guadarrama Jorge Eduardo                 Busqueda de soluciones (Arbol, Nodo)                                  //
//                                                                                                                                       //
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Puzzle8 implements IColors {
    public static void main(String[] args) {
        /*
         * 1. Estado Inicial
         * 2. Funcion Sucesor (a partir de un estado, genera todos los sucesores)
         * 3. Caminos
         * 4. Funcion Objetivo
        */

        /*
         * Coordenadas Fila y Columna
         * ----------------------------
         * (0,0) (0,1) (0,2)
         * (1,0) (1,1) (1,2)
         * (2,0) (2,1) (2,2)
         * ----------------------------
        */

        /*
         * Centro = 4 Movimientos
         * Esquinas = 2 Movimientos
         * Lados = 3 Movimientos
        */

        printMessage();

        
        byte[][] initialState = {
            { 7, 2, 4 },
            { 5, 0, 6 },
            { 8, 3, 1 }
        };

        byte[][] goalState = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 0 }
        };

        Tree tree = new Tree(new Node(initialState, null, 0), new Node(goalState, null, 0));

        SearchAnalysis searchAnalysis = new SearchAnalysis(tree);

        Node solutionBFS = searchAnalysis.analyzeBFS();
        Node solutionDFS = searchAnalysis.analyzeDFS();
        // Node solutionDLS = searchAnalysis.analyzeDLS(20);
        // Node solutionIDS = searchAnalysis.analyzeIDS();
        Node solutionUCS = searchAnalysis.analyzeUCS();
        Node solutionBS = searchAnalysis.analyzeBS();

        // Imprimir camino
        System.out.println(TEXT_BRIGHT_PURPLE + "─────────────────────────────────┤Path├─────────────────────────────────" + TEXT_RESET);
        // tree.printPath(solutionBFS);
        // tree.printPath(solutionDFS);
        // tree.printPath(solutionDLS);
        // tree.printPath(solutionIDS);
        // tree.printPath(solutionUCS);
        tree.printPath(solutionBS);
    }

    // Metodo para imprimir el mensaje
    private static void printMessage() {
        String message = """
                \n────────────────────────────────────────────────────────────────────────
                         █████╗ ██████╗ ██╗   ██╗███████╗███████╗██╗     ███████╗
                        ██╔══██╗██╔══██╗██║   ██║╚══███╔╝╚══███╔╝██║     ██╔════╝
                        ╚█████╔╝██████╔╝██║   ██║  ███╔╝   ███╔╝ ██║     █████╗
                        ██╔══██╗██╔═══╝ ██║   ██║ ███╔╝   ███╔╝  ██║     ██╔══╝
                        ╚█████╔╝██║     ╚██████╔╝███████╗███████╗███████╗███████╗
                         ╚════╝ ╚═╝      ╚═════╝ ╚══════╝╚══════╝╚══════╝╚══════╝
                                                    - Escalante Guadarrama Jorge Eduardo
                ────────────────────────────────────────────────────────────────────────
                        """;
        System.out.println(TEXT_BRIGHT_BLUE + message + TEXT_RESET);
    }

}