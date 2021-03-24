# Keycloak NodeJS Adapter demo

## Setup

```sh
git clone https://github.com/nmasse-itix/keycloak-quarkus-demo.git
cd keycloak-quarkus-demo
```

## Demo scenario

Install Red Hat SSO.

Create a Realm named Red Hat.

Start the Petstore Server.

```sh
./mvnw compile quarkus:dev
```

Show some REST requests.

```sh
http http://localhost:8080/pets/ 
http http://localhost:8080/pets/1
```

Create a client named "quarkus-app", **Bearer Only**.

Edit **resources/application.properties** and fill-in the blanks.

Edit **pom.xml** and uncomment **quarkus-smallrye-jwt**.

Edit **src/main/java/fr/itix/petstore/PetstoreResource.java** and uncomment all lines.

Show that the Petstore server now requires authentication.

```sh
http http://localhost:8080/pets/ 
```

Create a **confidential** client named "rest-client", with only the **Direct Access Grants** flow enabled.

Create a user **john** with password **secret**.

Request a token for john.

```sh
curl https://$SSO_HOSTNAME/auth/realms/redhat/protocol/openid-connect/token -XPOST -d client_id=rest-client -d client_secret=$CLIENT_SECRET -d grant_type=password -d username=john -d password=secret 
```

Save it for later.

```sh
TOKEN=$(curl https://$SSO_HOSTNAME/auth/realms/redhat/protocol/openid-connect/token -XPOST -d client_id=rest-client -d client_secret=$CLIENT_SECRET -d grant_type=password -d username=john -d password=secret -s |jq -r .access_token)
```

Show that now, calls are rejected.

```sh
http http://localhost:8080/pets/ "Authorization:Bearer $TOKEN"
```

Give the **read** role to john, get a new token and show that you can query the Read REST endpoints.

```sh
http http://localhost:8080/pets/ "Authorization:Bearer $TOKEN"
```

Write calls a forbidden.

```sh
http DELETE http://localhost:8080/pets/1 "Authorization:Bearer $TOKEN"
```

Give the **write** role to john, get a new token and show that you can query the Write REST endpoints.
