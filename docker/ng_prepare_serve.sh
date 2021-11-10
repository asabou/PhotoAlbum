ROOT_PATH=/photo-album

install_angular_cli() {
    npm install -g @angular/cli@13.0.0
}

serve_angular() {
    /usr/local/bin/ng serve --host 0.0.0.0 --port 4200
}

cd $ROOT_PATH/photo-album-web
install_angular_cli
serve_angular