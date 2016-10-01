package com.donal;


import com.donal.model.Result;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("resolutions")
public class WebResource {


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getResolutions(@QueryParam("file_name") String fileName) {
        System.out.println("Get File Resolutions (web service)...");

        FileProcessor fileProcessor = new FileProcessor();

        try {
            Result result = fileProcessor.processFile(fileName, FileProcessor.TYPE_URL, false);

            System.out.println("File processing for " + fileName + " completed successfully");

            Response.ResponseBuilder builder = Response.ok(result);

            return builder.build();
        } catch (Exception e) {
            System.err.println("Error " + e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing file " + fileName).build();
        }
    }

}

