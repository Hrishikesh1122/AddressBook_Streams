package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Person;

public class AddressBookRunner {
	
	 private static Scanner sc = new Scanner(System.in);
	 List<Person> personList = new ArrayList<>();

	/**
	 *Displays welcome message 
	 */
	private void displayWelcome() {
		System.out.println("Welcome to address book");
	}
	
	/**
	 * UC2 
	 * This method adds object person and its fields to ArrayList
	 */
	private void add() {
		 Person person = new Person();
		 System.out.println("First Name :");
		 person.setFirstName(sc.next());
		 System.out.println("Last name : ");
		 person.setLastName(sc.next());
		 System.out.println("Address :");
		 person.setAddress(sc.next());
		 System.out.println("City :");
		 person.setCity(sc.next());
		 System.out.println("State :");
		 person.setState(sc.next());
		 System.out.println("Zip :");
		 person.setZip(sc.next());
		 System.out.println("Phone :");
		 person.setPhone(sc.next());
		 System.out.println("Email :");
		 person.setEmail(sc.next());
		 
		 personList.add(person);		 
	 }
	
	
	public static void main(String[] args) {
		AddressBookRunner runner = new AddressBookRunner();
		runner.displayWelcome();
		
		boolean isExit = false;
		while (!isExit) {
			System.out.println("Enter options\n1.Add\n2.Edit\n3.Delete\n4.Show\n5.Exit");
			int userInput =sc.nextInt();
			switch (userInput) {
			case 1: 
				runner.add();
				break;
			case 2:
				runner.edit();
				break;
			case 3:
				runner.delete();
				break;
			case 4:
				runner.show();
				break;
			case 5 :
				isExit=true;
				break;
			default :
				System.out.println("Invalid input");
			}
		}
	}

}
