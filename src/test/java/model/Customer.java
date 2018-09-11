package model;

public class Customer {
	private String firstName;
	private String secondName;
	private String email;
	private String address;
	private int postcode;
	private String city;

	public Customer(String firstName, String secondName, String email, String address, int postcode, String city) {
		super();
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.city = city;
	}

	public static Customer generate() {
		return new Customer("Bruce", "Wayne",
				"batEmail" + System.currentTimeMillis() + "@test.adress", "Bat House", 12345, "Gotham");
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public int getPostcode() {
		return postcode;
	}

	public String getCity() {
		return city;
	}

}
