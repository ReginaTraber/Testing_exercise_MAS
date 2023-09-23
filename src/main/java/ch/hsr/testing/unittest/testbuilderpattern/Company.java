package ch.hsr.testing.unittest.testbuilderpattern;


public class Company {
    private final AddressHistoryChain addressHistoryChain;

    public Company(AddressHistoryChain addressHistoryChain) {
        this.addressHistoryChain = addressHistoryChain;
    }

    public AddressHistoryChain getShippingAddressHistoryChain() {
        return addressHistoryChain;
    }
}
