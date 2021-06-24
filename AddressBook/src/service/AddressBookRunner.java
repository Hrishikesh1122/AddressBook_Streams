package service;

public class AddressBookRunner {

	/**
	 *Displays welcome message 
	 */
	private void displayWelcome() {
		System.out.println("Welcome to address book");
	}
	
	public static void main(String[] args) {
		AddressBookRunner runner = new AddressBookRunner();
		runner.displayWelcome();
	}

}
