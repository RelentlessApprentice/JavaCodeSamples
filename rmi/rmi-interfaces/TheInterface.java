import java.rmi.Remote;
import java.rmi.RemoteException;


public interface TheInterface extends Remote {

	String talkToMe(String message) throws RemoteException;
	TheModelInterface getModelReference() throws RemoteException;
	void tryChangingValue(StringBuilder sb) throws RemoteException;
	void tryChangingValueOfRemoteObject(TheModelInterface clientSideModel) throws RemoteException;

}
