@REM create new project
@REM set ACTION=new 

@REM build photo-album projects
@REM set ACTION=build

@REM install dependency
@REM set ACTION=dep

@REM install all dependencies
@REM set ACTION=all_dep

@REM action
set ACTION=dep

@REM name of the component
@REM set COMPONENT=commons/abstract-table

@REM name of dependency
set DEPENDENCY=@angular/common

docker-compose -f ./docker/docker-compose.build.yml up