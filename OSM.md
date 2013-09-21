Dans ce projet on a besoin de récupérer les points de recyclage.
On va chercher nos données dans OpenStreetMap.

Récupérer un noeud dans OSM :
	Exemple de requête GET sur un noeud d'ID : 1078409380
	http://api.openstreetmap.org/api/0.6/node/1078409380

	On récupére la data xml suivante :
	<osm version="0.6" generator="OpenStreetMap server" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
		<node id="1078409380" version="2" changeset="10544488" lat="43.6465125" lon="3.7964135" user="NicoB" uid="89288" visible="true" timestamp="2012-01-30T22:06:34Z">
			<tag k="amenity" v="recycling"/>
			<tag k="recycling:cans" v="yes"/>
			<tag k="recycling:glass" v="yes"/>
		</node>
	</osm>

	On observe que l'on a un noeud tag d'attribut k=amenity et d'attibut v=recycling pour un point de récolte de déchets
