import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class TheModel extends UnicastRemoteObject implements TheModelInterface {

    private static final long serialVersionUID = 3474253300296222212L;
    private String sentence = "";

    protected TheModel() throws RemoteException {
        super();
    }

    public void add(String extension) throws RemoteException {
        sentence = sentence + " " + extension;
        System.out.println("[Model] Changing state : "+ sentence);
    }

    public String getValue() throws RemoteException {
        return sentence;
    }

}
