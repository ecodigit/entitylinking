package it.cnr.istc.stlab.ecodigit.entitylinking.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.istc.stlab.lgu.commons.entitylinking.TagMe;
import it.cnr.istc.stlab.lgu.commons.model.Lang;

@Path("/linker")
public class Linker {

	private static Logger logger = LoggerFactory.getLogger(Linker.class);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/extractEntities")
	public Response getEntities(@QueryParam("lang") String lang, String text) {
		JSONObject obj = new JSONObject(text);
		List<String> entities = new ArrayList<>();
		logger.trace("Lang {}", lang);
		logger.trace("Text: {}", text);
		try {
			if (lang == null || lang.equalsIgnoreCase("it")) {
				entities.addAll(TagMe.getMentionedEntitiesURIUsingTagMe(obj.getString("text"), Lang.IT));
			} else {
				entities.addAll(TagMe.getMentionedEntitiesURIUsingTagMe(obj.getString("text"), Lang.EN));
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
	public Response getMentions(@QueryParam("lang") String lang, String text) {
		JSONObject obj = new JSONObject(text);
		logger.trace("Lang {}", lang);
		logger.trace("Text: {}", text);
		try {
			if (lang == null || lang.equalsIgnoreCase("it")) {
				return Response.ok(TagMe.getMentions(obj.getString("text"), Lang.IT)).build();
			} else {
				return Response.ok(TagMe.getMentions(obj.getString("text"), Lang.EN)).build();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
