package ch.hsr.testing.unittest.mocking;

import ch.hsr.testing.unittest.testbuilderpattern.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressRetrieverTestBadExample {

    private AddressRetriever addressRetriever;

    @Test
    public void testUsingRealOnlineHttpService() throws AddressRetrieverException {
        addressRetriever = new AddressRetriever("nDYMo1V9YAueoI3wD9BTnRPQ1CYOluSa");
        Address address = addressRetriever.retrieve(47.49996,8.72351);

        Assertions.assertThat(address.getCity()).isEqualTo("Winterthur");
    }

}
