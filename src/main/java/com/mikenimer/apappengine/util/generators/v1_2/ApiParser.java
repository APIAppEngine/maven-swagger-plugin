package com.mikenimer.apappengine.util.generators.v1_2;

import com.mikenimer.apappengine.util.models.v1_2.Api;
import com.mikenimer.apappengine.util.models.v1_2.ApiDeclaration;
import com.mikenimer.apappengine.util.models.v1_2.Operation;
import com.mikenimer.apappengine.util.models.v1_2.OperationParameter;
import com.mikenimer.apappengine.util.models.v1_2.Resource;
import com.mikenimer.apappengine.util.models.v1_2.ResourceListing;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by mnimer on 8/28/14.
 */
public class ApiParser
{
    private Resource resource;
    private ResourceListing resourceListing;
    private List<String> basePackages;

    //generated apis
    private List<ApiDeclaration> apiDeclarations = new ArrayList<>();

    public ApiParser(ResourceListing resourceListing, Resource resource)
    {
        this.resource = resource;
        this.resourceListing = resourceListing;
        this.basePackages = resource.getBasePackage();
    }


    public List<ApiDeclaration> execute()
    {
        ApiDeclaration apiDeclaration = new ApiDeclaration();
        apiDeclaration.setFileName(resource.getPath());
        apiDeclaration.setApiVersion(resourceListing.getApiVersion());
        apiDeclaration.setSwaggerVersion(resourceListing.getSwaggerVersion());
        apiDeclaration.setBasePath(resource.getBasePath());



        for (String basePackage : basePackages) {

            try {
                Reflections reflections = new Reflections(basePackage);
                //find Spring @Controller classes
                Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);

                //1. find all of the SpringMVC controllers
                int classIndx = 1;
                for (Class<?> aClass : classes) {

                    String rootPath = "";
                    boolean isApiController = false;

                    // Look for @RequestMapping at the class level, and set that as a root
                    if (aClass.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMappingAnnotation = aClass.getAnnotation(RequestMapping.class);

                        if (requestMappingAnnotation != null) {
                            String[] values = requestMappingAnnotation.value();
                            if (values.length > 0) {
                                isApiController = true;
                                rootPath = values[0];
                            }

                            //apiDeclaration.setConsumes(StringUtils.join(requestMappingAnnotation.consumes(), ","));
                            //apiDeclaration.setProduces(StringUtils.join(requestMappingAnnotation.produces(), ","));
                        }
                    }

                    if (aClass.isAnnotationPresent(com.wordnik.swagger.annotations.Api.class)) {
                        com.wordnik.swagger.annotations.Api apiAnnotation = aClass.getAnnotation(com.wordnik.swagger.annotations.Api.class);

                        apiDeclaration.setProtocols(apiAnnotation.protocols());
                        apiDeclaration.setPosition(apiAnnotation.position());
                        apiDeclaration.setProtocols(apiAnnotation.protocols());

                        if( apiAnnotation.consumes().length() > 0 ) {
                            apiDeclaration.setProduces(  StringUtils.join(apiAnnotation.consumes(), ",")  );
                        }
                        if( apiAnnotation.produces().length() > 0 ) {
                            apiDeclaration.setProduces(  StringUtils.join(apiAnnotation.produces(), ",")  );
                        }
                        if( apiAnnotation.basePath().length() > 0 ) {
                            //apiDeclaration.setBasePath(apiAnnotation.basePath());
                        }


                        //apiDeclaration.setFileName(apiAnnotation.value());
                        //apiDeclaration.setAuthorizations(apiAnnotation.authorizations());
                    }



                    // Next look at each method with a @RequestMapping.
                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            Annotation requestMappingMethodAnnotation = method.getAnnotation(RequestMapping.class);
                            Annotation apiOperationMethodAnnotation = method.getAnnotation(com.wordnik.swagger.annotations.ApiOperation.class);

                            if (requestMappingMethodAnnotation != null) {
                                String[] params = ((RequestMapping) requestMappingMethodAnnotation).params();
                                String[] produces = ((RequestMapping) requestMappingMethodAnnotation).produces();
                                String[] consumes = ((RequestMapping) requestMappingMethodAnnotation).consumes();
                                RequestMethod[] httpMethods = ((RequestMapping) requestMappingMethodAnnotation).method();
                                String[] headers = ((RequestMapping) requestMappingMethodAnnotation).headers();
                                String[] values = ((RequestMapping) requestMappingMethodAnnotation).value();
                                if (values.length > 0) {
                                    isApiController = true;
                                    String path = rootPath +values[0];


                                    Api api = new Api();
                                    api.setPath(path);

                                    // create an operation, over for each http verb
                                    for (RequestMethod httpMethod : httpMethods) {
                                        Operation operation = new Operation();
                                        api.getOperations().add(operation);
                                        operation.setMethod(httpMethod.name());
                                        if( produces.length > 0) {
                                            operation.setProduces(Arrays.toString(produces));
                                        }
                                        if( consumes.length > 0 ){
                                            operation.setConsumes(Arrays.toString(consumes));
                                        }




                                        for (Annotation[] annotations : method.getParameterAnnotations()) {

                                            // A quick sort to make sure @ApiParam can override the spring annotations.
                                            Arrays.sort(annotations, new Comparator<Annotation>()
                                            {
                                                @Override
                                                public int compare(Annotation o1, Annotation o2)
                                                {
                                                    if( !(o1 instanceof ApiParam) && (o2 instanceof ApiParam) ) return -1;
                                                    if( (o1 instanceof ApiParam) && !(o2 instanceof ApiParam) ) return 1;
                                                    return 0;
                                                }
                                            });

                                            OperationParameter parameter = new OperationParameter();
                                            operation.getParameters().add(parameter);


                                            for (int i = 0; i < annotations.length; i++) {
                                                Annotation annotation = annotations[i];

                                                //multipart
                                                if( annotation instanceof RequestPart)
                                                {
                                                    //@see https://github.com/wordnik/swagger-spec/blob/master/versions/1.2.md#524-parameter-object
                                                    operation.setConsumes("multipart/form-data");
                                                    parameter.setParamType("form");
                                                    parameter.setType("file");
                                                    parameter.setRequired( ((RequestPart)annotation).required() );
                                                    parameter.setName( ((RequestPart)annotation).value() );
                                                }
                                                //form
                                                if( annotation instanceof RequestParam)
                                                {
                                                    parameter.setParamType("form");
                                                    parameter.setRequired( ((RequestParam)annotation).required() );
                                                    parameter.setName( ((RequestParam)annotation).value() );
                                                    parameter.setDefaultValue( ((RequestParam)annotation).defaultValue().trim() );
                                                }
                                                //url
                                                if( annotation instanceof PathVariable)
                                                {
                                                    parameter.setParamType("url");
                                                    parameter.setRequired(true);
                                                    parameter.setName( ((PathVariable)annotation).value() );
                                                }
                                                //header
                                                if( annotation instanceof RequestHeader)
                                                {
                                                    parameter.setParamType("header");
                                                    parameter.setRequired( ((RequestHeader)annotation).required() );
                                                    parameter.setName( ((RequestHeader)annotation).value() );
                                                    parameter.setDefaultValue( ((RequestHeader)annotation).defaultValue().trim() );
                                                }
                                                if( annotation instanceof MatrixVariable )
                                                {
                                                    //todo
                                                    //((MatrixVariable)annotation).pathVar();
                                                    //((MatrixVariable)annotation).required();
                                                    //((MatrixVariable)annotation).value();
                                                    //((MatrixVariable)annotation).defaultValue();
                                                }
                                                //Overrides
                                                if( annotation instanceof ApiParam ) {
                                                    parameter.setAllowMultiple( ((ApiParam)annotation).allowMultiple() );
                                                    parameter.setName( ((ApiParam)annotation).name() );
                                                    parameter.setRequired( ((ApiParam)annotation).required() );
                                                    parameter.setAllowableValues( ((ApiParam)annotation).allowableValues() );
                                                    parameter.setDefaultValue( ((ApiParam)annotation).defaultValue().trim() );
                                                    parameter.setDescription( ((ApiParam)annotation).value() );
                                                    parameter.setAccess( ((ApiParam)annotation).access() );
                                                }
                                            }
                                        }



                                        //@API values are considered hard coded overrides, and will replace what we "parse" out of the spring mvc config
                                        if( method.isAnnotationPresent(ApiOperation.class) ) {
                                            ApiOperation apiOperationAnnotation = method.getAnnotation(ApiOperation.class);

                                            if (apiOperationAnnotation.httpMethod().length() > 0 ) {
                                                operation.setMethod( apiOperationAnnotation.httpMethod() );
                                            }
                                            if (apiOperationAnnotation.consumes().length() > 0 ) {
                                                operation.setConsumes(apiOperationAnnotation.consumes());
                                            }
                                            if (apiOperationAnnotation.produces().length() > 0 ) {
                                                operation.setProduces(apiOperationAnnotation.produces());
                                            }

                                            //operation.setAuthorizations( apiOperationAnnotation.authorizations() );
                                            if( apiOperationAnnotation.nickname().length() > 0 ) {
                                                operation.setNickname(apiOperationAnnotation.nickname());
                                            }
                                            operation.setNotes( apiOperationAnnotation.notes() );
                                            api.setDescription( apiOperationAnnotation.value() );
                                            //apiAnnotation.hidden();
                                            //apiAnnotation.position();
                                            //apiAnnotation.protocols();
                                            //apiAnnotation.response();
                                            //apiAnnotation.responseContainer();
                                            //apiAnnotation.tags();
                                        }
                                    }


                                    apiDeclaration.getApis().add(api);

                                }
                            }
                        }
                    }


                    if( isApiController )
                    {
                        apiDeclarations.add(apiDeclaration);
                    }
                }

            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return apiDeclarations;
    }
}
