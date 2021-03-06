package com.mikenimer.apappengine.util;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.AbstractComponentConfigurator;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ConfigurationListener;
import org.codehaus.plexus.component.configurator.converters.composite.ObjectWithFieldsConverter;
import org.codehaus.plexus.component.configurator.converters.special.ClassRealmConverter;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class IncludeProjectDependenciesComponentConfigurator extends AbstractComponentConfigurator
{


        private final static Logger logger = Logger.getLogger(IncludeProjectDependenciesComponentConfigurator.class.getName());

        public void configureComponent(Object component, PlexusConfiguration configuration, ExpressionEvaluator expressionEvaluator, org.codehaus.classworlds.ClassRealm containerRealm, ConfigurationListener listener) throws ComponentConfigurationException
        {

            addProjectDependenciesToClassRealm(expressionEvaluator, containerRealm);

            converterLookup.registerConverter(new ClassRealmConverter(containerRealm));

            ObjectWithFieldsConverter converter = new ObjectWithFieldsConverter();

            converter.processConfiguration(converterLookup, component, containerRealm.getClassLoader(), configuration, expressionEvaluator, listener);
        }

        private void addProjectDependenciesToClassRealm(ExpressionEvaluator expressionEvaluator, org.codehaus.classworlds.ClassRealm containerRealm) throws ComponentConfigurationException {
            Set<String> runtimeClasspathElements = new HashSet<String>();
            try {
                runtimeClasspathElements.addAll((List<String>) expressionEvaluator.evaluate("${project.runtimeClasspathElements}"));

            } catch (ExpressionEvaluationException e) {
                throw new ComponentConfigurationException("There was a problem evaluating: ${project.runtimeClasspathElements}", e);
            }

            Collection<URL> urls = buildURLs(runtimeClasspathElements);
            urls.addAll(buildAritfactDependencies(expressionEvaluator));
            for (URL url : urls) {
                containerRealm.addConstituent(url);
            }
        }

        private Collection<URL> buildAritfactDependencies(ExpressionEvaluator expressionEvaluator) throws ComponentConfigurationException {
            MavenProject project;
            try {
                project = (MavenProject) expressionEvaluator.evaluate("${project}");
            } catch (ExpressionEvaluationException e1) {
                throw new ComponentConfigurationException("There was a problem evaluating: ${project}", e1);
            }
            Collection<URL> urls = new ArrayList<URL>();
            for (Object a : project.getArtifacts()) {
                try {
                    urls.add(((Artifact) a).getFile().toURI().toURL());
                } catch (MalformedURLException e) {
                    throw new ComponentConfigurationException("Unable to resolve artifact dependency: " + a, e);
                }
            }
            return urls;
        }

        private Collection<URL> buildURLs(Set<String> runtimeClasspathElements) throws ComponentConfigurationException {

            List<URL> urls = new ArrayList<URL>(runtimeClasspathElements.size());
            for (String element : runtimeClasspathElements) {
                try {
                    final URL url = new File(element).toURI().toURL();
                    urls.add(url);
                } catch (MalformedURLException e) {
                    throw new ComponentConfigurationException("Unable to access project dependency: " + element, e);
                }
            }

            return urls;
        }



}
