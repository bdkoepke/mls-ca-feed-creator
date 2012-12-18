@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' )

import groovyx.net.http.HTTPBuilder
import org.codehaus.groovy.runtime.DateGroovyMethods
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.JSON
import groovy.text.SimpleTemplateEngine
import net.sf.json.JSONNull

def dirpath = "./"

// an array of maps with title, file and query xml
def searches = [
	[title:"Saltspring - residential 100K - 400K",
		file: "ss-res.xml",
		xml:'<ListingSearchMap><PolygonPoints>-123.5951614379883 48.92452953490443,-123.52478027343751 48.91708480384718,-123.41217041015626 48.843706195467924,-123.37818145751955 48.7525671836539,-123.52924346923828 48.75120902416533,-123.59001159667969 48.8823280521585,-123.5951614379883 48.92452953490443</PolygonPoints><Culture>en-CA</Culture><OrderBy>1</OrderBy><OrderDirection>A</OrderDirection><Culture>en-CA</Culture><LatitudeMax>48.9310709582563</LatitudeMax><LatitudeMin>48.60340355404897</LatitudeMin><LongitudeMax>-123.1945037841797</LongitudeMax><LongitudeMin>-123.77746582031251</LongitudeMin><PriceMax>400000</PriceMax><PriceMin>100000</PriceMin><PropertyTypeID>300</PropertyTypeID><TransactionTypeID>2</TransactionTypeID><MinBath>0</MinBath><MaxBath>0</MaxBath><MinBed>0</MinBed><MaxBed>0</MaxBed><StoriesTotalMin>0</StoriesTotalMin><StoriesTotalMax>0</StoriesTotalMax></ListingSearchMap>'],
	[title:"Saltspring - land 100K - 350K",
		file: "ss-land.xml",
		xml:'<ListingSearchMap><PolygonPoints>-123.5951614379883 48.92452953490443,-123.52478027343751 48.91708480384718,-123.41217041015626 48.843706195467924,-123.37818145751955 48.7525671836539,-123.52924346923828 48.75120902416533,-123.59001159667969 48.8823280521585,-123.5951614379883 48.92452953490443</PolygonPoints><Culture>en-CA</Culture><OrderBy>1</OrderBy><OrderDirection>A</OrderDirection><Culture>en-CA</Culture><LandUseID>-1</LandUseID><LatitudeMax>49.03741780590361</LatitudeMax><LatitudeMin>48.65241782207756</LatitudeMin><LongitudeMax>-123.21372985839847</LongitudeMax><LongitudeMin>-123.79669189453126</LongitudeMin><PriceMax>350000</PriceMax><PriceMin>100000</PriceMin><PropertyTypeID>303</PropertyTypeID><TransactionTypeID>2</TransactionTypeID><LandSizeMin>0</LandSizeMin><LandSizeMax>10</LandSizeMax></ListingSearchMap>'],
	[title:"Cortes - residential 100K - 400K",
		file: "cortes-res.xml",
		xml:'<ListingSearchMap><Culture>en-CA</Culture><OrderBy>1</OrderBy><OrderDirection>A</OrderDirection><Culture>en-CA</Culture><LatitudeMax>50.18811020464772</LatitudeMax><LatitudeMin>50.00045707622858</LatitudeMin><LongitudeMax>-124.84039306640626</LongitudeMax><LongitudeMin>-125.13187408447266</LongitudeMin><PriceMax>400000</PriceMax><PriceMin>100000</PriceMin><PropertyTypeID>300</PropertyTypeID><TransactionTypeID>2</TransactionTypeID><MinBath>0</MinBath><MaxBath>0</MaxBath><MinBed>0</MinBed><MaxBed>0</MaxBed><StoriesTotalMin>0</StoriesTotalMin><StoriesTotalMax>0</StoriesTotalMax></ListingSearchMap>'],
	[title:"Cortes - land - 100K - 350K",
		file: "cortes-land.xml",
		xml:'<ListingSearchMap><Culture>en-CA</Culture><OrderBy>1</OrderBy><OrderDirection>A</OrderDirection><Culture>en-CA</Culture><LandUseID>-1</LandUseID><LatitudeMax>50.18811020464772</LatitudeMax><LatitudeMin>50.00045707622858</LatitudeMin><LongitudeMax>-124.84039306640626</LongitudeMax><LongitudeMin>-125.13187408447266</LongitudeMin><PriceMax>350000</PriceMax><PriceMin>100000</PriceMin><PropertyTypeID>303</PropertyTypeID><TransactionTypeID>2</TransactionTypeID></ListingSearchMap>'],
	[title:"Quadra land - 100K - 350K",
		file: "q-land.xml",
		xml:'<ListingSearchMap><Culture>en-CA</Culture><OrderBy>1</OrderBy><OrderDirection>A</OrderDirection><Culture>en-CA</Culture><LandUseID>-1</LandUseID><LatitudeMax>50.15666574810976</LatitudeMax><LatitudeMin>49.968889261762214</LatitudeMin><LongitudeMax>-125.04810333251952</LongitudeMax><LongitudeMin>-125.33958435058595</LongitudeMin><PriceMax>350000</PriceMax><PriceMin>100000</PriceMin><PropertyTypeID>303</PropertyTypeID><TransactionTypeID>2</TransactionTypeID></ListingSearchMap>'],
	[title:"Quadra residential - 100K - 400K",
		file: "q-res.xml",
		xml:'<ListingSearchMap><PolygonPoints>-125.255126953125 50.147426381905575,-125.17616271972659 50.144126175613266,-125.1329040527344 50.00751836604389,-125.20225524902345 50.00023639420784,-125.24345397949217 50.06308977990468,-125.255126953125 50.147426381905575</PolygonPoints><Culture>en-CA</Culture><OrderBy>1</OrderBy><OrderDirection>A</OrderDirection><Culture>en-CA</Culture><LatitudeMax>50.16629918559332</LatitudeMax><LatitudeMin>50.00023639420784</LatitudeMin><LongitudeMax>-125.05624008178712</LongitudeMax><LongitudeMin>-125.34772109985353</LongitudeMin><PriceMax>400000</PriceMax><PriceMin>100000</PriceMin><PropertyTypeID>300</PropertyTypeID><TransactionTypeID>2</TransactionTypeID><MinBath>0</MinBath><MaxBath>0</MaxBath><MinBed>0</MinBed><MaxBed>0</MaxBed><StoriesTotalMin>0</StoriesTotalMin><StoriesTotalMax>0</StoriesTotalMax></ListingSearchMap>']
]

def today = DateGroovyMethods.format(new Date(), "EEE, dd MMM yyyy HH:mm:ss Z")

def channel_str = '''<?xml version="1.0"?>
<rss xmlns:content="http://purl.org/rss/1.0/modules/content/" xmlns:sy="http://purl.org/rss/1.0/modules/syndication/" version="2.0">
    <channel>
        <title>MLS listings for $FEEDTITLE</title>
        <link>http://www.mydomain.org</link>
        <description>MLS listings for $FEEDTITLE</description>
        <lastBuildDate>$DATE</lastBuildDate>
        <language>en-US</language>
        <sy:updatePeriod>daily</sy:updatePeriod>
        <sy:updateFrequency>1</sy:updateFrequency>
		$ITEMS
    </channel>
</rss>
'''
def item_str = '''
        <item>
            <title>$TITLE</title>
            <link>
http://www.realtor.ca/propertyDetails.aspx?propertyId=$PID
</link>
            <pubDate>$DATE</pubDate>
            <guid isPermaLink="false">http://www.realtor.ca/propertyDetails.aspx?propertyId=$PID</guid>
            <description><![CDATA[
$DESCRIPTION
]]></description>
            <content:encoded><![CDATA[
$CONTENT
]]></content:encoded>
        </item>
'''

def engine = new SimpleTemplateEngine()
def item_template = engine.createTemplate(item_str);
def channel_template = engine.createTemplate(channel_str);

searches.each { search ->

	File file = new File("${dirpath}${search.file}")
	if (file.exists()) file.delete();
	
	def channel_output = new StringBuffer()

	def http = new HTTPBuilder('http://www.realtor.ca/')
	http.request( GET, JSON ) {
		uri.path = '/handlers/MapSearchHandler.ashx'
		uri.query = [ xml: search.xml ]

		def items_output = new StringBuffer()
		response.success = { resp, json ->
			println json.MapSearchResults.size()
			json.MapSearchResults.each { item ->

				def content = new StringBuffer()

				if (item instanceof JSONNull == false) {

					if (item.Latitude && item.Longitude) {
						def q = java.net.URLEncoder.encode("${item.Latitude}, ${item.Longitude}")
						content.append('<a href="https://maps.google.com/maps?q=' + q + '">MAP LINK</a><br/>')
					}

					item.PropertyLowResPhotos.each { photo ->
						def url = item.PropertyLowResImagePath + photo
						url = url.replace('lowres', 'highres');
						def tag = '<img src="' + url + '"/>&nbsp;'
						content.append(tag)
					}

					def item_data = ["TITLE":"${item.Price} - ${item.Address} (${item.MLS})", "DATE":today, "DESCRIPTION":"${item.Price} - ${item.Address}", "CONTENT":content, "PID":"${item.PropertyID}"]
					items_output.append(item_template.make(item_data))
				}
			}
			def channel_data = ["FEEDTITLE":search.title, "DATE":today, "ITEMS":items_output]

			channel_output.append(channel_template.make(channel_data))

			file << channel_output.toString()
		}
	}
}
