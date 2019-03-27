package ml.pra_bhu.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ml.pra_bhu.messenger.database.DatabaseClass;
import ml.pra_bhu.messenger.model.Profile;

public class ProfileService {
private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("Profile_Prashant", new Profile(1L, "Bhushan", "Prashant", "Profile_Prashant"));
		profiles.put("Profile_Roma", new Profile(2L, "Roma", "Kapoor", "Profile_Roma"));
		profiles.put("Profile_Abhish", new Profile(3L, "Abhish", "Bhushan" ,"Profile_Abhish"));
	}
	
	
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if(profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
