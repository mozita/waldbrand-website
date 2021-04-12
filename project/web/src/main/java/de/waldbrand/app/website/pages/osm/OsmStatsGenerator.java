// Copyright 2021 Sebastian Kuerten
//
// This file is part of waldbrand-website.
//
// waldbrand-website is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// waldbrand-website is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with waldbrand-website. If not, see <http://www.gnu.org/licenses/>.

package de.waldbrand.app.website.pages.osm;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.Head;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.Table;
import de.topobyte.jsoup.components.TableRow;
import de.topobyte.webpaths.WebPath;
import de.waldbrand.app.website.Website;
import de.waldbrand.app.website.osm.OsmTypes;
import de.waldbrand.app.website.osm.PoiType;
import de.waldbrand.app.website.osm.model.OsmPoi;
import de.waldbrand.app.website.pages.base.SimpleBaseGenerator;
import de.waldbrand.app.website.util.MapUtil;

public class OsmStatsGenerator extends SimpleBaseGenerator
{

	public OsmStatsGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content() throws IOException
	{
		Head head = builder.getHead();
		MapUtil.head(head);

		List<String> names = OsmTypes.names(Arrays.asList(PoiType.values()));

		content.ac(HTML.h2("Wasserentnahmestellen (OpenStreetMap)"));
		P p = content.ac(HTML.p());
		p.appendText("Typen: " + Joiner.on(", ").join(names));

		Table table = content.ac(HTML.table());
		table.addClass("table");

		for (PoiType type : PoiType.values()) {
			List<OsmPoi> pois = Website.INSTANCE.getData().getTypeToPois()
					.get(type);
			TableRow row = table.row();
			row.cell(type.getName());
			row.cell(String.format("%d", pois.size()));
		}

		OsmAttributionUtil.attribution(content);
	}

}
