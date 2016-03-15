#! /bin/sh

set -e

export HERE=$(dirname $(readlink -f $0))
. ${HERE}/settings.sh


COUNTRY=netherlands

SOURCE=diyanet

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

country=${COUNTRY}
for country in netherlands
do
    :

    mkdir -p ${STORAGE}/${country}/${MONTH}/${DAY}

    curl \
        "http://localhost:8888/rest/ditib/import-nl" \
        -o ${LOGDIR}/curl-ditib-places-${country}-import.txt \
        > ${LOGDIR}/curl-ditib-places-${country}-import.out \
        2> ${LOGDIR}/curl-ditib-places-${country}-import.err

    for x in curl-ditib-places-${country}-import.txt curl-ditib-places-${country}-import.out curl-ditib-places-${country}-import.err
    do
        mv ${LOGDIR}/${x} ${STORAGE}/${country}/${MONTH}/${DAY}/${x}
    done

done

# FINI
