import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Call: java TheClient host testType arguments talk/ref/val arguments ...
 * 
 * - [talk]: java TheClient localhost talk "message 1" "message 2" ...
 * 
 * - [ref] : run multiple clients with different arguments at once:
 *           java TheClient localhost ref A
 *           java TheClient localhost ref B
 *           
 * - [val] : java TheClient localhost val
 */
public class TheClient {

	public static void main(String args[]) {
		try {
			Registry registry = LocateRegistry.getRegistry(args[0]);
			TheInterface remoteObject = (TheInterface) registry.lookup("TheServer");
			
			String testType = args[1];
			if("talk".equalsIgnoreCase(testType)){
			    talk(args, remoteObject);
			} else if("ref".equalsIgnoreCase(testType)){
			    testReferences(args, remoteObject);
			} else if("val".equalsIgnoreCase(testType)){
			    testPassingByValue(remoteObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private static void talk(String[] args, TheInterface remoteObject)
            throws RemoteException, InterruptedException {
        
        for(int i=2; i<args.length; i++){
        	String response = remoteObject.talkToMe(args[i]);
        	
        	System.out.println("Out: "+args[i]);
        	System.out.println("In:  "+response);
        	Thread.sleep(500);
        }
    }
    
    private static void testReferences(String[] args, TheInterface remoteObject) 
            throws RemoteException, InterruptedException {
        
        System.out.println("Adding: "+args[2]);
        TheModelInterface model = remoteObject.getModelReference();
        for(int i=0; i<10; i++){
            model.add(args[2]);
            Thread.sleep(1000);
        }
        System.out.println("Result: "+model.getValue());
    }

    private static void testPassingByValue(TheInterface remoteObject) 
            throws RemoteException {
        
        StringBuilder sb = new StringBuilder("Talk to me, Server. ");
        System.out.println("Old value: "+sb.toString());
        remoteObject.tryChangingValue(sb);
        System.out.println("New value: "+sb.toString());
    }
	
}