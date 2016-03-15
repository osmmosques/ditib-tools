# Overall settings...

MONTH=$(date +%Y%m)
DAY=$(date +%Y%m%d)

APP_PROPERTIES=/etc/webapps/osm-mosques/application-optional-override.properties

if [ -r ${APP_PROPERTIES} ]
then
    WEBDATA=$(grep ditib.data.location ${APP_PROPERTIES} | sed -e 's|^ditib.data.location=||')
fi

if [ "${WEBDATA}" == "" ]
then
    WEBDATA=${HOME}/data
fi

mkdir -p ${WEBDATA}

STORAGE=${HOME}/Dropbox/osmdata
mkdir -p ${STORAGE}

TMPDIR=${HOME}/tmp/osm-place-diyanet
mkdir -p ${TMPDIR}

LOGDIR=${HOME}/logs
mkdir -p ${LOGDIR}

db=osm_mosques

DB_DIR=${STORAGE}/database/${MONTH}/${DAY}
mkdir -p ${DB_DIR}


OSMOSIS=${HOME}/osmosis-0.44.1
