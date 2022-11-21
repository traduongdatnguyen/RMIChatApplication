package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;



public interface ChatServerIF extends Remote{
	
	/**
	 * Function to register the hostname for the clients
	 * @param chatClient ChatClient object
	 * @throws RemoteException
	 */
	void resgiterChatClient(ChatClientIF chatClient) throws RemoteException;
	
	/**
	 * Function to broadcast message to all the clients
	 * @param message The message to broadcast
	 * @throws RemoteException
	 */
	void brosdcastMessage(String message) throws RemoteException;
	
	/**
	 * Function to add client names.
	 * @param name The name of the clients
	 * @throws RemoteException
	 */
	void addToAllClients(String name) throws RemoteException;
	
	/**
	 * Function to send direct message from one client to other.
	 * @param myName Client's name.
	 * @param message The message to send.
	 * @param receiver Client name who gets the message.
	 * @throws RemoteException
	 */
	void directMessage(String myName, String message, String receiver) throws RemoteException;
	
	/**
	 * Function to add availability of the user.
	 * @param availibiity The availability status.
	 * @throws RemoteException
	 */
	void addAvailability(boolean availibiity) throws RemoteException;
	
	/**
	 * Function to exit out of the chat application.
	 * @param name The name of client who wants to exit.
	 * @throws RemoteException
	 */
	void exitClient(String name) throws RemoteException;
	
	/**
	 * Function to change the availability status of the user.
	 * @param name Name of the user
	 * @param status The availability status to change to.
	 * @throws RemoteException
	 */
	void changeStatus(String name, boolean status) throws RemoteException;
	
	/**
	 * Function to get the list of all the user names.
	 * @return ArrayList of all the names
	 * @throws RemoteException
	 */
	ArrayList<String> getNames() throws RemoteException;
	
	/**
	 * Function to get the availability of the users.
	 * @return ArrayList of all the user availability.
	 * @throws RemoteException
	 */
	ArrayList<Boolean> getAllAvailability() throws RemoteException;
}
