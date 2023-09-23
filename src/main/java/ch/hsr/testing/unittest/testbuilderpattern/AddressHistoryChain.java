package ch.hsr.testing.unittest.testbuilderpattern;

import java.util.ArrayList;
import java.util.List;

public class AddressHistoryChain {

    private final List<Address> addressList;

    public AddressHistoryChain() {
        this.addressList = new ArrayList<>();
    }

    public AddressHistoryChain(Address address) {
        this();
        this.addAddress(address);
    }

    public void addAddress(Address address){
        this.addressList.add(address);
    }

    public List<Address> getAddressList() {
        return addressList;
    }
}
