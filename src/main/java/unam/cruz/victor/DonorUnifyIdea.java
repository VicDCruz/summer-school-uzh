package unam.cruz.victor;

import com.hedera.hashgraph.sdk.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import pinata.PinataException;
import unam.cruz.victor.account.AccountBuilder;
import unam.cruz.victor.account.AccountsPreloaded;
import unam.cruz.victor.donation.Donor;
import unam.cruz.victor.donation.OrganType;
import unam.cruz.victor.donation.RecipientTopicService;
import unam.cruz.victor.ipfs.IpfsService;
import unam.cruz.victor.token.NonFungibleTokenService;
import unam.cruz.victor.token.TokenCredentialAccount;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class DonorUnifyIdea {

    private static final int MAX_DONORS = 3;
    private static final Set<String> RECIPIENT_SET = new HashSet<>();
    private static final TokenCredentialAccount treasury;
    private static final TokenCredentialAccount supplier;

    static {
        try {
            System.out.println("CREATING TREASURY TOKEN...");
            treasury = AccountBuilder.createTokenAccount();
            System.out.println("CREATING SUPPLIER TOKEN...");
            supplier = AccountBuilder.createTokenAccount();
        } catch (PrecheckStatusException | TimeoutException | ReceiptStatusException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("******** PROCESSING NEW BATCH OF DONORS ********");

            List<TokenCredentialAccount> donorAccounts = new ArrayList<>(AccountsPreloaded.getDonorAccounts());
            Collections.shuffle(donorAccounts);
            List<Donor> donors = donorAccounts.stream().limit(MAX_DONORS)
                    .map(donorAccount -> new Donor("Donor " + donorAccount.getAccountId(), (int) (Math.random() * 50) + 8, getDonorOrgans()))
                    .toList();
            issueDonorTokens(donors);

            System.out.println("Press any key to repeat or type 'exit' to quit...");
        } while (!scanner.nextLine().equals("exit"));

        scanner.close();
    }

    private static @NotNull List<OrganType> getDonorOrgans() {
        int numOrgans = 1 + (int) (Math.random() * 4);

        System.out.println("Choosing " + numOrgans + " organs...");
        List<OrganType> organs = Arrays.asList(OrganType.values());
        Collections.shuffle(organs);
        List<OrganType> selectedOrgans = new ArrayList<>(organs.subList(0, numOrgans));

        // TODO: remove temp
        if (!selectedOrgans.contains(OrganType.HEART)) selectedOrgans.add(OrganType.HEART);

        return selectedOrgans;
    }

    public static void issueDonorTokens(List<Donor> donors) throws ReceiptStatusException, PrecheckStatusException, TimeoutException, PinataException, IOException {
        int tokenIds = 0;

        for (Donor donor : donors) {
            System.out.println("---------------------------------------------");
            tokenIds += getTokenIds(donor);
        }

        System.out.println(tokenIds + " tokens created");
    }

    private static int getTokenIds(Donor donor) throws ReceiptStatusException, PrecheckStatusException, TimeoutException, PinataException, IOException {
        System.out.println("Checking donor: " + donor);
        int tokenIds = 0;
        for (OrganType organ : donor.organs) {
            AccountId recipient = RecipientTopicService.getOldestRecipient(organ, RECIPIENT_SET);

            if (recipient != null) {
                TokenId token = NonFungibleTokenService.createToken(DonorUnifyIdea.treasury, DonorUnifyIdea.supplier);
                tokenIds++;

                NonFungibleTokenService.mintToken(token, IpfsService.sendJson(buildJSONObject(donor.id, organ)), AccountsPreloaded.getAccountById(recipient), DonorUnifyIdea.supplier);
            }
        }
        return tokenIds;
    }

    private static @NotNull JSONObject buildJSONObject(String donor, OrganType organType) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("owner", donor);
        jsonObj.put("organ", organType);
        return jsonObj;
    }
}
