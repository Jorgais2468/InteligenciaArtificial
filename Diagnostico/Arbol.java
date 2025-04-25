public class Arbol {
    private Nodo raiz;

    public Arbol() {
        this.raiz = null;
    }

    public boolean vacio() {
        return raiz == null;
    }

    public Nodo buscarNodo(int valor) {
        return buscarNodoRecursivo(raiz, valor);
    }

    private Nodo buscarNodoRecursivo(Nodo actual, int valor) {
        if (actual == null || actual.valor == valor) {
            return actual;
        }
        if (valor < actual.valor) {
            return buscarNodoRecursivo(actual.izquierdo, valor);
        } else {
            return buscarNodoRecursivo(actual.derecho, valor);
        }
    }
}
