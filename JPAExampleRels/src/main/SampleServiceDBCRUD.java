package main;
import java.io.FileReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import entities.AllData;
import entities.Description;
import entities.Emission;
import entities.User;
import entities.XML;
import dao.EmissionDAO;
import dao.UserDAO;

@Path("/sampleserviceDBCRUD")
public class SampleServiceDBCRUD {

	private static Map<String, User> users = new HashMap<String, User>();
	private UserDAO dao = new UserDAO();
	private EmissionDAO emdao = new EmissionDAO();
	
private static Map<String, Emission> emissions = new HashMap<String, Emission>();
	
	static {
		
		Emission emission1 = new Emission();
        emission1.setCategory("2.A.");
        emission1.setGasUnits("Total GHGs (ktCO2e)");
        emission1.setValue("107.34");
        emissions.put(emission1.getCategory(), emission1);
        
        Emission emission2 = new Emission();
        emission2.setCategory("3.B.");
        emission2.setGasUnits("Total ESD GHGs (ktCO2e)");
        emission2.setValue("1877");
        emissions.put(emission2.getCategory(), emission2);
        
        
    }

	
	@GET
    @Path("/hello")
    @Produces("text/plain")
    public String hello(){
        return "Hello World";    
    }
	
	//Getting All Data 
	@GET
	@Path("/all")
	@Produces("application/xml")
	public List<AllData> getAllEmissions() {
		return emdao.readAllFromFile();
	}
	
	@POST
	@Path("/createdata")
	@Consumes("application/xml")
	public String addData(AllData d){
		emdao.persist(d);
		return "Data added " +d.getCategory() + "\n" + d.getGasUnits();		
	}
	
	@GET
	@Path("/category/{category}")
	@Produces("application/xml")
	public List<AllData> searchByCategory(@PathParam("category")String category){
	    return emdao.searchByCategory(category);    
	}
	
	@DELETE
	@Path("/deletedata/{category}/{gasUnits}")
	@Produces("application/xml")
	public String removeData(
	        @PathParam("category") String category,
	        @PathParam("gasUnits") String gasUnits) {
		return emdao.removeData(category, gasUnits);
	}

	
	//CRUD OPERATIONS FOR USER
	@POST
	@Path("/createuserxml")
	@Consumes("application/xml")
	public String addEmployee(User user){
		dao.persist(user);
		return "User added " +user.getUserName();		
	}

	@POST
	@Path("/createuserjson")
	@Consumes("application/json")
	public String addJSONEmployee(User user){
		return "User added " +user.getUserName();		
	}
	
//	@POST
//	@Path("/login/{username}/{password}")
//	@Produces("text/plain")
//	public String login(
//	        @PathParam("username") String userName,
//	        @PathParam("password") String password) {
//	    UserDAO userDao = new UserDAO();
//	    return userDao.login(userName, password);
//	}
	
	@GET
	@Path("/usersxmlfromdb")
	@Produces("application/xml")
	public List<User> getUsersFromDB(){
		return dao.getAllUsers();
	}
    
	@GET
	@Path("/usersjsonfromdb")
	@Produces("application/json")
	public List<User> getUsersFromDBJSON(){
		return dao.getAllUsers();
	}

	@GET
	@Path("/userfromDBXML/{userName}")
	@Produces("application/xml")
	public User getUserByNameFromDBXML(@PathParam("userName")String userName){
		return dao.getUserByName(userName);	
	}
	
	@PUT
    @Path("/updateUser/")
    @Produces("application/json")
    public User updateUser(User user){
		UserDAO dao = new UserDAO();
		return dao.merge(user);	
    }
	
	@DELETE
    @Path("/deleteUser/{name}")
    @Produces("text/plain")
    public String deleteUser(@PathParam("name")String name){
		UserDAO dao = new UserDAO();
		User us = dao.getUserByName(name);
		dao.removeUser(us);	
		return "User "+us+" deleted";
    }
}
