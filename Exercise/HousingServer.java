
//NOTE :


// Below Java code covers whole Section 1 and partly Section 2 (Extra Credit).
// In Section 2 it includes Delivery of message i.e point 5 in Exercise sheet where landlord can see "all" the messages, reply  and delete them when he needs to.


// I used software PUTTY - SSH Client for connecting to the housing server socket in my code.
// Enter the following specifications in PUTTY - HostName(or IP Address) : localhost, Port no. : 5555, Connection Type : RAW and then open it.

import java.io.*;
import java.net.*;
import java.util.*;

public class Housing_Bulletin_Board {
	static ServerSocket housing;
	static Socket service = null;
	static String entry1 = null, entry2 = null;
	static ArrayList<Object> list1 = new ArrayList<Object>();
	static ArrayList<Object> list2 = new ArrayList<Object>();
	static ArrayList<ArrayList<Object>> list3 = new ArrayList<ArrayList<Object>>();
	static PrintWriter socketOutput = null;
	static BufferedReader socketInput = null;
	static int d = 1;

	public static String[] landlord() throws IOException {
		String adv = null, con = null;
		socketOutput.println("\nPOST YOUR HOUSING ADV. HERE \n");
		socketOutput.println("POST:");
		adv = socketInput.readLine();
		socketOutput.println("\nPOST YOUR CONTACT DETAILS HERE: ");
		con = socketInput.readLine();
		socketOutput.println("\nYOUR HOUSING ADV. CONTACT ID IS :  " + d + "  !! PLEASE REMEMBER THIS ID FOR ACCESSING MESSAGES FROM USERS REGARDING YOUR HOUSING ADV. !! \n" );
		d = d+1;
		String[] returnArray = new String[] { adv, con };
		return returnArray;
		
	}

	public static ArrayList<ArrayList<Object>> renter(ArrayList<Object> list1, ArrayList<Object> list2, ArrayList<ArrayList<Object>> list3) throws IOException {
		int n = 0, m = 0;
		String entry1 = null, entry2 = null, entry3 = null, con = null, msg = null;
		socketOutput.println("\nENTER THE WORD -show- TO SEE ALL THE AVAILABLE HOUSING ADV. \n");
		entry1 = socketInput.readLine();
		if (entry1.equalsIgnoreCase("show")) {
			socketOutput.println("\nHERE IS THE LIST OFF ALL HOUSING ADV. IN REVERSE CHRONOLOGICAL ORDER: \n");
			socketOutput.println("CONTACT ID - ADV. \n");
			for (int i = list1.size() - 1; i >= 0; i--) {
				socketOutput.println(i + 1 + " - " + list1.get(i));
			}
		}
		socketOutput.println("\nENTER THE WORD -contact- IF YOU WANT CONTACT DETAILS OF A LANDLORD WHO POSTED A PARTICULAR HOUSING ADV. \n");
		entry2 = socketInput.readLine();
		if (entry2.equalsIgnoreCase("contact")) {
			socketOutput.println("\nENTER THE CONTACT ID OF HOUSING ADV. IN THE ABOVE LIST FOR WHICH YOU WANT THE CONTACT DETAILS \n");
			n = Integer.parseInt(socketInput.readLine());
			m = list2.size();
			Collections.reverse(list2);
			con = (String) list2.get(m - n);
			socketOutput.println("\nHERE ARE THE CONTACT DETAILS OF LANDLORD YOU REQUESTED: " + con);
		}
		socketOutput.println("\nENTER THE WORD -message- IF YOU WANT TO SEND A MESSAGE TO THE LANDLORD WHO POSTED A PARTICULAR HOUSING ADV. \n");
		entry3 = socketInput.readLine();
		if (entry3.equalsIgnoreCase("message")) {
			socketOutput.println("\nENTER THE CONTACT ID OF HOUSING ADV. IN THE ABOVE LIST FOR WHICH YOU WANT TO SEND A MEASSAGE \n");
			n = Integer.parseInt(socketInput.readLine());
			socketOutput.println("\nENTER THE MESSAGE THAT YOU WANT TO SEND \n");
			msg = socketInput.readLine();
			ArrayList<Object> msgListForCurrentAd = list3.get(n-1);
			msgListForCurrentAd.add(msg);
			list3.set(n-1, msgListForCurrentAd);
		}
		    return list3;
	}
	
	public static void messages(ArrayList<ArrayList<Object>> list3)throws IOException{
		int m = 0, n = 0;
		String entry1 = null, entry2 = null, reply = null;
		socketOutput.println("\nENTER THE CONTACT ID OF YOUR HOSING ADV. FOR WHICH YOU WANT TO ACCESS MESSAGES FROM USERS \n");
		m = Integer.parseInt(socketInput.readLine());
		ArrayList<Object> msgListForCurrentAd = list3.get(m-1);
		socketOutput.println("\nHERE ARE THE MESSAGES FOR YOUR HOUSING ADV. \n");
		socketOutput.println("\nMSG NO. - MSG \n");
		for (int i = msgListForCurrentAd.size() - 1; i >= 0; i--) {
		socketOutput.println(i + 1 + " - " + msgListForCurrentAd.get(i));
		}
		socketOutput.println("\nIF YOU WANT TO REPLY ENTER THE WORD -reply- \n");
		entry1 = socketInput.readLine();
		if (entry1.equalsIgnoreCase("reply")){
			socketOutput.println("\nENTER YOUR REPLY HERE \n");
			reply = socketInput.readLine();
			msgListForCurrentAd.add(reply);	
		}
		socketOutput.println("\nIF YOU WANT TO DELETE A MESSAGE ENTER THE WORD -delete- \n");
		entry2 = socketInput.readLine();
		if (entry2.equalsIgnoreCase("delete")){
			socketOutput.println("\nENTER THE MESSAGE NUMBER YOU WANT TO DELETE \n");
			n = Integer.parseInt(socketInput.readLine());
			msgListForCurrentAd.remove(n-1);	
		}

	}

	public static void main(String[] args) throws IOException {
		
		try {
			housing = new ServerSocket(5555);
			System.out.println("Waiting for a client");
			service = housing.accept();
			System.out.println("Client accepted.");
			socketOutput = new PrintWriter(service.getOutputStream(), true);
			socketInput = new BufferedReader(new InputStreamReader(service.getInputStream()));
		} catch (IOException e) {
			System.out.println("error \n" + e.toString());
			System.exit(-1);
		}

		do {
			socketOutput.println("HOUSING BULLETIN BOARD WELCOMES YOU \n");
			socketOutput.println("PLEASE ENTER THE WORD -share- IF YOU ARE A LANDLORD OR ENTER THE WORD -look- IF YOU ARE A RENTER AND WORD -exit- IF YOU WANT TO LOG OUT anytime \n");
			entry1 = socketInput.readLine();
			socketOutput.println("\nyou choose to " + entry1);
			if (entry1.equalsIgnoreCase("share")) {
				socketOutput.println("\n IF YOU WANT TO POST A NEW HOUSING ADV. ENTER THE WORD -post- OR ENTER THE WORD -message- IF YOU WANT TO ACCESS MESSAGES FROM USERS REGARDING YOUR PREVIOUS HOUSING ADV. \n");
				entry2 = socketInput.readLine();
				if (entry2.equalsIgnoreCase("post")){
				socketOutput.println("\nyou choose to post a new housing adv. \n");
				String[] array = landlord();
				String adv = array[0];
				String con = array[1];
				list1.add(adv);
				list2.add(con);
				list3.add(new ArrayList<Object>());
				}
				if (entry2.equalsIgnoreCase("message")){
			    socketOutput.println("\nyou choose to access messages from users regarding your previous housing adv. \n");
			    messages(list3);
				}
			} else if (entry1.equalsIgnoreCase("look"))
				list3 = renter(list1, list2, list3);
			else
				socketOutput.println("wrong entry");

			socketOutput.println("\n \n");

		} while (!entry1.equalsIgnoreCase("exit"));

		socketOutput.close();
		socketInput.close();
		housing.close();

	}
}