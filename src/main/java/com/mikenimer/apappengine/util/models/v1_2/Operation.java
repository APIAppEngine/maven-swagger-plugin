package com.mikenimer.apappengine.util.models.v1_2;

import com.wordnik.swagger.annotations.Authorization;

import java.util.ArrayList;
import java.util.List;

/**
 * @see https://github.com/wordnik/swagger-spec/blob/master/versions/1.2.md#523-operation-object
 * Created by mnimer on 8/28/14.
 */
public class Operation
{
    //swagger spec
    /**
     * Required. The HTTP method required to invoke this operation. The value MUST be one of the following values: "GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS". The values MUST be in uppercase.
     */
    private String method;
    //swagger spec
    /**
     * A short summary of what the operation does. For maximum readability in the swagger-ui, this field SHOULD be less than 120 characters.
     */
    private String summary;
    //swagger spec
    /**
     * A verbose explanation of the operation behavior.
     */
    private String notes;
    //swagger spec
    /**
     * Required. A unique id for the operation that can be used by tools reading the output for further and easier manipulation. For example, Swagger-Codegen will use the nickname as the method name of the operation in the client it generates. The value MUST be alphanumeric and may include underscores. Whitespace characters are not allowed.
     */
    private String nickname;
    //swagger spec
    /**
     * A list of MIME types this operation can produce. This is overrides the global produces definition at the root of the API Declaration. Each string value SHOULD represent a MIME type.
     */
    private String produces;
    //swagger spec
    /**
     * A list of MIME types this operation can consume. This is overrides the global consumes definition at the root of the API Declaration. Each string value SHOULD represent a MIME type.
     */
    private String consumes;
    //swagger spec
    /**
     * Declares this operation to be deprecated. Usage of the declared operation should be refrained. Valid value MUST be either "true" or "false". Note: This field will change to type boolean in the future.
     */
    private String deprecated = "false";
    //swagger spec
    /**
     * A list of authorizations required to execute this operation. While not mandatory, if used, it overrides the value given at the API Declaration's authorizations. In order to completely remove API Declaration's authorizations completely, an empty object ({}) may be applied.
     */
    private Object[] authorizations;
    //swagger spec
    /**
     * Lists the possible response statuses that can return from the operation.
     */
    private List<Object> responseMessages;
    //swagger spec
    /**
     * Required. The inputs to the operation. If no parameters are needed, an empty array MUST be included.
     */
    private List<OperationParameter> parameters = new ArrayList<>(); //default empty array


    public String getMethod()
    {
        return method;
    }


    public void setMethod(String method)
    {
        this.method = method.toUpperCase();
    }


    public String getSummary()
    {
        return summary;
    }


    public void setSummary(String summary)
    {
        this.summary = summary;
    }


    public String getNotes()
    {
        return notes;
    }


    public void setNotes(String notes)
    {
        this.notes = notes;
    }


    public String getNickname()
    {
        return nickname;
    }


    public void setNickname(String nickname)
    {
        this.nickname = nickname;
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


    public String getDeprecated()
    {
        return deprecated;
    }


    public void setDeprecated(String deprecated)
    {
        this.deprecated = deprecated;
    }


    public Object[] getAuthorizations()
    {
        return authorizations;
    }


    public void setAuthorizations(Object[] authorizations)
    {
        this.authorizations = authorizations;
    }


    public List<Object> getResponseMessages()
    {
        return responseMessages;
    }


    public void setResponseMessages(List<Object> responseMessages)
    {
        this.responseMessages = responseMessages;
    }


    public List<OperationParameter> getParameters()
    {
        return parameters;
    }


    public void setParameters(List<OperationParameter> parameters)
    {
        this.parameters = parameters;
    }
}
