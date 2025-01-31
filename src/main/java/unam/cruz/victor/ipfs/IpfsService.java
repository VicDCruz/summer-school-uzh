package unam.cruz.victor.ipfs;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import pinata.Pinata;
import pinata.PinataException;
import pinata.PinataResponse;
import unam.cruz.victor.utils.JsonMapper;

import java.io.IOException;

public class IpfsService {
    public static String sendJson(JSONObject pinataBody) throws PinataException, IOException {
        Pinata pinata = new Pinata(Dotenv.load().get("PINATA_API_KEY"), Dotenv.load().get("PINATA_SECRET_API_KEY"));
        PinataResponse pinataResponse = pinata.pinJsonToIpfs(pinataBody);

        logPinataResponse(pinataResponse);
        IpfsResponse data = JsonMapper.mapJsonToObject(pinataResponse.getBody(), IpfsResponse.class);
        String ipfsHash = data.getIpfsHash();
        System.out.println("https://gateway.pinata.cloud/ipfs/" + ipfsHash + "/");
        return ipfsHash;
    }

    private static void logPinataResponse(PinataResponse pinataResponse) {
        System.out.println("Response from pinning JSON to IPFS: " + pinataResponse.getBody());
    }
}
