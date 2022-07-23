/*
Autores: Valentino Coppola Ferrari y Constantino Byelov Serdiuk
Video: https://youtu.be/jtnjD-a8tlY
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/*
Clase TaulaJoc la cual extiende un JPanel donde pintamos el JPanel donde 
se ponen las cartas y dibujamos los cuadrados que van detras de las cartas.
 */
public class TaulaJoc extends JPanel {

    //variables globales
    private final int ANCHURA = 120;
    private final int ALTURA = 170;
    private final int X_AXIS = 10;

    @Override
    public void paintComponent(Graphics g) {
        int x = 10;
        int y = 190;
        //declaramos el Graphics
        Graphics2D g2 = (Graphics2D) g;
        //ponemos el color a verde oscuro
        g2.setColor(new Color(0, 102, 0));
        //llenamos toda la pantalla desde el 0,0 hasta la anchura y altura del panel
        g2.fill(new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight()));

        //ponemos un verde más oscuro
        g2.setColor(new Color(0, 153, 0));
        //dibujo las los cuadraditos verdes oscuros que van detras de las cartas
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                g2.fill(new Rectangle2D.Double(x, y, ANCHURA, ALTURA));
                //vamos incrementando la x para mover donde se pinta el cuadrado
                x += X_AXIS + ANCHURA;
            }
            //reseteamos el parametro x a X_AXIS y sumamos la altura a la cual dibujamos
            x = X_AXIS;
            y += X_AXIS + ALTURA;
        }

        // Cuadradito para el jugador
        g2.fill(new Rectangle2D.Double(10, 950, 120, 160));
        // Cuadraditos para la IA
        g2.fill(new Rectangle2D.Double(300, 10, ANCHURA, ALTURA));
        g2.fill(new Rectangle2D.Double(800, 10, ANCHURA, ALTURA));
        g2.fill(new Rectangle2D.Double(1300, 10, ANCHURA, ALTURA));
    }
}
