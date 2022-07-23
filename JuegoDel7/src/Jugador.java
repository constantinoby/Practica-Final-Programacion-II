/*
Autores: Valentino Coppola Ferrari y Constantino Byelov Serdiuk
Video: https://youtu.be/jtnjD-a8tlY
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
Clase Jugador la cual está compuesta por un array de cartas que tiene función de mano del Jugador, 
un entero que indica la cantidad máxima de cartas que puede tener en la mano, 
otro entero que indica las cartas que tiene en ese momento en la mano y un objeto baraja.
 */
public class Jugador extends JLabel {

    //Atributos 
    private Carta c[];
    private final int Mano = 13;
    private int cartasActuales;
    Baraja baraja = new Baraja();

    /**
     * Contructor de la clase Jugador.
     */
    public Jugador() {
        c = new Carta[Mano];
        cartasActuales = 0;
    }

    //------------------------------Métodos de la clase Jugador--------------------------------
    /**
     * Método que añade una carta al jugador pasado por parametro.
     *
     * @param carta Carta que se debe de poner
     * @param jugador Número del jugador al que se le debe de añadir la carta.
     */
    public void añadirCarta(Carta carta, int jugador) {
        //Si el jugador a añadir es el jugador humano, es decir el 0 hacemos que la carta sea visible.
        if (jugador == 0) {
            c[cartasActuales].setVisible(true);
        }
        //cogemos la carta le ponemos el numero, el palo y para acabar el icono.
        c[cartasActuales].setNum(carta.getNum());
        c[cartasActuales].setPalo(carta.getPalo());
        c[cartasActuales].setIcon(carta.getIcon());

        //tras eso aumentamos en uno las cartas que tenemos en la mano
        cartasActuales++;
    }

    /**
     * Método que pone las cartas del jugador humano para que se puedan ver y
     * jugar con ellas.
     */
    public void cartasHumano() {
        int x = 10;
        //recorremos la mano del jugador 
        for (int i = 0; i < Mano; i++) {
            //ponemos la posición/restringimos la zona en la que debe de estar la carta del jugador.
            c[i].setBounds(x, 950, 120, 170);
            //ponemos la carta visible
            c[i].setVisible(true);
            //aumentamos el valor de X la cual se debe de deplazar a la derecha
            x += 130;
        }
    }

    /**
     * Método actualizaCartas, el cual actualiza el numero de cartas que tiene
     * el jugador en ese momento.
     *
     * @param nJ Número de jugador al cual se le debe de actualizar el label de
     * cartas
     * @param mano numero de cartas que se debe de poner.
     */
    public void actualizaCartas(int nJ, int mano) {
        //actualizamos el número de cartas que tiene cada jugador
        cartasActuales = mano;
        setText(String.valueOf(cartasActuales));
        setForeground(Color.white);

        //Miramos que jugador es, si es el jugador humano pasamos al caso 1 donde ponemos el 
        switch (nJ) {
            case 1://caso jugador humano
                setFont(new Font("Calibri", Font.BOLD, 30));
                setBounds(50, 850, 120, 160);
                break;
            case 2://caso Jugador IA 2
                setFont(new Font("Calibri", Font.BOLD, 50));
                setBounds(350, 10, 120, 160);
                break;
            case 3://caso Jugador IA 3
                setFont(new Font("Calibri", Font.BOLD, 50));
                setBounds(850, 10, 120, 160);
                break;
            case 4://caso Jugador IA 4
                setFont(new Font("Calibri", Font.BOLD, 50));
                setBounds(1350, 10, 120, 160);
                break;
        }
    }

    /**
     * Método cartaAtras la cual pone la imagen de carta atras en los jugadores
     * de la IA
     *
     * @param nJ numero de jugador pasado por parametro
     * @param cartasMano
     */
    public void cartaAtras(int nJ, int cartasMano) {

        //seleccionamos la imagen que queremos utilizar 
        ImageIcon imagen = new ImageIcon("Cartas/card_back_blue.png");

        //miramos el número de jugador que nos han pasado por parametro
        switch (nJ) {
            case 2://caso 2 jugador IA 2
                setText("" + cartasMano);
                setIcon(new ImageIcon(imagen.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH)));
                setBounds(300, 10, 120, 160);
                setHorizontalTextPosition(JLabel.CENTER);
                break;
            case 3://caso 3 jugador IA 3
                setText("" + cartasMano);
                setIcon(new ImageIcon(imagen.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH)));
                setBounds(800, 10, 120, 160);
                setHorizontalTextPosition(JLabel.CENTER);
                break;
            case 4://caso 4 jugador IA 4
                setText("" + cartasMano);
                setIcon(new ImageIcon(imagen.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH)));
                setBounds(1300, 10, 120, 160);
                setHorizontalTextPosition(JLabel.CENTER);
                break;
        }
    }

    /**
     * Método que quita todas las cartas de un jugador y pone el estado de la
     * carta en no puesta sobre el tablero.
     */
    public void limpia() {
        //ponemos que las cartas actuales sean cero.
        cartasActuales = 0;
        //for de la mano donde ponemos las cartas de la mano en invisible 
        //y ponemos el estado de la carta en no posiciona sobre el tablero
        for (int i = 0; i < Mano; i++) {
            c[i].setVisible(false);
            c[i].setEnPos(false);
        }
    }

    //-----------------------------------GETTERS Y SETETRS-------------------------------------------
    /**
     * Getter de las Cartas que tiene en mano.
     *
     * @return Devuelve la cantidad de cartas que tenga dicho jugador en la mano
     * actualmente.
     */
    public int getCartasMano() {
        return cartasActuales;
    }

    /**
     * Getter del Objeto Carta la cual enseña toda la mano que tenga el jugador
     * en ese momento.
     *
     * @return Devuelve el array de objetos Carta del jugador.
     */
    public Carta[] getCartas() {
        return c;
    }

    /**
     * Setter de las cartas que tiene en mano actualmente.
     *
     * @param cartasActuales pone el entero pasado por parametro.
     */
    public void setCartasActuales(int cartasActuales) {
        this.cartasActuales = cartasActuales;
    }

}
