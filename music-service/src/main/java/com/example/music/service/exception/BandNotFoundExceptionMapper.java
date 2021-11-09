package com.example.music.service.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BandNotFoundExceptionMapper implements ExceptionMapper<BandNotFoundException> {

	@Override
	public Response toResponse(BandNotFoundException exception) {
		return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE)
				.entity(new Message(exception)).build();
	}

	public static class Message {
		private final Long id;
		private final String name;
		private final String description;

		public Message(BandNotFoundException exception) {
			this.id = 1L;
			this.name = exception.getBandName();
			this.description = exception.getDescription();
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}
	}
}
