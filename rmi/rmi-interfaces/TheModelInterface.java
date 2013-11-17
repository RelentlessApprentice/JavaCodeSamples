import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TheModelInterface extends Remote {
    
    public void add(String extension) throws RemoteException;
    public String getValue() throws RemoteException;
    
}
