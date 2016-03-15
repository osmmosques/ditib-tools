#! /bin/sh

set -e

export HERE=$(dirname $(readlink -f $0))
. ${HERE}/settings.sh


COUNTRY=germany

SOURCE=ditib

extract_data() {

    country=${COUNTRY}
    county=${SOURCE}

    page=$1

    FILE=${TMPDIR}/${country}-${source}-${page}.html

    mkdir -p ${STORAGE}/${country}-${source}/${MONTH}/${DAY}

    cp -f \
        ${FILE} \
        ${STORAGE}/${country}-${source}/${MONTH}/${DAY}/${country}-${source}-page-${page}.html

    cp -f \
        ${FILE} \
        ${WEBDATA}/${country}-${source}-page-${page}.html
}


country=${COUNTRY}
source=${SOURCE}
for page in $(seq 0 9)
do
    :
    mkdir -p ${TMPDIR}
    cd ${TMPDIR}

    FILE=${TMPDIR}/${country}-${source}-${page}.html

    rm -f ${FILE}

    # http://www.ditib.de/default.php?id=13&lang=de
    # http://www.ditib.de/default.php?pageNum_kat=0&totalRows_kat=907&id=13&lang=de
    # http://www.ditib.de/default.php?pageNum_kat=1&totalRows_kat=907&id=13&lang=de
    # http://www.ditib.de/default.php?pageNum_kat=2&totalRows_kat=907&id=13&lang=de
    # http://www.ditib.de/default.php?pageNum_kat=6&totalRows_kat=907&id=13&lang=de
    wget "http://www.ditib.de/default.php?pageNum_kat="${page}"&totalRows_kat=907&id=13&lang=de" -O ${FILE} \
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

MONTH=$(date +%Y%m)
DAY=$(date +%Y%m%d)

# TODO grep in property file to obtain username / password for webapp
country=${COUNTRY}
for country in germany
do
    :

    mkdir -p ${STORAGE}/${country}/${MONTH}/${DAY}

    curl \
        "http://localhost:8888/rest/ditib/import-de" \
        -o ${LOGDIR}/curl-ditib-places-${country}-import.txt \
        > ${LOGDIR}/curl-ditib-places-${country}-import.out \
        2> ${LOGDIR}/curl-ditib-places-${country}-import.err

    for x in curl-ditib-places-${country}-import.txt curl-ditib-places-${country}-import.out curl-ditib-places-${country}-import.err
    do
        mv ${LOGDIR}/${x} ${STORAGE}/${country}/${MONTH}/${DAY}/${x}
    done

    curl \
        "http://localhost:8888/rest/ditibPlace?size=999&sort=name" \
        -o ${LOGDIR}/curl-ditib-places-data.txt \
        > ${LOGDIR}/curl-ditib-places-data.out \
        2> ${LOGDIR}/curl-ditib-places-data.err

    for x in curl-ditib-places-data.txt curl-ditib-places-data.out curl-ditib-places-data.err
    do
        mv ${LOGDIR}/${x} ${STORAGE}/${country}/${MONTH}/${DAY}/${x}
    done

    # Every single extracted DITIB place
    # cp -ar ${WEBDATA}/${country}-${source}-split-* ${STORAGE}/${country}-${source}/${MONTH}/${DAY}
done

# FINI
