package exception;

public class CreatingObjectExcption extends Exception{
    public CreatingObjectExcption(){
        super("Cant create object. wrong key vaalue");
    }
}
