/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
 ***/
package ch.hsr.testing.unittest.testbuilderpattern;

import org.json.simple.JSONObject;

import java.util.Objects;

public class Address {
    private final String houseNumber;
    private final String road;
    private final String city;
    private final String state;
    private final String zip;

    private boolean approved;

    public Address(String houseNumber, String road, String city, String state, String zip) {
        this.houseNumber = houseNumber;
        this.road = road;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getRoad() {
        return road;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public boolean isApproved() {
        return approved;
    }
    
    public static Address fromJSONLocation(JSONObject location) {
        String houseNumberAndstreet = (String) location.get("street");
        String houseNumber = houseNumberAndstreet.substring(0, houseNumberAndstreet.indexOf(" "));
        String street = houseNumberAndstreet.substring(houseNumberAndstreet.indexOf(" ")).trim();
        String city = (String) location.get("adminArea5");
        String state = (String) location.get("adminArea1");
        String zip = (String) location.get("postalCode");
        return new Address(houseNumber, street, city, state, zip);
    }

    @Override
    public String toString() {
        return "Address{" +
                "houseNumber='" + houseNumber + '\'' +
                ", road='" + road + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", approved=" + approved +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return approved == address.approved &&
                Objects.equals(houseNumber, address.houseNumber) &&
                Objects.equals(road, address.road) &&
                Objects.equals(city, address.city) &&
                Objects.equals(state, address.state) &&
                Objects.equals(zip, address.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseNumber, road, city, state, zip, approved);
    }
}
