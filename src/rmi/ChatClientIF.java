package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Interface class for chat client
 * @author rikeshpuri
 *
 */
public interface ChatClientIF extends Remote   {
	
	/**
	 * Function to print the message.
	 * @param message The message string.
	 */
	void retriveMessage(String message) throws RemoteException;
}
