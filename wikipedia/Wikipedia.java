package eu.oelschner.wikipedia;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.function.Consumer;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

public class Wikipedia {

	public static final String PREFIX = "https://";

	public static final String LANG_EN = "en";
	public static final String LANG_DE = "de";

	public static final String TYPE_WIKITEXT = "wikitext";
	public static final String TYPE_HTML = "text";

	public static final String API_QUERY = "wikipedia.org/w/api.php?action=query&titles=<title>&format=json";
	public static final String API_GET_TEXT = "wikipedia.org/w/api.php?action=parse&prop=<type>&pageid=<id>&formatversion=2&format=json";

	public static final String USER_AGENT_WIKI_CLIENT = "WikiClient/1.0";
	public static final String USER_AGENT_WIN_10_EDGE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246";
	public static final String USER_AGENT_CHROME = "Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36";
	public static final String USER_AGENT_ANDROID = "Mozilla/5.0 (Linux; Android 8.0.0; SM-G960F Build/R16NW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.84 Mobile Safari/537.36";

	private final String lang;
	private String userAgent = "WikiClient/1.0";

	public Wikipedia(String lang) {
		this.lang = lang;
	}

	public Wikipedia() {
		this(LANG_EN);
	}

	public String getLang() {
		return lang;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	private int i = 0;

	public WikipediaArticle getArticle(int pageid) {
		return new WikipediaArticle(pageid, this);
	}
	
	public WikipediaArticle[] query(String title) throws IOException, InterruptedException {

		URI uri = URI.create(Wikipedia.PREFIX + this.lang + "." + Wikipedia.API_QUERY.replace("<title>", title));

		HashMap<String, String> header = new HashMap<String, String>();
		header.put("User-Agent", this.userAgent);

		HttpResponse<String> response = Utils.httpRequest(uri, header);

		JsonObject jo = Json.parse(response.body()).asObject().get("query").asObject().get("pages").asObject();

		WikipediaArticle[] articles = new WikipediaArticle[jo.size()];

		i = 0;
		jo.forEach(new Consumer<JsonObject.Member>() {
			@Override
			public void accept(JsonObject.Member member) {
				articles[i] = new WikipediaArticle(member.getValue().asObject().get("pageid").asInt(), getLang(),
						getUserAgent());
				i++;
			}
		});

		return articles;
	}
}
