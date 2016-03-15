#! /bin/sh

set -e

export HERE=$(dirname $(readlink -f $0))
. ${HERE}/settings.sh


# mysql -uroot -p$(cat ${HOME}/.my.pass) ${db} \
#     -e "update ditib_places set lat = 46.42, lon = 6.55 where lat = 0 and lon = 0;" \
#     > ${DB_DIR}/${db}-mosques-in-lac-leman.sql

mysqldump -uroot -p$(cat ${HOME}/.my.pass) --skip-extended-insert ${db} \
    > ${DB_DIR}/${db}-dump.sql

mysql -uroot -p$(cat ${HOME}/.my.pass) ${db} \
    -e "select D_KEY, LAT, LON, GEOCODED, ADDR_POSTCODE, ADDR_CITY, ADDR_STREET, ADDR_HOUSENUMBER, ADDR_STATE, PHONE, FAX from DITIB_PLACES order by D_KEY, NAME limit 9999;" \
    > ${DB_DIR}/${db}-ditib_places.sql
