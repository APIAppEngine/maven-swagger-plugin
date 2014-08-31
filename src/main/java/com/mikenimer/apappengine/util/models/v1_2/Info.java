package com.mikenimer.apappengine.util.models.v1_2;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * @see https://github.com/wordnik/swagger-spec/blob/master/versions/1.2.md#513-info-object
 *
 * Created by mnimer on 8/28/14.
 */
public class Info
{
    //swagger spec
    /**
     * Required. The title of the application
     */
    @Parameter(required = false)
    private String title = null;
    //swagger spec
    /**
     * Required. A short description of the application.
     */
    @Parameter(required = false)
    private String description = null;
    //swagger spec
    /**
     * A URL to the Terms of Service of the API.
     */
    @Parameter(required = false)
    private String termsOfServiceUrl = null;
    //swagger spec
    /**
     * An email to be used for API-related correspondence.
     */
    @Parameter(required = false)
    private String contact = null;
    //swagger spec
    /**
     * The license name used for the API.
     */
    @Parameter(required = false)
    private String license = null;
    //swagger spec
    /**
     * A URL to the license used for the API.
     */
    @Parameter(required = false)
    private String licenseUrl = null;


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public String getTermsOfServiceUrl()
    {
        return termsOfServiceUrl;
    }


    public void setTermsOfServiceUrl(String termsOfServiceUrl)
    {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }


    public String getContact()
    {
        return contact;
    }


    public void setContact(String contact)
    {
        this.contact = contact;
    }


    public String getLicense()
    {
        return license;
    }


    public void setLicense(String license)
    {
        this.license = license;
    }


    public String getLicenseUrl()
    {
        return licenseUrl;
    }


    public void setLicenseUrl(String licenseUrl)
    {
        this.licenseUrl = licenseUrl;
    }
}
