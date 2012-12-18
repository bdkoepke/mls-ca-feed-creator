mls-ca-feed-creator
===================

Creates an RSS feed based on an Canadian MLS realty site search (realtor.ca).   There is no API for the Canadian MLS and ideally wanted a feed i could check daily for new properties in my desired search area.   So far this script seems to fit the bill.

- to use this, you need to snag the query XML that the realtor.ca sends for the search you want to create a feed from.   You may need to use a tool to URL unescape the string.  You will need to use firebug, or Chrome inspector etc. to get this.   It should start with (<ListingSearchMap>...).
- create a new map entry in the searches array using title, output file name and the XML from the above step.
- set up a daily cron to run this script
- add the URL to the output files created by the script to your favorite feed reader
