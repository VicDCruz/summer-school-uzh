package unam.cruz.victor.ipfs;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import pinata.Pinata;
import pinata.PinataException;
import pinata.PinataResponse;

import java.io.IOException;

public class PinataExample {
    public static void main(String[] args) throws PinataException, IOException {
        Pinata pinata = new Pinata(Dotenv.load().get("PINATA_API_KEY"), Dotenv.load().get("PINATA_SECRET_API_KEY"));
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("name", "John Doe");
        jsonObj.put("age", 40);
        jsonObj.put("has_pets", true);
        PinataResponse pinataResponse = pinata.pinJsonToIpfs(jsonObj);
        System.out.println(pinataResponse.getBody());
    }
}
