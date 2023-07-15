/**
 * @author Jorge Rayo La Calle
 */
public class Contrasenia {
    private final StringBuilder contrasenia; //Longitud entre 8 y 16 caracteres
    private final int tamanio;

    /**
     * Constructor sin parametros que inicializa un StringBuilder donde se guardará la contraseña
     * y se inicializa el tamaño que tendrá la contraseña de manera aleatoria con el método privado
     * tamanioAleatorio()
     */
    public Contrasenia() {
        this.contrasenia = new StringBuilder();
        this.tamanio = tamanioAleatorio();
    }

    private int tamanioAleatorio() {
        return (int) ((Math.random() * (17 - 8)) + 8);
    }

    /**
     * @param mayusculas true = uso false = no uso
     * @param numeros true = uso false = no uso
     * @param simbolos true = uso false = no uso
     * Todos los parametros son booleanos que dictaminan su uso o no en la generación de la contraseña
     * Uso de los métodos auxiliares privados letras(), numeros() y simbolos()
     */
    public void generarContrasenia(boolean mayusculas, boolean numeros, boolean simbolos) {
        int tipoCaracter, numSimbolos = 0;
        for (int i = 0; i < this.tamanio; i++) {
            tipoCaracter = (int) ((Math.random() * 3));
            switch (tipoCaracter) {
                case 0 -> letras(mayusculas);
                case 1 -> {
                    if (numeros) {
                        numeros();
                    } else {
                        i--;
                    }
                }
                case 2 -> {
                    if (simbolos && numSimbolos < 3) {
                        numSimbolos++;
                        simbolos();
                    } else {
                        i--;
                    }
                }
            }
        }
    }

    private void letras(boolean mayusculas) {
        String todasLetras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String minus = "abcdefghijklmnopqrstuvwxyz";
        if (mayusculas) {
            contrasenia.append(todasLetras.charAt((int) (Math.random() * (todasLetras.length()))));
        } else {
            contrasenia.append(minus.charAt((int) (Math.random() * (minus.length()))));
        }
    }

    private void numeros() {
        String todosNumeros = "0123456789";
        contrasenia.append(todosNumeros.charAt((int) (Math.random() * (todosNumeros.length()))));
    }

    private void simbolos() {
        String todosSimbolos = "!·$%&/()=?¿*^Ç+-_^[]`'.,;:><ª\\\"|@#~€¬";
        contrasenia.append(todosSimbolos.charAt((int) (Math.random() * (todosSimbolos.length()))));
    }

    @Override
    public String toString() {
        return "   "+contrasenia.toString();
    }
}