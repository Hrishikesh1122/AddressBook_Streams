/*******************************************************
 * Purpose : Do address book operations
 * @author Hrishikesh Ugavekar
 * @Version 1.1
 * @since 28-06-2021
 *
 ******************************************************/
package service;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import model.Person;

public class AddressBookRunner {
	
	 private static Scanner sc = new Scanner(System.in);
	 private ArrayList<Person> personList1 =null;
	 private Map<String,ArrayList<Person>> Books= new HashMap<>();
	 public String city;

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
	
		 System.out.println("To which city you want to add ?");
		 city =sc.next();
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
		 
		 
		 if(Books.containsKey(city)) {
			 Books.get(city).add(person); 
		 }
		 else {
			 personList1 = new ArrayList<>();
			 personList1.add(person);
			 Books.put(city, personList1);
		 }
		 
	 }
	
	/**
	 * UC3
	 * This method finds person according to first name and change its field according to user input
	 */
	private void edit() {
		System.out.println("Enter the city to which u want to edit person ");
		city=sc.next();
		String enteredName;
		System.out.println("Enter First name of contact to edit it ");
		enteredName=sc.next();
		for(int i=0;i<Books.get(city).size();i++)
		{
			if(Books.get(city).get(i).getFirstName().equals(enteredName))
			{
				int check=0;
				System.out.println("Person found , what do you want to edit ?");
				System.out.println("Enter\n1.First Name\n2.Last Name\n3.Address\n4.city\n5.State\n6.Zip\n7.Phone\n8.Email");
				check=sc.nextInt();
				switch(check) {
				case 1:
					System.out.println("Enter new first name");
					Books.get(city).get(i).setFirstName(sc.next());
					break;
				case 2:
					System.out.println("Enter new last name");
					Books.get(city).get(i).setLastName(sc.next());
					break;
				case 3:
					System.out.println("Enter new Address");
					Books.get(city).get(i).setAddress(sc.next());
					break;
				case 4:
					System.out.println("Enter new city");
					Books.get(city).get(i).setCity(sc.next());
					break;
				case 5:
					System.out.println("Enter new state");
					Books.get(city).get(i).setState(sc.next());
					break;
				case 6:
					System.out.println("Enter new zip");
					Books.get(city).get(i).setZip(sc.next());
					break;
				case 7:
					System.out.println("Enter new phone number");
					Books.get(city).get(i).setPhone(sc.next());
					break;
				case 8:
					System.out.println("Enter new email");
					Books.get(city).get(i).setEmail(sc.next());
					break;
				default :
					System.out.println("Invalid Entry");
						
				}
			}
		}
		
		
	}
	

	/**
	 * UC4
	 * This method removes person from list according to first name.
	 */
	private void delete() {
		System.out.println("Enter First name of contact to delete it ");
		String enteredName=sc.next();
		for(int i=0;i<Books.get(city).size();i++)
		{
			if(Books.get(city).get(i).getFirstName().equals(enteredName))
				Books.get(city).remove(i);
		}
		System.out.println("Person removed from Address book");
	}
	
	/**
	 * Displays books on console
	 */
	private void show() {
		System.out.println(Books);
	}
	
	/**
	 * UC8 : Ability to search person across all cities
	 * Here Name of cities are stored as key of Hashmap
	 * For loop iterates over all keys
	 * Use of stream to search target person 
	 */
	private void search() {
		System.out.println("Enter name to search across all books");
		String searchKey = sc.next();
		for(String key : Books.keySet()) {
				Books.get(key).stream().filter(personList1->personList1.getFirstName().equals(searchKey)).
				collect(Collectors.toList()).forEach(Person -> System.out.println(Person.toString()));	
		}
	}
	
	/**
	 * UC9 : Ability to view persons by city
	 * Gets city name from user 
	 * If Hashmap contains that city as key it will display all contacts in list
	 * using stream.
	 * Else displays message "City not found".
	 * Use of Atomic integer is to count number of contacts in a city within lambda expression.
	 */
	private void searchBycity() {
		System.out.println("Enter city name to display all contacts");
		String searchKey = sc.next();
		AtomicInteger count = new AtomicInteger(0);
		if(Books.containsKey(searchKey)) {
			Books.get(searchKey).stream().forEach(Person -> {System.out.println(Person.toString());count.incrementAndGet();});
			System.out.println("Number of contacts : "+count.get());
		}
		else {
			System.out.println("City not found");
		}	
	}
	
	/**
	 * UC11 Sort by First name in Alphabetical order
	 */
	private void sortByFirstName() {
		for(String key : Books.keySet()) {
			Books.get(key).stream().sorted(Comparator.comparing(Person::getFirstName)).collect(Collectors.toList())
			.forEach(Person -> System.out.println(Person.toString()));
		}
	}
	
	/**
	 * UC12 Sort by city name
	 */
	private void sortByCity() {
		for(String key : Books.keySet()) {
			Books.get(key).stream().sorted(Comparator.comparing(Person::getCity)).collect(Collectors.toList())
			.forEach(Person -> System.out.println(Person.toString()));
		}
	}
	
	/**
	 * Purpose : Writes contact book to file contactbook.txt
	 * @throws IOException 
	 */
	public void writeToFile() throws IOException {
		FileOutputStream writeData = new FileOutputStream("contactbook.txt");
		ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
		try {
		
			writeStream.writeObject(Books);
			writeStream.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			writeStream.close();
			
		}
		
	}
	

	/**
	 * Reads objects from file
	 * Prints them on console
	 * @throws IOException 
	 */
	public void readFromFile() throws IOException {
		FileInputStream readData = new FileInputStream("contactbook.txt");
		ObjectInputStream readStream = new ObjectInputStream(readData);
		try {
			Map<String,ArrayList<Person>> newBooks = (Map<String, ArrayList<Person>>) readStream.readObject();
			System.out.println(newBooks);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			readStream.close();
		}
	}
	
	/**
	 * UC 14
	 * Ability to Read the Address Book with Persons Contact
     * as CSV File
	 */
	public void readCsv() {
		try (CSVReader reader = new CSVReader(new FileReader("contacts.csv"));)
		{
			String[] nextLine;
			while((nextLine = reader.readNext()) != null) {
				if(nextLine != null) {
					System.out.println(Arrays.toString(nextLine));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Read complete");
	}
	
	/**
	 * UC 14
	 * Ability to write the Address Book with Persons Contact
     * as CSV File
	 */
	public void writeToCsv() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		try(FileWriter writer = new FileWriter("contacts.csv"))
		{
			ColumnPositionMappingStrategy mappingStrategy=
                    new ColumnPositionMappingStrategy();
			
			mappingStrategy.setType(Person.class);
			String[] columns = new String[] {"FirstName","LastName","Address",
					"city","state","zip","phone","email"};
			mappingStrategy.setColumnMapping(columns);
			
			StatefulBeanToCsvBuilder<Person> builder = 
					new StatefulBeanToCsvBuilder(writer); 
			
			StatefulBeanToCsv beanWriter = 
			          builder.withMappingStrategy(mappingStrategy).build();
			
			beanWriter.write(personList1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		AddressBookRunner runner = new AddressBookRunner();
		runner.displayWelcome();
		
		boolean isExit = false;
		while (!isExit) {
			System.out.println("Enter options\n1.Add\n2.Edit\n3.Delete\n4.Show\n5.Search\n6.ShowCity\n7.SortByName\n"
					+ "8.SortByCity\n9.WriteToFile\n10.ReadFromFile\n11.ReadCSV\n12.WriteToCsv\n13.Exit");
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
				runner.search();
				break;
			case 6 :
				runner.searchBycity();
				break;
			case 7:
				runner.sortByFirstName();
				break;
			case 8:
				runner.sortByCity();
				break;
			case 9 :
				runner.writeToFile();
				break;
			case 10 :
				runner.readFromFile();
				break;
			case 11:
				runner.readCsv();
				break;
			case 12:
				runner.writeToCsv();
				break;
			case 13:
				isExit = true;
				break;
			default :
				System.out.println("Invalid input");
			}
		}
	}

	
}
