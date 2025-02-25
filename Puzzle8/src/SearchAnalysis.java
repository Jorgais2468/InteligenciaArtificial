public class SearchAnalysis {
    private Tree tree;
    private Node solutionBFS;
    private Node solutionDFS;
    private Node solutionDLS;
    private Node solutionIDS;
    private Node solutionUCS;

    public SearchAnalysis(Tree tree) {
        this.tree = tree;
    }

    public Node analyzeBFS() {
        long startTime = System.currentTimeMillis();
        this.solutionBFS = tree.breadthFirstSearch();
        long endTime = System.currentTimeMillis();
        printResults("Breadth First Search", solutionBFS, startTime, endTime, tree.getGeneratedStates());
        return solutionBFS;
    }

    public Node analyzeDFS() {
        long startTime = System.currentTimeMillis();
        this.solutionDFS = tree.depthFirstSearch();
        long endTime = System.currentTimeMillis();
        printResults("Depth First Search", solutionDFS, startTime, endTime, tree.getGeneratedStates());
        return solutionDFS;
    }

    public Node analyzeDLS(int depth) {
        long startTime = System.currentTimeMillis();
        this.solutionDLS = tree.depthLimitedSearch(depth);
        long endTime = System.currentTimeMillis();
        printResults("Depth Limited Search", solutionDLS, startTime, endTime, depth, tree.getGeneratedStates());
        return solutionDLS;
    }

    public Node analyzeIDS() {
        long startTime = System.currentTimeMillis();
        this.solutionIDS = tree.iterativeDepthSearch();
        long endTime = System.currentTimeMillis();
        printResults("Iterative Depth Search", solutionIDS, startTime, endTime, solutionIDS.getDepth(), tree.getGeneratedStates());
        return solutionIDS;
    }

    public Node analyzeUCS() {
        long startTime = System.currentTimeMillis();
        this.solutionUCS = tree.uniformCostSearch();
        long endTime = System.currentTimeMillis();
        printResults("Uniform Cost Search", solutionUCS, startTime, endTime, tree.getGeneratedStates());
        return solutionUCS;
    }

    public Node analyzeBS() {
        long startTime = System.currentTimeMillis();
        Node solutionBS = tree.bidirectionalSearch();
        long endTime = System.currentTimeMillis();
        printResults("Bidirectional Search", solutionBS, startTime, endTime, tree.getGeneratedStates());
        return solutionBS;
    }

    private void printResults(String searchType, Node solution, long startTime, long endTime, long generatedStates) {
        long duration = endTime - startTime;
        int states = countStates(solution);
        String colorDuration = (duration < 1000) ? IColors.TEXT_BRIGHT_GREEN : IColors.TEXT_BRIGHT_RED;
        String colorStates = (states < 100) ? IColors.TEXT_BRIGHT_GREEN : IColors.TEXT_BRIGHT_RED;
        String colorGeneratedStates = (generatedStates < 5000) ? IColors.TEXT_BRIGHT_GREEN : IColors.TEXT_BRIGHT_RED;

        String header = formatHeader(searchType);

        System.out.println(IColors.TEXT_BRIGHT_BLUE + header + IColors.TEXT_RESET);
        System.out.println("States (Movements): \t" + colorStates + states + IColors.TEXT_RESET);
        System.out.println("Generated States: \t" + colorGeneratedStates + generatedStates + IColors.TEXT_RESET);
        System.out.println("Duration: \t\t" + colorDuration + duration + " ms" + IColors.TEXT_RESET);
        System.out.println();
    }

    private void printResults(String searchType, Node solution, long startTime, long endTime, int depth, long generatedStates) {
        long duration = endTime - startTime;
        int states = (solution == null) ? 0 : countStates(solution);
        String colorDuration = (duration < 1000) ? IColors.TEXT_BRIGHT_GREEN : IColors.TEXT_BRIGHT_RED;
        String colorStates = (states < 100) ? IColors.TEXT_BRIGHT_GREEN : IColors.TEXT_BRIGHT_RED;
        String colorGeneratedStates = (generatedStates < 5000) ? IColors.TEXT_BRIGHT_GREEN : IColors.TEXT_BRIGHT_RED;

        String header = formatHeader(searchType);

        System.out.println(IColors.TEXT_BRIGHT_BLUE + header + IColors.TEXT_RESET);

        if (solution == null) {
            System.out.println("States (Movements): \t" + IColors.TEXT_BRIGHT_RED  + "NOT FOUND" + IColors.TEXT_RESET);
            System.out.println("Generated States: \t" + IColors.TEXT_BRIGHT_RED  + generatedStates + "???" + IColors.TEXT_RESET);
            System.out.println("Duration: \t\t" + IColors.TEXT_BRIGHT_RED + duration + " ms???" + IColors.TEXT_RESET);
            System.out.println("Result: \t\t" + IColors.TEXT_BRIGHT_RED + "Depth limit reached" + IColors.TEXT_RESET + " -> " + depth + " levels");
            System.out.println();
        } else {
            System.out.println("States (Movements): \t" + colorStates + states + IColors.TEXT_RESET);
            System.out.println("Generated States: \t" + colorGeneratedStates + generatedStates + IColors.TEXT_RESET);
            System.out.println("Duration: \t\t" + colorDuration + duration + " ms" + IColors.TEXT_RESET);
            System.out.println("Result: \t\t" + IColors.TEXT_BRIGHT_GREEN + "Found" + IColors.TEXT_RESET + " -> " + depth + " levels");
            System.out.println();
        }
    }

    // Metodo que permite imprimir el formato del encabezado
    private String formatHeader(String searchType) {
        int totalLength = 72;
        int searchTypeLength = searchType.length();
        int sideLength = (totalLength - searchTypeLength - 2) / 2;
        String side = "─".repeat(sideLength);
        String header = side + "┤" + searchType + "├" + side;
        if (header.length() < totalLength) {
            header += "─";
        }
        return header;
    }

    // Metodo para contar los estados
    private int countStates(Node node) {
        int count = 0;
        while (node != null) {
            count++;
            node = node.getParent();
        }
        return count;
    }
}
