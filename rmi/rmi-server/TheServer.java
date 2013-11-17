import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TheServer extends UnicastRemoteObject implements TheInterface {

    private static final long serialVersionUID = 6058739354329752567L;
    private TheModelInterface model = new TheModel();
    
    
    public TheServer() throws RemoteException {
        try {
            model.add("Initial Value Set By Server ");
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("TheServer", this);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String args[]) {
        try {
            TheServer server = new TheServer();
            System.out.println("I'm up");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String talkToMe(String message) throws RemoteException {
        String response = prepareResponse(message);

        System.out.println("In:  " + message);
        System.out.println("Out: " + response);

        return response;
    }

    private String prepareResponse(String message) {
        String result = "";
        int responseNumber = message.length() %7 ;
        switch (responseNumber) {
            case 0: result = "No";                      break;
            case 1: result = "Go away";                 break;
            case 2: result = "\"" + message + "\" ?!";  break;
            case 3: result = "I think I like you.";     break;
            case 4: result = "It depends.";             break;
            case 5: result = "Ok...";                   break;
            case 6: result = "I think '"+getOneWord(message)+"' sounds good."; break;
        }
        return result;
    }

    private String getOneWord(String message) {
        String[] words = message.split(" ");
        int iWord = (words.length > 1) ? words.length - 2 : words.length - 1;
        String word = words[iWord];
        return word;
    }

    public TheModelInterface getModelReference() throws RemoteException {
        return model;
    }

    public void tryChangingValue(StringBuilder sb) throws RemoteException {
        System.out.println("Old value: "+sb.toString());
        sb.append("I told you to go away.");
        System.out.println("New value: "+sb.toString());
    }

}