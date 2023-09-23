package ch.hsr.testing.unittest.mocking;


import ch.hsr.testing.unittest.testbuilderpattern.Address;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

public class AddressRetriever {
    private final HttpService httpService = new HttpService();
    private final String apiKey;

    public AddressRetriever(String apiKey) {
        this.apiKey = apiKey;
    }

    public Address retrieve(double latitude, double longitude) throws AddressRetrieverException {
        String params = new Formatter(Locale.US).format("location=%.6f%s%.6f", latitude, "%2C",longitude).toString();
        String url = "https://www.mapquestapi.com/geocoding/v1/reverse?key=" + apiKey
                + "&outFormat=json&thumbMaps=false&format=json&" + params;
        try {
            String response = httpService.get(url);

            JSONObject obj = (JSONObject) new JSONParser().parse(response);
            JSONObject location =  (JSONObject) ((JSONArray)((JSONObject)((JSONArray) obj.get("results")).get(0)).get("locations")).get(0);
            return Address.fromJSONLocation(location);

        } catch (IOException e) {
            throw new AddressRetrieverException("Received Exception from HttpService", e);
        } catch (Exception e) {
            throw new AddressRetrieverException("Could not parse JSON from HttpService", e);
        }

    }
}
