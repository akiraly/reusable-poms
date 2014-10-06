reusable-poms
=============

This project contains reusable maven parent poms and maven project archetypes with sensible defaults, for example pre-configured with the latest maven plugin versions or already defining some common libraries as dependencies (commons-lang, guava, etc).

**Howto use the archetypes to generate new projects:**
------------------------------------------------------

Please replace `groupId`, `artifactId` and `version` values with yours.

### Simple java project with basic dependencies (commons-lang, guava,...):
> mvn archetype:generate -DarchetypeGroupId=com.github.akiraly.reusable-poms -DarchetypeArtifactId=simple-java-project-with-util-libs-archetype -DarchetypeVersion=4 -DinteractiveMode=false -DgroupId=foo.bar.baz -DartifactId=bar-utils -Dversion=1.0-SNAPSHOT

### Simple java project with basic spring deps (+ deps from the previous):
> mvn archetype:generate -DarchetypeGroupId=com.github.akiraly.reusable-poms -DarchetypeArtifactId=simple-java-project-with-spring-context-archetype -DarchetypeVersion=4 -DinteractiveMode=false -DgroupId=foo.bar.baz -DartifactId=bar-spring -Dversion=1.0-SNAPSHOT

### Simple java project for db related stuff: spring-jdbc, dbcp, h2 (+ deps from the previous ones):
> mvn archetype:generate -DarchetypeGroupId=com.github.akiraly.reusable-poms -DarchetypeArtifactId=simple-java-project-with-spring-dbcp-archetype -DarchetypeVersion=4 -DinteractiveMode=false -DgroupId=foo.bar.baz -DartifactId=bar-spring-dbcp -Dversion=1.0-SNAPSHOT
