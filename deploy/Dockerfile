###############################################################################
# README
###############################################################################
# This Dockerfile prepares a docker image with MobileRT.
#
# It setups MobileRT by fetching it from git and compiling it using the
# `install_dependencies.sh` and `compile_native.sh` scripts inside the docker
# image.
# The script `install_dependencies.sh` installs all the Linux necessary
# dependencies in order to compile MobileRT.
# And the script `compile_native.sh` compiles the MobileRT.
#
# At the moment, this Dockerfile allows to setup MobileRT inside a docker image
# in multiple Linux distributions:
# * Debian
# * Red Hat
# * Arch
# * Alpine
# * Gentoo
###############################################################################
###############################################################################


###############################################################################
# Set base docker image
###############################################################################
ARG BASE_IMAGE=ubuntu:22.04
FROM ${BASE_IMAGE}
###############################################################################
###############################################################################


###############################################################################
# Setup arguments
###############################################################################
ARG BUILD_TYPE=release
ARG BRANCH=master
###############################################################################
###############################################################################


###############################################################################
# Change user to root
###############################################################################
USER root
###############################################################################
###############################################################################


###############################################################################
# Prepare environment
###############################################################################
ENV \
    # Create an environment variable to use it in the docker container when boot
    ENV_BUILD_TYPE=${BUILD_TYPE} \
    # Allow execution of Qt in docker
    DISPLAY=":1" \
    QT_GRAPHICSSYSTEM="native" \
    # Set lib path
    LD_LIBRARY_PATH="/MobileRT/libraries:/MobileRT/build_${BUILD_TYPE}/lib" \
    # Set apt-get as non interactive mode
    DEBIAN_FRONTEND="noninteractive"
###############################################################################
###############################################################################


###############################################################################
# Print environment
###############################################################################
RUN echo "DISPLAY = ${DISPLAY}"; \
    echo "QT_GRAPHICSSYSTEM = ${QT_GRAPHICSSYSTEM}"; \
    echo "LD_LIBRARY_PATH = ${LD_LIBRARY_PATH}"; \
    echo "DEBIAN_FRONTEND = ${DEBIAN_FRONTEND}"; \
    echo "BUILD_TYPE = ${BUILD_TYPE}";
###############################################################################
###############################################################################


###############################################################################
# Install git
###############################################################################
RUN \
if command -v apt-get > /dev/null; then \
  echo 'Detected Debian based Linux'; \
  apt-get update -y; \
  apt-get install --no-install-recommends -y sudo git tzdata; \
elif command -v yum > /dev/null; then \
  echo 'Detected Red Hat based Linux'; \
  yum --setopt=skip_missing_names_on_install=False --setopt=skip_missing_names_on_update=False update -y; \
  yum --setopt=skip_missing_names_on_install=False --setopt=skip_missing_names_on_update=False install -y \
    sudo git; \
elif command -v pacman > /dev/null; then \
  echo 'Detected Arch based Linux'; \
  rm -rf /var/cache/pacman/pkg/; \
  pacman -Sy archlinux-keyring --noconfirm --needed; \
  echo 'Resetting all the keys'; \
  pacman-key --init; \
  pacman-key --populate; \
  echo 'Upgrade system'; \
  pacman -Syu --noconfirm --needed; \
  pacman -Sy --noconfirm --needed sudo git; \
elif command -v apk > /dev/null; then \
  echo 'Detected Alpine based Linux'; \
  apk update; \
  apk add sudo git; \
elif command -v emerge > /dev/null; then \
  echo 'Detected Gentoo based Linux'; \
  echo 'FEATURES="-sandbox -usersandbox -ipc-sandbox -network-sandbox -pid-sandbox"' >> /etc/portage/make.conf; \
  echo 'USE="dev-libs/libpcre2-10.35 pcre16 x11-libs/libxkbcommon-1.0.3 media-libs/libglvnd-1.3.2-r2 X"' >> /etc/portage/make.conf; \
  emerge --sync || true; \
  emerge sudo dev-vcs/git; \
else \
  echo 'Detected unknown based Linux'; \
fi
###############################################################################
###############################################################################


###############################################################################
# Configure git
###############################################################################
RUN git config --global http.postBuffer 1048576000; \
    git config --global https.postBuffer 1048576000; \
    git config --global core.compression -1; \
    git config --global http.sslVerify "false";
###############################################################################
###############################################################################


###############################################################################
# Get MobileRT contents (required to create docker image in CI)
###############################################################################
WORKDIR /
RUN git clone https://github.com/TiagoMSSantos/MobileRT.git \
  --shallow-submodules \
  --progress \
  --branch ${BRANCH} && \
  ls -la ./MobileRT/app/src/androidTest/resources/CornellBox/CornellBox-Water.obj && \
  ls -la ./MobileRT/app/src/androidTest/resources/teapot/teapot.obj;
###############################################################################
###############################################################################


###############################################################################
# Alernative: Copy MobileRT contents locally (not possible in CI)
###############################################################################
# COPY . MobileRT
###############################################################################
###############################################################################


###############################################################################
# Install dependencies
###############################################################################
WORKDIR /MobileRT
RUN sh scripts/install_dependencies.sh;
###############################################################################
###############################################################################


###############################################################################
# Compile MobileRT
###############################################################################
RUN sh scripts/compile_native.sh -t ${BUILD_TYPE} -c g++ -r yes && \
    du -h -d 1 build_${BUILD_TYPE}/bin && \
    du -h -d 1 build_${BUILD_TYPE}/lib && \
    ls -la ./app/src/androidTest/resources/CornellBox/CornellBox-Water.obj && \
    ls -la ./app/src/androidTest/resources/teapot/teapot.obj && \
    rm -rfv $(find app/ -iname "*" ! -name "app" ! -name "MobileRT.jks" ! -name "src" ! -name "androidTest" ! -name "resources" | grep -v "src/androidTest/resources") && \
    rm -rfv docs && \
    rm -rfv documentation && \
    rm -rfv git-hooks && \
    rm -rfv gradle && \
    rm -rfv .git && \
    ls -la ./app/src/androidTest/resources/CornellBox/CornellBox-Water.obj && \
    ls -la ./app/src/androidTest/resources/CornellBox/CornellBox-Water.mtl && \
    ls -la ./app/src/androidTest/resources/CornellBox/CornellBox-Water.cam && \
    ls -la ./app/src/androidTest/resources/teapot/teapot.obj && \
    ls -la ./app/src/androidTest/resources/teapot/teapot.mtl && \
    ls -la ./app/src/androidTest/resources/teapot/teapot.cam && \
    chmod -R +x scripts/
###############################################################################
###############################################################################


###############################################################################
# Create volume (for Qt)
###############################################################################
VOLUME /tmp/.X11-unix
###############################################################################
###############################################################################


###############################################################################
# Execute MobileRT by default
###############################################################################
ENTRYPOINT ["sh", "-c"]
CMD ["sh ./scripts/profile.sh ${ENV_BUILD_TYPE}"]
###############################################################################
###############################################################################
