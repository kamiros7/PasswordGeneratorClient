# PasswordGeneratorClient

This project works with PasswordGeneratorClient, that simulate a modified OTP lamport algorith, to autheticate users with token.
The client part is to generate the tokens to login in the PasswordGeneratorServer (the projects doesn't communicate, both projects
use the same algorithm to generate the valid tokens).

To use the project, in client_resources.txt, replace the hash strings with password/user desire. Remember that the local password is to
login in the PasswordGeneratorClient and both passwords use SHA-256.

The code can be founded in master branch, I'm too lazy to change it, sorry.
