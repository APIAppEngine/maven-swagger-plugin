package com.mikenimer.apappengine.util.models.v1_2;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single endpoint/method
 *
 * Created by mnimer on 8/28/14.
 *
 */
public class Api
{
    //swagger spec
    /**
     * Required. The relative path to the operation, from the basePath, which this operation describes. The value SHOULD be in a relative (URL) path format.
     */
    private String path = "";
    //swagger spec
    /**
     * Recommended. A short description of the resource.
     */
    private String description = "";
    //swagger spec
    /**
     * Required. A list of the API operations available on this path. The array may include 0 or more operations. There MUST NOT be more than one Operation Object per method in the array.
     */
    private List<Operation> operations = new ArrayList<>();


    public String getPath()
    {
        return path;
    }


    public void setPath(String path)
    {
        this.path = path;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public List<Operation> getOperations()
    {
        return operations;
    }


    public void setOperations(List<Operation> operations)
    {
        this.operations = operations;
    }


}
