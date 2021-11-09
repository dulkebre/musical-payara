package com.example.music.service;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import com.example.music.service.exception.BandNotFoundException;
import com.example.music.service.faker.FakerService;
import com.example.music.service.model.Band;

/**
 *
 */
@Path("/bands")
@ApplicationScoped
public class BandsController {

	@Inject
	private FakerService fakerService;

	// TODO: Should return a list of bands
	@GET
	@Operation(summary = "Get best bands ever!", description = "Returns names of 100 best bands of all times!")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @APIResponse(responseCode = "200", description = "The bands")
	@Tag(name = "BETA", description = "This API is currently in beta state")
	@APIResponse(description = "The items",
            responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = String.class,
                            readOnly = true, description = "the items",
                            required = true, name = "items")))
	public String listBands() {
		return fakerService.getNamesOfBestBandEver().toString();
	}

	@GET
	@Path("{bandName}/albums")
	@Operation(summary = "Find albums by band name", description = "Find albums by band name")
    @APIResponse(responseCode = "200", description = "The albums")
    @APIResponse(responseCode = "404", description = "When the band does not exist")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Tag(name = "BETA", description = "This API is currently in beta state")
    @APIResponse(description = "The albums",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = String.class)))
	public String listBandAlbums(@Parameter(description = "The item ID", required = true, example = "water",
            schema = @Schema(type = SchemaType.STRING)) @PathParam("bandName") String bandName) {
		validateRequest(bandName);

		Set<String> albumNames = new HashSet<>();
		for (Band band : fakerService.getBestBandsEver()) {
			if (bandName.equals(band.getBandName())) {
				albumNames.addAll(band.getAlbums());
			}
		}

		return albumNames.toString();
	}

	private void validateRequest(String bandName) {
		if (bandName == null) {
			throw new BandNotFoundException("", "Missing band name parameter value");
		}
		if (!fakerService.getNamesOfBestBandEver().contains(bandName)) {
			throw new BandNotFoundException(bandName, "Unknown band");
		}
	}

}
