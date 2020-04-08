# Wikipedia-Api
Lightweight Api for Viewing Wikipedia Articles

## Examples
#### Get Article by Name
```java
//Create Wikipedia Client
Wikipedia w = new Wikipedia(Wikipedia.LANG_EN);

//Get all Articles with the Title "test"
WikipediaArticle[] articles = w.query("test");

//Get Content of First Article as html
String content = articles[0].getText(Wikipedia.TYPE_HTML);
```
#### Get Article by Id
```java
//Create Wikipedia Client
Wikipedia w = new Wikipedia(Wikipedia.LANG_EN);

//Get Article by Id
WikipediaArticle article = w.getArticle(3920);

//Get Content of Article as WikiText
String content = article.getText(Wikipedia.TYPE_WIKITEXT);
```
