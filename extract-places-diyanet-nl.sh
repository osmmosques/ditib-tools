#! /bin/sh

OSMOSIS=${HOME}/osmosis-0.43.1
STORAGE=${HOME}/Dropbox/osmdata
TMPDIR=${HOME}/tmp/osm-place-diyanet-nl
WEBDATA=/home/tomcat/osm-mosques/data

COUNTRY=netherlands

SOURCE=diyanet-nl

extract_data() {

    country=${COUNTRY}
    county=${SOURCE}

    FILE=${TMPDIR}/${country}-${source}.html

    mkdir -p ${STORAGE}/${country}-${source}/${MONTH}/${DAY}

    cp -f \
	${FILE} \
	${STORAGE}/${country}-${source}/${MONTH}/${DAY}/${country}-${source}.html

    cp -f \
	${FILE} \
	${WEBDATA}/${country}-${source}.html
}


country=${COUNTRY}
source=${SOURCE}


mkdir -p ${TMPDIR}
cd ${TMPDIR}

FILE=${TMPDIR}/${country}-${source}.html

wget "http://www.diyanet.nl/hdv-cami-hizmetleri/sube-cami-adresleri/" -O ${FILE} \
	> ${FILE}.out 2> ${FILE}.err

if [ -a ${FILE} ] 
then
    if [ -s ${FILE} ]
    then
	MONTH=$(date +%Y%m --reference ${FILE})
	DAY=$(date +%Y%m%d --reference ${FILE})

	extract_data
    fi
fi

# find ${STORAGE}/${country} -type f -a -mtime +14 | xargs --no-run-if-empty rm
# find ${STORAGE}/${country} -type d -a -empty | xargs --no-run-if-empty rmdir
