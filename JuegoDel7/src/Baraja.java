
/*
Autores: Valentino Coppola Ferrari y Constantino Byelov Serdiuk
Video: https://youtu.be/jtnjD-a8tlY
 */
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

/*
Clase Baraja que esta compuesta por un array de cartas y las imagenes asociadas a ellas.
 */
public class Baraja {

    //Array de las imagenes de las cartas
    private ImageIcon fotos[];
    //Numero de cartas por palo
    private final int CARTASPALO = 13;
    //Numero de palos que hay.
    private final int NPALO = 4;
    // 52 Número máximo de cartas= 13 cartas * 4 Palos
    private final int TOTALCARTAS = CARTASPALO * NPALO;
    //Array doble de cartas 
    private Carta c[][];

    //Atributos para poner las cartas
    private final int ANCHURA = 120;
    private final int ALTURA = 170;
    private int X_AXIS = 10;
    private int Y_AXIS = 190;

    /**
     * Contructor de la clase Baraja, el cual inicializa el array de cartas y
     * llama al metodo de inicialización de imagenes.
     */
    public Baraja() {
        //Le damos valores al array de cartas 
        c = new Carta[NPALO][CARTASPALO];
        //inicializamos el array de imagenes correspondiente a cada carta
        imagenCarta();
    }

    /**
     * Getter de las cartas que devuelve las cartas que hay.
     *
     * @return devuelve el array de cartas.
     */
    public Carta[][] getCartas() {
        return c;
    }

    /**
     * Método imagenCarta() el cual inicializa la imagen correspondiente a cada
     * carta.
     */
    public void imagenCarta() {

        //inicializamos el array de imagenes 
        fotos = new ImageIcon[TOTALCARTAS];
        //mediante un for añadimos todas las cartas mirando cada el path
        for (int i = 0; i < TOTALCARTAS; i++) {
            fotos[i] = new ImageIcon("Cartas/" + (i + 1) + ".png");
        }
    }

    /**
     * Método que crea la baraja .
     */
    public void creaBaraja() {
        int carta = 0;
        for (int i = 0; i < NPALO; i++) {
            for (int j = 0; j < CARTASPALO; j++) {
                c[i][j] = new Carta(j + 1, Palos.values()[i], fotos[carta++]);

            }
        }

        //Mediante dos fors vamos a recorrer las 52 posiciones en las que tenemos que poner cartas, 
        //mediante estos dos fors vamos a settear el tamaño de cada carta sobre el tablero, además de su posición el mismo.
        for (int i = 0; i < NPALO; i++) {
            for (int j = 0; j < CARTASPALO; j++) {
                //vamos carta por carta, palo por plao poniendo las medidas correctas a los labels que son las cartas
                c[i][j].setBounds(X_AXIS, Y_AXIS, ANCHURA, ALTURA);
                //tras poner una carta aumentamos la X, asi se desplaza a la derecha donde pondremos otra carta
                X_AXIS += 10 + c[i][j].getWidth();
            }
            //tras acabar de poner toda una fila reseteamos el valor de X_AXIS
            X_AXIS = 15;
            //y incrementamos la Y para que pase a la siguiente fila
            Y_AXIS += 15 + c[i][0].getHeight();
        }
    }

    /**
     * Método de reparto de cartas entre los jugadores.
     *
     * @param jugadores Array de objetos de jugador que se pasa.
     */
    public void reparto(Jugador jugadores[]) {

        //mediante dos fors recorremos 4 veces añadiendo así 13 cartas a cada jugador
        for (int i = 0; i < jugadores.length; i++) {
            for (int j = 0; j < CARTASPALO; j++) {

                //añadimos una carta al jugador que esta pasado por indice
                jugadores[i].añadirCarta(c[i][j], i);
            }
        }
    }

    /**
     * Método que sirve para reiniciar las cartas de la baraja.
     */
    public void resetCartas() {

        //declaramos un contador que nos va a ayudar progresar por el array de fotos
        int contadorC = 0;

        //Mediante 2 fors recorremos todas las cartas
        for (int i = 0; i < NPALO; i++) {
            for (int j = 0; j < CARTASPALO; j++) {

                //ponemos su icono, el palo, el numero y que no estan puestas en el tablero.
                c[i][j].setIcon(new ImageIcon(fotos[contadorC].getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH)));
                contadorC++;
                c[i][j].setPalo(Palos.values()[i]);
                c[i][j].setNum(j + 1);
                c[i][j].setEnPos(false);
            }
        }
    }

    /**
     * Método que pone en visión las cartas seleccionadas.
     */
    public void visibilizar() {

        //ponemos todas las cartas en visible mediante 2 fors recorremos todas las cartas 
        for (int i = 0; i < NPALO; i++) {
            for (int j = 0; j < CARTASPALO; j++) {

                //con el setVisible ponemos la carta/JLabel en visible
                c[i][j].setVisible(true);
            }
        }
    }

    /**
     * Método que quita la visión de la carta que se ha seleccionado.
     */
    public void quitarVsion() {
        //ponemos todas las cartas en visible mediante 2 fors recorremos todas las cartas 
        for (int i = 0; i < NPALO; i++) {
            for (int j = 0; j < CARTASPALO; j++) {

                //ponemos la carta en invisible con el setVisible(false) y ponemos el estado ,que no estan puestas.
                c[i][j].setVisible(false);
                c[i][j].setEnPos(false);
            }
        }
    }

    /**
     * Método que mezcla las cartas de cartas.
     */
    public void mezclar() {
        // la implementació Durstenfeld de l'algorisme Fisher-Yates
        Random ran = new Random();
        //Mediante un for descendiente vamos cambiando los indices de los numeros
        for (int i = TOTALCARTAS - 1; i > 0; i--) {
            //Sacamos un numero random usando el indice del for.
            int rnum = ran.nextInt(i);
            //hacemos el cambio del indice del for con el numero random 
            permutar(i, rnum);
        }

        //tras la mezcla ponemos las imagenes nuevas a cada carta.
        int contadorC = 0;

        //recorremos todas las cartas mediante dos fors 
        for (int i = 0; i < NPALO; i++) {
            for (int j = 0; j < CARTASPALO; j++) {

                //reseteamos el icono.
                c[i][j].setIcon(new ImageIcon(fotos[contadorC++].getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH)));

            }
        }
    }

    /**
     * Método permutar al cual le llegan dos enteros y mediante ellos saca 2
     * cartas y las mezcla.
     *
     * @param i el indice del for en el que esta en ese momento
     * @param j es el número random que llega
     */
    private void permutar(int i, int j) {

        //Atributos random pasados por parametro
        int PrimerPalo = i / 13;
        int PaloA = j / 13;

        int PrimerNumero = i % 13;
        int NumeroA = j % 13;

        //Igual que el numero cogemos una variable palo auxiliar con el numero  y su respectivo palo y lo cambiamos por otro aleatorio.
        Palos paloAux = c[PrimerPalo][PrimerNumero].getPalo();
        //deteamos el numero y palo de la carta aleatorio con la de numeros y palo primeros.
        c[PrimerPalo][PrimerNumero].setPalo(c[PaloA][NumeroA].getPalo());
        //tras esto seteamos el palo a la carta aleatoria
        c[PaloA][NumeroA].setPalo(paloAux);

        //Cambiamos el numero de la carta, llamamos a una variable auxiliar y despues mediante un palo y numero aleatorio lo cambuamos a ese.
        int aux = c[PrimerPalo][PrimerNumero].getNum();
        c[PrimerPalo][PrimerNumero].setNum(c[PaloA][NumeroA].getNum());
        c[PaloA][NumeroA].setNum(aux);

        //Cambiamos la imagen por la aleatoria.
        ImageIcon im = fotos[i];
        fotos[i] = fotos[j];
        fotos[j] = im;

    }
}
