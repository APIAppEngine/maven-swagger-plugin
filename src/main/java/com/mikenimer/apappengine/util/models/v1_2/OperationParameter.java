package com.mikenimer.apappengine.util.models.v1_2;

import com.google.gson.annotations.SerializedName;

/**
 * @See https://github.com/wordnik/swagger-spec/blob/master/versions/1.2.md#524-parameter-object
 * Created by mnimer on 8/28/14.
 */
public class OperationParameter
{
    //swagger spec
    /**
     * Required. The type of the parameter (that is, the location of the parameter in the request). The value MUST be one of these values: "path", "query", "body", "header", "form". Note that the values MUST be lower case.
     */
    private String paramType;
    //swagger spec
    /**
     * Required. The unique name for the parameter. Each name MUST be unique, even if they are associated with different paramType values. Parameter names are case sensitive.
         If paramType is "path", the name field MUST correspond to the associated path segment from the path field in the API Object.
         If paramType is "query", the name field corresponds to the query parameter name.
         If paramType is "body", the name is used only for Swagger-UI and Swagger-Codegen. In this case, the name MUST be "body".
         If paramType is "form", the name field corresponds to the form parameter key.
         If paramType is "header", the name field corresponds to the header parameter key.
     */
    private String name;
    //swagger spec
    /**
     * Recommended. A brief description of this parameter.
     */
    private String description;
    //swagger spec
    /**
     * A flag to note whether this parameter is required. If this field is not included, it is equivalent to adding this field with the value false. If paramType is "path" then this field MUST be included and have the value true.
     */
    private Boolean required = true;
    //swagger spec
    /**
     * Another way to allow multiple values for a "query" parameter. If used, the query parameter may accept comma-separated values. The field may be used only if paramType is "query", "header" or "path".
     */
    private Boolean allowMultiple = false;

    //swagger datatype spec
    private String type;

    //swagger datatype spec
    @SerializedName("$ref")
    private String ref;

    //swagger datatype spec
    private String format;

    //swagger datatype spec
    private String defaultValue;
    //swagger datatype spec
    private String minimum;

    //swagger datatype spec
    private String maximum;

    //swagger datatype spec
    @SerializedName("[enum]")
    private String allowableValues;

    private Boolean uniqueItems;

    private String access;

    //todo private List<Item> items;


    public String getParamType()
    {
        return paramType;
    }


    public void setParamType(String paramType)
    {
        this.paramType = paramType.toLowerCase();
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public Boolean getRequired()
    {
        return required;
    }


    public void setRequired(Boolean required)
    {
        this.required = required;
    }


    public Boolean getAllowMultiple()
    {
        return allowMultiple;
    }


    public void setAllowMultiple(Boolean allowMultiple)
    {
        this.allowMultiple = allowMultiple;
    }


    public String getType()
    {
        return type;
    }


    public void setType(String type)
    {
        this.type = type;
    }


    public String getRef()
    {
        return ref;
    }


    public void setRef(String ref)
    {
        this.ref = ref;
    }


    public String getFormat()
    {
        return format;
    }


    public void setFormat(String format)
    {
        this.format = format;
    }


    public String getDefaultValue()
    {
        return defaultValue;
    }


    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }


    public String getMinimum()
    {
        return minimum;
    }


    public void setMinimum(String minimum)
    {
        this.minimum = minimum;
    }


    public String getMaximum()
    {
        return maximum;
    }


    public void setMaximum(String maximum)
    {
        this.maximum = maximum;
    }


    public String getAllowableValues()
    {
        return allowableValues;
    }


    public void setAllowableValues(String allowableValues)
    {
        this.allowableValues = allowableValues;
    }


    public Boolean getUniqueItems()
    {
        return uniqueItems;
    }


    public void setUniqueItems(Boolean uniqueItems)
    {
        this.uniqueItems = uniqueItems;
    }


    public String getAccess()
    {
        return access;
    }


    public void setAccess(String access)
    {
        this.access = access;
    }
}

