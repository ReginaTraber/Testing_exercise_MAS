package ch.hsr.testing.unittest.testbuilderpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static ch.hsr.testing.unittest.testbuilderpattern.ShippingAddressSelector.INTERNAL_ADDRESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class ShippingAddressSelectorTest {


    public static final String DEFAULT_CITY = "DefaultCity";
    public static final String SHIPPING_CITY = "ShippingCity";
    public static final String COMPANY_CITY = "CompanyCity";
    public static final String UNAPPROVED_DEFAULT_CITY = "UnapprovedDefaultCity";
    public static final String APPROVED_DEFAULT_CITY = "ApprovedDefaultCity";
    public static final String NEW_APPROVED_DEFAULT_CITY = "NewApprovedDefaultCity";


    @Test
    public void internalAddressShouldBeChosenForInternalCustomer() {
        test()
                .setInternalCustomer()
                .run()
                .assertInternalAddressIsSelected();
    }

    @Test
    public void companyAddressIsChosenIfCustomerIsAssociatedWithCompany() {
        test()
                .setAssociatedCompany()
                .run()
                .assertAddressIsFound()
                .assertCompanyAddressIsSelected();
    }

    @Test
    public void defaultAddressIsChosenIfNoShippingAddressExists() {
        test()
                .run()
                .assertAddressIsFound()
                .assertDefaultAddressIsSelected();
    }

    @Test
    public void shippingAddressIsChosenIfExists() {
        // TODO: Implement this
        Assertions.fail("Implement Testcase");
    }


    @Test
    public void unapprovedAddressIsTakenIfNotRestrictedToApprovedAddresses() throws Exception {
        // TODO: Implement this
        Assertions.fail("Implement Testcase");
    }

    @Test
    public void noAddressIsSelectedIfRestrictedToApprovedAddressesButNoneExists() throws Exception {
        // TODO: Implement this
        Assertions.fail("Implement Testcase");
    }

    @Test
    public void newestAddressVersionIsTakenIfMultipleVersionsExist() throws Exception {
        // TODO: Implement this
        Assertions.fail("Implement Testcase");
    }

    @Test
    public void onlyApprovedAddressIsTakenIfRestrictedToApprovedAddresses() throws Exception {
        test()
                .addApprovedDefaultAddress()
                .addUnapprovedDefaultAddress()
                .useOnlyApprovedAddresses()
                .run()
                .assertApprovedDefaultAddressIsTaken();
    }


    @Test
    public void onlyApprovedAddressIsTakenIfRestrictedToApprovedAddresses_BadExample() throws Exception {
        ShippingAddressSelector shippingAddressSelector = new ShippingAddressSelector();

        AddressHistoryChain addressHistoryChain = new AddressHistoryChain();
        Address approvedAddress = new Address("13", "ApprovedRoad", "ApprovedCity", "ApprovedState", "4242");
        approvedAddress.setApproved(true);
        addressHistoryChain.addAddress(approvedAddress);

        Address unapprovedAddress = new Address("13", "UnapprovedRoad", "UnapprovedCity", "UnapprovedState", "4242");
        unapprovedAddress.setApproved(false);
        addressHistoryChain.addAddress(unapprovedAddress);

        CustomerForShipping customer = new CustomerForShipping(addressHistoryChain);
        customer.setInternalCustomer(false);

        Optional<Address> selectedShippingAddress = shippingAddressSelector.selectShippingAddress(customer, true);
        org.junit.Assert.assertThat(selectedShippingAddress.isPresent(), is(true));
        org.junit.Assert.assertThat(selectedShippingAddress.get().getCity(), is("ApprovedCity"));

    }


    private TestBuilder test() {
        return new TestBuilder();
    }

    private static class TestBuilder {

        private final ShippingAddressSelector shippingAddressSelector;
        private final CustomerForShipping customer;
        private boolean hasToBeApproved;

        private TestBuilder() {
            shippingAddressSelector = new ShippingAddressSelector();
            AddressHistoryChain defaultAddressChain;
            defaultAddressChain = new AddressHistoryChain(new AddressBuilder().city(DEFAULT_CITY).build());
            customer = new CustomerForShipping(defaultAddressChain);
            hasToBeApproved = false;
        }

        private TestBuilder setInternalCustomer() {
            customer.setInternalCustomer(true);
            return this;
        }

        private TestBuilder setShippingAddress() {
            customer.setShippingAddress(new AddressHistoryChain(new AddressBuilder().city(SHIPPING_CITY).build()));
            return this;
        }

        private TestResult run() {
            Optional<Address> selectedAddress = shippingAddressSelector.selectShippingAddress(customer, hasToBeApproved);

            return new TestResult(selectedAddress);
        }


        private TestBuilder setAssociatedCompany() {
            Company company = new Company(new AddressHistoryChain(new AddressBuilder().city(COMPANY_CITY).build()));
            customer.setAssociatedCompany(company);
            return this;
        }

        private TestBuilder addUnapprovedDefaultAddress() {
            customer.getDefaultAddress().addAddress(new AddressBuilder()
                    .city(UNAPPROVED_DEFAULT_CITY)
                    .approved(false)
                    .build());
            return this;
        }

        private TestBuilder addApprovedDefaultAddress() {
            customer.getDefaultAddress().addAddress(new AddressBuilder()
                    .city(APPROVED_DEFAULT_CITY)
                    .approved(true)
                    .build());
            return this;
        }

        private TestBuilder useOnlyApprovedAddresses() {
            hasToBeApproved = true;
            return this;
        }
    }

    private static class TestResult {

        private final Optional<Address> selectedAddress;

        private TestResult(Optional<Address> selectedAddress) {
            this.selectedAddress = selectedAddress;
        }

        private TestResult assertAddressIsFound() {
            assertThat(selectedAddress.isPresent(), is(true));
            return this;
        }

        private TestResult assertInternalAddressIsSelected() {
            assertAddressIsFound();
            assertThat(selectedAddress.get(), is(INTERNAL_ADDRESS));
            return this;
        }

        private TestResult assertDefaultAddressIsSelected() {
            assertAddressIsFound();
            assertThat(selectedAddress.get().getCity(), is(DEFAULT_CITY));
            return this;
        }

        private TestResult assertCompanyAddressIsSelected() {
            assertAddressIsFound();
            assertThat(selectedAddress.get().getCity(), is(COMPANY_CITY));
            return this;
        }

        private TestResult assertApprovedDefaultAddressIsTaken() {
            assertThat(selectedAddress.get().getCity(), is(APPROVED_DEFAULT_CITY));
            return this;
        }

    }

    private static class AddressBuilder {
        private String road = "SomeAvenue";
        private String city = "AnyCity";
        private String state = "NowhereState";
        private String zip = "4242";
        private String houseNumber = "42";
        private boolean approved = true;


        private AddressBuilder road(String road) {
            this.road = road;
            return this;
        }

        private AddressBuilder city(String city) {
            this.city = city;
            return this;
        }

        private AddressBuilder state(String state) {
            this.state = state;
            return this;
        }

        private AddressBuilder zip(String zip) {
            this.zip = zip;
            return this;
        }

        private AddressBuilder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        private AddressBuilder approved(boolean approved) {
            this.approved = approved;
            return this;
        }

        private Address build() {
            Address address = new Address(houseNumber, road, city, state, zip);
            address.setApproved(approved);
            return address;
        }


    }

}