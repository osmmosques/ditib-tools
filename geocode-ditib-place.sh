#! /bin/sh

set -e

export HERE=$(dirname $(readlink -f $0))
. ${HERE}/settings.sh


country=world

curl -X POST \
    "http://localhost:8888/rest/internal/ditib/geocode/enqueue" \
        -o ${LOGDIR}/geocode-ditib-place.txt \
        > ${LOGDIR}/geocode-ditib-place.out \
        2> ${LOGDIR}/geocode-ditib-place.err

for x in geocode-ditib-place.txt geocode-ditib-place.out geocode-ditib-place.err
do
    mv ${LOGDIR}/${x} ${STORAGE}/${country}/${MONTH}/${DAY}/${x}
done

# FINI
