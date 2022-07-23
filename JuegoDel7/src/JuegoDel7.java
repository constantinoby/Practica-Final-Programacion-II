/*
Autores: Valentino Coppola Ferrari y Constantino Byelov Serdiuk
Video: https://youtu.be/jtnjD-a8tlY
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Clase principal del programa donde creamos la estructura del programa además
 * de gestionar todos los eventos que pueden pasar en la clase, ya sea
 * pulsaciones de botones, o cartas a poner.
 */
public class JuegoDel7 {

    //Declaramos atributos
    private JFrame frame;
    private TaulaJoc mesa;
    private Baraja barajaBase, barajaJuego;
    private Jugador jugadores[];
    boolean tirada = false, enCurso = false;
    private int turno = 0;
    private JButton jugar, mezcla, paso, reiniciar, turnoJ;
    private JTextField desc;

    /**
     * Método inicio el cual declara todas las componentes y las añade al frame
     * pricipal además de crear todas las barajas con sus respectivas cartas.
     */
    public void inicio() {

        //--------------------------------------Creación JFrame y paneles--------------------------------------
        //Inicializamos variables
        jugadores = new Jugador[4];

        //Creamos el JFrame y le damos nombre
        frame = new JFrame("Juego del 7");
        //ponemos el tamaño de la ventana
        frame.setSize(1750, 1200);
        //hacemos que al ejecutar la aplicación la ventana emerja en el medio de la pantalla
        frame.setLocationRelativeTo(null);
        //ponemos que no se pueda cambiar el tamaño de la ventana
        frame.setResizable(false);
        //si el frame se cierra, decimos que se acabe el programa
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        //Hacemos un getter del contenido del frame y se lo asignamos a el contenedor jPantalla
        Container jPantalla = frame.getContentPane();
        jPantalla.setLayout(new BorderLayout());

        //declaramos el JPanel que va ha tener los botones y el texto de información.
        JPanel jPanelAbajo = new JPanel();
        //Al JPanel le damos un layout tipo BorderLayout para poder organizar de manera correcta los componentes
        jPanelAbajo.setLayout(new BorderLayout());

        //Declaramos el JPanel jPanelBotones, es el panel que va a contender los botones 
        JPanel jPanelBotones = new JPanel();

        //---------------------Declaracion botones----------------------------------------
        //Inicializamos el primer boton, el "Mezclar"
        mezcla = new JButton("Mezclar");
        //ponemos el color azul de fondo
        mezcla.setBackground(new Color(51, 153, 255));
        //Y le añadimos el ActionListener para saber cuando ha sido apretado
        mezcla.addActionListener(new Listener());

        //Inicializamos el segundo JButton, el "Jugar"
        jugar = new JButton("Jugar");
        //Ponemos el boton en deshabilitado para que no se pueda usar hasta que no se pulse el boton de mezclar
        jugar.setEnabled(false);
        //Y le añadimos el ActionListener para saber cuando se ha pulsado
        jugar.addActionListener(new Listener());

        //Inicializamos el tercer boton que puede salir en la pantalla principal, el "Reiniciar"
        reiniciar = new JButton("Reiniciar");
        //Ponemos que el botón esta deshabilitado para que no se pueda usar hasta el momento de pulsar el "Mezclar"
        reiniciar.setEnabled(false);
        //Y le añadimos el ActionListener para saber cuando se ha pulsado
        reiniciar.addActionListener(new Listener());

        //Seguimos con el siguiente botón el cuarto, el cual sale después de empezar el juego.
        paso = new JButton("Paso");
        //Le ponemos un fondo azul.
        paso.setBackground(new Color(51, 153, 255));
        //Y ponemos que el botón se esté visible, el cual habilitaremos al momento de detectar que el usario ha empezado el juego.
        paso.setVisible(false);
        //Y le añadimos un ActionListener para saber cuando se ha pulsado.
        paso.addActionListener(new Listener());

        //Seguimos con el siguiente botón el quinto, el cual sale al momento de jugar la IA, al pulsarlo se pasa el turno de la IA 
        turnoJ = new JButton("Turno jugador");
        //Le ponemos un fondo azul
        turnoJ.setBackground(new Color(51, 153, 255));
        //Y lo ponemos invisible, el botón se activará al momento de haber jugado una carta el jugador o el jugador haber pasado de turno porque 
        //no tiene cartas a poner.
        turnoJ.setVisible(false);
        //Y le agregamos un ActionListener para saber cuando se ha pulsado
        turnoJ.addActionListener(new Listener());

        //declaramos un JTextField desc el cual nos va ha poner la información que necesitamos abajo a la izquierda del JPanel
        desc = new JTextField("Antes de jugar necesita mezclar la baraja");
        //Ponemos que el texto no se pueda editar
        desc.setEditable(false);
        //Con todos los botones ya inicializados podemos añadirlos al panel de botones
        jPanelBotones.add(mezcla);
        jPanelBotones.add(jugar);
        jPanelBotones.add(reiniciar);
        jPanelBotones.add(paso);
        jPanelBotones.add(turnoJ);

        //El panel de botones lo añadimso en el centro del panel de abajo
        jPanelAbajo.add(jPanelBotones, BorderLayout.CENTER);
        //Añadimos la descripción abajo del panel
        jPanelAbajo.add(desc, BorderLayout.SOUTH);
        //Añadimso el panel de abajo que tiene los botones y la información, en dirección SUD del contenedor principal.
        jPantalla.add(jPanelAbajo, BorderLayout.SOUTH);

//------------------------------------------Mesa creación------------------------------------------------------
        //Con los botones ya añadidos nos falta añadir los cuadrados verdes y las cartas 
        //Inicializamos el tablero, el cual nos pinta los cuadrados verdes.
        mesa = new TaulaJoc();
        //Ponemos la mesa con un layout de null para poder poner las cartas donde queramos
        mesa.setLayout(null);

        //Mediante un for creamos todos los jugadores 
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Jugador();

            //Mediante un for de 13 añadimos 13 objetos carta al jugador y les metemo un listener a cada carta
            for (int j = 0; j < 13; j++) {

                jugadores[i].getCartas()[j] = new Carta();
                jugadores[i].getCartas()[j].addMouseListener(new ListenerCarta());

            }
        }

        //Ponemos que las cartas del jugador humano sean visibles
        jugadores[0].cartasHumano();
        //Cogemos el array de cartas del jugador humano y lo asignamos a un array de cartas del jugador.
        Carta cartasJ[] = jugadores[0].getCartas();

        //Mediante un for de 13 voy añaiendo las cartas al tablero
        for (int i = 0; i < 13; i++) {
            mesa.add(cartasJ[i]);
        }
        //ponemos las puntuaciones a los jugadores y los añadimos al tablero
        for (int i = 0; i <= 3; i++) {
            jugadores[i].actualizaCartas((i + 1), 0);
            mesa.add(jugadores[i]);
        }

        //Creamos la baraja con la cual vamos a jugar y la cual es la que mezclaremos.
        barajaJuego = new Baraja();
        //Creamos la baraja
        barajaJuego.creaBaraja();

        //Creamos una segunda baraja la cual no se va ha ver, la cual no se mezcla
        barajaBase = new Baraja();
        //Creamos la baraja
        barajaBase.creaBaraja();
        //quitamos la visión de la misma, para que no se vea.
        barajaBase.quitarVsion();

        //Inicializamos dos array bidimensionales los cuales llenamos con las cartas de cada baraja.
        //Inicializamos el array bidimensional para la baraja que se va ha mezclar y es visible
        Carta cartasJuego[][] = barajaJuego.getCartas();
        //Inicializamos el array bidimensional para la baraja que no se va ha mezclar y está invisible
        Carta cartasBase[][] = barajaBase.getCartas();
        //for doble donde recorremos todas las 52 cartas y las añadimos al tablero
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                //añadimos las cartas visibles 
                mesa.add(cartasJuego[i][j]);
                //añadimos las cartas que no estan mezcladas y son invisibles
                mesa.add(cartasBase[i][j]);
            }

        }

        //añadimos al Contenedor la mesa con todo ya hecho
        jPantalla.add(mesa, BorderLayout.CENTER);

        //ponemos que el frame sea visible
        frame.setVisible(true);
    }

    /**
     * Método que comprueba si una carta se puede colocar en el tablero o no,
     * devuelve un booleano que es true si se puede colocar y false en caso
     * contrario.
     *
     * @param palo Parametro palo donde se pasa el palo de la carta.
     * @param numero Parámetro número que pasa el número de la carta.
     * @param carta Parámetro que pasa el objeto carta.
     * @return Devuelve el boolean que dice si se puede poner la carta o no.
     */
    public boolean movimientoPosible(int palo, int numero, Carta carta) {

        //ponemos 
        boolean enable = false;

        //si la carta que nos llega tiene como número el 7 la ponemos visible en el tablero.
        if (carta.getNum() == 7) {

            barajaBase.getCartas()[carta.getPalo().ordinal()][carta.getNum() - 1].setEnPos(true);
            barajaBase.getCartas()[carta.getPalo().ordinal()][carta.getNum() - 1].setVisible(true);
            enable = true;
        } else {//en otro caso miramos el numero de la carta
            switch (carta.getNum()) {

                //Tenemos 2 casos criticos el caso del rey y el caso del As, para ello dependiendo del numero miramos una cosa u otra.
                case 13://si la carta es un trece el rey y la reina de ese palo esta puesta. 
                    if (barajaBase.getCartas()[palo][carta.getNum() - 2].isEnPos() == true) {

                        //Ponemos al rey en la baraja y la hacemos visible.
                        barajaBase.getCartas()[palo][carta.getNum() - 1].setEnPos(true);
                        barajaBase.getCartas()[palo][carta.getNum() - 1].setVisible(true);
                        enable = true;

                    }
                    break;
                case 1: //si la carta es un As miramos si en la posición del 2 haya una carta
                    if (barajaBase.getCartas()[palo][carta.getNum()].isEnPos() == true) {
                        //hay el 2 puesto ponemos la carta del As y la hacemos visible
                        barajaBase.getCartas()[palo][carta.getNum() - 1].setEnPos(true);
                        barajaBase.getCartas()[palo][carta.getNum() - 1].setVisible(true);
                        enable = true;

                    }
                    break;

                default://para cualquier otra carta miramos si tiene cartas adjacentes puesta si es que si la ponemos en la baraja y la hacemos visible.
                    if (barajaBase.getCartas()[palo][carta.getNum() - 2].isEnPos() == true || barajaBase.getCartas()[palo][carta.getNum()].isEnPos() == true) {

                        barajaBase.getCartas()[palo][carta.getNum() - 1].setEnPos(true);
                        barajaBase.getCartas()[palo][carta.getNum() - 1].setVisible(true);
                        enable = true;
                    }
                    break;
            }
        }
        //hacemos return del boolean
        return enable;
    }

    /**
     * Método turnoIA que mira que carta puede jugar el bot y mira si el bot ha
     * llegado a 0 cartas así ganando la maquina.
     *
     * @param turno turno en el que esta el bot, asi sabemos de que bot se
     * trata.
     */
    public void turnoIA(int turno) {

        //declaramos un boolean que mira si se ha posido colocar la carta, en caso verdadero se saldria del bucle de busqueda de cartas.
        boolean posible = false;

        //Hacemos un for donde mira si el bot puede colocar cualquier carta que tenga en la mano.
        for (int i = 0; !posible && (i < 13); i++) {

            //Si la carta que tiene en ese momento se puede poner entramos al condicional.
            if (movimientoPosible(jugadores[turno].getCartas()[i].getPalo().ordinal(), jugadores[turno].getCartas()[i].getNum(), jugadores[turno].getCartas()[i])
                    && !jugadores[turno].getCartas()[i].isEnPos()) {

                //decimos que se ha podido poner una carta
                posible = true;
                //cogemos la carta la ponemos en la baraja.
                jugadores[turno].getCartas()[i].setEnPos(true);
                //decrementamos el numero de cartas del bot
                jugadores[turno].setCartasActuales(jugadores[turno].getCartasMano() - 1);
                //actualizamos el numero de cartas que se ven en pantalla.
                jugadores[turno].cartaAtras(turno + 1, jugadores[turno].getCartasMano());

                //Ahora comprobamos que tras poner la carta si el bot tiene 0 cartas, si es verdad ha ganado.
                if (jugadores[turno].getCartasMano() == 0) {

                    //cogemos el emoticono del jugador que ha ganado
                    ImageIcon icono = new ImageIcon("Cartas/Jug" + turno + "Riu.png");
                    //Y lo enseñamos por pantalla mediante un JOptionPane.
                    JOptionPane.showMessageDialog(null, "HA GANADO EL JUGADOR " + (turno + 1) + " !!!", "Uep!", JOptionPane.INFORMATION_MESSAGE, icono);
                    //cambiamos el color del turno de azul a blanco, deshabilitamos el boton de turno y ponemos de azul el boton de reiniciar.
                    turnoJ.setBackground(Color.white);
                    turnoJ.setEnabled(false);
                    reiniciar.setBackground(new Color(51, 153, 255));
                }

                //si se han recorrido todas las cartas y no se ha puesto ninguna se incremnta el turno y el jugador pasa.
            } else if (i == 12) {
                desc.setText("El jugador " + (turno + 1) + " pasa");
            }
        }
    }

    /**
     * Método Listener que gestiona las acciones que se deben de hacer al
     * presionar un botón.
     */
    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {//dependiendo del comando que nos llegue hacemos una cosa u otra.
                //Caso de botón de mezcla.
                case "Mezclar":

                    //Cogemos la baraja de juego y la mezclamos
                    barajaJuego.mezclar();
                    Carta cartas[][] = barajaJuego.getCartas();
                    mezcla.setBackground(Color.white);
                    //Cambiamos el color del reiniciar y lo activamos.
                    reiniciar.setBackground(Color.white);
                    reiniciar.setEnabled(true);
                    //cambiamos el fondo del jugar y activamos el boton
                    jugar.setEnabled(true);
                    jugar.setBackground(new Color(51, 153, 255));
                    //actualizamos la descripción.
                    desc.setText("La baraja se ha mezclado");
                    break;

                //caso jugar
                case "Jugar":

                    //Ponemos que la partida esta en curso
                    enCurso = true;
                    //quitamos la visión de mezclar, jugar y activamos el boton de paso.
                    mezcla.setVisible(false);
                    jugar.setVisible(false);
                    paso.setVisible(true);
                    //repartimos las cartas a los jugadores
                    barajaJuego.reparto(jugadores);
                    //actulizamos el numero de cartas del jugador humano a 13.
                    jugadores[0].actualizaCartas(1, 13);

                    //ponemos la carta de atras a los botos y ponemos el número de cartas que tiene el bot a 13.
                    for (int i = 1; i <= 3; i++) {
                        jugadores[i].cartaAtras((i + 1), 13);
                    }

                    //quitamos la visión de la baraja de juego.
                    barajaJuego.quitarVsion();
                    //guardamos las cartas en un array de cartas.
                    cartas = barajaJuego.getCartas();
                    desc.setText("Las cartas se han repartido, es su turno, si tiene un 7 pongalo en el tablero.");
                    break;

                //caso Reiniciar
                case "Reiniciar":

                    //ponemos el color del fondo a blanco
                    reiniciar.setBackground(Color.white);
                    //reiniciamos el turno a 0.
                    turno = 0;
                    //ponemos tirada a false.
                    tirada = false;
                    //si la partida no se ha comenzado
                    if (enCurso == false) {
                        // reseteamos las imagenes de las cartas y las cartas en si.
                        barajaJuego.imagenCarta();
                        barajaJuego.resetCartas();
                        //cambiamos el fondo de mezcla.
                        mezcla.setBackground(new Color(51, 153, 255));
                        //deshabilitamos el boton de jugar y lo ponemos con un fondo blanco.
                        jugar.setEnabled(false);
                        jugar.setBackground(Color.white);
                        //desactivamos el boton de reiniciar.
                        reiniciar.setEnabled(false);
                        //quitamos el boton de pasar.
                        paso.setVisible(false);
                        //y actulizamos la descripción por pantalla para que sea igual que al iniciar el programa.
                        desc.setText("Antes de jugar se ha de mezclar la baraja");
                        //por ultimo visibilizamos la baraja de juego.
                        barajaJuego.visibilizar();
                    } else {
                        //en caso de que la partida haya empezado.
                        //resetamos las imagenes de las cartas y las cartas en si.
                        barajaJuego.imagenCarta();
                        barajaJuego.resetCartas();

                        //Actulizamos 
                        for (int i = 0; i <= 3; i++) {
                            jugadores[i].actualizaCartas((i + 1), 0);
                        }

                        //ponemos que la baraja sea visible
                        barajaJuego.visibilizar();

                        for (int i = 0; i < 4; i++) {
                            jugadores[i].limpia();
                        }
                        //cambiamos el fondo de mezclar y lo hacemos visible.
                        mezcla.setBackground(new Color(51, 153, 255));
                        mezcla.setVisible(true);
                        //activamos el boton de jugar, le cambiamos el fondo y lo hacemos visible.
                        jugar.setEnabled(false);
                        jugar.setBackground(Color.white);
                        jugar.setVisible(true);
                        //deshabilitamos el botón de reinciar
                        reiniciar.setEnabled(false);

                        //quitamos la visión al boton de paso
                        paso.setVisible(false);
                        //quitamos la visión del boton de turno jugador y lo activamos 
                        //y le cambiamos el color, para la siguiente vez que se pase por ese punto que se vea de la forma correcta.
                        turnoJ.setVisible(false);
                        turnoJ.setEnabled(true);
                        turnoJ.setBackground(new Color(51, 153, 255));
                        desc.setText("Antes de jugar se ha de mezclar la baraja");
                        //quitamos la visión a la baraja donde se han puesto las cartas.
                        barajaBase.quitarVsion();

                    }
                    break;

                //Caso que el jugadro pasa 
                case "Paso":
                    //ponemos el boolean de tirada a false.
                    tirada = false;
                    //aumentamos el turno
                    turno++;
                    //Si el turno es mayor que cero significa que el turno de un bot.
                    if (turno > 0) {
                        //Actualizamos la descripción, informando de quien es turno
                        desc.setText("Es el torno del jugador " + (turno + 1));
                        //y le damos el turno a jugador de la ia pasando por parametro el turno, que nos da el bot que va ha jugar.
                        turnoIA(turno);
                    }
                    //quitamos la visión del boton de paso y ponemos visible el boton de turno jugador.
                    paso.setVisible(false);
                    turnoJ.setVisible(true);
                    break;

                case "Turno jugador":
                    //ponemos el boolean tirada a false.
                    tirada = false;
                    //aumentamos el turno
                    turno++;
                    //si el turno es 4, significa que el turno delo jugador
                    if (turno == 4) {
                        //reiniciamos el turno a 0
                        turno = 0;
                        //actualizamos la descripción.
                        desc.setText("Es su turno, ponga una carta.");
                        //quitamos la vision al boton de turno jugador y ponemos visible el boton de paso. 
                        turnoJ.setVisible(false);
                        paso.setVisible(true);
                    } else if (turno > 0) {//si el turno es mayor que 0 significa que el turno de un bot
                        //actulizamos la descripción, informando de quien es el turno.
                        desc.setText("Es el torno del jugador " + (turno + 1));
                        //y le damos el turno a jugador de la ia pasando por parametro el turno, que nos da el bot que va ha jugar.
                        turnoIA(turno);
                    }
                    break;
            }
        }
    }

    /**
     * Método ListenerCarta que gestiona las acciones que se deben de hacer
     * cuando el jugador humano toca las cartas.
     */
    public class ListenerCarta implements MouseListener {

        //Miramos cuando se presiona el boton del ratoóm.
        @Override
        public void mousePressed(MouseEvent e) {

            //si el turno es 0, es decir es el turno del jugador y no se ha puesto una carta.
            if (turno == 0 && !tirada) {
                //declramaos un boolean que nos dira si la carta presionada es del jugador.
                boolean encontrado = false;
                int counter = 0;

                //mientras no se hayan recorrido todas las cartas de la mano del jugador y no se haya encontrado la carta clickada.
                while (counter < 13 && !encontrado) {
                    //si la carta que tenemos en la mano actualmente es igual al source del evento,
                    //ponemos que se ha encontrado la carta asi saliendo del bucle.
                    if (jugadores[0].getCartas()[counter] == (Carta) e.getSource()) {
                        encontrado = true;
                    }
                    //si no se ha encontrado aumentamos el counter.
                    counter++;
                }

                //Miramos que la carta clickada se pueda poner en el tablero
                if (movimientoPosible(jugadores[0].getCartas()[counter - 1].getPalo().ordinal(),
                        jugadores[0].getCartas()[counter - 1].getNum(), jugadores[0].getCartas()[counter - 1])) {
                    //ponemos tirada a true.
                    tirada = true;
                    //actuliazamos el numero de cartas del jugador humano.
                    jugadores[0].getCartas()[counter - 1].setVisible(false);
                    //enseñamos la actulización por pantalla.
                    jugadores[0].actualizaCartas(1, jugadores[0].getCartasMano() - 1);
                    //quitamos la visibilidad del boton de pasar.
                    paso.setVisible(false);
                    //ponemos visible el boton de turno jugador.
                    turnoJ.setVisible(true);
                    //actulizamos la descripción indicando que carta ha puesto el jugador.
                    desc.setText("Has puesto la carta " + jugadores[0].getCartas()[counter - 1]);
                    //Y miramos si al jugador humano le queda cartas, si no le quedan cartas el jugador gana.
                    if (jugadores[0].getCartasMano() == 0) {
                        //cogemos la imagen de jugador.
                        ImageIcon icono = new ImageIcon("Cartes/Jug0Riu.png");
                        //Enseñamos el mensaje que el jugador ha ganado
                        JOptionPane.showMessageDialog(null, "HAS GANADO !!!", "GANADOR", JOptionPane.INFORMATION_MESSAGE, icono);
                        //actulizamos el color del turno jugador y lo deshabilitamos.
                        turnoJ.setBackground(Color.white);
                        turnoJ.setEnabled(false);
                        //ponemos el color del boton de reiniciar ha azul, haciendo asi que sea el unico boton que se pueda clickar.
                        reiniciar.setBackground(new Color(51, 153, 255));
                        //actulizamos la descripción, indicando que el juego ha acabado.
                        desc.setText("Simulación acabada");
                    } else {//en caso de que el jugadro siga teniendo cartas ponemos en visible el turno de jugador.
                        turnoJ.setVisible(true);
                    }

                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    //Clase main que ejecuta el subprograma de inicio.
    public static void main(String[] args) throws Exception {
        new JuegoDel7().inicio();
    }

}
