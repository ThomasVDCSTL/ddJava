package Exceptions;

public class LeavingGame extends Exception{
    public LeavingGame(){
        super("le joueur a quitté la partie");
    }
}
