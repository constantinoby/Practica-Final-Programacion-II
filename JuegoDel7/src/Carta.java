/*
Autores: Constantino Byelov Serdiuk y Valentino Coppola Ferrari
Video: https://youtu.be/jtnjD-a8tlY
 */
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

//Enumerado de los palos
enum Palos {
    TR, DI, CO, PI
}

/*
Clase Carta que esta compuesta por un número, un palo, la imagen de la carta 
y un boolean que dice si la carta esta en el tablero o no.
 */
public class Carta extends JLabel {

    private int num;
    private Palos palo;
    private ImageIcon imagen;
    private boolean enPos;

    //constructor de una carta
    public Carta(int num, Palos palo, ImageIcon imagen) {
        this.num = num;
        this.palo = palo;
        this.imagen = imagen;
        this.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH)));
    }

    //Constructor vacio
    public Carta() {

    }

    //Getter del numero de la carta
    public int getNum() {
        return num;
    }

    //Setter del numero de la carta
    public void setNum(int num) {
        this.num = num;
    }

    //Getter del palo de la carta
    public Palos getPalo() {
        return palo;
    }

    //Setter del palo de la carta
    public void setPalo(Palos palo) {
        this.palo = palo;
    }

    //Getter de la imagen de la carta
    public ImageIcon getImagen() {
        return imagen;
    }

    //Getter de el estado en posicion.
    public boolean isEnPos() {
        return enPos;
    }

    //Setter para poner el estado de la carta.
    public void setEnPos(boolean enPos) {
        this.enPos = enPos;
    }

    //toString de la carta
    @Override
    public String toString() {
        return "[" + palo + "  " + num + "]";
    }

}
