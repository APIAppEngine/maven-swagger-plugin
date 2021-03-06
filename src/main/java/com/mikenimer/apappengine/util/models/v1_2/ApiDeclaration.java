package com.mikenimer.apappengine.util.models.v1_2;


import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Authorization;

import java.util.ArrayList;
import java.util.List;

/**
 * @see https://github.com/wordnik/swagger-spec/blob/master/versions/1.2.md#52-api-declaration
 * Created by mnimer on 8/28/14.
 */
public class ApiDeclaration
{
    private transient String fileName;

    //Swagger Spec
    /**
     * Provides the version of the application API (not to be confused by the specification version).
     */
    private String apiVersion;
    //Swagger Spec
    /**
     * Required. Specifies the Swagger Specification version being used. It can be used by the Swagger UI and other clients to interpret the API listing. The value MUST be an existing Swagger specification version.
     Currently, "1.0", "1.1", "1.2" are valid values.
     */
    private String swaggerVersion;
    //Swagger Spec
    /**
     * Required. The root URL serving the API. This field is important because while it is common to have the Resource Listing and API Declarations on the server providing the APIs themselves, it is not a requirement. The API specifications can be served using static files and not generated by the API server itself, so the URL for serving the API cannot always be derived from the URL serving the API specification. The value SHOULD be in the format of a URL.
     */
    private String basePath;
    //Swagger Spec
    /**
     * The relative path to the resource, from the basePath, which this API Specification describes. The value MUST precede with a forward slash ("/").
     */
    private String resourcePath = "/";
    //Swagger Spec
    /**
     * Required. A list of the APIs exposed on this resource. There MUST NOT be more than one API Object per path in the array.
     */
    private List<Api> apis = new ArrayList<>();
    //Swagger Spec
    /**
     * A list of the models available to this resource. Note that these need to be exposed separately for each API Declaration.
     */
    private List<Object> models = new ArrayList<>();
    //Swagger Spec
    /**
     * A list of authorizations schemes required for the operations listed in this API declaration. Individual operations may override this setting. If there are multiple authorization schemes described here, it means they're all applied.
     */
    private Object[] authorizations = null;
    //Swagger Spec
    /**
     * A list of MIME types the APIs on this resource can produce. This is global to all APIs but can be overridden on specific API calls.
     */
    private String produces = null;
    //Swagger Spec
    /**
     * A list of MIME types the APIs on this resource can consume. This is global to all APIs but can be overridden on specific API calls.
     */
    private String consumes = null;

    //swagger spec
    private Integer position = 0;

    //swagger spec
    private String protocols = "HTTP";

    //swagger spec
    private String tags = "";


    public String getFileName()
    {
        return fileName;
    }


    public void setFileName(String fileName)
    {
        this.fileName = fileName.replace("/../", "");
    }


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


    public String getBasePath()
    {
        return basePath;
    }


    public void setBasePath(String basePath)
    {
        this.basePath = basePath;
    }


    public String getResourcePath()
    {
        return resourcePath;
    }


    public void setResourcePath(String resourcePath)
    {
        this.resourcePath = resourcePath;
    }


    public List<Api> getApis()
    {
        return apis;
    }


    public void setApis(List<Api> apis)
    {
        this.apis = apis;
    }


    public List<Object> getModels()
    {
        return models;
    }


    public void setModels(List<Object> models)
    {
        this.models = models;
    }


    public Object[] getAuthorizations()
    {
        return authorizations;
    }


    public void setAuthorizations(Object[] authorizations)
    {
        this.authorizations = authorizations;
    }


    public String getProduces()
    {
        return produces;
    }


    public void setProduces(String produces)
    {
        this.produces = produces;
    }


    public String getConsumes()
    {
        return consumes;
    }


    public void setConsumes(String consumes)
    {
        this.consumes = consumes;
    }


    public Integer getPosition()
    {
        return position;
    }


    public void setPosition(Integer position)
    {
        this.position = position;
    }


    public String getProtocols()
    {
        return protocols;
    }


    public void setProtocols(String protocols)
    {
        this.protocols = protocols;
    }


    public String getTags()
    {
        return tags;
    }


    public void setTags(String tags)
    {
        this.tags = tags;
    }


    public String toJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
