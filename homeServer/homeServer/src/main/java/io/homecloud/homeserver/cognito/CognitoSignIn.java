package io.homecloud.homeserver.cognito;

import org.json.JSONObject;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

public class CognitoSignIn {

	private static final String clientId = "2s5i6tajs75gpgu3b3i4sta6q4";
	private static final String userPoolId = "us-east-1_yCMhOFJ2h";
	private static final String endpoint = "";
	private static final String region = "us-east-1";
	private static final String identityPoolId = "";

	public AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {
		ClasspathPropertiesFileCredentialsProvider propertiesFileCredentialsProvider = 
				new ClasspathPropertiesFileCredentialsProvider();

		return AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(propertiesFileCredentialsProvider)
				.withRegion(region)
				.build();

	}
	
	public static boolean signIn2(String username, String password) {
		
	}
	
	public  SpringSecurityUser signIn(AuthenticationRequestauthenticationRequest){
	    AuthenticationResultType authenticationResult = null;
	    AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();
	 
	    final Map<String, String>authParams = new HashMap<>();
	    authParams.put(USERNAME, authenticationRequest.getUsername());  
	    authParams.put(PASS_WORD, authenticationRequest.getPassword());
	 
	   final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
	       authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
	       .withClientId(cognitoConfig.getClientId())
	       .withUserPoolId(cognitoConfig.getUserPoolId())
	       withAuthParameters(authParams);
	 
	   AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);
	 
	   //Has a Challenge
	   if(StringUtils.isNotBlank(result.getChallengeName())) {
	//If the challenge is required new Password validates if it has the new password variable.
	   if(NEW_PASS_WORD_REQUIRED.equals(result.getChallengeName())){
	      if(null == authenticationRequest.getNewPassword()) {
	throw new CognitoException(messages.get(USER_MUST_PROVIDE_A_NEW_PASS_WORD), CognitoException.USER_MUST_CHANGE_PASS_WORD_EXCEPTION_CODE, result.getChallengeName());
	      }else{
	       //we still need the username
	       final Map<String, String> challengeResponses = new HashMap<>();
	       challengeResponses.put(USERNAME, authenticationRequest.getUsername());
	       challengeResponses.put(PASS_WORD, authenticationRequest.getPassword());
	       //add the new password to the params map
	       challengeResponses.put(NEW_PASS_WORD, authenticationRequest.getNewPassword());
	       //populate the challenge response
	        final AdminRespondToAuthChallengeRequest request = 
	new AdminRespondToAuthChallengeRequest();
	                                           request.withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
	          .withChallengeResponses(challengeResponses)
	          .withClientId(cognitoConfig.getClientId())
	          .withUserPoolId(cognitoConfig.getPoolId())
	          .withSession(result.getSession());
	 
	      AdminRespondToAuthChallengeResult resultChallenge =  
	               cognitoClient.adminRespondToAuthChallenge(request);
	      authenticationResult = resultChallenge.getAuthenticationResult();
	      }
	  }else{
	    //has another challenge
	    throw new CognitoException(result.getChallengeName(),    
	  CognitoException.USER_MUST_DO_ANOTHER_CHALLENGE, result.getChallengeName());
	  }
	   }else{
	       //Doesn't have a challenge
	       authenticationResult = result.getAuthenticationResult();
	   }
	   cognitoClient.shutdown();
	   return userAuthenticated;
	}
	
	public static boolean signIn(String username, String password) {
		CognitoHelper helper = new CognitoHelper();
		String result = helper.ValidateUser(username, password);
		if (result != null) {
            System.out.println("User is authenticated:" + result);
            JSONObject payload = CognitoJWTParser.getPayload(result);
            String provider = payload.get("iss").toString().replace("https://", "");

            Credentials credentails = helper.GetCredentials(provider, result);


            System.out.println(credentails);
            return true;
        } else {
            System.out.println("Username/password is invalid");
        }
		return false;
	}
	
//	public boolean signIn(String username, String password) {
//
//		//I copied AuthenticationHelper as is from the project
//		AuthenticationHelper helper = new AuthenticationHelper();
//
//		//I then retrieve the tokens with SRP authentication
//		AuthenticationResultType result = helper.performSRPAuthentication(username, password);
//
//		//Now I can successfully print the tokens, for example:
//		System.out.println(result.getAccessToken());
//	}
}
