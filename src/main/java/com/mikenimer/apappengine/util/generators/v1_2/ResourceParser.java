package com.mikenimer.apappengine.util.generators.v1_2;

import com.google.gson.Gson;
import com.mikenimer.apappengine.util.models.v1_2.Api;
import com.mikenimer.apappengine.util.models.v1_2.ApiDeclaration;
import com.mikenimer.apappengine.util.models.v1_2.Resource;
import com.mikenimer.apappengine.util.models.v1_2.ResourceListing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mnimer on 8/26/14.
 */
public class ResourceParser
{
    private ResourceListing resourceListing;
    private List<ApiDeclaration> apis = new ArrayList<>();


    public ResourceParser(ResourceListing resourceListing)
    {
        this.resourceListing = resourceListing;
    }


    public List<ApiDeclaration> getApis()
    {
        return apis;
    }


    public void execute()
    {
        for (Resource resource : resourceListing.getResources()) {
            List<ApiDeclaration> apiList = new ApiParser(resourceListing, resource).execute();
            this.apis.addAll(apiList);
        }
    }


/** todo delete
    public Set<Class<?>> findControllerClasses(File buildOutputDir) throws IOException, ClassNotFoundException {

        Collection<URL> urls = ClasspathHelper.forJavaClassPath();
        if (buildOutputDir != null) {
            urls.add(buildOutputDir.toURI().toURL());
        }

        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(urls));
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Controller.class);
        return types;
    }
 ***/
}
