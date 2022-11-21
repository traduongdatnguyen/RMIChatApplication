package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


import rmi.ChatClientIF;
import rmi.ChatServerIF;

public class ChatServer extends UnicastRemoteObject implements ChatServerIF {

	private static final long serialVersionUID = 1L;
	
	/**
	 * List of host names.
	 */
	private ArrayList<ChatClientIF> chatClients; 
	
	/**
	 * List of client names.
	 */
	private ArrayList<String> allClients;
	
	/**
	 * List of client availability.
	 */
	private ArrayList<Boolean> allAvailability;
	
	/**
	 * 
	 * @throws RemoteException
	 */
	protected ChatServer() throws RemoteException {
		chatClients = new ArrayList<ChatClientIF>();
		allClients = new ArrayList<String>();
		allAvailability = new ArrayList<Boolean>();
	}

	/**
	 * Function to register the hostname for the clients
	 * @param chatClient ChatClient object
	 * @throws RemoteException
	 */
	@Override
	public synchronized void resgiterChatClient(ChatClientIF chatClient) throws RemoteException {
		this.chatClients.add(chatClient);
	}

	/**
	 * Function to broadcast message to all the clients
	 * @param message The message to broadcast
	 * @throws RemoteException
	 */
	@Override
	public synchronized void brosdcastMessage(String message) throws RemoteException {
		int i = 0;
		while (i < chatClients.size()) {
			chatClients.get(i++).retriveMessage(message);
		}
		
	}
	
	/**
	 * Function to add client names.
	 * @param name The name of the clients
	 * @throws RemoteException
	 */
	@Override
	public synchronized void addToAllClients(String name) throws RemoteException{
		this.allClients.add(name);
	}

	/**
	 * Function to send direct message from one client to other.
	 * @param myName Client's name.
	 * @param message The message to send.
	 * @param receiver Client name who gets the message.
	 * @throws RemoteException
	 */
	@Override
	public void directMessage(String myName, String message, String receiver) throws RemoteException {
		
		int myIndex = 0;
		int rIndex = -1;
		for (int j = 0; j < allClients.size();j++ ) {
			if (allClients.get(j).equals(myName)) {
				myIndex = j;
			}
		}
		for (int i = 0; i < allClients.size();i++ ) {
			if (allClients.get(i).equals(receiver)) { 
				rIndex = i;
				if (allAvailability.get(i) != true) {
					rIndex = myIndex;
					myName = "";
					message = "User is not available to chat right now.";
				}
			}
		} 
		if (rIndex == -1) {
			rIndex = myIndex;
			myName = "";
			message = "User does not exixts";
		}
		
		chatClients.get(rIndex).retriveMessage(myName + ": " + message);	
	}

	/**
	 * Function to add availability of the user.
	 * @param availibiity The availability status.
	 * @throws RemoteException
	 */
	@Override
	public void addAvailability(boolean availibility) throws RemoteException {
		this.allAvailability.add(availibility);
		
	}

	/**
	 * Function to exit out of the chat application.
	 * @param name The name of client who wants to exit.
	 * @throws RemoteException
	 */
	@Override
	public void exitClient(String name) throws RemoteException {
		for (int i = 0; i < allClients.size();i++ ) {
			if (allClients.get(i).equals(name)) { 
				chatClients.remove(i);
				allClients.remove(i);
				allAvailability.remove(i);
			}
		}	
	}
	
	/**
	 * Function to change the availability status of the user.
	 * @param name Name of the user
	 * @param status The availability status to change to.
	 * @throws RemoteException
	 */
	public void changeStatus(String name, boolean status) throws RemoteException {
		for (int i = 0; i < allClients.size();i++ ) {
			if (allClients.get(i).equals(name)) { 
				allAvailability.set(i, status);
			}
		} 
	}

	
	/**
	 * Function to get the list of all the user names.
	 * @return ArrayList of all the names
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<String> getNames() throws RemoteException {
		
		return this.allClients;
	}

	
	/**
	 * Function to get the availability of the users.
	 * @return ArrayList of all the user availability.
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<Boolean> getAllAvailability() throws RemoteException {
		return this.allAvailability;
	}
}
