package it.cnr.istc.stlab.ecodigit.entitylinking.resources;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import it.cnr.istc.stlab.lgu.commons.entitylinking.TagMe;
import it.cnr.istc.stlab.lgu.commons.model.Lang;

@Path("/wikipedia")
public class WikiLinker {

	private static Logger logger = LogManager.getLogger(WikiLinker.class);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/extractEntities")
	public Response getEntities(@QueryParam("lang") String lang, String text,
			@QueryParam("senseInventory") String senseInventory) {
		JSONObject obj = new JSONObject(text);
		Set<String> entities = new HashSet<>();
		logger.trace("Lang " + lang);
		logger.trace("Text: " + text);
		if (senseInventory == null)
			senseInventory = "wiki";
		try {
			if (lang == null || lang.equalsIgnoreCase("it")) {
				entities.addAll(
						TagMe.getMentionedEntitiesURIUsingTagMe(obj.getString("text"), Lang.IT, senseInventory));
			} else {
				entities.addAll(
						TagMe.getMentionedEntitiesURIUsingTagMe(obj.getString("text"), Lang.EN, senseInventory));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		JSONObject r = new JSONObject();
		r.put("entities", entities);

		return Response.ok(r.toString()).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/extractMentions")
	public Response getMentions(@QueryParam("lang") String lang, String text,
			@QueryParam("senseInventory") String senseInventory) {
		JSONObject obj = new JSONObject(text);
		
		logger.trace("Lang " + lang);
		logger.trace("Text: " + text);
		if (senseInventory == null)
			senseInventory = "wiki";
		try {
			if (lang == null || lang.equalsIgnoreCase("it")) {
				return Response.ok(TagMe.getMentions(obj.getString("text"), Lang.IT, senseInventory)).build();
			} else {
				return Response.ok(TagMe.getMentions(obj.getString("text"), Lang.EN, senseInventory)).build();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	

}
