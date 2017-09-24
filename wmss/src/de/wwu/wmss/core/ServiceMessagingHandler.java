package de.wwu.wmss.core;

import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.wwu.wmss.factory.FactoryWMSS;

public class ServiceMessagingHandler {

	private static Logger logger = Logger.getLogger("ServiceExceptionHandler");
	
	@SuppressWarnings("unchecked")
	public static String getServiceExceptionReport(String errorCode, String errorMessage){
		
		
		JSONObject exceptionReport = new JSONObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		exceptionReport.put("type", "ExceptionReport");
		exceptionReport.put("code", errorCode);
		exceptionReport.put("message", errorMessage);
		
        logger.error(errorCode + ": " + errorMessage);
        
        return gson.toJson(exceptionReport);

	}
	
	@SuppressWarnings("unchecked")
	public static String getServiceDescription(){
		
		JSONObject serviceDescription = new JSONObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
		serviceDescription.put("type", "ServiceDescriptionReport");
		serviceDescription.put("service", SystemSettings.getService());
		serviceDescription.put("port", SystemSettings.getPort());
		serviceDescription.put("version", SystemSettings.getServiceVersion());
		serviceDescription.put("timeout", SystemSettings.getTimeout());
		serviceDescription.put("contact", SystemSettings.getAdmin());
		serviceDescription.put("title", SystemSettings.getTitle());
		serviceDescription.put("startup", SystemSettings.getStartup());
		serviceDescription.put("supportedProtocols", SystemSettings.getVersion());
		
		JSONObject environment = new JSONObject();
		
		
		environment.put("java", System.getProperty("java.version"));
		environment.put("os", System.getProperty("os.name").toString() + " " + 
				   System.getProperty("os.version").toString() + " (" + 
				   System.getProperty("os.arch").toString()+")");		
		serviceDescription.put("environment", environment);
				
		JSONArray dsArray = new JSONArray();
		
		for (int i = 0; i < SystemSettings.sourceList.size(); i++) {
			
			JSONObject ds = new JSONObject();
			
			ds.put("id", SystemSettings.sourceList.get(i).getId());
			ds.put("active", SystemSettings.sourceList.get(i).isActive());
			ds.put("type", SystemSettings.sourceList.get(i).getType());
			ds.put("storage", SystemSettings.sourceList.get(i).getStorage());
			ds.put("port", SystemSettings.sourceList.get(i).getPort());
			ds.put("host", SystemSettings.sourceList.get(i).getHost());
			ds.put("repository", SystemSettings.sourceList.get(i).getRepository());
			ds.put("version", SystemSettings.sourceList.get(i).getVersion());
			ds.put("user", SystemSettings.sourceList.get(i).getUser());
			//ds.put("password", SystemSettings.sourcesList.get(i).getPassword());
			
			dsArray.add(ds);
		}
		
		
		serviceDescription.put("datasources", dsArray);
		
		return gson.toJson(serviceDescription);
		
	}
	
	public static String getScoreList(Enumeration<String> parameterList){
		
		ArrayList<MusicScore> listScores = new ArrayList<MusicScore>();
		
		ScoreList scoreList = new ScoreList();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JSONObject scr = new JSONObject();
		
		try {
			
			//listScores = FactoryWMSS.getScoreList(parameterList);			
			scoreList = FactoryWMSS.getScoreList(parameterList);
			scr.put("repositories", scoreList);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		
		//TODO gerar JSON
		return gson.toJson(scr);
		
	}
	
	public static String getScore(Enumeration<String> parameterList){
		
		return null;
		
	}
	
	
}
