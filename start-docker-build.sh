#create new project
#export ACTION=new 

#build photo-album projects
#export ACTION=build

#install dependency
#export ACTION=dep

#install all dependencies
#export ACTION=all_dep

#action
export ACTION=dep

#name of the component
#export COMPONENT=login

#name of dependency
export DEPENDENCY=@angular/material

export ROOT=/mnt/c/Users/alexandru.sabou/Desktop/PhotoAlbum
docker-compose -f ./docker/docker-compose.build.yml up --remove-orphans