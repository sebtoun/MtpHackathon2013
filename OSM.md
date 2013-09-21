Dans ce projet on a besoin de récupérer les points de recyclage.
On va chercher nos données dans OpenStreetMap.

Récupérer un noeud dans OSM :
	Exemple de requête GET sur un noeud d'ID : 1078409380
	http://api.openstreetmap.org/api/0.6/node/1078409380
Pour centrer la carte openstreetmap sur le noeud en question :
	http://www.openstreetmap.org/#map=10/43.6465125/3.7964135
Map va de 1 toute la carte du monde à 19 l'échelle d'une rue

	On récupére la data xml suivante :
	<osm version="0.6" generator="OpenStreetMap server" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
	<node id="1078409380" version="2" changeset="10544488" lat="43.6465125" lon="3.7964135" user="NicoB" uid="89288" visible="true" timestamp="2012-01-30T22:06:34Z">
	<tag k="amenity" v="recycling"/>
	<tag k="recycling:cans" v="yes"/>
	<tag k="recycling:glass" v="yes"/>
	</node>
	</osm>

	On observe que l'on a un noeud tag d'attribut k=amenity et d'attibut v=recycling pour un point de récolte de déchets

On peut récupérer tous les éléments dans une boundingbox :
exemple la requête GET : http://api.openstreetmap.org/api/0.6/map?bbox=3.7964130,43.6465120,3.7964140,43.6465130
On y retrouve notre noeud.

left,bottom,right,top
where:
left is the longitude of the left (westernmost) side of the bounding box.
bottom is the latitude of the bottom (southernmost) side of the bounding box.
right is the longitude of the right (easternmost) side of the bounding box.
top is the latitude of the top (northernmost) side of the bounding box.

Définie une bounding box et renvoie une liste de node
<?xml version="1.0" encoding="UTF-8"?>
<osm version="0.6" generator="CGImap 0.2.0" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
 <bounds minlat="43.6465120" minlon="3.7964130" maxlat="43.6465130" maxlon="3.7964140"/>
 <node id="1078409380" visible="true" version="2" changeset="10544488" timestamp="2012-01-30T22:06:34Z" user="NicoB" uid="89288" lat="43.6465125" lon="3.7964135">
  <tag k="amenity" v="recycling"/>
  <tag k="recycling:cans" v="yes"/>
  <tag k="recycling:glass" v="yes"/>
 </node>
 <node id="1834975377" visible="true" version="1" changeset="12445089" timestamp="2012-07-23T06:41:30Z" user="NicoB" uid="89288" lat="43.6466153" lon="3.7968171"/>
 <node id="966886974" visible="true" version="1" changeset="6197501" timestamp="2010-10-27T16:07:43Z" user="NicoB" uid="89288" lat="43.6470262" lon="3.7964580"/>
 <node id="1834975376" visible="true" version="1" changeset="12445089" timestamp="2012-07-23T06:41:30Z" user="NicoB" uid="89288" lat="43.6465390" lon="3.7969824"/>
 <node id="966893346" visible="true" version="1" changeset="6197501" timestamp="2010-10-27T16:15:10Z" user="NicoB" uid="89288" lat="43.6468656" lon="3.7967764"/>
 <node id="1078409373" visible="true" version="1" changeset="6836099" timestamp="2011-01-02T09:08:19Z" user="NicoB" uid="89288" lat="43.6469101" lon="3.7962247"/>
 <node id="966925013" visible="true" version="1" changeset="6197501" timestamp="2010-10-27T16:41:46Z" user="NicoB" uid="89288" lat="43.6468493" lon="3.7967611"/>
 <node id="1078409381" visible="true" version="2" changeset="12445089" timestamp="2012-07-23T06:41:33Z" user="NicoB" uid="89288" lat="43.6467526" lon="3.7971490"/>
 <node id="1078409378" visible="true" version="1" changeset="6836099" timestamp="2011-01-02T09:08:19Z" user="NicoB" uid="89288" lat="43.6466729" lon="3.7965273"/>
 <node id="966921525" visible="true" version="1" changeset="6197501" timestamp="2010-10-27T16:39:04Z" user="NicoB" uid="89288" lat="43.6468268" lon="3.7968067"/>
 <node id="1086845948" visible="true" version="1" changeset="6887448" timestamp="2011-01-06T21:37:43Z" user="NicoB" uid="89288" lat="43.6467901" lon="3.7968521"/>
 <node id="1086845927" visible="true" version="1" changeset="6887448" timestamp="2011-01-06T21:37:42Z" user="NicoB" uid="89288" lat="43.6466432" lon="3.7965711"/>
 <node id="1078409376" visible="true" version="1" changeset="6836099" timestamp="2011-01-02T09:08:19Z" user="NicoB" uid="89288" lat="43.6464065" lon="3.7966397"/>
 <node id="1078409377" visible="true" version="1" changeset="6836099" timestamp="2011-01-02T09:08:19Z" user="NicoB" uid="89288" lat="43.6466022" lon="3.7964928"/>
 <node id="1078409374" visible="true" version="1" changeset="6836099" timestamp="2011-01-02T09:08:19Z" user="NicoB" uid="89288" lat="43.6468543" lon="3.7969749"/>
 <way id="93658774" visible="true" version="4" changeset="15793507" timestamp="2013-04-20T06:55:23Z" user="cquest" uid="158826">
  <nd ref="1078409380"/>
  <nd ref="1078409377"/>
  <nd ref="1086845927"/>
  <nd ref="1078409378"/>
  <nd ref="1078409373"/>
  <nd ref="966886974"/>
  <nd ref="966893346"/>
  <nd ref="966925013"/>
  <nd ref="966921525"/>
  <nd ref="1086845948"/>
  <nd ref="1078409374"/>
  <nd ref="1078409381"/>
  <nd ref="1834975376"/>
  <nd ref="1834975377"/>
  <nd ref="1078409376"/>
  <nd ref="1078409380"/>
  <tag k="amenity" v="parking"/>
  <tag k="capacity:disabled" v="2"/>
  <tag k="name" v="Parking Jean Ponsy"/>
  <tag k="source" v="cadastre-dgi-fr source : Direction Générale des Impôts - Cadastre. Mise à jour : 2011"/>
 </way>
</osm>

