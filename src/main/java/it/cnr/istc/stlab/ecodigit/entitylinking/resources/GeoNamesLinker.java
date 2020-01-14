package it.cnr.istc.stlab.ecodigit.entitylinking.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import it.cnr.istc.stlab.lgu.commons.entitylinking.model.Result;
import it.cnr.istc.stlab.lgu.commons.nlp.entitylinking.Geonames;

@Path("/geonames")
public class GeoNamesLinker {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/extractEntities")
	public Response getGeoNamesEntities(@QueryParam("lang") String lang, String text) {
		JSONObject obj = new JSONObject(text);
		Result r;
		if (lang == null || lang.equalsIgnoreCase("it")) {
			r = Geonames.getMentionedGeonames(obj.getString("text"), it.cnr.istc.stlab.lgu.commons.nlp.Lang.IT);
		} else {
			r = Geonames.getMentionedGeonames(obj.getString("text"), it.cnr.istc.stlab.lgu.commons.nlp.Lang.EN);
		}

		JSONObject result = new JSONObject();
		result.put("text", obj.getString("text"));
		result.put("entities", r.getMentionedEntities());

		return Response.ok(result.toString()).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/extractMentions")
	public Response getGeoNames(@QueryParam("lang") String lang, String text) {
		JSONObject obj = new JSONObject(text);
		if (lang == null || lang.equalsIgnoreCase("it")) {
			return Response
					.ok(Geonames.getMentionedGeonames(obj.getString("text"), it.cnr.istc.stlab.lgu.commons.nlp.Lang.IT))
					.build();
		} else {
			return Response
					.ok(Geonames.getMentionedGeonames(obj.getString("text"), it.cnr.istc.stlab.lgu.commons.nlp.Lang.EN))
					.build();
		}
	}

}
