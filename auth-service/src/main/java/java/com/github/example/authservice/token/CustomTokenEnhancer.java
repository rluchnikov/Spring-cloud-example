package java.com.github.example.authservice.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import java.com.github.example.authservice.client.CatalogServiceClient;
import java.com.github.example.authservice.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogServiceClient catalogServiceClient;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        java.com.github.example.authservice.entity.User existing = userRepository.findByUsername(user.getUsername());
        final Map<String, Object> additionalInfo = new HashMap<>();
        user.getAuthorities().stream().findFirst().ifPresent(x->additionalInfo.put("role",x.getAuthority()));
        if (existing.getFirstName() !=null)
         additionalInfo.put("username",existing.getFirstName()+" "+ existing.getLastName());
        if (existing.getLocation() !=null)
         additionalInfo.put("location",existing.getLocation());
        ((DefaultOAuth2AccessToken) accessToken)
                .setAdditionalInformation(additionalInfo);

        return accessToken;
    }

}

