# OrgId

The program manages organ donations by minting unique tokens for each organ donated by a donor, and publishing these tokens to a blockchain. This allows the program to use all the benefits of blockchain technology like immutability, security and transparency to make organ donation more effective and trustworthy.

## Classes

The code includes some different classes that each handle a unique feature of the system:

- DonorUnifyIdea: This is the main class and serves as the entry point into the application. It initializes the system, and manages donors, donations, and tokens. It also initiates the minting of organ tokens.
- OrganType: An enum that represents the different organ types.
- TokenCredentialAccount: Manages the token and account credentials.
- NonFungibleTokenService: This provides services for creating and managing non-fungible tokens (unique tokens that represent the donated organs).
- IpfsService: This is probably handling interactions with the InterPlanetary File System (IPFS), a decentralized storage system.
- RecipientTopicService: This class is probably administering recipient-related services, such as retrieving the oldest recipient of a certain organ.

## Initial Setup
You would need to have Java SDK installed on your system and an IDE (Eclipse, IntelliJ, etc.) to compile and run this Java project. You'd also need to ensure you have the Hedera Hashgraph SDK and any other dependencies used in the project.
Please adapt this as needed based on your project's specific functionalities and requirements.