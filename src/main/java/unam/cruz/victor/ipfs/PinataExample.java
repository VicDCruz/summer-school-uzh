package unam.cruz.victor.ipfs;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import pinata.Pinata;
import pinata.PinataException;
import pinata.PinataResponse;

import java.io.IOException;

public class PinataExample {
    public static void main(String[] args) throws Exception {
        JSONObject pinataBody = buildJSONObject();
        String pinataResponse = sendBody(pinataBody);

        IpfsResponse data = JsonMapper.mapJsonToObject(pinataResponse, IpfsResponse.class);
        System.out.println("\tIpfsHash: " + data.getIpfsHash());
        System.out.println("\tPinSize: " + data.getPinSize());
        System.out.println("\tTimestamp: " + data.getTimestamp());
    }

    private static @NotNull JSONObject buildJSONObject() {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("name", "John Doe");
        jsonObj.put("age", 40);
        jsonObj.put("has_pets", true);
        return jsonObj;
    }

    public static String sendBody(JSONObject pinataBody) throws PinataException, IOException {
        Pinata pinata = new Pinata(Dotenv.load().get("PINATA_API_KEY"), Dotenv.load().get("PINATA_SECRET_API_KEY"));
        PinataResponse pinataResponse = pinata.pinJsonToIpfs(pinataBody);

        logPinataResponse(pinataResponse);
        return pinataResponse.getBody();
    }

    private static void logPinataResponse(PinataResponse pinataResponse) {
        System.out.println("Response from pinning JSON to IPFS: " + pinataResponse.getBody());
    }
}
