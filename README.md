reusable-poms
=============

This project contains reusable maven parent poms and maven project archetypes with sensible defaults, for example pre-configured with the latest maven plugin versions or already defining some common libraries as dependencies (commons-lang, guava, etc).

*Howto use the archetypes to generate new projects:*
---------------------------------------------------

Please replace `groupId`, `artifactId` and `version` values with yours.

Simple java project with basic dependencies (commons-lang, guava,...):
----------------------------------------------------------------------
``

Simple java project with basic spring deps (+ deps from the previous):
----------------------------------------------------------------------
``

Simple java project for db related stuff with jpa dependencies: spring-data, hibernate, querydsl (+ deps from the previous ones):
---------------------------------------------------------------------------------------------------------------------------------
``
