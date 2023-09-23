package ch.hsr.testing.unittest.testbuilderpattern;

import java.util.Optional;

public class CustomerForShipping {

    private final AddressHistoryChain defaultAddress;
    private Optional<AddressHistoryChain> shippingAddress = Optional.empty();
    private Optional<Company> associatedCompany = Optional.empty();

    private boolean internalCustomer;


    public CustomerForShipping(AddressHistoryChain defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public AddressHistoryChain getDefaultAddress() {
        return defaultAddress;
    }

    public Optional<AddressHistoryChain> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressHistoryChain shippingAddress) {
        this.shippingAddress = Optional.of(shippingAddress);
    }

    public boolean isInternalCustomer() {
        return internalCustomer;
    }

    public void setInternalCustomer(boolean internalCustomer) {
        this.internalCustomer = internalCustomer;
    }

    public Optional<Company> getAssociatedCompany() {
        return associatedCompany;
    }

    public void setAssociatedCompany(Company associatedCompany) {
        this.associatedCompany = Optional.of(associatedCompany);
    }
}
