package com.planet.Entity;

public class Order {
	
	 private String id;
	    private String email;
	    private String phoneNumber;
	    private String parcel_weight;
	    private String country;
		public Order(String values, String email, String phoneNumber, String parcel_weight, String country) {
			super();
			this.id = values;
			this.email = email;
			this.phoneNumber = phoneNumber;
			this.parcel_weight = parcel_weight;
			this.country = country;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getParcel_weight() {
			return parcel_weight;
		}
		public void setParcel_weight(String parcel_weight) {
			this.parcel_weight = parcel_weight;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		@Override
		public String toString() {
			return "Order [id=" + id + ", email=" + email + ", phoneNumber=" + phoneNumber + ", parcel_weight="
					+ parcel_weight + ", country=" + country + "]";
		}

    
}
