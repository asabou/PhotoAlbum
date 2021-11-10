ROOT_PATH=/photo-album

ACTION=$1

install_angular_cli() {
    npm install -g @angular/cli@13.0.0
}

create_new_project() {
    cd $ROOT_PATH
    /usr/local/bin/ng new photo-album-web
}

build_project() {
    cd $ROOT_PATH/photo-album-web
    /usr/local/bin/ng build
}

install_dependency() {
    cd $ROOT_PATH/photo-album-web
    npm install --save ${DEPENDENCY}
}

install_all_dependencies() {
    cd $ROOT_PATH/photo-album-web
    npm install
}

create_new_component() {
    cd $ROOT_PATH/photo-album-web
    /usr/local/bin/ng g c ${COMPONENT}
}

install_angular_cli

case ${ACTION} in
    new) create_new_project ;;
    build) build_project ;;
    dep) install_dependency ;;
    all_dep) install_all_dependencies ;;
    comp) create_new_component ;;
esac
