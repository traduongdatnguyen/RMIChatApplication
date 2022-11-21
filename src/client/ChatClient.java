package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;


import rmi.ChatClientIF;
import rmi.ChatServerIF;

public class ChatClient extends UnicastRemoteObject implements ChatClientIF, Runnable {

	/**
	 * Object of class ChatServerIF
	 */
	private ChatServerIF chatServer;
	
	/**
	 * Client Name
	 */
	private String name;
	
	/**
	 * Constructor.
	 * @param name Name of the user.
	 * @param chatServer ChatServerIF object.
	 * @throws RemoteException
	 */
	protected ChatClient(String name, ChatServerIF chatServer) throws RemoteException {
		this.chatServer = chatServer;
		this.name = name;
		chatServer.addToAllClients(name);
		chatServer.addAvailability(true);
		chatServer.resgiterChatClient(this);
	}

	/**
	 * Function to print the message.
	 * @param message The message string.
	 */
	@Override
	public void retriveMessage(String message) throws RemoteException {
		System.out.println(message);
		
	}

	@Override
	public void run() {
		System.out.println("!!! Welcome !!!");
		System.out.println("Please enter one of the following commands: ");
		System.out.println("friends	- Lists all the friends online with there status of availability.");
		System.out.println("talk {username} {message}	- Sends message to the username given;");
		System.out.println("broadcast {message}	- Breadcasts message to all the online users.");
		System.out.println("busy	- Change your availibility to busy.");
		System.out.println("available	- Change your availability to available");
		System.out.println("exit	- Exit from chat application.");
		System.out.println("------------------------------------");
		Scanner commandScanner = new Scanner(System.in);
		String userInput;
		boolean active = true;
		while (active) {
			userInput = commandScanner.nextLine();
			String[] inputArray = userInput.trim().split(" ");
			switch (inputArray[0]) {
			case "friends":
				ArrayList<String> allNames = new ArrayList<String>();
				ArrayList<Boolean> allAvailability = new ArrayList<Boolean>();
				try {
					allNames = chatServer.getNames();
					allAvailability = chatServer.getAllAvailability();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				for(int i = 0; i < allNames.size(); i++) {
					System.out.print(allNames.get(i) + " - ");
					if (allAvailability.get(i)) System.out.println("Available");
					else System.out.println("Busy");
				}
				
				break;
				
			case "talk":
				String receiver = inputArray[1];
				String message = "";
				for (int i = 2; i < inputArray.length; i++) {
					message = message + " " + inputArray[i];
				}
				try {
					chatServer.directMessage(this.name, message, receiver);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
				
			case "broadcast": 
				String message1 = "";
				for (int i = 1; i < inputArray.length; i++) {
					message1 = message1 + " " + inputArray[i];
				}
				try {
					chatServer.brosdcastMessage(this.name + " : " + message1);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
							
				
			case "busy":
				try {
					chatServer.changeStatus(name, false);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				System.out.println("You status is changed to busy");
				break;
				
			case "available":
				try {
					chatServer.changeStatus(name, true);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				System.out.println("Your status is changed to available");
				break;
				
			case "exit":
				active = false;
				break;
				
			default:
				System.out.println("!! command error");
				break;
			}
			
		}
		System.out.println("Have a nice day!");
		try {
			chatServer.exitClient(this.name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
