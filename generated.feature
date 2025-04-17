FinsireClient is a Java library for interacting with the Finsire API. It provides methods for creating Finsire users, generating OTPs, and validating OTPs. The library also includes methods for retrieving detailed CAS information.

Here's a brief overview of each method in the FinsireClient class:

1. `createFinsireUser`: Creates a new Finsire user with the specified assets and KYC status. Returns a response object containing the Finsire ID.
2. `generateMfcOtp`: Generates an OTP for a given Finsire ID and CAS type. Returns a response object containing the generated OTP.
3. `validateMfcOtp`: Validates an OTP for a given Finsire ID and CAS type. Returns a response object indicating whether the OTP is valid or not.
4. `getMfcDetailedCas`: Retrieves detailed information about a given CAS ID and type. Returns a response object containing the detailed CAS information.
5. `sendPostRequest`: Sends a POST request to the Finsire API with the specified path, request body, and response class. Returns a response object containing the response from the API.
6. `sendGetRequest`: Sends a GET request to the Finsire API with the specified path and response class. Returns a response object containing the response from the API.

Note that these methods are only part of the FinsireClient class, and there may be other methods available for interacting with the Finsire API.