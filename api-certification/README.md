# API Certification Service

This service is part of light-portal and it is used to check if the production deployment
meet the security requirements. 

The following things will be checked with server/info endpoint:

- If TLS https is enable and key pair are replaced 
- If http port is disabled
- If JWT token verification and scope verification are enabled
- If metrics is enabled
- If the right version of JDK/JRE is used to start the server
- If the default JWT key is replaced 

