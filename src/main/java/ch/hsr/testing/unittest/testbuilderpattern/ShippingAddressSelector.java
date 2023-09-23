package ch.hsr.testing.unittest.testbuilderpattern;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ShippingAddressSelector {

    public static final Address INTERNAL_ADDRESS = new Address("internal", "internal", "internal", "internal", "internal");

    public Optional<Address> selectShippingAddress(CustomerForShipping customer, boolean hasToBeApproved) {
        if (customer.isInternalCustomer()) {
            return Optional.of(INTERNAL_ADDRESS);
        } else {
            if (customer.getAssociatedCompany().isPresent()) {
                return getNewest(customer
                                .getAssociatedCompany().get()
                                .getShippingAddressHistoryChain()
                                .getAddressList(),
                        hasToBeApproved);
            }
            if (customer.getShippingAddress().isPresent()) {
                return getNewest(customer.getShippingAddress().get().getAddressList(), hasToBeApproved);
            } else {
                return getNewest(customer.getDefaultAddress().getAddressList(), hasToBeApproved);

            }
        }
    }


    private Optional<Address> getNewest(List<Address> addressList, boolean hasToBeApproved) {
        Predicate<Address> approvalFilter;
        if (hasToBeApproved) {
            approvalFilter = Address::isApproved;
        } else {
            approvalFilter = (a -> true);
        }
        Collections.reverse(addressList);
        return addressList.stream().filter(approvalFilter).findFirst();
    }

}
