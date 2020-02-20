:: https://stackoverflow.com/questions/30634658/how-to-create-a-certificate-chain-using-keytool

:: create payment_concentrator_root_ca_main certficate in keystore.jks (validity=7300=20yeras*365)
:: Note here an extension with BasicaContraint (bc) created to show that it's a CA.
keytool -genkeypair -keyalg RSA -keysize 2048 -validity 7300 -alias payment_concentrator_root_ca_main -keystore keystore.jks -dname "CN=payment_concentrator_root_ca_main" -storepass key_store_pass -keypass key_store_pass -ext bc=ca:true -ext SAN=dns:localhost
keytool -keystore keystore.jks -storepass key_store_pass -alias payment_concentrator_root_ca_main -exportcert -rfc > payment_concentrator_root_ca_main.pem


keytool -genkeypair -keyalg RSA -keysize 2048 -validity 365 -alias proxy_server -keystore keystore.jks -dname "CN=proxy_server" -storepass key_store_pass -keypass key_store_pass
keytool -keystore keystore.jks -storepass key_store_pass -certreq -alias proxy_server | keytool -storepass key_store_pass -keystore keystore.jks -gencert -validity 365 -alias payment_concentrator_root_ca_main -ext SAN=dns:localhost -ext ku:c=dig,keyEncipherment -rfc > proxy_server.pem
cat payment_concentrator_root_ca_main.pem proxy_server.pem > proxy_server_chain.pem
keytool -keystore keystore.jks -storepass key_store_pass -importcert -alias proxy_server -file proxy_server_chain.pem

keytool -genkeypair -alias payment_concentrator_trust -keystore truststore.jks -dname "CN=payment_concentrator_trust" -storepass trust_store_pass -keypass trust_store_pass
keytool -delete -keystore truststore.jks -storepass trust_store_pass -alias payment_concentrator_trust
keytool -keystore truststore.jks -storepass trust_store_pass -importcert -file payment_concentrator_root_ca_main.pem -alias payment_concentrator_root_ca_main -noprompt

del "proxy_server.pem"
del "proxy_server_chain.pem"
del "payment_concentrator_root_ca_main.pem"