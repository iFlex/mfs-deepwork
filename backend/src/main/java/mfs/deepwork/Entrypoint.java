package mfs.deepwork;

import de.dentrassi.crypto.pem.PemKeyStoreProvider;
import mfs.deepwork.auth.jwt.SecurityConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

@SpringBootApplication
public class Entrypoint {

	public static void main(String[] args) throws Throwable {
		Provider pemProvider = new PemKeyStoreProvider();
		Security.addProvider(pemProvider);

		Provider[] providers = Security.getProviders();
		for(Provider provider: providers){
			System.out.println("Provider:"+pemProvider);
		}

		SpringApplication.run(Entrypoint.class, args);
	}

}
