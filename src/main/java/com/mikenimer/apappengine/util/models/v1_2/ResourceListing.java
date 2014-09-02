package com.mikenimer.apappengine.util.models.v1_2;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @see https://github.com/wordnik/swagger-spec/blob/master/versions/1.2.md#51-resource-listing
 * Created by mnimer on 8/26/14.
 */
public class ResourceListing
{

    //swagger spec
    /**
     * Provides the version of the application API (not to be confused by the specification version).
     */
    @Parameter(required = false)
    private String apiVersion = "v1.0.0";

    //swagger spec
    /**
     * Required. Specifies the Swagger Specification version being used. It can be used by the Swagger UI and other clients to interpret the API listing. The value MUST be an existing Swagger specification version.
     * Currently, "1.2" are valid values. The field is a string type for possible non-numeric versions in the future (for example, "1.2a").
     */
    private String swaggerVersion = "1.2";

    //swagger spec
    /**
     * Required. Lists the resources to be described by this specification implementation. The array can have 0 or more elements.
     */
    @SerializedName("apis")
    @Parameter
    private List<Resource> services = new ArrayList<>();

    /**
     * Provides information about the authorization schemes allowed on this API.
     */
    private List<Object> authorizations = new ArrayList<>();

    //swagger spec
    /**
     * Provides metadata about the API. The metadata can be used by the clients if needed, and can be presented in the Swagger-UI for convenience.
     */
    @Parameter
    private Info info;


    /**
     * Relative path under the web output directory.
     */
    private transient String outputWebDir = "/api-docs";


    public String getApiVersion()
    {
        return apiVersion;
    }


    public void setApiVersion(String apiVersion)
    {
        this.apiVersion = apiVersion;
    }


    public String getSwaggerVersion()
    {
        return swaggerVersion;
    }


    public void setSwaggerVersion(String swaggerVersion)
    {
        this.swaggerVersion = swaggerVersion;
    }


    public List<Resource> getServices()
    {
        return services;
    }


    public void setServices(List<Resource> services)
    {
        this.services = services;
    }


    public Info getInfo()
    {
        return info;
    }


    public void setInfo(Info info)
    {
        this.info = info;
    }


    public List<Object> getAuthorizations()
    {
        return authorizations;
    }


    public void setAuthorizations(List<Object> authorizations)
    {
        this.authorizations = authorizations;
    }


    public String getOutputWebDir()
    {
        return outputWebDir;
    }


    public void setOutputWebDir(String outputWebDir)
    {
        this.outputWebDir = outputWebDir;
    }


    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
