import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Tree implements IColors{
    private Node root;
    private Node goalState;
    private Long generatedStates;

    public Tree(Node root, Node goalState) {
        this.root = root;
        this.goalState = goalState;
        this.generatedStates = 0L;
    }

    public Long getGeneratedStates() {
        return generatedStates;
    }

    // Busqueda en Anchura (BFS)
    public Node breadthFirstSearch() {
        generatedStates = 0L;
        Queue<Node> frontier = new ArrayDeque<>();
        Set<String> explored = new HashSet<>();
        frontier.add(root);
        explored.add(Arrays.deepToString(root.getState()));

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            List<byte[][]> successors = successorFunction(node.getState());

            for (int i = 0; i < successors.size(); i++) {
                byte[][] state = successors.get(i);
                if (!explored.contains(Arrays.deepToString(state))) {
                    generatedStates++;
                    Node child = new Node(state, node, 0);
                    if (Arrays.deepEquals(state, goalState.getState())) {
                        return child;
                    } else {
                        frontier.add(child);
                        explored.add(Arrays.deepToString(state));
                    }
                }
            }
        }
        return null;
    }

    // Busqueda en Profundidad (DFS)
    public Node depthFirstSearch() {
        generatedStates = 0L;
        Stack<Node> frontier = new Stack<>();
        Set<String> explored = new HashSet<>();
        frontier.add(root);
        explored.add(Arrays.deepToString(root.getState()));

        while (!frontier.isEmpty()) {
            Node node = frontier.pop();
            List<byte[][]> successors = successorFunction(node.getState());

            for (int i = 0; i < successors.size(); i++) {
                byte[][] state = successors.get(i);
                if (!explored.contains(Arrays.deepToString(state))) {
                    generatedStates++;
                    Node child = new Node(state, node, 0);
                    if (Arrays.deepEquals(state, goalState.getState())) {
                        return child;
                    } else {
                        frontier.add(child);
                        explored.add(Arrays.deepToString(state));
                    }
                }
            }
        }
        return null;
    }

    // Busqueda de Profundidad Limitada (DLS)
    public Node depthLimitedSearch(int limit) {
        generatedStates = 0L;
        return recursiveDepthLimitedSearch(root, limit);
    }

    private Node recursiveDepthLimitedSearch(Node node, int limit) {
        if (Arrays.deepEquals(node.getState(), goalState.getState())) {
            return node;
        } else if (node.getDepth() == limit) {
            return null;
        } else {
            List<byte[][]> successors = successorFunction(node.getState());
            for (int i = 0; i < successors.size(); i++) {
                byte[][] state = successors.get(i);
                generatedStates++;
                Node child = new Node(state, node, 0);
                Node result = recursiveDepthLimitedSearch(child, limit);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    // Busqueda de Profundidad Iterativa (IDS)
    public Node iterativeDepthSearch() {
        generatedStates = 0L;
        int limit = 0;
        while (true) {
            Node result = depthLimitedSearch(limit);
            if (result != null) {
                return result;
            }
            limit++;
        }
    }

    // Busqueda de Costo Uniforme (UCS)
    public Node uniformCostSearch() {
        generatedStates = 0L;
        PriorityQueue<Node> frontier = new PriorityQueue<>();
        Set<String> explored = new HashSet<>();
        root.setCost(0);
        frontier.add(root);

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();

            if (Arrays.deepEquals(node.getState(), goalState.getState())) {
                return node;
            }

            explored.add(Arrays.deepToString(node.getState()));
            List<byte[][]> successors = successorFunction(node.getState());

            for (int i = 0; i < successors.size(); i++) {
                byte[][] state = successors.get(i);
                int newCost = node.getCost() + 1;
                if (!explored.contains(Arrays.deepToString(state))) {
                    generatedStates++;
                    Node child = new Node(state, node, newCost);
                    frontier.add(child);
                }
            }
        }
        return null;
    }
    
    // Busqueda Bidireccional
    public Node bidirectionalSearch() {
        generatedStates = 0L;
        Queue<Node> frontierStart = new ArrayDeque<>();
        Queue<Node> frontierGoal = new ArrayDeque<>();
        Set<String> exploredStart = new HashSet<>();
        Set<String> exploredGoal = new HashSet<>();
        
        frontierStart.add(root);
        frontierGoal.add(goalState);
        exploredStart.add(Arrays.deepToString(root.getState()));
        exploredGoal.add(Arrays.deepToString(goalState.getState()));

        while (!frontierStart.isEmpty() && !frontierGoal.isEmpty()) {
            // Busqueda desde el estado "inicial"
            Node nodeStart = frontierStart.poll();
            List<byte[][]> successorsStart = successorFunction(nodeStart.getState());
            for (int i = 0; i < successorsStart.size(); i++) {
                byte[][] state = successorsStart.get(i);
                if (!exploredStart.contains(Arrays.deepToString(state))) {
                    generatedStates++;
                    Node child = new Node(state, nodeStart, 0);
                    if (exploredGoal.contains(Arrays.deepToString(state))) {
                        Node intersectionNode = getNodeFromFrontier(frontierGoal, state);
                        return getNodePathBidirectional(child, intersectionNode);
                    } else {
                        frontierStart.add(child);
                        exploredStart.add(Arrays.deepToString(state));
                    }
                }
            }
            
            // Busqueda desde el estado "objetivo"
            Node nodeGoal = frontierGoal.poll();
            List<byte[][]> successorsGoal = successorFunction(nodeGoal.getState());
            for (int i = 0; i < successorsGoal.size(); i++) {
                byte[][] state = successorsGoal.get(i);
                if (!exploredGoal.contains(Arrays.deepToString(state))) {
                    generatedStates++;
                    Node child = new Node(state, nodeGoal, 0);
                    if (exploredStart.contains(Arrays.deepToString(state))) {
                        Node intersectionNode = getNodeFromFrontier(frontierStart, state);
                        return getNodePathBidirectional(intersectionNode, child);
                    } else {
                        frontierGoal.add(child);
                        exploredGoal.add(Arrays.deepToString(state));
                    }
                }
            }
        }
        return null;
    }

    // Funcion que obtiene el camino bidireccional
    public Node getNodePathBidirectional(Node nodeFromStart, Node nodeFromGoal) {
        List<Node> pathFromStart = new ArrayList<>();
        Node current = nodeFromStart;
        while (current != null) {
            pathFromStart.add(current);
            current = current.getParent();
        }
        Collections.reverse(pathFromStart);

        List<Node> pathFromGoal = new ArrayList<>();
        current = nodeFromGoal;
        while (current != null) {
            pathFromGoal.add(current);
            current = current.getParent();
        }

        if (!pathFromGoal.isEmpty()) {
            pathFromGoal.remove(0);
        }

        List<Node> fullPath = new ArrayList<>();
        fullPath.addAll(pathFromStart);
        fullPath.addAll(pathFromGoal);

        Node unifiedChain = null;
        Node previous = null;
        for (int i = 0; i < fullPath.size(); i++) {
            Node n = fullPath.get(i);
            Node newNode = new Node(n.getState(), previous, n.getCost());
            previous = newNode;
            unifiedChain = newNode;
        }

        return unifiedChain;

    }

    // Funcion que encuentra el nodo en el sentido contrario
    private Node getNodeFromFrontier(Queue<Node> frontier, byte[][] state) {
        for (int i = 0; i < frontier.size(); i++) {
            Node node = frontier.poll();
            if (Arrays.deepEquals(node.getState(), state)) {
                return node;
            }
        }
        return null;
    }

    // Funcion Sucesor
    public List<byte[][]> successorFunction(byte[][] state){
        byte[] zeroCoords = findZero(state);
        List<byte[][]> successors = new ArrayList<>();

        if (zeroCoords[0] == 1 && zeroCoords[1] == 1) { // Centro
            successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] - 1), zeroCoords[1]})); // Arriba
            successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] - 1)})); // Izquierda
            successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] + 1)})); // Derecha
            successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] + 1), zeroCoords[1]})); // Abajo
        } else if (zeroCoords[0] == 0 && zeroCoords[1] == 0 || zeroCoords[0] == 0 && zeroCoords[1] == 2 || zeroCoords[0] == 2 && zeroCoords[1] == 0 || zeroCoords[0] == 2 && zeroCoords[1] == 2) { // Esquinas
            if (zeroCoords[0] == 0 && zeroCoords[1] == 0) {
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] + 1)})); // Derecha
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] + 1), zeroCoords[1]})); // Abajo
            } else if (zeroCoords[0] == 0 && zeroCoords[1] == 2) { // Esquina 0,2
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] - 1)})); // Izquierda
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] + 1), zeroCoords[1]})); // Abajo
            } else if (zeroCoords[0] == 2 && zeroCoords[1] == 0) { // Esquina 2,0
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] - 1), zeroCoords[1]})); // Arriba
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] + 1)})); // Derecha
            } else { // Esquina 2,2
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] - 1), zeroCoords[1]})); // Arriba
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] - 1)})); // Izquierda
            }
        } else { // Lados
            if (zeroCoords[0] == 0 && zeroCoords[1] == 1) { // Lado Superior
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] - 1)})); // Izquierda
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] + 1)})); // Derecha
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] + 1), zeroCoords[1]})); // Abajo
            } else if (zeroCoords[0] == 1 && zeroCoords[1] == 0) { // Lado Izquierdo
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] - 1), zeroCoords[1]})); // Arriba
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] + 1)})); // Derecha
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] + 1), zeroCoords[1]})); // Abajo
            } else if (zeroCoords[0] == 1 && zeroCoords[1] == 2) { // Lado Derecho
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] - 1), zeroCoords[1]})); // Arriba
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] - 1)})); // Izquierda
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] + 1), zeroCoords[1]})); // Abajo
            }
            else { // Lado Inferior
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ (byte) (zeroCoords[0] - 1), zeroCoords[1]})); // Arriba
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] - 1)})); // Izquierda
                successors.add(generateSuccessor(state, zeroCoords, new byte[]{ zeroCoords[0], (byte) (zeroCoords[1] + 1)})); // Derecha
            }
        }

        return successors;
    }

    // Encuentra la posicion del Cero
    private byte[] findZero(byte[][] state){
        byte[] coords = new byte[2];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    coords[0] = (byte) i;
                    coords[1] = (byte) j;
                    return coords;
                }
            }
        }
        return null;
    }

    // Generar el sucesor
    private byte[][] generateSuccessor(byte[][] state, byte[] zeroCoords, byte[] newCoords){
        byte[][] newState = new byte[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                newState[i][j] = state[i][j];
            }
        }
        newState[zeroCoords[0]][zeroCoords[1]] = newState[newCoords[0]][newCoords[1]];
        newState[newCoords[0]][newCoords[1]] = 0;
        return newState;
    }

    // Imprimir el camino
    public void printPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.getParent();
        }
        
        Collections.reverse(path);
    
        for (int a = 0; a < path.size(); a++) {
            System.out.println(TEXT_BRIGHT_YELLOW + "Step: " + (a + 1) + TEXT_RESET);
            Node n = path.get(a);
            n.printState();
        }
    }
}