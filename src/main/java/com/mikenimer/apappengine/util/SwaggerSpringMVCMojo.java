package com.mikenimer.apappengine.util;

import com.mikenimer.apappengine.util.generators.v1_2.ResourceParser;
import com.mikenimer.apappengine.util.models.v1_2.ApiDeclaration;
import com.mikenimer.apappengine.util.models.v1_2.ResourceListing;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mnimer on 8/26/14.
 */
@Mojo( name = "swagger-springmvc-generator", defaultPhase = LifecyclePhase.COMPILE,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class SwaggerSpringMVCMojo extends AbstractMojo
{
    @Parameter( defaultValue = "${project.build.directory}/api-docs/docs/index.json", required = true )
    private File outputDirectoryAndFile;

    @Parameter( required = true )
    private ResourceListing apiListing;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "project.compileClasspathElements", required = true, readonly = true)
    private List<String> classpath;

    @Parameter(defaultValue="${descriptor}")
    private PluginDescriptor descriptor;


    public File getOutputDirectoryAndFile()
    {
        return outputDirectoryAndFile;
    }


    public void setOutputDirectoryAndFile(File outputDirectoryAndFile)
    {
        this.outputDirectoryAndFile = outputDirectoryAndFile;
    }


    public ResourceListing getApiListing()
    {
        return apiListing;
    }


    public void setApiListing(ResourceListing apiListing)
    {
        this.apiListing = apiListing;
    }


    public MavenProject getProject()
    {
        return project;
    }


    public void setProject(MavenProject project)
    {
        this.project = project;
    }


    public List<String> getClasspath()
    {
        return classpath;
    }


    public void setClasspath(List<String> classpath)
    {
        this.classpath = classpath;
    }


    public PluginDescriptor getDescriptor()
    {
        return descriptor;
    }


    public void setDescriptor(PluginDescriptor descriptor)
    {
        this.descriptor = descriptor;
    }


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        getLog().debug("**********************************");
        getLog().debug("version=" +apiListing.getApiVersion());
        getLog().debug("output director=" +outputDirectoryAndFile);

        // load up all of the classes so we can search for different annotations.
        loadClasses();

        // Parse all of the SpringMVC classes and create the swagger DOM
        ResourceParser resourceParser = new ResourceParser(apiListing);
        resourceParser.execute();

        // generate the final swagger json files
        generateSwaggerFiles(resourceParser);
    }


    /**
     * Load all of the project classes into our class loader, so we can query for annotated classes
     */
    private void loadClasses()
    {
        try {
            Set<URL> urls = new HashSet<URL>();
            List<String> elements = project.getRuntimeClasspathElements();
            //getRuntimeClasspathElements()
            //getCompileClasspathElements()
            //getSystemClasspathElements()
            for (String element : elements) {
                urls.add(new File(element).toURI().toURL());
            }


            ClassLoader contextClassLoader = URLClassLoader.newInstance(
                    urls.toArray(new URL[0]),
                    Thread.currentThread().getContextClassLoader());

            Thread.currentThread().setContextClassLoader(contextClassLoader);

        } catch (DependencyResolutionRequiredException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Create the different swagger json files.
     */
    private void generateSwaggerFiles(ResourceParser resourceParser)
    {
        File dir = new File(project.getBuild().getOutputDirectory() +apiListing.getOutputWebDir());
        if( !dir.exists() )
        {
            dir.mkdir();
        }

        if( dir.isDirectory() ){
            try{
                //write the ResourceListing root file
                File resourceFile = outputDirectoryAndFile;//new File(dir.getPath() +"/index.json");
                if( !resourceFile.getParentFile().exists() )
                {
                    resourceFile.getParentFile().mkdir();
                }

                FileWriter writer = new FileWriter(resourceFile);
                writer.write(apiListing.toJson());
                writer.close();

                //write each of the individual resource API files
                int indx = 1;
                for (ApiDeclaration api : resourceParser.getApis()) {
                    File apiFile = new File(resourceFile.getParentFile() +"/"  +api.getFileName());
                    FileWriter apiWriter = new FileWriter(apiFile);
                    apiWriter.write(api.toJson());
                    apiWriter.close();
                }

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
