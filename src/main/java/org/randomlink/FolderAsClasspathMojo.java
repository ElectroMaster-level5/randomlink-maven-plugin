package org.randomlink;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

@Mojo(name = "folderAsClasspath", requiresDependencyResolution = ResolutionScope.TEST, defaultPhase = LifecyclePhase.INITIALIZE)
public class FolderAsClasspathMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}")
    MavenProject project;

    @Parameter
    private Set<String> classpaths = new HashSet<>();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (classpaths.size() == 0) {
            return;
        }

        Set<Artifact> originalArtifacts = project.getArtifacts();

        DefaultArtifactHandler artifactHandler = new DefaultArtifactHandler("jar");
        artifactHandler.setExtension("jar");
        artifactHandler.setLanguage("java");
        artifactHandler.setAddedToClasspath(true);  // very important

        for (String classpath : classpaths) {
            File classpathFile = Paths.get(classpath).toFile();
            if (!(classpathFile.isDirectory() && classpathFile.exists())) {
                getLog().warn("classpath -> " + classpath + " not exist");
                continue;
            }

            String virtualArtifactId = "VirtualArtifact" + (new Date()).getTime();
            DefaultArtifact artifact = new DefaultArtifact("com.randomlink.virtualArtifacts",
                    virtualArtifactId,
                    "1.0-SNAPSHOT",
                    "system",
                    "jar",
                    null,
                    artifactHandler);

            List<String> dependencyTrail = new ArrayList<>();
            dependencyTrail.add(project.getArtifact().toString());
            dependencyTrail.add(artifact.toString());
            artifact.setDependencyTrail(dependencyTrail);

            artifact.setFile(classpathFile);
            artifact.setResolved(true);
            originalArtifacts.add(artifact);
        }

        project.setArtifacts(originalArtifacts);
        project.setResolvedArtifacts(originalArtifacts);

    }


}
