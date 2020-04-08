package eu.oelschner.wikipedia;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.eclipsesource.json.*;

public class WikipediaArticle {

	private final int pageid;
	private final String lang;

	private String text = null;
	private String currentType = null;
	private String userAgent;

	WikipediaArticle(int pageid, String lang, String userAgent) {
		this.pageid = pageid;
		this.userAgent = userAgent;
		this.lang = lang;

	}

	WikipediaArticle(int pageid, Wikipedia w) {
		this(pageid, w.getLang(), w.getUserAgent());
	}

	public int getPageid() {
		return pageid;
	}

	public String getLang() {
		return lang;
	}

	public String getText(String type) throws IOException, InterruptedException {
		if (this.text != null && this.currentType.contentEquals(type))
			return this.text;

		URI uri = URI.create(Wikipedia.PREFIX + this.lang + "."
				+ Wikipedia.API_GET_TEXT.replace("<id>", String.valueOf(this.getPageid())).replace("<type>", type));
		
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("User-Agent", this.userAgent);

		HttpResponse<String> response = Utils.httpRequest(uri, header);

		this.currentType = type;
		
		return Json.parse(response.body()).asObject().get("parse").asObject().get(type).asString();
	}

	public String getTitle() throws IOException, InterruptedException {
		URI uri = URI.create(Wikipedia.PREFIX + this.lang + "."
				+ Wikipedia.API_GET_TEXT.replace("<id>", String.valueOf(this.getPageid())).replace("<type>", Wikipedia.TYPE_WIKITEXT));
		
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("User-Agent", this.userAgent);

		HttpResponse<String> response = Utils.httpRequest(uri, header);

		return Json.parse(response.body()).asObject().get("parse").asObject().get("title").asString();
	}

}
