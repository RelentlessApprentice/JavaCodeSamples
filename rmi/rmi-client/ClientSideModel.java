import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ClientSideModel extends UnicastRemoteObject implements TheModelInterface {

    private static final long serialVersionUID = -8827712985532223959L;
    private String sentence = "";

    protected ClientSideModel() throws RemoteException {
        super();
    }

    public void add(String extension) throws RemoteException {
        sentence = sentence + " | " + extension;
        System.out.println("[ClientSideModel] Changing state : "+ sentence);
    }

    public String getValue() throws RemoteException {
        return sentence;
    }

}
