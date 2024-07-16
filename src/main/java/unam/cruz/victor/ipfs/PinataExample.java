package unam.cruz.victor.ipfs;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class PinataExample {
    public static void main(String[] args) throws Exception {
        JSONObject pinataBody = buildJSONObject();
        String ipfsHash = IpfsService.sendJson(pinataBody);

        System.out.println("\tIpfsHash: " + ipfsHash);
    }

    private static @NotNull JSONObject buildJSONObject() {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("name", "John Doe");
        jsonObj.put("age", 50);
        jsonObj.put("has_pets", true);
        return jsonObj;
    }
}
