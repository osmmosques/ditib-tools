#! /bin/sh

OSMOSIS=${HOME}/osmosis-0.43.1
STORAGE=${HOME}/Dropbox/osmdata
TMPDIR=${HOME}/tmp/osm-place-ditib
WEBDATA=/home/tomcat/osm-mosques/data

COUNTRY=germany

SOURCE=ditib

extract_data() {

    country=${COUNTRY}
    county=${SOURCE}

    page=$1

    FILE=${TMPDIR}/${source}-${country}-${page}.html

    mkdir -p ${STORAGE}/${source}-${country}/${MONTH}/${DAY}

    cp -f \
	${FILE} \
	${STORAGE}/${source}-${country}/${MONTH}/${DAY}/${source}-${country}-page-${page}.html

    cp -f \
	${FILE} \
	${WEBDATA}/${source}-${country}-page-${page}.html
}


country=${COUNTRY}
source=${SOURCE}
for page in $(seq 1 8)
do
    :
    mkdir -p ${TMPDIR}
    cd ${TMPDIR}

    FILE=${TMPDIR}/${source}-${country}-${page}.html

    rm -f ${FILE}

    wget "http://www.ditib.de/default.php?pageNum_kat="${page}"&id=12&lang=de&12" -O ${FILE} \
	> ${FILE}.out 2> ${FILE}.err

    if [ -a ${FILE} ] 
    then
	if [ -s ${FILE} ]
	then
	    MONTH=$(date +%Y%m --reference ${FILE})
	    DAY=$(date +%Y%m%d --reference ${FILE})

	    extract_data ${page}
	fi
    fi

    find ${STORAGE}/${country} -type f -a -mtime +14 | xargs --no-run-if-empty rm
    find ${STORAGE}/${country} -type d -a -empty | xargs --no-run-if-empty rmdir
done

# TODO grep in property file to obtain username / password for webapp
country=${COUNTRY}
for country in germany
do
    :

    mkdir -p ${STORAGE}/${country}/${MONTH}/${DAY}

    curl \
	"http://localhost:8888/osm-mosques-rest/ditib/import" \
	-o ${STORAGE}/${country}/${MONTH}/${DAY}/curl-ditib-places-import.txt \
	> ${STORAGE}/${country}/${MONTH}/${DAY}/curl-ditib-places-import.out \
	2> ${STORAGE}/${country}/${MONTH}/${DAY}/curl-ditib-places-import.err

    curl \
	"http://localhost:8888/osm-mosques-rest/ditibPlace?size=999&sort=name" \
	-o ${STORAGE}/${country}/${MONTH}/${DAY}/curl-ditib-places-data.txt \
	> ${STORAGE}/${country}/${MONTH}/${DAY}/curl-ditib-places-data.out \
	2> ${STORAGE}/${country}/${MONTH}/${DAY}/curl-ditib-places-data.err

    # cp -ar ${WEBDATA}/${SOURCE}-${country}-split-* ${STORAGE}/${SOURCE}-${country}/${MONTH}/${DAY}
done

db=osm_mosques

DB_DIR=${STORAGE}/database/${MONTH}/${DAY}

mkdir -p ${DB_DIR}

mysqldump -uroot -p$(cat ${HOME}/.my.pass) --skip-extended-insert ${db} \
    > ${DB_DIR}/${db}-dump.sql

mysql -uroot -p$(cat ${HOME}/.my.pass) ${db} \
    -e "select d_code, d_key, addr_postcode, addr_city, addr_street, addr_housenumber, addr_state, phone, fax from ditib_places order by d_code, name limit 9999;" \
    > ${DB_DIR}/${db}-ditib_places.sql
